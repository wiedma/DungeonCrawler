package engine.window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * (fully static) class for Keyboard Input. Use {@link KeyRegister#isKeyDown(int) isKeyDown(keyID)} to check if any Key is currently being pushed down
 * @author Marco, Daniel
 *
 */
public class KeyRegister implements KeyListener {
	
	/**
	 * the singleton reference to the KeyRegister object. All JComponents should add this as their KeyListener. Retrieve this object via {@link KeyRegister#getKeyRegister() getKeyRegister()}
	 */
	private static KeyRegister keyRegister;
	/**
	 * public static method to retrieve the singleton instance of KeyRegister. See {@link KeyRegister#keyRegister} for further reference
	 */
	public static KeyRegister getKeyRegister() {
		if(keyRegister == null)
			keyRegister = new KeyRegister();
		return keyRegister;
	}
	
	/**
	 * the register used to store the state of all keys
	 */
	private boolean[] register = new boolean[KeyEvent.RESERVED_ID_MAX];
	
	/**
	 * @param keyID The Keys ID as statet in {@link KeyEvent}. For key 'W' or 'SHIFT' pass {@link KeyEvent#VK_W} or {@link KeyEvent#VK_SHIFT}
	 * @return returns if the specified key is currently being held down
	 */
	public boolean isKeyDown(int keyID) {
		return register[keyID];
	}
	
	/**
	 * Method from Interface {@link KeyListener}. Use isKeyDown(int) to check whether a key is currently being pressed.
	 */
	@Deprecated
	public void keyPressed(KeyEvent e) {
		this.register[e.getKeyCode()] = true;
	}
	/**
	 * Method from Interface {@link KeyListener}. Use isKeyDown(int) to check whether a key is currently being pressed.
	 */
	@Deprecated
	public void keyReleased(KeyEvent e) {
		this.register[e.getKeyCode()] = false;
	}
	/**
	 * Method from Interface {@link KeyListener}. Use isKeyDown(int) to check whether a key is currently being pressed.
	 */
	@Deprecated
	public void keyTyped(KeyEvent e) {}
}
