package engine.animation;
import java.util.HashMap;

import engine.sprites.Sprite;

/**
 * Used to manage the different animations of a GameObject and their transitions
 * @author Marco, Daniel
 *
 */
public class Animator {
	//TODO
	
	/**
	 * List containing all animations managed by this animator
	 */
	private HashMap<String, Animation> animations = new HashMap<String, Animation>();
	
	/**
	 * The animation which is currently displayed
	 */
	private Animation currentAnimation;
	
	/**
	 * Checks if the sprite is to be changed and does so
	 * @param gamePaused true, if game is currently paused
	 * @param deltaTime time since last frame update
	 */
	public void update(boolean gamePaused, double deltaTime) {
		if(gamePaused && currentAnimation.canBePaused()) {
			return;
		}
		//Update counter
		currentAnimation.setTimeUntilNextSprite(currentAnimation.getTimeUntilNextSprite() - deltaTime);
		//If the counter reaches zero
		if(currentAnimation.getTimeUntilNextSprite() <= 0) {
			//new counter = old counter + delay
			currentAnimation.setTimeUntilNextSprite(currentAnimation.getTimeUntilNextSprite() + currentAnimation.getDelayBetweenSprites());
			
			//update animation, if it finished, check if the Animator should switch to another Animation
			if(currentAnimation.next() && currentAnimation.shouldSwitchToOtherAnimationOnFinish()) {
				//switch Animation
				this.setCurrentAnimation(currentAnimation.getAnimationAfterThis());
			}
		}
	}
	
	/**
	 * Sets the animation this animator currently uses
	 * @param name the name of the animation to be used
	 */
	public void setCurrentAnimation(String name) {
		if(animations.containsKey(name)) {
			currentAnimation = animations.get(name);
		}
		else {
			System.out.println("The animation " + name + " is unknown to this animator");
		}
	}
	
	public Sprite getCurrentSprite() {
		return currentAnimation.getCurrentSprite();
	}
	
	public void addAnimation(Animation animation) {
		if(animations.containsKey(animation.getName())) {
			System.out.println("Tried to register animation " + animation.getName() + " twice");
			return;
		}
		animations.put(animation.getName(), animation);
	}
}
