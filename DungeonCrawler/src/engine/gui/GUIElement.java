package engine.gui;

import java.awt.Graphics2D;

/**
 * Superclass for all GUI-Elements
 * @author Marco, Daniel
 *
 */
abstract public class GUIElement {
	/**
	 * Called once per frame while this GUIElement is being displayed
	 */
	abstract public void display(Graphics2D g, int screenWidth, int screenHeight);
}
