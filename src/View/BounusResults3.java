package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.GameController;
import Model.Game;
import Model.Player;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;

public class BounusResults3 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private GameController gameController = new GameController(null);


	public BounusResults3( String difficultyLevel,List<Player>  playersSortedByOrder) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 20, 1491, 889);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		 // Make the frame undecorated (no title bar, no minimize/maximize/close buttons)
        setUndecorated(true);
		
		JLabel lblNewLabel_1 = new JLabel("The Order of the Players is:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 40));
		lblNewLabel_1.setForeground(new Color(51, 102, 0));
		lblNewLabel_1.setBounds(555, 27, 500, 47);
		contentPane.add(lblNewLabel_1);
		
		JLabel Player3 = new JLabel(" "+playersSortedByOrder.get(2).getName());
		Player3.setForeground(new Color(0, 0, 0));
		Player3.setFont(new Font("Tahoma", Font.BOLD, 42));
		Player3.setBounds(445, 653, 274, 72);
		contentPane.add(Player3);
		
		JLabel Player2 = new JLabel(" "+playersSortedByOrder.get(1).getName());
		Player2.setForeground(new Color(0, 0, 0));
		Player2.setFont(new Font("Tahoma", Font.BOLD, 42));
		Player2.setBounds(445, 451, 500, 72);
		contentPane.add(Player2);
		
		JLabel Player1 = new JLabel(" " + playersSortedByOrder.get(0).getName());
		Player1.setForeground(new Color(0, 0, 0));
		Player1.setBounds(456, 235, 500, 72);
		contentPane.add(Player1);
		Player1.setFont(new Font("Tahoma", Font.BOLD, 42));
       
		JButton btnNewButton_1 = new JButton("");
		 btnNewButton_1.setIcon(new ImageIcon(BounusResults2.class.getResource("/images/NextButton.png")));
	        btnNewButton_1.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	gameController.buttonClick();
                Game game = new Game(difficultyLevel, playersSortedByOrder);

            	   if(difficultyLevel.equals("Easy")) {   
                       new BoardEasyViewPlayers(game).setVisible(true);
                       BounusResults3.this.setVisible(false); 
                       }
                       if(difficultyLevel.equals("Medium")) {
                           new MediumGameBoard(game).setVisible(true);
                           BounusResults3.this.setVisible(false); 
                           }
                       if(difficultyLevel.equals("Hard")) {
                           new HardGameBoard(game).setVisible(true);
                           BounusResults3.this.setVisible(false); 
                           }
            }
        });
        btnNewButton_1.setBounds(1277, 801, 177, 66);
        contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(BounusResults3.class.getResource("/images/result3players.png")));
		lblNewLabel.setBounds(-20, -40, 1551, 969);
		contentPane.add(lblNewLabel);
	}
}
