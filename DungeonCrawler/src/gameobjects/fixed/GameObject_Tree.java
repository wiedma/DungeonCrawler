package gameobjects.fixed;

import engine.hitbox.Hitbox;
import gameobjects.GameObject;

public class GameObject_Tree extends GameObject {

	private static final long serialVersionUID = -6350700197722013786L;

	public GameObject_Tree() {
		this(0, 0);
		
	}
	
	public GameObject_Tree(double x, double y) {
		super(x, y);
		this.hitbox = new Hitbox(2, 2, this);
	}

	public void start() {}

	public void update() {}

}
