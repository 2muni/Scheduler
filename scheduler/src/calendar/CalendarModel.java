package calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarModel {
	
	final String[] WEEK_DAY_NAME = {"일","월","화","수","목","금","토"};
	final String SaveButMsg1 = "를 MemoData폴더에 저장하였습니다.";
	final String SaveButMsg2 = "메모를 먼저 작성해 주세요.";
	final String SaveButMsg3 = "ERROR : 파일 쓰기 실패";
	final String DelButMsg1 = "메모를 삭제하였습니다.";
	final String DelButMsg2 = "작성되지 않았거나 이미 삭제된 memo입니다.";
	final String DelButMsg3 = "ERROR : 파일 삭제 실패";
	final String ClrButMsg1 = "입력된 메모를 비웠습니다.";
	
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
		// 1일의 위치와 마지막 날짜를 구함 
		int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK) + 7 
				- (cal.get(Calendar.DAY_OF_MONTH)) % 7) % 7;
		if(calMonth == 1) 
			calLastDate = CAL_LAST_DATE_OF_MONTH[calMonth] 
					+ leapCheck(calYear);
		else calLastDate = CAL_LAST_DATE_OF_MONTH[calMonth];
		// 달력 배열 초기화
		for(int row = 0; row < NUMBER_OF_ROWS; row++){
			for(int col = 0; col < NUMBER_OF_COLS; col++){
				calDates[row][col] = 0;
			}
		}
		// 달력 배열에 값 채워넣기
		for(int row = 0, date = 1, startingPos = 0; row < NUMBER_OF_ROWS; row++){
			if(row == 0) startingPos = calStartingPos;
			else startingPos = 0;
			for(int col = startingPos; col < NUMBER_OF_COLS; col++){
				if(date <= calLastDate) calDates[row][col] = date++;
			}
		}
	}
    
	private int leapCheck(int year){ // 윤년인지 확인하는 함수
		if(year%4 == 0 && year%100 != 0 || year%400 == 0) return 1;
		else return 0;
	}
		
	public void moveMonth(int month){ // 현재달로 부터 n달 전후를 받아 달력 배열을 만드는 함수(1년은 +12, -12달로 이동 가능)
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
