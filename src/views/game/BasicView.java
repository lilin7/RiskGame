package views.game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controllers.game.GameStartController;
import controllers.game.PlayerSetupController;
import controllers.map.MapEditorStartController;
import views.map.MapCountryPanel;

/**
 * The Class BasicView. To show the basic information of the game
 * @version  1.0
 */
public class BasicView {

	/**
	 * Instantiates a new basic view.
	 *
	 * @param controlPanel the control panel
	 */
	public BasicView(JPanel controlPanel) {
		
		FlowLayout fl_controlPanel = (FlowLayout) controlPanel.getLayout();
		fl_controlPanel.setAlignment(FlowLayout.LEADING);
		
		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new PlayerSetupController());
		
		controlPanel.add(newGameButton);

		JButton newTurnamentButton = new JButton("New Turnament");
		newTurnamentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				TournamentSetupView tns=new TournamentSetupView();
				tns.setVisible(true);
			}
			
		});
		
		
		
		controlPanel.add(newTurnamentButton);
		
		JButton openMapEditorButton = new JButton("Open Map Editor");
		openMapEditorButton.addActionListener(new MapEditorStartController());

		controlPanel.add(openMapEditorButton);
		
		
        
	}
	
	
	
	
	
	
}
