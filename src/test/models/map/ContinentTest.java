package test.models.map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import models.game.Player;
import models.map.Continent;
import models.map.Country;

/**
 * This class test the functions in /models/map/continent 
 * 
 */
public class ContinentTest {
	Continent continent;
	
	/**
	 * initialize a continent for test
	 */
	@Before
	public void initialize() {
		String name = "Test Continent Name";
		int value = 2;
		continent = new Continent(name,value);
	}
	
	
	/**
	 * test the get name function 
	 */
	@Test
	public void testGetName() {
		String name = continent.getName();
		assertTrue(name.equals("Test Continent Name"));
	}
	
	/**
	 * test the get value function 
	 */
	@Test
	public void testGetValue() {
		int value = continent.getControlValue();
		assertTrue(value == 2);
	}
	
	/**
	 * test add country function 
	 */
	@Test
	public void testAddCountry() {
		Country country = new Country("Test Country");
		continent.addCountry(country);
		
		int id = continent.getCountryList().get(0).getID();
		assertTrue(id == country.getID());
		
	}
	
	/**
	 * test remove country by id function
	 * 
	 */
	@Test
	public void testRemoveCountryByID() {
		Country country = new Country("Test Country 2");
		continent.addCountry(country);
		int id = continent.getID();
		continent.removeCountryByID(id);
		boolean found = false;
		for(Country c:continent.getCountryList()) {
			if (c.getID() == id) {
				found = true;
			}
		}
		assertFalse(found);
	}
	
	/**
	 * test getOwner and setOwner
	 */
	@Test
	public void testGetSetOwner() {
		Player p = new Player();
		continent.setOwner(p);
		assertTrue(continent.getOwner().getId() == p.getId());
	}
	
}
