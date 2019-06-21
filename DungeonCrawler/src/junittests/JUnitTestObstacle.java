package junittests;

import engine.gameobjects.GameObject;
import engine.hitbox.Hitbox;

public class JUnitTestObstacle extends GameObject {

	private static final long serialVersionUID = 1329302109173864078L;
	
	public JUnitTestObstacle() {
		super(0,0,null);
		this.hitbox = new Hitbox(2,2,this);
	}

	public JUnitTestObstacle(double x, double y) {
		super(x,y,null);
		this.hitbox = new Hitbox(2,2,this);
	}

	@Override
	public void start() {
	}

	@Override
	public void update() {

	}

}
