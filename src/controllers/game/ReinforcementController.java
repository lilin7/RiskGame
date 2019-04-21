package controllers.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.JComboBox;

import views.game.CardExchangeView;
import views.game.ReinforcementView;
import views.game.StateView;
import models.game.GameState;
import models.game.Player;
import models.game.GameState.Phase;
import models.game.Human;
import models.map.Country;

/**
 * Class ReinforcementController is the controller which is bound to ReinforcementView
 * and instructs the model to perform actions based on user inputs
 * @author Lin Li
 * @version 2.0
 */
public class ReinforcementController implements ActionListener {

	private ReinforcementView starUpView;
	private CardExchangeView exchangeview;
	private Player player;
	
	/**
	 * Constructor of the class which initialize the view  	
	 * @param view ReinforcementView
	 */
	public ReinforcementController(ReinforcementView view) {
		starUpView = view;
	}
	
	/**
	 * Performs actions based on user inputs
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Add Army":
			addArmy();
			break;
		case "Open exchange card view":
			starUpView.exchangeCard();
			break;
		case "Finish Attack":
			StateView.getInstance().showAttackView();
		}
	}
	
	/**
	 * Perform actions when Add Army button clicked
	 */
	public void addArmy() {
		Country selectedCoutnry = starUpView.getSelectedCountry();
		selectedCoutnry.AddArmy();	
		if (GameState.getInstance().getPhase().equals(Phase.SETUP)) {		
			if(starUpView.decreaseLeftArmies() == 0) {//finish setup	
				Boolean isLastPlayer=!GameState.getInstance().setUpRoundRobin();
				
				if (isLastPlayer) {//if this is the last player to setup, directly jump to reinforcement
					GameState.getInstance().setPhase(Phase.REINFORCEMENT);
					GameState.getInstance().setFirstPlayer();
					//starUpView.showPlayer();
					StateView.getInstance().getMapPanel().addCountryTableForMap(GameState.getInstance().getMap());		
					StateView.getInstance().showReinforcementView();		
				}
				else { //if this is not the last player, check if next player is human	
					starUpView.showPlayer();
				}	
			} else {
				starUpView.showLeftArmies();
			}
		} else if (GameState.getInstance().getPhase().equals(Phase.REINFORCEMENT)) {
			if(starUpView.decreaseLeftArmies() == 0) {
				GameState.getInstance().setPhase(Phase.ATTACK);
				StateView.getInstance().showAttackView();
			} else {
				starUpView.showLeftArmies();
			}
		}

		//refresh the table for map
		StateView.getInstance().getMapPanel().addCountryTableForMap(GameState.getInstance().getMap());			
	}
}