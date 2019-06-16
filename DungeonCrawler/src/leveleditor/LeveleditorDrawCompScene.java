package leveleditor;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JComponent;

import engine.Engine;
import engine.Scene;
import engine.gameobjects.GameObject;
import engine.sprites.Sprite;
import engine.window.DrawComp;
import engine.window.KeyRegister;

public class LeveleditorDrawCompScene extends JComponent implements MouseListener, MouseWheelListener {
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -8891639164169984471L;

	/**
	 * states the 'zoom level' of sprites that are currently being displayed<br>
	 * if the sprite on the screen is double its actual size in the spritesheet file, the value of this would be 2
	 */	
	private static double spriteScale = 1;
	
	private Leveleditor leveleditor;
	
	public LeveleditorDrawCompScene(Leveleditor leveleditor) {
		this.leveleditor = leveleditor;
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {		
		
		Scene scene = Engine.getSceneActive();
		double pxPerTile = spriteScale * DrawComp.SPRITE_SIZE_PX_ORIGINAL;
		
		
		Sprite sprite;
		for(GameObject gameObject : scene.getGameObjects()) {
			sprite = gameObject.getCurrentSprite();
			
			g.drawImage(sprite.getImage(spriteScale),
					(int) (   (gameObject.getX() - (sprite.getWidth()/2d)) * pxPerTile   ),
					(int) (   (gameObject.getY() - (sprite.getHeight()/2d)) * pxPerTile   ),
//					(int)     (sprite.getWidth() * pxPerTile),
//					(int)     (sprite.getHeight() * pxPerTile),
					null
			);
		}
		
		
		//if the user has selected a GameObject and is hovering over the scene, draw the GameObject as if it was placed a the mouses current position
		GameObject gameObjectSelected = leveleditor.getSelectedObject();
		if(gameObjectSelected != null) {
			Point mousePos = this.getMousePosition();
			if(mousePos != null) {
				sprite = gameObjectSelected.getCurrentSprite();
				g.drawImage(sprite.getImage(spriteScale),
						(int) (   ((int)((mousePos.x / pxPerTile) + 0.5) - (sprite.getWidth()/2d)) * pxPerTile   ), //TODO cameraposition mit einberechnen
						(int) (   ((int)((mousePos.y / pxPerTile) + 0.5) - (sprite.getHeight()/2d)) * pxPerTile   ),
						(int)     (sprite.getWidth() * pxPerTile),
						(int)     (sprite.getHeight() * pxPerTile),
						null
				);
				
			}
		}		
	}
	
	private KeyRegister cacheKeyRegister = KeyRegister.getKeyRegister();	
	public void processMouseHover() {
		//place object
		if(this.mouseLeftButtonBeingPressed) {
			Point mousePos = this.getMousePosition();
			if(mousePos != null) {
				GameObject gameObjectSelected = leveleditor.getSelectedObject();
				if(gameObjectSelected == null)
					return;
				
				double pxPerTile = spriteScale * DrawComp.SPRITE_SIZE_PX_ORIGINAL;
				int x = (int) ((mousePos.x / pxPerTile) + 0.5);
				int y = (int) ((mousePos.y / pxPerTile) + 0.5);
				
				//check if there is already a GameObject at the same Position (hold CONTROL to circumvent that)
				boolean collision = false;
				if(!cacheKeyRegister.isKeyDown(KeyEvent.VK_CONTROL)) {
					ArrayList<GameObject> gameObjects = Engine.getSceneActive().getGameObjects();
					for(GameObject gameObjectScene : gameObjects) {
						if(gameObjectScene.getX() == x && gameObjectScene.getY() == y) {
							collision = true;
							break;
						}
					}
				}
				
				if(!collision) {
					GameObject gameObjectNew = gameObjectSelected.getOtherInstance();
					gameObjectNew.setX(x); //TODO Camera position miteinberechnen
					gameObjectNew.setY(y); //TODO Camera position miteinberechnen
					
					Engine.getSceneActive().addGameObject(gameObjectNew);
					System.out.println("placed");
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {}

	private boolean mouseLeftButtonBeingPressed;
	public void mousePressed(MouseEvent e) {
		mouseLeftButtonBeingPressed = true;
		
	}

	public void mouseReleased(MouseEvent e) {
		mouseLeftButtonBeingPressed = false;
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {
		mouseLeftButtonBeingPressed = false;
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation() < 0) {			
			if(this.spriteScale >= 1)
				this.spriteScale++;
			else
				this.spriteScale *= 1.25;
		} else {
			if(this.spriteScale > 1)
				this.spriteScale--;
			else
				this.spriteScale *= 0.8;
		}
		
		System.out.println(this.spriteScale);
	}
}
