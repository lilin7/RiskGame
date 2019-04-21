package views.map;

import javax.swing.JFrame;

import models.map.Map;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controllers.map.BasicInfoController;

import javax.swing.JRadioButton;
import javax.swing.JButton;

/**
 * The Class BasicInfoView. This class shows the view of the game on its startup phase.
 * @version  1.0
 */
public class BasicInfoView extends JFrame{

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3406877960836351665L;
	
	/** The author text. */
	private JTextField authorText;
	
	/** The image text. */
	private JTextField imageText;
	


	/** The wrap text. */
	private JTextField wrapText;
	
	/** The scroll text. */
	private JTextField scrollText;
	
	/** The warn text. */
	private JTextField warnText;
	

	/**
	 * Instantiates a new basic info view.
	 *
	 * @param map the map
	 */
	public BasicInfoView(Map map) {
		this.setSize(480,350);
		getContentPane().setLayout(null);
		
		JLabel authorLabel = new JLabel("Author");
		authorLabel.setBounds(124, 30, 56, 16);
		getContentPane().add(authorLabel);
		
		authorText = new JTextField();
		authorText.setBounds(195, 27, 116, 22);
		getContentPane().add(authorText);
		authorText.setColumns(10);
		
		JLabel imageLabel = new JLabel("Image");
		imageLabel.setBounds(124, 59, 56, 16);
		getContentPane().add(imageLabel);
		
		imageText = new JTextField();
		imageText.setBounds(195, 56, 116, 22);
		getContentPane().add(imageText);
		imageText.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Wrap");
		lblNewLabel_2.setBounds(124, 88, 56, 16);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Scroll");
		lblNewLabel_3.setBounds(124, 117, 56, 16);
		getContentPane().add(lblNewLabel_3);
		
		wrapText = new JTextField();
		wrapText.setBounds(195, 85, 116, 22);
		getContentPane().add(wrapText);
		wrapText.setColumns(10);
		
		scrollText = new JTextField();
		scrollText.setBounds(195, 114, 116, 22);
		getContentPane().add(scrollText);
		scrollText.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Warn");
		lblNewLabel_4.setBounds(124, 146, 56, 16);
		getContentPane().add(lblNewLabel_4);
		
		warnText = new JTextField();
		warnText.setBounds(195, 143, 116, 22);
		getContentPane().add(warnText);
		warnText.setColumns(10);
		
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(176, 223, 97, 25);
		getContentPane().add(saveButton);
		
		
		this.authorText.setText(map.getAuthor());
		this.imageText.setText(map.getImage());
		this.wrapText.setText(map.getWrap());
		this.scrollText.setText(map.getScroll());
		this.warnText.setText(map.getWarn());
		
		saveButton.addActionListener(new BasicInfoController(map ,this));
		
		
		
	}
	
	/**
	 * Gets the author text.
	 *
	 * @return the author text
	 */
	public String getAuthorText() {
		return authorText.getText();
	}


	


	/**
	 * Gets the image text.
	 *
	 * @return the image text
	 */
	public String getImageText() {
		return imageText.getText();
	}


	


	/**
	 * Gets the wrap text.
	 *
	 * @return the wrap text
	 */
	public String getWrapText() {
		return wrapText.getText();
	}


	

	/**
	 * Gets the scroll text.
	 *
	 * @return the scroll text
	 */
	public String getScrollText() {
		return scrollText.getText();
	}


	

	/**
	 * Gets the warn text.
	 *
	 * @return the warn text
	 */
	public String getWarnText() {
		return warnText.getText();
	}


	
}
