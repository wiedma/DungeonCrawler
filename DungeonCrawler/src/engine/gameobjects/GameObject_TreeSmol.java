package engine.gameobjects;

import engine.hitbox.Hitbox;

public class GameObject_TreeSmol extends GameObject {

	
	public GameObject_TreeSmol(double x, double y) {
		super(x, y);
		this.hitbox = new Hitbox(1, 2, this);
	}
	
	public GameObject_TreeSmol() {
		this(0, 0);		
	}

	public void start() {}

	public void update() {}

}
