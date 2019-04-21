package models.game;

public enum CardType {
	/** The infantry. */
	INFANTRY(0),
	
	/** The cavalry. */
	CAVALRY(1),
	
	/** The artillery. */
	ARTILLERY(2);
    
	/** The card type code. */
	private final int cardTypeCode;
    
	/**
	 * Instantiates a new card type.
	 * @param cardTypeCode the card type code
	 */
	private CardType(int cardTypeCode) {
        this.cardTypeCode = cardTypeCode;
    }
    
	/**
	 * Gets the card type code.
	 * @return the card type code
	 */
	public int getCardTypeCode(){
    	return cardTypeCode;
    }
}