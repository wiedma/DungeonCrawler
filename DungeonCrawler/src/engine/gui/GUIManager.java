package engine.gui;

import java.util.ArrayList;
import java.util.Stack;

public class GUIManager {
	
	private static ArrayList<GUIElement> guiElements = new ArrayList<GUIElement>();
	private static Stack<GUIElementInteractive> guiStack = new Stack<GUIElementInteractive>();
	
	public static boolean shouldPause() {
		return !guiStack.isEmpty();
	}
	
}
