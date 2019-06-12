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
	
	//TODO
	/**
	 * Gives all GameObjects within this scene
	 * @return ArrayList of all GameObjects
	 */
	public ArrayList<GameObject> getGameObjects() {
		//TODO: return all GameObjects
		return null;
	}
	
	public void sortByRenderOrder() {
		int gameObjectAmount = gameObjects.size();		
		GameObject cacheGameObject;
		for(int i = 0; i < gameObjectAmount - 1; i++) {
			
			if(gameObjects.get(i).getZAbsolute() > gameObjects.get(i + 1).getZAbsolute()) {
				cacheGameObject = gameObjects.get(i);
				gameObjects.set(i, gameObjects.get(i + 1));
				gameObjects.set(i + 1, cacheGameObject);
				i -= 2;
			}
		}
	}
}
