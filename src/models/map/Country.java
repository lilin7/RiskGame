package models.map;

import models.game.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class Country. To create, remove, add the countries to the country list
 * @version  1.0
 */
public class Country {
	
	/** The name. */
	private String name;
	
	/** The continent. */
	private Continent continent;
	
	/** The owner. */
	private Player owner;
	
	/** The location X. */
	private int locationX;
	
	/** The location Y. */
	private int locationY;
	
	/** The adjacent country list. */
	private ArrayList<Country> adjacentCountryList = new ArrayList<Country>();
	
	/** The army list. */
	private ArrayList<Army> armyList = new ArrayList<Army>();	
	
	/** The id. */
	private int id;
	
	/** The id generator. */
	private static int idGenerator=0;

	/**
	 * Instantiates a new country.
	 *
	 * @param name the name
	 */
	public Country(String name) {
		this.name = name;
		idGenerator++;
		this.id = idGenerator;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the continent.
	 *
	 * @return the continent
	 */
	public Continent getContinent() {
		return continent;
	}
	
	/**
	 * Sets the continent.
	 *
	 * @param continent the new continent
	 */
	public void setContinent(Continent continent) {
		this.continent = continent;
	}
	
	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}
	
	/**
	 * Sets the owner.
	 *
	 * @param owner the new owner
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	/**
	 * Gets the num of armies.
	 *
	 * @return the num of armies
	 */
	public int getNumOfArmies() {
		return armyList.size();
	}
	
	/**
	 * Gets the location X.
	 *
	 * @return the location X
	 */
	public int getLocationX() {
		return locationX;
	}
	
	/**
	 * Sets the location X.
	 *
	 * @param locationX the new location X
	 */
	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}
	
	/**
	 * Gets the location Y.
	 *
	 * @return the location Y
	 */
	public int getLocationY() {
		return locationY;
	}
	
	/**
	 * Sets the location Y.
	 *
	 * @param locationY the new location Y
	 */
	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}
	
	/**
	 * Gets the adjacent country list.
	 *
	 * @return the adjacent country list
	 */
	public ArrayList<Country> getAdjacentCountryList() {
		return adjacentCountryList;
	}
	
	/**
	 * Sets the adjacent country list.
	 *
	 * @param adjacentCountryList the new adjacent country list
	 */
	public void setAdjacentCountryList(ArrayList<Country> adjacentCountryList) {
		this.adjacentCountryList = adjacentCountryList;
	}
	
	/**
	 * Adds the adjacent country.
	 *
	 * @param country the country
	 */
	public void addAdjacentCountry(Country country) {
		this.adjacentCountryList.add(country);
		
	}
	
	/**
	 * check if the country has adjacent country which belongs to other players
	 * @return boolean
	 */
	public boolean hasAdjacentControlledByOthers() {
		for(Country c:adjacentCountryList) {
			if (!c.getOwner().equals(owner))
				return true;
		}
		return false;	
	}
	
	/**
	 * Removes the adjacent country by ID.
	 * @param countryID the country ID
	 */
	public void removeAdjacentCountryByID(int countryID) {
		for(Country country:this.adjacentCountryList) {
			if(country.getID() == countryID) {
				this.adjacentCountryList.remove(country);
				return;
			}
		}
	}
	
	/**
	 * Adds the army.
	 */
	public void AddArmy() {
		for (Army army:owner.getArmyList()) {
			if(army.getCountry() == null) {
				army.setCountry(this);
				armyList.add(army);
				break;
			}
		}
	}	
	
	/**
	 * Decrease one Army of this country.
	 */
	public void decreaseArmy() {
		if(armyList.size()>1){
			armyList.remove(1);
		}		
	}
	
	/**
	 * Increase One Army to this country.
	 */
	public void increaseArmy() {
		Army army = new Army(owner);
		owner.getArmyList().add(army);
		armyList.add(army);
	}
	
	/**
	 * Remove a number of Armies from this country.
	 * @param armiesNo
	 */
	public void removeArmies(int armiesNo) {
		int armiesNumber = armiesNo;
		while(armyList.size()>0 && armiesNumber>0){
			Army army = armyList.get(0);
			owner.getArmyList().remove(army);
			armyList.remove(army);
			armiesNumber--;
		}		
	}	
}
