package system;

import java.awt.Font;

public class Properties {
	private final int WIDTH = 1152;
	private final int HEIGHT = 648;
	private final String FONTNAME = "¸¼Àº °íµñ";
	
	public int getWidth() {		return WIDTH;	}
	public int getHeight() {		return HEIGHT;	}
	
	public Font getFont16(boolean isBOLD) {
		if(isBOLD)		return new Font(FONTNAME, Font.BOLD, 16);
		else		return new Font(FONTNAME, Font.PLAIN, 16);
	}
	public Font getFont24(boolean isBOLD) {
		if(isBOLD)		return new Font(FONTNAME, Font.BOLD, 24);
		else		return new Font(FONTNAME, Font.PLAIN, 24);
	}
}
