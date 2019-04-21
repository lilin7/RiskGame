package views.game;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

import models.game.Aggressive;
import models.game.Benevolent;
import models.game.Cheater;
import models.game.GameState;
import models.game.Human;
import models.game.Player;
import models.game.Random;
import models.map.Map;

/**
 * The Class TournamentSetupView. This class is to create a pop up window to setup a tournament.
 * @version 2.0
 */
public class TournamentSetupView extends JFrame {

	List<Map> maps=new ArrayList<Map>();
	List<Player> players=new ArrayList<Player>();
	int numOfGames;
	int turns;
	int numberOfMaps=1;
	
	private JPanel 	mainPanel = new JPanel();

	
	public TournamentSetupView() {
		this.setSize(1024,768);

		final List<JComboBox> playerTypeComboBoxList=new ArrayList();
		mainPanel.setLayout(new FlowLayout());
		this.add(mainPanel);
		//this.setLayout(new FlowLayout());
		//((FlowLayout) this.getLayout()).setAlignment(FlowLayout.LEADING);
		
		final JPanel basicinfoPanel =new JPanel(new GridLayout(0,2));
		
		//select #maps
		JLabel selectNoOfMaps = new JLabel("Select Number of Maps:");
		JComboBox numberOfMapsCombobox = new JComboBox();
		for (int i = 1; i < 6; i++) {
			numberOfMapsCombobox.addItem(i);
		}
		numberOfMapsCombobox.setSelectedIndex(0);
		this.numberOfMaps=(Integer)numberOfMapsCombobox.getSelectedItem();

		
		
		//select #players
		JLabel selectNoOfPlayers = new JLabel("Select Number of Players:");
		JComboBox numberOfPlayers = new JComboBox();
		for (int i = 2; i < 5; i++) {
			numberOfPlayers.addItem(i);
		}
		numberOfPlayers.setSelectedIndex(0);
		//select #games
		
		JLabel selectNoOfGames = new JLabel("Select Number of Games:");
		JComboBox numberOfGamesCombobox = new JComboBox();
		for (int i = 1; i < 6; i++) {
			numberOfGamesCombobox.addItem(i);
		}
		numberOfGamesCombobox.setSelectedIndex(0);
		
		//selection #maxturns
		
		JLabel selectNoOfTurns = new JLabel("Select Number of Max Turns:");
		JComboBox numberOfTurnsCombobox = new JComboBox();
		for (int i = 10; i < 51; i++) {
			numberOfTurnsCombobox.addItem(i);
		}
		numberOfTurnsCombobox.setSelectedIndex(0);
		
		basicinfoPanel.add(selectNoOfMaps);
		basicinfoPanel.add(numberOfMapsCombobox);
		basicinfoPanel.add(selectNoOfPlayers);
		basicinfoPanel.add(numberOfPlayers);
		basicinfoPanel.add(selectNoOfGames);
		basicinfoPanel.add(numberOfGamesCombobox);
		basicinfoPanel.add(selectNoOfTurns);
		basicinfoPanel.add(numberOfTurnsCombobox);
		
		///// deal with players
		final JPanel playerPanel =new JPanel(new GridLayout(0,2));

		for(int j=0;j<2;j++)
		{
			JLabel playerNum = new JLabel("Player "+ j);
			JComboBox playerType = new JComboBox();
			playerType.addItem("Aggressive");
			playerType.addItem("Benevolent");
			playerType.addItem("Random");
			playerType.addItem("Cheater");
			playerType.setSelectedIndex(0);
			playerTypeComboBoxList.add(playerType);
			playerPanel.add(playerNum);
			playerPanel.add(playerType);
		}
		
		numberOfPlayers.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				playerPanel.removeAll();
				playerPanel.revalidate();
				playerPanel.repaint();
				
				players.clear();
				playerTypeComboBoxList.clear();
				JComboBox comboBox=(JComboBox)e.getSource();
				int i=comboBox.getSelectedIndex()+2;

				for(int j=0;j<i;j++)
				{
					JLabel playerNum = new JLabel("Player "+ j);
					JComboBox playerType = new JComboBox();
					
					playerType.addItem("Aggressive");
					playerType.addItem("Benevolent");
					playerType.addItem("Random");
					playerType.addItem("Cheater");

					playerType.setSelectedIndex(0);
					playerTypeComboBoxList.add(playerType);
					
					
					
					playerPanel.add(playerNum);
					playerPanel.add(playerType);
				}
			}

			
		});
		
		//////deal with maps
		final JPanel mapsPanel =new JPanel(new GridLayout(0,2));
		
		JButton selectFileButton=new JButton("Choose map 1");
		JLabel mapPath1 = new JLabel("");
		Map map=new Map();
		maps.add(map);
		selectFileButton.addActionListener(new TournamentChooseMapListener(maps,0,mapPath1) );

		
		mapsPanel.add(selectFileButton);
		mapsPanel.add(mapPath1);
	
		numberOfMapsCombobox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				mapsPanel.removeAll();
				mapsPanel.revalidate();
				mapsPanel.repaint();
				
				maps.clear();
				
				JComboBox comboBox=(JComboBox)e.getSource();
				int i=comboBox.getSelectedIndex()+1;
				for(int j=0;j<i;j++){//init list of empty maps
					Map map=new Map();
					maps.add(map);
				}
				
				for(int j=0;j<i;j++)
				{
					JButton selectFileButton=new JButton("Choose map "+(j+1));
					JLabel mapPath = new JLabel("");
					
					selectFileButton.addActionListener(new TournamentChooseMapListener(maps,j,mapPath) );
					
					mapsPanel.add(selectFileButton);
					mapsPanel.add(mapPath);
				}
			}

			
		});

		/////Start button	
		JButton startButton=new JButton("Start Tournament");

		
		
		numOfGames=(Integer)numberOfGamesCombobox.getSelectedItem();
		turns=(Integer)numberOfTurnsCombobox.getSelectedItem();
		startButton.addActionListener(new StartTournamentListener(maps,playerTypeComboBoxList,numberOfGamesCombobox,numberOfTurnsCombobox,numberOfMapsCombobox) );

		
		/////finally put everything there
		mainPanel.add(basicinfoPanel);
		mainPanel.add(playerPanel);
		mainPanel.add(mapsPanel);
		mainPanel.add(startButton);
		
		
	}
	
		
}
