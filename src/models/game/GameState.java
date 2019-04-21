package models.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;

import models.map.Continent;
import models.map.Country;
import models.map.Map;
import views.game.StateView;

/**
 * class GameState to store and pass the current state of the game
 * @version 3.0
 */
public class GameState extends Observable {

	private File selectedFile;
	private Map map;
	
	private int currentPlayer;
	private List<Player> playerList = new ArrayList<Player>();	// hold players
	private List<Country> destinationCountryList;	// hold phase to switch between map info and current state
	
	private int turns=1;
	private int MAX_TURNS;
	private String mapPath="";
	
	public int getTurns() {
		return turns;
	}

	public void setTurns(int turns) {
		this.turns = turns;
	}

	public int getMAX_TURNS() {
		return MAX_TURNS;
	}

	public enum Phase {
		SETUP,
		REINFORCEMENT,
		ATTACK,
		FORTIFICATION,
		FINISHED
	};
	
	public class PhaseState extends Observable {
		private Phase phase = Phase.SETUP;
		
		public Phase getPhase() { 
			return phase; 
		}
		public void setPhase(Phase p) { 
			phase = p; 
			setChanged();
			notifyObservers();		
		}
		
		public String getPhaseInfo() {
			String phaseInfo = "";
			
			switch(this.phase) {
			case SETUP:
				phaseInfo =  "Assign initial armies to your countries";
				break;
			case REINFORCEMENT:
				phaseInfo = "<html>"+
							"Exchaneg armies using cards<br>"+
							"Assign armies to your country"+
							"</html>";
				break;
			case ATTACK:
				phaseInfo = "<html>"+
							"Attack your neighboring countries"+
							"</html>";
				break;
				
			case FORTIFICATION:
				phaseInfo = "<html>"+
						"Move armies between your country"+
						"</html>";
				break;
			default:
				break;
			
			}
			return phaseInfo;
		}
	}
	
	private PhaseState phaseState = new PhaseState();
	
	/**
	 * Public method to return the current phase of game
	 * @return Phase
	 */
	public Phase getPhase() { 
		return phaseState.getPhase(); 
	}
	
	/**
	 * Public method to return the current phase state of game
	 * @return
	 */
	public PhaseState getPhaseState() {
		return phaseState;
	}
	
	/**
	 * Public method to set the current phase of game as parameter passed
	 * @param phase
	 */
	public void setPhase(Phase phase) {
		System.out.println("Player "+this.getCurrentPlayer().getId()+":"+this.getCurrentPlayer().getStrategy()+" changed to Phase "+ phase.name());
		phaseState.setPhase(phase);	
		if (phase.equals(Phase.FINISHED))
		{
			this.turns=1;

		}
	}

	/**
	 * add phase observer
	 * @param observer
	 */
	public void addPhaseObserver(Observer observer) {
		phaseState.addObserver(observer);
	}
	
	/**
	 * Constructor of class GameState
	 */
	private GameState() {
		this.map = new Map();
	}
	
	
	static private GameState instance = new GameState();
	
	/**
	 * Static public method to get the instance of GameState
	 * @return GameState
	 */
	static public GameState getInstance() {
		return instance;
	}
	
	/**
	 * Public method to check if map is loaded
	 * @return boolean
	 */
	public boolean isMapLoaded() {
		return map.isLoaded();
	}	
	
	/**
	 * check if fortification succeed
	 * @param from the from
	 * @param to the to
	 * @param qt the qt
	 * @return true, if successful
	 */
	public boolean fortify(String from, String to, int qt) {
		return getCurrentPlayer().fortify(from, to, qt);
	}
	
	/**
	 * Public method to check if file is selected
	 * @return File
	 */
	public File getSelectedFile() {
		return this.selectedFile;
	}
	
	/**
	 * Public method to set the file to be selected
	 * @param selectedFile
	 */
	public void setSelectedFile( File selectedFile ) {
		this.selectedFile = selectedFile;
	}
	
