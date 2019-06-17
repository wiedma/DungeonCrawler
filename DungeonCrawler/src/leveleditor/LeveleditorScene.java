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

import engine.Scene;
import engine.gameobjects.GameObject;
import engine.sprites.Sprite;
import engine.window.DrawComp;
import engine.window.KeyRegister;

public class LeveleditorScene extends JComponent implements MouseListener, MouseWheelListener {
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -8891639164169984471L;

	/**
	 * states the 'zoom level' of sprites that are currently being displayed<br>
	 * if the sprite on the screen is double its actual size in the spritesheet file, the value of this would be 2
	 */	
	private double spriteScale = 1;
	
	private Leveleditor leveleditor;
	
	private int cameraPosXPx, cameraPosYPx;
	private final int CAMERA_MOVEMENT_SPEED = 5;
	
	public LeveleditorScene(Leveleditor leveleditor) {
		this.leveleditor = leveleditor;
		this.addMouseListener(this);
		this.addMouseWheelListener(this);		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int cameraPosXPx = this.cameraPosXPx;
		int cameraPosYPx = this.cameraPosYPx;
		
		Scene scene = this.leveleditor.getSceneActive();
		double pxPerTile = spriteScale * DrawComp.SPRITE_SIZE_PX_ORIGINAL;
		
		
		Sprite sprite;
		for(GameObject gameObject : scene.getGameObjects()) {
			sprite = gameObject.getCurrentSprite();
			
			g.drawImage(sprite.getImage(spriteScale),
					(int) (   (gameObject.getX() - (sprite.getWidth()/2d)) * pxPerTile   ) - cameraPosXPx,
					(int) (   (gameObject.getY() - (sprite.getHeight()/2d)) * pxPerTile   ) - cameraPosYPx,
//					(int)     (sprite.getWidth() * pxPerTile),
//					(int)     (sprite.getHeight() * pxPerTile),
					null
			);
		}
		
		
		//if the user has selected a GameObject and is hovering over the scene, draw the GameObject as if it was placed a the mouses current position
		GameObject gameObjectSelected = leveleditor.getSelectedObject();
		if(gameObjectSelected != null) {
//			long duration = System.nanoTime();
			Point mousePos = this.getMousePosition();
//			if((System.nanoTime() - duration) > 10000000) {
//				System.out.println("WOOP MORE THAN 10 MILLIS");
//			}
//			System.out.println(System.nanoTime() - duration);
			if(mousePos != null) {
				sprite = gameObjectSelected.getCurrentSprite();
				g.drawImage(sprite.getImage(spriteScale),
						(int) (   ((int)(((mousePos.x+cameraPosXPx) / pxPerTile) + 0.5) - (sprite.getWidth()/2d)) * pxPerTile - cameraPosXPx  ),
						(int) (   ((int)(((mousePos.y+cameraPosYPx) / pxPerTile) + 0.5) - (sprite.getHeight()/2d)) * pxPerTile - cameraPosYPx  ),
//						(int)     (sprite.getWidth() * pxPerTile),
//						(int)     (sprite.getHeight() * pxPerTile),
						null
				);
				
			}
		}		
	}
	
	private KeyRegister cacheKeyRegister = KeyRegister.getKeyRegister();	
	public void processMouseInteractions() {
		//place object
		if(this.mouseLeftButtonBeingPressed) {
			Point mousePos = this.getMousePosition();
			if(mousePos != null) {
				GameObject gameObjectSelected = leveleditor.getSelectedObject();
				if(gameObjectSelected == null)
					return;
				
				double pxPerTile = spriteScale * DrawComp.SPRITE_SIZE_PX_ORIGINAL;
				int x = (int) (((mousePos.x+cameraPosXPx) / pxPerTile) + 0.5);
				int y = (int) (((mousePos.y+cameraPosYPx) / pxPerTile) + 0.5);
				
				//check if there is already a GameObject at the same Position (hold CONTROL to circumvent that)
				boolean collision = false;
				if(!cacheKeyRegister.isKeyDown(KeyEvent.VK_CONTROL)) {
					ArrayList<GameObject> gameObjects = this.leveleditor.getSceneActive().getGameObjects();
					for(GameObject gameObjectScene : gameObjects) {
						if(gameObjectScene.getX() == x && gameObjectScene.getY() == y) {
							collision = true;
							break;
						}
					}
				}
				
				if(!collision) {
					GameObject gameObjectNew = gameObjectSelected.getOtherInstance();
					gameObjectNew.setX(x);
					gameObjectNew.setY(y);
					
					this.leveleditor.getSceneActive().addGameObject(gameObjectNew);
//					System.out.println("placed");
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
			if(spriteScale >= 1)
				spriteScale += 0.5;
			else
				spriteScale += 0.1;
		} else {
			if(spriteScale > 1)
				spriteScale -= 0.5;
			else
				spriteScale = Math.max(0.1, spriteScale - 0.1);
		}
		
		
		//round number
		if(spriteScale > 1) {
			spriteScale = ((int) ((spriteScale*2) + 0.01)) /2d;
		} else {
			spriteScale = ((int) ((spriteScale*10) + 0.01)) /10d;
		}
		
		System.out.println(spriteScale);
	}
	
	public void moveCamera(int xDirection, int yDirection) {
		this.cameraPosXPx += xDirection * CAMERA_MOVEMENT_SPEED;
		this.cameraPosYPx += yDirection * CAMERA_MOVEMENT_SPEED;
	}
	
	
	//merge fragments:
//	/**
//	 * <p>Cache-variable. This indicates whether the mouse was over this component the last time the method was called.</p>
//	 * <p>It is used when determining whether the selected GameObject of the {@link LeveleditorObjectChooser} should be added or deleted to the {@link Scene}</p>
//	 */
//	private boolean mouseOnLastFrame = false;
//	
//	public void processMouseHover() {
//		draggedObject = objectChooser.getSelectedGameObject();				
//		if(draggedObject == null) {
//			mouseOnLastFrame = this.getMousePosition() != null;
//			return;
//		}
//		
//		Point mousePos = this.getMousePosition();
//		if(mousePos == null) {
//			if(mouseOnLastFrame) {
//				scene.removeGameObject(draggedObject);
//				System.out.println("Removed GameObject " + draggedObject.getClass().toString() + " from Scene!");
//			}
//			mouseOnLastFrame = false;
//			return;
//		}
//		
//		draggedObject.setX(mousePos.getX());
//		draggedObject.setY(mousePos.getY());
//		
//		if(!mouseOnLastFrame) {
//			scene.addGameObject(draggedObject);
//			System.out.println("Added GameObject " + draggedObject.getClass().toString() + " to Scene!");
//		}
//		
//		this.mouseOnLastFrame = true;
//	}
}
