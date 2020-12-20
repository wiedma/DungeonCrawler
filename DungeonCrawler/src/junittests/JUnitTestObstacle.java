package junittests;

import engine.hitbox.Hitbox;
import gameobjects.GameObject;

public class JUnitTestObstacle extends GameObject {

	private static final long serialVersionUID = 1329302109173864078L;
	
	public JUnitTestObstacle() {
		super(0,0);
		this.hitbox = new Hitbox(2,2,this);
	}

	public JUnitTestObstacle(double x, double y) {
		super(x,y);
		this.hitbox = new Hitbox(2,2,this);
	}

	@Override
	public void start() {
	}

	@Override
	public void update() {

	}

}
