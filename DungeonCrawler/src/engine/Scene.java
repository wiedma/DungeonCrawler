package engine;

import java.util.ArrayList;

import engine.gameobjects.GameObject;

/**
 * Scenes
 * @author Marco, Daniel
 *
 */
public class Scene {
	
	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	
	private String initialFilePath;
	
	/**
	 * Gives all GameObjects within this scene
	 * @return ArrayList of all GameObjects
	 */
	public ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}
	
	/**
	 * sortes GameObjects in ascending absolute Z Value. This order is maintained throughout this frame and used beyond.
	 */
	public void sortByRenderOrder() {
		int gameObjectAmount = gameObjects.size();
		GameObject cacheGameObject;
		for(int i = 0; i < gameObjectAmount - 1; i++) {
			if(gameObjects.get(i).getZAbsolute() > gameObjects.get(i + 1).getZAbsolute()) {
				cacheGameObject = gameObjects.get(i);
				gameObjects.set(i, gameObjects.get(i + 1));
				gameObjects.set(i + 1, cacheGameObject);
				i -= i == 0 ? 1 : 2;
			}
		}
	}
	
	public void addGameObject(GameObject gameObject) {
		this.gameObjects.add(gameObject);
	}
	
	public void removeGameObject(GameObject gameObject) {
		if(gameObjects.contains(gameObject)) {
			this.gameObjects.remove(gameObjects.indexOf(gameObject));			
		}
	}
	
	public String getInitialFilePath() {
		return this.initialFilePath;
	}
	public void setInitialFilePath(String filePath) {
		this.initialFilePath = filePath;
	}
}
