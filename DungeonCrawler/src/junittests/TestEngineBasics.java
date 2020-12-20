package junittests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import engine.Scene;
import gameobjects.GameObject;
import junittests.gameobjects.JUnitTestGameObject;

public class TestEngineBasics {
	
	@Test
	public void test_sortGameObjectsByZ_noGameobjects() {
		Scene scene = new Scene();
		try {
			scene.sortByRenderOrder();
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		assertTrue(true);
	}
	
	@Test
	public void test_sortGameObjectsByZ() {		
		Scene scene = new Scene();
		for(int i = 0; i < 100; i++) {
			GameObject obj = new JUnitTestGameObject();
			obj.setZPositionOffset(Math.random()*10);
			scene.addGameObject(obj);
		}
				
		scene.sortByRenderOrder();
				
		for(int i = 0; i < 100-1; i++) {
			GameObject obj0 = scene.getGameObjects().get(i);  
			GameObject obj1 = scene.getGameObjects().get(i+1);
			assertTrue("(Scene).sortByRenderOrder() did not sort correctly.\n(The z values retured by (GameObject).getZAbsolute() were not sorted)",
					obj0.getZAbsolute() < obj1.getZAbsolute());
		}
	}
	
}
