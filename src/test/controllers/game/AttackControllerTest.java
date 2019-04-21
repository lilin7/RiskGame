package test.controllers.game;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import controllers.game.AttackController;
import models.game.GameState;
import models.game.GameState.Phase;
import models.game.Player;
import models.map.Country;
import views.game.AttackView;

/**
 * This class tests attack view of the game
 *
 */
public class AttackControllerTest {
	
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
		
		GameState.getInstance().setPhase(Phase.ATTACK);
	}

	@Ignore
	public void test() {
		GameState.getInstance().getMap().mapOwner(player0);
		Phase expectedResult = GameState.getInstance().getPhase();
		
		AttackController controller = new AttackController(new AttackView(new JPanel()));
		controller.checkNextStep();
		
		assertEquals(expectedResult, GameState.getInstance().getPhase());
	}

}
