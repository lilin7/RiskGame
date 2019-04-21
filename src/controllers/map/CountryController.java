package controllers.map;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import models.map.*;
import views.map.CountryView;

/**
 * The Class CountryController. This is a class to add and edit country.
 * @author Lin Li
 * @version 1.0
 */
public class CountryController implements ActionListener {
	/** The map. */
	Map map;
	
	/** The view. */
	CountryView view;
	
	/** The id. */
	int id;
	
	/**
	 * Instantiates a new country controller.
	 *
	 * @param view the view
	 * @param map the map
	 */
	public CountryController(CountryView view, Map map) {
		this.map = map;
		this.view = view;
	}
	
	/**
	 * Instantiates a new country controller.
	 *
	 * @param view the view
	 * @param map the map
	 * @param id the id
	 */
	public CountryController(CountryView view, Map map, int id) {
		this(view, map);
		this.id = id;
	}
	
	/**
	 * This method is to pass action
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		switch(actionCommand) {
		case "Create":
			createCountry();
			break;
		case "Save":
			editCountry();
			break;
		case "Delete":
			deleteCountry();
			break;
		}	
	}
	
	/**
	 * Creates the country.
	 */
	public void createCountry() {
		String countryName = view.getCountryName();
		String continentName = view.getContinentName();
		
		if(map.checkDuplicateCountryName(countryName,-1)) {
			JOptionPane.showMessageDialog(null, "The country's name must be unique!");
		} else {
			Country country = new Country(countryName);
			country.setContinent(map.getContinentByName(continentName));
			map.addCountry(country);
			view.setVisible(false);
			view.dispose();	
		}
	}
	
	/**
	 * Edits the country.
	 */
	public void editCountry() {
		String countryName = view.getCountryName();
		String continentName = view.getContinentName();
		
		if(map.checkDuplicateCountryName(countryName,id)) {
			JOptionPane.showMessageDialog(null, "The country's name must be unique!");
		} else {
			map.updateCountryByID(id, countryName, continentName);
			view.setVisible(false);
			view.dispose();	
		}
	}
	
	/**
	 * Delete country.
	 */
	public void deleteCountry() {	
		
	}
}