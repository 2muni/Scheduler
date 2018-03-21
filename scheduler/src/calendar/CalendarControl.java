package calendar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import scheduler.Container;
import system.Properties;


public class CalendarControl {
	
	CalendarView view;
	CalendarModel model;
	
	Thread timeThread;
	
	final int NUMBER_OF_COLS;
	final int NUMBER_OF_ROWS;
	final String DIR_OF_MEMO;	
	String fileName; 
	String curYYYYMMLab, curYYYYMMDDLab, selYYYYMMDDLab;	
	String dDayString;

	
	public CalendarControl(CalendarView view, CalendarModel model){
		
		this.view = view;
		this.model = model;
		timeThread = new Thread(new ThreadControl());
		
		DIR_OF_MEMO = "MemoData/";		
		NUMBER_OF_COLS = model.NUMBER_OF_COLS();
		NUMBER_OF_ROWS = model.NUMBER_OF_ROWS();
		dDayString = "Today";
		setDateString();
		
	}
	
	public void setWeekdays(int col) {
		view.weekday[col].setText(model.WEEK_DAY_NAME[col]);
	}
	
	private void setDateString() {
		
		curYYYYMMLab = model.calYear + " - "
				+ ((model.calMonth+1)<10?"0":"")
				+ (model.calMonth+1);
		
		curYYYYMMDDLab = model.today.get(Calendar.YEAR) + "-"
				+ ((model.today.get(Calendar.MONTH)+1)<10?"0":"")
				+ (model.today.get(Calendar.MONTH)+1) + "-"
				+ (model.today.get(Calendar.DAY_OF_MONTH)<10?"0":"")
				+ model.today.get(Calendar.DAY_OF_MONTH) + " "
				+ model.WEEK_DAY_NAME[(model.today.get(Calendar.DAY_OF_WEEK)-1)] + "¿äÀÏ";
		
		selYYYYMMDDLab = model.cal.get(Calendar.YEAR) + "/"
				+ ((model.cal.get(Calendar.MONTH)+1)<10?"0":"")
				+ (model.cal.get(Calendar.MONTH)+1) + "/"
				+ (model.cal.get(Calendar.DAY_OF_MONTH)<10?"0":"")
				+ model.cal.get(Calendar.DAY_OF_MONTH) + " ("+dDayString+")";
		
		fileName = model.calYear
				+ ((model.calMonth+1)<10?"0":"")
				+ (model.calMonth+1) 
				+ (model.calDayOfMon<10?"0":"")
				+ model.calDayOfMon + ".txt";
	}
	
	public void printCalDates(){
		for(int row = 0; row < NUMBER_OF_ROWS; row++){
			for(int col = 0; col < NUMBER_OF_COLS; col++){
				
				view.dateButs[row][col].setText(Integer.toString(
						model.calDates[row][col]));
				if(col==0) 
					view.dateButs[row][col].setForeground(Color.RED);
				else if(col==6) 
					view.dateButs[row][col].setForeground(Color.BLUE);
				else
					view.dateButs[row][col].setForeground(Color.BLACK);
				
				
				File f =new File(DIR_OF_MEMO + model.calYear
						+ ((model.calMonth+1)<10?"0":"")
						+ (model.calMonth+1) 
						+ (model.calDates[row][col]<10?"0":"")
						+ model.calDates[row][col] + ".txt");
				
				if(f.exists())
					view.dateButs[row][col].setBackground(new Color(250, 250, 100));
				else
					view.dateButs[row][col].setBackground(Color.WHITE);
				
				view.dateButs[row][col].removeAll();
				if(model.calMonth == model.today.get(Calendar.MONTH) &&
					model.calYear == model.today.get(Calendar.YEAR) &&
					model.calDates[row][col] == model.today.get(Calendar.DAY_OF_MONTH)){
					view.dateButs[row][col].setForeground(new Color(0, 128, 0));
				}
				
				if(model.calDates[row][col] == 0) 
					view.dateButs[row][col].setVisible(false);
				else 
					view.dateButs[row][col].setVisible(true);
			}
		}
	}
	

	private class ThreadControl implements Runnable{
		
