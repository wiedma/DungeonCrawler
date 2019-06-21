package junittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import engine.Engine;
import engine.Scene;

class TestCollisionSystem {
	
	private static Scene scene;
	private static JUnitTestDynamicGameObject movingObject;
	private static JUnitTestTriggerGameObject firstTrigger;
	private static JUnitTestTriggerGameObject secondTrigger;
	private static JUnitTestObstacle obstacle;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		scene = new Scene();
		movingObject = new JUnitTestDynamicGameObject(0,5);
		firstTrigger = new JUnitTestTriggerGameObject(2.5, 5);
		secondTrigger = new JUnitTestTriggerGameObject(5, 5);
		obstacle = new JUnitTestObstacle(5,5);
		scene.addGameObject(movingObject);
		scene.addGameObject(firstTrigger);
		scene.addGameObject(secondTrigger);
		scene.addGameObject(obstacle);
	}

	@Test
	void test() {
		Engine.init();
		Engine.loadScene(scene);
		try {
			Thread.sleep(3500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(3, movingObject.getX(), 0.01);
		assertTrue(firstTrigger.getNumberOfTriggers() > 0); 
		assertTrue(secondTrigger.getNumberOfTriggers() == 0);
	}

}
