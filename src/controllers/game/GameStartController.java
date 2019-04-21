package controllers.game;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import models.game.Army;
import models.game.GameState;
import models.game.Player;
import models.map.Continent;
import models.map.Country;
import models.map.Map;
import views.game.StateView;

/**
 * The Class GameStartController.
 * @author Lin Li
 * @version 3.0
 */
public class GameStartController implements ActionListener {

	/** The number of player. */
	private JComboBox numberOfPlayer;

	/** Strategy of eachplayer. */
	private List<JComboBox> playerTypeComboBoxList ;

	/** The selected file. */
	private File selectedFile;

	/** The frame. */
	private JFrame frame;

	/** The map. */
	private Map map;

	/**
	 * Instantiates a new game start controller. Load map into the controller. Display the map on the frame.
	 *  If map is not valid, show an error message.
	 * @param numberOfPlayer a JComboBox to choose the number of players
	 * @param selectedFile player selected map file. Format: .map
	 * @param frame the frame of the entire program.
	 */
	public GameStartController(JComboBox numberOfPlayer,List<JComboBox> playerTypeComboBoxList, File selectedFile,JFrame frame) {

           this.numberOfPlayer = numberOfPlayer;
           this.playerTypeComboBoxList=playerTypeComboBoxList;
           this.selectedFile = selectedFile;
           this.frame = frame;

           map = new Map();
           boolean result = map.loadMapFromFile(selectedFile);
   		   if(result) {
   			//get different separator from different system
   			String mapFileString = selectedFile.getParentFile().getAbsolutePath() + java.nio.file.FileSystems.getDefault().getSeparator() + map.getImage();
   			File imageFile = new File(mapFileString);
   	        Image i;
   			try {
   				i = ImageIO.read(imageFile);
   				ImageIcon image = new ImageIcon(i);
   		        JLabel imageLabel = new JLabel(image);
   		        frame.add(imageLabel);
   		        frame.setLayout(null);

   		        imageLabel.setLocation(0, 50);
   		        int width = i.getWidth(null);
   		        int height = i.getHeight(null);
   		        imageLabel.setSize(width, height);
   		        imageLabel.setVisible(true);

   		        frame.setSize(width, height+50);

   			} catch (IOException ex) {

   				ex.printStackTrace();
   			}
   		}
   		else {
   			JOptionPane.showMessageDialog(frame, "not a valid map");
   		}
	}

	/**
	 * this method is triggered by "Start" button. Players are created and countries are randomly assigned.
	 * @param numberOfPlayer
	 * @param playerTypeComboBoxList
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public GameStartController(JComboBox numberOfPlayer,List<JComboBox> playerTypeComboBoxList) {
		this.numberOfPlayer = numberOfPlayer;
        this.playerTypeComboBoxList=playerTypeComboBoxList;

		File selectedFile = GameState.getInstance().getSelectedFile();
		boolean result = GameState.getInstance().loadMapFromFile(selectedFile);

		if(result) {
		}
		else {
			JOptionPane.showMessageDialog(StateView.getInstance(), "not a valid map");
		}
	}

	/**This method is activate the Game start button to assign all players, countries, armies and maps. and move to reinforcement view.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int num = (Integer)numberOfPlayer.getSelectedItem();
		
		GameState.getInstance().assignInitialPlayers(num,playerTypeComboBoxList);
		GameState.getInstance().randomAssignCountry();
		GameState.getInstance().assignInitialArmy();
		//refresh the table for map of all players
		StateView.getInstance().getMapPanel().addCountryTableForMap(GameState.getInstance().getMap());
		StateView.getInstance().showReinforcementView();
	}
}