package junittests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import engine.hitbox.Hitbox;
import gameobjects.GameObject;
import junittests.gameobjects.JUnitTestGameObject;

public class TestCollisionSystem {
	
//	private static Scene scene;
//	private static JUnitTestDynamicGameObject movingObject;
//	private static JUnitTestTriggerGameObject firstTrigger;
//	private static JUnitTestTriggerGameObject secondTrigger;
//	private static JUnitTestObstacle obstacle;

//	@BeforeAll
//	static void setUpBeforeClass() throws Exception {
//		scene = new Scene();
//		
//		movingObject = new JUnitTestDynamicGameObject(0,5);
//		scene.addGameObject(movingObject);
//				
//		firstTrigger = new JUnitTestTriggerGameObject(2.5, 5);
//		scene.addGameObject(firstTrigger);
//		
//		secondTrigger = new JUnitTestTriggerGameObject(5, 5);
//		scene.addGameObject(secondTrigger);
//		
//		obstacle = new JUnitTestObstacle(5,5);
//		scene.addGameObject(obstacle);
//	}
//
//	@Test
//	void test() {
//		Engine.init();
//		Engine.loadScene(scene);
//		try {
//			Thread.sleep(3500);
//		} catch(InterruptedException e) {
//			e.printStackTrace();
//		}
//		assertEquals(3, movingObject.getX(), 0.01);
//		assertTrue(firstTrigger.getNumberOfTriggers() > 0); 
//		assertTrue(secondTrigger.getNumberOfTriggers() == 0);
//	}
	
	@Test
	public void test_hitboxCollisionWithPoint() {
		
		double[] valuesHitboxPosition = new double[] {0, 10, -10};
		double[] valuesHitboxSize = new double[] {1, 4, 17};
		double[] valuesHitboxOffset = new double[] {0, -30, 30};
		
		for(double hitboxPosition : valuesHitboxPosition) {
			for(double hitboxSize : valuesHitboxSize) {
				for(double hitboxOffset : valuesHitboxOffset) {
					test_hitboxCollisionWithPoint_xAxis(hitboxPosition, hitboxSize, hitboxOffset);
					test_hitboxCollisionWithPoint_yAxis(hitboxPosition, hitboxSize, hitboxOffset);
				}
			}
		}
		
		//TODO test collision on x and y axis at the same time 
	}
	
