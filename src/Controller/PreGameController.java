package Controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Model.Dice;
import Model.Player;

public class PreGameController {
	  private Dice dice;
	    private Map<Player, Integer> playerRolls;
	    public List<Player> players;
	    private String difficultyLevel;
	    
	 public PreGameController(Dice dice, Map<Player, Integer> playerRolls, List<Player> players,
				String difficultyLevel) {
			super();
			this.dice = dice;
			this.playerRolls = playerRolls;
			this.players = players;
			this.difficultyLevel = difficultyLevel;
		}
	 
	public Dice getDice() {
		return dice;
	}


	public void setDice(Dice dice) {
		this.dice = dice;
	}


	public Map<Player, Integer> getPlayerRolls() {
		return playerRolls;
	}


	public void setPlayerRolls(Map<Player, Integer> playerRolls) {
		this.playerRolls = playerRolls;
	}


	public List<Player> getPlayers() {
		return players;
	}


	public void setPlayers(List<Player> players) {
		this.players = players;
	}


	public String getDifficultyLevel() {
		return difficultyLevel;
	}


	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}


	public StringBuilder displayTurnOrder() {
	        players.sort(Comparator.comparing(playerRolls::get).reversed());

	        StringBuilder turnOrderMessage = new StringBuilder("Turn order:\n");
	        for (int i = 0; i < players.size(); i++) {
	            turnOrderMessage.append(i + 1).append(". ").append(players.get(i).getName()).append("\n");
	        }	 
	        return turnOrderMessage;
	        }

	    public void startNewGame() {
	        Queue<Player> sortedPlayers = new ArrayDeque<>(players);
	      /*  Game newGame = new Game(difficultyLevel, sortedPlayers, dice);*/
	        // newGame.startGame(); 
	    }
	 // Method to check for ties in player rolls
	    public boolean checkForTies() {
	        Set<Integer> uniqueRolls = new HashSet<>(playerRolls.values());
	        return uniqueRolls.size() < playerRolls.size(); // If there are fewer unique rolls than players, there's a tie
	    }

	    // Method to get the list of players who have tied
	    public List<Player> getTiedPlayers() {
	        Map<Integer, List<Player>> rollToPlayers = new HashMap<>();
	        for (Map.Entry<Player, Integer> entry : playerRolls.entrySet()) {
	            rollToPlayers.computeIfAbsent(entry.getValue(), k -> new ArrayList<>()).add(entry.getKey());
	        }

	        List<Player> tiedPlayers = new ArrayList<>();
	        for (List<Player> playerList : rollToPlayers.values()) {
	            if (playerList.size() > 1) {
	                tiedPlayers.addAll(playerList);
	            }
	        }
	        return tiedPlayers;
	    }
	    public void sortPlayers() {
	        players.sort(Comparator.comparing((Player p) -> playerRolls.get(p)).reversed()
	                               .thenComparing(Player::getName));
	    }
	  
}