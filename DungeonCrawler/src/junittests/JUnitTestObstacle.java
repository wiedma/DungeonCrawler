package junittests;

import java.util.HashMap;

import engine.animation.Animation;
import engine.gameobjects.GameObject;
import engine.hitbox.Hitbox;
import engine.sprites.Sprite;
import engine.sprites.Spritesheet;

public class JUnitTestObstacle extends GameObject {

	private static final long serialVersionUID = 1329302109173864078L;
	public static final HashMap<String, Animation> ANIMATION_MAP = new HashMap<String, Animation>();
	static {
		Animation defaultAnimation = new Animation("default", new Sprite[] {
				new Sprite(16,0,2,2,Spritesheet.DIR_SPRITESHEETS + "misc.png")
		}, 0, false, "");
		ANIMATION_MAP.put("default", defaultAnimation);
	}
	
	public JUnitTestObstacle() {
		super(0,0,null,ANIMATION_MAP.get("default"),ANIMATION_MAP);
		this.hitbox = new Hitbox(2,2,this);
	}

	public JUnitTestObstacle(double x, double y) {
		super(x,y,null,ANIMATION_MAP.get("default"),ANIMATION_MAP);
		this.hitbox = new Hitbox(2,2,this);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
