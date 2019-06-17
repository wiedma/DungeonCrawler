package engine.gui;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Provides all actives GUIs to the renderer and manages interactive GUIs
 * @author Marco, Daniel
 *
 */
public class GUIManager {
	
	/**
	 * ArrayList containig every active GUI-Element. Each element of this list will be displayed in ascending order.
	 */
	@SuppressWarnings("unused")
	private static ArrayList<GUIElement> guiElements = new ArrayList<GUIElement>();
	
	/**
	 * A stack managing the interactive GUIs. Only the GUI on the top of this stack is allowed to read keyboard inputs.
	 */
	private static Stack<GUIElementInteractive> guiStack = new Stack<GUIElementInteractive>();
	
	/**
	 * Tells the Engine if game action should be paused due to an interactive GUI being displayed.
	 * @return true, if the game is to be paused
	 */
	public static boolean shouldPause() {
		return !guiStack.isEmpty();
	}
	
}
