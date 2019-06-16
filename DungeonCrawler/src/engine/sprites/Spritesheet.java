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

public class Spritesheet {
	
	/**
	 * the spriteScale of {@link Spritesheet#image}<br>
	 * see {@linkplain DrawComp#getSpriteScale()} for further reference
	 */
//	private double spriteScale;
	
	/**
	 * the path of this spritesheets File
	 */
//	private String sheetFilePath;
	
	/**
	 * the scaled version of the entire spritesheet, basically the spritesheet file but scaled up
	 */
	private BufferedImage image;
	
	public Spritesheet(File sheetFile) {
//		this.sheetFilePath = sheetFile.getAbsolutePath();
		loadSpritesheetFromFile(sheetFile);
	}
	
	/**
	 * loads the given spritesheet file into a matrix of Sprites, each one scaled up to the spriteScale {@link DrawComp#getSpriteSizeScaled()}
	 * @param sheetFile path to the file the spritesheet should be loaded from
	 */
	private void loadSpritesheetFromFile(File sheetFile) {
		
		try {
			this.image = ImageIO.read(sheetFile);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image extractSprite(int xTiles, int yTiles, int widthTiles, int heightTiles, double scale) {
//		checkScale(scale);
		
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
	
//	public void checkScale(double scale) {
//		if(this.spriteScale == scale)
//			return;
//		
//		this.loadSpritesheetFromFile(new File(this.sheetFilePath));
//	}
}