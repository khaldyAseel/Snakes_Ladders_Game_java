package View;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.GameController;
import Model.Game;
import Model.Player;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import java.awt.Color;

public class BounusResults2 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private GameController gameController = new GameController(null);

	public BounusResults2(String difficultyLevel,List<Player>  playersSortedByOrder) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 20, 1500, 889);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		 // Make the frame undecorated (no title bar, no minimize/maximize/close buttons)
        setUndecorated(true);
		
		JLabel lblNewLabel_1 = new JLabel("The order of players is: ");
		lblNewLabel_1.setForeground(new Color(0, 102, 0));
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 40));
		lblNewLabel_1.setBounds(553, 43, 548, 50);
		contentPane.add(lblNewLabel_1);
		
		JLabel PlayerTurnOne = new JLabel(" "+playersSortedByOrder.get(0).getName());
		PlayerTurnOne.setForeground(new Color(0, 0, 0));
		PlayerTurnOne.setFont(new Font("Sitka Text", Font.BOLD, 42));
		PlayerTurnOne.setBounds(522, 274, 300, 58);
		contentPane.add(PlayerTurnOne);
		
		JLabel PlayerTurnTwo = new JLabel(" "+playersSortedByOrder.get(1).getName());
		PlayerTurnTwo.setForeground(new Color(0, 0, 0));
		PlayerTurnTwo.setFont(new Font("Sitka Text", Font.BOLD, 42));
		PlayerTurnTwo.setBounds(522, 474, 300, 58);
		contentPane.add(PlayerTurnTwo);
        JButton btnNewButton_1 = new JButton("");
        btnNewButton_1.setIcon(new ImageIcon(BounusResults2.class.getResource("/images/NextButton.png")));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	gameController.buttonClick();
                Game game = new Game(difficultyLevel, playersSortedByOrder);
                if(difficultyLevel.equals("Easy")) {
                new BoardEasyViewPlayers(game).setVisible(true);
                BounusResults2.this.setVisible(false); 
                }
                if(difficultyLevel.equals("Medium")) {
                    new MediumGameBoard(game).setVisible(true);
                    BounusResults2.this.setVisible(false); 
                    }
                if(difficultyLevel.equals("Hard")) {
                    new HardGameBoard(game).setVisible(true);
                    BounusResults2.this.setVisible(false); 
                    }
            }
        });


        btnNewButton_1.setBounds(1258, 790, 177, 66);
        contentPane.add(btnNewButton_1);
		 
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(BounusResults2.class.getResource("/images/result2players.png")));
		lblNewLabel.setBounds(0, -98, 1550, 1084);
		contentPane.add(lblNewLabel);
		 

	}
}