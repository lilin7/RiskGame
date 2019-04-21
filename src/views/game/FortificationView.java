package views.game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controllers.game.FortificationController;
import models.game.GameState;
import models.game.Human;
import models.game.Player;
import models.game.GameState.Phase;
import models.map.Country;
import views.map.MapCountryPanel;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * class FortificationView is the view for fortification phase
 * @version 3.0
 * @see controllers.game.FortificationController
 */
public class FortificationView {

	private JComboBox<String> fromDropDown = new JComboBox<String>(); //declare JComboBox to show a drop-down list
	private JComboBox<String> toDropDown = new JComboBox<String>(); //declare JComboBox to show a drop-down list
	private JTextField quantity = new JTextField();
	
	/**
	 * Constructor of class FortificationView
	 */
	public FortificationView(JPanel controlPanel) {

		GameState.getInstance().setPhase(Phase.FORTIFICATION);
		Player currentPlayer= GameState.getInstance().getCurrentPlayer();
		currentPlayer.doStrategyfortification();
		
		if(!(currentPlayer.getStrategy() instanceof Human))	{//if not human, directly finish and jump to reinforcement
			GameState.getInstance().endPlayerTurn();

			//remember to refresh the map view all the time
			StateView.getInstance().getMapPanel().addCountryTableForMap(GameState.getInstance().getMap());
			GameState.getInstance().setPhase(Phase.REINFORCEMENT);
			StateView.getInstance().showReinforcementView();	
		
		}
		else// if this is human, draw the tables to do fortification
		{
			
			JPanel informationPanel = new JPanel();
			controlPanel.add(informationPanel);
			informationPanel.setLayout(new GridLayout(0, 2));

			
			JPanel labelColumn = new JPanel();
			informationPanel.add(labelColumn);
			labelColumn.setLayout(new GridLayout(0, 1,0,0));
			
			JLabel phaseTextLabel = new JLabel("Phase: ");
			labelColumn.add(phaseTextLabel);
			
			JLabel currentPlayerTextLabel = new JLabel("Current Player: ");
			labelColumn.add(currentPlayerTextLabel);
			
			JLabel ruleInfoLabel = new JLabel("Rule Information: ");
			labelColumn.add(ruleInfoLabel);
			
			JPanel dataColumn = new JPanel();
			informationPanel.add(dataColumn);
			dataColumn.setLayout(new GridLayout(0, 1));
			dataColumn.add(StateView.getInstance().getPhaseLabel());
			
			JLabel currentPlayerIndicator = new JLabel(String.valueOf(GameState.getInstance().getCurrentPlayer().getId()));
			dataColumn.add(currentPlayerIndicator);
			
			JLabel ruleInfoDisplay=new JLabel(StateView.getInstance().getRuleInfoLabel().getText());
			dataColumn.add(ruleInfoDisplay);
			
			String fortificationInfoRule=" <html>In the fortification<br/>" + 
			 		"phase, the player may move any number of armies from one of his owed countries to the other, provided that<br/>" + 
			 		"there is a path between these two countries that is composed of countries that he owns. Only one such move is<br/>" + 
			 		"allowed per fortification phase. Once the move is made or the player forfeits his fortification phase, the player’s<br/>" + 
			 		"turn ends and it is now the next player’s turn. Any player than does not control at least one country is removed<br/>" + 
			 		"from the game. The game ends at any time one of the players owns all the countries in the map. </html>";

			ruleInfoDisplay.setToolTipText(fortificationInfoRule);
			String str=ruleInfoLabel.getToolTipText();
			System.out.print(str);
			
			
			JPanel fortificationPanel = new JPanel();
			controlPanel.add(fortificationPanel);
			fortificationPanel.setLayout(new GridLayout(0, 3, 0, 0));
			
			JPanel fortificationInfoPanel = new JPanel();
			fortificationPanel.add(fortificationInfoPanel);
			fortificationInfoPanel.setLayout(new GridLayout(0, 1, 0, 0));
			
			// text shown to guide user to select from which country the armies should go out
			JTextArea textFrom = new JTextArea("From Country:");
			fortificationInfoPanel.add(textFrom);
			
			// text shown to guide user to select destination country
			JTextArea textTo = new JTextArea("To Country:");
			fortificationInfoPanel.add(textTo);
			
			// declare quantity fields
			JTextArea textQuantity = new JTextArea("Number of Armies:");
			fortificationInfoPanel.add(textQuantity);
			
			JPanel fortificationPanel_1 = new JPanel();
			fortificationPanel.add(fortificationPanel_1);
			fortificationPanel_1.setLayout(new GridLayout(3, 1, 0, 0));
			
			// initialized before handler
			fortificationPanel_1.add(fromDropDown);
			fortificationPanel_1.add(toDropDown);
			
			quantity = new JTextField(5);
			fortificationPanel_1.add(quantity);
			quantity.setColumns(10);
			
			JPanel fortificationButtonPanel = new JPanel();
			fortificationPanel.add(fortificationButtonPanel);
					
			Player player = GameState.getInstance().getCurrentPlayer(); //get current player
			final ArrayList<Country> countryList = player.getCountryList(); //get country list of current player
			
			// create drop-down list for all the countries owned by current player
			for (Country country:countryList) {
				fromDropDown.addItem(country.getName());
			}
			
			fromDropDown.addActionListener((ActionListener) new ActionListener() {
				public void actionPerformed(ActionEvent event){
	                JComboBox<String> comboBox = (JComboBox<String>) event.getSource();
	                String selected = (String)comboBox.getSelectedItem();
	            	Country selectedCountry = null;
	                for(Country c:countryList) {
	                	if (c.getName().equals(selected))
	                		selectedCountry = c; 
	                }
	                
	                // get valid destinations
	                ArrayList<Country> destinationCountryList = GameState.getInstance().getValidDestination(selectedCountry);
	                                
	                toDropDown.removeAllItems(); // clear the to combobox
	                
	                // add all possible destinations (belong to current player and there is a path) to the toDropDown JComboBox
	                for (Country country:destinationCountryList) {
	        			toDropDown.addItem(country.getName());
	        		}
	                
	                // by default show the maximum number of armies that can be moved, but also able to use user input
	                if (selectedCountry != null) {
	                	quantity.setText(String.valueOf(Math.max(0,selectedCountry.getNumOfArmies()-1)));
	                }               	
	        	}
			});
				
			// callback to fortification controller
			JButton executeFortification = new JButton("Fortify!");
			executeFortification.setVerticalAlignment(SwingConstants.BOTTOM);
			executeFortification.addActionListener(new FortificationController(fromDropDown, toDropDown, quantity));
			
			fortificationButtonPanel.add(executeFortification);
		}
	}
}
