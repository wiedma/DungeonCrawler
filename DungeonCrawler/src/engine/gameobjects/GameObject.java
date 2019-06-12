package engine.gameobjects;

import engine.animation.Animator;
import engine.hitbox.Hitbox;

abstract public class GameObject {
	
	private Hitbox hitbox; //TODO
	
	private double x, y; //TODO
	
	private Animator Sanimator; //TODO
	
	private double zPositionOffset = 0.5D;	//TODO use in sorting
	
	public abstract void start();
	
	public abstract void update();	
}
