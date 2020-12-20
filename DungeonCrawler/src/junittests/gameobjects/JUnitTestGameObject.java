package junittests.gameobjects;

import gameobjects.GameObject;

public class JUnitTestGameObject extends GameObject {


	private static final long serialVersionUID = -6980373835975577220L;
	private int counterStartMethodCalled = 0;
	private int counterUpdateMethodCalled = 0;
	
	
	public JUnitTestGameObject() {
		super(0, 0);
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
