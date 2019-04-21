package views.game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controllers.game.AttackController;
import controllers.game.FortificationController;
import models.game.GameState;
import models.game.Human;
import models.game.Player;
import models.game.GameState.Phase;
import models.map.Country;

/**
 * class AttackView is the view for attack phase
 * @version 3.0
 * @author Parisa
*/
public class AttackView {
	
	/**
	 * Different situations when clicking different buttons
	 */
	public final static String EndAttackPhaseStr =  "End Attack Phase";
	public final static String RollDiceStr =  "ROLL THE DICE!";
	public final static String MoveArmiesStr =  "Move Armies";
	public final static String StopAttackStr =  "Stop Attack";
	public final static String ContinueStr =  "Continue!";
	public final static String AllOutStr =  "All Out Mode";
	
	/**
	 * UI Objects that will be modified depending of state
	 */
	private JPanel actionPanel;
	private JButton endAttBtn;

	
	private int armiesNumberToMove;
	private boolean isActionListenerCountryActive = false;
	private boolean isActionListenerDiceActive = false;
	private boolean isActionListenerCountryLabelActive = false;
	private Country selectedCountryFrom;
	private Country selectedCountryTo;
	private int attackerDiceNumber;
	private int defenderDiceNumber;
	
	/**
	 * Objects requiring a full lifescope to transit informations with the controller
	 */
	private JComboBox<String> fromDropBox = new JComboBox<String>();
	private JComboBox<String> targetDropBox = new JComboBox<String>();
	private JTextField qtTextField = new JTextField();
	public ArrayList<Integer> results = new ArrayList<Integer>();
	
	/**
	 * Clear the action Panel
	 */
	private void clearAction() {
		actionPanel.removeAll();
		actionPanel.revalidate();
		actionPanel.repaint();
	}
	
	/**
	 * getter and setter methods
	 */
	
	public Country getSelecterdCountryFrom() {
		return selectedCountryFrom;
	}

	public Country getSelecterdCountryTo() {
		return selectedCountryTo;
	}

	public int getAttacherDiceNumber() {
		return attackerDiceNumber;
	}

	public int getDefenderDiceNumber() {
		return defenderDiceNumber;
	}

	public int getArmiesNumberToMove() {
		return armiesNumberToMove;
	}
	
	/**
	 * Constructor of class AttackView. 
	 * @param controlPanel
	 */
	public AttackView(JPanel controlPanel) {
		//do strategy first, may not need to create the view if not human
		GameState.getInstance().setPhase(Phase.ATTACK);
		Player currentPlayer= GameState.getInstance().getCurrentPlayer();
		currentPlayer.doStrategyAttack();
		
		if(!(currentPlayer.getStrategy() instanceof Human))	{//if not human, directly jump to attack, other wise wait
			
			if (GameState.getInstance().getMap().mapOwner(GameState.getInstance().getCurrentPlayer())) {
				GameState.getInstance().setPhase(Phase.FINISHED);
				StateView.getInstance().showEndGameView();
			}
			else if(GameState.getInstance().getCurrentPlayer().getArmyNumber() == 0) {
				// current player ended his/her turn.
				GameState.getInstance().endPlayerTurn();
				GameState.getInstance().setPhase(Phase.REINFORCEMENT);
				StateView.getInstance().showReinforcementView();					
			}
			else {
			GameState.getInstance().setPhase(Phase.FORTIFICATION);
			StateView.getInstance().getMapPanel().addCountryTableForMap(GameState.getInstance().getMap());
			StateView.getInstance().showFortificationView();
			}
		}	
		else//if human, show the view
		{
			// Prepare this view layout ; info, middle panel that changes, end phase button
			controlPanel.setLayout(new GridLayout(0,3));
			
			//prepare the three panels
			prepareInformationPanel(controlPanel);
			
			actionPanel = new JPanel();
			controlPanel.add(actionPanel);
			actionPanel.setLayout(new GridLayout(3, 1));
			
			JPanel rightPanel = new JPanel();
			endAttBtn = new JButton(EndAttackPhaseStr);
			endAttBtn.setVerticalAlignment(SwingConstants.BOTTOM);
			endAttBtn.addActionListener(new AttackController(this));
			
			controlPanel.add(rightPanel);
	
			showSelectionState();
		
		}
		
		
	}
	
