package engine.sprites;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.window.DrawComp;

public class Spritesheet {
	
	/**
	 * a matrix of Sprites as depicted in the SpriteSheet Image File.
	 * <br> [xDimension][yDimension]
	 */
	private Sprite[][] spritesheet;
	
	/**
	 * the spriteScale that these sprite have<br>
	 * see {@linkplain DrawComp#getSpriteScale()} for further reference
	 */
	private double spriteScale;
	
	private String sheetFilePath;
	
	public Spritesheet(File sheetFile) {
		this.sheetFilePath = sheetFile.getAbsolutePath();
		loadSpritesheetFromFile(sheetFile);
	}
	
	/**
	 * loads the given spritesheet file into a matrix of Sprites, each one scaled up to the spriteScale {@link DrawComp#getSpriteSizeScaled()}
	 * @param sheetFile
	 */
	private void loadSpritesheetFromFile(File sheetFile) {
		this.spriteScale = DrawComp.getSpriteScale();
		int spriteSizeOriginal = DrawComp.SPRITE_SIZE_PX_ORIGINAL;
		
		try {
			BufferedImage img = ImageIO.read(sheetFile);
			
			spritesheet = new Sprite[(int) (img.getWidth()/DrawComp.SPRITE_SIZE_PX_ORIGINAL)][(int) (img.getHeight()/DrawComp.SPRITE_SIZE_PX_ORIGINAL)];
			
			for(int x = 0; x < spritesheet.length; x++) {
				for(int y = 0; y < spritesheet[x].length; y++) {					
					spritesheet[x][y] = new Sprite(							
							img.getSubimage(x*spriteSizeOriginal, y*spriteSizeOriginal, spriteSizeOriginal, spriteSizeOriginal).getScaledInstance((int) (spriteSizeOriginal * this.spriteScale), (int) (spriteSizeOriginal * this.spriteScale), Image.SCALE_DEFAULT)
					);
				}	
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Sprite[][] getSpriteMatrix(){
		return this.spritesheet;
	}
	
	/**
	 * this will reload the entire spritesheet and scale all sprites with the spriteScale given at {@link DrawComp#getSpriteScale()}}
	 */
	public void reload() {
		this.loadSpritesheetFromFile(new File(this.sheetFilePath));
	}
	
	public double getSpriteScale() {
		return this.spriteScale;
	}
}
