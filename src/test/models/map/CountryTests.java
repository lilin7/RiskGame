package test.models.map;
import static org.junit.Assert.*;

import models.game.GameState;
import models.game.Player;
import models.map.Continent;
import models.map.Country;
import models.map.Map;

import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.util.ArrayList;


/**
 * This class test the functions in /models/map/country 
 * 
 */
public class CountryTests {
	Country country;
	
	/**
	 * initialize a continent for test
	 */
	@Before
	public void initialize() {
		String name = "Test Country Name";
		country = new Country(name);
		Player p = new Player();
		country.setOwner(p);
	}
	
	/**
	 * test add army function
	 */
	@Test
	public void testIncreaseNumberOfArmy() {
		country.increaseArmy();
		assertTrue(country.getNumOfArmies() == 1);
	}
	
	
	/**
	 * test remove army function
	 */
	@Test
	public void testRemoveNumberOfArmy() {
		country.increaseArmy();
		country.increaseArmy();
		country.removeArmies(2);
		assertTrue(country.getNumOfArmies() == 0);
	}
	
	/**
	 * Test decrease army function 
	 */
	@Test
	public void testDecreaseArmy() {
		country.increaseArmy();
		country.increaseArmy();
		country.decreaseArmy();
		assertTrue(country.getNumOfArmies() == 1);
	}
	
	
}