	/**
	 * Static information panel to display current turn information,
	 * the phase view using the Observer pattern
	 * @param controlPanel
	 */
	private void prepareInformationPanel(JPanel controlPanel) {
		JPanel informationPanel = new JPanel();
		controlPanel.add(informationPanel);
		informationPanel.setLayout(new GridLayout(0, 2));
		
		JPanel labelColumn = new JPanel();
		informationPanel.add(labelColumn);
		labelColumn.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel phaseTextLabel = new JLabel("Phase: ");
		labelColumn.add(phaseTextLabel);
		
		JLabel currentPlayerTextLabel = new JLabel("Current Player: ");
		labelColumn.add(currentPlayerTextLabel);
		
		JLabel ruleInfoLabel = new JLabel("Rule Information: ");
		labelColumn.add(ruleInfoLabel);
		
		JPanel dataColumn = new JPanel();
		informationPanel.add(dataColumn);
		dataColumn.setLayout(new GridLayout(0, 1));
		dataColumn.add(StateView.getInstance().getPhaseLabel()); // observer
		
		
		JLabel currentPlayerIndicator = new JLabel(String.valueOf(GameState.getInstance().getCurrentPlayer().getId()));
		dataColumn.add(currentPlayerIndicator);
		
		
		JLabel ruleInfoDisplay=new JLabel(StateView.getInstance().getRuleInfoLabel().getText());

		dataColumn.add(ruleInfoDisplay);
		
		
		 String attackInfoRule="In the attack phase"+
				 "the player may choose one of the countries he owns that contains two or more armies, and declare an attack on"+
		 		"an adjacent country that is owned by another player. A battle is then simulated by the attacker rolling at most 3"+ 
		 		"dice (which should not be more than the number of armies contained in the attacking country) and the defender"+
		 		"rolling at most 2 dice (which should not be more than the number of armies contained in the attacking country)."+
		 		"The outcome of the attack is determined by comparing the defenders best dice roll with the attackers best dice"+ 
		 		"roll. If the defender rolls greater or equal to the attacker then the attacker loses an army otherwise the defender"+ 
		 		"loses an army. If the defender rolled two dice then his other dice roll is compared to the attacker's second best\n"+
		 		"dice roll and a second army is lost by the attacker or defender in the same way. The attacker can choose to\n"+
		 		"continue attacking until either all his armies or all the defending armies have been eliminated. If all the defender's\n"+ 
		 		"armies are eliminated the attacker captures the territory. The attacking player must then place a number of armies\n"+
		 		"in the conquered country which is greater or equal than the number of dice that was used in the attack that\n"+
		 		"resulted in conquering the country. A player may do as any attacks as he wants during his turn. Once he declares\n"+ 
		 		"that he will not attack anymore (or cannot attack because none of his countries that have an adjacent country\n"+
		 		"controlled by another player is containing more than one army), the fortification phase begins</html>";
		 		
		
		ruleInfoLabel.setToolTipText(attackInfoRule);
		String str=ruleInfoLabel.getToolTipText();
		System.out.print(str);
	}

	class CountLabelListener implements ActionListener {
		private JLabel label;
		private boolean needsSame; //to facilitate code re-use
		
		CountLabelListener(JLabel label, boolean needsSame) {
			this.label = label;
			this.needsSame = needsSame;
		}
		
        public void actionPerformed(ActionEvent event) {
        	
	        	JComboBox comboBox = (JComboBox) event.getSource();
	            String selected = (String)comboBox.getSelectedItem();
	            if (selected != null) {
		            Country selectedCountry = null;
		            for(Country c: GameState.getInstance().getMap().getCountryList()) {
		            	if (c.getName().equals(selected))
		            		selectedCountry = c; 
		            }
		            
		            //check if the owner of the selected country is the same as current player
		            boolean sameOwner = selectedCountry.getOwner() == GameState.getInstance().getCurrentPlayer();
		            
		            //if select a country and the owner is the current player,
		            //show the number of armies of this country
		            if ( selectedCountry != null && (sameOwner == needsSame) ) {
		            	label.setText(String.valueOf(selectedCountry.getNumOfArmies()));
		            }
	            }
	       
        }
	}
	
