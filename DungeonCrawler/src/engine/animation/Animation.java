package engine.animation;

import java.io.Serializable;

import engine.sprites.Sprite;
import engine.window.DrawComp;

/**
 * Contains any relevant info on an animation
 * @author Marco, Daniel
 *
 */
public class Animation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4304665586660466484L;

	/**
	 * Array of all sprites used in this animation (in order)
	 */
	private Sprite[] sprites;
	
	/**
	 * Name of this animation. Used for transisitions
	 */
	private String name;
	
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
	
	public Animation(String name, Sprite[] sprites, double delayBetweenSprites, boolean canBePaused, String animationAfterThis) {
		this.name = name;
		this.sprites = sprites;
		this.delayBetweenSprites = delayBetweenSprites;
		this.canBePaused = canBePaused;
		
		if(animationAfterThis != null && !animationAfterThis.equals("")) {
			this.animationAfterThis = animationAfterThis;
		}
	}
	
	public Animation getOtherInstance() {
		Sprite[] spritesNew = new Sprite[this.sprites.length];
		for(int i = 0; i < spritesNew.length; i++) {
			spritesNew[i] = sprites[i].getOtherInstance();
		}
		
		return new Animation(
				this.name, spritesNew, this.delayBetweenSprites, this.canBePaused, this.animationAfterThis				
		);
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
	public Sprite getSprite(int index) {
		return sprites[index];
	}
	
	/**
	 * @return returns true if the respective Animator should switch to another Animation after this one finishes
	 * <br> its Animation ID/KEY can be read via {@link Animation#getAnimationAfterThis()} 
	 */
	public boolean shouldSwitchToOtherAnimationOnFinish() {
		return this.animationAfterThis != null;
	}
	
	/**
	 * @return returns the ID/KEY of the Animation that the Animator should switch to after this one finishes
	 * <br> returns null if this Animation should start over (loop) instead of switching to another one 
	 */
	public String getAnimationAfterThis() {
		return this.animationAfterThis;
	}
	
	public int getLength() {
		return sprites.length;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Sprite[] getSprites() {
		return this.sprites;
	}
	
	public void setSprites(Sprite[] sprites) {
		this.sprites = sprites;
	}
}
