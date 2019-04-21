package views.map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import controllers.map.*;
import models.map.Continent;
import models.map.Map;

import java.awt.GridLayout;

/**
 * this class for showing continent 
 */

public class ContinentView extends JFrame {
	private JTextField nameTextField;
	private JTextField valueTextField;
/*
 * this is the constructor for the class
 */
	
	public ContinentView() {
		this.setSize(480,300);
		getContentPane().setLayout(null);
		
		JLabel nameLabel = new JLabel("Continent Name");
		nameLabel.setBounds(90, 25, 100, 16);
		getContentPane().add(nameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(200, 22, 116, 22);
		getContentPane().add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel valueLabel = new JLabel("Continent Value");
		valueLabel.setBounds(90, 60, 100, 16);
		getContentPane().add(valueLabel);
		
		valueTextField = new JTextField();
		valueTextField.setBounds(200, 57, 116, 22);
		getContentPane().add(valueTextField);
		valueTextField.setColumns(10);
	}
	
	/*
	 * this method call the constructor
	 * add action listener to create button
	 */
	public ContinentView(Map map) {
		
		this();
		
		JButton createButton = new JButton("Create");
		createButton.setBounds(155, 147, 97, 25);
		createButton.addActionListener(new ContinentController(this,map));
		getContentPane().add(createButton);
		
	}
	/*
	 * the method is for creating continent
	 */

	public ContinentView(Map map, int id) {
		this();
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(155, 147, 97, 25);
		saveButton.addActionListener(new ContinentController(this,map,id));
		getContentPane().add(saveButton);
		
		Continent continent = map.getContinentByID(id);
		this.nameTextField.setText(continent.getName());
		this.valueTextField.setText(Integer.toString(continent.getControlValue()));
	}
	
	public String getContinentName() {
		return this.nameTextField.getText();
	}
	
	public String getContinentValue() {
		return this.valueTextField.getText();
	}
}
