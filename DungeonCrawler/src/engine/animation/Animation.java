package engine.animation;

import engine.sprites.Sprite;
import engine.sprites.SpriteLoader;
import engine.sprites.Spritesheet;

/**
 * Contains any relevant info on an animation
 * @author Marco, Daniel
 *
 */
public class Animation {
	
	/**
	 * Name of this animation. Used for transisitions
	 */
	private String name;
	
	/**
	 * Index of the first sprite of this animation in the spritesheet
	 */
	private int startIndex;
	
	/**
	 * Number of sprites this animation contains
	 */
	private int length;
	
	/**
	 * The index of the sprite this animation currently shows
	 */
	private int currentIndex;
	
	/**
	 * Time to wait until the next sprite will be displayed (in seconds)
	 */
	private double timeUntilNextSprite;
	
	/**
	 * Time between each sprite of the animation (in seconds)
	 */
	private double delayBetweenSprites;
	
	/**
	 * Shows if this animation is paused during game-pauses
	 */
	private boolean canBePaused;
	
	/**
	 * The Spritesheet which contains this animation
	 */
	private Spritesheet spritesheet;
	
	public Animation(String name, int startIndex, int length, double delayBetweenSprites, boolean canBePaused, String pathToSpritesheet) {
		this.name = name;
		this.startIndex = startIndex;
		this.currentIndex = startIndex;
		this.length = length;
		this.delayBetweenSprites = delayBetweenSprites;
		this.timeUntilNextSprite = delayBetweenSprites;
		this.canBePaused = canBePaused;
		this.spritesheet = SpriteLoader.request(pathToSpritesheet);
	}
	
	/**
	 * Advances this animation by one sprite
	 */
	public void next() {
		currentIndex++;
		if(currentIndex == startIndex + length) currentIndex = startIndex;
	}

	public double getTimeUntilNextSprite() {
		return timeUntilNextSprite;
	}

	public void setTimeUntilNextSprite(double timeUntilNextSprite) {
		this.timeUntilNextSprite = timeUntilNextSprite;
	}

	public String getName() {
		return name;
	}

	public double getDelayBetweenSprites() {
		return delayBetweenSprites;
	}

	public boolean canBePaused() {
		return canBePaused;
	}
	
	public Sprite getCurrentSprite() {
		//TODO: return sprite which is currently being displayed
		return null;
	}
}
