package leveleditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import engine.Scene;
import engine.hitbox.Hitbox;
import engine.sprites.Sprite;
import engine.window.DrawComp;
import engine.window.KeyRegister;
import gameobjects.GameObject;

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
	
	private boolean drawHitboxes = false;
	
	public static final Color HITBOX_COLOR = new Color(1f, 0f, 0f, 0.5f);
	
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
			
			Hitbox hitbox;
			if(drawHitboxes && (hitbox = gameObject.getHitbox()) != null) {
				g.setColor(HITBOX_COLOR);
				g.fillRect(
							(int)	((gameObject.getX()+hitbox.getOffsetX()) * pxPerTile) - (int) (hitbox.getWidth() * 0.5 * pxPerTile) - cameraPosXPx,
							(int)	((gameObject.getY()+hitbox.getOffsetY()) * pxPerTile) - (int) (hitbox.getHeight() * 0.5 * pxPerTile) -  cameraPosYPx,
							(int)   (hitbox.getWidth() * pxPerTile),
							(int)   (hitbox.getHeight() * pxPerTile));
			}
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
		
		if(this.mouseLeftButtonBeingPressed) {
			if(cacheKeyRegister.isKeyDown(KeyEvent.VK_SHIFT)) {
				removeObject();
			}
			else {
				placeObject();				
			}
		}
	}
	
	public void removeObject() {
		Point mousePos = this.getMousePosition();
		if(mousePos == null) {
			return;
		}
		double pxPerTile = spriteScale * DrawComp.SPRITE_SIZE_PX_ORIGINAL;
		double x = (((mousePos.getX()+cameraPosXPx) / pxPerTile));
		double y = (((mousePos.getY()+cameraPosYPx) / pxPerTile));
		GameObject collisionObject = null;
		Hitbox hitbox;
		Sprite sprite;
		for(GameObject obj : this.leveleditor.getSceneActive().getGameObjects()) {
			if((hitbox = obj.getHitbox()) != null) {
				collisionObject = hitbox.collidesWith(x, y) ? obj : collisionObject;
			}
			else if((sprite = obj.getCurrentSprite()) != null){
				hitbox = new Hitbox(sprite.getWidth(), sprite.getHeight(), obj);
				collisionObject = hitbox.collidesWith(x, y) ? obj : collisionObject;
			}
		}
		if(collisionObject != null) {
			this.leveleditor.getSceneActive().removeGameObject(collisionObject);			
		}
	}
	
	/**
	 * <p>Places the selected {@link GameObject} on the mouse position.</p>
	 * If no Object is selected this method does nothing.
	 */
	public void placeObject() {
		GameObject gameObjectSelected = leveleditor.getSelectedObject();
		if(gameObjectSelected == null)
			{return;}
		Point mousePos = this.getMousePosition();
		if(mousePos != null) {
			
			double pxPerTile = spriteScale * DrawComp.SPRITE_SIZE_PX_ORIGINAL;
			int x = (int) (((mousePos.x+cameraPosXPx) / pxPerTile) + 0.5);
			int y = (int) (((mousePos.y+cameraPosYPx) / pxPerTile) + 0.5);
			
			//check if there is already a GameObject at the same Position (hold CONTROL to circumvent that)
			boolean collision = false;
			if(!cacheKeyRegister.isKeyDown(KeyEvent.VK_CONTROL)) {
				ArrayList<GameObject> gameObjects = this.leveleditor.getSceneActive().getGameObjects();
				for(GameObject gameObjectScene : gameObjects) {
					if(gameObjectScene.getClass() != gameObjectSelected.getClass())
						continue;					
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

	public void mouseClicked(MouseEvent e) {}

	private boolean mouseLeftButtonBeingPressed;
	public void mousePressed(MouseEvent e) {		
		mouseLeftButtonBeingPressed = true;
		this.requestFocus();
	}

	public void mouseReleased(MouseEvent e) {
		mouseLeftButtonBeingPressed = false;
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {
		mouseLeftButtonBeingPressed = false;
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		
		Point2D.Double mousePositionWorldBeforeZoom = this.getWorldPositionAtMousePosition(this.getMousePosition());
		
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
		
		//Zoom 'towards' the mouse-cursor by shifting the camera after the zoom with MATHZ
		Point2D.Double mousePositionWorldAfterZoom = this.getWorldPositionAtMousePosition(this.getMousePosition());		
		if(mousePositionWorldAfterZoom != null && mousePositionWorldAfterZoom != null) {
			double pixelPerTile = getPixelPerTile();
			this.moveCameraByPixels(
					(int) -(pixelPerTile*(mousePositionWorldAfterZoom.x-mousePositionWorldBeforeZoom.x)),
					(int) -(pixelPerTile*(mousePositionWorldAfterZoom.y-mousePositionWorldBeforeZoom.y))
			);
		}
	}
	
	private Point2D.Double getWorldPositionAtMousePosition(Point mousePosition) {
		Point mousePos = this.getMousePosition();
		if(mousePos == null) {
			return null;
		}
		double pxPerTile = getPixelPerTile();
		return new Point2D.Double((mousePos.getX()+cameraPosXPx) / pxPerTile, (mousePos.getY()+cameraPosYPx) / pxPerTile);
	}
	
	private double getPixelPerTile() {
		return spriteScale * DrawComp.SPRITE_SIZE_PX_ORIGINAL;
	}
	
	public void moveCameraByPixels(int xd, int yd) {
		this.cameraPosXPx += xd;
		this.cameraPosYPx += yd;
	}
	
	public void moveCamera(int xDirection, int yDirection) {
		this.cameraPosXPx += xDirection * CAMERA_MOVEMENT_SPEED;
		this.cameraPosYPx += yDirection * CAMERA_MOVEMENT_SPEED;
	}
	
	public void resetCamera() {
		this.cameraPosXPx = 0;
		this.cameraPosYPx = 0;
	}
	
	public void setDisplayHitboxes(boolean displayHitboxes) {
		this.drawHitboxes = displayHitboxes;
	}
	
}
