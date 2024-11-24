package View;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.GameController;
import Model.Game;
import Model.Player;
import Model.WinFrame;

import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class GreenWin extends JFrame  implements WinFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel_2;
	private String plyerNickname;
	private String time;
	private Game game;
    private GameController gameController = new GameController(game);


	public GreenWin(String winnerName, String time, Game game) {
		this.plyerNickname=winnerName;
		this.time=time;
		this.game=game;
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 // Make the frame undecorated (no title bar, no minimize/maximize/close buttons)
        setUndecorated(true);
		setBounds(100, 20, 1074, 936);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		gameController.WinngPage();

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel restart = new JLabel("");
		restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	for(Player player: game.getPlayers())
				{
					player.setPosition(1);
				}
				GreenWin.this.setVisible(false);
				if(game.getDifficulty() == "Easy") {
				new BoardEasyViewPlayers(game).setVisible(true);
				}
				else if(game.getDifficulty().equals("Medium")) {
					new MediumGameBoard(game).setVisible(true);
				}
				else if(game.getDifficulty().equals("Hard")) {
					new HardGameBoard(game).setVisible(true);
				}
				gameController.buttonClick();
			}
        });
		
		JLabel menu = new JLabel("");
		menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	GreenWin.this.setVisible(false);
				new MainScreen().setVisible(true);
				gameController.buttonClick();
			}
        });
		menu.setBounds(796, 832, 200, 80);
		contentPane.add(menu);
		restart.setBounds(102, 817, 200, 80);
		contentPane.add(restart);
		JLabel playerWin = new JLabel(plyerNickname);
		playerWin.setBounds(383, 315, 282, 80);
		playerWin.setForeground(new Color(0, 153, 102));
		playerWin.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 40));
		contentPane.add(playerWin);
		
		JLabel lblNewLabel_3 = new JLabel(time);
		lblNewLabel_3.setBounds(383, 610, 268, 74);
		lblNewLabel_3.setForeground(new Color(0, 153, 102));
		lblNewLabel_3.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 40));
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2 = new JLabel(new ImageIcon(getClass().getResource("/images/winnerGreenscreen.png")));
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setBackground(new Color(255, 255, 255));
		lblNewLabel_2.setBounds(-23, 0, 1116, 977);
		contentPane.add(lblNewLabel_2);
			
	}


	@Override
	public void createWinFrame(String winnerName, String time, Game game) {
		this.plyerNickname=winnerName;
		this.game=game;
		this.time=time;	
		new GreenWin(winnerName,time, game).setVisible(true);

	}
}