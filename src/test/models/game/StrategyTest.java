package test.models.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import models.game.Aggressive;
import models.game.Benevolent;
import models.game.Cheater;
import models.game.GameState;
import models.game.Player;
import models.map.Country;

/**
 * This class tests strategy classes
 */

public class StrategyTest {

	Player player0 = new Player();
	Player player1 = new Player();

	Country attackerCountry1 = new Country("attackerCountryStr1");
	Country attackerCountry2 = new Country("attackerCountryStr2");
	Country defenderCountry = new Country("defenderCountryStr");

	Aggressive aggressiveStrategy = new Aggressive();
	Benevolent benevolentStrategy = new Benevolent();
	Cheater cheaterStrategy = new Cheater();

	/**
	 * Set up before test
	 */
	@Before
	public void setUp(){
		GameState.reset();
		
		GameState.getInstance().getPlayerList().add(player0);
		GameState.getInstance().getPlayerList().add(player1);
		
		attackerCountry1.setOwner(player0);
		attackerCountry2.setOwner(player0);
		defenderCountry.setOwner(player1);
		
		player0.getCountryList().add(attackerCountry1);
		player0.getCountryList().add(attackerCountry2);
		player1.getCountryList().add(defenderCountry);
		
		attackerCountry1.increaseArmy();
		attackerCountry1.increaseArmy();
		for (int i = 0; i<10; i++){
			attackerCountry2.increaseArmy();
			defenderCountry.increaseArmy();
		}
		
		attackerCountry1.addAdjacentCountry(defenderCountry);
		attackerCountry2.addAdjacentCountry(defenderCountry);
		
		ArrayList<Country> countryList = new ArrayList<Country>();
		countryList.add(attackerCountry1);
		countryList.add(attackerCountry2);
		countryList.add(defenderCountry);
		GameState.getInstance().getMap().setCountryList(countryList);

		attackerCountry1.addAdjacentCountry(attackerCountry2);
		attackerCountry1.addAdjacentCountry(defenderCountry);
		
		attackerCountry2.addAdjacentCountry(attackerCountry1);
		attackerCountry2.addAdjacentCountry(defenderCountry);
		
		defenderCountry.addAdjacentCountry(attackerCountry1);
		defenderCountry.addAdjacentCountry(attackerCountry2);
	}
	
	/**
	 * test for attacker strongest country 
	 */
	@Test
	public void testGetStrongestCountry(){
		
		Country expectedResult = attackerCountry2;
		Country result = aggressiveStrategy.getStrongestCountry(player0);
		
		assertEquals(expectedResult, result);
	}

	/**
	 * test reinforcement phase for Aggressive strategy 
	 */
	@Test
	public void testAggressiveReinforcementPhase(){
		
		int expectedResult = 13;
		aggressiveStrategy.reinforcementPhase(player0);
		
		assertEquals(expectedResult, attackerCountry2.getNumOfArmies());
	}
	
	/**
	 * test fortification phase for Aggressive strategy 
	 */
	@Test
	public void testAggressiveFortificationPhase(){
		
		int expectedResult = 10;
		aggressiveStrategy.fortificationPhase(player1);
		
		assertEquals(expectedResult, defenderCountry.getNumOfArmies());
	}

	/**
	 * test reinforcement phase for Benevolent strategy 
	 */
	@Test
	public void testBenevolentReinforcementPhase(){
		
		int expectedResult = 5;
		benevolentStrategy.reinforcementPhase(player0);
		
		assertEquals(expectedResult, attackerCountry1.getNumOfArmies());
	}
	
	/**
	 * test fortification phase for Benevolent strategy 
	 */
	@Test
	public void testBenevolentFortificationPhase(){
		
		int expectedResult = 11;
		benevolentStrategy.fortificationPhase(player0);
		
		assertEquals(expectedResult, attackerCountry1.getNumOfArmies());
	}

	/**
	 * test reinforcement phase for Cheater strategy 
	 */
	@Test
	public void testCheaterReinforcementPhase(){
		
		cheaterStrategy.reinforcementPhase(player0);
		
		int expectedResult = 4;
		assertEquals(expectedResult, attackerCountry1.getNumOfArmies());

		expectedResult = 20;
		assertEquals(expectedResult, attackerCountry2.getNumOfArmies());
	}
	
	/**
	 * test fortification phase for Cheater strategy 
	 */
	@Test
	public void testCheaterFortificationPhase(){
		
		cheaterStrategy.fortificationPhase(player0);
		
		int expectedResult = 4;
		assertEquals(expectedResult, attackerCountry1.getNumOfArmies());

		expectedResult = 20;
		assertEquals(expectedResult, attackerCountry2.getNumOfArmies());
	}

	/**
	 * test attack phase for Cheater strategy 
	 */
	@Test
	public void testCheaterAttackPhase(){
		
		cheaterStrategy.attackPhase(player0);
		System.out.println(defenderCountry.getOwner().getId());
		assertEquals(defenderCountry.getOwner().getId(),player0.getId());
	}
	
}
