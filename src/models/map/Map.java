package models.map;


import java.util.*;

import models.game.Player;

import java.io.*;

/**
 * This class is the model for map. 
 * It defines the basic information and behaviors of a map, and it is observed by MapEditorView.
 * @version 1.0
 * @see views.map.MapEditorView
 */

public class Map extends Observable {
	private final int FILE_HEAD_LINE_NUMBER = 7;

	private String author;
	private String image;
	private String wrap;
	private String scroll;
	private String warn;
	private boolean loaded;
	
	private ArrayList<Continent> continentList;
	private ArrayList<Country> countryList;
	
	/**
	 * Constructor to create a map
	 * It initializes the basic information of a map
	 */
	public Map() {
		loaded = false;
		author="";
		image="";
		wrap = "no";
		scroll = "none";
		warn = "no";
		loaded = false;
		continentList = new ArrayList<Continent>();
		countryList = new ArrayList<Country>();
	}
	
	/**
	 * This method gets the author's name of the map
	 * @return The author's name
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * This method sets the author's name by a given name
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * This method gets the path of the map's image file
	 * @return The image file's path of the map
	 */
	public String getImage() {
		return image;
	}
	
	/**
	 * This method sets the image file's path as the given path
	 * @param image The image file's path
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * This method gets the map's wrap type
	 * @return The wrap type of the map
	 */
	public String getWrap() {
		return wrap;
	}
	
	/**
	 * This method sets the wrap type based on given type
	 * @param wrap The given wrap type
	 */
	public void setWrap(String wrap) {
		this.wrap = wrap;
	}
	
	/**
	 * This method gets the scroll type of the map
	 * @return The map's scroll type
	 */
	public String getScroll() {
		return scroll;
	}
	
	/**
	 * Set the scroll typo based on given type
	 * @param scroll The given scroll type
	 */
	public void setScroll(String scroll) {
		this.scroll = scroll;
	}
	
	/**
	 * This method gets the warn type of the map
	 * @return The warn type of the map
	 */
	public String getWarn() {
		return warn;
	}
	
	/**
	 * Set the map's warn type as the given type
	 * @param warn The given type of warn
	 */
	public void setWarn(String warn) {
		this.warn = warn;
	}
	
	/**
	 * Get all continents in this map
	 * @return The list of continents in this map
	 */
	public ArrayList<Continent> getContinentList() {
		return continentList;
	}
	
	/**
	 * Set the map's continents based on a given continents list
	 * @param continentList A lists of continents
	 */
	public void setContinentList(ArrayList<Continent> continentList) {
		this.continentList = continentList;
	}
	
	/**
	 * Get all countries in this map
	 * @return The list of countries in this map
	 */
	public ArrayList<Country> getCountryList() {
		return countryList;
	}
	
	/**
	 * Set the map's countries based on a given countries list
	 * @param countryList A lists of countries
	 */
	public void setCountryList(ArrayList<Country> countryList) {
		this.countryList = countryList;
	}
	
