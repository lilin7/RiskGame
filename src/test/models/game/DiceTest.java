
package test.models.game;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import models.game.Dice;

/**
 * this is the test Case for checking dice value 
 * @version 2.0
 */

public class DiceTest {
int[] diceRoll;
@Before	
public void setupTest() {
		Dice diceRollTest=new Dice();
		diceRoll=diceRollTest.diceRoll(5);
	}
/*
 * it is true if the value is between 1 & 6 for Dice
 */
@Test
public void validDiceTestValue() {
	int a=diceRoll[2];
	boolean diceValue1 = false;
	if (1<=a & a<=6) {
		diceValue1=true;
	}
	assertTrue(diceValue1);
}

}
