package engine;

import engine.io.FileLoader;

/**
 * Main class used for testing
 * @author Marco
 *
 */
public class Main {

	public static void main(String[] args) {
		Engine.init();
		Engine.loadScene(FileLoader.readFromFile(FileLoader.PATH_TO_FILES + "test"));
	}

}
