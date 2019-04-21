package views.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import models.game.Aggressive;
import models.game.Benevolent;
import models.game.Cheater;
import models.game.Human;
import models.game.Player;
import models.game.Random;
import models.game.Tournament;
import models.map.Map;

public class StartTournamentListener implements ActionListener {
	
	private List<Map> maps;
	private List<Player> players;
	private int numOfGames;
	private int turns;
	private int numberOfMaps;
	private List<JComboBox> playerTypeComboBoxList;
	private JComboBox numberOfGamesCombobox;
	private JComboBox numberOfTurnsCombobox;
	private JComboBox numberOfMapsCombobox;
		
	
	public StartTournamentListener(List<Map> maps, List<JComboBox> playerTypeComboBoxList, JComboBox numberOfGamesCombobox,
			JComboBox numberOfTurnsCombobox, JComboBox numberOfMapsCombobox) {
		this.maps=maps;
		this.playerTypeComboBoxList=playerTypeComboBoxList;
		this.numberOfGamesCombobox=numberOfGamesCombobox;
		this.numberOfMapsCombobox=numberOfMapsCombobox;
		this.numberOfTurnsCombobox=numberOfTurnsCombobox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.numOfGames=(Integer)(numberOfGamesCombobox.getSelectedItem());
		this.turns=(Integer)(numberOfTurnsCombobox.getSelectedItem());
		this.numberOfMaps=(Integer)(numberOfMapsCombobox.getSelectedItem());;
		
		
		if(numberOfMaps!=maps.size()){
			//todo popup error
			System.out.println("missing maps");
		}
		else{
			this.players=new ArrayList<Player>();
			int numOfPlayer=playerTypeComboBoxList.size();
			for(int i = 0; i < numOfPlayer; i++) {
				Player p = new Player();
				p.setId(i);
				JComboBox comboBox=playerTypeComboBoxList.get(i);
				String type=(String)comboBox.getSelectedItem();
				switch (type){
					case "Human":
						p.setStrategy(new Human());
						break;
					case "Aggressive":
						p.setStrategy(new Aggressive());
						break;
					case "Benevolent":
						p.setStrategy(new Benevolent());
						break;
					case "Random":
						p.setStrategy(new Random());
						break;
					case "Cheater":
						p.setStrategy(new Cheater());
				}
				players.add(p);
			}
			
			Tournament tournament=new Tournament(maps, players, numOfGames, turns);
			tournament.run();
		}
	}

}
