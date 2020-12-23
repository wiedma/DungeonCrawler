package engine.window;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -1918528334655033909L;
	private DrawComp drawComp;
	
	public Window() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		drawComp = new DrawComp();
		drawComp.setPreferredSize(new Dimension(500,500));
		this.add(drawComp);
		
		this.addKeyListener(KeyRegister.getKeyRegister());
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
