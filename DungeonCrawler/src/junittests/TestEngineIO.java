package junittests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import engine.Scene;
import engine.io.FileLoader;
import junittests.gameobjects.JUnitTestGameObject;

class TestEngineIO {
	
//	private static Scene s = new Scene();

	private final String PATH_TO_JUNIT_SCENES = FileLoader.PATH_TO_FILES + "junitTestScenes/";
	

//	@Test
//	public void test_readFileThatDoesNotExist() {
//		FileLoader.readSceneFromFile(PATH_TO_JUNIT_SCENES + "THIS WARNING-ERROR CAN BE IGNORED - JUNIT is just checking, that trying to read a nonexistent scene file does not crash the system (" + (Math.random()) + ").69");		
//	}
	
	@Test
	public void test_writeToAndReadSceneFromFile() {
		
		Scene sceneOriginal = new Scene();
		for(int i = 0; i < 100; i++) {
			//TODO add random positions
			sceneOriginal.addGameObject(new JUnitTestGameObject());
		}
		FileLoader.writeSceneToFile(sceneOriginal, PATH_TO_JUNIT_SCENES + "JUnitTestScene");
		
		try {
			Thread.sleep(100);
		} catch(Exception e) {}
		
		Scene sceneCopy = FileLoader.readSceneFromFile(PATH_TO_JUNIT_SCENES + "JUnitTestScene");
		for(int i = 0; i < 100; i++) {
			JUnitTestGameObject objOrig = (JUnitTestGameObject) (sceneOriginal.getGameObjects().get(i));  
			JUnitTestGameObject objCopy = (JUnitTestGameObject) (sceneCopy.getGameObjects().get(i));
			assertTrue("Writing a scene to file and reading it back resulted in two scenes with differing GameObjects",
					objOrig.equals(objCopy));
		}
	}

}
