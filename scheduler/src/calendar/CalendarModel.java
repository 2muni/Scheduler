package calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarModel {
	
	final String[] WEEK_DAY_NAME = {"��","��","ȭ","��","��","��","��"};
	final String SaveButMsg1 = "�� MemoData������ �����Ͽ����ϴ�.";
	final String SaveButMsg2 = "�޸� ���� �ۼ��� �ּ���.";
	final String SaveButMsg3 = "ERROR : ���� ���� ����";
	final String DelButMsg1 = "�޸� �����Ͽ����ϴ�.";
	final String DelButMsg2 = "�ۼ����� �ʾҰų� �̹� ������ memo�Դϴ�.";
	final String DelButMsg3 = "ERROR : ���� ���� ����";
	final String ClrButMsg1 = "�Էµ� �޸� ������ϴ�.";
	
	final int NUMBER_OF_COLS = 7;
	final int NUMBER_OF_ROWS = 6;
	final int CAL_LAST_DATE_OF_MONTH[] = { 31, 28, 31, 30, 
										   31, 30, 31, 31, 
										   30, 31, 30, 31 };
		
	int calDates[][] = new int[NUMBER_OF_ROWS][NUMBER_OF_COLS];
	int calYear;
	int calMonth;
	int calDayOfMon;
	int calLastDate;
	
	Calendar today = Calendar.getInstance();
	Calendar cal = Calendar.getInstance();
    	
    public CalendarModel(){
    	setToday();
    }
    
    public int NUMBER_OF_COLS() {    	return NUMBER_OF_COLS;    }
    public int NUMBER_OF_ROWS() {    	return NUMBER_OF_ROWS;    }
    
    public void setToday(){
		calYear = today.get(Calendar.YEAR); 
		calMonth = today.get(Calendar.MONTH);
		calDayOfMon = today.get(Calendar.DAY_OF_MONTH);
		inputCalData(today);
	}
    
    private void inputCalData(Calendar cal){
		// 1���� ��ġ�� ������ ��¥�� ���� 
		int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK) + 7 
				- (cal.get(Calendar.DAY_OF_MONTH)) % 7) % 7;
		if(calMonth == 1) 
			calLastDate = CAL_LAST_DATE_OF_MONTH[calMonth] 
					+ leapCheck(calYear);
		else calLastDate = CAL_LAST_DATE_OF_MONTH[calMonth];
		// �޷� �迭 �ʱ�ȭ
		for(int row = 0; row < NUMBER_OF_ROWS; row++){
			for(int col = 0; col < NUMBER_OF_COLS; col++){
				calDates[row][col] = 0;
			}
		}
		// �޷� �迭�� �� ä���ֱ�
		for(int row = 0, date = 1, startingPos = 0; row < NUMBER_OF_ROWS; row++){
			if(row == 0) startingPos = calStartingPos;
			else startingPos = 0;
			for(int col = startingPos; col < NUMBER_OF_COLS; col++){
				if(date <= calLastDate) calDates[row][col] = date++;
			}
		}
	}
    
	private int leapCheck(int year){ // �������� Ȯ���ϴ� �Լ�
		if(year%4 == 0 && year%100 != 0 || year%400 == 0) return 1;
		else return 0;
	}
		
	public void moveMonth(int month){ // ����޷� ���� n�� ���ĸ� �޾� �޷� �迭�� ����� �Լ�(1���� +12, -12�޷� �̵� ����)
		calMonth += month;
		if(calMonth > 11) {
				calYear++;
				calMonth -= 12;
		}else if (calMonth<0) {
			calYear--;
			calMonth += 12;
		}
		cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);
		inputCalData(cal);
	}
	
	
}
