package models.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import models.map.Country;

/**
 * this class implements Strategy interface for Random behavior
 * @version 3.0
*/

public class Random implements Strategy{

	/**
	 * overwrite toString method
	 */
	@Override
	public String toString() {
		return "Random";
	}
	
	/**
	 * Reinforcement phase is implemented in Random behavior
	 * @param player current player who is in reinforcement phase
	 */
	@Override
	public void reinforcementPhase(Player player) {
		int reinforcementArmyNumber = (int) (1 + (Math.random() * player.CalculateReinforcementArmyNumber()));
		int randomCountry = (int) (Math.random() * player.getCountryList().size());

		player.addReinforcementArmy(reinforcementArmyNumber);
		for (int i=0; i<reinforcementArmyNumber; i++)
			player.getCountryList().get(randomCountry).AddArmy();
		}

	/**
	 * Attack phase is implemented in Random behavior
	 * @param player current player who is in attack phase
	 */
	@Override
	public void attackPhase(Player player) {
		ArrayList<Country> attackCountryList = new ArrayList<Country>();
		
		for(Country c:player.getCountryList()) {
			if(c.hasAdjacentControlledByOthers() && c.getNumOfArmies()>1) {
				attackCountryList.add(c);
			}
		}
		
		if(attackCountryList.size()==0) {
			return;
		}
		
		int randomCountry = (int) (Math.random() * attackCountryList.size());
		Country country = attackCountryList.get(randomCountry);
		int randomTime = (int) Math.random()*10;
		int i = 0;
		if(country!=null) {
			while(country.getNumOfArmies()>1 && country.hasAdjacentControlledByOthers()
					&& country.getOwner().getId() == player.getId() && i<randomTime) {
				i++;
				int numAttackDice = Math.min(3, country.getNumOfArmies());
				ArrayList<Country> defenseCountryList = country.getAdjacentCountryList();
				Country defenseCountry = null;
				for(Country c:defenseCountryList) {
					if(c.getOwner().getId() != player.getId()) {
						defenseCountry = c;
						break;
					}
				}
				
				while(defenseCountry.getNumOfArmies()!=0 && country.getNumOfArmies()>1) {
					int numDefendDice = Math.min(2, defenseCountry.getNumOfArmies());
					Dice dice = new Dice();
					int[] attackerDice = dice.diceRoll(numAttackDice);
					int[] defenderDice = dice.diceRoll(numDefendDice);
					int[] attackResult = player.attack(attackerDice, defenderDice);
					country.removeArmies(attackResult[0]);
					defenseCountry.removeArmies(attackResult[1]);
					System.out.println("Player "+player.getId()+" Country "+country.getName()+" attack country "+defenseCountry.getName());
					System.out.println("Attack Country "+country.getName()+": "+country.getNumOfArmies()+", "+country.getOwner().getId());
					System.out.println("Defense Country "+defenseCountry.getName()+": "+defenseCountry.getNumOfArmies()+", "+defenseCountry.getOwner().getId());
				
					
					if(defenseCountry.getNumOfArmies()==0) {
						break;
					}
					
					if(country.getNumOfArmies()<=1) {
						break;
					}
					
				}
				if(country.getNumOfArmies()<=0) {
					defenseCountry.getOwner().conquer(country);
					defenseCountry.decreaseArmy();
					country.increaseArmy();
					return;
				}
				if(defenseCountry.getNumOfArmies() == 0) {
					country.decreaseArmy();
					player.conquer(defenseCountry);
					defenseCountry.increaseArmy();
				}
			}
		}
	}

	/**
	 * Fortification phase is implemented in Random behavior
	 * @param player current player who is in fortification phase
	 */	
	@Override
	public void fortificationPhase(Player player) {
		// TODO Auto-generated method stub
		HashMap <Country, ArrayList<Country>> connectedCountryList = new HashMap<Country, ArrayList<Country>>();
		
		for (Country c:player.getCountryList()) {
			if(c.getNumOfArmies()>0) {
				ArrayList<Country> destination = player.getValidDestination(c);
				for (int i=0; i<destination.size(); i++) {
					connectedCountryList.put(c, destination);				
				}
			}
		}
		
		if(connectedCountryList.size()>0) {
			int randomFromCountry =  (int) (Math.random()*connectedCountryList.size());
			Set<Country> fromCountryList =connectedCountryList.keySet();
			Country fromCountry=null;
			int i = 0;
			for(Country c : fromCountryList)
			{
			    if (i == randomFromCountry) {
			    	 fromCountry=c;
			    }
			    i++;
			}
			ArrayList<Country> destination = connectedCountryList.get(fromCountry);
			
			if (destination.size()>0) {
				int randomToCountry =  (int) (Math.random()*destination.size());
				Country toCountry = destination.get(randomToCountry);
				
				int randomQt =  (int) (1 + Math.random()*(fromCountry.getNumOfArmies()-1));
				
				player.fortify(fromCountry.getName() , toCountry.getName(), randomQt);
			}
		}
		
	}

	/**
	 * Setup phase is implemented in Random behavior
	 * @param player current player who is in setup phase
	 */
	@Override
	public void setupPhase(Player player) {
		// TODO Auto-generated method stub
		int leftArmy=player.getLeftArmyNumber();
		for(int i=0; i<leftArmy;i++) {
			java.util.Random rand = new java.util.Random();
		    List<Country> playerCountrylist=player.getCountryList();
			Country randomCountry = player.getCountryList().get(rand.nextInt(playerCountrylist.size()));
			randomCountry.AddArmy();	
		}
	}
}