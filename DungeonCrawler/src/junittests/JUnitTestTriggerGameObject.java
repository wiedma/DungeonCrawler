package junittests;

import engine.hitbox.Hitbox;
import gameobjects.GameObject;

public class JUnitTestTriggerGameObject extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1730653650509171533L;
	
	private transient int numberOfTriggers = 0;
	
	public JUnitTestTriggerGameObject() {
		super(0,0);
		this.hitbox = new Hitbox(2,2,this);
		this.hitbox.setIsTrigger(true);
	}

	public JUnitTestTriggerGameObject(double x, double y) {
		super(x,y);
		this.hitbox = new Hitbox(2,2,this);
		this.hitbox.setIsTrigger(true);
	}

	@Override
	public void start() {
	}

	@Override
	public void update() {
	}
	
	public void onTrigger(GameObject other) {
		numberOfTriggers++;
	}
	
	public int getNumberOfTriggers() {
		return numberOfTriggers;
	}

}
