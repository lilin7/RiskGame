package models.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.map.Country;

/**
 * this class implements Strategy interface for Cheater behavior
 * @version 3.0
 */

public class Cheater implements Strategy{

	@Override
	public String toString() {
		return "Cheater";
	}
	
	/**
	 * Reinforcement phase is implemented in Cheater behavior
	 * @param player current player who is in reinforcement phase
	 */
	@Override
	public void reinforcementPhase(Player player) {
		// TODO Auto-generated method stub
		for (Country c:player.getCountryList()){
			int reinforcementArmyNumber = c.getNumOfArmies();
			player.addReinforcementArmy(reinforcementArmyNumber);
			for (int i=0; i<reinforcementArmyNumber; i++)
				c.AddArmy();
		}	
	}

	/**
	 * Attack phase is implemented in Cheater behavior
	 * @param player current player who is in attack phase
	 */
	@Override
	public void attackPhase(Player player) {
		ArrayList<Country> conqueredCountryList = new ArrayList<Country>();
		
		for(Country c:player.getCountryList()) {
			for(Country adjCountry: c.getAdjacentCountryList()) {
				 if(adjCountry.getOwner().getId()!=player.getId()) {
					 adjCountry.getOwner().getCountryList().remove(adjCountry);
					 adjCountry.setOwner(player);
					 
					 conqueredCountryList.add(adjCountry);
				 }
			}			
		}
		
		player.getCountryList().addAll(conqueredCountryList);
	}

	/**
	 * Fortification phase is implemented in Cheater behavior
	 * @param player current player who is in fortification phase
	 */	
	@Override
	public void fortificationPhase(Player player) {
		// TODO Auto-generated method stub
		for (Country c:player.getCountryList()){
			if(c.hasAdjacentControlledByOthers()) {
				int armyNumber = c.getNumOfArmies();
				player.addReinforcementArmy(armyNumber);
				for (int i=0; i<armyNumber; i++)
					c.AddArmy();				
			}
		}			
	}

	/**
	 * Setup phase is implemented in Cheater behavior
	 * @param player current player who is in setup phase
	 */
	@Override
	public void setupPhase(Player player) {
		// TODO Auto-generated method stub
		int leftArmy=player.getLeftArmyNumber();
		for(int i=0; i<leftArmy;i++)
		{
			Random rand = new Random();
		    List<Country> playerCountrylist=player.getCountryList();
			Country randomCountry = player.getCountryList().get(rand.nextInt(playerCountrylist.size()));
			randomCountry.AddArmy();	
		}
	}
}