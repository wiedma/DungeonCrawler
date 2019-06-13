package engine.sprites;

import java.awt.Image;

import engine.window.DrawComp;

public class Sprite {
	//TODO: implement Sprites
	
	/**
	 * The image this sprite produces
	 */
	private Image image;
	
	/**
	 * this sprites' position on the spritesheet in 16-pixel units
	 */
	private int x, y;
	
	/**
	 * this sprites' dimensions on the spritesheet in 16-pixel units
	 */
	private int width, height;
	
	/**
	 * the scale the image has been loaded with
	 */
	private double currentScale;
	
	/**
	 * the spritesheet this sprite is from
	 */
	private Spritesheet spritesheet;
	
	
	public Sprite(int xpos, int ypos, int width, int height, Spritesheet sheet) {
		this.x = xpos;
		this.y = ypos;
		this.width = width;
		this.height = height;
		this.spritesheet = sheet;
	}
	
	public Sprite(int xpos, int ypos, int width, int height, String sheetPath) {
		this.x = xpos;
		this.y = ypos;
		this.width = width;
		this.height = height;
		this.spritesheet = SpriteLoader.request(sheetPath);
	}
	
	/**
	 * getter function to retrieve the image encapsulated by this Sprite Object
	 * <br> if the given scale {@link DrawComp#getSpriteScale()} does not coincide with the scale of this Sprite (or image), the Sprite Object will gently ask the {@link Spritesheet} to extract a new image from itself (which will then be of correct scale)
	 * @param scale
	 * @return
	 */
	public Image getImage() {
		if(DrawComp.getSpriteScale() == currentScale) {
			return this.image;
		} else {
			//extract image from spritesheet
			return (this.image = spritesheet.extractSprite(x, y, width, height));
		}
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
}
