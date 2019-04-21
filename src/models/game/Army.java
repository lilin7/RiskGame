package models.game;

import models.map.Country;

/**
 * The Class Army. Set and get army according to the rules of risk game
 * @version 1.0
 */
public class Army {
	/** The player. */
	private Player player;
	
	/** The country. */
	private Country country;
	
	/**
	 * Gets the player.
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Sets the player.
	 * @param player the new player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * Gets the country.
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}
	
	/**
	 * Sets the country.
	 * @param country the new country
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
	
	/**
	 * Instantiates a new army.
	 * @param player the player
	 */
	public Army(Player player) {
		super();
		this.player = player;
	}
}