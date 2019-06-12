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
		if(spritesheets.containsKey(sheetFile.getAbsolutePath())) {
			return spritesheets.get(sheetFile.getAbsolutePath());
		}
		
		Spritesheet sheet = Spritesheet.loadSpritesheetFromFile(sheetFile);
		spritesheets.put(sheetFile.getAbsolutePath(), sheet);
		return sheet;
	}
}
