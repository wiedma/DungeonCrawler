package engine;

public class Engine {
	//TODO
	
	//TODO deltaTime (static, via public getter)
	
	public static void startMainLoop() {
		(new Thread() {
			public void run() {
				
				while(true) {
					
					// --MAIN LOOP
					
					//TODO loop through game Objects => update()
					
					//TODO rendern
					
					//TODO delay
					
					// --
				}				
			}
		}).start();
	}
}
