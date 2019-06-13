package engine.sprites;
import java.io.File;
import java.util.HashMap;

/**
 * Loads sprites and spritesheets from the harddrive and stores them in RAM. This ensures that the number of I/O-operations is kept to a minimum
 * @author Marco, Daniel
 *
 */
public class SpriteLoader {
	
	/**
	 * HashMap contains references all previously loaded spritesheets.
	 */
	private static HashMap<String, Spritesheet> spritesheets = new HashMap<String, Spritesheet>();
	
	public static Spritesheet request(String pathToSpritesheet) {
		File sheetFile = new File(pathToSpritesheet);
		String  absolutePath = sheetFile.getAbsolutePath();
		
		if(spritesheets.containsKey(absolutePath)) {
			return spritesheets.get(absolutePath);
		}
		
		Spritesheet sheet = new Spritesheet(sheetFile);
		spritesheets.put(absolutePath, sheet);
		return sheet;
	}
}
