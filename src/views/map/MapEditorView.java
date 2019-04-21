package views.map;

import javax.swing.JFrame;

import controllers.map.*;
import models.map.*;
import views.game.StateView;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JLabel;

/**
 * this is the Class For Map Editor
 *@version 1.0
 */
public class MapEditorView extends JFrame implements Observer{
	private static final long serialVersionUID = 1L;

	private MapCountryPanel mapCountryPanel;
	private MapContinentPanel mapContinentPanel;
	
	
	/**
	 * this is the constructor
	 * @Param map the map
	 */
	
	public MapEditorView(Map map) {	
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel controlPanel = new JPanel();
		getContentPane().add(controlPanel, BorderLayout.LINE_START);
		
		//the button for adding country
		JButton addCountryButton = new JButton("Add Country");
		addCountryButton.addActionListener(new MapEditorController(map,this));
		
		//the button for adding continent
		JButton addContinentButton = new JButton("Add Continent");
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		controlPanel.add(addCountryButton);
		
		//the button for deleting country
		JButton deleteCountryButton = new JButton("Delete Country");
		deleteCountryButton.addActionListener(new MapEditorController(map,this));
		
		//the button for editing country
		JButton editCountryButton = new JButton("Edit Country");
		editCountryButton.addActionListener(new MapEditorController(map,this));
		controlPanel.add(editCountryButton);
		controlPanel.add(deleteCountryButton);
		controlPanel.add(addContinentButton);
		
		//the button for deleting continent
		JButton deleteContinentButton = new JButton("Delete Continent");
		deleteContinentButton.addActionListener(new MapEditorController(map,this));
		
		//the button for editing continent
		JButton editContinentButton = new JButton("Edit Continent");
		controlPanel.add(editContinentButton);
		controlPanel.add(deleteContinentButton);
		
		//the button for adding connection between country
		JButton addConnectionButton = new JButton("Add Connection");
		controlPanel.add(addConnectionButton);
		addConnectionButton.addActionListener(new MapEditorController(map,this));
		
		//the button for deleting connection between country
		JButton deleteConnectionButton = new JButton("Delete Connection");
		controlPanel.add(deleteConnectionButton);
		
		JButton editMapInfoButton = new JButton("Edit Basic Info");
		controlPanel.add(editMapInfoButton);
		
		editMapInfoButton.addActionListener(new MapEditorController(map,this));
		
		deleteConnectionButton.addActionListener(new MapEditorController(map,this));
		addContinentButton.addActionListener(new MapEditorController(map,this));
		
		editContinentButton.addActionListener(new MapEditorController(map,this));
		
		//JPanel menuPanel = new JPanel();
		//getContentPane().add(menuPanel, BorderLayout.PAGE_START);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		//menuPanel.add(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		menuBar.add(fileMenu);
		
		JMenuItem loadMenuItem = new JMenuItem("Load");
		loadMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		fileMenu.add(loadMenuItem);
		
		loadMenuItem.addActionListener(new MapEditorController(map,this));
		
		this.setJMenuBar(menuBar);
		
		//adding saving item in menu
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		fileMenu.add(saveMenuItem);
		saveMenuItem.addActionListener(new MapEditorController(map,this));

		this.mapCountryPanel = new MapCountryPanel();
		mapCountryPanel.addCountryTableForMapEditor(map);
        getContentPane().add(mapCountryPanel, BorderLayout.CENTER);
        
        this.mapContinentPanel = new MapContinentPanel();
        mapContinentPanel.addContinentTableForMapEditor(map);
        getContentPane().add(mapContinentPanel, BorderLayout.LINE_END);

	}
/*
 * method for getting Country ID
 * @return id countryId
 */
	public int getSelectedCountryID() {
		JTable countryTable = this.mapCountryPanel.getCountryTable();
		int rowID = countryTable.getSelectedRow();
		
		// no country has been selected
		if(rowID==-1) {
			return -1;	
		}

		String idString = (String)(countryTable.getValueAt(rowID, 0));

		int id = Integer.parseInt(idString);
		
		return id;
	}
	
	/*
	 * method for getting continent ID
	 * @return id continent Id
	 */
	public int getSelectedContinentID() {
		JTable continentTable = this.mapContinentPanel.getContinentTable();
		int rowID = continentTable.getSelectedRow();
		
		// no continent has been selected
		if(rowID==-1) {
			return -1;	
		}

		String idString = (String)(continentTable.getValueAt(rowID, 0));

		int id = Integer.parseInt(idString);
		
		return id;
	}
/*
 * this the observer
 * @see update(Observable map, Object x)
 */
	
	@Override
	public void update(Observable map, Object x) {
		getContentPane().remove(mapContinentPanel);
		getContentPane().remove(mapCountryPanel);
		this.mapContinentPanel = new MapContinentPanel();
	    mapContinentPanel.addContinentTableForMapEditor((Map)map);
		this.mapCountryPanel = new MapCountryPanel();
		mapCountryPanel.addCountryTableForMapEditor((Map)map);
        getContentPane().add(mapCountryPanel, BorderLayout.CENTER);
        getContentPane().add(mapContinentPanel, BorderLayout.LINE_END);
		this.revalidate();
	}	

}
