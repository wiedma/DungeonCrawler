package engine.gameobjects;

import java.util.HashMap;

import engine.animation.Animation;
import engine.hitbox.Hitbox;

/**
 * Superclass of all GameObjects which are able to move
 * @author Marco, Daniel
 *
 */
abstract public class DynamicGameObject extends GameObject {
	/**
	 * SerialVersionUID for serialization
	 */
	private static final long serialVersionUID = -7650282908372282384L;
	
	/**
	 * This Objects speed in x-direction
	 */
	protected double speedX;
	
	/**
	 * This Objects speed in y-direction
	 */
	protected double speedY;

	public DynamicGameObject() {
		super(0, 0, null, null, null);
	}
	
	public DynamicGameObject(double x, double y, Hitbox hitbox, Animation startAnimation, HashMap<String, Animation> animations) {
		super(x,y,hitbox,startAnimation, animations);
	}
	
	//Getters and Setters
	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}
	
}