	/**
	 * set the map
	 * @param map
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * Public method to get the array list of players
	 * @return ArrayList
	 */
	public List<Player> getPlayerList() {
		return playerList;
	}
	
	/**
	 * Public method to set the array list of players
	 * @param players
	 */
	public void setPlayerList(List<Player> players) {
		this.playerList = players;
		this.currentPlayer = 0;
	}
	
	/**
	 * Public method to check if map is loaded from selected file
	 * @param selectedFile2
	 * @return boolean
	 */
	public boolean loadMapFromFile(File selectedFile2) {
		if(map.loadMapFromFile(selectedFile2)) {
			this.mapPath = selectedFile2.getAbsolutePath();
			return true;
		}
		return false;
	}
	
	/**
	 * Public method to get the map
	 * @return Map
	 */
	public Map getMap() {
		return map;
	}
	
	/**
	 * Randomly assign countries to players
	 */
	public void randomAssignCountry() {
		int player_index=0;
		List<Continent> countinentList = this.map.getContinentList();

		for(Continent continent: countinentList) {//this is try to avoid one play takes over an entire continent in the first round
			List<Country> countryList = continent.getCountryList();  //get all countries from each continent
			
			Collections.shuffle(countryList);

			for(int i = 0; i < countryList.size(); i++) {
				Country c = countryList.get(i);                // loop for get each country of the map
				Player p = this.playerList.get(player_index);  // find the corresponding player by the order of the player
				p.getCountryList().add(c);                     // assign country to each player
				c.setOwner(p);
				
				if(player_index<this.playerList.size()-1) {     //if not all players get a new country in this round
					player_index++;
				}
				else {                                         //if all players get a new counter in this round, start from player 1
					player_index=0;
				}	
			}	
		}	
		setChanged();
		notifyObservers();
	}

	/**
	 * Public method to get initial army number
	 * @return initial number of armies
	 */
	public int getInitialArmyNumber(){
		return Math.round(this.map.getCountryList().size() / this.playerList.size()) + this.playerList.size();
	}
	
