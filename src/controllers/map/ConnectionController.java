package controllers.map;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.map.*;
import views.map.ConnectionView;

/**
 * The Class ConnectionController. To manage the connection between countries
 * @author Lin Li
 * @version 1.0
 */
public class ConnectionController implements ActionListener {
	/** The view. */
	private ConnectionView view;
	
	/** The id. */
	private int id;
	
	/** The map. */
	private Map map;
	
	/**
	 * Instantiates a new connection controller.
	 *
	 * @param view the view
	 * @param map the map
	 * @param id the id
	 */
	public ConnectionController(ConnectionView view, Map map, int id) {
		this.view = view;
		this.map = map;
		this.id = id;	
	}
	
	/**
	 * Add action button to pass action to action listener
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		switch(actionCommand) {
		case "Add":
			addConnection();
			break;
		case "Delete":
			deleteConnection();
			break;
		}
	}
	
	/**
	 * Adds the connection.
	 */
	public void addConnection() {
		
		String newConnectedCountryName = this.view.getSelectedCountryName();
		
		int newConnectedCountryID = map.getCountryByName(newConnectedCountryName).getID();
		
		map.addConnection(id, newConnectedCountryID);
		this.view.setVisible(false);
		this.view.dispose();	
	}
	
	/**
	 * Delete connection.
	 */
	public void deleteConnection() {
		String deleteConnectedCountryName = this.view.getSelectedCountryName();
		
		int deleteConnectedCountryID = map.getCountryByName(deleteConnectedCountryName).getID();
		
		map.removeConnection(id, deleteConnectedCountryID);
		this.view.setVisible(false);
		this.view.dispose();
	}
}
