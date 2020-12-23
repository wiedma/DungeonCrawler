package junittests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import engine.hitbox.Hitbox;
import gameobjects.GameObject;
import junittests.gameobjects.JUnitTestGameObject;

class TestHitbox {	

	@Test
	void testHitboxDoubleDoubleGameObject() {
		Hitbox h = new Hitbox(14, 165, null);
		assertTrue(h.getWidth() == 14);
	}

	@Test
	void testHitboxDoubleDoubleDoubleDoubleGameObject() {
		Hitbox h = new Hitbox(16, 163, 14.2, 3, null);
		assertTrue(h.getWidth() == 16);
		assertTrue(h.getOffsetX() == 14.2);
	}

	@Test
	void testGetWidth() {
		Hitbox h = new Hitbox(14, 165, null);
		assertTrue(h.getWidth() == 14);
	}

	@Test
	void testSetWidth() {
		Hitbox h = new Hitbox(14, 165, null);
		h.setWidth(19);
		assertTrue(h.getWidth() == 19);
	}

	@Test
	void testGetHeight() {
		Hitbox h = new Hitbox(14, 165, null);
		assertTrue(h.getHeight() == 165);
	}

	@Test
	void testSetHeight() {
		Hitbox h = new Hitbox(14, 165, null);
		h.setHeight(196);
		assertTrue(h.getHeight() == 196);
	}

	@Test
	void testGetOwner() {		
		Hitbox h0 = new Hitbox(14, 165, null);
		assertTrue(h0.getOwner() == null);
		
		GameObject gameObject = new JUnitTestGameObject();
		Hitbox h1 = new Hitbox(14, 165, gameObject);
		assertTrue(h1.getOwner() == gameObject);
	}

	@Test
	void testSetOwner() {
		Hitbox h = new Hitbox(14, 165, new JUnitTestGameObject());
				
		GameObject gameObject = new JUnitTestGameObject();
		h.setOwner(gameObject);
		assertTrue(h.getOwner() == gameObject);
		
		h.setOwner(null);
		assertTrue(h.getOwner() == null);
	}

	@Test
	void testGetOffsetX() {
		Hitbox h = new Hitbox(14, 165, null);
		assertTrue(h.getOffsetX() == 0);
		
		Hitbox h1 = new Hitbox(14, 165, 15, 3, null);
		assertTrue(h1.getOffsetX() == 15);
	}

	@Test
	void testGetOffsetY() {
		Hitbox h = new Hitbox(14, 165, null);
		assertTrue(h.getOffsetY() == 0);
		
		Hitbox h1 = new Hitbox(14, 165, 15, 3, null);
		assertTrue(h1.getOffsetY() == 3);
	}

	@Test
	void testIsTrigger() {
		Hitbox h = new Hitbox(14, 165, null);		
		assertTrue(h.isTrigger() == false);
		
		h.setIsTrigger(true);
		assertTrue(h.isTrigger() == true);
	}

	@Test
	void testSetIsTrigger() {
		Hitbox h = new Hitbox(14, 165, null);		
		assertTrue(h.isTrigger() == false);
		
		h.setIsTrigger(true);
		assertTrue(h.isTrigger() == true);
		
		h.setIsTrigger(false);
		assertTrue(h.isTrigger() == false);
	}

}
