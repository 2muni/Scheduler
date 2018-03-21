package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import scheduler.Container;
import system.Properties;


public class CalendarView extends JPanel {
	
	CalendarControl control;
	Properties prop;
	
// -------------------- variable of subPanelWest -------------------- //
	
	JPanel calOpPanel;
	GridBagLayout calOpGBL;
		GridBagConstraints calOpGBC;
		JButton todayBut;
		JLabel todayLab;
		JButton lYearBut;
		JButton lMonBut;
		JLabel curYYYYMMLab;
		JButton nMonBut;
		JButton nYearBut;

	JPanel calPanel;
		JButton[] weekday;
		JButton[][] dateButs;
	
	CalendarControl.ListenForCalOpButtons calOpAction;
	CalendarControl.ListenForDateButtons dateAction;
	
	
// -------------------- variable of subPanelCenter -------------------- //
	
	JPanel infoPanel;
		JLabel infoClock;

	JPanel memoPanel;
		JLabel selectedDate;
		JTextArea memoArea;
		JScrollPane memoAreaSP;
		JPanel memoSubPanel;
		JButton saveBut; 
		JButton delBut; 
		JButton clearBut;

	CalendarControl.ListenForMemoSaveButton memoSaveAction;
	CalendarControl.ListenForMemoDeleteButton memoDeleteAction;
	CalendarControl.ListenForMemoClearButton memoClearAction;	
	
// -------------------- variable of subPanelSouth -------------------- //
	
	JPanel subPanelSouth;
		JLabel noticeLab;			

// ---------------------- variable setting end ---------------------- //
		
	public CalendarView(Container c) {
		prop = c.prop;
		control = new CalendarControl(this, c.calendarM);
		
		dateButs = new JButton[control.NUMBER_OF_ROWS][control.NUMBER_OF_COLS];
		
		calOpAction = control.new ListenForCalOpButtons();
		dateAction = control.new ListenForDateButtons();		
		memoSaveAction = control.new ListenForMemoSaveButton();
		memoDeleteAction = control.new ListenForMemoDeleteButton();
		memoClearAction = control.new ListenForMemoClearButton();
				
		JPanel subPanelWest = new JPanel();
			
			calOpPanel = new JPanel();
			calOpGBL = new GridBagLayout();
			calOpGBC = new GridBagConstraints();
			calOpPanel.setLayout(calOpGBL);
			
				addCalOpObjs();
				
			calPanel = new JPanel();
				addCalButs();
						
			Dimension calOpPanelSize = calOpPanel.getPreferredSize();
			calOpPanelSize.height = 90;
			calOpPanel.setPreferredSize(calOpPanelSize);
			
		subPanelWest.setLayout(new BorderLayout());
		subPanelWest.add(calOpPanel,BorderLayout.NORTH);
		subPanelWest.add(calPanel,BorderLayout.CENTER);
					
		JPanel subPanelCenter = new JPanel();
		
			infoPanel = new JPanel();
			infoPanel.setLayout(new BorderLayout());
				addInfoLabel();
				
			memoPanel=new JPanel();
			memoPanel.setBorder(BorderFactory.createTitledBorder("Memo"));
				addMemoObjs();
			
			Dimension infoPanelSize = infoPanel.getPreferredSize();
			infoPanelSize.height = 65;
			infoPanel.setPreferredSize(infoPanelSize);
			
		subPanelCenter.setLayout(new BorderLayout());
		subPanelCenter.add(infoPanel,BorderLayout.NORTH);
		subPanelCenter.add(memoPanel,BorderLayout.CENTER);
									
		subPanelSouth = new JPanel();
			addNoticeLab();
			
		//setPreferredSize of CalendarTabPane	
		Dimension subPanelWestSize = subPanelWest.getPreferredSize();
		subPanelWestSize.width = (int)(prop.getWidth() * 0.7);
		subPanelWest.setPreferredSize(subPanelWestSize);
		
		//add panels
		setLayout(new BorderLayout());
		setOpaque(true);
		add(subPanelWest, BorderLayout.WEST);
		add(subPanelCenter, BorderLayout.CENTER);
		add(subPanelSouth, BorderLayout.SOUTH);
		
		control.timeThread.start();
		
	}	
		
	
// -------------------- createPanels of subPanelWest -------------------- //	
		
	private void addGrid(GridBagLayout gbl, GridBagConstraints gbc, Component c, 
			int gridx, int gridy, int gridwidth, int gridheight) {

		calOpGBC.gridx = gridx;
		calOpGBC.gridy = gridy;			
		calOpGBC.gridwidth = gridwidth;
		calOpGBC.gridheight = gridheight;
		calOpGBC.weightx = 1;
		calOpGBC.weighty = 1;
		gbl.setConstraints(c, gbc);
		calOpPanel.add(c);
	}

