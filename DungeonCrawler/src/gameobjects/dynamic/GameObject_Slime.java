package gameobjects.dynamic;

import engine.hitbox.Hitbox;
import gameobjects.DynamicGameObject;

public class GameObject_Slime extends DynamicGameObject {

	private static final long serialVersionUID = -4955172889570249749L;

	public GameObject_Slime() {		
		this(0, 0);
	}
	
	public GameObject_Slime(double x, double y) {
		super(x, y);
//		this.hitbox = new Hitbox(1, 1, this);
		this.hitbox = new Hitbox(10/16d, 5/16d, 0, 2.5/16d, this);
	}

	public void start() {}

	public void update() {
//		System.out.println(this.getX());
	}

}
