package scheduler;

import calendar.CalendarModel;
import system.Properties;
import timetable.TimetableModel;

public class Container {

	public CalendarModel calendarM;
	public TimetableModel timetableM;
	public Properties prop;
	
	Container(){
		prop = new Properties();
		calendarM = new CalendarModel();
		timetableM = new TimetableModel();		
	}
	
}
