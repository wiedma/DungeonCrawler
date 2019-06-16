package leveleditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.Timer;

import engine.gameobjects.GameObject;
import engine.sprites.Sprite;
import engine.window.DrawComp;

/**
 * <p>This class is a JComponent that is added to the main Leveleditor. Its purpose is to show the user a pallet of {@link GameObject GameObjects} as specified in <it>Leveleditor.fillObjectChooser(..)</it>.
 * It is seperated from the main Scenes' DrawComp through a {@link JSplitPane}. While the user drags that SplitPanes Split Element, this Component will ensure that all objects
 * are visible to the user at all times in a neat way. By setting {@link LeveleditorObjectChooser#setContinuousResort(boolean) setContinuousResort(boolean)} to false, this component will only process the
 * heavy sorting algorithm after the user finishes the resizing process. This ensures lower CPU cost. For further reference, see the arguments documentation as linked above.</p>
 * @author Marco, Daniel
 */
public class LeveleditorObjectChooser extends JComponent implements ComponentListener, MouseListener {	
	private static final long serialVersionUID = 6730035525378342464L;

	/**
	 * ArrayList containing all GameObjects displayed in this GameObjectChooser. Their positions will be treated as their top-left position, their width in the chooser will be their sprite's width
	 */
	private ArrayList<GameObject> gameObjectsSorted;
	
	/**
	 * specifes how many Tiles / Sprites fit into this component horizontally at this moment
	 */
	private int widthTiles;
		
	/**
	 * <p>this specifies if, while this component is being resized, the Objects in the GameObjectChooser should be resorted continuously or only once after the resort is finished.
	 * Setting this to false lowers the CPU-Load during resizing</p>
	 */
	private boolean continuousResort = true;
	
	/**
	 * <p>this is only used, when {@link LeveleditorObjectChooser#continuousResort continuousResort} is equal to false, meaning continuous resort is deactivated</p>
	 * <p>this timer will be started whenever the user resizes this component. The timer will be reset any time this component gets resized.<br>
	 * If the timer finishes, the user must have finished resizing the component and all GameObjects can be resorted to fit nicely into the GameObjectChooser, because if
	 * the timer was still running, the user is still in the means of resizing it</p>
	 * <p>(only doing this after the user finished resizing ensures a lag-free resizing process, without having to resort all GameObjects hundreds of times during the process)</p> 
	 */
	private Timer timerResort;
	
	/**
	 * the {@link GameObject} that is currently being hovered over by the user/mouse
	 */
	private GameObject mouseHoverGameObject;	
	private final Color colorObjectChooserHover = new Color(1f, 0f, 0f, 0.3f);
	
	/**
	 * the most recent {@link GameObject} that the user has selected(clicked on)
	 */
	private GameObject selectedGameObject;
	private final Color colorSelectedGameObject = new Color(1f, 0f, 0f);
	
	/**
	 * The scale with which the {@link GameObject objects} in the ObjectChooser are drawn
	 */
	private double drawScale = 2;
	
	
	public LeveleditorObjectChooser(int widthTiles) {
		this.gameObjectsSorted = new ArrayList<GameObject>();
		this.widthTiles = widthTiles;
		
		this.addComponentListener(this);
		this.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		double pxPerTile = getPxPerTile();
		
		Sprite sprite;		
		for(GameObject gameObject : gameObjectsSorted) {
			sprite = gameObject.getCurrentSprite();
			g.drawImage(
					sprite.getImage(drawScale),
					(int)	(gameObject.getX() * pxPerTile),
					(int)	(gameObject.getY() * pxPerTile),
					(int)   (sprite.getWidth() * pxPerTile),
					(int)   (sprite.getHeight() * pxPerTile),
					null
			);			
		}
		
		//paint the Object that the mouse is hovering over
		GameObject gameObjectHover = this.mouseHoverGameObject;
		if(gameObjectHover != null) {
			sprite = gameObjectHover.getCurrentSprite();
			g.setColor(colorObjectChooserHover);
			g.fillRect(
					(int)	(gameObjectHover.getX() * pxPerTile),
					(int)	(gameObjectHover.getY() * pxPerTile),
					(int)   (sprite.getWidth() * pxPerTile),
					(int)   (sprite.getHeight() * pxPerTile)
			);
		}
		
		//draw rect around Object that has been selected
		GameObject gameObjectSelected = this.selectedGameObject;
		if(gameObjectSelected != null) {
			sprite = gameObjectSelected.getCurrentSprite();
			g.setColor(colorSelectedGameObject);
			g.drawRect(
					(int)	(gameObjectSelected.getX() * pxPerTile),
					(int)	(gameObjectSelected.getY() * pxPerTile),
					(int)   (sprite.getWidth() * pxPerTile),
					(int)   (sprite.getHeight() * pxPerTile)
			);
		}
	}
	
