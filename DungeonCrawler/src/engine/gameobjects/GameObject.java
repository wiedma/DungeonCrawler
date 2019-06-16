package engine.gameobjects;

import java.awt.Image;
import java.util.HashMap;

import engine.animation.Animation;
import engine.hitbox.Hitbox;
import engine.sprites.Sprite;
/**
 * Basic superclass for all Objects in a scene. Every object within this game must be a subclass of GameObject
 * @author Marco, Daniel
 *
 */
abstract public class GameObject implements Cloneable {
	
	/**
	 * The Hitbox of this GameObject
	 */
	private Hitbox hitbox;
	
	/**
	 * x- and y-position of this GameObject within the scene
	 */
	private double x, y;
	
	/**
	 * Z-position for rendering. Objects are rendered in ascending z-position order. Will be added to Y Coordinate of this GameObject to get its absolute Z Value
	 */
	protected double zPositionOffset = 0.5D;
	
	/**
	 * Hashmap contains all animations for this GameObject
	 */
	protected HashMap<String, Animation> animations;
	
	/**
	 * The animation which is currently displayed
	 */
	protected Animation currentAnimation;
	
	/**
	 * Time until the next sprite is to be displayed
	 */
	protected double timeUntilNextSprite;
	
	/**
	 * The Index of the current Sprite in the current Animation
	 */
	protected int currentSpriteIndex = 0;
	
	public GameObject(double x, double y, Hitbox hitbox, Animation startAnimation, HashMap<String, Animation> animations) {
		this.x = x;
		this.y = y;
		this.hitbox = hitbox;
		this.animations = animations;
		this.currentAnimation = startAnimation;
	}
	
	/**
	 * Initialized this GameObject
	 */
	public abstract void start();
	
	/**
	 * Gets called every frame
	 */
	public abstract void update();	
	
	/**
	 * Checks if the sprite is to be changed and does so
	 * @param gamePaused true, if game is currently paused
	 * @param deltaTime time since last frame update
	 */
	public void animationStep(boolean gamePaused, double deltaTime) {
		if(currentAnimation == null) {
			return;
		}
		if(gamePaused && currentAnimation.canBePaused()) {
			return;
		}
		//Update counter
		timeUntilNextSprite = timeUntilNextSprite - deltaTime;
		//If the counter reaches zero
		if(timeUntilNextSprite <= 0) {
			//new counter = old counter + delay
			timeUntilNextSprite += currentAnimation.getDelayBetweenSprites();
			
			//update animation, if it finished, check if the Animator should switch to another Animation
			if(nextSprite() && currentAnimation.shouldSwitchToOtherAnimationOnFinish()) {
				//switch Animation
				if(animations.containsKey(currentAnimation.getAnimationAfterThis())){
					currentAnimation = animations.get(currentAnimation.getAnimationAfterThis());
				}
				else {
					System.out.println("Die Animation " + currentAnimation.getAnimationAfterThis() + " in " + this.getClass().toString() 
							+ " konnte nicht gefunden werden");
				}
			}
		}
	}
	
	/**
	 * Advances the animation by one sprite
	 * @return indicates if the animation has finished and is ready to loop or change
	 */
	protected boolean nextSprite() {
		currentSpriteIndex++;
		if(currentSpriteIndex >= currentAnimation.getLength()) {
			currentSpriteIndex = 0;
			return true;
		}
		return false;
	}
	
	////////////////////////
	//////////////////////// GETTERS
	////////////////////////
	
	/**
	 * @return returns absolute z Value for render order. Position of this GameObject is factored in
	 */
	public double getZAbsolute() {
		return this.y + this.zPositionOffset;
	}
	
	public Sprite getCurrentSprite() {
		return currentAnimation.getSprite(currentSpriteIndex);
	}
	
	public Image getCurrentImage(double scale) {
		return getCurrentSprite().getImage(scale);
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	/**
	 * this method is used by the Leveleditor to obtain a 'copy' of a GameObject to be able to place it in the Scene.
	 * This method should be overwritten by any class, that does not implement a Constructor without any Parameters
	 */
	public GameObject getOtherInstance() {
		try {
			return this.getClass().getConstructor().newInstance();
		}catch(Exception e) {
			System.err.println("[EXCEPTION]: While trying to make a copy (call GameObject.getOtherInstance()) on the currently selected GameObject (type:'"
					+ this.getClass().toString() + "') a Exception was thrown. It is very likely that this class does not implement a Constructor without any Parameters."
					+ "To fix these issues, please create such a Constructor or overwrite the GameObject.getOtherInstance() (=> that method should return a new Object of " + this.getClass().toString() + "; You can pass any relevant data of the Object (for example text, if it's a sign) to it)"
			);
			e.printStackTrace();
			return null;
		}
	}
	
	////////////////////////
	//////////////////////// SETTERS
	////////////////////////
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
}