	private void test_hitboxCollisionWithPoint_xAxis(double gameObjectX, double hitboxWidth, double hitboxOffsetX) {
		GameObject gameObject0 = new JUnitTestGameObject();
		gameObject0.setX(gameObjectX);
		gameObject0.setY(1);
		
		double pointY = 1;
		
		Hitbox h1 = new Hitbox(hitboxWidth, pointY, hitboxOffsetX, 0, gameObject0);
		
		double smallValue = 0.01;		
		
		//No collision at all
		assertFalse("Engine said that hitbox collides with point, although they don't"
				+ "[gameObjectX:" + gameObject0.getX() + ", gameObjectY:" + gameObject0.getY() + ", hitboxWidth:" + h1.getWidth() + ", hitboxHeight:" + h1.getHeight() + ", hitboxOffsetX:" + h1.getOffsetX() + ", hitboxOffsetY:" + h1.getOffsetY() + ", "
						+ "pointX:" + (gameObject0.getX()+(h1.getWidth()/2)+smallValue) + ", pointY:1]",
				h1.collidesWith((gameObjectX + hitboxOffsetX) + (hitboxWidth/2) + smallValue, pointY));
		assertFalse("Engine said that hitbox collides with point, although they don't"
				+ "[gameObjectX:" + gameObject0.getX() + ", gameObjectY:" + gameObject0.getY() + ", hitboxWidth:" + h1.getWidth() + ", hitboxHeight:" + h1.getHeight() + ", hitboxOffsetX:" + h1.getOffsetX() + ", hitboxOffsetY:" + h1.getOffsetY() + ", "
						+ "pointX:" + (gameObject0.getX()-(h1.getWidth()/2)-smallValue) + ", pointY:1]",
				h1.collidesWith((gameObjectX + hitboxOffsetX) - (hitboxWidth/2) - smallValue, pointY));
		
		//Tangient
		assertTrue("Engine said that hitbox does not collide with point, although they tangent (counts as collision) each other"
				+ "[gameObjectX:" + gameObject0.getX() + ", gameObjectY:" + gameObject0.getY() + ", hitboxWidth:" + h1.getWidth() + ", hitboxHeight:" + h1.getHeight() + ", hitboxOffsetX:" + h1.getOffsetX() + ", hitboxOffsetY:" + h1.getOffsetY() + ", "
						+ "pointX:" + (gameObject0.getX()+(h1.getWidth()/2)) + ", pointY:1]",
				h1.collidesWith((gameObjectX + hitboxOffsetX) + (hitboxWidth/2), pointY));
		assertTrue("Engine said that hitbox does not collide with point, although they tangent (counts as collision) each other"
				+ "[gameObjectX:" + gameObject0.getX() + ", gameObjectY:" + gameObject0.getY() + ", hitboxWidth:" + h1.getWidth() + ", hitboxHeight:" + h1.getHeight() + ", hitboxOffsetX:" + h1.getOffsetX() + ", hitboxOffsetY:" + h1.getOffsetY() + ", "
						+ "pointX:" + (gameObject0.getX()-(h1.getWidth()/2)) + ", pointY:1]",
				h1.collidesWith((gameObjectX + hitboxOffsetX) - (hitboxWidth/2), pointY));
		
		//Overlapping
		assertTrue("Engine said that hitbox does not collide with point, even though they do"
				+ "[gameObjectX:" + gameObject0.getX() + ", gameObjectY:" + gameObject0.getY() + ", hitboxWidth:" + h1.getWidth() + ", hitboxHeight:" + h1.getHeight() + ", hitboxOffsetX:" + h1.getOffsetX() + ", hitboxOffsetY:" + h1.getOffsetY() + ", "
						+ "pointX:" + (gameObject0.getX()+(h1.getWidth()/2)-smallValue) + ", pointY:1]",
				h1.collidesWith((gameObjectX + hitboxOffsetX) + (hitboxWidth/2) - smallValue, pointY));
		assertTrue("Engine said that hitbox does not collide with point, even though they do"
				+ "[gameObjectX:" + gameObject0.getX() + ", gameObjectY:" + gameObject0.getY() + ", hitboxWidth:" + h1.getWidth() + ", hitboxHeight:" + h1.getHeight() + ", hitboxOffsetX:" + h1.getOffsetX() + ", hitboxOffsetY:" + h1.getOffsetY() + ", "
						+ "pointX:" + (gameObject0.getX()-(h1.getWidth()/2)+smallValue) + ", pointY:1]",
				h1.collidesWith((gameObjectX + hitboxOffsetX) - (hitboxWidth/2) + smallValue, pointY));
	}
	
