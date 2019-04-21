package models.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import javax.swing.JFrame;

import models.map.Continent;
import models.map.Country;

/**
 * The Class Player. after a player was created ,
 * actions of assign and remove  all the countries, all the cards, all the armies to the player
 * is possible to do.
 * @version 2.0
 */
public class Player extends Observable {
	/** The id. */
	private int id;

	/** The country list. */
	private ArrayList<Country> countryList = new ArrayList<Country>();
	private ArrayList<Continent> continentList = new ArrayList<Continent>();
	/** The card list. */
	private ArrayList<Card> cardList = new ArrayList<Card>();

	/** The army list. */
	private ArrayList<Army> armyList = new ArrayList<Army>();

	/** The get army for cards. */
	private int getArmyforCards = 0; //number of times player is given army for cards
	//the number of looser

	private Strategy strategy;

	private boolean conqueredCountryInThisTurn = false;

	public Player(Strategy strategy) {
		this.strategy=strategy;
	}

	public Player() {
		this.strategy=new Human();
	}

	public void doStrategySetup(){
		strategy.setupPhase(this);
	}

	public void doStrategyAttack(){
		strategy.attackPhase(this);
	}

	public void doStrategyReinforcement(){
		strategy.reinforcementPhase(this);
	}

	public void doStrategyfortification(){
		strategy.fortificationPhase(this);
	}

	/**
	 * Gets the id.
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the army list.
	 * @return the army list
	 */
	public ArrayList<Army> getArmyList() {
		return armyList;
	}

	/**
	 * Gets the army number.
	 * @return the army number
	 */
	public int getArmyNumber() {
		int num = 0;
		for(Country c:this.countryList) {
			num+=c.getNumOfArmies();
		}
		return num;
	}

	/**
	 * Gets the left army number.
	 * @return the left army number
	 */
	public int getLeftArmyNumber() {
		int left = 0;
		for(Army army:armyList)
			if (army.getCountry() == null)
				left++;
		return left;
	}

	/**
	 * Sets the country list.
	 * @param countryList the new country list
	 */
	public void setCountryList(ArrayList<Country> countryList) {
		this.countryList = countryList;
	}

	/**
	 * Gets the country list.
	 * @return the country list
	 */
	public ArrayList<Country> getCountryList() {
		return countryList;
	}

	/**
	 * Gets the card list.
	 * @return the card list
	 */
	public ArrayList<Card> getCardList() {
		return cardList;
	}

	/**
	 * Sets the army for cards.
	 * @param i the new army for cards
	 */
	public void setArmyforCards(int i) {
		getArmyforCards = i;
	}

	/**
	 * get the continent list
	 */
	public ArrayList<Continent> getContinentList(){
		return this.continentList;
	}
	/**
	 * Attack from attacker to defender
	 * @param attacker the attacker
	 * @param defender the defender
	 * @return true, if successful
	 */
	public boolean attack(Country attacker,Country defender) {
		//to do check connections between countries, roll dice,determine winner
		//reassign country and armies
		return false;
	}

	/**
	 * This method add armies to the player according to the reinforcement rules.
	 * @param reinforcementArmyNumber TODO
	 *
	 */
	public void addReinforcementArmy(int reinforcementArmyNumber) {
		for(int i=0; i<reinforcementArmyNumber; i++) {
			Army army = new Army(this);
			armyList.add(army);
		}
	}

	/**
	 * This method is to calculate and give a number of armies
	 * to the player at the beginning of reinforcements phase.
	 * @return the number of armies given to player
	 */
	public int CalculateReinforcementArmyNumber(){
		int armyNumber = Math.floorDiv(countryList.size(),3);

		ArrayList<Continent> ContinentList = GameState.getInstance().getMap().getContinentList();
		for(Continent continent: ContinentList){
			if(continent.getOwner() != null && continent.getOwner().equals(this)){
				armyNumber += continent.getControlValue();
			}
		}
		armyNumber = Math.max(armyNumber, 3);
		return armyNumber;
	}

