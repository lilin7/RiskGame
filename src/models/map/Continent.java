package models.map;

import java.util.ArrayList;

import models.game.*;

/**
 * The Class Continent. To create, remove, add the continents to the continent list
 * @version  1.0
 */
public class Continent {
	
	/** The id. */
	private int id;
	
	/** The name. */
	private String name;
	
	/** The country list. */
	private ArrayList<Country> countryList = new ArrayList<Country>();
	
	/** The control value. */
	private int controlValue;
	
	/** The owner. */
	private Player owner;
	
	/** The id generator. */
	private static int idGenerator=0;
	
	/**
	 * Instantiates a new continent.
	 * @param name the name
	 * @param controlValue the control value
	 */
	public Continent(String name, int controlValue) {
		this.name = name;
		this.controlValue = controlValue;
		idGenerator++;
		this.id = idGenerator;
	}
	
	/**
	 * Gets the id.
	 * @return the id
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Gets the name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the country list.
	 * @return the country list
	 */
	public ArrayList<Country> getCountryList() {
		return countryList;
	}
	
	/**
	 * Sets the country list.
	 * @param countryList the new country list
	 */
	public void setCountryList(ArrayList<Country> countryList) {
		this.countryList = countryList;
	}
	
	/**
	 * Gets the control value.
	 * @return the control value
	 */
	public int getControlValue() {
		return controlValue;
	}
	
	/**
	 * Sets the control value.
	 * @param value the new control value
	 */
	public void setControlValue(int value) {
		this.controlValue = value;
	}
	
	/**
	 * Gets the owner.
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}
	
	/**
	 * Sets the owner.
	 * @param owner the new owner
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	/**
	 * Adds the country.
	 * @param country the country
	 */
	public void addCountry(Country country) {
		this.countryList.add(country);
	}
	
	/**
	 * Removes the country by ID.
	 * @param countryID the country ID
	 */
	public void removeCountryByID(int countryID) {
		for(int i=0;i<this.countryList.size();i++) {
			if(countryList.get(i).getID() == countryID) {
				countryList.remove(i);
				return;
			}
		}
	}

	/**
	 * Check ownership.
	 * @return the player
	 */
	public Player checkOwnership() {
		return this.owner;
	}
}