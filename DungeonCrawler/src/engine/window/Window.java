package engine.window;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	private DrawComp drawComp;
	
	public Window() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		drawComp = new DrawComp();
		drawComp.setPreferredSize(new Dimension(500,500));
		this.add(drawComp);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
