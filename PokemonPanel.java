import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Choice;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Random;

/**
* This program is a GUI Pokemon game where the user can
*  create a team of Pokemon and play against the program.
* @author Jun Kim
* @since 12/15/21
*/

public class PokemonPanel extends JPanel {

   /** Number of Pokemon available. */
   static final int NUM_POKEMON = 9;
   
   /** "Choose Pokemon" label. */
   private JLabel lAvailable = new JLabel("   Choose a Pokemon:");
   /** "Your Team" label. */
   private JLabel lYourTeam = new JLabel("    Your Team:");
   /** "Opponent's Team" label. */
   private JLabel lOppTeam = new JLabel("          Opponent's Team:");
   /** Whitespace label. */
   private JLabel lBlank = new JLabel("                                       ");
   
   /** Top sub-panel. */
   private JPanel northSubPanel = new JPanel(new GridBagLayout());
   /** Center sub-panel. */
   private JPanel centerSubPanel = new JPanel();
   /** East sub-panel. */
   private JPanel eastSubPanel = new JPanel();
   /** West sub-panel. */
   private JPanel westSubPanel = new JPanel();
   /** South sub-panel. */
   private JPanel southSubPanel = new JPanel(new GridBagLayout());
   
   /** "Add Pokemon" button. */
   private JButton bAddPokemon = new JButton("Add Pokemon to Team");
   /** "Clear Team" button. */
   private JButton bClearTeam = new JButton("Clear Team");
   /** "Start Battle" button. */
   private JButton bBattle = new JButton("Start Battle!");
   /** "Save Battle Log to File" button. */
   private JButton bSaveFile = new JButton("Save Battle Log to File");
   
   /** GridBag Layout constraint for "Add Pokemon" button. */
   private GridBagConstraints addConstraint = new GridBagConstraints();
   /** GridBag Layout constraint for "Clear Team" button. */
   private GridBagConstraints clearConstraint = new GridBagConstraints();
   /** GridBag Layout constraint for "Start Battle" button. */
   private GridBagConstraints battleConstraint = new GridBagConstraints();
   /** GridBag Layout constraint for drop-down menu. */
   private GridBagConstraints choiceConstraint = new GridBagConstraints();
   /** GridBag Layout constraint for whitespace label. */
   private GridBagConstraints blankConstraint = new GridBagConstraints();
   /** GridBag Layout constraint for player's team text area. */
   private GridBagConstraints playerConstraint = new GridBagConstraints();
   /** GridBag Layout constraint for opponent's team text area. */
   private GridBagConstraints oppConstraint = new GridBagConstraints();
   /** GridBag Layout constraint for file status label. */
   private GridBagConstraints statusConstraint = new GridBagConstraints();

   /** Text area to list player's team. */
   private JTextArea taListPlayer = new JTextArea(25, 16);
   /** Text area to log battle. */
   private JTextArea taBattleLog = new JTextArea(25, 22);
   /** Text area to list opponent's team. */
   private JTextArea taListOpp = new JTextArea(25, 16);
   /** Text field to display file saved status. */
   private JTextField tfFileStatus = new JTextField(18);
   
