package Model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GameDetails  implements GameObserver{
	public String winnerName;
	public String difficulty;
	public String time;
	@Override
	public void updateGameHistory(String name,String difficulty,String time) {
		Gson gson = new Gson();
	    java.lang.reflect.Type gameListType = new TypeToken<ArrayList<GameDetails>>(){}.getType();
	    List<GameDetails> gameList;
	    File gameHistory = new File("src/game_history.json");
	    if (!gameHistory.exists()) {
	        try {
				gameHistory.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // This will throw IOException if the file cannot be created
	    }

	    // Load existing game details
	    try (FileReader reader = new FileReader("src/game_history.json")) {
	        gameList = gson.fromJson(reader, gameListType);
	        if (gameList == null) {
	            gameList = new ArrayList<>();
	        }
	    } catch (IOException e) {
	        gameList = new ArrayList<>();
	    }

	    // Add new game details
	    GameDetails details = new GameDetails();
	    details.winnerName = name;
	    details.difficulty = difficulty;
	    details.time = time;
	    gameList.add(details);

	    // Save updated game details
	    try (FileWriter writer = new FileWriter("src/game_history.json")) {
	        gson.toJson(gameList, writer);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public String getWinnerName() {
		return winnerName;
	}
	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "GameDetails [winnerName=" + winnerName + ", difficulty=" + difficulty + ", time=" + time + "]";
	}
	
    

}
