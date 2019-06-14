package leveleditor;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class LeveleditorDrawCompScene extends JComponent {
	public void paintComponent(Graphics g) {
		
		g.setColor(new Color(0f, 0f, 0.5f, 0.3f));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
	}
	
	public void processMouseHover() {
		
	}
}