   /** East scroll pane. */
   private JScrollPane eastScroll = new JScrollPane(
      taListPlayer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
   /** West scroll pane. */
   private JScrollPane westScroll = new JScrollPane(
      taListOpp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
   /** Center scroll pane. */
   private JScrollPane centerScroll = new JScrollPane(
      taBattleLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
         
   /** Drop down menu. */
   private Choice chPokemon = new Choice();
   
   /** Action Listener. */
   private GUIListener listener = new GUIListener();
   
   /** ArrayList to hold player's team. */
   private ArrayList<Pokemon> playerArray = new ArrayList<>();
   /** ArrayList to hold opponent's team. */
   private ArrayList<Pokemon> oppArray = new ArrayList<>();
   
   /** String to display player's team. */
   private String sPlayerTeam = "";
   /** String to display opponent's team. */
   private String sOppTeam = "";
   /** String to display battle log. */
   private String sBattleLog = "";
   /** Strings to display starting instructions. */
   private String sInstructions = "Instructions: \n\n"
      + "1. Choose a Pokemon from the drop-\n    down menu\n\n"
      + "2. Click \"Add Pokemon\" to add that\n    Pokemon to your team\n\n"
      + "3. For every Pokemon added, your\n    opponent will also gain a Pokemon\n\n"
      + "4. Click \"Start Battle\" to begin!";
         
   /** Random number generator. */
   private Random rand = new Random();
   /** Random number to pick a Pokemon from the player's team. */  
   private int playerIndex = 0;
   /** Random number to pick a Pokemon from the opponent's team. */
   private int oppIndex = 0; 
   
   /** Player's Pokemon that is currently in play. */
   private Pokemon playerPokemon = new Pokemon("Dummy", "Dummy", 1, 1);
   /** Opponent's Pokemon that is currently in play. */
   private Pokemon oppPokemon = new Pokemon("Dummy", "Dummy", 1, 1);
   
      /** Indicates whose turn it is. */
   private boolean playerTurn = true;
   /** Indicates whether the game is currently in progress or not. */
   private boolean gameInProgress = false;
   
   /** File object to save team as file. */
   private File f = new File("Battle_Log.txt");
   
      
   /**
   * Constructor.
   */
   public PokemonPanel() {
   
      this.setLayout(new BorderLayout());
      this.setPreferredSize(new Dimension(745, 545));
      
      //Create dropdown list
      chPokemon.add("Random");
      chPokemon.add("Bulbasaur");
      chPokemon.add("Venusaur");
      chPokemon.add("Ivysaur");
      chPokemon.add("Squirtle");
      chPokemon.add("Wartortle");
      chPokemon.add("Blastoise");
      chPokemon.add("Charmander");
      chPokemon.add("Charmeleon");
      chPokemon.add("Charizard");
   
      //** NORTH SUBPANEL **//
      add("North", northSubPanel);
      //Drop-down list 
      northSubPanel.add(lAvailable);      
      choiceConstraint.gridx = 1;
      choiceConstraint.gridy = 0;
      choiceConstraint.anchor = GridBagConstraints.LINE_START;
      northSubPanel.add(chPokemon, choiceConstraint);
      
      //"Add Pokemon to Team" button
      addConstraint.gridx = 2;
      addConstraint.gridy = 0;
      northSubPanel.add(bAddPokemon, addConstraint);
      bAddPokemon.addActionListener(listener); 
      
      //"Clear Team" button
      clearConstraint.gridx = 3;
      clearConstraint.gridy = 0;
      northSubPanel.add(bClearTeam, clearConstraint);
      bClearTeam.addActionListener(listener);
      
      //"Start Battle" button      
      battleConstraint.gridx = 2;
      battleConstraint.gridy = 1;
      northSubPanel.add(bBattle, battleConstraint);
      bBattle.addActionListener(listener);
      
      //"Opponent's Team" label
      oppConstraint.gridx = 0;
      oppConstraint.gridy = 2;
      northSubPanel.add(lOppTeam, oppConstraint);
      
      //"Your Team" label
      playerConstraint.gridx = 4;
      playerConstraint.gridy = 2;
      playerConstraint.anchor = GridBagConstraints.LINE_START;
      northSubPanel.add(lYourTeam, playerConstraint);
      
      //Whitespaces for alignment           
      blankConstraint.gridx = 4;
      blankConstraint.gridy = 0;
      northSubPanel.add(lBlank, blankConstraint);
      blankConstraint.gridy = 1;
      northSubPanel.add(lBlank, blankConstraint); 
      blankConstraint.gridy = 2;
      northSubPanel.add(lBlank, blankConstraint);
   
      //** CENTER SUBPANEL **//
      add("Center", centerSubPanel);
      //Battle Log
      centerSubPanel.add(centerScroll);
      centerScroll.setBorder(null);
      centerScroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
      taBattleLog.setText(sInstructions);
      taBattleLog.setEditable(false);       
      
      //** EAST SUBPANEL **//
      add("East", eastSubPanel);
      //Add elements to list Player's team
      eastSubPanel.add(eastScroll);
      taListPlayer.setBackground(Color.CYAN);
      eastScroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
      taListPlayer.setEditable(false);           
      
      //** WEST SUBPANEL **//
      add("West", westSubPanel);
      //Add elements to list Opponent's team
      westSubPanel.add(westScroll);
      taListOpp.setBackground(Color.PINK);
      westScroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
      taListOpp.setEditable(false);
     
      //** SOUTH SUBPANEL **//
      add("South", southSubPanel);
      //"Save Battle Log as File" button
      southSubPanel.add(bSaveFile);
      bSaveFile.addActionListener(listener); 
   
      //File saved message
      statusConstraint.gridx = 0;
      statusConstraint.gridy = 1;
      southSubPanel.add(tfFileStatus, statusConstraint);
      tfFileStatus.setEditable(false);
      tfFileStatus.setBackground(UIManager.getColor("Panel.background"));      
   } //Close PokemonPanel constructor
      
      
   /**
   * Private ActionListener class.
   */
   private class GUIListener implements ActionListener {
   
   /**
   * ActionPerformed method.
   * @param event the button that is clicked.
   * @throws PokemonException if HP becomes negative
   */ 
      public void actionPerformed(ActionEvent event) throws PokemonException {
      
         /** "Add Pokemon" button */
         if (event.getSource() == bAddPokemon && !gameInProgress) {
            
            //Add a Pokemon to both teams
            if (chPokemon.getSelectedItem().equals("Random")) {
               playerArray.add(MakePokemon.forPlayer(Integer.toString(rand.nextInt(NUM_POKEMON))));
            } else {
               playerArray.add(MakePokemon.forPlayer(chPokemon.getSelectedItem())); 
            }      
            oppArray.add(MakePokemon.forOpp(Integer.toString(rand.nextInt(NUM_POKEMON))));
            
            //Display both teams
            sPlayerTeam += playerArray.get(playerArray.size() - 1).toString() + "\n\n";
            sOppTeam += oppArray.get(oppArray.size() - 1).toString() + "\n\n";
            taListPlayer.setText(sPlayerTeam);            
            taListOpp.setText(sOppTeam);
         }
         
         /** "Start Battle" button. Subsequently changes to say "Next Attack" */
         if (event.getSource() == bBattle && playerArray.size() > 0 && oppArray.size() > 0) {
            gameInProgress = true;
            playerIndex = rand.nextInt(playerArray.size());
            oppIndex = rand.nextInt(oppArray.size());
            playerPokemon = playerArray.get(playerIndex);
            oppPokemon = oppArray.get(oppIndex);
            bBattle.setText("Next Attack");
                                  
            //Attack and display battle message
            //Player's turn
            if (playerTurn) {
               try {
                  oppPokemon.setHp(oppPokemon.getHp() - playerPokemon.getAtk());
               } catch (PokemonException PE) {
                  oppPokemon.setHp(0);
               }
                  
               sBattleLog = sBattleLog.concat("Your turn:\n" 
                  + playerPokemon.getName() + " attacks " + oppPokemon.getName() + "!\n" 
                  + oppPokemon.getName() + "'s remaining HP: " + oppPokemon.getHp() + "\n");
               if (oppPokemon.getHp() == 0) {
                  sBattleLog = sBattleLog.concat(oppPokemon.getName() + " has fainted.\n");
                  oppArray.remove(oppIndex);
               }           
               //Update opponent's team display
               sOppTeam = "";
               for (int i = 0; i < oppArray.size(); i++) {
                  sOppTeam += oppArray.get(i).toString() + "\n\n";
                  taListOpp.setText(sOppTeam);
               }
               
            //Opponent's turn
            } else {
               try {
                  playerPokemon.setHp(playerPokemon.getHp() - oppPokemon.getAtk());
               } catch (PokemonException PE) {
                  playerPokemon.setHp(0);
               }                 
               sBattleLog = sBattleLog.concat("Opponent's turn:\n" 
                  + oppPokemon.getName() + " attacks " + playerPokemon.getName() + "!\n"
                  + playerPokemon.getName() + "'s remaining HP: " + playerPokemon.getHp() + "\n");
               if (playerPokemon.getHp() == 0) {
                  sBattleLog = sBattleLog.concat(playerPokemon.getName() + " has fainted.\n");
                  playerArray.remove(playerIndex);
               }
               //Update player team display
               sPlayerTeam = "";
               for (int i = 0; i < playerArray.size(); i++) {
                  sPlayerTeam += playerArray.get(i).toString() + "\n\n";
                  taListPlayer.setText(sPlayerTeam);
               }
            }
         
            //Battle end condition
            if (playerArray.size() == 0) {
               sBattleLog = sBattleLog.concat("\nYou have been defeated\n");
               taListPlayer.setText("");
               sOppTeam = "";
               bClearTeam.setText("Play Again");
               bClearTeam.setForeground(Color.RED);
            }
            if (oppArray.size() == 0) {
               sBattleLog = sBattleLog.concat("\nYou won!\n");
               taListOpp.setText("");
               sPlayerTeam = "";
               bClearTeam.setForeground(Color.RED);
               bClearTeam.setText("Play Again");
            }
                        
            sBattleLog += "\n";
            taBattleLog.setText(sBattleLog);
            playerTurn = !playerTurn;
         }
         
         
         /** "Save Battle Log as file" button */
         if (event.getSource() == bSaveFile) {
            try {
               FileWriter fw = new FileWriter(f);
               PrintWriter writer = new PrintWriter(fw);
               writer.println(sBattleLog);
               writer.close();
               tfFileStatus.setText("File saved as Battle_Log.txt");
               tfFileStatus.setBackground(Color.yellow);            
            } catch (IOException ioe) {
               tfFileStatus.setText("Error saving file. Please try again");
               tfFileStatus.setBackground(Color.yellow);
            }
         }
         
         
         //"Clear team" button
         if (event.getSource() == bClearTeam) {
            playerArray.clear();
            oppArray.clear();
            sPlayerTeam = "";
            sOppTeam = "";
            sBattleLog = "";
            bBattle.setText("Start Battle!");
            bClearTeam.setText("Clear Team");
            bClearTeam.setForeground(Color.BLACK);
            taListPlayer.setText("");
            taListOpp.setText("");
            taBattleLog.setText("");
            tfFileStatus.setText("");
            tfFileStatus.setBackground(UIManager.getColor("Panel.background"));
            gameInProgress = false;
            taBattleLog.setText(sInstructions);
         }
      } //Close actionPerformed method
   } //Close GUIListener class 
   
   
   /**
   * Generates new Pokemon objects.
   */
   private class MakePokemon {
   
      /**
      * Generates a Pokemon object for the player.
      * @param choice the choice of Pokemon, either the species or a random integer 0-8.
      * @return a Pokemon object.
      */ 
      private static Pokemon forPlayer(String choice) {
         switch(choice) {
            case "Bulbasaur":
            case "0":
               return new Pokemon("Bulbasaur", "Bobby", 80, 420);
            case "Venusaur":
            case "1":
               return new Pokemon("Venusaur", "Vicky", 95, 405);
            case "Ivysaur":
            case "2":
               return new Pokemon("Ivysaur", "Ida", 110, 390);
            case "Squirtle":
            case "3":
               return new Pokemon("Squirtle", "Sammy", 125, 375);
            case "Wartortle":
            case "4":
               return new Pokemon("Wartortle", "Watson", 140, 360);
            case "Blastoise":
            case "5":
               return new Pokemon("Blastoise", "Benjamin", 155, 345);
            case "Charmander":
            case "6":
               return new Pokemon("Charmander", "Chippy", 170, 330);
            case "Charmeleon":
            case "7":
               return new Pokemon("Charmeleon", "Chappy", 185, 315);
            case "Charizard":
            case "8":
               return new Pokemon("Charizard", "Choppy", 200, 300);
            default:
               return new Pokemon("Dummy", "Dummy", 1, 1);
         }
      } //Close forPlayer method
      
      
         /**
         * Generates a Pokemon object for the opponent.
         * @param choice the choice of Pokemon, either the species or a random integer 0-8.
         * @return a Pokemon object.
         */ 
      private static Pokemon forOpp(String choice) {
         switch(choice) {
            case "Bulbasaur":
            case "0":
               return new Pokemon("Bulbasaur", "Benedict", 80, 420);
            case "Venusaur":
            case "1":
               return new Pokemon("Venusaur", "Vladimir", 95, 405);
            case "Ivysaur":
            case "2":
               return new Pokemon("Ivysaur", "Ivan", 110, 390);
            case "Squirtle":
            case "3":
               return new Pokemon("Squirtle", "Sephiroth", 125, 375);
            case "Wartortle":
            case "4":
               return new Pokemon("Wartortle", "Wolfgang", 140, 360);
            case "Blastoise":
            case "5":
               return new Pokemon("Blastoise", "Bruce", 155, 345);
            case "Charmander":
            case "6":
               return new Pokemon("Charmander", "Charles", 170, 330);
            case "Charmeleon":
            case "7":
               return new Pokemon("Charmeleon", "Chadwick", 185, 315);
            case "Charizard":
            case "8":
               return new Pokemon("Charizard", "Cthulu", 200, 300);
            default:
               return new Pokemon("Dummy", "Dummy", 1, 1);
         }
      } //Close forOpp method
   } //close MakePokemon class
} //Close PokemonPanel class