	private void test_hitboxCollisionWithPoint_yAxis(double yAbsHitboxCenter, double hitboxHeight, double hitboxOffsetY) {
		GameObject gameObject0 = new JUnitTestGameObject();
		gameObject0.setX(1);		
		gameObject0.setY(yAbsHitboxCenter);
		
		double pointX = 1;
		
		Hitbox h1 = new Hitbox(pointX, hitboxHeight, 0,  hitboxOffsetY, gameObject0);
		
		double smallValue = 0.01;
		
		//No collision at all
		assertFalse("Engine said that hitbox collides with point, although they don't"
				+ "[gameObjectX:" + gameObject0.getX() + ", gameObjectY:" + gameObject0.getY() + ", hitboxWidth:" + h1.getWidth() + ", hitboxHeight:" + h1.getHeight() + ", hitboxOffsetX:" + h1.getOffsetX() + ", hitboxOffsetY:" + h1.getOffsetY() + ", "
						+ "pointX:" + pointX + ", pointY:" + (gameObject0.getY()+(h1.getHeight()/2)+smallValue) + "]",
				h1.collidesWith(pointX, (yAbsHitboxCenter + hitboxOffsetY) + (hitboxHeight/2) + smallValue));
		assertFalse("Engine said that hitbox collides with point, although they don't"
				+ "[gameObjectX:" + gameObject0.getX() + ", gameObjectY:" + gameObject0.getY() + ", hitboxWidth:" + h1.getWidth() + ", hitboxHeight:" + h1.getHeight() + ", hitboxOffsetX:" + h1.getOffsetX() + ", hitboxOffsetY:" + h1.getOffsetY() + ", "
						+ "pointX:" + pointX + ", pointY:" + (gameObject0.getY()-(h1.getHeight()/2)-smallValue) + "]",
				h1.collidesWith(1, (yAbsHitboxCenter + hitboxOffsetY) - (hitboxHeight/2) - smallValue));
		
		//Tangient
		assertTrue("Engine said that hitbox does not collide with point, although they tangent (counts as collision) each other"
				+ "[gameObjectX:" + gameObject0.getX() + ", gameObjectY:" + gameObject0.getY() + ", hitboxWidth:" + h1.getWidth() + ", hitboxHeight:" + h1.getHeight() + ", hitboxOffsetX:" + h1.getOffsetX() + ", hitboxOffsetY:" + h1.getOffsetY() + ", "
						+ "pointX:" + pointX + ", pointY:" + (gameObject0.getY()+(h1.getHeight()/2)) + "]",
				h1.collidesWith(1, (yAbsHitboxCenter + hitboxOffsetY) + (hitboxHeight/2)));
		assertTrue("Engine said that hitbox does not collide with point, although they tangent (counts as collision) each other"
				+ "[gameObjectX:" + gameObject0.getX() + ", gameObjectY:" + gameObject0.getY() + ", hitboxWidth:" + h1.getWidth() + ", hitboxHeight:" + h1.getHeight() + ", hitboxOffsetX:" + h1.getOffsetX() + ", hitboxOffsetY:" + h1.getOffsetY() + ", "
						+ "pointX:" + pointX + ", pointY:" + (gameObject0.getY()-(h1.getHeight()/2)) + "]",
				h1.collidesWith(1, (yAbsHitboxCenter + hitboxOffsetY) - (hitboxHeight/2)));
		
		//Overlapping
		assertTrue("Engine said that hitbox does not collide with point, even though they do"
				+ "[gameObjectX:" + gameObject0.getX() + ", gameObjectY:" + gameObject0.getY() + ", hitboxWidth:" + h1.getWidth() + ", hitboxHeight:" + h1.getHeight() + ", hitboxOffsetX:" + h1.getOffsetX() + ", hitboxOffsetY:" + h1.getOffsetY() + ", "
						+ "pointX:" + pointX + ", pointY:" + (gameObject0.getY()+(h1.getHeight()/2)-smallValue) + "]",
				h1.collidesWith(1, (yAbsHitboxCenter + hitboxOffsetY) + (hitboxHeight/2) - smallValue));
		assertTrue("Engine said that hitbox does not collide with point, even though they do"
				+ "[gameObjectX:" + gameObject0.getX() + ", gameObjectY:" + gameObject0.getY() + ", hitboxWidth:" + h1.getWidth() + ", hitboxHeight:" + h1.getHeight() + ", hitboxOffsetX:" + h1.getOffsetX() + ", hitboxOffsetY:" + h1.getOffsetY() + ", "
						+ "pointX:" + pointX + ", pointY:" + (gameObject0.getY()-(h1.getHeight()/2)+smallValue) + "]",
				h1.collidesWith(1, (yAbsHitboxCenter + hitboxOffsetY) - (hitboxHeight/2) + smallValue));
	}
	
	@Test
	public void test_hitboxCollisionWithHitbox() {
		Hitbox h1, h2;
		
		GameObject gameObject0 = new JUnitTestGameObject();
		gameObject0.setX(1);
		gameObject0.setY(1);
		
		GameObject gameObject1 = new JUnitTestGameObject();
		gameObject1.setX(3);
		gameObject1.setY(1);
		
		
		//No collision at all
		h1 = new Hitbox(1, 1, gameObject0);
		h2 = new Hitbox(1, 1, gameObject1);
		assertFalse("Engine said that two hitboxes collide, although they don't", h1.collidesWith(h2));
		
		//Tangient
		h1 = new Hitbox(2, 1, gameObject0);
		h2 = new Hitbox(2, 1, gameObject1);
		assertFalse("Engine said that two hitboxes collide, although they only tangient each other", h1.collidesWith(h2));
		
		//Overlapping
		h1 = new Hitbox(2, 1, gameObject0);
		h2 = new Hitbox(2.5, 1, gameObject1);
		assertTrue("Engine said that two hitboxes DO NOT collide, although they do", h1.collidesWith(h2));
		
		//TODO test collsion for hitboxes with offset
	}
	

}
