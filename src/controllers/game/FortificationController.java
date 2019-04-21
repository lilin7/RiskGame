package controllers.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import models.game.GameState;
import models.game.GameState.Phase;
import models.map.Country;
import views.game.StateView;

/**
 * class FortificationController to receive player action and call methods from model
 * @author Lin Li
 * @version 2.0
 */
public class FortificationController implements ActionListener {
	
	private JComboBox from, to;
	private JTextField quantity;
	
	/**
	 * Constructor of class FortificationController
	 * @param from
	 * @param to
	 * @param quantity
	 */
	public FortificationController(JComboBox from, JComboBox to, JTextField quantity) {
		this.from = from;
		this.to = to;
		this.quantity = quantity;
	}
	
	@Override
	/**
	 * Perform Fortification if player click on button
	 * @param ActionEvent e
	 */
	public void actionPerformed(ActionEvent e) {
		String fromStr = (String) from.getSelectedItem();
		String toStr = (String) to.getSelectedItem();
		
		int qt = 0;
		try { qt = Integer.parseInt( quantity.getText() ); } catch (Exception ex) {}
		
		if (GameState.getInstance().fortify(fromStr, toStr, qt ) ) {
			//deduct number of armies from country fromStr, add number of armies to country toStr
						
			// TODO current player ended his/her turn.
			GameState.getInstance().endPlayerTurn();
						
			//remember to refresh the map view all the time
			StateView.getInstance().getMapPanel().addCountryTableForMap(GameState.getInstance().getMap());
			
			GameState.getInstance().setPhase(Phase.REINFORCEMENT);
			StateView.getInstance().showReinforcementView();		
			
		} else {
			System.out.println("Invalid fortification");
		}
	}
}
