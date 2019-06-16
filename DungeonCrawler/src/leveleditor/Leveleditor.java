package leveleditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import engine.gameobjects.GameObject;
import engine.window.KeyRegister;
import junittests.JUnitTestGameObject;

public class Leveleditor extends JFrame {
	
//	/**
//	 * Singleton of the Leveleditor object. Will only be created if {@link Leveleditor#getLeveleditor() getLeveleditor()} is called
//	 */
//	private static Leveleditor leveleditor;
//	public synchronized static Leveleditor getLeveleditor() {
//		if(Leveleditor.leveleditor == null)
//			Leveleditor.leveleditor = new Leveleditor();
//		return Leveleditor.leveleditor;
//	}
//	
//	
//	
	
	
	
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -3128924728020046714L;

	public static void main(String[] args) {
//		getLeveleditor();
		new Leveleditor();
	}
	
	
	/**
	 * <p>The DrawComp that will draw the Scene in its current state</p>
	 */
	private LeveleditorScene dcScene;
	/**
	 * <p>The DrawComp that enable the user to choose from a pallete of different GameObjects to place in the Scene.
	 * To add GameObjects to this list see {@link Leveleditor#fillObjectChooser(LeveleditorObjectChooser) fillObjectChooser(..)} for further reference</p>
	 */
	private LeveleditorObjectChooser dcObjects;
	
	public Leveleditor() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.addKeyListener(KeyRegister.getKeyRegister());
		
		
		this.initJFrameStructure();
		
		
		this.startLoopThread();
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void initJFrameStructure() {
		this.setLayout(new BorderLayout());
		
		
		//------PANEL OPTIONS		
		JPanel pOptions = new JPanel();
		pOptions.setPreferredSize(new Dimension(50, 0));
		pOptions.setBackground(Color.BLUE);
		this.add(pOptions, BorderLayout.WEST);
		
		
		JSplitPane splitMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitMain.setContinuousLayout(true);

			//------PANEL SCENE
			dcScene = new LeveleditorScene(this);
			dcScene.setPreferredSize(new Dimension(500, 500));
			dcScene.setBackground(Color.GREEN);
			splitMain.setLeftComponent(dcScene);

			//------PANEL Object Chooser
			dcObjects = new LeveleditorObjectChooser(5);
			dcObjects.setPreferredSize(new Dimension(160, 0));
			this.fillObjectChooser(dcObjects);
			splitMain.setRightComponent(dcObjects);			

		this.add(splitMain, BorderLayout.CENTER);
		
	}
	
	/**
	 * called during initialization of {@link LeveleditorObjectChooser}<br>
	 * all GameObjects designated to be in the GameObjectChooser Panel should be added here
	 */
	private void fillObjectChooser(LeveleditorObjectChooser dcObjects) {
		dcObjects.addGameObjects(new GameObject[] {
				new JUnitTestGameObject(0),
				new JUnitTestGameObject(0),
				new JUnitTestGameObject(0),
				new JUnitTestGameObject(0),
				new JUnitTestGameObject(0),
				new JUnitTestGameObject(0)
		});
	}
	
	/**
	 * starts the Leveleditos 'Main-Loop' asynchronously
	 */
	private void startLoopThread() {
		(new Thread() {
			public void run() {
				while(true) {
					
					//check if the user is hovering over anything
					dcScene.processMouseHover();
					dcObjects.processMouseHover();
					
					dcScene.repaint();
					dcObjects.repaint();
					
					try {
						Thread.sleep(16);
					}catch(Exception e) {}
				}
			}
		}).start();
	}
	
	////////////////////////
	//////////////////////// GETTERS
	////////////////////////
	
	public GameObject getSelectedObject() {
		return this.dcObjects.getSelectedObject();
	}
}
