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
	public void test_hitboxCollision() {
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
	}
	

}
