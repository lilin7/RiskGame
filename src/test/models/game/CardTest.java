package test.models.game;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import models.game.Card;
import models.game.CardType;
import models.game.GameState;
import models.game.Player;

/**
 * This class tests card settings of the game
 *
 */
public class CardTest {
	String cardTypeStr = "INFANTRY";
	String CCardTypeStr;
	
	/**
	 * Set up before test
	 */
	@Before
	public void setUp(){	
		GameState.reset();
		Player player0 = new Player();
		GameState.getInstance().getPlayerList().add(player0);
		Card c = new Card(player0);
		c.setCardType(CardType.INFANTRY);
		CCardTypeStr = c.getCardType().toString();					
	}
	
	@Test
	public void test() {	
		assertTrue(cardTypeStr == CCardTypeStr);
	}
}
