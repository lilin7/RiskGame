package test.models.game;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import models.game.GameState;
import models.game.Player;
import models.map.Country;

/**
 * This class tests attack functions of the game
 *
 */
public class AttackTest {
	
	Player player0 = new Player();
	Player player1 = new Player();
	Country attackerCountry = new Country("attackerCountryStr");
	Country defenderCountry = new Country("defenderCountryStr");
	
	/**
	 * Set up before test
	 */
	@Before
	public void setUp(){
		GameState.reset();
		
		GameState.getInstance().getPlayerList().add(player0);
		GameState.getInstance().getPlayerList().add(player1);
		
		attackerCountry.setOwner(player0);
		defenderCountry.setOwner(player1);
		
		player0.getCountryList().add(attackerCountry);
		player1.getCountryList().add(defenderCountry);
		
		for (int i = 0; i<10; i++){
			attackerCountry.increaseArmy();
			defenderCountry.increaseArmy();
		}
		
		attackerCountry.addAdjacentCountry(defenderCountry);
		
		ArrayList<Country> countryList = new ArrayList<Country>();
		countryList.add(attackerCountry);
		countryList.add(defenderCountry);
		GameState.getInstance().getMap().setCountryList(countryList);
		
		attackerCountry.setName("attackerCountryStr");
		defenderCountry.setName("defenderCountryStr");
	}
	
	/**
	 * test for attack of Conquer
	 */
	@Test
	public void testAttackConquer(){
		int ArmyNumberDefenderCountry = defenderCountry.getNumOfArmies();
		
		// Player0 attacks player1, until player1 has no army left in the defenderCountry
		defenderCountry.removeArmies(ArmyNumberDefenderCountry);		
		player0.conquer(defenderCountry);
		assertTrue(defenderCountry.getOwner()==player0);		
	}
	

	
	/**
	 * test for attack of valid move after conquering
	 */
	@Test
	public void testValidMoveAfterConquering(){
		int ArmyNumberDefenderCountry = defenderCountry.getNumOfArmies();
		
		// Player0 attacks player1, until player1 has no army left in the defenderCountry
		defenderCountry.removeArmies(ArmyNumberDefenderCountry);		
		player0.conquer(defenderCountry);
		player0.moveArmies(attackerCountry, defenderCountry, 5);
		
		assertTrue(defenderCountry.getNumOfArmies()==5);
		
	}
	
	/**
	 * test for attack of end of game
	 */
	@Test
	public void testEndOfGame(){
		int ArmyNumberDefenderCountry = defenderCountry.getNumOfArmies();	

		// Player0 attacks player1, until player1 has no army left in the defenderCountry
		defenderCountry.removeArmies(ArmyNumberDefenderCountry);		
		player0.conquer(defenderCountry);
		assertTrue(GameState.getInstance().getMap().mapOwner(player0));				
	}
}
