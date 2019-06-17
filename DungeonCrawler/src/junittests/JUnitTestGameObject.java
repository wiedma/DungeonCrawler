package junittests;

import java.util.HashMap;

import engine.animation.Animation;
import engine.gameobjects.GameObject;
import engine.sprites.Sprite;

public class JUnitTestGameObject extends GameObject {

	private int counterStartMethodCalled = 0;
	private int counterUpdateMethodCalled = 0;
	private static final HashMap<String, Animation> ANIMATION_MAP_JUNITTESTGAMEOBJECT = new HashMap<String, Animation>();
	
	static {
		Animation defaultAnimation = new Animation("default", new Sprite[] {new Sprite(11, 0, 1, 2, "res/spritesheets/baum.jpg")}, 1, false, null);
		ANIMATION_MAP_JUNITTESTGAMEOBJECT.put(defaultAnimation.getName(), defaultAnimation);
	}

	public JUnitTestGameObject(double zValue) {
		super(0, 0, null, ANIMATION_MAP_JUNITTESTGAMEOBJECT.get("default"), ANIMATION_MAP_JUNITTESTGAMEOBJECT);
		this.zPositionOffset = zValue;
	}
	
//	public GameObject getOtherInstance() {
//		return new JUnitTestGameObject(this.zPositionOffset);		
//	}
	
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
