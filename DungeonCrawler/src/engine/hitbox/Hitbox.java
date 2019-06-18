package engine.hitbox;

import java.io.Serializable;

import engine.gameobjects.GameObject;

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
	 * Height of the hitbox in units
	 */
	private double height;
	/**
	 * The {@link GameObject} this Hitbox belongs to
	 */
	private GameObject owner;
	
	public Hitbox(double width, double height, GameObject owner) {
		this.width = width;
		this.height = height;
		this.owner = owner;
	}
	
	/**
	 * Checks if this Hitbox collides with the other Hitbox
	 * @param other The other Hitbox
	 * @return True only if the Hitboxes collide
	 */
	public boolean collidesWith(Hitbox other) {
		//TODO: Collision checking for other hitboxes
		return false;
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
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public GameObject getOwner() {
		return owner;
	}

	public void setOwner(GameObject owner) {
		this.owner = owner;
	}
	
	
}
