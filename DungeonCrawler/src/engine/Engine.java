package engine;

import engine.gameobjects.GameObject;
import engine.gui.GUIManager;

public class Engine {
	//TODO
	
	//TODO deltaTime (static, via public getter)
	
	private static Scene sceneActive;
	
	public static void startMainLoop() {
		(new Thread() {
			public void run() {
				
				while(true) {
					
					// --MAIN LOOP
					
					//TODO loop through game Objects => update()
					if(!GUIManager.shouldPause()) {
						for(GameObject gameObject : sceneActive.getGameObjects()) {
							gameObject.update();
						}
					}
					 //TODO UNTERSCHEIDEN ZWISCHEN ANIMATION DIE BEI PAUSIERUNG STOPPEN SOLLEN UND WELCHEN DIE DAS NICHT TUN SOLLEN => IDLE ANIMATIONEN
					//TODO rendern
					
					//TODO delay
					
					// --
				}				
			}
		}).start();
	}
}
