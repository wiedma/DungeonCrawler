package engine.sprites;

import java.awt.Image;

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
	
	public Image getImage(double scale) {
		if(scale == currentScale) {
			return this.image;			
		}
		else {
			reload(scale);
			return this.image;
		}
	}
	
	/**
	 * Reloads the image to match the new scale
	 * @param scale new scale of the image
	 */
	public void reload(double scale) {
		//TODO: reload the image to match the new scale
	}
	
}
