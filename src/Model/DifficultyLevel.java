package Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifficultyLevel  implements ActionListener  {
	//ActionListener for difficulty buttons
	 private final String difficulty;

	 public DifficultyLevel(String difficulty) {
	     this.difficulty = difficulty;
	 }

	 public void actionPerformed(ActionEvent e) {
	     // Handle button click (you can add your logic here)
	     System.out.println("Selected difficulty: " + difficulty);
	 }
	}


