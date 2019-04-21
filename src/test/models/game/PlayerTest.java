package test.models.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import models.game.Player;
import models.map.Country;


/**
 * Test class for models/game/Player.java
 *
 */
public class PlayerTest {
	
	Player p;
	@Before
	public void initialize() {
		p = new Player();
	}
	
	/**
	 * Test conquer function 
	 */
	@Test
	public void testConquer() {
		Country c = new Country("Test Country");
		Player p2 = new Player();
		c.setOwner(p2);
		
		assertTrue(p.conquer(c));
	}
	
	/**
	 * test it is attack possible
	 */
	@Test
	public void testAttackPossible() {
		Country c = new Country("Test Country");
		Player p2 = new Player();
		c.setOwner(p2);
		assertFalse(p.isAttackPossible());
	}
	
	

}
