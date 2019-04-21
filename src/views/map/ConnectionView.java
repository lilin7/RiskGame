package views.map;

import javax.swing.JFrame;
import javax.swing.JLabel;

import models.map.*;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import controllers.map.ConnectionController;
import controllers.map.ContinentController;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 * this class provide connection between countries
 */
public class ConnectionView extends JFrame{
	private static final long serialVersionUID = -6541127724099173677L;
	public static final int ADD_CONNECTION_OPTION = 1;
	public static final int DELETE_CONNECTION_OPTION = 2;
	
	private int id;
	private Map map;
	JComboBox<String> comboBox;
	/*
	 * this is a constructor for connectionView Class
	 */
	public ConnectionView(Map map, int id, int option) {
		this.map = map;
		this.id  = id;
		Country country = map.getCountryByID(id);
		
		this.setSize(480,300);
		getContentPane().setLayout(null);
		
		
		if(option == ADD_CONNECTION_OPTION) {
			
			JLabel countryInfo = new JLabel("Add a connection for country: "+country.getName());
			countryInfo.setBounds(12, 13, 300, 16);
			getContentPane().add(countryInfo);
			
			JLabel text = new JLabel("Link this country to");
			text.setBounds(12, 42, 110, 16);
			getContentPane().add(text);
			
			
			// the country available for the new connection should not include the countries that are already 
			// connected to the selected country and the selected country itself;
			ArrayList<String> availableCountryName = new ArrayList<String>();
			ArrayList<String> connectedCountryName = new ArrayList<String>();
			
			for(Country c: map.getCountryList()) {
				availableCountryName.add(c.getName());
			}
			
			for(Country c:country.getAdjacentCountryList()) {
				connectedCountryName.add(c.getName());
			}
			availableCountryName.removeAll(connectedCountryName);
			availableCountryName.remove(country.getName());
			String[] availableCountryNameArray = new String[availableCountryName.size()];
			availableCountryName.toArray(availableCountryNameArray);
			
			
			comboBox = new JComboBox<String>(availableCountryNameArray);
			comboBox.setBounds(134, 39, 150, 22);
			getContentPane().add(comboBox);
			
			JButton addButton = new JButton("Add");
			addButton.setBounds(155, 147, 97, 25);
			addButton.addActionListener(new ConnectionController(this,map,id));
			getContentPane().add(addButton);

		}
		
		
		if(option == DELETE_CONNECTION_OPTION) {
			
			JLabel countryInfo = new JLabel("Delete a connection for country: "+country.getName());
			countryInfo.setBounds(12, 13, 300, 16);
			getContentPane().add(countryInfo);
			
			JLabel text = new JLabel("Delete the link to country:");
			text.setBounds(12, 42, 180, 16);
			getContentPane().add(text);
			
			
			ArrayList<String> connectedCountryName = new ArrayList<String>();
			
			for(Country c:country.getAdjacentCountryList()) {
				connectedCountryName.add(c.getName());
			}
			
			String[] connectedCountryNameArray = new String[connectedCountryName.size()];
			connectedCountryName.toArray(connectedCountryNameArray);
			
			
			comboBox = new JComboBox<String>(connectedCountryNameArray);
			comboBox.setBounds(210, 39, 150, 22);
			getContentPane().add(comboBox);
			
			JButton deleteButton = new JButton("Delete");
			deleteButton.setBounds(155, 147, 97, 25);
			deleteButton.addActionListener(new ConnectionController(this,map,id));
			getContentPane().add(deleteButton);

		}
		
	}
	public String getSelectedCountryName(){
		return (String)(this.comboBox.getSelectedItem());
	}

}
