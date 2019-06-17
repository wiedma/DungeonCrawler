package junittests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import engine.Scene;
import engine.io.FileLoader;

class TestIOOperations {
	
	private static Scene s = new Scene();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		for(int i = 0; i < 100; i++) {
			s.addGameObject(new JUnitTestGameObject(Math.random() * 50));
		}
		s.sortByRenderOrder();
	}

	@Test
	void test() {
		FileLoader.writeToFile(s, FileLoader.PATH_TO_FILES + "JUnitTestScene");
		Scene copy = FileLoader.readFromFile(FileLoader.PATH_TO_FILES + "JUnitTestScene");
		copy.sortByRenderOrder();
		for(int i = 0; i < 100; i++) {
			assertTrue(((JUnitTestGameObject) (s.getGameObjects().get(i))).equals((JUnitTestGameObject)copy.getGameObjects().get(i)));
		}
		
	}

}
