package gameobjects;

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
		this(0, 0);
	}
	
	public DynamicGameObject(double x, double y) {
		super(x,y);
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