	/**
	 * Adds and sorts any number GameObjects into this ObjectChoosers list of sorted GameObjects and places it somewhere it fits
	 * @param gameObjects The GameObjects to sort in
	 */
	public void addGameObjects(GameObject... gameObjects) {
		for(GameObject gameObject : gameObjects) {
			sortObject(gameObject);
		}
	}
	
	/**
	 * sorts a GameObject into this ObjectChoosers list of sorted GameObjects and places it somewhere it fits 
	 * @param gameObject The GameObject to sort in
	 */
	private void sortObject(GameObject gameObject) {
		boolean collision;
		for(int y = 0; ; y++) {
			for(int x = 0; x < this.widthTiles; x++) {
				
				//check for collision with all sorted gameObjects gameObject
				collision = false;
				for(GameObject gameObjectSorted : gameObjectsSorted) {
//					if(		Math.pow(gameObject.getX() - gameObjectSorted.getX(), 2) < (gameObject.getCurrentSprite().getWidth() + gameObjectSorted.getCurrentSprite().getWidth()) / 2
//						&&	Math.pow(gameObject.getY() - gameObjectSorted.getY(), 2) < (gameObject.getCurrentSprite().getHeight() + gameObjectSorted.getCurrentSprite().getHeight()) / 2
//					) {
					if(		x < gameObjectSorted.getX() + gameObjectSorted.getCurrentSprite().getWidth()
						&&	x + gameObject.getCurrentSprite().getWidth() > gameObjectSorted.getX()
						&&	y < gameObjectSorted.getY() + gameObjectSorted.getCurrentSprite().getHeight()
						&&	y + gameObject.getCurrentSprite().getHeight() > gameObjectSorted.getY()
					) {
						collision = true;
						break;
					}
				}
				if(collision)
					continue;
				
				//if this gameObject is wider than the entire GameObjectChooser it should be placed on the very left side => x == 0
				if(gameObject.getCurrentSprite().getWidth() > this.widthTiles) {
					if(x != 0)
						continue;
				} else {
					//if it would fit into the entire width of the GameObjectChooser, choose a position where it doesnt collide with the right side (go beyond the right side)
					if(x + gameObject.getCurrentSprite().getWidth() > this.widthTiles)
						continue;
				}
					
				
				
				//set this position
				gameObject.setX(x);
				gameObject.setY(y);
				gameObjectsSorted.add(gameObject);
				
				return;
			}
		}
	}
	
	private void updateWidthTiles(boolean resortAfterThis) {
		
		this.widthTiles = Math.max(1, (int) (this.getWidth() / (drawScale * DrawComp.SPRITE_SIZE_PX_ORIGINAL)));
		
		if(resortAfterThis)
			resortAll();
	}
	
