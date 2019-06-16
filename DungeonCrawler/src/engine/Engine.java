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
				
				while(true) {
					
					// --MAIN LOOP
					
					paused = GUIManager.shouldPause();
					if(!paused) {
						for(GameObject gameObject : sceneActive.getGameObjects()) {
							gameObject.update();
						}
					}
					for(GameObject gameObject : sceneActive.getGameObjects()) {
						gameObject.animationStep(paused, deltaTime);
					}
					
					sceneActive.sortByRenderOrder();
					
					window.repaint();
					
					//TODO delay and set deltaTime
					
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
