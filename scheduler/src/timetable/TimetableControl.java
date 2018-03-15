package timetable;

import system.Properties;

public class TimetableControl {
	
	Properties prop;
	TimetableView view;
	TimetableModel model;
	
	final int NUMBER_OF_COLS;
	final int NUMBER_OF_ROWS;
	
	TimetableControl(TimetableView view){
		prop = new Properties();
		this.view = view;
		model = new TimetableModel();
		
		NUMBER_OF_COLS = model.NUMBER_OF_COLS();
		NUMBER_OF_ROWS = model.NUMBER_OF_ROWS();
				
	}
	
	public void setWeekday(int col) {
		view.weekday[col].setText(model.WEEKDAY(col));
	}
	
	public void setPeriods(int row) {
		view.periods[row].setText(model.PERIODS(row));
	}
	
	public void setTable(int row, int col) {
		view.tableButs[col][row].setText(model.getTableData(col, row));

	}
}