	/**
	 * This method activated exchange card phase.
	 * @return true if player has to exchange card for army
	 */
	public boolean enforceExchangeCard() {
		return (cardList.size()>=5);
	}

	/**
	 * This method check the possibility of performing card exchange action.
	 * @return true if player has to exchange card for army
	 */
	public boolean isPossibleExchangeCard() {
		int[] cardTypeNumber = cardTypeNumber();
		return (Math.max(cardTypeNumber[0], Math.max(cardTypeNumber[1], cardTypeNumber[2])) >= 3)
				|| (Math.min(cardTypeNumber[0], Math.min(cardTypeNumber[1], cardTypeNumber[2])) >= 1);
	}

	/**
	 * This method get the type of each card.
	 * @return number of each card type
	 */
	public int[] cardTypeNumber() {
		int[] cardTypeNumber = {0,0,0};
		for(Card card: cardList){
			cardTypeNumber[card.getCardType().getCardTypeCode()] += 1;
		}
		return cardTypeNumber;
	}
	
	/**
	 * This method automatically exchange 3 cards for army
	 */
	public void autoExchangeCardforArmy() {

		if(this.getCardList().size()<=4){//if with less than 5 cards, don't do this
			return;
		}
		
		System.out.println("sucessfully ecxchange card");

		//first give them the army
		int armyForCard = (getArmyforCards += 1) * 5;

		for(int i=0; i<armyForCard; i++) {
			Army army = new Army(this);
			armyList.add(army);
		}
		//now remove the cards, either remove 3 of them with the same type, or remove one of each
		int[] cardTypeNumber = cardTypeNumber();
		for (int counter=0; counter<3; counter++) {
			if (cardTypeNumber[counter]>=3){
				for (int i=0; i<3; i++){
					removeCard(counter);
				}
				return;
			}
		}
		removeCard(0);removeCard(1);removeCard(2);
	}

	/**
	 * This method exchange 3 cards for army.
	 * @param toremovecards is the list of cards to be changed
	 *@return the number of armies
	 */
	public int exchangeCardforArmy(List<Card> toremovecards) {
		removeCards(toremovecards);
		int armyForCard = (getArmyforCards += 1) * 5;

		for(int i=0; i<armyForCard; i++) {
			Army army = new Army(this);
			armyList.add(army);
		}
		return armyForCard;
	}

	/**
	 * This method remove a card from cardList of player.
	 * @param cardTypeCode
	 */
	public void removeCard(int cardTypeCode) {
		for(Card card: cardList){
			if (card.getCardType().getCardTypeCode() == cardTypeCode) {
				cardList.remove(card);

				setChanged();
				notifyObservers(this);
				return;
			}
		}
	}
	/**
	 * This method remove multiple cards at the same time
	 * @param toremovecards is the list of cards to be removed for exchange
	 */
	public void removeCards(List<Card> toremovecards) {
		cardList.removeAll(toremovecards);
		setChanged();
		notifyObservers(this);
	}

	/**
	 * This method gets a random new card for the player
	 */
	public void getNewCard()
	{
		Card c=new Card(this);
		this.cardList.add(c);

		setChanged();
		notifyObservers(this);
	}

	/**
	 * Execute the fortification move
	 * @param from
	 * @param to
	 * @param qt
	 * @return boolean, true if the fortification order was executed, false in case of error
	 */
	public boolean fortify(String from, String to, int qt) {
		Country source = null, dest = null;
		// validate that the player owns both countries
		for(Country country: countryList){
			if (country.getName() == from) {
				source = country;
			}
			if (country.getName() == to) {
				dest = country;
			}
		}

		if (source == null || dest == null) {
			return false;
		}

		// Make sure there's enough armies to move
		int realQt = Math.min( source.getNumOfArmies()-1, qt );

		// Move armies
		for(int i = 0; i<realQt; i++){
			source.decreaseArmy();
			dest.increaseArmy();
		}

		// error if move was invalid, made the biggest move
		return realQt == qt;
	}

