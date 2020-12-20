package engine;

import engine.gui.GUIManager;
import engine.window.Window;
import gameobjects.DynamicGameObject;
import gameobjects.GameObject;

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
							if(gameObject instanceof DynamicGameObject) {
								move((DynamicGameObject)gameObject);
							}
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
	
	private static void move(DynamicGameObject obj) {
		double xSpeed = obj.getSpeedX();
		double ySpeed = obj.getSpeedY();
		if(xSpeed == 0 && ySpeed == 0) {
			return;
		}
		double oldX = obj.getX();
		double oldY = obj.getY();
		
		double newX = oldX + xSpeed * deltaTime;
		double newY = oldY + ySpeed * deltaTime;
		obj.setX(newX);
		obj.setY(newY);
		//Try to move object to its new position
		boolean collision = false;
		for(GameObject other : sceneActive.getGameObjects()) {
			//TODO: Avoid triggers, if GameObject doesn't actually move there
			if(obj.getHitbox() == null || other.getHitbox() == null)
				continue;
			if(obj != other && obj.getHitbox().collidesWith(other.getHitbox())) {
				collision = true;
			}
		}
		//If it collides there, don't move there
		//TODO: Move as close as possible to target destination
		if(collision) {
			obj.setX(oldX);
			obj.setY(oldY);
		}
	}
}
