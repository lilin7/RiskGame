package controllers.map;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.map.Map;
import views.map.BasicInfoView;

/**
 * The Class BasicInfoController. It shows information of the player.
 * @author Lin Li
 * @version 1.0
 */
public class BasicInfoController implements ActionListener{
	/** The map. */
	Map map;
	
	/** The view. */
	BasicInfoView view;
	
	/**
	 * Instantiates a new basic info controller.
	 *
	 * @param map the map
	 * @param view the view
	 */
	public BasicInfoController(Map map, BasicInfoView view){
		this.map = map;
		this.view = view;
	}
	
	/** 
	 * This is the method to pass the action to the updateBasicInfo method
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		 
		updateBasicInfo();
	}
	
	/**
	 * Update basic info. 
	 */
	public void updateBasicInfo() {
		map.setAuthor(view.getAuthorText());
		map.setImage(view.getImageText());
		map.setScroll(view.getScrollText());
		map.setWrap(view.getWrapText());
		map.setWarn(view.getWarnText());
		this.view.setVisible(false);
	}
}