	/**
	 * Let player to select the countries, number of armies, etc, in the attack phase
	 */
	public void showSelectionState() {
			
		clearAction();
		endAttBtn.setEnabled(true);
		
		
		final JComboBox attackerDiceNumberDropBox = new JComboBox();
		final JComboBox defenderDiceNumberDropBox = new JComboBox();		
		

		JPanel actionInforPanel = new JPanel();
		actionPanel.add(actionInforPanel);
		actionInforPanel.setLayout(new GridLayout(1, 3));
		
		JPanel actionTextPanel = new JPanel();
		actionInforPanel.add(actionTextPanel);
		actionTextPanel.setLayout(new GridLayout(2, 1));
		
		JLabel fromTextPanel = new JLabel("Attack From: ");
		actionTextPanel.add(fromTextPanel);
		
		JLabel targetTextPanel = new JLabel("Attack Target: ");
		actionTextPanel.add(targetTextPanel);
		
		JPanel actionCountryInfoPanel = new JPanel();
		actionInforPanel.add(actionCountryInfoPanel);

		JLabel numberOfArmyInFromCountry = new JLabel("Number of Armies");
		JLabel numberOfArmyInTargetCountry = new JLabel("Number of Armies");
		
		//clear drop down box
		fromDropBox.removeAllItems();
		targetDropBox.removeAllItems();
		
		//get list for drop down box
        
        isActionListenerCountryActive = false;
        fromDropBox.removeAllItems();
        for(Country c: GameState.getInstance().getMap().getCountryList()) {
        	if (c.getOwner() == GameState.getInstance().getCurrentPlayer() &&
        		c.getNumOfArmies() > 1 && c.hasAdjacentControlledByOthers())
        		fromDropBox.addItem(c.getName());
        }
        fromDropBox.revalidate();
        fromDropBox.repaint();
		isActionListenerCountryActive = true;

		
		actionCountryInfoPanel.setLayout(new GridLayout(0, 2, 0, 0));
		actionCountryInfoPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		actionCountryInfoPanel.add(fromDropBox);
		
		
		fromDropBox.addActionListener(new CountLabelListener(numberOfArmyInFromCountry, true));
		
		fromDropBox.addActionListener((ActionListener) new ActionListener() {
            public void actionPerformed(ActionEvent event) {
	            	if (isActionListenerCountryActive) {
		            	JComboBox comboBox = (JComboBox) event.getSource();
		                String selected = (String)comboBox.getSelectedItem();
		                Country selectedCountry = null;
		                for(Country c: GameState.getInstance().getMap().getCountryList()) {
		                	if (c.getName().equals(selected)) {
		                		selectedCountry = c; 
		                		selectedCountryFrom = c;                		
		                	}
		                }
	                
		                //get target countries for the drop down box, only show the adjacent countries of the "from country" which has different owner
		                isActionListenerCountryActive = false;
		                if(selectedCountry != null) {
		                	targetDropBox.removeAllItems();
			                for( Country n: selectedCountry.getAdjacentCountryList()) {
			                	if ( n.getOwner() != GameState.getInstance().getCurrentPlayer() ) {
			                		targetDropBox.addItem(n.getName());
			                	}
			                }
			                targetDropBox.revalidate();
			                targetDropBox.repaint();
		                }
		                isActionListenerCountryActive = true;
	            	}
            }
         });
		
		JLabel label = new JLabel("");
		actionCountryInfoPanel.add(label);
		actionCountryInfoPanel.add(targetDropBox);
		targetDropBox.addActionListener(new CountLabelListener(numberOfArmyInTargetCountry, false));

		targetDropBox.addActionListener((ActionListener) new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (isActionListenerCountryActive) {
                	JComboBox comboBox = (JComboBox) event.getSource();
	                String selected = (String)comboBox.getSelectedItem();
	                for(Country c: GameState.getInstance().getMap().getCountryList()) {
	                	if (c.getName().equals(selected)) {
	                		selectedCountryTo = c;                		
	                	}
	                }
	 
                	isActionListenerDiceActive = false;
	                attackerDiceNumberDropBox.removeAllItems();
	                for (int i=1; i<= Math.min(3,selectedCountryFrom.getNumOfArmies()); i++)
	                	attackerDiceNumberDropBox.addItem(i);
	                attackerDiceNumberDropBox.revalidate();
	                attackerDiceNumberDropBox.repaint();

	                defenderDiceNumberDropBox.removeAllItems();
	                for (int i=1; i<= Math.min(2,Math.min(selectedCountryTo.getNumOfArmies(),selectedCountryFrom.getNumOfArmies())); i++)
	                	defenderDiceNumberDropBox.addItem(i);
	                defenderDiceNumberDropBox.revalidate();
	                defenderDiceNumberDropBox.repaint();

	                isActionListenerDiceActive = true;
	                if (attackerDiceNumberDropBox.getItemCount()>0)
	                	attackerDiceNumberDropBox.setSelectedIndex(0);
	                if (defenderDiceNumberDropBox.getItemCount()>0)
	                	defenderDiceNumberDropBox.setSelectedIndex(0);
                }
            }
         });
        
		JPanel actionNumberOfArmy = new JPanel();
		actionInforPanel.add(actionNumberOfArmy);
		actionNumberOfArmy.setLayout(new GridLayout(2, 1));
		
		actionNumberOfArmy.add(numberOfArmyInFromCountry);	
		actionNumberOfArmy.add(numberOfArmyInTargetCountry);

		
		JPanel diceNumberPanel = new JPanel();
		actionPanel.add(diceNumberPanel);
		diceNumberPanel.setLayout(new GridLayout(2, 1));
		
		JLabel attackerDiceNumberText = new JLabel("Attacker Dice Number: ");
		diceNumberPanel.add(attackerDiceNumberText);		
					
		diceNumberPanel.add(attackerDiceNumberDropBox);
		attackerDiceNumberDropBox.addActionListener((ActionListener) new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if (isActionListenerDiceActive) {
	            	JComboBox comboBox = (JComboBox) event.getSource();
	            	if (!comboBox.getSelectedItem().equals(null))
	                	attackerDiceNumber = Integer.parseInt(comboBox.getSelectedItem().toString());
            	}
            }
		});
		
                
		JLabel defenderDiceNumberText = new JLabel("Defender Dice Number: ");
		diceNumberPanel.add(defenderDiceNumberText);

		diceNumberPanel.add(defenderDiceNumberDropBox);
		defenderDiceNumberDropBox.addActionListener((ActionListener) new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if (isActionListenerDiceActive) {
	            	JComboBox comboBox = (JComboBox) event.getSource();
	            	if (!comboBox.getSelectedItem().equals(null))
	                	defenderDiceNumber = Integer.parseInt(comboBox.getSelectedItem().toString());
            	}
            }
		});
				
		
		JPanel actionButtonPanel = new JPanel();
		actionPanel.add(actionButtonPanel);
		
		JButton actionButton = new JButton(RollDiceStr);
		actionButton.addActionListener(new AttackController(this));

		JButton allOutButton = new JButton(AllOutStr);
		allOutButton.addActionListener(new AttackController(this));

		JLabel label_1 = new JLabel("");
		actionCountryInfoPanel.add(label_1);
		actionButtonPanel.add(actionButton);
		actionButtonPanel.setLayout(new GridLayout(2, 1));
		
		actionButtonPanel.add(endAttBtn);
		actionButtonPanel.add(allOutButton);
	}


	/**
	 * After player chooses from which country to attack, the target, and number of dice, show the result of dice play
	 * @param attackerDice
	 * @param defenderDice
	 * @param attackerLost
	 * @param defenderLost
	 */
	public void showResolutionState(int[] attackerDice, int[] defenderDice, int attackerLost, int defenderLost) {
		clearAction();

		JPanel actionInforPanel = new JPanel();
		actionPanel.add(actionInforPanel);
		actionInforPanel.setLayout(new GridLayout(3, 1));
		
		JPanel actionTextPanel = new JPanel();
		actionInforPanel.add(actionTextPanel);
		actionTextPanel.setLayout(new GridLayout(2, 1));
		
		JLabel attackerDiceTextPanel = new JLabel("Attacker Dices: ");
		actionTextPanel.add(attackerDiceTextPanel);
		
		JLabel defenderDiceTextPanel = new JLabel("Defender Dices: ");
		actionTextPanel.add(defenderDiceTextPanel);
		
		JPanel actionDiceInfoPanel = new JPanel();
		actionInforPanel.add(actionDiceInfoPanel);
		actionDiceInfoPanel.setLayout(new GridLayout(2, 3));

		JLabel attackerDice1 = new JLabel(String.valueOf(attackerDice[0]));
		actionDiceInfoPanel.add(attackerDice1);

		JLabel attackerDice2 = new JLabel("");
		actionDiceInfoPanel.add(attackerDice2);
		if(attackerDice.length>1) {
			attackerDice2.setText(String.valueOf(attackerDice[1]));
		}

		JLabel attackerDice3 = new JLabel("");
		actionDiceInfoPanel.add(attackerDice3);
		if(attackerDice.length>2) {
			attackerDice3.setText(String.valueOf(attackerDice[2]));
		}
		
		JLabel defenderDice1 = new JLabel(String.valueOf(defenderDice[0]));
		actionDiceInfoPanel.add(defenderDice1);				
		
		JLabel defenderDice2 = new JLabel("");
		actionDiceInfoPanel.add(defenderDice2);			
		if(defenderDice.length>1) {
			defenderDice2.setText(String.valueOf(defenderDice[1]));
		}

		
		JPanel lostArmyTextPanel = new JPanel();
		actionInforPanel.add(lostArmyTextPanel);
		lostArmyTextPanel.setLayout(new GridLayout(2, 1));

		JLabel attackerLostTextPanel = new JLabel("Attacker Lost Armies: ");
		lostArmyTextPanel.add(attackerLostTextPanel);
		
		JLabel defenderLostTextPanel = new JLabel("Defender Lost Armies: ");
		lostArmyTextPanel.add(defenderLostTextPanel);

		JPanel lostArmyPanel = new JPanel();
		actionInforPanel.add(lostArmyPanel);
		lostArmyPanel.setLayout(new GridLayout(2, 1));

		JLabel attackerLostPanel = new JLabel(String.valueOf(attackerLost));
		lostArmyPanel.add(attackerLostPanel);
		
		JLabel defenderLostPanel = new JLabel(String.valueOf(defenderLost));
		lostArmyPanel.add(defenderLostPanel);
		
		
		JPanel actionButtonPanel = new JPanel();
		actionInforPanel.add(actionButtonPanel);
		
		JButton actionButton = new JButton(ContinueStr);
		actionButtonPanel.add(actionButton);
		actionButton.addActionListener(new AttackController(this));
		
	}

	/**
	 * After conquering, move army
	 * @param minArmies
	 */
	public void showMoveArmiesState(int minArmies) {
		clearAction();

		JPanel actionInforPanel = new JPanel();
		actionPanel.add(actionInforPanel);
		actionInforPanel.setLayout(new GridLayout(1, 2));
		
		JPanel actionTextPanel = new JPanel();
		actionInforPanel.add(actionTextPanel);
		actionTextPanel.setLayout(new GridLayout(4, 1));
		
		JLabel fromCountryTextPanel = new JLabel("From Country: ");
		actionTextPanel.add(fromCountryTextPanel);

		JLabel fromCountryArmiesTextPanel = new JLabel("Number of Armies: ");
		actionTextPanel.add(fromCountryArmiesTextPanel);
		
		JLabel toCountryTextPanel = new JLabel("Conquered Country: ");
		actionTextPanel.add(toCountryTextPanel);

		JLabel armyNumberTextPanel = new JLabel("Number of Armies to Move: ");
		actionTextPanel.add(armyNumberTextPanel);
		
		JPanel actionValuePanel = new JPanel();
		actionInforPanel.add(actionValuePanel);
		actionValuePanel.setLayout(new GridLayout(4, 1));

		JLabel fromCountry = new JLabel(selectedCountryFrom.getName());
		actionValuePanel.add(fromCountry);

		JLabel fromCountryArmies = new JLabel(String.valueOf(selectedCountryFrom.getNumOfArmies()));
		actionValuePanel.add(fromCountryArmies);

		JLabel toCountry = new JLabel(selectedCountryTo.getName());
		actionValuePanel.add(toCountry);
		
		JComboBox armiesNumber = new JComboBox();
		actionValuePanel.add(armiesNumber);
		for (int i=minArmies; i<selectedCountryFrom.getNumOfArmies(); i++)
			armiesNumber.addItem(i);
		
		armiesNumber.addActionListener((ActionListener) new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	JComboBox comboBox = (JComboBox) event.getSource();
            	if (!comboBox.getSelectedItem().equals(null))
                	armiesNumberToMove = Integer.parseInt(comboBox.getSelectedItem().toString());

            }			
		});		
		
		JPanel actionButtonPanel = new JPanel();
		actionInforPanel.add(actionButtonPanel);
		
		JButton actionButton = new JButton(MoveArmiesStr);
		actionButtonPanel.add(actionButton);
		actionButton.addActionListener(new AttackController(this));
	}	

}
