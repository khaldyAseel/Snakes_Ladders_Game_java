package Model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Controller.GameController;

public abstract class BoardLevelTemplate  {
	protected int size;
	protected static Square[][] cells;
	protected static Snake[] snakes;
	protected static Ladder[] ladders;
	protected static Square[] questions;
	public abstract void startGame(Square[][] cellsformeduim,Snake[] snakes,Ladder[] ladders,Square[] questionSquares,int number);

	public abstract boolean endGame(int index , int result , String type , JLabel[] playerLabel , int WinValue,Game game,GameController controller);	
	

	/*public abstract void saveGameDetails(Player winner) ;*/
	public BoardLevelTemplate(int size) {
	        this.size = size;  
	    }
	 public final void playGame()
		{
			startGame(null, null, null, null,1);
			endGame(0,0, null, null, 0, null, null);
		}
		
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public  Square[][] getCells() {
		return cells;
	}
	public  void setCells(Square[][] cells) {
		BoardLevelTemplate.cells = cells;
	}
	public  Snake[] getSnakes() {
		return snakes;
	}
	public static void setSnakes(Snake[] snakes) {
		BoardLevelTemplate.snakes = snakes;
	}
	public Ladder[] getLadders() {
		return ladders;
	}
	public void setLadders(Ladder[] ladders) {
		BoardLevelTemplate.ladders = ladders;
	}
	public  Square[] getQuestions() {
		return questions;
	}
	public  void setQuestions(Square[] questions) {
		BoardLevelTemplate.questions = questions;
	}
	
	

}
