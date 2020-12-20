package engine;

import java.util.ArrayList;

import engine.gameobjects.GameObject;
import engine.gameobjects.GameObject_Slime;
import engine.io.FileLoader;

/**
 * Main class used for testing
 * @author Marco
 *
 */
public class Main {
	
	public static void main(String[] args) {
		Engine.init();
		Engine.loadScene(FileLoader.readFromFile(FileLoader.PATH_TO_FILES + "testRow"));
		
		ArrayList<GameObject> go = Engine.getSceneActive().getGameObjects();
		for(GameObject o : go) {
			if(o instanceof GameObject_Slime)
				((GameObject_Slime) o).setSpeedX(1);
		}
	}

}
