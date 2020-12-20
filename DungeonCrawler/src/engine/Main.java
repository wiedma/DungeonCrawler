package engine;

import java.util.ArrayList;

import engine.io.FileLoader;
import gameobjects.GameObject;
import gameobjects.dynamic.GameObject_Slime;

/**
 * Main class used for testing
 * @author Marco
 *
 */
public class Main {
	
	public static void main(String[] args) {
		Engine.init();
		Engine.loadScene(FileLoader.readSceneFromFile(FileLoader.PATH_TO_FILES + "testRow"));
		
		ArrayList<GameObject> go = Engine.getSceneActive().getGameObjects();
		for(GameObject o : go) {
			if(o instanceof GameObject_Slime)
				((GameObject_Slime) o).setSpeedX(1);
		}
	}

}
