package models.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import models.map.Country;

/**
 * this class implements Strategy interface for Aggressive behavior
 * @version 3.0
 */

public class Aggressive implements Strategy{

	@Override
	public String toString() {
		return "Aggressive";
	}

	/**
	 * Reinforcement phase is implemented in Aggressive behavior
	 * @param player current player who is in reinforcement phase
	 */
	@Override
	public void reinforcementPhase(Player player) {
		int reinforcementArmyNumber = player.CalculateReinforcementArmyNumber();
		int maxCountry = Integer.MIN_VALUE;
		int maxCountryCanAttack = Integer.MIN_VALUE;
		Country country = null;
		Country countryCanAttack = null;
		
		for(Country c:player.getCountryList()) {
			if (c.hasAdjacentControlledByOthers()) {
				if(c.getNumOfArmies()>maxCountryCanAttack) {
					countryCanAttack = c;
					maxCountryCanAttack = c.getNumOfArmies();
				}				
			}
			else {
				if(c.getNumOfArmies()>maxCountry) {
					country = c;
					maxCountry = c.getNumOfArmies();
				}				
			}
		}
		
		player.addReinforcementArmy(reinforcementArmyNumber);
		if(countryCanAttack != null) {
			//System.out.println("country seted");
			country = countryCanAttack;
			for (int i=0; i<reinforcementArmyNumber; i++)
				country.AddArmy();	
		}	
	}
	
	/**
	 * Attack phase is implemented in Aggressive behavior
	 * @param player current player who is in attack phase
	 */
	@Override
	public void attackPhase(Player player) {
		Country country = getStrongestCountry(player);
		if(country != null) {
			while(country.getNumOfArmies()>1 && country.hasAdjacentControlledByOthers()
					&& country.getOwner().getId() == player.getId()) {
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
	 * Fortification phase is implemented in Aggressive behavior
	 * @param player current player who is in fortification phase
	 */	
	@Override
	public void fortificationPhase(Player player) {
		HashMap <Country, ArrayList<Country>> connectedCountryList = new HashMap<Country, ArrayList<Country>>();
		
		for (Country c:player.getCountryList()) {
			if(c.getNumOfArmies()>0) {
				ArrayList<Country> destination = player.getValidDestination(c);
				for (int i=0; i<destination.size(); i++) {
					connectedCountryList.put(c, destination);				
				}
			}
		}	

		int max = Integer.MIN_VALUE;
		List<Country> fromCountries = new ArrayList<Country>();
		Country toCountry = null;
		
		for(Country f: connectedCountryList.keySet()) {
			for(Country t: connectedCountryList.get(f)) {
				if(t.hasAdjacentControlledByOthers() && t.getNumOfArmies()>max) {
					fromCountries.add(f);
					toCountry = t;
					max = t.getNumOfArmies();					
				}
			}
		}
		
		max = Integer.MIN_VALUE;
		Country fromCountry = null;
		for(Country c:fromCountries) {
			if(c.getNumOfArmies()>max) {
				fromCountry = c;
				max = c.getNumOfArmies();
			}
		}
		
		if (fromCountry != null) {
			player.fortify(fromCountry.getName() , toCountry.getName(), fromCountry.getNumOfArmies()-1);
		}				
	}
	
	/**
	 * get attacker country which is able to attack and has the most number of armies
	 * @param player current player
	 * @return attacker strongest country
	 */
	public Country getStrongestCountry(Player player) {
		Country country = null;
		int maxNumArmy = 0;
		for(Country c:player.getCountryList()) {
			if(c.hasAdjacentControlledByOthers()) {
				if(c.getNumOfArmies()>maxNumArmy) {
					maxNumArmy = c.getNumOfArmies();
					country = c;
				}
			}
		}
		if(country.getNumOfArmies()<2) {
			return null;
		}
		return country;
	}

	/**
	 * Setup phase is implemented in Aggressive behavior
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