	/**
	 * Allocate a number of initial armies to players
	 * place an army in each country
	 */
	public void assignInitialArmy() {		
		for (Player player:this.playerList) {
			for (int i=0; i<getInitialArmyNumber(); i++) {
				Army army = new Army(player);
				player.getArmyList().add(army);				
			}
			for(Country country: player.getCountryList()) {
				country.AddArmy();				
			}		
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Take user selection, set number of players, set strategy to player.
	 * @param num
	 */
	public void assignInitialPlayers(int num, List<JComboBox> playerTypeComboBoxList) {
		assert(playerList.size() == 0); //shouldn't be called with an initialized player list, for future debug
		
		playerList.clear(); // clear player list to make sure no previous record is there
		for(int i = 0; i < num; i++) {
			Player p = new Player();
			p.setId(i);
			JComboBox comboBox=playerTypeComboBoxList.get(i);
			String type=(String)comboBox.getSelectedItem();
			switch (type){
				case "Human":
					p.setStrategy(new Human());
					break;
				case "Aggressive":
					p.setStrategy(new Aggressive());
					break;
				case "Benevolent":
					p.setStrategy(new Benevolent());
					break;
				case "Random":
					p.setStrategy(new Random());
					break;
				case "Cheater":
					p.setStrategy(new Cheater());
			}
			playerList.add(p);
		}
		setChanged();
		notifyObservers();
		
		currentPlayer = 0;
	}

	/**
	 * Get current Player
	 * @return Player
	 */
	public Player getCurrentPlayer() {
		if(playerList.isEmpty()) {
			return null;
		}
		
		return playerList.get(currentPlayer);
	}

	/**
	 * End turn for current player, and set the "currentPlayer" to next player
	 */
	public void endPlayerTurn() {
		Player p=GameState.getInstance().getCurrentPlayer();
		if(p.getConqueredCountryInThisTurn()) {
			p.getNewCard();
		}
		System.out.println("Player "+currentPlayer+" finishes turn "+turns+" with "+
		p.getArmyNumber()+" army and "+p.getCountryList().size()+" country"
		);
		currentPlayer = ++currentPlayer % playerList.size();
		turns++;
		setChanged();
		notifyObservers();
	}

	/**
	 * Initial first Player for RoundRobin
	 */
	public void setFirstPlayer() {
		currentPlayer = 0;
	}
	
	/**
	 * End turn for current player and set the "currentPlayer" to next player in setUp Phase
	 */
	public boolean setUpRoundRobin() {
		if(currentPlayer < playerList.size()-1) {
			currentPlayer++;
			return true;
		}
		else return false;
	}
	
	/**
	 * Find all possible destination countries for the parameter country, and store them in an ArrayList
	 * @param selectedCountry
	 * @return ArrayList
	 */
	public ArrayList<Country> getValidDestination(Country selectedCountry) {		
		assert( selectedCountry.getOwner() == playerList.get(currentPlayer) );// defensive programming
		//if no country is selected, or the owner of selected country is not the current player, return an empty ArrayList
		if ( selectedCountry == null || selectedCountry.getOwner() != playerList.get(currentPlayer) ) 
			return new ArrayList<Country>();
		
		// query the map
		return getCurrentPlayer().getValidDestination(selectedCountry);
	}
	
	/**
	 * reset the game state
	 */
	public static void reset() {
		instance = new GameState();
	}

	public void saveGameToFile(File file) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(file,"UTF-8");

			// write map info
			pw.println("[Map]");
			pw.println(this.mapPath);
			pw.println();
			
			// write player info
			pw.println("[Players]");
			for(Player player:this.playerList) {
				pw.print(player.getId());
				pw.print(","+player.getStrategy().toString());
				for(Card card:player.getCardList()) {
					pw.print(","+card.getCardType().toString());
				}
				pw.println();
			}
			pw.println();
			pw.println("[Continents]"); // write continent info
			for(Continent continent: this.map.getContinentList()) {
				if(continent.getOwner()!=null) {
					// print the owner of continent
					pw.println(continent.getName()+"="+continent.getOwner().getId());
				}
			}
			pw.println();
			
			// write country info
			pw.println("[Territories]");
			for(Country country: this.map.getCountryList()) {
				String countryInfo = country.getName()+","+country.getOwner().getId()+","+
							country.getNumOfArmies();
				pw.println(countryInfo);
			}
			pw.println();
			
			// write game state info
			pw.println("[Phase]");
			pw.println(this.getPhaseState().getPhase().toString()+","+this.currentPlayer);
			pw.println();		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		pw.close();	
	}
	
	/**
	 * load game from file
	 * @param file
	 * @return true if load successfully
	 */
	public boolean loadGameFromFile(File file) {
		playerList.clear();
		map.clear();
		String fileName = file.getName();
		
		String fileType = "";
		String[] nameArray = fileName.split("\\.");
		fileType = nameArray[nameArray.length-1];
		boolean continentBegin=false;
		boolean countryBegin=false;
		boolean playerBegin=false;
		boolean phaseBegin=false;
		if(!fileType.equals("save")) {
			return false;
		}
		try {
			FileReader reader = new FileReader(file);
		    BufferedReader bufferedReader = new BufferedReader (reader);
		    String line;		    
	    	String[] splitLine;
	    	int numLine = 1;
	    	while ((line=bufferedReader.readLine())!=null) {
		    	if(line.isEmpty()) {
		    		continue;
		    	}
		    	if(line.equals("[Map]")) {
		    		String path=bufferedReader.readLine();
		    		File mapFile = new File(path);
		    		loadMapFromFile(mapFile);
		    	}
		    	
		    	else if(line.equals("[Continents]")){
		    		continentBegin = true;
		    		countryBegin=false;
		    		playerBegin=false;
		    		phaseBegin=false;
		    	}
		    	else if(line.equals("[Territories]")) {
		    		countryBegin = true;
		    		continentBegin = false;
		    		playerBegin=false;
		    		phaseBegin=false;
		    	}
		    	else if(line.equals("[Players]")) {
		    		playerBegin=true;
		    		countryBegin = false;
		    		continentBegin = false;
		    		phaseBegin=false;
		    		
		    	}
		    	else if(line.equals("[Phase]")) {
		    		phaseBegin=true;
		    		playerBegin=false;
		    		countryBegin = false;
		    		continentBegin = false;
		    	}
		    	else {
		    		if(playerBegin) {
		    			splitLine = line.split(",");
		    			String strategy = splitLine[1];
		    			Player player = new Player();
		    			switch (strategy){
						case "Human":
							player.setStrategy(new Human());
							break;
						case "Aggressive":
							player.setStrategy(new Aggressive());
							break;
						case "Benevolent":
							player.setStrategy(new Benevolent());
							break;
						case "Random":
							player.setStrategy(new Random());
							break;
						case "Cheater":
							player.setStrategy(new Cheater());
		    			}
		    			
		    			player.setId(Integer.parseInt(splitLine[0]));
		    			
		    			for(int i=2;i<splitLine.length;i++) {
		    				Card card = new Card(player);
		    				card.setCardType(CardType.valueOf(splitLine[i]));
		    				player.getCardList().add(card);
		    			}
		    			playerList.add(player);
		    		}
		    		else if(continentBegin) {
		    			if(!line.equals("")) {
		    				splitLine = line.split("=");
		    				Continent continent = this.map.getContinentByName(splitLine[0]);
		    				Player player = getPlayerByID(Integer.parseInt(splitLine[1]));
		    				continent.setOwner(player);
		    			}
		    		}
		    		else if(countryBegin) {
		    			splitLine = line.split(",");
		    			Country country = this.map.getCountryByName(splitLine[0]);
		    			Player player = getPlayerByID(Integer.parseInt(splitLine[1]));
		    			country.setOwner(player);
		    			int numArmy = Integer.parseInt(splitLine[2]);
		    			for(int i=0;i<numArmy;i++) {
		    				country.increaseArmy();
		    			}
		    			player.getCountryList().add(country);
		    		}
		    		else if(phaseBegin) {
		    			splitLine = line.split(",");
		    			Phase phase = Phase.valueOf(splitLine[0]);
		    			
		    			setPhase(phase);
		    			phaseState.setPhase(phase);
		    			currentPlayer = Integer.parseInt(splitLine[1]);
		    		}
		    	}
		    	numLine++;
	    	}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("error");
			return false;
		}
		StateView.getInstance().addObserver();
		//StateView.getInstance().getMapPanel().addCountryTableForMap(GameState.getInstance().getMap());
		
		setChanged();
		notifyObservers();
		
		phaseState.notifyObservers();
		switch(getPhase()) {
		case REINFORCEMENT:
			StateView.getInstance().showReinforcementView();
			break;
		case ATTACK:
			StateView.getInstance().showAttackView();
			break;
		case FORTIFICATION:
			StateView.getInstance().showFortificationView();
			break;
		default:
			break;
		}
		setChanged();
		notifyObservers();
		return true;
	}

	/**
	 * set path for the map file
	 * @param path map file path
	 */
	public void setMapPath(String path) {
		this.mapPath = path;
	}
	
	/**
	 * get the map file path
	 * @return path of the map file
	 */
	public String getMapPath() {
		return this.mapPath;
	}

	/**
	 * get player by the player's id
	 * @param id given id
	 * @return the target player, null if there is no player with this id
	 */
	public Player getPlayerByID(int id) {
		for(Player p:this.playerList) {
			if(p.getId() == id) {
				return p;
			}
		}
		return null;
	}
}