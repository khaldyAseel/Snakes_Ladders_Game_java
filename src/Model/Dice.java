package Model;
import java.util.Random;

import javax.swing.*;
import java.util.*;
import Model.SysData;

public class Dice {
	private Random random;
	private String difficulty;
	
	public Dice(String difficulty) {
		this.difficulty=difficulty;
	    this.random = new Random();
	}
	public Dice() {
	    this.random = new Random();
	}
	public int rollforEasy(String difficulty) {
		if (difficulty.equals("Easy"))	{
	        return random.nextInt(6) + 1; 
		}
		return 0;
	}
	
	public int rollForEasy() {
	    int roll = random.nextInt(6) + 1;
	    return roll;
	}
	
	public int DiceForMediumGame() {
		 int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 7, 8, 9};
	        Random rand = new Random();
	        int index = rand.nextInt(numbers.length);
	        int result = numbers[index];
	        return result;
	}
	
	public int DiceForHardGame() {
		 int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 7 , 8, 8 , 9 , 9, 9 , 9};
	        Random rand = new Random();
	        int index = rand.nextInt(numbers.length);
	        int result = numbers[index];
	        return result;
	        
	}


    public int rollForTurn() {
        return random.nextInt(6) + 1; // rolls a number between 1 and 6
    }
    public int roll() {
	    return random.nextInt(11)+1; // Roll a number between 1 and 10
	}
}

