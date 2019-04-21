package views.map;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import models.map.*;

/**
 * the class for showing continent in a table
 */
public class MapContinentPanel extends JScrollPane {
	private static final long serialVersionUID = 3592055955052270313L;
	
	private JTable continentTable;
	
	public MapContinentPanel() {
		super();
	}
	
	public JTable getContinentTable() {
		return this.continentTable;
	}
	
	
	/*
	 * method for adding continent table in map Editor
	 */
	public void addContinentTableForMapEditor(Map map) {
		
		ArrayList<Continent>continentList = map.getContinentList();
		String[][] mapData = new String[continentList.size()][3];
        for(int i=0;i<continentList.size();i++) {
        	Continent continent = continentList.get(i);
        	mapData[i][0] = Integer.toString(continent.getID());
        	mapData[i][1] = continent.getName();
        	mapData[i][2] = Integer.toString(continent.getControlValue());
        }
        // these are columns for Jtable 
        
        String[] columnNames = {"ID" ,"Continent Name", "Value" }; 
        
        // creating the layout of the table 
        
        JTable mapTable = new JTable(mapData,columnNames);
        mapTable.setBounds(30, 40, 100, 200); 
        mapTable.setRowHeight(40);
        mapTable.setFont(new Font("Serif", Font.BOLD, 20));
        mapTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        mapTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.continentTable = mapTable;
        this.getViewport().add(continentTable);  
        continentTable.setDefaultEditor(Object.class, null);
	}
	
	
	
}
