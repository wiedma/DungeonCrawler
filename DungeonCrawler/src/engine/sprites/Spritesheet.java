package engine.sprites;

import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.window.DrawComp;

public class Spritesheet{
	

	public static final String DIR_SPRITESHEETS = "res/spritesheets/"; 
	
	/**
	 * the scaled version of the entire spritesheet, basically the spritesheet file but scaled up
	 */
	private transient BufferedImage image;
	
	/**
	 * width of the original sprite sheet file in Tiles
	 */
	private transient int widthTiles; 
	
	/**
	 * height of the original sprite sheet file in Tiles
	 */
	private transient int heightTiles;
	
	private String filePath;
	
	public Spritesheet(File sheetFile) {
		loadSpritesheetFromFile(sheetFile);
		filePath = DIR_SPRITESHEETS + sheetFile.getName();
	}
	
	/**
	 * loads the given spritesheet file into a matrix of Sprites, each one scaled up to the spriteScale {@link DrawComp#getSpriteSizeScaled()}
	 * @param sheetFile path to the file the spritesheet should be loaded from
	 */
	private void loadSpritesheetFromFile(File sheetFile) {
		
		try {
			this.image = ImageIO.read(sheetFile);
			this.widthTiles = this.image.getWidth() / DrawComp.SPRITE_SIZE_PX_ORIGINAL;
			this.heightTiles = this.image.getHeight() / DrawComp.SPRITE_SIZE_PX_ORIGINAL;
			
		} catch(IOException e) {
			System.err.print("ERROR while trying to load spritesheet at '" + sheetFile.getAbsolutePath() + "'\n\n"
					+ "not found:    " + sheetFile.getName() + "\n"
					+ "available: "
			);			
			File[] files = sheetFile.getParentFile().listFiles();
			for(int f = 0; f < files.length; f++)
				System.err.println((f == 0 ? "" : "           ") + "|- " + files[f].getName());	
			
			System.err.println("\n");
			
			e.printStackTrace();
		}
	}
	
	/**
	 * this Method extracts a fraction of the original Spritesheet as it is stored in {@link Spritesheet#image} and scales it up to the specified scale
	 * @param xTiles top left x position of sprite on the spritesheet (counting starts at 0), in Tiles (pixel / {@link DrawComp#SPRITE_SIZE_PX_ORIGINAL})
	 * @param yTiles top left y position of sprite on the spritesheet (counting starts at 0), in Tiles (pixel / {@link DrawComp#SPRITE_SIZE_PX_ORIGINAL})
	 * @param widthTiles width of sprite on the spritesheet, in Tiles (pixel / {@link DrawComp#SPRITE_SIZE_PX_ORIGINAL})
	 * @param heightTiles height of sprite on the spritesheet, in Tiles (pixel / {@link DrawComp#SPRITE_SIZE_PX_ORIGINAL})
	 * @param scale the scale to which the sprite should be upscaled to
	 */
	public Image extractSprite(int xTiles, int yTiles, int widthTiles, int heightTiles, double scale) {
		if(this.image == null || xTiles < 0 || yTiles < 0 || xTiles + widthTiles > this.widthTiles || yTiles + heightTiles > this.heightTiles)
			return null;
		
		//scale this picture, and at the same time get a BufferedImage out of it			
		BufferedImageOp op = new AffineTransformOp(
				AffineTransform.getScaleInstance(scale, scale),
				new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION,
						RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED
				)
		);
		return op.filter(this.image.getSubimage(xTiles * DrawComp.SPRITE_SIZE_PX_ORIGINAL, yTiles * DrawComp.SPRITE_SIZE_PX_ORIGINAL,
												widthTiles * DrawComp.SPRITE_SIZE_PX_ORIGINAL, heightTiles * DrawComp.SPRITE_SIZE_PX_ORIGINAL), null);
					
	}
	
	public String getFilePath() {
		return this.filePath;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public int getWidthTiles() {
		return this.widthTiles;
	}
	
	public int getHeightTiles() {
		return this.heightTiles;
	}
}