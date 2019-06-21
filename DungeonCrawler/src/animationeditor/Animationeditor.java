package animationeditor;

import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import engine.animation.Animation;
import engine.window.KeyRegister;

public class Animationeditor extends JFrame {
	
	private HashMap<String, Animation> animationMapActive;
	
	public Animationeditor() {
		//create empty scene
		loadAnimationMap(new HashMap<String, Animation>(), false);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.addKeyListener(KeyRegister.getKeyRegister());		
		this.initJFrameStructure();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);	
	}
	
	private void initJFrameStructure() {
		//the first JSplitPane divides the AnimationMap and Animation area from the Spritesheet Display on the right
		JSplitPane splitMain = new JSplitPane();
		splitMain.addKeyListener(KeyRegister.getKeyRegister());
		
		this.add(splitMain);
		
	}
	
	////////////////////////
	//////////////////////// SETTERS
	////////////////////////
	
	private void loadAnimationMap(HashMap<String, Animation> animationMap, boolean nullIsOK) {		
		if(animationMap == null && !nullIsOK)
			return;
		this.animationMapActive = animationMap;
	}
}
