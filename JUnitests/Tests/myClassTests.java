package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Controller.MangQuestionControl;
import Model.Color;
import Model.Game;
import Model.Player;
import Model.Questions;
import Model.SysData;
import View.BoardEasyViewPlayers;
import View.DataReception;
import View.GreenWin;
import View.LogIn;
import View.MainScreen;
import View.PlayerNicknames2;
import View.QuestionManagment;
import javafx.scene.input.MouseEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class myClassTests {

	/*In the tests, there are some tests that their result from calling some functions /test functions can
	 * open popups or  windows ,when run the test u can press okay/enter on each frame that open or x
	 */

	/*ID=WB1 tests if the easy game correctly initializes the board size and sets players's first positions at 1*/
	/*test for board class*/
	@Test
	public void testEasyDifficultyInitialization() {
		Player player1 = new Player("TestPlayer1", Color.GREEN);
		Player player2 = new Player("TestPlayer", Color.BLUE);	   
		Game game = new Game("Easy", new ArrayList<>(Arrays.asList(player1,player2)));
		assertEquals( 7, game.getBoard().getSize());

		game.getPlayers().forEach(player ->
		assertEquals( 1, player.getPosition())
				);
	}
	
	/*ID=WB2 tests the dice roll correctly updates the player's position on the board, 
	 * considering the board's size and avoiding overshooting the last square in easy game.
	 */
	/*test movePlayer function in BoardEasyPlyers if it handles overshooting the last square*/
	@Test
    public void testDiceRollMovement() {
		Player player1 = new Player("TestPlayer1", Color.GREEN);
		Player player2 = new Player("TestPlayer", Color.BLUE);	   
		Game game = new Game("Easy", new ArrayList<>(Arrays.asList(player1,player2)));
		BoardEasyViewPlayers board2= new BoardEasyViewPlayers(game);
		player1.setPosition(48);

        int diceRoll = 3;
        board2.movePlayer(player1, diceRoll);
        
        
        // Expected Output: Player moves to square 49, not beyond it
        int expectedPosition = 49; // Last square on the Easy game board
        assertEquals( expectedPosition, player1.getPosition());
    }
	
	/*ID=WB3,tests that the method returns true for valid username and password.*/
	/*test for login class*/
	 @Test
	  public void testValidateLoginSuccess() {
		     LogIn logIn = new LogIn();
	        assertTrue(logIn.validateLogin("admin1", "123"));
	    }
	 
	 /*ID=WB4 tests if a NullPointerException to be thrown for null inputs.*/
	 /*test for login class*/
	    public void testValidateLoginNullInput() {
		     LogIn logIn = new LogIn();
	        assertThrows(NullPointerException.class, () -> {
	            logIn.validateLogin(null, null);
	        });
	    }
	 
	 /*ID=WB5 tests that the method returns false for invalid username and password.*/
	    /*test for login class*/
	    @Test
	    public void testValidateLoginFailure() {
		     LogIn logIn = new LogIn();
	        assertFalse(logIn.validateLogin("wrongusername", "wrongPassword"));
	    }
		 
		 /*ID=WB6 tests that a player moves correctly on the board based on the dice roll outcome.
		  when the result square not ladder or snake or question square*/
	    /*test for movePlayer function in BoardEasyPlyers when roll dice*/
			@Test
		    public void testPlayerMovementOnBoard() {
				Player player1 = new Player("TestPlayer1", Color.GREEN);
				Player player2 = new Player("TestPlayer", Color.BLUE);	   
				Game game = new Game("Easy", new ArrayList<>(Arrays.asList(player1,player2)));
				BoardEasyViewPlayers board2= new BoardEasyViewPlayers(game);
		        // Simulate dice roll outcome as 4
		        int diceOutcome = 6;

		        board2.movePlayer(player1, diceOutcome);

		        assertEquals(7, player1.getPosition());//in all the random easy board there is no ladder or snake or question in square 7 so no need to chick this
		    }

	/*ID:BB1 tests if the addNewQuestion method in the SysData class successfully add a new question,answers,difficulty
	     to a JSON file.
	 */
	/*test for addQuestion function in SysData class*/
	@Test
	public void testAddNewQuestion() {
		MangQuestionControl controller =new MangQuestionControl();
        Questions newQuestion = new Questions("What is a key principle of Agile software development?", new String[]{"1.Extensive planning at the beginning of a project to ensure there are no changes in the requirements."
        		,"2.Strong documentation is more important than working software.",
        		"3.Regular adaptation to changing circumstances and user feedback.",
        		"4.Complete each project phase before starting the next one without any overlap."}, 3, 1);
        int initialSize = controller.getQuestions().size();

        controller.addNewQuestion(newQuestion);

        List<Questions> updatedQuestions = controller.getQuestions();
        int updatedSize = updatedQuestions.size();

        // The question list size should be increased by 1 and we can see it now in the JSON file.
        Assertions.assertTrue(updatedSize == initialSize + 1);
    }
	
	  /*ID=BB2 test that reaching the winner in easy game and correctly triggers the appropriate win popup based on the player's color */
	/*test for endGame function in BoardEasyPlyers and test the displaying win frames */
	@Test
		 public void testGetWinPopupClassForWinnerPlayer() {
			Player player1 = new Player("TestPlayer1", Color.GREEN);
			Player player2 = new Player("TestPlayer", Color.BLUE);	   
			Game game = new Game("Easy", new ArrayList<>(Arrays.asList(player1,player2)));
			BoardEasyViewPlayers board2= new BoardEasyViewPlayers(game);
	       player1.setPosition(49);
		     Class<?> popupClass = board2.getWinPopupClassForWinner(player1);
		     
		     assertEquals(GreenWin.class, popupClass);
		 }

	
	/*ID=BB3 tests if the system successfully registers a new players when not unique nicknames are provided.*/
    @Test 
    /*test for class PlayerNicknames2*/
    public void testSuccessfulPlayerRegistration() {
        // Launch the DataReception screen
    	PlayerNicknames2 nickNames = new PlayerNicknames2(2,"Easy");
    	nickNames.textField1.setText("Player1");
    	nickNames.textField2.setText("Player1");
       
        assertFalse(nickNames.handleNextAction());
    }
    
    /*ID:BB4,tests for red snake in easy game if it return the player to 1*/
    /*test checkForSnakesAndLadders() function in BoardEasyViewPlayers class*/
	@Test
	public void testRedSnakeEncounter() {
		Player player1 = new Player("TestPlayer1", Color.GREEN);
		Player player2 = new Player("TestPlayer", Color.BLUE);	    
		Game game = new Game("Easy", new ArrayList<>(Arrays.asList(player1,player2)));

		BoardEasyViewPlayers board2= new BoardEasyViewPlayers(game);
		if ((board2.path).equals("board1"))
		{
			player1.setPosition(11);//red snake in 11
		}
		if ((board2.path).equals("board2"))
		{
			player1.setPosition(45);//red snake in 45
		}
		if  ((board2.path).equals("board3"))
		{
			player1.setPosition(22);//red snake in 22
		}
		board2.checkForSnakesAndLadders(player1.getPosition(),0);

		// Assert player position is now 1
		assertEquals(1, player1.getPosition());
	}

	/*ID=BB5,tests if the ladder in square 2 leads to square 23 0 In all the easy game changing board 
	 * we have ladder at square number 2 that leads to square 23 */
    /*Another test checkForSnakesAndLadders() function in BoardEasyViewPlayers class*/
	@Test
	public void testLadderClimb() {
		Player player1 = new Player("TestPlayer1", Color.GREEN);
		Player player2 = new Player("TestPlayer", Color.BLUE);	    
		Game game = new Game("Easy", new ArrayList<>(Arrays.asList(player1,player2)));

		BoardEasyViewPlayers board2= new BoardEasyViewPlayers(game);
		player1.setPosition(2);//a ladder in 2
		board2.checkForSnakesAndLadders(player1.getPosition(),0);
		assertEquals( 23, player1.getPosition());

	}
	
}
