package Model;

import java.util.ArrayList;
import java.util.List;




public class Game implements GameSubject {
    private List<GameObserver> observers = new ArrayList<>();
	private static Game instance = null;
    private List<Player> players;
    private Player currentPlayer;
    private BoardLevelTemplate board;
    private String difficulty;
    private Dice dice;
    public int OnwhichQuestionland = 0;
    private int currentPlayerIndex = 0; 
//  Singleton Instance
	public static Game getInstance(List<Player> players,String difficulty) {
		if (instance == null) {
			instance = new Game(difficulty,players);
		}
		return instance;
	}
    
    public Game( String difficulty,List<Player> players) {
        this.players = players;
        this.currentPlayer = players.get(0); 
        this.difficulty=difficulty;
        int boardSize = difficulty.equals("Easy") ? 7 : difficulty.equals("Medium") ? 10 : 13;
        if (boardSize==7)
        {
          this.board = new EasyBoard();

        }
        if (boardSize==10)
        {
          this.board = new MediumBoard();

        }if (boardSize==13)
        {
            this.board = new HardBoard();

          }
        this.dice = new Dice(difficulty);
        
    }
    public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
	   // players.set(currentPlayerIndex, currentPlayer);
		this.players = players;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		
		this.currentPlayer = currentPlayer;
	}
	

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public BoardLevelTemplate getBoard() {
		return board;
	}

	public void setBoard(BoardLevelTemplate board) {
		this.board = board;
	}

	public Dice getDice() {
		return dice;
	}

	public void setDice(Dice dice) {
		this.dice = dice;
	}

    public static Game getInstance() {
		return instance;
	}

	public static void setInstance(Game instance) {
		Game.instance = instance;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
  
	public void updatePlayerPositionInList(String playerName, int newPosition) {
	    for (Player p : players) {
	        if (p.getName().equals(playerName)) {
	            p.setPosition(newPosition);
	            break;
	        }
	    }
	}
	 @Override
	    public void registerObserver(GameObserver observer) {
	        observers.add(observer);
	    }
	    
	    @Override
	    public void removeObserver(GameObserver observer) {
	        observers.remove(observer);
	    }
	    
	    @Override
	    public void notifyObservers(String name,String difficulty,String time) {
	        for (GameObserver observer : observers) {
	            observer.updateGameHistory(name, difficulty, time);
	        }
	    }
	    
	    // Method to call notifyObservers when a game ends
	    public void endGame(String name,String difficulty,String time) {
	        notifyObservers( name, difficulty, time);
	    }

}



