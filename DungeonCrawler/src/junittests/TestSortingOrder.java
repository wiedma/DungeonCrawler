package junittests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import engine.Engine;
import engine.Scene;
import gameobjects.GameObject;

class TestSortingOrder {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Engine.init();
		
		//add GameObjects to scene
		Scene scene = new Scene();
		
		for(int i = 0; i < 50; i++)
			scene.addGameObject(new JUnitTestGameObject(Math.random()));
		
		Engine.loadScene(scene);
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		
		try {
			Thread.sleep(2000);
		} catch(Exception e) {}
		
		ArrayList<GameObject> gameObjects = Engine.getSceneActive().getGameObjects(); 
		for(int i = 0; i < gameObjects.size() - 1; i++) {
			assertTrue(gameObjects.get(i).getZAbsolute() < gameObjects.get(i + 1).getZAbsolute());
			System.out.println(gameObjects.get(i).getZAbsolute());
		}
	}

}
