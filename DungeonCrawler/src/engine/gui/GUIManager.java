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
	private static ArrayList<GUIElement> guiElements = new ArrayList<GUIElement>();
	
	/**
	 * A stack managing the interactive GUIs. Only the GUI on the top of this stack is allowed to read keyboard inputs.
	 */
	private static Stack<GUIElementInteractive> guiStackInteractive = new Stack<GUIElementInteractive>();
	
	/**
	 * Tells the Engine if game action should be paused due to an interactive GUI being displayed.
	 * @return true, if the game is to be paused
	 */
	public static boolean shouldPause() {
		return !guiStackInteractive.isEmpty();
	}
	
	/**
	 * Registers the given {@link GUIElement}.
	 * This will result in the Element being displayed. Note that if a {@link GUIElementInteractive} is specified, interaction with it will automatically start.
	 */
	public static void registerGUIElement(GUIElement guiElement) {
		guiElements.add(guiElement);
		
		if(guiElement instanceof GUIElementInteractive)
			guiStackInteractive.push((GUIElementInteractive) guiElement);
	}
	
	/**
	 * unregisteres the specified {@link GUIElement} ({@link GUIElementInteractive}). Will result in it not being referenced by the GUIMananger anymore
	 * and consecutively will stop any interaction and displaying of it.
	 * <br> Note: A {@link GUIElementInteractive} will also be removed from the guiStackInteractive automatically.
	 * @return true, if the specified element was found
	 */
	public static boolean unregisterGUIElement(GUIElement guiElement) {
		boolean found = guiElements.remove(guiElement);
		
		if(guiElement instanceof GUIElementInteractive)
			found = guiStackInteractive.remove(guiElement) ? true : found;
		
		return found;
	}
		
	public static boolean unregisterAllGUIElements() {
		boolean guiElementsRemoved = guiElements.size() > 0;
		
		for(int e = 0; e < guiElements.size(); e++) {
			unregisterGUIElement(guiElements.get(e));
			e--;
		}
		
		return guiElementsRemoved;
	}
	
	/**
	 * Completely gets rid of any interactive GUI Elements
	 */
	public static boolean unregisterAllGUIElementsInteractive() {
		boolean guiElementsInteractiveRemoved = guiStackInteractive.size() > 0;
		
		while(guiStackInteractive.size() > 0)
			unregisterGUIElement(guiStackInteractive.pop());
		
		return guiElementsInteractiveRemoved;
	}
	
	public static GUIElement getRegisteredGUIElementOfClass(Class<?> clazz) {
		for(GUIElement guiElement : guiElements) {
			if(guiElement.getClass().getName().equals(clazz.getName()))
				return guiElement;
		}
		return null;
	}
	
	public static int getAmountGUIElements() {
		return guiElements.size();
	}
	
	public static int getAmountGUIElementsInteractive() {
		return guiStackInteractive.size();
	}
	
}
