package views.game;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import controllers.game.GameStartController;
import models.game.GameState;
import views.map.MapCountryPanel;

import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * Class PlayerSetupView is the view for player to start up by selecting number of players
 * @version 3.0
 */
public class PlayerSetupView {

	/**
	 * Instantiates a new player setup view.
	 *
	 * @param controlPanel the control panel
	 * @param frame the frame
	 */
	public PlayerSetupView(final JPanel controlPanel,StateView frame) {
		
		FlowLayout fl_controlPanel = (FlowLayout) controlPanel.getLayout();
		fl_controlPanel.setAlignment(FlowLayout.LEADING);
		
		//Prompt user to select number of players
		JTextArea selectNoOfPlayers = new JTextArea("Select Number of Players:");
				
		controlPanel.add(selectNoOfPlayers);
		
		final JPanel playerTypePanel =new JPanel(new GridLayout(0,2));
		
		//set the drop-down box for selecting number of players, and set the options to be integer between 2 and 5
		JComboBox numberOfPlayer = new JComboBox();
		for (int i = 2; i < 6; i++) {
			numberOfPlayer.addItem(i);
		}
		final List<JComboBox> playerTypeComboBoxList=new ArrayList();

		for(int j=0;j<2;j++)
		{
			JLabel playerNum = new JLabel("Player "+ j);
			JComboBox playerType = new JComboBox();
			playerType.addItem("Human");
			playerType.addItem("Aggressive");
			playerType.addItem("Benevolent");
			playerType.addItem("Random");
			playerType.addItem("Cheater");
			playerType.setSelectedIndex(0);
			playerTypeComboBoxList.add(playerType);
			playerTypePanel.add(playerNum);
			playerTypePanel.add(playerType);
		}
		
		
		numberOfPlayer.addActionListener(new ActionListener() {
			
			@Override

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				playerTypePanel.removeAll();
				playerTypePanel.revalidate();
				playerTypePanel.repaint();
				
				playerTypeComboBoxList.clear();
				
				JComboBox comboBox=(JComboBox)e.getSource();
				int i=comboBox.getSelectedIndex()+2;

				for(int j=0;j<i;j++)
				{
					JLabel playerNum = new JLabel("Player "+ j);
					JComboBox playerType = new JComboBox();
					
					playerType.addItem("Human");
					playerType.addItem("Aggressive");
					playerType.addItem("Benevolent");
					playerType.addItem("Random");
					playerType.addItem("Cheater");
					
					playerType.setSelectedIndex(0);
					playerTypeComboBoxList.add(playerType);
					playerTypePanel.add(playerNum);
					playerTypePanel.add(playerType);
				}
			}

			
		});
		
		
		 
		JButton newGameButton = new JButton("START THE GAME");
		
		//receive user clicking ""START THE GAME" by GameStartController
		newGameButton.addActionListener(new GameStartController(numberOfPlayer,playerTypeComboBoxList));
		controlPanel.add(numberOfPlayer);
		controlPanel.add(playerTypePanel);
		controlPanel.add(newGameButton);

		MapCountryPanel mapPanel = frame.getMapPanel();
		
		mapPanel.addCountryTableForMap(GameState.getInstance().getMap());	
	}
}
