package engine.gameobjects;

import engine.animation.Animator;
import engine.hitbox.Hitbox;
/**
 * Basic superclass for all Objects in a scene. Every object within this game must be a subclass of GameObject
 * @author Marco, Daniel
 *
 */
abstract public class GameObject {
	
	/**
	 * The Hitbox of this GameObject
	 */
	private Hitbox hitbox; //TODO
	
	/**
	 * x- and y-position of this GameObject within the scene
	 */
	private double x, y; //TODO
	
	/**
	 * The animator of this GameObject
	 */
	private Animator animator; //TODO
	
	
	/**
	 * Z-position for rendering. Objects are rendered in ascending z-position order.
	 */
	private double zPositionOffset = 0.5D;	//TODO use in sorting
	
	/**
	 * Initialized this GameObject
	 */
	public abstract void start();
	
	/**
	 * Gets called every frame
	 */
	public abstract void update();	
	
	/**
	 * Updates the animation of this GameObject
	 * @param gamePaused true if game is currently paused
	 * @param deltaTime time since last frame update
	 */
	public void animationStep(boolean gamePaused, double deltaTime) {
		animator.update(gamePaused, deltaTime);
	}
}
