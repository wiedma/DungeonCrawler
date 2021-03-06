package engine.window;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Iterator;
import java.util.function.Consumer;

import javax.swing.JComponent;

import engine.Engine;
import engine.Scene;
import engine.gui.GUIElement;
import engine.gui.GUIManager;
import engine.sprites.Sprite;
import gameobjects.GameObject;

public class DrawComp extends JComponent {
	
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 8610317593291514579L;
	
	/**
	 * states the 'zoom level' of sprites that are currently being displayed<br>
	 * if the sprite on the screen is double its actual size in the spritesheet file, the value of this would be 2
	 */	
	private static double spriteScale = 1;
	public static final int SPRITE_SIZE_PX_ORIGINAL = 16;
	
	@Override
	public void paintComponent(Graphics g_) {		
		Graphics2D g = (Graphics2D) g_;
		int screenWidth = this.getWidth();
		int screenHeight = this.getHeight();
		
		double pxPerTile = spriteScale * SPRITE_SIZE_PX_ORIGINAL;
		
		Sprite sprite;
		Image img;
		Scene scene = Engine.getSceneActive();
		for(GameObject gameObject : scene.getGameObjects()) {
			sprite = gameObject.getCurrentSprite();
			img = sprite.getImage(spriteScale);
			
//			if(gameObject == scene.getGameObjects().get(0)) {
//				System.out.println((int) (   (gameObject.getX() - (sprite.getWidth()/2d)) * pxPerTile   ));
//				System.out.println(img);
//			}
			
			g.drawImage(img,
					(int) (   (gameObject.getX() - (sprite.getWidth()/2d)) * pxPerTile   ),
					(int) (   (gameObject.getY() - (sprite.getHeight()/2d)) * pxPerTile   ),
					(int)     (sprite.getWidth() * pxPerTile),
					(int)     (sprite.getHeight() * pxPerTile),
					null
			);	
		}
		
		
		Iterator<GUIElement> guiElements = GUIManager.getGUIElements();
		guiElements.forEachRemaining(new Consumer<GUIElement>() {
			public void accept(GUIElement guiElement) {
				guiElement.display(g, screenWidth, screenHeight);
			}			
		});
		
//		System.out.println(scene.getGameObjects().get(0).getX());
	}
}
