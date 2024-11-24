package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ExitConfirmationDialog extends JDialog {
    private boolean confirmed = false;

    public ExitConfirmationDialog(Frame parent) {
        super(parent, "Confirmation", true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add some padding
        
        JPanel textPanel = new JPanel(); // Use a JPanel for the text panel
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); // Use BoxLayout to stack components vertically
        
        JLabel label = new JLabel("<html>Are you sure you want to exit to the main screen?</html>");
        label.setFont(new Font("Arial", Font.BOLD, 18)); // Change the font to bold and size 18
        label.setForeground(new Color(10, 79, 40)); // Change the text color
        textPanel.add(label);
        
        JLabel label_1 = new JLabel("<html>If you exit, the game won't be saved in the history!</html>");
        label_1.setFont(new Font("Arial", Font.BOLD, 18)); // Change the font to bold and size 18
        label_1.setForeground(new Color(79, 10, 47)); // Change the text color
        textPanel.add(label_1);

        JLabel imageLabel = new JLabel("<html><img src='" + getClass().getResource("/images/exit_pop.jpg") + "' width='150' height='150'></body></html>");
        textPanel.add(imageLabel);       
        panel.add(textPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // Use GridLayout for buttons with some spacing
        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmed = true;
                dispose();
            }
        });
        buttonPanel.add(yesButton);
        JButton noButton = new JButton("No");
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                dispose();
            }
        });
        buttonPanel.add(noButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);
        setSize(450, 400); // Set the size of the dialog
        setLocationRelativeTo(parent);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Enable the X button
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