	/**
	 * Add a continent to the map, also notify the MapEditorView that the map has been changed.
	 * @param continent The continent need to be added
	 */
	public void addContinent(Continent continent) {
		this.continentList.add(continent);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Add a country to the map, also notify the MapEditorView that the map has been changed.
	 * @param country The country need to be added
	 */
	public void addCountry(Country country) {
		country.getContinent().addCountry(country);
		this.countryList.add(country);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Check if the map has been successfully loaded from file
	 * @return True if the map has been loaded from file, false if it hasn't
	 */
	public boolean isLoaded() {
		return loaded;
	}
	
	/**
	 * Get the continent in this map based on the continent's name
	 * @param continentName The continent's name need to be searched in the map
	 * @return The target continent object; null if there is no such continent
	 */
	public Continent getContinentByName(String continentName) {
		for(int i=0;i<continentList.size();i++) {
			if(continentList.get(i).getName().equals(continentName)){
				return continentList.get(i);
			}
		}
		return null;
	}
	/**
	 * Get the continent in this map based on the continent's id
	 * @param continentID The continent's id need to be searched in the map
	 * @return The target continent object; null if there is no such continent
	 */
	public Continent getContinentByID(int continentID) {
		for(int i=0;i<continentList.size();i++) {
			if(continentList.get(i).getID() == continentID){
				return continentList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Get the country in this map based on the country's name
	 * @param countryName The country's name need to be searched in the map
	 * @return The target country object; null if there is no such country
	 */
	public Country getCountryByName(String countryName) {
		for(int i=0;i<this.countryList.size();i++) {
			if(this.countryList.get(i).getName().equals(countryName)){
				System.out.println(this.countryList.get(i).getName());
				return this.countryList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Get the country in this map based on the country's id.
	 * @param countryID The country's id need to be searched in the map
	 * @return The target country object; null if there is no such country
	 */
	
	public Country getCountryByID(int countryID) {
		for(int i=0;i<this.countryList.size();i++) {
			if(this.countryList.get(i).getID()==countryID){
				return this.countryList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Update a continent's name and value in this map. 
	 * In addition, notify the MapEditorView that the map has been changed.
	 * @param continentID The id of the continent which is to be updated
	 * @param name The new name of the continent
	 * @param value The new value of the continent
	 */
	public void updateContinentByID(int continentID, String name, int value) {
		for(int i=0;i<continentList.size();i++) {
			if(continentList.get(i).getID() == continentID){
				continentList.get(i).setName(name);
				continentList.get(i).setControlValue(value);
				setChanged();
				notifyObservers();
			}
		}
	}
	
	/**
	 * Update a country's name and continent which the country belongs to in this map. 
	 * In addition, notify the MapEditorView that the map has been changed.
	 * @param countryID The id of the country which is to be updated
	 * @param name The new name of the country
	 * @param continentName The new continent's name which the country belongs to
	 */
	public void updateCountryByID(int countryID, String name, String continentName) {
		for(int i=0;i<countryList.size();i++) {
			if(countryList.get(i).getID() == countryID){
				Country country = countryList.get(i);
				country.setName(name);
				
				Continent preContinent = countryList.get(i).getContinent();
				preContinent.removeCountryByID(countryID);
				
				Continent continent = getContinentByName(continentName);
				continent.addCountry(country);
				country.setContinent(continent);
				
				setChanged();
				notifyObservers();
			}
		}
	}
	
	/**
	 * Get the number of continents in this map
	 * @return The number of continents in this map
	 */
	public int getContinentNumber() {
		return this.continentList.size();
	}
	
	/**
	 * Get the number of countries in this map
	 * @return The number of countries in this map
	 */
	public int getCountryNumber() {
		return this.countryList.size();
	}
	
	/**
	 * Remove a continent from the map, and also remove all countries belong to this continent.
	 * In addition, notify the MapEditorView that the map has been changed.
	 * @param id The id of the continent to be removed
	 */
	public void removeContinentByID(int id) {
		for(int i=0;i<continentList.size();i++) {
			if(continentList.get(i).getID() == id){
				Continent continent = continentList.get(i);
				
				while(continent.getCountryList().size()!=0) {
					int countryID = continent.getCountryList().get(0).getID();
					removeCountryByID(countryID);
				}
				
				
				continentList.remove(i);
				setChanged();
				notifyObservers();
				return;
			}
		}
	}
	
	/**
	 * Remove a country from the map, and also remove all connection relationships of this country.
	 * In addition, notify the MapEditorView that the map has been changed.
	 * @param id The id of the country to be removed
	 */
	public void removeCountryByID(int id) {
		for(int i=0;i<this.countryList.size();i++) {
			if(this.countryList.get(i).getID()==id){
				Continent continent = countryList.get(i).getContinent();
				// remove the country from its continent's country list
				continent.removeCountryByID(id);
				
				// remove the country from its adj country's adj country list
				for(Country country:this.countryList) {
					for(Country adjCountry:country.getAdjacentCountryList()) {
						adjCountry.removeAdjacentCountryByID(id);
					}
				}
				
				// remove the country from the map's country list
				countryList.remove(i);
				
				setChanged();
				notifyObservers();
				return;
			}
		}
	}
	
	/**
	 * Add a connection between two countries.
	 * In addition, notify the MapEditorView that the map has been changed.
	 * @param countryID1 The id of the first country
	 * @param countryID2 The id of the second country
	 */
	public void addConnection(int countryID1, int countryID2) {
		Country country1 = this.getCountryByID(countryID1);
		Country country2 = this.getCountryByID(countryID2);
		
		country1.addAdjacentCountry(country2);
		country2.addAdjacentCountry(country1);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Remove a connection between two countries.
	 * In addition, notify the MapEditorView that the map has been changed.
	 * @param countryID1 The id of the first country
	 * @param countryID2 The id of the second country
	 */
	public void removeConnection(int countryID1, int countryID2) {
		Country country1 = this.getCountryByID(countryID1);
		Country country2 = this.getCountryByID(countryID2);
		country1.removeAdjacentCountryByID(countryID2);
		country2.removeAdjacentCountryByID(countryID1);
		
		setChanged();
		notifyObservers();
		
	}
	
	/**
	 * Load and store map information from a .map file
	 * @param mapFile The file's path
	 * @return True if the file is valid map file, false if the file is not
	 */
	public boolean loadMapFromFile(File mapFile) {
		this.clear();
		
		boolean continentBegin = false;
		boolean countryBegin = false;
		
		try {
			String fileName = mapFile.getName();

			String fileType = "";
			String[] nameArray = fileName.split("\\.");
			fileType = nameArray[nameArray.length-1];
			
			if(!fileType.equals("map")) {
				return false;
			}
			
			FileReader reader = new FileReader(mapFile);
		    BufferedReader bufferedReader = new BufferedReader (reader);
		    String line;
		    int lineNum = 1;

	    	String[] splitLine;
	    	Hashtable<Country,String[]> connectivityHashTable = new Hashtable<Country,String[]>();
	    	
	    	
		    while ((line=bufferedReader.readLine())!=null) {
		    	if(line.isEmpty()) {
		    		continue;
		    	}
		    	if(lineNum<this.FILE_HEAD_LINE_NUMBER) {
			    	switch(lineNum) {
			    	case 1: 
			    		if(!line.equals("[Map]")) {
			    			return false;
			    		}
			    		break;
			    	case 2:
			    		splitLine = line.split("=");
			    		if(splitLine.length!=2) {
	    					return false;
	    				}
			    		if(!splitLine[0].equals("author")) {
			    			return false;
			    		}
			    		if(splitLine[1].equals("")) {
			    			return false;
			    		}
			    		this.author = splitLine[1];
			    		break;
			    	case 3:
			    		splitLine = line.split("=");
			    		if(splitLine.length!=2) {
	    					return false;
	    				}
			    		if(!splitLine[0].equals("image")) {
			    			return false;
			    		}
			    		if(splitLine[1].equals("")) {
			    			return false;
			    		}
			    		this.image = splitLine[1];
			    		break;
			    		
			    	case 4:
			    		splitLine = line.split("=");
			    		if(splitLine.length!=2) {
	    					return false;
	    				}
			    		if(!splitLine[0].equals("wrap")) {
			    			return false;
			    		}
			    		if(splitLine[1].equals("")) {
			    			return false;
			    		}
			    		this.wrap = splitLine[1];
			    		break;
			    	case 5:
			    		splitLine = line.split("=");
			    		if(splitLine.length!=2) {
	    					return false;
	    				}
			    		if(!splitLine[0].equals("scroll")) {
			    			return false;
			    		}
			    		if(splitLine[1].equals("")) {
			    			return false;
			    		}
			    		this.scroll = splitLine[1];
			    		break;
			    	case 6:
			    		splitLine = line.split("=");
			    		if(splitLine.length!=2) {
	    					return false;
	    				}
			    		if(!splitLine[0].equals("warn")) {
			    			return false;
			    		}
			    		if(splitLine[1].equals("")) {
			    			return false;
			    		}
			    		this.warn = splitLine[1];
			    		break;
			    	}
		    	}
		    	
		    	else {
		    		if(!continentBegin && !countryBegin) {
			    		if(!line.equals("[Continents]")) {
			    			return false;
			    		}
			    		continentBegin = true;
		    		}
		    		else if(continentBegin && !countryBegin) {
		    			if(line.equals("[Territories]")) {
		    				continentBegin = false;
		    				countryBegin = true;
		    			}
		    			else {
		    				splitLine = line.split("=");
		    				if(splitLine.length!=2) {
		    					return false;
		    				}
		    				if(this.checkDuplicateContinentName(splitLine[0], -1)) {
		    					return false;
		    				}
		    				int value = Integer.parseInt(splitLine[1]);
		    				if(value<=0) {
		    					return false;
		    				}
		    				continentList.add(new Continent(splitLine[0], value));
		    			}
		    		}
		    		else if(countryBegin && !continentBegin) {
		    			splitLine = line.split(",");
		    			if(this.checkDuplicateCountryName(splitLine[0], -1)) {
		    				return false;
		    			}
		    			if(splitLine.length<=4) {
		    				return false;
		    			}
		    			Country country = new Country(splitLine[0]);
		    			
		    			int locationX = Integer.parseInt(splitLine[1]);
		    			int locationY = Integer.parseInt(splitLine[2]);
		    			country.setLocationX(locationX);
		    			country.setLocationY(locationY);
		    			
		    			String continentName = splitLine[3];
		    			Continent continent = this.getContinentByName(continentName);
		    			if(continent == null) {
		    				return false;
		    			}
		    			country.setContinent(continent);
		    			continent.addCountry(country);
		    			
		    			// the connectivity part begins
		    			// first store the information in an ArrayList
		    			// after finish reading the countries, add the connection relationship
		    			int numConnectedCountry = splitLine.length-4;
		    			if(numConnectedCountry<=0) {
		    				return false;
		    			}
		    			
		    			String[] connectedCountryNameArray = new String[numConnectedCountry];
		    			System.arraycopy(splitLine, 4, connectedCountryNameArray, 0, numConnectedCountry);
		    			
		    			connectivityHashTable.put(country, connectedCountryNameArray);
		    			this.countryList.add(country);
		    		}
		    	}
		    	lineNum++;
		    }
		    
		    for(int i=0;i<this.countryList.size();i++) {
		    	Country country = this.countryList.get(i);
		    	String[] connectedCountryNameArray = connectivityHashTable.get(country);
		    	for(int j = 0;j<connectedCountryNameArray.length;j++) {
		    		Country connectedCountry = this.getCountryByName(connectedCountryNameArray[j]);
		    		if(connectedCountry == null) {
		    			return false;
		    		}
		    		if(connectedCountryNameArray[j].equals(country.getName())) {
		    			return false;
		    		}
		    		country.addAdjacentCountry(connectedCountry);
		    	}
		    }
		}
		catch (Exception e) {
			return false;
		}
		
		if(!this.isValid()) {
			return false;
		}
		loaded = true;
		setChanged();
		notifyObservers();
		return true;
	}
	
	/**
	 * Check if there is a continent has the same name except itself in the map
	 * @param name The continent's name need to be checked
	 * @param id The continent's id
	 * @return True if there is a duplicate name, false if there is no duplicate name
	 */	
	public boolean checkDuplicateContinentName(String name, int id) {
		for(int i=0;i<this.continentList.size();i++) {
			Continent continent = continentList.get(i);
			if(name.equals(continent.getName()) && continent.getID()!=id) {
				return true;
			}	
		}
		return false;
	}
	
	/**
	 * Check if there is a country has the same name except itself in the map
	 * @param name The country's name need to be checked
	 * @param id The country's id
	 * @return True if there is a duplicate name, false if there is no duplicate name
	 */
	public boolean checkDuplicateCountryName(String name, int id) {
		for(int i=0;i<this.countryList.size();i++) {
			Country country = countryList.get(i);
			if(name.equals(country.getName()) && country.getID()!=id) {
				return true;
			}	
		}
		return false;
	}
	
	/**
	 * Save the map to a .map file
	 * @param file
	 * @return True if the map successfully saved, false if it is not.
	 */
	public boolean saveMapToFile(File file) {
		if(!this.isValid()) {
			return false;
		}
		PrintWriter pw;

		try {
			pw = new PrintWriter(file,"UTF-8");

			// write basic info
			pw.println("[Map]");
			pw.println("author="+this.author);
			pw.println("image="+this.image);
			pw.println("wrap="+this.wrap);
			pw.println("scroll="+this.scroll);
			pw.println("warn="+this.warn);
			pw.println();
			
			
			// write continent info
			pw.println("[Continents]");
			for(Continent continent: this.continentList) {
				pw.println(continent.getName()+"="+continent.getControlValue());
			}
			pw.println();
			
			// write country info
			pw.println("[Territories]");
			for(Country country: this.countryList) {
				String countryInfo = country.getName()+","+country.getLocationX()+","+
							country.getLocationY()+","+country.getContinent().getName();
				for(Country adjCountry: country.getAdjacentCountryList()) {
					countryInfo+=","+adjCountry.getName();
				}
				pw.println(countryInfo);
			}
			pw.println();
		}
		catch(Exception e) {
			return false;
		}
		pw.close();
		return true;
	}
	
	/**
	 * To check if the map is a valid map
	 * @return True if it is valid, false if not
	 */
	public boolean isValid() {
		// check basic info
		if(this.author.equals("") || this.image.equals("") || 
			this.wrap.equals("") || this.scroll.equals("") ||
			this.warn.equals("")) {
					return false;
			}
		
		// check the number of continent
		if(this.getContinentNumber() <= 0) {
			return false;
		}
		
		// check the duplicate of continent
		for(Continent c:this.continentList) {
			if(this.checkDuplicateContinentName(c.getName(), c.getID())) {
				return false;
			}
		}
		
		// check the number of country
		if(this.getCountryNumber()<=0) {
			return false;
		}
		
		// check the number of country in the continent{
		for(Continent c:this.continentList) {
			if(c.getCountryList().size()<=0) {
				return false;
			}
		}
		
		// check the duplicate of continent
		for(Country c:this.countryList) {
			if(this.checkDuplicateCountryName(c.getName(), c.getID())) {
				return false;
			}
		}
		
		// check if the map is connected
		if(!this.isConnected()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Check if the map is a connected graph
	 * @return True if the map is connected, false if it is not
	 */
	public boolean isConnected() {
    	if(countryList.size()<=1) {
    		return false;
    	}
		Hashtable<String,String> countryVisited = new Hashtable<String,String>();
    	for(Country country:this.countryList) {
    		countryVisited.put(country.getName(), "unvisited");
    	}
    	Country startCountry = this.countryList.get(0);
    	dfs(startCountry,countryVisited);
    	if(countryVisited.containsValue("unvisited")) {
    		return false;
    	}
    	return true;
	}
	
	/**
	 * dfs for country list
	 * @param country
	 * @param countryVisited
	 */
	public void dfs(Country country, Hashtable<String,String> countryVisited){ 
	    String name = country.getName();
	    if(countryVisited.get(name).equals("visited")){
	    	return;
	    }    
	    countryVisited.put(name,"visited");    
	    for(Country c:country.getAdjacentCountryList()){
	    	if(countryVisited.get(c.getName()).equals("unvisited")){
	    		dfs(c,countryVisited);
	    	}
	    }
	}

	/**
	 * find out if player parameter is the owner of the whole map 
	 * @param player
	 * @return
	 */
	public boolean mapOwner(Player player) {
		for(Country c: countryList) {
			if(!c.getOwner().equals(player))
				return false;
		}
		return true;
	}
	
	/**
	 * Clear all the information of the map.
	 * In addition, notify the MapEditorView that the map has been changed.
	 */
	public void clear() {
		this.author="";
		this.image="";
		this.wrap = "no";
		this.scroll = "none";
		this.warn = "no";
		this.loaded = false;
		
		this.continentList.clear();
		this.countryList.clear();
		this.loaded = false;
		
		setChanged();
		notifyObservers();
	}
}