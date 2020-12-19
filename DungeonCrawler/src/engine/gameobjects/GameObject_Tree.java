package engine.gameobjects;

import engine.hitbox.Hitbox;

public class GameObject_Tree extends GameObject {

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