	private void addCalOpObjs() {
			
		todayBut = new JButton("Today");
		todayBut.setToolTipText("Today");
		todayBut.addActionListener(calOpAction);
	
		todayLab = new JLabel(control.curYYYYMMDDLab);
		todayLab.setFont(prop.getFont16(false));
		
		lYearBut = new JButton("<<");
		lYearBut.setToolTipText("Previous Year");
		lYearBut.addActionListener(calOpAction);

		lMonBut = new JButton("<");
		lMonBut.setToolTipText("Previous Month");
		lMonBut.addActionListener(calOpAction);

		curYYYYMMLab = new JLabel(control.curYYYYMMLab);
		curYYYYMMLab.setFont(prop.getFont24(true));

		nMonBut = new JButton(">");
		nMonBut.setToolTipText("Next Month");
		nMonBut.addActionListener(calOpAction);

		nYearBut = new JButton(">>");
		nYearBut.setToolTipText("Next Year");
		nYearBut.addActionListener(calOpAction);
		

		calOpGBC.insets = new Insets(0,5,0,5);
		calOpGBC.fill = GridBagConstraints.NONE;

		calOpGBC.anchor = GridBagConstraints.WEST;
			addGrid(calOpGBL, calOpGBC, todayBut, 0, 0, 1, 1);
			addGrid(calOpGBL, calOpGBC, todayLab, 1, 0, 2, 1);	
		calOpGBC.anchor = GridBagConstraints.EAST;
			addGrid(calOpGBL, calOpGBC, lYearBut, 0, 1, 1, 1);
		calOpGBC.anchor = GridBagConstraints.CENTER;
			addGrid(calOpGBL, calOpGBC, lMonBut, 1, 1, 1, 1);		
			addGrid(calOpGBL, calOpGBC, curYYYYMMLab, 2, 1, 1, 1);			
			addGrid(calOpGBL, calOpGBC, nMonBut, 4, 1, 1, 1);
		calOpGBC.anchor = GridBagConstraints.WEST;
			addGrid(calOpGBL, calOpGBC, nYearBut, 5, 1, 1, 1);
	}
		
	private void addCalButs() {
		
		weekday = new JButton[control.NUMBER_OF_COLS];
		
		for(int col = 0; col < control.NUMBER_OF_COLS; col++)
			weekday[col]=new JButton();
			for(int row = 0; row < control.NUMBER_OF_ROWS; row++)
			for(int col = 0; col < control.NUMBER_OF_COLS; col++)
				dateButs[row][col] = new JButton();
				
			for(int col = 0; col < control.NUMBER_OF_COLS; col++){
				control.setWeekdays(col);
				weekday[col].setBorderPainted(false);
				weekday[col].setContentAreaFilled(false);
				weekday[col].setForeground(Color.WHITE);
				if(col == 0) 
					weekday[col].setBackground(new Color(200, 50, 50));
				else if (col == 6) 
					weekday[col].setBackground(new Color(50, 100, 200));
				else 
					weekday[col].setBackground(new Color(150, 150, 150));
				weekday[col].setFont(prop.getFont24(true));
				weekday[col].setOpaque(true);
				calPanel.add(weekday[col]);
			}
			for(int row = 0; row < control.NUMBER_OF_ROWS; row++){
				for(int col = 0; col < control.NUMBER_OF_COLS; col++){
					dateButs[row][col].setBorderPainted(false);
					dateButs[row][col].setContentAreaFilled(false);
					dateButs[row][col].setBackground(Color.WHITE);
					dateButs[row][col].setFont(prop.getFont24(false));
					dateButs[row][col].setOpaque(true);
					dateButs[row][col].addActionListener(dateAction);
					calPanel.add(dateButs[row][col]);
				}
			}
			calPanel.setLayout(new GridLayout(0,7,2,2));
			calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
			control.printCalDates(); 
	}		
		
// -------------------- createPanels of subPanelCenter -------------------- //
	
	private void addInfoLabel() {
	
			infoClock = new JLabel("", SwingConstants.RIGHT);
			infoClock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 50));
			infoClock.setFont(prop.getFont16(false));
			infoPanel.add(infoClock, BorderLayout.NORTH);
	}

	private void addMemoObjs() {

		selectedDate = new JLabel(control.selYYYYMMDDLab, 
			SwingConstants.LEFT);
			selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
				
		memoArea = new JTextArea();
			memoArea.setLineWrap(true);
			memoArea.setWrapStyleWord(true);
			memoAreaSP = new JScrollPane(memoArea, 
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			control.readMemo();
	
		memoSubPanel=new JPanel();
			saveBut = new JButton("Save"); 
			saveBut.addActionListener(memoSaveAction);
			delBut = new JButton("Delete");
			delBut.addActionListener(memoDeleteAction);
			clearBut = new JButton("Clear");
			clearBut.addActionListener(memoClearAction);
			
		memoSubPanel.add(saveBut);
		memoSubPanel.add(delBut);
		memoSubPanel.add(clearBut);
		memoPanel.setLayout(new BorderLayout());
		memoPanel.add(selectedDate, BorderLayout.NORTH);
		memoPanel.add(memoAreaSP,BorderLayout.CENTER);
		memoPanel.add(memoSubPanel,BorderLayout.SOUTH);
	}
	
	private void addNoticeLab() {
		noticeLab = new JLabel("메모파일은 본 파일이 위치한 디렉토리의 하위에 생성됩니다.");
		noticeLab.setFont(prop.getFont16(false));
		subPanelSouth.add(noticeLab);
	}
	
}
