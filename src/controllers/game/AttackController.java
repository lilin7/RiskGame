package controllers.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import models.game.Dice;
import models.game.GameState;
import models.game.GameState.Phase;
import models.map.Country;
import views.game.AttackView;
import views.game.StateView;

/**
 * The Class AttackController. player perform attacks according to the rule of risk
 * @author Lin Li
 * @version 2.0
 */
public class AttackController implements ActionListener {

	Country attackerCountry;
	Country defenderCountry;
	private AttackView attackView;
	
	/**
	 * Instantiates a new attack controller.
	 */
	public AttackController(AttackView attackView) {
		this.attackView = attackView;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		StateView.getInstance().getMapPanel().addCountryTableForMap(GameState.getInstance().getMap());

		attackerCountry = attackView.getSelecterdCountryFrom();
		defenderCountry = attackView.getSelecterdCountryTo();

		switch ( e.getActionCommand() ) {
		case AttackView.RollDiceStr:
			if (attackView.getAttacherDiceNumber()!=0 && attackView.getDefenderDiceNumber()!=0) {
				int attackerDiceNumber = attackView.getAttacherDiceNumber();
				int defenderDiceNumber = attackView.getDefenderDiceNumber();

				Dice dice = new Dice();
				int[] attackerDice = dice.diceRoll(attackerDiceNumber);
				int[] defenderDice = dice.diceRoll(defenderDiceNumber);
				int[] attackResult = GameState.getInstance().getCurrentPlayer().attack(attackerDice, defenderDice);
				attackerCountry.removeArmies(attackResult[0]);
				defenderCountry.removeArmies(attackResult[1]);
				attackView.showResolutionState(attackerDice, defenderDice, attackResult[0], attackResult[1]);
			}
			StateView.getInstance().getMapPanel().addCountryTableForMap(GameState.getInstance().getMap());
			break;
			
		case AttackView.ContinueStr:
//			int diceNumber = attackView.getAttacherDiceNumber();
			checkNextStep();
			break;
			
		case AttackView.MoveArmiesStr:
			//GameState.getInstance().fortify(attackView.getSelecterdCountryFrom().getName(), attackView.getSelecterdCountryTo().getName(), attackView.getArmiesNumberToMove());
			GameState.getInstance().getCurrentPlayer().moveArmies(attackView.getSelecterdCountryFrom(), attackView.getSelecterdCountryTo(), attackView.getArmiesNumberToMove());
			if(GameState.getInstance().getCurrentPlayer().isAttackPossible())
				attackView.showSelectionState();
			else {
				GameState.getInstance().setPhase(Phase.FORTIFICATION);
				StateView.getInstance().showFortificationView();
			}
			StateView.getInstance().getMapPanel().addCountryTableForMap(GameState.getInstance().getMap());				
			break;
			
		case AttackView.EndAttackPhaseStr:
			GameState.getInstance().setPhase(Phase.FORTIFICATION);
			StateView.getInstance().showFortificationView();
			break;

		case AttackView.AllOutStr:
			int diceNo = 0;
			while(defenderCountry.getNumOfArmies()!=0 && attackerCountry.getNumOfArmies()>1) {
				diceNo = doAttack(attackerCountry, defenderCountry);
			}
			StateView.getInstance().getMapPanel().addCountryTableForMap(GameState.getInstance().getMap());			
			checkNextStep();							
			break;
		}
	}
	
	/**
	 * Implement attack from one country to another
	 * @param attackerCountry
	 * @param defenderCountry
	 * @return int
	 */
	public int doAttack(Country attackerCountry, Country defenderCountry) {

		int attackerDiceNumber = Math.min(3,attackerCountry.getNumOfArmies());
		int defenderDiceNumber = Math.min(2,Math.min(attackerCountry.getNumOfArmies(), defenderCountry.getNumOfArmies()));

		Dice dice = new Dice();
		int[] attackerDice = dice.diceRoll(attackerDiceNumber);
		int[] defenderDice = dice.diceRoll(defenderDiceNumber);
		int[] attackResult = GameState.getInstance().getCurrentPlayer().attack(attackerDice, defenderDice);
		attackerCountry.removeArmies(attackResult[0]);
		defenderCountry.removeArmies(attackResult[1]);
		return attackerDiceNumber;
	}

	
	public void checkNextStep() {
		if (GameState.getInstance().getCurrentPlayer().conquer(defenderCountry)) {
			if (GameState.getInstance().getMap().mapOwner(GameState.getInstance().getCurrentPlayer())) {
				GameState.getInstance().setPhase(Phase.FINISHED);
				StateView.getInstance().showEndGameView();
			}
			else
//				attackView.showMoveArmiesState(fromCountry.getNumOfArmies()<=diceNumber ? diceNumber-1 : diceNumber);
				attackView.showMoveArmiesState(1);
		}
		else {
			// in case that attacker lost the country
			if (defenderCountry.getNumOfArmies() == 0) {
				defenderCountry.setOwner(attackerCountry.getOwner());
				attackerCountry.getOwner().moveArmies(defenderCountry, attackerCountry, 1);
			}
			
			if(GameState.getInstance().getCurrentPlayer().getArmyNumber() == 0) {
			// current player ended his/her turn.
			GameState.getInstance().endPlayerTurn();
			GameState.getInstance().setPhase(Phase.REINFORCEMENT);
			StateView.getInstance().showReinforcementView();					
			}
			else {
				if(GameState.getInstance().getCurrentPlayer().isAttackPossible())
					attackView.showSelectionState();
				else {
					GameState.getInstance().setPhase(Phase.FORTIFICATION);
					StateView.getInstance().showFortificationView();
				}
			}				
		}		
	}
}
