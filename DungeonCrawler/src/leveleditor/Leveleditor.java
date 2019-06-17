package leveleditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import engine.Scene;
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
	
	private double framerate = 60;
	
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
	
	private Scene sceneActive;
	
	public Leveleditor() {
		//create empty scene
		this.sceneActive = new Scene();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.addKeyListener(KeyRegister.getKeyRegister());		
		this.initJFrameStructure();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);	
		
		this.startLoopThread();
	}
	
	private void initJFrameStructure() {
		this.setLayout(new BorderLayout());
		
		
		//------PANEL OPTIONS		
		JPanel pOptions = new JPanel();
		pOptions.setPreferredSize(new Dimension(50, 0));
		pOptions.setBackground(Color.BLUE);
		this.add(pOptions, BorderLayout.WEST);
		
		
		JSplitPane splitMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitMain.setResizeWeight(0.80);
		splitMain.setContinuousLayout(true);

			//------PANEL SCENE
			dcScene = new LeveleditorScene(this);
			dcScene.setPreferredSize(new Dimension(500, 500));
			dcScene.setBackground(Color.GREEN);
			splitMain.setLeftComponent(dcScene);

			//------PANEL Object Chooser
			dcObjects = new LeveleditorObjectChooser();
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
				
				long time, duration, delay = 0;
				
				while(true) {
					time = System.nanoTime();
//					duration = System.nanoTime(); 
					
					processKeyInputs();
					
//					System.out.print(System.nanoTime() - duration + ", "); duration = System.nanoTime();
					
					//check if the user is hovering over anything
					dcScene.processMouseInteractions();
					
//					System.out.print(System.nanoTime() - duration + ", "); duration = System.nanoTime();
					
					dcObjects.processMouseHover();
					
//					System.out.print(System.nanoTime() - duration + ", "); duration = System.nanoTime();
					
					sceneActive.sortByRenderOrder();
					
//					System.out.print(System.nanoTime() - duration + ", "); duration = System.nanoTime();
					
					dcScene.repaint();
					dcObjects.repaint();
					
//					System.out.println(System.nanoTime() - duration + ", "); duration = System.nanoTime();
					
					//Delay to stabilize framerate
					duration = System.nanoTime() - time;
					if(duration <= (1000000000/framerate)) {
						delay = (long) (1000000000/framerate) - duration;
						try {
							Thread.sleep((delay/1000000), (int) (delay % 1000000));
						} catch(Exception e) {}
					}
					else {
					}
				}
			}
		}).start();
	}
	
	private void processKeyInputs(){
		if(KeyRegister.getKeyRegister().isKeyDown(KeyEvent.VK_W)) {
			this.dcScene.moveCamera(0, -1);
		} else if(KeyRegister.getKeyRegister().isKeyDown(KeyEvent.VK_S)){
			this.dcScene.moveCamera(0, 1);
		}
		
		if(KeyRegister.getKeyRegister().isKeyDown(KeyEvent.VK_A)) {
			this.dcScene.moveCamera(-1, 0);
		} else if(KeyRegister.getKeyRegister().isKeyDown(KeyEvent.VK_D)){
			this.dcScene.moveCamera(1, 0);
		}
		
		if(KeyRegister.getKeyRegister().isKeyDown(KeyEvent.VK_ESCAPE)) {
			this.dcObjects.setSelectedGameObject(null);
		}
	}
	
	////////////////////////
	//////////////////////// GETTERS
	////////////////////////
	
	public GameObject getSelectedObject() {
		return this.dcObjects.getSelectedObject();
	}
	
	public Scene getSceneActive() {
		return this.sceneActive;
	}
}
