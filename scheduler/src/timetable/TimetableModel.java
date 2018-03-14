package timetable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import system.DBConnection;

public class TimetableModel {
	
	
    private final String[] WEEKDAY = {"", "������", "ȭ����", "������", "�����", "�ݿ���"};
    private final String[] PERIODS = {"1����", "2����", "3����", "4����", "5����", 
    		"6����", "7����", "8����", "9����", "10����"};
    
    private String[][] tableData = new String[WEEKDAY.length-1][PERIODS.length];
	
    private DBConnection dbConn = new DBConnection();
    private Connection conn = null;
    private PreparedStatement pstm = null;
    private ResultSet rs = null;
	
	TimetableModel() {
		setDate();
	}
	
	
	private void setDate() {
		
        try {
        	conn = dbConn.getConnection();
        	for(int col = 0; col < WEEKDAY.length-1; col++) {
        		
            String quary = "SELECT period, sname"
            		+ " FROM timetable"
            		+ " JOIN lectures USING(sbjno)" 
            		+ " WHERE time BETWEEN " 
            		+ (1000*(col+1)) + " AND " + (1000*(col+1)+1000)
            		+ " ORDER BY period";
            
            pstm = conn.prepareStatement(quary);
            rs = pstm.executeQuery();
            
            	while(rs.next())
            		tableData[col][rs.getInt("period")-1] = rs.getString("sname");       	          	
        	}        	
        } catch (SQLException sqle) {       	
            System.out.println("SELECT������ ���� �߻�");
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
