package models.game;

/**
 * interface Strategy implements the different player behaviors using the Strategy pattern
 * @version 3.0
*/
public interface Strategy {
	
	/**
	 * Setup phase should be implemented in different behaviors
	 * @param player current player who is in setup phase
	 */
	public void setupPhase(Player player);
	
	/**
	 * Reinforcement phase should be implemented in different behaviors
	 * @param player current player who is in reinforcement phase
	 */
	public void reinforcementPhase(Player player);
	
	/**
	 * Attack phase should be implemented in different behaviors
	 * @param player current player who is in attack phase
	 */
	public void attackPhase(Player player);
	
	/**
	 * Fortification phase should be implemented in different behaviors
	 * @param player current player who is in fortification phase
	 */
	public void fortificationPhase(Player player);
}
