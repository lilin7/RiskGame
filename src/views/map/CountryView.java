package views.map;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import controllers.map.*;
import models.map.*;

/**
 * the calss for showing Country 
 */
public class CountryView extends JFrame{
	private static final long serialVersionUID = 1743487642059996604L;
	private JTextField nameTextField;
	JComboBox<String> comboBox;
	
	/**
	 * method for viewing Country 
	 * creating layout for country
	 */
	public CountryView() {
		this.setSize(480,300);
		getContentPane().setLayout(null);
		
		JLabel nameLabel = new JLabel("Country Name");
		nameLabel.setBounds(90, 25, 100, 16);
		getContentPane().add(nameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(200, 22, 116, 22);
		getContentPane().add(nameTextField);
		nameTextField.setColumns(10);
		
		
		
	}
	
	
	public CountryView(Map map) {
		
		this();
		
		ArrayList<Continent> continentList = map.getContinentList();
		String[] continentName = new String[map.getContinentNumber()];
		for(int i=0;i<continentList.size();i++) {
			continentName[i] = continentList.get(i).getName();
		}
		
		JLabel continentLabel = new JLabel("Continent");
		continentLabel.setBounds(90, 60, 100, 16);
		getContentPane().add(continentLabel);
		
		comboBox = new JComboBox<String>(continentName);
		comboBox.setBounds(200, 57, 116, 22);
		getContentPane().add(comboBox);
		
		JButton createButton = new JButton("Create");
		createButton.setBounds(155, 147, 97, 25);
		createButton.addActionListener(new CountryController(this,map));
		getContentPane().add(createButton);
		
	}
/*
 * @param map getting country list for filling ComboBox
 * @param id
 */
	public CountryView(Map map, int id) {
		this();
		
		ArrayList<Continent> continentList = map.getContinentList();
		String[] continentName = new String[map.getContinentNumber()];
		for(int i=0;i<continentList.size();i++) {
			continentName[i] = continentList.get(i).getName();
		}
		
		JLabel continentLabel = new JLabel("Continent");
		continentLabel.setBounds(90, 60, 100, 16);
		getContentPane().add(continentLabel);
		
		comboBox = new JComboBox<String>(continentName);
		comboBox.setBounds(200, 57, 116, 22);
		getContentPane().add(comboBox);
		
		
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(155, 147, 97, 25);
		saveButton.addActionListener(new CountryController(this,map,id));
		getContentPane().add(saveButton);
		
		Country country = map.getCountryByID(id);
		this.nameTextField.setText(country.getName());
		this.comboBox.setSelectedItem(country.getContinent().getName());
	}
	
	/*
	 * @return returning the name of country written
	 */
	public String getCountryName() {
		return this.nameTextField.getText();
	}
	
	public String getContinentName() {
		return (String)(this.comboBox.getSelectedItem());
	}
}
