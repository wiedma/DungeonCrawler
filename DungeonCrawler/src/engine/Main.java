package engine;

import engine.io.FileLoader;

/**
 * Main class used for testing
 * @author Marco
 *
 */
public class Main {

	public static void main(String[] args) {
//		Engine.init();
		FileLoader.readFromFile("res/scenes/JUnitTestScene");
	}

}
