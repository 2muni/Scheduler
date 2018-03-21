package timetable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import system.DBConnection;

public class TimetableModel {
	
	
    private final String[] WEEKDAY = {"SETTING", "월요일", "화요일", "수요일", "목요일", "금요일"};
    private final String[] PERIODS = {"09:00", "10:00", "11:00", "12:00"
    		, "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};
    
    private String[][] tableData = new String[WEEKDAY.length-1][PERIODS.length];
	
    private DBConnection dbConn = new DBConnection();
    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
	
    private String[] DB_TIMETABLE_COLS = {"mon", "tue", "wed", "thu", "fri"};
    
	public TimetableModel() {
		setDate();
	}
	
	
	private void setDate() {
		
        try {
        	conn = dbConn.getConnection();
        	for(int col = 0; col < WEEKDAY.length-1; col++) {
        		
            String query = "SELECT t.time_id, l.lecture_name"
            		+ " FROM lectures l JOIN timetable t"
            		+ " ON (l.lecture_id = t." + DB_TIMETABLE_COLS[col] +")";
            
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();
            
            	while(rs.next())
            		tableData[col][rs.getInt("time_id")] = rs.getString("lecture_name");       	          	
        	}        	
        } catch (SQLException sqle) {       	
            System.out.println("SELECT문에서 예외 발생");
            sqle.printStackTrace();           
        } finally{       	
            try{
            	
                if (rs != null) {rs.close();}  
                if (pstm != null) {pstm.close();}  
                if (conn != null) {conn.close();}
            }catch(Exception e){
            	
                throw new RuntimeException(e.getMessage());
            }          
        }
	}
	
	public int NUMBER_OF_COLS() {		return WEEKDAY.length;	}	
	public int NUMBER_OF_ROWS() {		return PERIODS.length;	}
	public String WEEKDAY(int index) {		return WEEKDAY[index];	}
	public String PERIODS(int index) {		return PERIODS[index];	}
	public String getTableData(int col, int row) {		return tableData[col][row];	}
	
}
