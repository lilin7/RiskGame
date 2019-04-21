package controllers.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import models.game.GameState;

/**
 * Class MenuController for load and save game controller
 * @version 3.0
 */
public class MenuController implements ActionListener {

	/**
	 * Action perform when clicking Load and Save game
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		switch(actionCommand) {
		case "Load":
			loadGame();
			break;
		case "Save":
			saveGame();
			break;
		}	
	}
	
	/**
	 * Class to save game
	 */
	public void saveGame() {
		String fileName = new SimpleDateFormat("yyyyMMddHHmm'.save'").format(new Date());
		String currentPath=System.getProperty("user.dir");
    	String absoluteFilePath = currentPath+"\\save\\"+fileName;
		File file = new File(absoluteFilePath);
		GameState.getInstance().saveGameToFile(file);
	}
	
	/**
	 * Class to load game
	 */
	public void loadGame() {
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		int returnValue = jfc.showOpenDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			GameState.reset();
			File selectedFile = jfc.getSelectedFile();
			GameState.getInstance().loadGameFromFile(selectedFile);
		}	
	}
}