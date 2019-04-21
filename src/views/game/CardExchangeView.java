package views.game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import models.game.Card;
import models.game.Player;
import models.game.CardType;
import javax.swing.JFrame;

/**
 * The Class CardExchangeView. This class is to create a pop up window for player to exchange card in reinforcement phase.
 * @version 2.0
 */
public class CardExchangeView extends JFrame implements Observer {
	
	/** The main panel. */
	private JPanel 	mainPanel = new JPanel();
	
	/** The cardpanel. */
	private JPanel cardpanel=new JPanel(new GridLayout(0,2));
	
	/** The cards. */
	List<Card> cards ;
	
	/** The card labels. */
	List<JLabel> cardLabels;
    
    /** The toggle buttons. */
    List<JToggleButton> toggleButtons;
	
	/** The exchangeButton. */
	private JButton exchangebutton;
	
	/** The originalView. */
	ReinforcementView originalview;
	
	/** The returnButton. */
	private JButton returnbutton=new JButton("Return");
	
	//new add
	private Player player;
	
	/**
	 * Instantiates a new card exchange frame.
	 *
	 * @throws HeadlessException the headless exception
	 */
	public CardExchangeView(Player player,ReinforcementView reinforcementView)  {
		this.player = player;
		this.originalview = reinforcementView;
		this.originalview.disable(); 
		this.setSize(300,500);
		getContentPane().add(mainPanel, BorderLayout.NORTH);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
		this.init();
		
		
	}
	
	
	/**
	 * Instantiates the card exchange view
	 */
	public void init()
	{
	
		
		this.mainPanel.setLayout((new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS)));
		returnbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	mainPanel.removeAll();
        		mainPanel.revalidate();
        		mainPanel.repaint();

        		cardpanel.removeAll();
        		cardpanel.revalidate();
        		cardpanel.repaint();
        		CardExchangeView.super.dispose();
        		
        		originalview.updateLabels();
        		originalview.enable();
            }
        });
		
		exchangebutton=new JButton("Exchange");
		
		exchangebutton.setVisible(false);
		exchangebutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				List<Card> checkedcards = new ArrayList<Card>();
				for(int i =0; i<toggleButtons.size();i++)
				{
					if(toggleButtons.get(i).isSelected())                //if the toggle is checked, then record the corresponding card
					{
						checkedcards.add(cards.get(i));
					}
				}
				int numberOfNewCards=player.exchangeCardforArmy(checkedcards);
				
			}
			
		});
		
				
		
		mainPanel.add(cardpanel);
		mainPanel.add(exchangebutton);
		mainPanel.add(returnbutton);

		 cards = player.getCardList();
		 if(cards.size()>4)
		 {
			 returnbutton.setVisible(false);
		 }
		 else
		 {
			 returnbutton.setVisible(true);
		 }
		 cardLabels = new ArrayList<JLabel>();
	     toggleButtons = new ArrayList<JToggleButton>();
		for(Card c:cards)
		{	
			String cardName="";
			switch (c.getCardType()) {
			case INFANTRY: 
				cardName="INFANTRY";
				break;
			case CAVALRY:
				cardName = "CAVALRY";
				break;
			case ARTILLERY:
				cardName = "ARTILLERY";
			}
			
			JLabel cardlabel = new JLabel(cardName);
			cardLabels.add(cardlabel);
			JToggleButton toggle=new JToggleButton();
			toggleButtons.add(toggle);
			//this is to make the check boxes only have 3 max to be checked
			toggle.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					int totalChecked=0;
					for(JToggleButton tg:toggleButtons){
						if(tg.isSelected())
						{
							totalChecked++;
						}
					}
					
					if(totalChecked>=3){//need to disable the unchecked ones to prevent more to be checked
						for(JToggleButton tg:toggleButtons){
							if(!tg.isSelected())
							{
								tg.setEnabled(false);
							}
						}
					}
					else
					{
						for(JToggleButton tg:toggleButtons){
							tg.setEnabled(true);
						}
					}
					//if total checked three same ones or one of each, display the exchange button
					if(totalChecked==3)
					{
						List<Card> checkedcards = new ArrayList<Card>();
						
						for(int i =0; i<toggleButtons.size();i++)
						{
							if(toggleButtons.get(i).isSelected())//if the toggle is checked, then record the corresponding card
							{
								checkedcards.add(cards.get(i));
							}
						}
						if(checkedcards.get(0).getCardType().equals(checkedcards.get(1).getCardType())&&
								checkedcards.get(0).getCardType().equals(checkedcards.get(2).getCardType()))//all three cards are the same
						{
							exchangebutton.setVisible(true);
						}
						else if (!checkedcards.get(0).getCardType().equals(checkedcards.get(1).getCardType()) && 
								!checkedcards.get(0).getCardType().equals(checkedcards.get(2).getCardType())&&
								!checkedcards.get(1).getCardType().equals(checkedcards.get(2).getCardType()))// all three cards are different
						{
							exchangebutton.setVisible(true);

						}
						else
						{
							exchangebutton.setVisible(false);

						}
					}
					else
					{
						exchangebutton.setVisible(false);
					}
					
				}
			});
			
			cardpanel.add(cardlabel);
			cardpanel.add(toggle);
			

			
		}
		
	}
	/** This class is to update the card exchange view.*/
	 
	@Override
	public void update(Observable o, Object arg) {
		
		this.mainPanel.removeAll();
		this.mainPanel.revalidate();
		this.mainPanel.repaint();

		this.cardpanel.removeAll();
		this.cardpanel.revalidate();
		this.cardpanel.repaint();
		
		this.init();
		this.originalview.updateLabels();
		
	}
	
	
}
