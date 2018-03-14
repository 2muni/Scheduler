package calendar;

import java.awt.Color;
import java.awt.Font;
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

import javax.swing.JButton;


public class CalendarControl {
	
	CalendarView view;
	CalendarModel model;
	
	String curYYYYMMLab, curYYYYMMDDLab, selYYYYMMDDLab;
	
	String dDayString;
	final String DIR_OF_MEMO;
	final String NAME_OF_MEMO;  
	
	final int NUMBER_OF_COLS;
	final int NUMBER_OF_ROWS;
	
	CalendarControl(CalendarView view){
		this.view = view;
		model = new CalendarModel();

		DIR_OF_MEMO = "MemoData/";
		NAME_OF_MEMO = model.calYear
				+ ((model.calMonth+1)<10?"0":"")
				+ (model.calMonth+1) 
				+ (model.calDayOfMon<10?"0":"")
				+ model.calDayOfMon+".txt";
		NUMBER_OF_COLS = model.NUMBER_OF_COLS();
		NUMBER_OF_ROWS = model.NUMBER_OF_ROWS();
		dDayString = "Today";
		setLabels();
		
	}
	
	public void setWeekdays(int col) {
		view.weekday[col].setText(model.WEEK_DAY_NAME[col]);
	}
	
	private void setLabels() {
		
		curYYYYMMLab = model.calYear + " - "
				+ ((model.calMonth+1)<10?"0":"")
				+ (model.calMonth+1);
		
		curYYYYMMDDLab = model.today.get(Calendar.YEAR) + "-"
				+ ((model.today.get(Calendar.MONTH)+1)<10?"0":"")
				+ (model.today.get(Calendar.MONTH)+1) + "-"
				+ model.today.get(Calendar.DAY_OF_MONTH);
		
		selYYYYMMDDLab = model.today.get(Calendar.YEAR) + "/"
				+ ((model.today.get(Calendar.MONTH)+1)<10?"0":"")
				+ (model.today.get(Calendar.MONTH)+1) + "/"
				+ model.today.get(Calendar.DAY_OF_MONTH) + " ("+dDayString+")";
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
				
				
				File f =new File(DIR_OF_MEMO + NAME_OF_MEMO);
				
				if(f.exists())
					view.dateButs[row][col].setFont(new Font("맑은 고딕", Font.BOLD,24));
				else 
					view.dateButs[row][col].setFont(new Font("맑은 고딕", Font.PLAIN,24));

				
				view.dateButs[row][col].removeAll();
				if(model.calMonth == model.today.get(Calendar.MONTH) &&
					model.calYear == model.today.get(Calendar.YEAR) &&
					model.calDates[row][col] == model.today.get(Calendar.DAY_OF_MONTH)){
					view.dateButs[row][col].setForeground(Color.GREEN);
				}
				
				if(model.calDates[row][col] == 0) 
					view.dateButs[row][col].setVisible(false);
				else 
					view.dateButs[row][col].setVisible(true);
			}
		}
	}
	
	public class ListenForCalOpButtons implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == view.todayBut){
				model.setToday();
			}
			else if(e.getSource() == view.lYearBut) model.moveMonth(-12);
			else if(e.getSource() == view.lMonBut) model.moveMonth(-1);
			else if(e.getSource() == view.nMonBut) model.moveMonth(1);
			else if(e.getSource() == view.nYearBut) model.moveMonth(12);
			
			setLabels();
			
			view.curYYYYMMLab.setText(curYYYYMMLab);
			printCalDates();
		}
	}

	public class ListenForDateButtons implements ActionListener{
		public void actionPerformed(ActionEvent e) {
						
			int k=0,l=0;
			for(int row = 0; row < NUMBER_OF_ROWS; row++){
				for(int col = 0; col < NUMBER_OF_COLS; col++){
					if(e.getSource() == view.dateButs[row][col]){ 
						k=row;
						l=col;
					}
				}
			}

			if(!(k ==0 && l == 0)) model.calDayOfMon = model.calDates[k][l]; //today버튼을 눌렀을때도 이 actionPerformed함수가 실행되기 때문에 넣은 부분
			
			model.cal = new GregorianCalendar(model.calYear, model.calMonth, model.calDayOfMon);
			
			
			int dDay=((int)((model.cal.getTimeInMillis() - model.today.getTimeInMillis())/1000/60/60/24));
			if(dDay == 0 && (model.cal.get(Calendar.YEAR) == model.today.get(Calendar.YEAR)) 
					&& (model.cal.get(Calendar.MONTH) == model.today.get(Calendar.MONTH))
					&& (model.cal.get(Calendar.DAY_OF_MONTH) == model.today.get(Calendar.DAY_OF_MONTH))) dDayString = "Today"; 
			else if(dDay >=0) dDayString = "D-" + (dDay+1);
			else if(dDay < 0) dDayString = "D+" + (dDay) * (-1);
			
			setLabels();
			
			view.selectedDate.setText(selYYYYMMDDLab);
			readMemo();
		}
	}
	
	public void readMemo(){
		try{
			File f = new File(DIR_OF_MEMO + NAME_OF_MEMO);
			if(f.exists()){
				BufferedReader in = new BufferedReader(
						new FileReader(DIR_OF_MEMO + NAME_OF_MEMO));
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
							new FileWriter(DIR_OF_MEMO + NAME_OF_MEMO));
					
					String str = view.memoArea.getText();
					out.write(str);  
					out.close();
					view.bottomInfo.setText(NAME_OF_MEMO
							+ model.SaveButMsg1);
				}
				else 
					view.bottomInfo.setText(model.SaveButMsg2);
			} catch (IOException e) {
				view.bottomInfo.setText(model.SaveButMsg3);
			}
			printCalDates();
		}					
	}

	public class ListenForMemoDeleteButton implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			view.memoArea.setText("");
			File f =new File(DIR_OF_MEMO + NAME_OF_MEMO);
			if(f.exists()){
				f.delete();
				printCalDates();
				view.bottomInfo.setText(model.DelButMsg1);
			}
			else 
				view.bottomInfo.setText(model.DelButMsg2);					
		}					
	}
	
	public class ListenForMemoClearButton implements ActionListener{
		
		public void actionPerformed(ActionEvent arg0) {
			view.memoArea.setText(null);
			view.bottomInfo.setText(model.ClrButMsg1);
			
		}
	}
	
}
