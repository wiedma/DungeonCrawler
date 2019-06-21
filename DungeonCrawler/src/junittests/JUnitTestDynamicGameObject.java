package junittests;

import engine.gameobjects.DynamicGameObject;
import engine.hitbox.Hitbox;

public class JUnitTestDynamicGameObject extends DynamicGameObject {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1643624214074756401L;

	public JUnitTestDynamicGameObject() {
		super();
		this.hitbox = new Hitbox(2,1.5,this);
		this.zPositionOffset = 0.5;
	}
	
	public JUnitTestDynamicGameObject(double x, double y) {
		super(x, y, null);
		this.hitbox = new Hitbox(2,1.5,this);
		this.zPositionOffset = 0.5;
	}

	@Override
	public void start() {
		this.speedX = 1;
	}

	@Override
	public void update() {

	}

}
