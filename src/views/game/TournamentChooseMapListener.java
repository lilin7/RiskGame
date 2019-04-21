package views.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileSystemView;

import models.game.GameState;
import models.map.Map;

public class TournamentChooseMapListener implements ActionListener {

	private int j;
	private List<Map> maps;
	private JLabel mapPath;
	
	public TournamentChooseMapListener(List<Map> maps, int j,JLabel mapPath) {
		// TODO Auto-generated constructor stub
		this.j=j;
		this.maps=maps;
		this.mapPath=mapPath;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);
	
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			//GameState.getInstance().setSelectedFile(selectedFile);
			System.out.println("Map"+(j+1)+"uses "+selectedFile.getAbsolutePath());
			
			Map map=maps.get(j);
			map.loadMapFromFile(selectedFile);
			mapPath.setText(selectedFile.getAbsolutePath());
		}

		
	}

}
