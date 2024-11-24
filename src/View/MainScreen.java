package View;

import Controller.GameController;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Model.Sound;
public class MainScreen extends JFrame{

    private MainScreen frame ;
    public JButton btnNewButton_1;
    private GameController gameController = new GameController(null);

    public MainScreen() {
    	frame=this;
 		Sound sound = new Sound("sounds/start.wav");
 	//	Sound sound = new Sound("src/sounds/start.wav");
 		sound.play();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 20, 1525, 900);
        getContentPane().setLayout(null);
        
        // Make the frame undecorated (no title bar, no minimize/maximize/close buttons)
        setUndecorated(true);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setBounds(1421, 21, 74, 77);
        getContentPane().add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setBounds(601, 325, 258, 69);
        getContentPane().add(lblNewLabel_2);
        lblNewLabel_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	MainScreen.this.setVisible(false);
				new DataReception().setVisible(true);
				gameController.buttonClick();
            }
        });
        
        
        lblNewLabel_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Instructions instructionsFrame = new Instructions();
                instructionsFrame.setVisible(true);
                MainScreen.this.setVisible(false); //
				gameController.buttonClick();

            }
        });
       
        JLabel lblNewLabel_2_1 = new JLabel("");
        lblNewLabel_2_1.setBounds(601, 505, 258, 69);
        getContentPane().add(lblNewLabel_2_1);
        lblNewLabel_2_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showQuestionDialog();
				gameController.buttonClick();

            }
        });
        
        
        
        JLabel lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setBounds(601, 696, 271, 54);
        getContentPane().add(lblNewLabel_3);
        lblNewLabel_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	MainScreen.this.setVisible(false);
                Game_History gameHistoryScreen = new Game_History();
                gameHistoryScreen.setVisible(true); 
				gameController.buttonClick();

            }
        });
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(MainScreen.class.getResource("/images/MainScreen.jpeg")));
        lblNewLabel.setBounds(-53,-93, 1600, 1104);
        getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_4 = new JLabel("");
        lblNewLabel_4.setBounds(1263, 11, 105, 87);
        getContentPane().add(lblNewLabel_4);
        lblNewLabel_4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	MainScreen.this.setVisible(false); 
				gameController.buttonClick();
            }
        });
          
    }
 


    private void showQuestionDialog() {
        boolean isAdmin = true;

        if (isAdmin) {
            int choice = JOptionPane.showConfirmDialog(null, "To enter the Mangment Question you have to be an admin with a LogIn",
                    "Confirmation", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                // Open the admin login screen
                LogIn logIn = new LogIn();
                logIn.showLoginScreen();
                this.setVisible(false);
            }
        } else {
            JOptionPane.showMessageDialog(null, "You do not have permission to access this feature.");
        }
    }
}
