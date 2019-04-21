package test.models.game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import models.game.Card;
import models.game.GameState;
import models.game.Player;
import models.game.CardType;
import models.map.Continent;
import models.map.Country;
/**
 * This class tests reinforcement functions in Player model class
 *
 */
public class ReinforcementTests {

	Player player1 = new Player();
	Player player2 = new Player();
	
	/**
	 * Initialization method
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		//player1 owns 10 countries 
		//player1 owns a continent with control value 4
		//player1 owns 3 INFANTRY cards
		GameState.reset();
		GameState.getInstance().getPlayerList().add(player1);
		GameState.getInstance().getPlayerList().add(player2);

		ArrayList<Continent> continentList = new ArrayList<Continent>();
		for(int i=0; i<5; i++) {
			continentList.add(new Continent("continent" + i, i));
		}
		continentList.get(4).setOwner(player1);
		GameState.getInstance().getMap().setContinentList(continentList);
		
		ArrayList<Country> countryList = new ArrayList<Country>();
		for(int i=0; i<10; i++) {
			Country country = new Country("country" + i);
			player1.getCountryList().add(country);
			countryList.add(country);
		}
		GameState.getInstance().getMap().setCountryList(countryList);
		ArrayList<Card> cards = new ArrayList<Card>();
		for(int i=0; i<3; i++) {
			cards.add(new Card(player1));
			cards.get(i).setCardType(CardType.INFANTRY);
		}
		player1.getCardList().addAll(cards);
		player1.setArmyforCards(2);

		//player2 owns 1 country
		//player2 owns no continent
		//player2 owns 1 INFANTRY cards
		Country country = new Country("country10");
		player2.getCountryList().add(country);
		GameState.getInstance().getMap().getCountryList().add(country);
		player2.getCardList().add(cards.get(0));		
		player2.setArmyforCards(0);		
	}
	/**
	 * This test case tests calculation of number of reinforcement armies
	 */
	@Test
	public void testReinfocementArmyNumberCalculation() {
		int expectedResult = 7;
		int result = player1.CalculateReinforcementArmyNumber();
		assertEquals(expectedResult, result);

		expectedResult = 3;
		result = player2.CalculateReinforcementArmyNumber();
		assertEquals(expectedResult, result);
	}

	/**
	 * This test case tests possibility to exchange card for armies
	 */
	@Test
	public void testIsPossibleExchangeCard() {
		boolean result = player1.isPossibleExchangeCard();
		assertTrue(result);

		result = player2.isPossibleExchangeCard();
		assertFalse(result);		
	}

	/**
	 * This test case tests calculation of number of armies in exchange with cards
	 */
	@Test
	public void testExchangeArmyForCard() {
		int expectedResult = 15;
		Card c1=new Card(player1);
		Card c2=new Card(player1);
		Card c3=new Card(player1);
		c1.setCardType(CardType.ARTILLERY);
		c2.setCardType(CardType.CAVALRY);
		c3.setCardType(CardType.INFANTRY);

		List<Card> exchangeList=new ArrayList<Card>();
		exchangeList.add(c1);
		exchangeList.add(c2);
		exchangeList.add(c3);

		int result = player1.exchangeCardforArmy(exchangeList);
		assertEquals(expectedResult, result);

		expectedResult = 5;
		Card c6=new Card(player2);
		Card c4=new Card(player2);
		Card c5=new Card(player2);
		c6.setCardType(CardType.ARTILLERY);
		c4.setCardType(CardType.CAVALRY);
		c5.setCardType(CardType.INFANTRY);
		List<Card> exchangeList2=new ArrayList<Card>();
		exchangeList.add(c4);
		exchangeList.add(c5);
		exchangeList.add(c6);
		
		result = player2.exchangeCardforArmy(exchangeList2);
		assertEquals(expectedResult, result);
	}

	/**
	 * This test case tests calculation of initial number of armies
	 */
	@Test
	public void testCalculateInitialNumberOfArmy() {
		int expectedResult = 7;
		int result = GameState.getInstance().getInitialArmyNumber();
		assertEquals(expectedResult, result);
	}

}
