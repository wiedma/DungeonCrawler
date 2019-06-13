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
	 * the spriteScale of {@link Spritesheet#imageScaled}<br>
	 * see {@linkplain DrawComp#getSpriteScale()} for further reference
	 */
	private double spriteScale;
	
	/**
	 * the path of this spritesheets File
	 */
	private String sheetFilePath;
	
	/**
	 * the scaled version of the entire spritesheet, basically the spritesheet file but scaled up
	 */
	private BufferedImage imageScaled;
	
	public Spritesheet(File sheetFile) {
		this.sheetFilePath = sheetFile.getAbsolutePath();
		loadSpritesheetFromFile(sheetFile);
	}
	
	/**
	 * loads the given spritesheet file into a matrix of Sprites, each one scaled up to the spriteScale {@link DrawComp#getSpriteSizeScaled()}
	 * @param sheetFile path to the file the spritesheet should be loaded from
	 */
	private void loadSpritesheetFromFile(File sheetFile) {
		
		this.spriteScale = DrawComp.getSpriteScale();
		
		try {
			BufferedImage imageUnscaled = ImageIO.read(sheetFile);
			
			//scale this picture, and at the same time get a BufferedImage out of it			
			BufferedImageOp op = new AffineTransformOp(
					AffineTransform.getScaleInstance(spriteScale, spriteScale),
					new RenderingHints(RenderingHints.KEY_INTERPOLATION,
							RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
					)
			);
			this.imageScaled = op.filter(imageUnscaled, null);
			
//			this.imageScaled = ((ToolkitImage) imageUnscaled.getScaledInstance((int) (imageUnscaled.getWidth(null) * this.spriteScale), (int) (imageUnscaled.getHeight(null) * this.spriteScale), Image.SCALE_DEFAULT)).getBufferedImage();			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image extractSprite(int xTiles, int yTiles, int widthTiles, int heightTiles) {
		checkScale();
		
		int imageScaledSize = (int) (DrawComp.SPRITE_SIZE_PX_ORIGINAL * DrawComp.getSpriteScale());
					
		return this.imageScaled.getSubimage(xTiles * imageScaledSize, yTiles * imageScaledSize, widthTiles * imageScaledSize, heightTiles * imageScaledSize);
	}
	
	public void checkScale() {
		if(this.spriteScale == DrawComp.getSpriteScale())
			return;
		
		this.loadSpritesheetFromFile(new File(this.sheetFilePath));
	}
}