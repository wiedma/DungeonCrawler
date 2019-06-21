package junittests;

import engine.gameobjects.GameObject;
import engine.hitbox.Hitbox;

public class JUnitTestGameObject extends GameObject {


	private static final long serialVersionUID = -6980373835975577220L;
	private int counterStartMethodCalled = 0;
	private int counterUpdateMethodCalled = 0;
	
	
	public JUnitTestGameObject(double zValue) {
		super(0, 0, null);
		this.zPositionOffset = zValue;
		this.hitbox = new Hitbox(5, 8, this);
	}
	
	public GameObject getOtherInstance() {
		return new JUnitTestGameObject(this.zPositionOffset);		
	}
	
	public void start() {
		counterStartMethodCalled++;
	}

	public void update() {
		counterUpdateMethodCalled++;
	}
	
	public int getCounterStartMethodCalled() {
		return counterStartMethodCalled;
	}
	
	public int getCounterUpdateMethodCalled() {
		return counterUpdateMethodCalled;
	}
	
	public String toString() {
		return this.getClass().getName() + ";" + this.getX() + ";" + this.getY() + ";1;java.lang.Double;" + this.zPositionOffset;
	}
	
	public boolean equals(JUnitTestGameObject other) {
		return this.zPositionOffset == other.zPositionOffset;
	}
}
