package controllers.game;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import models.game.GameState;
import models.map.Map;
import views.game.StateView;

/**
 * The Class PlayerSetupController. 
 * @see views.game.PlayerSetupView
 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
 * @version 3.0
 */
public class PlayerSetupController implements ActionListener{
	
	/**
	 * Instantiates a new player setup controller.
	 */
	public PlayerSetupController() {
		
	}
	
	/**
	 * This method is triggered by "NEW GAME" button. 
	 * It opens up a new dialog and allows player to choose map file. 
	 * The file is passed to PlayerSetupView
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			GameState.getInstance().setSelectedFile(selectedFile);
			System.out.println(selectedFile.getAbsolutePath());
			boolean result = GameState.getInstance().loadMapFromFile(selectedFile);
			if(result) {
				StateView.getInstance().showPlayerView();
			}
			else {
				JOptionPane.showMessageDialog(null, "Not a valid map!");
			}
		}		
	}
}