package scheduler;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import calendar.CalendarView;
import system.Properties;
import timetable.TimetableView;
public class Scheduler extends JFrame {
	Properties prop;
	
	Scheduler(){
		prop = new Properties();
		
		setTitle("Scheduler");
        setSize(prop.getWidth(), prop.getHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JTabbedPane tab = new JTabbedPane();
        tab.setFont(new Font("¸¼Àº °íµñ", Font.BOLD,16));
        
        
        TimetableView tab_1 = new TimetableView();
        CalendarView tab_2 = new CalendarView(); 
        tab.addTab("½Ã°£Ç¥", tab_1);
        tab.addTab("´Þ·Â", tab_2);
        
        add(tab);
		setVisible(true);
		setResizable(false);
	}

	public static void main(String[] args) {
		new Scheduler();
	}

}
