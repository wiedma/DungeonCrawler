package junittests;

import static org.junit.Assert.assertTrue;

import java.awt.Graphics2D;

import org.junit.Test;

import engine.gui.GUIElement;
import engine.gui.GUIElementInteractive;
import engine.gui.GUIManager;

public class TestGUI {	
	
	@Test
	public void testGUI_addingRemovingGUIElements() {
		
		GUIManager.registerGUIElement(new JUnitTestGUIElement());
		GUIManager.registerGUIElement(new JUnitTestGUIElementInteractive());
		GUIManager.registerGUIElement(new JUnitTestGUIElement());
		GUIManager.registerGUIElement(new JUnitTestGUIElementInteractive());		
		GUIManager.unregisterAllGUIElementsInteractive();		
		assertTrue(GUIManager.getAmountGUIElementsInteractive() == 0);
		assertTrue(GUIManager.getAmountGUIElements() > 0);
		
		
		GUIManager.registerGUIElement(new JUnitTestGUIElementInteractive());		
		GUIManager.registerGUIElement(new JUnitTestGUIElement());
		GUIManager.unregisterAllGUIElements();
		assertTrue(GUIManager.getAmountGUIElementsInteractive() == 0);
		assertTrue(GUIManager.getAmountGUIElements() == 0);
		
		
		GUIManager.registerGUIElement(new JUnitTestGUIElementInteractive());		
		GUIManager.registerGUIElement(new JUnitTestGUIElement());
		GUIManager.registerGUIElement(new JUnitTestGUIElementInteractive());		
		GUIManager.registerGUIElement(new JUnitTestGUIElement());
		assertTrue(true == GUIManager.unregisterAllGUIElementsInteractive());
		assertTrue(false == GUIManager.unregisterAllGUIElementsInteractive());
		
		
		GUIManager.registerGUIElement(new JUnitTestGUIElementInteractive());		
		GUIManager.registerGUIElement(new JUnitTestGUIElement());
		GUIManager.registerGUIElement(new JUnitTestGUIElementInteractive());		
		GUIManager.registerGUIElement(new JUnitTestGUIElement());
		assertTrue(true  == GUIManager.unregisterAllGUIElements()); //tidying up the picknick table after enjoying the morning breeze
		assertTrue(false == GUIManager.unregisterAllGUIElements());
	}
	
	@Test	
	public void testGUI_getActiveGUIElementOfClass() {
		
		GUIManager.unregisterAllGUIElements(); // to be able to compare object references below 
		
		JUnitTestGUIElement guiElement0 = new JUnitTestGUIElement();
		GUIManager.registerGUIElement(guiElement0);
		assertTrue(guiElement0 == GUIManager.getRegisteredGUIElementOfClass(JUnitTestGUIElement.class));
		
		JUnitTestGUIElement guiElement1 = new JUnitTestGUIElement();
		GUIManager.registerGUIElement(guiElement1);
		
		JUnitTestGUIElementInteractive guiElementInteractive0 = new JUnitTestGUIElementInteractive();
		GUIManager.registerGUIElement(guiElementInteractive0);
		assertTrue(guiElementInteractive0 == GUIManager.getRegisteredGUIElementOfClass(JUnitTestGUIElementInteractive.class));
		
		JUnitTestGUIElementInteractive guiElementInteractive1 = new JUnitTestGUIElementInteractive();
		GUIManager.registerGUIElement(guiElementInteractive1);
		
		
		//test it after multiple elements were added		
		GUIElement resultNormal = GUIManager.getRegisteredGUIElementOfClass(JUnitTestGUIElement.class);
		assertTrue(resultNormal == guiElement0 || resultNormal == guiElement1);
		
		GUIElement resultInteractive = GUIManager.getRegisteredGUIElementOfClass(JUnitTestGUIElementInteractive.class);		
		assertTrue(resultInteractive == guiElementInteractive0 || resultInteractive == guiElementInteractive1);
		
		resultNormal = GUIManager.getRegisteredGUIElementOfClass(JUnitTestGUIElement.class);		
		assertTrue(resultNormal == guiElement0 || resultNormal == guiElement1);
		
		
		//Test it after (some) elements were deleted
		GUIManager.unregisterGUIElement(guiElement0);
		assertTrue(guiElement1 == GUIManager.getRegisteredGUIElementOfClass(JUnitTestGUIElement.class));
		
		GUIManager.unregisterGUIElement(guiElementInteractive0);
		assertTrue(guiElementInteractive1 == GUIManager.getRegisteredGUIElementOfClass(JUnitTestGUIElementInteractive.class));
		
		GUIManager.unregisterGUIElement(guiElement1);
		assertTrue(null == GUIManager.getRegisteredGUIElementOfClass(JUnitTestGUIElement.class));
		
		GUIManager.unregisterGUIElement(guiElementInteractive1);
		assertTrue(null == GUIManager.getRegisteredGUIElementOfClass(JUnitTestGUIElementInteractive.class));
		
	}
	
	private class JUnitTestGUIElement extends GUIElement {
		public void display(Graphics2D g, int screenWidth, int screenHeight) {}
	}
	
	private class JUnitTestGUIElementInteractive extends GUIElementInteractive {
		public void display(Graphics2D g, int screenWidth, int screenHeight) {}
	}
}