		@Override
		public void run(){
			boolean msgCntFlag = false;
			int num = 0;
			String curStr = new String();
			while(true){
				try{
					model.today = Calendar.getInstance();
					String amPm = (model.today.get(Calendar.AM_PM)==0?"AM":"PM");
					String hour;
							if(model.today.get(Calendar.HOUR) == 0) hour = "12"; 
							else if(model.today.get(Calendar.HOUR) == 12) hour = " 0";
							else hour = (model.today.get(Calendar.HOUR)<10?" ":"")+model.today.get(Calendar.HOUR);
					String min = (model.today.get(Calendar.MINUTE)<10?"0":"")+model.today.get(Calendar.MINUTE);
					String sec = (model.today.get(Calendar.SECOND)<10?"0":"")+model.today.get(Calendar.SECOND);
					view.infoClock.setText(amPm+" "+hour+":"+min+":"+sec);

					Thread.sleep(1000);
					String infoStr = view.noticeLab.getText();
					
					if(infoStr != " " && (msgCntFlag == false || curStr != infoStr)){
						num = 3;
						msgCntFlag = true;
						curStr = infoStr;
					}
					else if(infoStr != " " && msgCntFlag == true){
						if(num > 0) num--;
						else{
							msgCntFlag = false;
							view.noticeLab.setText(" ");
						}
					}		
				}
				catch(InterruptedException e){
					System.out.println("Error: " + e);
				}
			}
		}
	}
	
	
	public class ListenForCalOpButtons implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == view.todayBut){
				model.setToday();
				view.dateAction.actionPerformed(e);
			}
			else if(e.getSource() == view.lYearBut) model.moveMonth(-12);
			else if(e.getSource() == view.lMonBut) model.moveMonth(-1);
			else if(e.getSource() == view.nMonBut) model.moveMonth(1);
			else if(e.getSource() == view.nYearBut) model.moveMonth(12);
			
			setDateString();
			
			view.curYYYYMMLab.setText(curYYYYMMLab);
			printCalDates();
		}
	}

	public class ListenForDateButtons implements ActionListener{
		public void actionPerformed(ActionEvent e) {
						
			for(int row = 0; row < NUMBER_OF_ROWS; row++){
				for(int col = 0; col < NUMBER_OF_COLS; col++){
					if(e.getSource() == view.dateButs[row][col])
						model.calDayOfMon = model.calDates[row][col];			
				}
			}

			model.cal = new GregorianCalendar(model.calYear, model.calMonth, model.calDayOfMon);
						
			int dDay=((int)((model.cal.getTimeInMillis() - model.today.getTimeInMillis())/1000/60/60/24));
			if(dDay == 0 && (model.cal.get(Calendar.YEAR) == model.today.get(Calendar.YEAR)) 
					&& (model.cal.get(Calendar.MONTH) == model.today.get(Calendar.MONTH))
					&& (model.cal.get(Calendar.DAY_OF_MONTH) == model.today.get(Calendar.DAY_OF_MONTH))) dDayString = "Today"; 
			else if(dDay >=0) dDayString = "D-" + (dDay+1);
			else if(dDay < 0) dDayString = "D+" + (dDay) * (-1);
			
			setDateString();
			
			view.selectedDate.setText(selYYYYMMDDLab);
			readMemo();
		}
	}
	
	public void readMemo(){
		try{
			File f = new File(DIR_OF_MEMO + fileName);
			if(f.exists()){
				BufferedReader in = new BufferedReader(
						new FileReader(DIR_OF_MEMO + fileName));
				String memoAreaText= new String();
				while(true){
					String tempStr = in.readLine();
					if(tempStr == null) break;
					memoAreaText = memoAreaText + tempStr 
							+ System.getProperty("line.separator");
				}
				view.memoArea.setText(memoAreaText);
				in.close();	
			}
			else view.memoArea.setText("");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class ListenForMemoSaveButton implements ActionListener{
		
		public void actionPerformed(ActionEvent arg0) {
			try {
				File f= new File("MemoData");
				if(!f.isDirectory()) f.mkdir();
				
				String memo = view.memoArea.getText();
				if(memo.length()>0){
					BufferedWriter out = new BufferedWriter(
							new FileWriter(DIR_OF_MEMO + fileName));
					
					String str = view.memoArea.getText();
					out.write(str);  
					out.close();
					view.noticeLab.setText(fileName
							+ model.SaveButMsg1);
				}
				else 
					view.noticeLab.setText(model.SaveButMsg2);
			} catch (IOException e) {
				view.noticeLab.setText(model.SaveButMsg3);
			}
			printCalDates();
		}					
	}

	public class ListenForMemoDeleteButton implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			view.memoArea.setText("");
			File f =new File(DIR_OF_MEMO + fileName);
			if(f.exists()){
				f.delete();
				printCalDates();
				view.noticeLab.setText(model.DelButMsg1);
			}
			else 
				view.noticeLab.setText(model.DelButMsg2);					
		}					
	}
	
	public class ListenForMemoClearButton implements ActionListener{
		
		public void actionPerformed(ActionEvent arg0) {
			view.memoArea.setText(null);
			view.noticeLab.setText(model.ClrButMsg1);
			
		}
	}
	
}
