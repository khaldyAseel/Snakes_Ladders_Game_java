package View;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import Controller.GameController;
import Controller.MangQuestionControl;
import Model.SysData;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPasswordField;
import java.awt.Font;

public class LogIn extends JFrame{

    private JFrame frame;
    private JTextField txtuser;
    private GameController gameController = new GameController(null);

	public JTextField getTxtuser() {
		return txtuser;
	}

	public void setTxtuser(JTextField txtuser) {
		this.txtuser = txtuser;
	}

	public JButton getBtnNewButton() {
		return btnNewButton;
	}

	public void setBtnNewButton(JButton btnNewButton) {
		this.btnNewButton = btnNewButton;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}
    static  SysData sysData = new SysData();
    static MangQuestionControl mangQuestionControl=new MangQuestionControl();
    public JPasswordField passwordField;
    private JLabel lblNewLabel;
	private JButton btnNewButton;

    public LogIn() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 20, 1400, 844);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        // Make the frame undecorated (no title bar, no minimize/maximize/close buttons)
        frame.setUndecorated(true);

        txtuser = new JTextField();
        txtuser.setBackground(new Color(255, 255, 255));
        txtuser.setBounds(680, 268, 297, 38);
        frame.getContentPane().add(txtuser);
        txtuser.setColumns(10);
        
        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(255, 255, 255));
        passwordField.setBounds(680, 400, 294, 38);
        frame.getContentPane().add(passwordField);
        
        JLabel lblNewLabel_3 = new JLabel(" ");
        lblNewLabel_3.setBackground(new Color(255, 140, 0));
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_3.setIcon(new ImageIcon(LogIn.class.getResource("/images/login.jpeg")));
        lblNewLabel_3.setBounds(-22, -8, 1472, 854);
        frame.getContentPane().add(lblNewLabel_3);
        
        lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(22, 758, 192, 64);
        lblNewLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	frame.dispose(); // Close the current frame
            	new MainScreen().setVisible(true); 
				gameController.buttonClick();
            }
        });
          
        frame.getContentPane().add(lblNewLabel);
        
        JLabel labelsubmit = new JLabel("");
        labelsubmit.setBounds(690, 646, 174, 55);
        labelsubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
            	gameController.buttonClick();
                String enteredUserName = txtuser.getText();
                String enteredPassword = new String(passwordField.getPassword()); // Use getPassword() for JPasswordField
                try {
                    if (validateLogin(enteredUserName, enteredPassword)) {
                        // Login successful
                        JOptionPane.showMessageDialog(null, "Login successful! Redirecting to admin page...");
                        frame.setVisible(false);
                        QuestionManagment questionManagement = new QuestionManagment();
                        questionManagement.frame.setVisible(true); 
                        
                    } else {
                        // Login failed
                        JOptionPane.showMessageDialog(null, "Invalid email or password. Please try again.");
                    }
                } catch(NullPointerException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });
        frame.getContentPane().add(labelsubmit);
        
    }
   
	public boolean validateLogin(String enteredUserName, String enteredPassword) {
        if(enteredUserName == null || enteredPassword == null) {
            throw new NullPointerException("Username or password is null.");
        }
         return mangQuestionControl.validateAdminCredentials(enteredUserName, enteredPassword);
    }


    public void showLoginScreen() {
        frame.setVisible(true);
    }
}
