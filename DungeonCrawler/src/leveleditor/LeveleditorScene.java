package leveleditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JComponent;

import engine.Scene;
import engine.gameobjects.GameObject;
import engine.sprites.Sprite;
import engine.window.DrawComp;

public class LeveleditorScene extends JComponent {
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -8891639164169984471L;
	
	/**
	 * The {@link LeveleditorObjectChooser ObjectChooser} which applies for this Scene-Editor
	 */
	private LeveleditorObjectChooser objectChooser;
	
	/**
	 * The {@link GameObject} which is currently being dragged by the user.
	 */
	private GameObject draggedObject;
	
	/**
	 * The {@link Scene} which is currently being edited
	 */
	private Scene scene;
	
	/**
	 * Scale of the sprites when drawn
	 */
	private double spriteScale = 1;
	
	public LeveleditorScene(LeveleditorObjectChooser objectChooser) {
		this.objectChooser = objectChooser;
		scene = new Scene();
	}
	
	public void paintComponent(Graphics g) {
		
//		g.setColor(new Color(0f, 0f, 0.5f, 0.3f));
//		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		scene.sortByRenderOrder();
		
		double pxPerTile = spriteScale * DrawComp.SPRITE_SIZE_PX_ORIGINAL;
		Sprite sprite;
		Image img;
		for(GameObject gameObject : scene.getGameObjects()) {
			sprite = gameObject.getCurrentSprite();
			img = sprite.getImage(spriteScale);
			
			g.drawImage(img,
					(int) (   (gameObject.getX() - (sprite.getWidth()/2d)) * pxPerTile   ),
					(int) (   (gameObject.getY() - (sprite.getHeight()/2d)) * pxPerTile   ),
					(int)     (sprite.getWidth() * pxPerTile),
					(int)     (sprite.getHeight() * pxPerTile),
					null
			);	
		}
	}
	
	/**
	 * <p>Cache-variable. This indicates whether the mouse was over this component the last time the method was called.</p>
	 * <p>It is used when determining whether the selected GameObject of the {@link LeveleditorObjectChooser} should be added or deleted to the {@link Scene}</p>
	 */
	private boolean mouseOnLastFrame = false;
	
	public void processMouseHover() {
		draggedObject = objectChooser.getSelectedGameObject();				
		if(draggedObject == null) {
			mouseOnLastFrame = this.getMousePosition() != null;
			return;
		}
		
		Point mousePos = this.getMousePosition();
		if(mousePos == null) {
			if(mouseOnLastFrame) {
				scene.removeGameObject(draggedObject);
				System.out.println("Removed GameObject " + draggedObject.getClass().toString() + " from Scene!");
			}
			mouseOnLastFrame = false;
			return;
		}
		
		draggedObject.setX(mousePos.getX());
		draggedObject.setY(mousePos.getY());
		
		if(!mouseOnLastFrame) {
			scene.addGameObject(draggedObject);
			System.out.println("Added GameObject " + draggedObject.getClass().toString() + " to Scene!");
		}
		
		this.mouseOnLastFrame = true;
	}
}
