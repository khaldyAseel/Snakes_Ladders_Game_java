package View;



import java.awt.Component;

import java.awt.LayoutManager;

import java.awt.event.ActionListener;


import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;



import Model.Game;
import Model.Player;
import Model.Questions;

public class QuestionpopUP extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
  
    private ButtonGroup optionsGroup;
    private JRadioButton answer1RButton, answer2RButton, answer3RButton, answer4RButton;
    private JButton submitButton;
    private JLabel questionLabel;
    private int selectedAnswer = -1;
    private Questions questionpopUP;
    private final int totalSquaresOnBoard = 7 * 7; // for a 7x7 board, this would be 49
    private  Player currentPlayer;
    private Game game;
    
    public QuestionpopUP(Questions questionpopUP , Player player, Game game) {
    	this.currentPlayer=player;
    	this.game=game;
    	this.questionpopUP=questionpopUP;
        setTitle("Question");
        setSize(510, 485);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 700);
        contentPane = new JPanel();
        contentPane.setLayout((LayoutManager) new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        setContentPane(contentPane);

        // Question label
        questionLabel = new JLabel(this.questionpopUP.getQuestionText());
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(questionLabel);

        // Radio buttons for options
        optionsGroup = new ButtonGroup();
        answer1RButton = new JRadioButton(this.questionpopUP.getOptions()[0]);
        answer2RButton = new JRadioButton(this.questionpopUP.getOptions()[1]);
        answer3RButton = new JRadioButton(this.questionpopUP.getOptions()[2]);
        answer4RButton = new JRadioButton(this.questionpopUP.getOptions()[3]);
        optionsGroup.add(answer1RButton);
        optionsGroup.add(answer2RButton);
        optionsGroup.add(answer3RButton);
        optionsGroup.add(answer4RButton);

        // Adding radio buttons to the content pane
        contentPane.add(answer1RButton);
        contentPane.add(answer2RButton);
        contentPane.add(answer3RButton);
        contentPane.add(answer4RButton);

        // Submit button
        submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// TODO Auto-generated method stub
				// Handle submit action
                if (answer1RButton.isSelected()) selectedAnswer = 1;
                if (answer2RButton.isSelected()) selectedAnswer = 2;
                if (answer3RButton.isSelected()) selectedAnswer = 3;
                if (answer4RButton.isSelected()) selectedAnswer = 4;
                
                // Close the dialog or handle the selected answer
                handleAnswer(selectedAnswer);
				
			}
        	
        });
   
        
        		
        contentPane.add(submitButton);
     // Initialize question and answers
        // Display the window.
        pack();
        setVisible(true);
    }


    private void handleAnswer(int selectedAnswer ) {
    	boolean isCorrectAnswer=true;
        if (answer1RButton.isSelected()) selectedAnswer = 1;
        if (answer2RButton.isSelected()) selectedAnswer = 2;
        if (answer3RButton.isSelected()) selectedAnswer = 3;
        if (answer4RButton.isSelected()) selectedAnswer = 4;
        
        if(selectedAnswer == this.questionpopUP.getCorrectOption()) {
            JOptionPane.showMessageDialog(this, "Correct Answer!");
            if(this.questionpopUP.getDiffculty()==1)
            {
                movePlayerBasedOnQuestion(1 ,isCorrectAnswer);

            }
            if(this.questionpopUP.getDiffculty()==2)
            {
                movePlayerBasedOnQuestion(2 ,isCorrectAnswer);

            }
            if(this.questionpopUP.getDiffculty()==3)
            {
                movePlayerBasedOnQuestion(3 ,isCorrectAnswer);

            }
        } else {
            JOptionPane.showMessageDialog(this, "Wrong Answer!");
            isCorrectAnswer=false;
            if(this.questionpopUP.getDiffculty()==1)
            {
                movePlayerBasedOnQuestion(1 ,isCorrectAnswer);

            }
            if(this.questionpopUP.getDiffculty()==2)
            {
                movePlayerBasedOnQuestion(2 ,isCorrectAnswer);

            }
            if(this.questionpopUP.getDiffculty()==3)
            {
                movePlayerBasedOnQuestion(3 ,isCorrectAnswer);

            }
        }

        dispose();
        
        
    }
    /**
     * Moves the player based on the result of the question.
     */
    public void movePlayerBasedOnQuestion(int questionDifficulty, boolean isCorrectAnswer) {
    	 System.out.println("heeeeeeeeeeeello  " + questionDifficulty + isCorrectAnswer);
        // Define the movement rules based on difficulty and correctness
    	
        int steps;
        if (isCorrectAnswer) {
            if (questionDifficulty == 3) { // Hard question
                steps = 1; // Advance one step
            } else {
                steps = 0; // Stay in place for easy and medium questions
            }
        } else {
            // For wrong answers, move back 1, 2, or 3 steps based on difficulty
            steps = -questionDifficulty;
        }

        // Apply the movement to the player's position
        updatePlayerPosition(steps);
    }

    /**
     * Update the player's position on the board.
     */
    private void updatePlayerPosition(int steps) {
        // Assume we have a currentPlayer object with a method setPosition
        int currentPosition = this.currentPlayer.getPosition();
        int newPosition = currentPosition + steps;

        // Ensure the new position is within bounds
        if (newPosition < 0) {
            newPosition = 0; // Prevent moving beyond the start
          	 System.out.println("Prevent moving beyond the start ");

        } else if (newPosition > totalSquaresOnBoard) {
            newPosition = totalSquaresOnBoard; // Prevent moving beyond the end
          	 System.out.println("Prevent moving beyond the end  " );

        }

        this.currentPlayer.setPosition(newPosition);
      	 System.out.println("ameeeeeeeeeeera  " + newPosition);
       QuestionpopUP.this.setVisible(false);
    }



}
