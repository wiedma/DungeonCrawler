package gameobjects.fixed;

import engine.hitbox.Hitbox;
import gameobjects.GameObject;

public class GameObject_TreeSmol extends GameObject {

	
	private static final long serialVersionUID = 369890715285422485L;

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
