package junittests;

import engine.animation.Animator;
import engine.gameobjects.GameObject;

public class JUnitTestGameObject extends GameObject {

	private int counterStartMethodCalled = 0;
	private int counterUpdateMethodCalled = 0;

	public JUnitTestGameObject(double zValue) {
		this.zPositionOffset = zValue;
		this.animator = new Animator();
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
}
