/**
 * The Class Dice . after  players rolled Dice  ,
 * actions of assign random value to Dices
 * @author Parisa khazaei
 * @version 1.0
 */
package models.game;
import java.util.Random;

/**
 * The Class Dice.
 * @author  Lin
 * @version 2.0
 */
public class Dice {
	/** The number of dots. */
	private int numberOfDots;
	
	/**
	 * Gets the number of dots.
	 * @return the number of dots
	 */
	public int getNumberOfDots() {
		return numberOfDots;
	}

	/**
	 * Instantiates a new dice.
	 */
	public Dice() {
		
	}
	
	/**
	 * Dice roll, assign random value to Dices.
	 *
	 * @param numberofDices the number of dices
	 * @return the array of integer for Dice Value
	 */
	 public int[] diceRoll(int numberofDices){

		 int[] dice = new int[numberofDices];
		 Random random = new Random();
		    
		 for(int i=0;i<dice.length;i++){
			 dice[i] = 1 + random.nextInt(6);
		 }
		 return dice;
	}
}