	/**
	 * Takes all GameObjects out of the {@link LeveleditorObjectChooser#gameObjectsSorted} (gameObjectsSorted ArrayList) and resorts them back in.
	 * This will be called after this component finished it's resizing (for further reference see:{@link LeveleditorObjectChooser#continuousResort})
	 */
	private void resortAll() {
		//cache all gameObjects
		GameObject[] gameObjectsSortedOLD = gameObjectsSorted.toArray(new GameObject[0]);
		//empty current GameObjectList
		this.gameObjectsSorted.clear();
		
		//add all GameObjects to the empty 'sorted' list
		this.addGameObjects(gameObjectsSortedOLD);
	}
	
	/**
	 * cache variable: If the current mouse position coincides with this position (and it's y component) the currently being hovered over {@link LeveleditorObjectChooser#mouseHoverGameObject} (GameObject) 
	 * doesn't need to be recalculated again
	 */
	private int mousePosXLastFrame = -1;
	/**
	 * cache variable: If the current mouse position coincides with this position (and it's x component) the currently being hovered over {@link LeveleditorObjectChooser#mouseHoverGameObject} (GameObject) 
	 * doesn't need to be recalculated again
	 */
	private int mousePosYLastFrame = -1;
	/**
	 * called every frame, checks if mouse is hovering over this component. If it is, it gets the Objects the mouse is hovering on and stores it in {@link LeveleditorObjectChooser#mouseHoverGameObject}
	 */
	public void processMouseHover() {
		Point mousePos = this.getMousePosition();
		if(mousePos == null) {
			this.mouseHoverGameObject = null;
			return;
		}
		
		//check if the mouse didn't move at all
		if(mousePos.x == this.mousePosXLastFrame && mousePos.y == this.mousePosYLastFrame) {
			return;
		} else {			
			this.mouseHoverGameObject = getGameObjectAtPosPx(mousePos);
			this.mousePosXLastFrame = mousePos.x;
			this.mousePosYLastFrame = mousePos.y;			
		}
	}
	
	/**
	 * @param posPx The mouse position relative to this component in pixel
	 * @return returns the GameObject the Mouse is hovering over, or null
	 */
	private GameObject getGameObjectAtPosPx(Point posPx) {
		double pxPerTile = getPxPerTile();
		
		for(GameObject gameObject : this.gameObjectsSorted) {
			
			if(		posPx.x / pxPerTile >= gameObject.getX()
				&&	posPx.x / pxPerTile <= gameObject.getX() + gameObject.getCurrentSprite().getWidth()
				&&	posPx.y / pxPerTile >= gameObject.getY()
				&&	posPx.y / pxPerTile <= gameObject.getY() + gameObject.getCurrentSprite().getHeight()
			) {
				return gameObject;
			}
		}
		return null;
	}

	public void componentResized(ComponentEvent e) {
		if(this.continuousResort == false) {
			//only resort once after timer finishes
			if(this.timerResort == null) {
				//there was no timer running, so a new 'resizing session' is going to begin
				this.timerResort = new Timer(250, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// stop / delete timer
						timerResort.stop();
						timerResort = null;
						
						//this will be executed on resize finish
						updateWidthTiles(true);					
					}
				});
				this.timerResort.start();
			} else {
				//the timer was still running, so reset it
				this.timerResort.restart();
			}
		} else {
			//resort completely on every resize event
			updateWidthTiles(true);
		}
	}

	public void componentMoved(ComponentEvent e) {}

	public void componentShown(ComponentEvent e) {}

	public void componentHidden(ComponentEvent e) {}

	public void mouseClicked(MouseEvent e) {
		this.selectedGameObject = getGameObjectAtPosPx(e.getPoint());
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
	
	/////////////////
	///////////////// SETTERS
	/////////////////
	
	public void setContinuousResort(boolean continuousResort) {
		this.continuousResort = continuousResort;
	}	
	
	/////////////////	
	///////////////// GETTERS
	/////////////////
	
	private double getPxPerTile() {
		return drawScale * DrawComp.SPRITE_SIZE_PX_ORIGINAL; 
	}

	public GameObject getSelectedGameObject() {
		return selectedGameObject;
	}
	
	public GameObject getSelectedObject() {
		return this.selectedGameObject;
	}
	
	
}
