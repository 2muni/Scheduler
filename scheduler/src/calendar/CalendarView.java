package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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


public class CalendarView extends JPanel {
	
	CalendarControl control;	
	
// -------------------- variable of subPanelWest -------------------- //
	
	JPanel calOpPanel;
	GridBagConstraints calOpGC;
		JButton todayBut;
		JLabel todayLab;
		JButton lYearBut;
		JButton lMonBut;
		JLabel curYYYYMMLab;
		JButton nMonBut;
		JButton nYearBut;

	JPanel calPanel;
		JButton[] weekday;
		JButton[][] dateButs = new JButton[6][7];
	
	CalendarControl.ListenForCalOpButtons calOpAction;
	CalendarControl.ListenForDateButtons dateAction;

// ------------------------------------------------------------------- //	
	
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
// ------------------------------------------------------------------- //	
	
// -------------------- variable of subPanelSouth -------------------- //
	
	JPanel noticePanel;
		JLabel bottomInfo;		
// ----------------------------- end ---------------------------------- //	
		
	public CalendarView() {

		
		control = new CalendarControl(this);
		
		calOpAction = control.new ListenForCalOpButtons();
		dateAction = control.new ListenForDateButtons();		
		memoSaveAction = control.new ListenForMemoSaveButton();
		memoDeleteAction = control.new ListenForMemoDeleteButton();
		memoClearAction = control.new ListenForMemoClearButton();
				
		JPanel subPanelWest = new JPanel();
			
			calOpPanel = new JPanel();
			calOpPanel.setLayout(new GridBagLayout());
			calOpGC = new GridBagConstraints();
			
				addCalOpButs();
				
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
									
		noticePanel = new JPanel();
			bottomInfo = new JLabel("Welcome to Memo Calendar!");
			noticePanel.add(bottomInfo);
			
		//setPreferredSize of CalendarTabPane	
		Dimension subPanelWestSize = subPanelWest.getPreferredSize();
		subPanelWestSize.width = 800;
		subPanelWest.setPreferredSize(subPanelWestSize);
		
		//add panels
		setLayout(new BorderLayout());
		setOpaque(true);
		add(subPanelWest, BorderLayout.WEST);
		add(subPanelCenter, BorderLayout.CENTER);
		add(noticePanel, BorderLayout.SOUTH);
	}	
		
	
// -------------------- createPanels of subPanelWest -------------------- //	
		private void setGCProperties(int width, int heigh, int x, int y) {
			calOpGC.weightx = 1;
			calOpGC.weighty = 1;
			calOpGC.insets = new Insets(5,5,0,0);
			calOpGC.fill = GridBagConstraints.NONE;
			
			calOpGC.gridwidth = width;
			calOpGC.gridheight = heigh;
			calOpGC.gridx = x;
			calOpGC.gridy = y;			
		}
	
		private void addCalOpButs() {
			
			todayBut = new JButton("Today");
			todayBut.setToolTipText("Today");
			todayBut.addActionListener(calOpAction);
		
			todayLab = new JLabel(control.curYYYYMMDDLab);
	
			lYearBut = new JButton("<<");
			lYearBut.setToolTipText("Previous Year");
			lYearBut.addActionListener(calOpAction);
	
			lMonBut = new JButton("<");
			lMonBut.setToolTipText("Previous Month");
			lMonBut.addActionListener(calOpAction);
	
			curYYYYMMLab = new JLabel(control.curYYYYMMLab);
	
			nMonBut = new JButton(">");
			nMonBut.setToolTipText("Next Month");
			nMonBut.addActionListener(calOpAction);
	
			nYearBut = new JButton(">>");
			nYearBut.setToolTipText("Next Year");
			nYearBut.addActionListener(calOpAction);
			
			calOpGC.anchor = GridBagConstraints.WEST;				
				setGCProperties(2, 1, 1, 1);				
				calOpPanel.add(todayBut,calOpGC);				
				setGCProperties(3, 1, 2, 1);
				calOpPanel.add(todayLab,calOpGC);

			calOpGC.anchor = GridBagConstraints.EAST;
				setGCProperties(1, 1, 1, 2);
				calOpPanel.add(lYearBut,calOpGC);
				setGCProperties(1, 1, 2, 2);
				calOpPanel.add(lMonBut,calOpGC);
				
			calOpGC.anchor = GridBagConstraints.CENTER;
				setGCProperties(2, 1, 3, 2);
				calOpPanel.add(curYYYYMMLab,calOpGC);
			
			calOpGC.anchor = GridBagConstraints.WEST;
				setGCProperties(1, 1, 5, 2);
				calOpPanel.add(nMonBut,calOpGC);
				setGCProperties(1, 1, 6, 2);
				calOpPanel.add(nYearBut,calOpGC);
		}
		
		public void addCalButs() {
			
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
					weekday[col].setFont(new Font("¸¼Àº °íµñ", Font.BOLD,24));
					weekday[col].setOpaque(true);
					calPanel.add(weekday[col]);
				}
				for(int row = 0; row < control.NUMBER_OF_ROWS; row++){
					for(int col = 0; col < control.NUMBER_OF_COLS; col++){
						dateButs[row][col].setBorderPainted(false);
						dateButs[row][col].setContentAreaFilled(false);
						dateButs[row][col].setBackground(Color.WHITE);
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
		
		public void addInfoLabel() {
		
				infoClock = new JLabel("", SwingConstants.RIGHT);
				infoClock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				infoPanel.add(infoClock, BorderLayout.NORTH);
				selectedDate = new JLabel(control.selYYYYMMDDLab, 
						SwingConstants.LEFT);
				selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		}
	
		public void addMemoObjs() {
					
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
	
}
