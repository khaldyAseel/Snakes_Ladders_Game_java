package View;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.GameController;

import java.awt.Color;
import java.awt.Font;


public class DataReception extends JFrame {

    private static final long serialVersionUID = 1L;
    public JPanel contentPane;
    private  JButton btnNewButton;
    private int numberOfPlayers;
    private String difficultyLevel;
    private boolean difficultySelected = false;
    private boolean playersSelected = false;
    private  JButton easyButton;
    private  JButton mediumButton;
    private  JButton hardButton;
    private  JButton player2Button;
    private  JButton player3Button;
    private  JButton player4Button;
    private GameController gameController = new GameController(null);


    public DataReception() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 20, 1500, 900);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, -17, 1500, 917);
        contentPane.add(panel);
        panel.setLayout(null);
        
        // Make the frame undecorated (no title bar, no minimize/maximize/close buttons)
        setUndecorated(true);
        
        easyButton = new JButton("Easy");
        easyButton.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
        easyButton.setBackground(new Color(218, 165, 32));
        easyButton.setBounds(262, 392, 234, 68);
        easyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                difficultyLevel = "Easy";
                difficultySelected = true;
                gameController.buttonClick();
                disableOtherDifficultyButtons();
            }
        });
        panel.add(easyButton);

        mediumButton = new JButton("Medium");
        mediumButton.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
        mediumButton.setBackground(new Color(218, 165, 32));
        mediumButton.setBounds(717, 392, 234, 68);
        panel.add(mediumButton);
        mediumButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                difficultyLevel = "Medium";
                difficultySelected = true;
                gameController.buttonClick();
                disableOtherDifficultyButtons();
            }
        });

        hardButton = new JButton("Hard");
        hardButton.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
        hardButton.setBackground(new Color(218, 165, 32));
        hardButton.setBounds(1145, 392, 229, 68);
        panel.add(hardButton);
        hardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                difficultyLevel = "Hard";
                difficultySelected = true;
                gameController.buttonClick();
                disableOtherDifficultyButtons();
            }
        });

        player2Button = new JButton("2 players");
        player2Button.setBackground(new Color(218, 165, 32));
        player2Button.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
        player2Button.setBounds(262, 624, 234, 53);
        player2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numberOfPlayers = 2;
                playersSelected = true;
                gameController.buttonClick();
                disableOtherPlayerButtons();
            }
        });
        player3Button = new JButton("3 players");
        player3Button.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
        player3Button.setBackground(new Color(218, 165, 32));
        player3Button.setBounds(717, 624, 234, 49);
        player3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numberOfPlayers = 3;
                playersSelected = true;
                gameController.buttonClick();
                disableOtherPlayerButtons();
            }
        });
        player4Button = new JButton("4 players");
        player4Button.setBackground(new Color(218, 165, 32));
        player4Button.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
        player4Button.setBounds(1145, 624, 229, 53);
        player4Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numberOfPlayers = 4;
                playersSelected = true;
                gameController.buttonClick();
                disableOtherPlayerButtons();
            }
        });

        panel.add(player2Button);
        panel.add(player3Button);
        panel.add(player4Button);
        
         btnNewButton = new JButton("");
         btnNewButton.setIcon(new ImageIcon(DataReception.class.getResource("/images/BackButton.png")));
         btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
         btnNewButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
            	 DataReception.this.setVisible(false);
 				new MainScreen().setVisible(true);
                gameController.buttonClick();
             }
         });
        btnNewButton.setBounds(20, 818, 177, 66);
        panel.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("");
        btnNewButton_1.setIcon(new ImageIcon(DataReception.class.getResource("/images/NextButton.png")));
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                gameController.buttonClick();
                // Set the visibility of the new screen
        		if (difficultySelected && playersSelected) {
        			if(numberOfPlayers == 2 ) {
        				 new PlayerNicknames2(numberOfPlayers, difficultyLevel).setVisible(true);
                         DataReception.this.setVisible(false);
        			}
        			if(numberOfPlayers == 3 ) {
        				 new PlayerNicknames3(numberOfPlayers, difficultyLevel).setVisible(true);
                         DataReception.this.setVisible(false);
        			}
        			if(numberOfPlayers == 4) {
        				 new PlayersNicknames4(numberOfPlayers, difficultyLevel).setVisible(true);
                         DataReception.this.setVisible(false);
        			}
                  
                } else {
                    // Display an error message or handle the case where both are not selected
                }
        	}
        });
        btnNewButton_1.setBounds(1300, 805, 177, 66);
        panel.add(btnNewButton_1);
        
         JLabel lblNewLabel = new JLabel("");
         lblNewLabel.setBounds(-68, -50, 1568, 1039);
         panel.add(lblNewLabel);
         lblNewLabel.setIcon(new ImageIcon(DataReception.class.getResource("/images/datareception.jpeg")));
    }
         
 // Disable other difficulty buttons when one is selected
    private void disableOtherDifficultyButtons() {
        if (this.difficultyLevel.equals("Easy")) {
            this.mediumButton.setEnabled(false);
            this.hardButton.setEnabled(false);
        } else if (difficultyLevel.equals("Medium")) {
            this.easyButton.setEnabled(false);
            this.hardButton.setEnabled(false);
        } else if (difficultyLevel.equals("Hard")) {
            this.easyButton.setEnabled(false);
            this.mediumButton.setEnabled(false);
        }
    }

    // Disable other player buttons when one is selected
    private void disableOtherPlayerButtons() {
        if (numberOfPlayers == 2) {
            this.player3Button.setEnabled(false);
            this.player4Button.setEnabled(false);
        } else if (numberOfPlayers == 3) {
            this.player2Button.setEnabled(false);
            this.player4Button.setEnabled(false);
        } else if (numberOfPlayers == 4) {
            this.player2Button.setEnabled(false);
            this.player3Button.setEnabled(false);
        }
    }
}
