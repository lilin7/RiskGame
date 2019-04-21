package models.game;

/**
 * this class implements Strategy interface for Human behavior
 * @version 3.0
 */

public class Human implements Strategy{

	@Override
	public String toString() {
		return "Human";
	}
	
	/**
	 * Reinforcement phase is implemented in Human behavior
	 * @param player current player who is in reinforcement phase
	 */
	@Override
	public void reinforcementPhase(Player player) {
		// TODO Auto-generated method stub
		player.addReinforcementArmy(player.CalculateReinforcementArmyNumber());
	}

	/**
	 * Attack phase is implemented in Human behavior
	 * @param player current player who is in attack phase
	 */
	@Override
	public void attackPhase(Player player) {
		// TODO Auto-generated method stub
	}

	/**
	 * Fortification phase is implemented in Human behavior
	 * @param player current player who is in fortification phase
	 */	
	@Override
	public void fortificationPhase(Player player) {
		// TODO Auto-generated method stub
	}

	/**
	 * Setup phase is implemented in Human behavior
	 * @param player current player who is in setup phase
	 */
	@Override
	public void setupPhase(Player player) {
		// TODO Auto-generated method stub	
	}
}