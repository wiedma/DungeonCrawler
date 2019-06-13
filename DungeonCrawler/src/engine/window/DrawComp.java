package engine.window;

import java.awt.Graphics;

import javax.swing.JComponent;

import engine.Engine;
import engine.Scene;
import engine.gameobjects.GameObject;
import engine.sprites.Sprite;

public class DrawComp extends JComponent {
	
	/**
	 * states the 'zoom level' of sprites that are currently being displayed<br>
	 * if the sprite on the screen is double its actual size in the spritesheet file, the value of this would be 2
	 */	
	private static double spriteScale = 1;
	public static final int SPRITE_SIZE_PX_ORIGINAL = 16;
	
	@Override
	public void paintComponent(Graphics g) {
		Sprite s;
		Scene scene = Engine.getSceneActive();
		for(GameObject gameObject : scene.getGameObjects()) {
			s = gameObject.getCurrentSprite();
			//TODO draw Sprite on relative position			
		}
	}
	
	/**
	 * see {@link DrawComp#spriteScale} for further reference 
	 */
	public static double getSpriteScale() {
		return DrawComp.spriteScale;
	}
}