	/**
	 * move armies during attack phase
	 * @param from
	 * @param to
	 * @param qt
	 */
	public void moveArmies(Country from, Country to, int qt) {
		Country source = from, dest = to;
		int realQt = Math.min( source.getNumOfArmies()-1, qt );

		// Move armies
		for(int i = 0; i<realQt; i++){
			source.decreaseArmy();
			dest.increaseArmy();
		}
	}

	/**
	 * To take input country, return which countries can be the valid destination of this country
	 * @param selectedCountry
	 */
	public ArrayList<Country> getValidDestination(Country selectedCountry) {
		ArrayList<Country> valid = new ArrayList<Country>();
		if ( selectedCountry.getOwner() != this) return valid;
		ArrayList<Country> toIterate = new ArrayList<Country>();
		toIterate.add(selectedCountry);

		while(!toIterate.isEmpty()) {
			// remove first element
			Country current = toIterate.get(0);
			toIterate.remove(0);

			// if it's a valid country
			if (current.getOwner() == this && valid.indexOf(current) == -1 ) {
				// keep it
				valid.add(current);

				// flag nei for validation
				for( Country nei : current.getAdjacentCountryList()) {
					if ( toIterate.indexOf(nei) == -1 && valid.indexOf(nei) == -1) {
						toIterate.add(nei);
					}
				}
			}
		}

		valid.remove(selectedCountry);
		return valid;
	}
	/**
	 * this is the attack method. it compares the value of dice
	 * and return the number of looser army for players
	 * @param attackerDice the number of attacker dice
	 * @param defenderDice the number of defender dice
	 * @return it returns an array with value of number of looser army for both players
	 */
	public int[] attack(int[] attackerDice,int[] defenderDice) {
    	int numberAttacerLoser=0;
    	int numberDefenderLoser=0;

    	int maxValueAttacker=0;
    	int maxValueDefender=0;

    	Arrays.sort(attackerDice);
    	Arrays.sort(defenderDice);

    	int j=attackerDice.length;

    	for(int i=defenderDice.length-1; i>=0; i--) {
    		j=j-1;
    		maxValueAttacker=attackerDice[j];
    		maxValueDefender=defenderDice[i];

    		//this step defines which player will lose his army.
    		if (maxValueDefender>=maxValueAttacker) {
    			numberAttacerLoser++;
    		}
    		else {
    			numberDefenderLoser++;
    		}
    	}
    	 int[] result= {numberAttacerLoser,numberDefenderLoser};
    	 return result;
    }
	/**
	 * find out that if the player can attack
	 * @return boolean
	 */
	public boolean isAttackPossible() {
		for (Country c: countryList) {
			if (c.getNumOfArmies() > 1 && c.hasAdjacentControlledByOthers())
				return true;
		}
		return false;
	}

	/**
	 * find out that if the player can attack
	 * @return boolean
	 */
	public boolean conquer(Country country) {
		if (country.getNumOfArmies() == 0) {
			Player originalOwner = country.getOwner();
			originalOwner.getCountryList().remove(country);
			country.setOwner(this);
			this.countryList.add(country);
			this.conqueredCountryInThisTurn = true;
			return true;
		}
		return false;
	}

	/**
	 * reset the conquered country in this turn
	 */
	public void resetConqueredCountryInThisTurn() {
		this.conqueredCountryInThisTurn = false;
	}

	/**
	 * get conquered country in this turn
	 * @return boolean
	 */
	public boolean getConqueredCountryInThisTurn() {
		return this.conqueredCountryInThisTurn;
	}

	/**
	 * cheater Conquer a country
	 * @param country
	 */
	public void cheaterConquer(Country country) {
		Player originalOwner = country.getOwner();
		originalOwner.getCountryList().remove(country);
		country.setOwner(this);
		this.countryList.add(country);
	}

	/**
	 * get the strategy
	 * @return
	 */
	public Strategy getStrategy() {
		return strategy;
	}

	/**
	 * 
	 * @param strategy
	 */
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
}