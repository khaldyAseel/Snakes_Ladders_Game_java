package View;

import java.awt.Component;

import java.awt.EventQueue;

import java.awt.SystemColor;
import java.awt.TextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;

import Controller.GameController;
import Controller.PreGameController;
import Model.Color;
import Model.Dice;
import Model.Player;
import Model.Sound;
import Model.Square;

import java.awt.Font;

public class PlayerTurn extends JFrame {

    private Dice dice;
    private Map<Player, Integer> playerRolls;
    private List<Player> players;
    public static HashMap<Player, Square> lastSquareVisit = new HashMap<>();
    private int currentPlayerIndex;
    private String difficultyLevel;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel rollLabel;
    private GameController gameController = new GameController(null);
    

    public PlayerTurn(int numberOfPlayers, String difficultyLevel, String[] namesOfPlayers , Color[] color) {
        this.difficultyLevel = difficultyLevel;
        dice = new Dice();
        lastSquareVisit = new HashMap<>();
        playerRolls = new LinkedHashMap<>();
        players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player(namesOfPlayers[i],color[i]));
        }
        currentPlayerIndex = 0;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 20, 1500, 900);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        // Make the frame undecorated (no title bar, no minimize/maximize/close buttons)
        setUndecorated(true);

     
        JButton diceButton = new JButton("");
        diceButton.setHorizontalAlignment(SwingConstants.LEADING);
        diceButton.setIcon(new ImageIcon(PlayerTurn.class.getResource("/images/dice 4.jpg")));
        JTextPane txtpnHi = new JTextPane();
        txtpnHi.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 33));
        txtpnHi.setForeground(new java.awt.Color(0, 0, 0));
        txtpnHi.setBackground(new java.awt.Color(255, 250, 250));
        
        txtpnHi.setBounds(69, 306, 315, 243);
        contentPane.add(txtpnHi);
        JTextArea txtrPlayer = new JTextArea();
        txtrPlayer.setForeground(new java.awt.Color(0, 0, 0));
        txtrPlayer.setBackground(new java.awt.Color(0, 100, 0));
        txtrPlayer.setBounds(71, 59, 313, 93);
        contentPane.add(txtrPlayer);
        txtrPlayer.setText("\r\n Turn : <dynamic>");
        txtrPlayer.setFont(new Font("David", Font.BOLD | Font.ITALIC, 30));
        txtrPlayer.setTabSize(20);
        txtrPlayer.setAlignmentX(0.2f);
        txtrPlayer.setAlignmentY(Component.TOP_ALIGNMENT);
        txtrPlayer.setBackground(new java.awt.Color(120, 180, 20)); // Green
        rollLabel = new JLabel(players.get(currentPlayerIndex).getName()+" Roll the dice!");
        rollLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 25));
        rollLabel.setBounds(449, 458, 400, 30);
        contentPane.add(rollLabel);
        displayRollLabel();

        diceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
            	DiceRollingSound();
                rollLabel.setText("");
                int rollResult = dice.rollForTurn();
                String path = "/images/dice " + rollResult + ".jpg";
                diceButton.setIcon(new ImageIcon(PlayerTurn.class.getResource(path)));
                Player currentPlayer = players.get(currentPlayerIndex);
                playerRolls.put(currentPlayer, rollResult);

                txtpnHi.setText("");
                displayRollsInTextPane(txtpnHi, playerRolls);
                txtpnHi.setFont(new Font("Yu Gothic Light", Font.BOLD | Font.ITALIC, 14));
                currentPlayerIndex++;

                if (currentPlayerIndex >= players.size()) {
                    diceButton.setEnabled(false); 
                    PreGameController controller = new PreGameController(dice, playerRolls, players, difficultyLevel);

                    controller.sortPlayers();

                    if (controller.checkForTies()) {
                        List<Player> tiedPlayers = controller.getTiedPlayers();
                        StringBuilder tieMessage = new StringBuilder("There's a tie between: ");
                        for (Player player : tiedPlayers) {
                            tieMessage.append(player.getName()).append(", ");
                        }
                        tieMessage.setLength(tieMessage.length() - 2);
                        tieMessage.append(" So the players ");
                        for (Player player : tiedPlayers) {
                            tieMessage.append(player.getName()).append(", ");
                        }
                        tieMessage.setLength(tieMessage.length() - 2); 
                        tieMessage.append(" will be plays according to apha beta of thier nacknames:))) ");
                        JOptionPane.showMessageDialog(PlayerTurn.this, tieMessage.toString(), "Tie Detected", JOptionPane.INFORMATION_MESSAGE);

                    } 
                        currentPlayerIndex = 0;
                        ResultPage(players.size(), players);
   
                } else {
                	rollLabel.setText(players.get(currentPlayerIndex).getName() + " Roll the dice!");
                    txtrPlayer.setText("\n    Turn : " + players.get(currentPlayerIndex).getName());
                    setPlayerBackgroundColor(color[currentPlayerIndex], txtrPlayer);
                    displayRollLabel();
                }
            }
        });
        diceButton.setBackground(SystemColor.controlLtHighlight);
        diceButton.setForeground(java.awt.Color.WHITE);
        diceButton.setBounds(1147, 517, 120, 100);
        contentPane.add(diceButton);
        
        JButton btnNewButton = new JButton("");
        btnNewButton.setIcon(new ImageIcon(PlayerTurn.class.getResource("/images/BackButton.png")));
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
        btnNewButton.setBounds(39, 823, 177, 66);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            PlayerTurn.this.setVisible(false);
				new DataReception().setVisible(true);
				gameController.buttonClick();
            }
        });
        
        contentPane.add(btnNewButton);
        
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBackground(new java.awt.Color(0, 100, 0));
        lblNewLabel.setIcon(new ImageIcon(PlayerTurn.class.getResource("/images/playerTurn.jpeg")));
        lblNewLabel.setBounds(-11, -12, 1511, 949);
        contentPane.add(lblNewLabel);
        contentPane.setVisible(true);
    }
        
   
        private void displayRollsInTextPane(JTextPane textPane, Map<Player, Integer> rolls) {//display the names of the player and what he got by rolling
            StyledDocument doc = textPane.getStyledDocument();
            
            for (Map.Entry<Player, Integer> entry : rolls.entrySet()) {
                Player player = entry.getKey();
                int rollResult = entry.getValue();

                String message = player.getName() + " --- " + rollResult + "\n";
                AttributeSet attributeSet = null; 

                try {
                    doc.insertString(doc.getLength(), message, attributeSet);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
        private void setPlayerBackgroundColor(Color color , JTextArea txtrPlayer) {//change the jtext background - by the player color
                switch (color.toString()) {
                case "BLUE":
                    txtrPlayer.setBackground(new java.awt.Color(0, 200, 220)); // Blue
                    break;
                case "GREEN": 
                    txtrPlayer.setBackground(new java.awt.Color(0, 120, 30)); // Green
                    break;
                case "RED":
                    txtrPlayer.setBackground(new java.awt.Color(255, 102, 102)); // Red
                    break;
                case "YELLOW":
                    txtrPlayer.setBackground(new java.awt.Color(255, 255, 153)); // Yellow
                    break;
                default:
                    // Default color for other players
                    txtrPlayer.setBackground(new java.awt.Color(192, 192, 192));
                    break;
                } }
        private void displayRollLabel() {
            rollLabel.setVisible(true);
        }
    
        
        private void ResultPage(int numPlayer, List<Player> playersSortedByOrder) {
            Timer timer = new Timer(1000, new ActionListener() { // Corrected to wait 5 seconds
                public void actionPerformed(ActionEvent e) {
                    if (numPlayer == 2) {
                        new BounusResults2(difficultyLevel,playersSortedByOrder).setVisible(true);
                    } else if (numPlayer == 3) {
                        new BounusResults3(difficultyLevel,playersSortedByOrder).setVisible(true);
                    } else if (numPlayer == 4) {
                        new BounusResults4(difficultyLevel,playersSortedByOrder).setVisible(true);
                    } 

                    PlayerTurn.this.setVisible(false); // Moved outside to ensure it executes in all cases
                }
           
        });

            timer.setRepeats(false);
            timer.start();///BJDGAS
        }
        public void DiceRollingSound() {
 //       Sound sound = new Sound("src/sounds/dice.wav");
        Sound sound = new Sound("sounds/dice.wav");
     		sound.setVolume(0.5f); 
             sound.play();
        }

     
}