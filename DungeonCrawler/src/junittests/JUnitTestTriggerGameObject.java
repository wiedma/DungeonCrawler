package junittests;

import java.util.HashMap;

import engine.animation.Animation;
import engine.gameobjects.GameObject;
import engine.hitbox.Hitbox;
import engine.sprites.Sprite;
import engine.sprites.Spritesheet;

public class JUnitTestTriggerGameObject extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1730653650509171533L;
	
	private transient int numberOfTriggers = 0;
	
	public static final HashMap<String, Animation> ANIMATION_MAP = new HashMap<String, Animation>();
	static {
		Animation defaultAnimation = new Animation("default", new Sprite[] {
				new Sprite(18,16,2,2,Spritesheet.DIR_SPRITESHEETS + "misc.png")
		}, 0, false, "");
		ANIMATION_MAP.put("default", defaultAnimation);
	}
	
	public JUnitTestTriggerGameObject() {
		super(0,0,null,ANIMATION_MAP.get("default"),ANIMATION_MAP);
		this.hitbox = new Hitbox(2,2,this);
		this.hitbox.setIsTrigger(true);
	}

	public JUnitTestTriggerGameObject(double x, double y) {
		super(x,y,null,ANIMATION_MAP.get("default"),ANIMATION_MAP);
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
