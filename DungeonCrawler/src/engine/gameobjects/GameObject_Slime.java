package engine.gameobjects;

import engine.hitbox.Hitbox;

public class GameObject_Slime extends DynamicGameObject {

	private static final long serialVersionUID = -4955172889570249749L;

	public GameObject_Slime() {		
		this(0, 0);
	}
	
	public GameObject_Slime(double x, double y) {
		super(x, y);
		this.hitbox = new Hitbox(2, 2, this);
	}

	public void start() {}

	public void update() {
//		System.out.println(this.getX());
	}

}
