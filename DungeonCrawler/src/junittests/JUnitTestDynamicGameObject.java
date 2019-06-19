package junittests;

import java.util.HashMap;

import engine.animation.Animation;
import engine.gameobjects.DynamicGameObject;
import engine.hitbox.Hitbox;
import engine.sprites.Sprite;
import engine.sprites.Spritesheet;

public class JUnitTestDynamicGameObject extends DynamicGameObject {
	
	private static final HashMap<String, Animation> ANIMATION_MAP = new HashMap<String, Animation>();
	
	static {
		Animation defaultAnimation = new Animation("default", new Sprite[] 
				{new Sprite(0, 28, 2, 2, Spritesheet.DIR_SPRITESHEETS + "misc.png"),
				 new Sprite(2, 28, 2, 2, Spritesheet.DIR_SPRITESHEETS + "misc.png"),
				 new Sprite(4, 28, 2, 2, Spritesheet.DIR_SPRITESHEETS + "misc.png")}
		,0.3,false, "");
		ANIMATION_MAP.put("default", defaultAnimation);
	}

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1643624214074756401L;

	public JUnitTestDynamicGameObject() {
		super();
		animations = ANIMATION_MAP;
		currentAnimation = ANIMATION_MAP.get("default");
		this.hitbox = new Hitbox(2,1.5,this);
		this.zPositionOffset = 0.5;
	}
	
	public JUnitTestDynamicGameObject(double x, double y) {
		super(x, y, null, ANIMATION_MAP.get("default"), ANIMATION_MAP);
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
