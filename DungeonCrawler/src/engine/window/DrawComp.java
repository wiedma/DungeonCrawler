package engine.window;

import java.awt.Graphics;

import javax.swing.JComponent;

import engine.Engine;
import engine.Scene;
import engine.gameobjects.GameObject;
import engine.sprites.Sprite;

public class DrawComp extends JComponent {
	
	@Override
	public void paintComponent(Graphics g) {
		Sprite s;
		Scene scene = Engine.getSceneActive();
		for(GameObject gameObject : scene.getGameObjects()) {
			s = gameObject.getCurrentSprite();
			//TODO draw Sprite on relative position
		}
	}
}
