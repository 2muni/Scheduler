package scheduler;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import calendar.CalendarView;
import system.Properties;
import timetable.TimetableView;

public class Scheduler extends JFrame {
	Properties prop;
	
	Container control;
	
	Scheduler(){
		
		control = new Container();
		
		try{
			UIManager.setLookAndFeel ("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this) ;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		prop = new Properties();
		
		setTitle("Scheduler");
        setSize(prop.getWidth(), prop.getHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JTabbedPane tab = new JTabbedPane();
        
        
        tab.setFont(prop.getFont16(false));
        
        TimetableView tab_1 = new TimetableView(control);
        CalendarView tab_2 = new CalendarView(control); 
        tab.addTab("시간표", tab_1);
        tab.addTab("달력", tab_2);
        
        add(tab);
		setVisible(true);
		setResizable(false);
		
		
		
	}

	public static void main(String[] args) {
		new Scheduler();
	}

}
