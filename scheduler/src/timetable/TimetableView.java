package timetable;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimetableView extends JPanel{
	TimetableControl control;
	
	public JLabel[] weekday;
	public JLabel[] periods;
	public JButton[][] tableButs;
	
	public TimetableView(){
		
		control = new TimetableControl(this);
		
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
			if(col == 0)
				weekday[col].setVisible(false);
			control.setWeekday(col);
			weekday[col].setHorizontalAlignment(JLabel.CENTER);
			weekday[col].setFont(new Font("¸¼Àº °íµñ", Font.BOLD,24));
			weekday[col].setBackground(Color.WHITE);
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
					periods[row].setFont(new Font("¸¼Àº °íµñ", Font.BOLD,24));
					periods[row].setBackground(Color.WHITE);
					periods[row].setOpaque(true);
					add(periods[row]);
				}
				control.setTable(row, col);
				tableButs[col][row].setFont(new Font("¸¼Àº °íµñ", Font.PLAIN,20));
				tableButs[col][row].setBorderPainted(false);
				tableButs[col][row].setContentAreaFilled(false);
				tableButs[col][row].setBackground(Color.WHITE);
				tableButs[col][row].setOpaque(true);
				add(tableButs[col][row]);
			}
		}
	}
}
