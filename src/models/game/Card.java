package models.game;

import java.util.Random;

/**
 * The Class Card.
 * @version 2.0
 */
public class Card {
	/** The player. */
	private Player player;
	
	/** The card type. */
	private CardType cardType;
	
	/**
	 * Gets the player.
	 *
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
	 * Gets the card type.
	 * @return the card type
	 */
	public CardType getCardType() {
		return cardType;
	}
	
	/**
	 * Sets the card type.
	 * @param cardType the new card type
	 */
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	/**
	 * Instantiates a new card.
	 * @param player the player
	 */
	public Card(Player player) {
		this.player = player;
		Random randomizer = new Random();
		int rand = randomizer.nextInt(3);
		switch(rand) {
		case 0: 
			cardType = CardType.INFANTRY;
			break;
		case 1:
			cardType = CardType.CAVALRY;
			break;
		case 2:
			cardType = CardType.ARTILLERY;
			break;
		default:
			break;
		}
	}
}