package engine.gui.elements;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.gui.GUIElementInteractive;

public class GUIElement_TextBox extends GUIElementInteractive {

	private String text;
	
	public GUIElement_TextBox(String text) {
		this.text = text;
	}
	
	public void display(Graphics2D g, int screenWidth, int screenHeight) {
		int marginLeftPx = (int) (screenWidth * 0.1);
		int marginTopPx = (int) (screenHeight * 0.6);
		int widthPx = screenWidth - (2*marginLeftPx);
		int heightPx = (int) (screenHeight*0.9) - marginTopPx;
		
		int paddingLeftPx = 10;
		int paddingTopPx = 10;
		
		g.setColor(Color.white);
		g.fillRect(marginLeftPx, marginTopPx, widthPx, heightPx);
		
		
		g.setColor(Color.black);
		g.drawString(this.text, marginLeftPx + paddingLeftPx, marginTopPx + paddingTopPx + g.getFontMetrics().getFont().getSize());
	}

}
