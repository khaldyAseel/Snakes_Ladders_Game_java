package View;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Color;

public class Instructions extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public Instructions() {
        setType(Type.POPUP);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 20, 1570, 900); // Set the bounds of the JFrame
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // Make the frame undecorated (no title bar, no minimize/maximize/close buttons)
        setUndecorated(true);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setForeground(Color.BLACK);
        lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 22));
        lblNewLabel_1.setBounds(1279, 55, 170, 40);
        contentPane.add(lblNewLabel_1);
        lblNewLabel_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new MainScreen().setVisible(true);
                Instructions.this.setVisible(false); //
            }
        });
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setBounds(-30, -70, 1600, 1024);
        lblNewLabel.setIcon(new ImageIcon(Instructions.class.getResource("/images/instruction.jpeg")));
        contentPane.add(lblNewLabel); // Add the label to the content pane
     
    }
}
