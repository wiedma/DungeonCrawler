package leveleditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import engine.Scene;
import engine.gameobjects.GameObject;
import engine.gameobjects.GameObject_Grass;
import engine.gameobjects.GameObject_Slime;
import engine.gameobjects.GameObject_Tree;
import engine.gameobjects.GameObject_TreeSmol;
import engine.io.FileLoader;
import engine.window.KeyRegister;

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
		loadScene(new Scene(), false);
		
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
		int pOptionsWidth = 200;
		int margin = 5;
		JPanel pOptions = new JPanel(null);
		pOptions.setPreferredSize(new Dimension(pOptionsWidth,0));
		pOptions.setBackground(new Color(0, 1, 0, 0.3f));
		
		
			//////////////LOAD SCENE
		
			JPanel pLoadScene = new JPanel(null);
			pLoadScene.setSize(pOptionsWidth, 50);
				JLabel lLoadScene = new JLabel("Load Scene:");
				lLoadScene.setBounds(margin, margin, pOptionsWidth-(2*margin), 10);
				pLoadScene.add(lLoadScene);
				
				int height = 23;
				String sceneLoadPathHead = "res/scenes/";
				JLabel lLoadScenePathInfo = new JLabel(sceneLoadPathHead);
				lLoadScenePathInfo.setBounds(lLoadScene.getX(), lLoadScene.getY()+lLoadScene.getHeight()+margin, 67, height);
				pLoadScene.add(lLoadScenePathInfo);
				
				JTextField txtLoadScene = new JTextField();
				txtLoadScene.setBounds(lLoadScenePathInfo.getX()+lLoadScenePathInfo.getWidth()+margin, lLoadScenePathInfo.getY(), pOptionsWidth-lLoadScenePathInfo.getX()-lLoadScenePathInfo.getWidth()-(2*margin), height);
				pLoadScene.add(txtLoadScene);
				
				int width = 70;
				JButton bLoadScene = new JButton("Load");
				bLoadScene.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						loadScene(FileLoader.readFromFile(sceneLoadPathHead + txtLoadScene.getText()), false);
						dcScene.resetCamera();
					}
				});
				bLoadScene.setBounds(pOptionsWidth-margin-width, txtLoadScene.getY()+txtLoadScene.getHeight()+margin, width, height);
				pLoadScene.add(bLoadScene);
			pLoadScene.setBounds(0, 0, pOptionsWidth, bLoadScene.getY()+bLoadScene.getHeight()+margin);
			pOptions.add(pLoadScene);
			
			
			//////////////SAVE SCENE
			
			JPanel pSaveScene = new JPanel(null);
			pSaveScene.setSize(pOptionsWidth, 50);
				JLabel lSaveScene = new JLabel("Save Scene:");
				lSaveScene.setBounds(margin, margin, pOptionsWidth-(2*margin), 10);
				pSaveScene.add(lSaveScene);
				
				height = 23;
				String sceneSavePathHead = "res/scenes/";
				JLabel lSaveScenePathInfo = new JLabel(sceneSavePathHead);
				lSaveScenePathInfo.setBounds(lSaveScene.getX(), lSaveScene.getY()+lSaveScene.getHeight()+margin, 67, height);
				pSaveScene.add(lSaveScenePathInfo);
				
				JTextField txtSaveScene = new JTextField();
				txtSaveScene.setBounds(lSaveScenePathInfo.getX()+lSaveScenePathInfo.getWidth()+margin, lSaveScenePathInfo.getY(), pOptionsWidth-lSaveScenePathInfo.getX()-lSaveScenePathInfo.getWidth()-(2*margin), height);
				pSaveScene.add(txtSaveScene);
				
				width = 70;
				JButton bSaveScene = new JButton("Save");
				bSaveScene.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						FileLoader.writeToFile(sceneActive, sceneSavePathHead + txtSaveScene.getText());
					}
				});
				bSaveScene.setBounds(pOptionsWidth-margin-width, txtSaveScene.getY()+txtSaveScene.getHeight()+margin, width, height);
				pSaveScene.add(bSaveScene);
			pSaveScene.setBounds(0, pLoadScene.getY()+pLoadScene.getHeight(), pOptionsWidth, bSaveScene.getY()+bSaveScene.getHeight()+margin);
			pOptions.add(pSaveScene);
			
			
		this.add(pOptions, BorderLayout.WEST);
		
		
		JSplitPane splitMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitMain.setResizeWeight(0.80);
		splitMain.setFocusable(true);
		splitMain.addKeyListener(KeyRegister.getKeyRegister());
		splitMain.setContinuousLayout(true);

			//------PANEL SCENE
			dcScene = new LeveleditorScene(this);
			dcScene.setPreferredSize(new Dimension(500, 500));
			dcScene.setBackground(Color.GREEN);
			dcScene.setFocusable(true);
			dcScene.addKeyListener(KeyRegister.getKeyRegister());
			splitMain.setLeftComponent(dcScene);

			//------PANEL Object Chooser
				dcObjects = new LeveleditorObjectChooser();
				dcObjects.setPreferredSize(new Dimension(160, 0));
				this.fillObjectChooser(dcObjects);				
			JScrollPane scrollObjectChooser = new JScrollPane(dcObjects);
			scrollObjectChooser.getVerticalScrollBar().setUnitIncrement(20);
			splitMain.setRightComponent(scrollObjectChooser);

		this.add(splitMain, BorderLayout.CENTER);
		
	}
	
	/**
	 * called during initialization of {@link LeveleditorObjectChooser}<br>
	 * all GameObjects designated to be in the GameObjectChooser Panel should be added here
	 */
	private void fillObjectChooser(LeveleditorObjectChooser dcObjects) {
//		dcObjects.addGameObjects(new GameObject[] {
//				new JUnitTestGameObject(1),
//				new JUnitTestDynamicGameObject(0,0),
//				new JUnitTestTriggerGameObject(0,0),
//				new JUnitTestObstacle(0,0)
//		});
		dcObjects.addGameObjects(new GameObject[] {
				new GameObject_Tree(5, 5),
				new GameObject_TreeSmol(),
				new GameObject_Slime(),
				new GameObject_Grass()
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
	//////////////////////// SETTERS
	////////////////////////
	
	public void loadScene(Scene scene, boolean nullIsOK) {
		if(scene == null && !nullIsOK)
			return;
		this.sceneActive = scene;
		this.setTitle(scene.getInitialFilePath());
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
