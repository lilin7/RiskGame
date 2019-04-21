package views.map;


import java.awt.Font;
import java.util.ArrayList;

import javax.swing.*;

import models.game.Player;
import models.map.Country;
import models.map.Map;
/**
 * the class for showing country in a table
 */

public class MapCountryPanel extends JScrollPane {
	private static final long serialVersionUID = 827155075130092035L;
	private JTable countryTable;
	public MapCountryPanel() {
		super();
		 	
	}
	
	
	public JTable getCountryTable() {
		return this.countryTable;
	}
	
	/**
	 * Adds the country table for map. needs to be call again to refresh in each phase.
	 * @param map 
	 */
	public void addCountryTableForMap(Map map) {
		ArrayList<Country> countryList = map.getCountryList();
        String[][] mapData = new String[countryList.size()][6];
        for(int i=0;i<countryList.size();i++) {
        	
        	Country country = countryList.get(i);
        	mapData[i][0] = Integer.toString(country.getID());
        	mapData[i][1] = country.getName();                   
        	if(country.getOwner() == null)               //show owners of the countries
        	{
            	mapData[i][2] = "Player unknown";        

        	}
        	else
        	{
        		mapData[i][2] = "Player "+country.getOwner().getId();
        	}
        	mapData[i][3] = String.valueOf(country.getNumOfArmies());    //shows each country have how many armies they own
        	mapData[i][4] = country.getContinent().getName();
        	mapData[i][5] = "";
        	ArrayList<Country> adjCountryList = country.getAdjacentCountryList();
        	
        	for(int j=0;j<adjCountryList.size();j++) {
        		Country adjCountry = adjCountryList.get(j);
        		if(j!=adjCountryList.size()-1) {
        			mapData[i][5]+=adjCountry.getName()+", ";
        		}
        		else {
        			mapData[i][5]+=adjCountry.getName();
        		}
        		
        	}  	
        }
        String[] columnNames = { "ID","Country Name", "Player","# Armies", "Continent", "Adj Country" }; 
        
        JTable mapTable = new JTable(mapData,columnNames);
        mapTable.setBounds(30, 40, 200, 300); 
        mapTable.setRowHeight(40);
        mapTable.setFont(new Font("Serif", Font.BOLD, 20));
        mapTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        mapTable.getColumnModel().getColumn(0).setMinWidth(100);
        mapTable.getColumnModel().getColumn(0).setMaxWidth(100);
        
        mapTable.getColumnModel().getColumn(1).setMinWidth(150);
        mapTable.getColumnModel().getColumn(1).setMaxWidth(150);

        mapTable.getColumnModel().getColumn(2).setMinWidth(150);
        mapTable.getColumnModel().getColumn(2).setMaxWidth(150);

        mapTable.getColumnModel().getColumn(3).setMinWidth(100);
        mapTable.getColumnModel().getColumn(3).setMaxWidth(100);
        
        
        mapTable.getColumnModel().getColumn(4).setMinWidth(150);
        mapTable.getColumnModel().getColumn(4).setMaxWidth(150);
       
        mapTable.getColumnModel().getColumn(5).setMinWidth(150);
        mapTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        mapTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.countryTable = mapTable;
        this.getViewport().add(countryTable);        //show up on "map"
        
        countryTable.setDefaultEditor(Object.class, null);

	}
	
	
	public void addCountryTableForMapEditor(Map map) {
		ArrayList<Country> countryList = map.getCountryList();
        String[][] mapData = new String[countryList.size()][4];
        for(int i=0;i<countryList.size();i++) {
        	
        	Country country = countryList.get(i);
        	mapData[i][0] = Integer.toString(country.getID());
        	mapData[i][1] = country.getName();
        	mapData[i][2] = country.getContinent().getName();
        	mapData[i][3] = "";
        	ArrayList<Country> adjCountryList = country.getAdjacentCountryList();
        	
        	for(int j=0;j<adjCountryList.size();j++) {
        		Country adjCountry = adjCountryList.get(j);
        		if(j!=adjCountryList.size()-1) {
        			mapData[i][3]+=adjCountry.getName()+", ";
        		}
        		else {
        			mapData[i][3]+=adjCountry.getName();
        		}
        		
        	}  	
        }
        // these are columns for Jtable
        String[] columnNames = { "ID","Country Name", "Continent", "Adj Country" }; 
        
        JTable mapTable = new JTable(mapData,columnNames);
        mapTable.setBounds(30, 40, 200, 300); 
        mapTable.setRowHeight(40);
        mapTable.setFont(new Font("Serif", Font.BOLD, 20));
        mapTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        mapTable.getColumnModel().getColumn(0).setMinWidth(100);
        mapTable.getColumnModel().getColumn(0).setMaxWidth(100);
        
        mapTable.getColumnModel().getColumn(1).setMinWidth(150);
        mapTable.getColumnModel().getColumn(1).setMaxWidth(150);
        
        mapTable.getColumnModel().getColumn(2).setMinWidth(150);
        mapTable.getColumnModel().getColumn(2).setMaxWidth(150);
       
        mapTable.getColumnModel().getColumn(3).setMinWidth(150);
        mapTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        mapTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.countryTable = mapTable;
        this.getViewport().add(countryTable);
        
        countryTable.setDefaultEditor(Object.class, null);

	}
	
	/*
	 * the method for adding country for reinforcement
	 * @param player the player should pass as a parameter
	 */
	
	
	public void addCountryTableForReinforcement(Player player) {
		ArrayList<Country> countryList = player.getCountryList();
		String[][] mapData = new String[countryList.size()][3];
        for(int i=0;i<countryList.size();i++) {
        	
        	Country country = countryList.get(i);
        	mapData[i][0] = country.getName();
        	mapData[i][1] = country.getContinent().getName();
        	mapData[i][2] = "";
        	mapData[i][2] = Integer.toString(country.getNumOfArmies()) ;
        }
        String[] columnNames = { "Country Name", "Continent","Numer of Armies"}; 
        
        
        
        
        JTable mapTable = new JTable(mapData,columnNames);
        mapTable.setBounds(30, 40, 200, 300); 
        mapTable.setRowHeight(40);
        //mapTable.setFont(new Font("Serif", Font.BOLD, 20));
       
        mapTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
       
        mapTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.countryTable = mapTable;
        this.getViewport().add(countryTable); 
        countryTable.setDefaultEditor(Object.class, null);
	}
	
	
	
	
	
}
