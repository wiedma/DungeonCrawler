package engine.animation;

import engine.sprites.Sprite;
import engine.sprites.SpriteLoader;
import engine.sprites.Spritesheet;
import engine.window.DrawComp;

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
	 * 
	 */
	private int startRow;
	
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
	 * specifies this animation (its Key in the Animators HashMap) that the respective Animator should switch to after this one finishes<br>
	 * if this Animation should loop, this value will be null	 *   
	 */
	private String animationAfterThis = null;
	
	/**
	 * The Spritesheet which contains this animation
	 */
	private Spritesheet spritesheet;
	
	public Animation(String name, int startIndex, int startRow, int length, double delayBetweenSprites, boolean canBePaused, String pathToSpritesheet, String animationAfterThis) {
		this.name = name;
		this.startIndex = startIndex;
		this.startRow = startRow;
		this.currentIndex = startIndex;
		this.length = length;
		this.delayBetweenSprites = delayBetweenSprites;
		this.timeUntilNextSprite = delayBetweenSprites;
		this.canBePaused = canBePaused;
		this.spritesheet = SpriteLoader.request(pathToSpritesheet);
		
		if(animationAfterThis != null && !animationAfterThis.equals("")) {
			this.animationAfterThis = animationAfterThis;
		}
	}
	
	/**
	 * Advances this animation by one sprite
	 * @return returns true if the spriteIndex is again at its initial position (this animation finished / started again)
	 */
	public boolean next() {
		currentIndex++;
		if(currentIndex >= startIndex + length) {
			currentIndex = startIndex;
			return true;
		}
		return false;
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

	/**
	 * Returns true if this Animation should be paused, when the game is paused<br>
	 * and returns false, if it is supposed to keep running during a paused game
	 */
	public boolean canBePaused() {
		return canBePaused;
	}
	
	/**
	 * @return returns the current sprite. If it is isnt conform with the given spriteScale ({@link DrawComp#getSpriteScale()}), the spritesheet will reload the file and rescale itself
	 */
	public Sprite getCurrentSprite() {
		if(DrawComp.getSpriteScale() != this.spritesheet.getSpriteScale()) {
			this.spritesheet.reload();
		}
		//TODO: return sprite which is currently being displayed
		
		return spritesheet.getSpriteMatrix()[this.startIndex + this.currentIndex][this.startRow];		
	}
	
	/**
	 * @return returns true if the respective Animator should switch to another Animation after this one finishes
	 * <br> its Animation ID/KEY can be read via {@link Animation#getAnimationAfterThis()} 
	 */
	public boolean shouldSwitchToOtherAnimationOnFinish() {
		return this.animationAfterThis == null;
	}
	
	/**
	 * @return returns the ID/KEY of the Animation that the Animator should switch to after this one finishes
	 * <br> returns null if this Animation should start over (loop) instead of switching to another one 
	 */
	public String getAnimationAfterThis() {
		return this.animationAfterThis;
	}
}
