package animationeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import javax.swing.JComponent;

import engine.sprites.Sprite;
import engine.sprites.Spritesheet;
import engine.window.DrawComp;

public class SpritesheetSelector extends JComponent implements MouseListener, MouseWheelListener {
	
	private double pxPerTile = 16;
	private double cameraFocusPosXTiles = 0;
	private double cameraFocusPosYTiles = 0;
	
	private Animationeditor animationeditor;
	
	private Spritesheet spritesheet;
	
	private boolean mouseDragging = false;
	private double mouseDragClickXTiles;
	private double mouseDragClickYTiles;
	
	private boolean mouseSelecting = false;
	private int mouseClickSelectionStartXTiles;
	private int mouseClickSelectionStartYTiles;
	private int mouseClickSelectionEndXTiles;
	private int mouseClickSelectionEndYTiles;
	private boolean mouseClickSelectionExists = false;
	
	public SpritesheetSelector(Animationeditor animationeditor, Spritesheet spritesheet) throws FileNotFoundException {
		if(spritesheet.getImage() == null)
			throw new FileNotFoundException();
		this.spritesheet = spritesheet;
		
		//center spritesheet
		cameraFocusPosXTiles = (spritesheet.getImage().getWidth()/DrawComp.SPRITE_SIZE_PX_ORIGINAL) / 2d;
		cameraFocusPosYTiles = (spritesheet.getImage().getHeight()/DrawComp.SPRITE_SIZE_PX_ORIGINAL) / 2d;
		
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.setFocusable(true);
		
		SpritesheetSelector thisSpritesheetSelector = this;
		(new Thread() {
			public void run() {
				super.run();
				try {
					Thread.sleep(100);
				}catch(Exception e) {}
				while(true) {
					// stop condintion for this thread: If the SpriteSheetChooser doesn't contain this SpritesheetChooser anymore, stop this Thread,
					// so that the Java Garbage collector can clear the SpriteSheetChooser up 
					if(!animationeditor.isSpritesheetChooserStillLoaded(thisSpritesheetSelector))
						break;
					
					if(mouseSelecting) {
						mouseClickSelectionEndXTiles = (int) getPosTilesX(getMousePosition().x);
						mouseClickSelectionEndYTiles = (int) getPosTilesY(getMousePosition().y);
					}
					
					if(mouseDragging || mouseSelecting)
						repaint();
					
					try {
						Thread.sleep(50);
					}catch(Exception e) {}
				}
			}
		}).start();
	}
	
	public void paintComponent(Graphics g) {
		BufferedImage image = spritesheet.getImage();
		
		g.drawImage(
				image,
				(int) ((this.getWidth()/2) - ((this.cameraFocusPosXTiles - (!mouseDragging ? 0 : getPosTilesX(getMousePosition().x) - mouseDragClickXTiles))*pxPerTile)),
				(int) ((this.getHeight()/2) - ((this.cameraFocusPosYTiles - (!mouseDragging ? 0 : getPosTilesY(getMousePosition().y) - mouseDragClickYTiles))*pxPerTile)),
				(int) (image.getWidth()*(this.pxPerTile/DrawComp.SPRITE_SIZE_PX_ORIGINAL)),
				(int) (image.getHeight()*(this.pxPerTile/DrawComp.SPRITE_SIZE_PX_ORIGINAL)),
				null
		);
		
		//highlight the part that is currently being selected
		if(this.mouseClickSelectionExists) {
			g.setColor(Color.red);
			g.drawRect(				
					(int) ((this.getWidth()/2) - ((
							this.cameraFocusPosXTiles - (!mouseDragging ? 0 : getPosTilesX(getMousePosition().x) - mouseDragClickXTiles)
							- Math.min(this.mouseClickSelectionStartXTiles, this.mouseClickSelectionEndXTiles)
							
					)*pxPerTile)),
					
					(int) ((this.getHeight()/2) - ((
							this.cameraFocusPosYTiles - (!mouseDragging ? 0 : getPosTilesY(getMousePosition().y) - mouseDragClickYTiles)
							- Math.min(this.mouseClickSelectionStartYTiles, this.mouseClickSelectionEndYTiles)
					)*pxPerTile)),
					
					(int) ((Math.abs(this.mouseClickSelectionStartXTiles - this.mouseClickSelectionEndXTiles)+1)*(this.pxPerTile)),
					
					(int) ((Math.abs(this.mouseClickSelectionStartYTiles - this.mouseClickSelectionEndYTiles)+1)*(this.pxPerTile))
			);
		}
	}

	public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON2) {
			mouseDragging = true;
			mouseDragClickXTiles = getPosTilesX(e.getPoint().x);
			mouseDragClickYTiles = getPosTilesY(e.getPoint().y);
			
		} else if(e.getButton() == MouseEvent.BUTTON1) {
			this.mouseClickSelectionExists = true;
			mouseSelecting = true;
			mouseClickSelectionStartXTiles = (int) getPosTilesX(e.getX());
			mouseClickSelectionStartYTiles = (int) getPosTilesY(e.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON2) {
			mouseDragging = false;
			cameraFocusPosXTiles -= getPosTilesX(e.getPoint().x) - mouseDragClickXTiles;
			cameraFocusPosYTiles -= getPosTilesY(e.getPoint().y) - mouseDragClickYTiles;
			repaint();
			
		} else if(e.getButton() == MouseEvent.BUTTON1) {
			mouseSelecting = false;
			mouseClickSelectionEndXTiles = (int) getPosTilesX(e.getX());
			mouseClickSelectionEndYTiles = (int) getPosTilesY(e.getY());
			repaint();
		}
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseWheelMoved(MouseWheelEvent e) {
		double mousePosXTiles = getPosTilesX(e.getX());
		double mousePosYTiles = getPosTilesY(e.getY());
		
		if(e.getWheelRotation() < 0) {
			pxPerTile += DrawComp.SPRITE_SIZE_PX_ORIGINAL/4;
		}else {
			pxPerTile -= DrawComp.SPRITE_SIZE_PX_ORIGINAL/4;
		}
		
		//reposition camera, so that the mouseposition (in Tiles) doesn't change throughout the zoom
		this.cameraFocusPosXTiles -= getPosTilesX(e.getX()) - mousePosXTiles;
		this.cameraFocusPosYTiles -= getPosTilesY(e.getY()) - mousePosYTiles;
		
		repaint();
	}
	
	private double getPosTilesX(int posPxX) {
		return ((posPxX - (this.getWidth()/2)) / this.pxPerTile) + this.cameraFocusPosXTiles;
	}
	
	private double getPosTilesY(int posPxY) {
		return ((posPxY - (this.getHeight()/2)) / this.pxPerTile) + this.cameraFocusPosYTiles;
	}
	
	public Sprite extractSelectedSprite() {
		return new Sprite(
					Math.min(mouseClickSelectionStartXTiles, mouseClickSelectionEndXTiles),
					Math.min(mouseClickSelectionStartYTiles, mouseClickSelectionEndYTiles),
					Math.abs(this.mouseClickSelectionStartXTiles - this.mouseClickSelectionEndXTiles)+1,
					Math.abs(this.mouseClickSelectionStartYTiles - this.mouseClickSelectionEndYTiles)+1,
					this.spritesheet
		);
	}
	
}
