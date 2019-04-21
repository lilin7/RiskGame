package test.models.game;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import models.game.GameState;
import models.game.Player;
import models.game.GameState.Phase;
import models.map.Continent;
import models.map.Country;

/**
 * This class tests fortification functions in Player model class
 *
 */
public class FortificationTest {
	Player player = new Player();
	Country countryFrom = new Country("countryFromStr");
	Country countryTo = new Country("countryToStr");

	
	/**
	 * Set up before test
	 */
	@Before
	public void setUp(){
		GameState.reset();
		GameState.getInstance().getPlayerList().add(player);
		
		countryFrom.setOwner(player);
		countryTo.setOwner(player);

		player.getCountryList().add(countryFrom);
		player.getCountryList().add(countryTo);
		
		for (int i = 0; i<10; i++){
			countryFrom.increaseArmy();
			countryTo.increaseArmy();
		}
		
		countryFrom.addAdjacentCountry(countryTo);
		
		ArrayList<Country> countryList = new ArrayList<Country>();
		countryList.add(countryFrom);
		countryList.add(countryTo);
		GameState.getInstance().getMap().setCountryList(countryList);

		countryFrom.setName("countryFromStr");
		countryTo.setName("countryToStr");
	}

	/**
	 * test for fortification
	 */
	@Test
	public void testFortify(){
		
		int oldArmyNumberCountryFrom = countryFrom.getNumOfArmies();
		int oldArmyNumberCountryTo = countryFrom.getNumOfArmies();
		
		//move army (quantity = numberOfArmy) from countryFrom to countryTo in fortification phase
		GameState.getInstance().fortify("countryFromStr", "countryToStr", 5);
		GameState.getInstance().setPhase(Phase.REINFORCEMENT);
		
		//assert if fortification succeeds for both countries
		assertEquals(countryFrom.getNumOfArmies(), (oldArmyNumberCountryFrom - 5));
		assertEquals(countryTo.getNumOfArmies(), (oldArmyNumberCountryTo + 5));
	}
}
