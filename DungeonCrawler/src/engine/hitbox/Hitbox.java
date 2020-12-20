package engine.hitbox;

import java.io.Serializable;

import gameobjects.GameObject;

/**
 * Hitboxes used for collision detection
 * @author Marco, Daniel
 *
 */
public class Hitbox implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4617402936920975606L;
	/**
	 * Width of the hitbox in units
	 */
	private double width;
	/**
	 * The half of this Hitbox's width. Only used for internal collision-checking
	 */
	private double halfWidth;
	/**
	 * Height of the hitbox in units
	 */
	private double height;
	/**
	 * The half of this Hitbox's height. Only used for internal collision-checking
	 */
	private double halfHeight;
	/**
	 * The {@link GameObject} this Hitbox belongs to
	 */
	private GameObject owner;
	/**
	 * Inidicates if this Hitbox is used for real collisions or if it's just a trigger for events. False by default.
	 */
	private boolean isTrigger = false;
	
	public Hitbox(double width, double height, GameObject owner) {
		this.width = width;
		this.halfWidth = width/2.0;
		this.height = height;
		this.halfHeight = height/2.0;
		this.owner = owner;
	}
	
	/**
	 * <p>Checks if this Hitbox collides with the other Hitbox.</p>
	 * If one of the Hitboxes is a trigger, the Method {@link GameObject#onTrigger()} of the triggers' owner {@link GameObject} is called instead
	 * @param other The other Hitbox
	 * @return True only if the Hitboxes collide
	 */
	public boolean collidesWith(Hitbox other) {
		
		if(other == null) return false;
		
		boolean collision = this.halfWidth + other.halfWidth > Math.abs(this.owner.getX() - other.owner.getX()) &&
							this.halfHeight + other.halfHeight > Math.abs(this.owner.getY() - other.owner.getY());
		if(collision && isTrigger) {
			owner.onTrigger(other.owner);
			return false;
		}
		if(collision && other.isTrigger) {
			other.owner.onTrigger(this.owner);
			return false;
		}
		return collision;
	}
	

	/**
	 * Checks if a given point in coordinate space in within this Hitbox
	 * @param x X-coordinate of the point which is to be checked
	 * @param y Y-coordinate of the point which is to be checked
	 * @return True only if the point lies within or on the boundary of the Hitbox
	 */
	public boolean collidesWith(double x, double y) {
		double leftBound = owner.getX() - (width/2);
		double upBound = owner.getY() - (height/2);
		return x <= leftBound + width && x >= leftBound && y <= upBound + height && y >= upBound;
	}

	//Getters and Setters
	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
		this.halfWidth = width/2.0;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
		this.halfHeight = height/2.0;
	}

	public GameObject getOwner() {
		return owner;
	}

	public void setOwner(GameObject owner) {
		this.owner = owner;
	}
	
	public boolean isTrigger() {
		return this.isTrigger;
	}
	
	public void setIsTrigger(boolean isTrigger) {
		this.isTrigger = isTrigger;
	}
}
