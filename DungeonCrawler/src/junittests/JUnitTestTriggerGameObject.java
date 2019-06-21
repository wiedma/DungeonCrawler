package junittests;

import engine.gameobjects.GameObject;
import engine.hitbox.Hitbox;

public class JUnitTestTriggerGameObject extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1730653650509171533L;
	
	private transient int numberOfTriggers = 0;
	
	public JUnitTestTriggerGameObject() {
		super(0,0,null);
		this.hitbox = new Hitbox(2,2,this);
		this.hitbox.setIsTrigger(true);
	}

	public JUnitTestTriggerGameObject(double x, double y) {
		super(x,y,null);
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
