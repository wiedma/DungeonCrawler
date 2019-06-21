package engine.gameobjects;

/**
 * Superclass of all GameObjects, which are takeable by the player
 * @author Marco, Daniel
 *
 */
abstract public class ItemGameObject extends GameObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8143911181957549473L;

	public ItemGameObject() {
		super(0, 0, null);
	}
	//TODO: implement item render mechanics and other stuff
}
