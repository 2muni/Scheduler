package timetable;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import scheduler.Container;
import system.Properties;

public class TimetableView extends JPanel{
	TimetableControl control;
	Properties prop;
	public JLabel[] weekday;
	public JLabel[] periods;
	public JButton[][] tableButs;
	public JButton settingBut;
	
	public TimetableView(Container c){
		prop = c.prop;
		
		control = new TimetableControl(this, c.timetableM);
		
		weekday = new JLabel[control.NUMBER_OF_COLS];
			for(int col = 0; col < control.NUMBER_OF_COLS; col++)
				weekday[col] = new JLabel();
		periods = new JLabel[control.NUMBER_OF_ROWS];
			for(int row = 0; row < control.NUMBER_OF_ROWS; row++)
				periods[row] = new JLabel();
		tableButs = new JButton[control.NUMBER_OF_COLS-1][control.NUMBER_OF_ROWS];
			for(int row = 0; row < control.NUMBER_OF_ROWS; row++) 
				for(int col = 0; col < control.NUMBER_OF_COLS-1; col++)
					tableButs[col][row] = new JButton();
		
		setLayout(new GridLayout(11, 5, 1, 1));
		
		addWeekdayObjs();
		addTableObjs();
					
	}
	
	private void addWeekdayObjs() {

		for(int col = 0; col < control.NUMBER_OF_COLS; col++) {
			control.setWeekday(col);
			if(col == 0) 
				weekday[col].setVisible(false);
			weekday[col].setHorizontalAlignment(JLabel.CENTER);
			weekday[col].setFont(prop.getFont24(false));
			weekday[col].setOpaque(true);
			add(weekday[col]);
		}
	}
	
	private void addTableObjs() {

		for(int row = 0; row < control.NUMBER_OF_ROWS; row++) {
			for(int col = 0; col < control.NUMBER_OF_COLS-1; col++) {
				if(col == 0) {
					control.setPeriods(row);
					periods[row].setHorizontalAlignment(JLabel.CENTER);
					periods[row].setFont(prop.getFont24(false));
					periods[row].setOpaque(true);
					add(periods[row]);
				}
				control.setTable(row, col);
				tableButs[col][row].setFont(prop.getFont16(false));
				tableButs[col][row].setBorderPainted(false);
				tableButs[col][row].setContentAreaFilled(false);
				tableButs[col][row].setBackground(Color.WHITE);
				tableButs[col][row].setOpaque(true);
				add(tableButs[col][row]);
			}
		}
	}
}
