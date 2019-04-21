package views.game;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import models.game.GameState;
import views.map.MapCountryPanel;


/**
 * The Class EndGameView.
 * @version 2.0
 */
public class EndGameView {

	/**
	 * Instantiates a new end game view.
	 *
	 * @param controlPanel the control panel
	 */
	public EndGameView(JPanel controlPanel) {

		controlPanel.setLayout(new GridLayout(2, 1));
		JPanel titlePanel = new JPanel();
		controlPanel.add(titlePanel);
		
		JPanel resultPanel = new JPanel();
		controlPanel.add(resultPanel);
		
		JLabel LabelTitle = new JLabel("Game Over!"); 
		JLabel LabelPlayerTitle = new JLabel("The winner ID is:"); 
		JLabel LabelPlayer = new JLabel(String.valueOf(GameState.getInstance().getCurrentPlayer().getId())); 
		titlePanel.add(LabelTitle);
		resultPanel.add(LabelPlayerTitle);
		resultPanel.add(LabelPlayer);
		
		
	}
}
