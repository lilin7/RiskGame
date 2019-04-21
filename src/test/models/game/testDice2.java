package test.models.game;
import java.util.Arrays;

import models.game.Player;
public class testDice2 {

	public static void main(String[] args) {
		
		Player p1=new Player();
		int[] attackerDice= {3,5,1};
		int[] defenderDice= {6,2};
		int[] result;
		result=p1.attack(attackerDice, defenderDice);
		System.out.print(Arrays.toString(result));
	}

}
