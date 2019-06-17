package engine;

import engine.gameobjects.GameObject;
import engine.gui.GUIManager;
import engine.window.Window;

/**
 * The main engine for the game. Incluedes game-loop and basic functionalities
 * @author Marco, Daniel
 *
 */
public class Engine {
	/**
	 * real time since last frame-update
	 */
	private static double deltaTime;
	
	/**
	 * Scene that is being displayed
	 */
	private static Scene sceneActive = new Scene();
	
	/**
	 * Targeted frames per second
	 */
	private static double framerate = 60;
	
	
	private static Window window;
	
	public static void init() {
		window = new Window();
		
		startMainLoop();
	}
	
	/**
	 * Starts the game on call
	 */
	private static void startMainLoop() {
		(new Thread() {
			public void run() {
				boolean paused;
				long time, duration, delay = 0;
				
				while(true) {
					
					// --MAIN LOOP
					//Get time
					time = System.nanoTime();
					//Get pause
					paused = GUIManager.shouldPause();
					//Call update if not paused
					if(!paused) {
						for(GameObject gameObject : sceneActive.getGameObjects()) {
							gameObject.update();
						}
					}
					//Animate GameObjects
					for(GameObject gameObject : sceneActive.getGameObjects()) {
						gameObject.animationStep(paused, deltaTime);
					}
					
					//Render the scene
					sceneActive.sortByRenderOrder();
					
					window.repaint();
					
					//Delay to stabilize framerate
					duration = System.nanoTime() - time;
					if(duration <= (1000000000/framerate)) {
						delay = (long) (1000000000/framerate) - duration;
						try {
							Thread.sleep((delay/1000000), (int) (delay % 1000000));
						} catch(Exception e) {}
						deltaTime = 1/framerate;
					}
					else {
						deltaTime = (duration/1000000000d);
					}
					
					// --
				}				
			}
		}).start();
	}
	
	/**
	 * loads a new scene
	 * @param newScene the scene to be loaded
	 */
	public static void loadScene(Scene newScene) {
		sceneActive = newScene;
		for(GameObject gameObject : sceneActive.getGameObjects()) {
			gameObject.start();
		}
	}
	
	/**
	 * Gives time since last frame-update
	 * @return time since last frame-update
	 */
	public static double deltaTime() {
		return deltaTime;
	}
	
	public static Scene getSceneActive() {
		return sceneActive;
	}
}
