package View;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import java.awt.Color;

import Model.BoardLevelTemplate;
import Model.EasyBoard;
import Model.Game;
import Model.GameDetails;
import Model.Ladder;
import Model.Player;
import Model.Questions;
import Model.Snake;
import Model.Square;
import Model.SysData;
import Model.WinFrame;
import Model.WinFrameFactory;
import Controller.GameController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.Timer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.util.concurrent.TimeUnit;
import java.awt.Dimension;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;


public class BoardEasyViewPlayers extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Game game;
	private Player currentPlayer;
	private int currentPlayerIndex;
	private int rollResult;
	private JButton diceButton;
	private JLabel currentPlayerLabel;
	private JTextPane txtpnHi;
	private JLabel timerLabel;
	private Timer gameTimer;
	public Questions quesTemp;
	private final int totalSquaresOnBoard = 49; 
	private  int selectedAnswer = -1;
	private JLabel greenPlayerLabel;
	private JLabel redPlayerLabel;
	private JLabel yellowPlayerLabel;
	private JLabel bluePlayerLabel;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_blue;
	private JLabel lblNewLabel_yellow;
	private JLabel lblNewLabel_red;
	private JLabel lblNewLabel_green;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	public String path ;
    private boolean isstopMusicClicked = false ;
    private boolean isGamePaused = false ; 
	private GameController controller; 
	private BoardLevelTemplate easyBoard;
	public static HashMap<String,Questions> questionsPOPUP= new HashMap<String, Questions>();
	private JLabel lblNewLabel_5;
	  long elapsedTime = 0;
	   long duration = 3000;
	   long startTime = System.currentTimeMillis();
	   long turnStartTime = System.currentTimeMillis();
	   long turnElapsedTime = 0;
	   private Timer autoRollTimer;


	public BoardEasyViewPlayers(Game game ) {
		this.easyBoard=new EasyBoard();
		this.currentPlayer=game.getCurrentPlayer();
		this.game=game;
		this.controller=new GameController(game);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1095, 772);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		// Make the frame undecorated (no title bar, no minimize/maximize/close buttons)
        setUndecorated(true);
		currentPlayerLabel = new JLabel("");
		currentPlayerLabel.setBounds(420, 80, 450, 50);
		currentPlayerLabel.setForeground(new java.awt.Color(0, 0, 0));
		currentPlayerLabel.setFont(new Font("Rage Italic", Font.BOLD, 35));
		contentPane.add(currentPlayerLabel);
		setContentPane(contentPane);
		txtpnHi = new JTextPane();
		txtpnHi.setEditable(false);
		txtpnHi.setBounds(10, 10, 311, 157);
		txtpnHi.setFont(new Font("Palatino Linotype", Font.BOLD, 24));
		txtpnHi.setForeground(new Color(0, 0, 0));
		txtpnHi.setBackground(new Color(255, 255, 153));
		contentPane.add(txtpnHi);

		diceButton = new JButton("");
		diceButton.setBounds(920, 360, 100, 100);
		diceButton.setIcon(new ImageIcon(PlayerTurn.class.getResource("/images/dice 4.jpg")));
		contentPane.add(diceButton);


		timerLabel = new JLabel("00:00");
		timerLabel.setBounds(920, 117, 100, 50);
		timerLabel.setFont(new Font("Snap ITC", Font.BOLD, 25));
		timerLabel.setForeground(new Color(252, 252, 252));
		contentPane.add(timerLabel);


		bluePlayerLabel = new JLabel(new ImageIcon(getClass().getResource("/images/blue-player.png")));
		bluePlayerLabel.setSize(30, 30);

		// Create JLabel for the green player
		greenPlayerLabel = new JLabel(new ImageIcon(getClass().getResource("/images/green-player.png")));
		greenPlayerLabel.setSize(30, 30);


		// Create JLabel for the red player
		redPlayerLabel = new JLabel(new ImageIcon(getClass().getResource("/images/red-player1.png")));
		redPlayerLabel.setSize(30, 30);

		// Create JLabel for the yellow player
		yellowPlayerLabel = new JLabel(new ImageIcon(getClass().getResource("/images/yellow-Player.png")));
		yellowPlayerLabel.setSize(30, 30);


		lblNewLabel_blue = new JLabel(new ImageIcon(getClass().getResource("/images/blue-player.png")));
		lblNewLabel_blue.setBounds(10, 250, 40, 40);

		lblNewLabel_green = new JLabel(new ImageIcon(getClass().getResource("/images/green-player.png")));
		lblNewLabel_green.setBounds(10, 300, 40, 40);


		lblNewLabel_red = new JLabel(new ImageIcon(getClass().getResource("/images/red-player1.png")));
		lblNewLabel_red.setBounds(10, 350, 40, 40);

		lblNewLabel_yellow = new JLabel(new ImageIcon(getClass().getResource("/images/yellow-Player.png")));
		lblNewLabel_yellow.setBounds(10, 400, 40, 40);

		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(50, 250, 250, 40);
		lblNewLabel_1.setForeground(new Color(252, 252, 252));
		lblNewLabel_1.setFont(new Font("Jokerman", Font.BOLD | Font.ITALIC, 30));


		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(50, 300, 250, 40);
		lblNewLabel_2.setForeground(new Color(252, 252, 252));
		lblNewLabel_2.setFont(new Font("Jokerman", Font.BOLD | Font.ITALIC, 30));

		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(50, 400, 250, 40);
		lblNewLabel_3.setForeground(new Color(252, 252, 252));
		lblNewLabel_3.setFont(new Font("Jokerman", Font.BOLD | Font.ITALIC, 30));


		lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(50, 350, 250, 40);
		lblNewLabel_4.setForeground(new Color(252, 252, 252));
		lblNewLabel_4.setFont(new Font("Jokerman", Font.BOLD | Font.ITALIC, 30));

		
		lblNewLabel_5 = new JLabel("");
	    lblNewLabel_5.setIcon(new ImageIcon(BoardEasyViewPlayers.class.getResource("/images/Button.png")));
	    lblNewLabel_5.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            ExitConfirmationDialog dialog = new ExitConfirmationDialog(BoardEasyViewPlayers.this);
	            dialog.setVisible(true);
	            if (dialog.isConfirmed()) {
	            	controller.MainSound(false);
	            	controller.FinalGame(false);
	                BoardEasyViewPlayers.this.setVisible(false);
	                new MainScreen().setVisible(true);
	            }
	        }
	    });
	    lblNewLabel_5.setBounds(24, 664, 105, 82);
	    contentPane.add(lblNewLabel_5);
	    JLabel stopLabel = new JLabel("");
        stopLabel.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/startMusic.png")));
        stopLabel.setBounds(954, 15, 130, 85); // Adjust size and position accordingly
        stopLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isstopMusicClicked) {
                    if (controller.isFialMusic) {
                        controller.FinalGame(false);
                    } else {
                        controller.MainSound(false);
                    }
                    isstopMusicClicked = true;
                    controller.isGameMuted = true;
                    stopLabel.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/stopMusic.png")));
                } else {
                    if (controller.isFialMusic) {
                        controller.FinalGame(true);
                    } else {
                        controller.MainSound(true);
                    }
                    isstopMusicClicked = false;
                    controller.isGameMuted = false;
                    stopLabel.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/startMusic.png")));
                }
            }
        });
        contentPane.add(stopLabel);

        JLabel resumeLabel = new JLabel();
        resumeLabel.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/StopButton.png")));
        resumeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isGamePaused) {
                    pauseGame();
                    diceButton.setEnabled(false);
                    resumeLabel.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/startButton.png")));
                    controller.MainSound(false);
                    controller.FinalGame(false);
                } else {
                    resumeGame();
                    diceButton.setEnabled(true);
                    resumeLabel.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/StopButton.png")));
                    stopLabel.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/startMusic.png")));
                    controller.MainSound(true);
                    if (controller.isFialMusic) {
                        controller.FinalGame(true);
                    } else {
                        controller.MainSound(true);
                    }
                }
            }
        });
        resumeLabel.setBounds(831, 27, 98, 73);
        contentPane.add(resumeLabel);
		startGame();


	}


	public void startGame() {
		initializeBoard();
        controller.MainSound(true);
		initializePlayerPositions();
	    contentPane.add(lblNewLabel);
		rollDiceAndMovePlayer();
		animatePlayerTurnTitle(); 
		startGameTimer(); 

	}
	void initializeBoard() {
	    // Generate a random number between 1 and 3
	    int randomNumber = (int)(Math.random() * 3) + 1;

	    lblNewLabel = new JLabel("");
	    lblNewLabel.setForeground(new Color(0, 0, 0));
	    lblNewLabel.setBounds(0, 0, 1095, 772);

	    switch (randomNumber) {
	    case 1:
	        lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/images/boardnew1.png")));
	        path = new String("board1");
	        break;
	    case 2:
	        lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/images/boardnew2.png")));
	        path = new String("board2");
	        break;
	    
	    case 3:
	        lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/images/boardnew3.png")));
	        path = new String("board3");
	        break;
	    }
	    easyBoard.startGame(null, null, null, null, randomNumber);
	    
	}


	private void startGameTimer() {
		startTime = System.currentTimeMillis();
		gameTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				long now = System.currentTimeMillis();
				long elapsed = now - startTime;
				long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed);
				long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsed) % 60;
				timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
			}
		});
		gameTimer.start();
	}


	private void animatePlayerTurnTitle() {
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
						currentPlayerLabel.setText("Player Turn: " + currentPlayer.getName());

						
			}
		};
	}



	private void advanceToNextPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % game.getPlayers().size();
		currentPlayer = game.getPlayers().get(currentPlayerIndex);
		game.setCurrentPlayer(currentPlayer);
		game.setCurrentPlayerIndex(currentPlayerIndex);
		displayCurrentPlayer(); 
		enableDiceRollForCurrentPlayer(); 
	}


	private void enableDiceRollForCurrentPlayer() {
		for (ActionListener al : diceButton.getActionListeners()) {
			diceButton.removeActionListener(al);
		}
		// Create a timer that will call performDiceRollAndMove() after 10 seconds
	    autoRollTimer = new Timer(10000, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	            // This will be called after 10 seconds
	        	 // Check if BoardEasyViewPlayers.this is visible
	            if (BoardEasyViewPlayers.this.isVisible()) {
	                // This will be called after 10 seconds only if the frame is visible
	                JOptionPane.showMessageDialog(BoardEasyViewPlayers.this,
	                    "Time is up! Rolling the dice automatically for " + currentPlayer.getName(), 
	                    "Auto Dice Roll", JOptionPane.INFORMATION_MESSAGE);
	                performDiceRollAndMove();
	            }
	        }
	    });
		diceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				diceButton.setEnabled(false);
	            autoRollTimer.stop(); // Stop the timer because the user has rolled the dice
				performDiceRollAndMove();
				
			}
		});

		diceButton.setEnabled(true);
		autoRollTimer.setRepeats(false); // Ensure the timer only runs once
	    autoRollTimer.start(); // Start the countdown for auto rolling the dice
	}
	public void updatePlayerPosition( int x, int y) {
		contentPane.revalidate();
		contentPane.repaint();

	}



	private void updateBoardView() {
		currentPlayer = game.getCurrentPlayer();

		int boardSize = game.getBoard().getSize();
		int position = currentPlayer.getPosition() - 1;
		int y = position % boardSize;
		int x = position / boardSize;

		if (x % 2 == 1) {
			y = boardSize - 1 - y;
		}

		x= (boardSize - 1) - x;

		this.updatePlayerPosition( x, y);

	}
	private void showLadderPopup(int lastpos) {
		JOptionPane.showMessageDialog(this,
				"<html><body><p> U have been move to "+lastpos+" but the Ladder will move u to "+currentPlayer.getPosition() +"🎉</p><img src='" + getClass().getResource("/images/giphy.gif") + "' width='100' height='100'></body></html>",
				"Congratss!! "+currentPlayer.getName()+" got a ladder!!!", JOptionPane.INFORMATION_MESSAGE);
	}

	private void showSnakePopup(int lastpos) {
		JOptionPane.showMessageDialog(this,
				"<html><body><p> U have been move to "+lastpos+" but the snake will move u to "+currentPlayer.getPosition()+" 😭</p><img src='" + getClass().getResource("/images/fall.gif") + "' width='100' height='100'></body></html>",
				"Oh no! "+currentPlayer.getName()+" got a snake ", JOptionPane.WARNING_MESSAGE);

	}

	public void performDiceRollAndMove() {
		controller.DiceRollingSound();
		diceButton.setEnabled(false);
		ImageIcon diceIcon;
		rollResult = game.getDice().rollForEasy();

		if(rollResult==5)
		{

			diceIcon = new ImageIcon(getClass().getResource("/images/dice 8.jpg"));
			diceButton.setIcon(diceIcon);
			SysData sysdata=new SysData();
			sysdata.LoadQuestions();
			questionsPOPUP=SysData.getQuestionsPOPUP();
			SysData.putQuestions(questionsPOPUP);
			String[] difficulty_levels ={"easy", "medium", "hard"};
			String temp=SysData.getRandomQuestion(difficulty_levels);
			quesTemp= SysData.getQuestionLevel(temp);
			JOptionPane.showMessageDialog(this, currentPlayer.getName() + " rolled a question!");
			showEditQuestionDialog(currentPlayer.getPosition());
			checkForSnakesAndLadders(currentPlayer.getPosition(),0);
			movePlayer(currentPlayer.getPosition());
			updateBoardView();
			displayPlayerPositions();     
		}
		else if(rollResult==6)
		{
			rollResult=0;

			diceIcon = new ImageIcon(getClass().getResource("/images/dice zero.jpg"));


			diceButton.setIcon(diceIcon);

			JOptionPane.showMessageDialog(this, currentPlayer.getName() + " rolled a " + rollResult +" , so will keep you on place", "Dice Roll", JOptionPane.INFORMATION_MESSAGE);
			movePlayer(currentPlayer, rollResult);
			updateBoardView();
			displayPlayerPositions(); // Update the display of player positions
		}

		else 
		{
			JOptionPane.showMessageDialog(this, currentPlayer.getName() + " rolled " + rollResult);

			diceIcon = new ImageIcon(getClass().getResource("/images/dice " + rollResult + ".jpg"));


			diceButton.setIcon(diceIcon);

			movePlayer(currentPlayer, rollResult);
			updateBoardView();
			displayPlayerPositions();
		}

		if (easyBoard.endGame(0,0, null, null,currentPlayer.getPosition(), game, null)) {
			endGame(currentPlayer);
		} else {
			advanceToNextPlayer();
		}
	}
	private void displayPlayerPositions() {
		StringBuilder positionsText = new StringBuilder();
		for (Player player : game.getPlayers()) {
			positionsText.append(" " + player.getName()).append(" on sqaure: ").append(player.getPosition()).append("\n");
		}
		txtpnHi.setText(positionsText.toString());
		contentPane.revalidate();
		contentPane.repaint();
	}


	private void rollDiceAndMovePlayer() {
		currentPlayer = game.getCurrentPlayer();
		displayCurrentPlayer();
		enableDiceRollForCurrentPlayer();
	}

	
	
	public Class<?> getWinPopupClassForWinner(Player winner) {
	    switch (winner.getColor()) {
	        case RED:
	            return RedWin.class;
	        case GREEN:
	            return GreenWin.class;
	        case BLUE:
	            return BlueWin.class;
	        case YELLOW:
	            return YellowWin.class;
	        default:
	            throw new IllegalArgumentException("Unknown player color");
	    }
	}



	public void endGame(Player winner) {
		gameTimer.stop(); // Stop the timer
		WinFrameFactory winframe=new WinFrameFactory();
		BoardEasyViewPlayers.this.setVisible(false);
		controller.MainSound(false);
		controller.FinalGame(false);
		switch (winner.getColor()) {
		case RED:
			WinFrame redFrame= winframe.getFrame(Model.Color.RED,winner.getName(), timerLabel.getText(),game);
			redFrame.createWinFrame(winner.getName(), timerLabel.getText(), game);
			break;
		case GREEN:
			WinFrame greenFrame= winframe.getFrame(Model.Color.GREEN,winner.getName(), timerLabel.getText(),game);
			greenFrame.createWinFrame(winner.getName(), timerLabel.getText(), game);
			break;
		case BLUE:
			WinFrame blueFrame= winframe.getFrame(Model.Color.GREEN,winner.getName(), timerLabel.getText(),game);
			blueFrame.createWinFrame(winner.getName(), timerLabel.getText(), game);
			break;
		case YELLOW:
			WinFrame yellowFrame= winframe.getFrame(Model.Color.GREEN,winner.getName(), timerLabel.getText(),game);
			yellowFrame.createWinFrame(winner.getName(), timerLabel.getText(), game);
			break;

		}
		game.endGame(winner.getName(),game.getDifficulty(),timerLabel.getText());
		saveGameDetails(currentPlayer);
	}
	public void saveGameDetails(Player winner) {
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
	    details.winnerName = winner.getName();
	    details.difficulty = game.getDifficulty();
	    details.time = timerLabel.getText();
	    gameList.add(details);

	    // Save updated game details
	    try (FileWriter writer = new FileWriter("src/game_history.json")) {
	        gson.toJson(gameList, writer);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void movePlayer(Player player, int roll) {
		int oldPosition = currentPlayer.getPosition();
		int newPosition = oldPosition + roll;
		JLabel playerLabel =null;
		Point startPoint=null;
		Point endPoint =null;
		// Ensure the player does not go past the last square
		if (newPosition >= totalSquaresOnBoard) {
			newPosition = totalSquaresOnBoard;
		}
		if (newPosition <= 0) {
			newPosition = 1;
		}
		boolean temp=false;
		temp=checkForSnakesAndLadders(newPosition,roll);
		if (newPosition >= totalSquaresOnBoard) {
			newPosition = totalSquaresOnBoard;
		}
		if(temp==true)
		{ player.setPosition(currentPlayer.getPosition());
		game.getCurrentPlayer().setPosition(currentPlayer.getPosition());
		currentPlayer.setPosition(currentPlayer.getPosition());
		game.updatePlayerPositionInList(player.getName(), currentPlayer.getPosition());

		startPoint = controller.boardPositionToPixel(newPosition,currentPlayer,path);
		endPoint = controller.boardPositionToPixel(currentPlayer.getPosition(),currentPlayer,path); 

		playerLabel = getPlayerLabel(currentPlayer);
		}else
		{

			player.setPosition(newPosition);
			game.getCurrentPlayer().setPosition(newPosition);
			currentPlayer.setPosition(newPosition);
			game.updatePlayerPositionInList(player.getName(),newPosition);

			startPoint = controller.boardPositionToPixel(oldPosition,currentPlayer,path);
			endPoint = controller.boardPositionToPixel(newPosition,currentPlayer,path); 

			playerLabel = getPlayerLabel(currentPlayer);
		}
		if (playerLabel != null) {
			animateMovement(playerLabel, startPoint, endPoint);
		}


	}	private void movePlayer1(Player player, int roll) {
		int oldPosition = currentPlayer.getPosition();
		int newPosition = oldPosition + roll;
		JLabel playerLabel =null;
		Point startPoint=null;
		Point endPoint =null;
		// Ensure the player does not go past the last square
		if (newPosition >= totalSquaresOnBoard) {
			newPosition = totalSquaresOnBoard;
		}
		boolean temp=false;
		temp=checkForSnakesAndLadders1(newPosition);

		if(temp==true)
		{ player.setPosition(currentPlayer.getPosition());
		game.getCurrentPlayer().setPosition(currentPlayer.getPosition());
		currentPlayer.setPosition(currentPlayer.getPosition());
		game.updatePlayerPositionInList(player.getName(), currentPlayer.getPosition());

		startPoint = controller.boardPositionToPixel(newPosition,currentPlayer,path);
		endPoint = controller.boardPositionToPixel(currentPlayer.getPosition(),currentPlayer,path); 

		playerLabel = getPlayerLabel(currentPlayer);
		}else
		{
			player.setPosition(newPosition);
			game.getCurrentPlayer().setPosition(newPosition);
			currentPlayer.setPosition(newPosition);
			game.updatePlayerPositionInList(player.getName(),newPosition);

			startPoint = controller.boardPositionToPixel(oldPosition,currentPlayer,path);
			endPoint = controller.boardPositionToPixel(newPosition,currentPlayer,path); 

			playerLabel = getPlayerLabel(currentPlayer);
		}
		if (playerLabel != null) {
			animateMovement(playerLabel, startPoint, endPoint);
		}


	}
	private boolean checkForSnakesAndLadders1(int pos)
	{
		int lastpos=pos;
		for (Snake snake : game.getBoard().getSnakes()) {
			if (pos ==(snake.getSquareStart().getValue())) {
			if (pos == (snake.getSquareStart().getValue())) {
				game.getCurrentPlayer().setPosition((snake.getSquareEnd().getValue()));
				currentPlayer.setPosition((snake.getSquareEnd().getValue()));
				game.updatePlayerPositionInList(currentPlayer.getName(), (snake.getSquareEnd().getValue()));
				showSnakePopup(lastpos); 
				return true;
			}
		}}

		for (Ladder ladder : game.getBoard().getLadders()) {
			if (pos == (ladder.getSquareStart().getValue())) {
				game.getCurrentPlayer().setPosition((ladder.getSquareEnd().getValue()));
				currentPlayer.setPosition((ladder.getSquareEnd().getValue()));
				game.updatePlayerPositionInList(currentPlayer.getName(), (ladder.getSquareEnd().getValue()));
				showLadderPopup(lastpos); 
				return true;
			}
		}
		return false;

	}
	
	//for a question on dice 
	private void movePlayer(int pos) {
		Point startPoint = controller.boardPositionToPixel(pos,currentPlayer,path);
		Point endPoint = controller.boardPositionToPixel(currentPlayer.getPosition(),currentPlayer,path);

		JLabel playerLabel = getPlayerLabel(currentPlayer);

		if (playerLabel != null) {
			animateMovement(playerLabel, startPoint, endPoint);
		}


	}
	private void animateMovement(JLabel playerLabel, Point start, Point end) {
		// Define the target end point based on specific positions
		Point targetEndPoint = end;
		final int numberOfSteps = 10;
		final Timer timer = new Timer(150, null);
		timer.addActionListener(new ActionListener() {
			int currentStep = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				double xIncrement = (targetEndPoint.x - start.x) / (double) numberOfSteps;
				double yIncrement = (targetEndPoint.y - start.y) / (double) numberOfSteps;

				int newX = (int) (start.x + (xIncrement * currentStep));
				int newY = (int) (start.y + (yIncrement * currentStep));

				playerLabel.setLocation(newX, newY);
				contentPane.revalidate();
				contentPane.repaint();

				currentStep++;
				if (currentStep > numberOfSteps) {
					timer.stop();
				}
			}
		});
		timer.start();
	}


	private JLabel getPlayerLabel(Player player) {
		switch (player.getColor()) {
		case BLUE:
			return bluePlayerLabel;
		case GREEN:
			return greenPlayerLabel;
		case RED:
			return redPlayerLabel;
		case YELLOW:
			return yellowPlayerLabel;
		default:
			return null; 
		}
	}

	    private void displayCurrentPlayer() {
	        if (currentPlayer != null) {
	        	currentPlayerLabel.setForeground(new Color(252, 252, 252));

	            currentPlayerLabel.setText("Player Turn: " + currentPlayer.getName());
	            setTitle("Current Player: " + currentPlayer.getName() + "'s Turn");
	        }
	    }

		private void initializePlayerPositions() {
	        StringBuilder positionsText = new StringBuilder();
	        for (Player player : game.getPlayers()) {
	            player.setPosition(1);
	            positionsText.append(player.getName()).append(" on square: ").append(player.getPosition()).append("\n");
	            game.updatePlayerPositionInList(player.getName(), 1);
	        }
	        Point bluePlayerStartPos ; 
	         Point greenPlayerStartPos; 
	         Point redPlayerStartPos ; 
	         Point yellowPlayerStartPos ;
	        txtpnHi.setText(positionsText.toString());
	        if(path.equals("board3"))
	        {
	        	 bluePlayerStartPos =  new Point(220,660); 
		         greenPlayerStartPos = new Point(250,660); 
		         redPlayerStartPos = new Point(220,635); 
		         yellowPlayerStartPos = new Point(250,635); 
	        }
	        else
	        {
	        	  bluePlayerStartPos =  new Point(290,640); 
	 	         greenPlayerStartPos = new Point(260,640); 
	 	         redPlayerStartPos = new Point(290,670); 
	 	         yellowPlayerStartPos = new Point(260,670);
	        }
	        
	        if (game.getPlayers().size()==2)
	        {
	        	bluePlayerLabel.setLocation(bluePlayerStartPos.x, bluePlayerStartPos.y);
		        greenPlayerLabel.setLocation(greenPlayerStartPos.x, greenPlayerStartPos.y);
				contentPane.add(greenPlayerLabel);
				contentPane.add(bluePlayerLabel);
		  		contentPane.add(lblNewLabel_green);
		  		contentPane.add(lblNewLabel_blue);


	        }
	        else if(game.getPlayers().size()==3)
	        {
	        	redPlayerLabel.setLocation(redPlayerStartPos.x, redPlayerStartPos.y);
	        	bluePlayerLabel.setLocation(bluePlayerStartPos.x, bluePlayerStartPos.y);
		        greenPlayerLabel.setLocation(greenPlayerStartPos.x, greenPlayerStartPos.y);
		        contentPane.add(greenPlayerLabel);
				contentPane.add(bluePlayerLabel);
				contentPane.add(redPlayerLabel);
				contentPane.add(lblNewLabel_green);
		  		contentPane.add(lblNewLabel_blue);
		  		contentPane.add(lblNewLabel_red);

	        }
	        else {
		        yellowPlayerLabel.setLocation(yellowPlayerStartPos.x, yellowPlayerStartPos.y);
		        redPlayerLabel.setLocation(redPlayerStartPos.x, redPlayerStartPos.y);
	        	bluePlayerLabel.setLocation(bluePlayerStartPos.x, bluePlayerStartPos.y);
		        greenPlayerLabel.setLocation(greenPlayerStartPos.x, greenPlayerStartPos.y);
		        contentPane.add(greenPlayerLabel);
				contentPane.add(bluePlayerLabel);
				contentPane.add(yellowPlayerLabel);
				contentPane.add(redPlayerLabel);
				contentPane.add(lblNewLabel_green);
		  		contentPane.add(lblNewLabel_blue);
		  		contentPane.add(lblNewLabel_red);
		  		contentPane.add(lblNewLabel_yellow);

	        }
	        for (Player p : game.getPlayers()) {
	            Model.Color playerColor = p.getColor();
	            if (playerColor.equals(Model.Color .BLUE)) {
	                lblNewLabel_1.setText(p.getName());
	                contentPane.add(lblNewLabel_1);
	            } else if (playerColor.equals(Model.Color .GREEN)) {
	                lblNewLabel_2.setText(p.getName());
	                contentPane.add(lblNewLabel_2);
	            } else if (playerColor.equals(Model.Color .YELLOW)) {
	                lblNewLabel_3.setText(p.getName());
	                contentPane.add(lblNewLabel_3);
	            } else {
	                lblNewLabel_4.setText(p.getName());
	                contentPane.add(lblNewLabel_4);
	            }
	        }

	        contentPane.revalidate();
	        contentPane.repaint();
	    }
	    
		private void showEditQuestionDialog(int pos ) {
	    	 JPanel questionPanel = new JPanel();
	    	    questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.PAGE_AXIS));
	    	    Dimension preferredSize = new Dimension(1200, 400);
	    	    questionPanel.setPreferredSize(preferredSize);
	    	  // Create and add the position label to the panel
	    	  String message;
	    	    switch (this.quesTemp.getDiffculty()) {
	    	        case 1: // Easy
	    	            message = "This is an easy question. A wrong answer will set you back one square, and a correct answer will keep you on place.";
	    	            break;
	    	        case 2: // Medium
	    	            message = "You're facing a medium difficulty question. Incorrectly answering will set you back two squares , and a correct answer will stay on place";
	    	            break;
	    	        case 3: // Hard
	    	            message = "This is a hard question. A wrong answer will set you back three squares, but a correct answer will move you forward one square ";
	    	            break;
	    	        default:
	    	            message = "Difficulty level is unknown. Be cautious with your answer.";
	    	            break;
	    	    }
	    	    
	    	    JLabel infoLabel = new JLabel("<html><div style='background-color: yellow; padding: 10px; font-size: 20px; font-family: Serif; font-style: italic;'>😃 " + message + " 😃</div></html>");
	    	    infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    	    questionPanel.add(infoLabel);


	    	    questionPanel.add(Box.createRigidArea(new Dimension(0, 10)));

	    	    JLabel questionLabel = new JLabel("<html><div style='padding: 10px;font-size: 20px;font-family: Serif; font-style: italic;'>" + this.quesTemp.getQuestionText() + "</div></html>");
	    	    questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    	    questionPanel.add(questionLabel);
	    	    questionPanel.add(Box.createRigidArea(new Dimension(0, 10)));


	    	  ButtonGroup optionsGroup = new ButtonGroup();
	    	  JRadioButton option1 = new JRadioButton(this.quesTemp.getOptions()[0]);
	    	  option1.setForeground(Color.RED); // Color the text
	    	  option1.setFont(new Font("Comic Sans MS", Font.BOLD, 20)); // Set the font

	    	  JRadioButton option2 = new JRadioButton(this.quesTemp.getOptions()[1]);
	    	  option2.setForeground(Color.ORANGE);
	    	  option2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));

	    	  JRadioButton option3 = new JRadioButton(this.quesTemp.getOptions()[2]);
	    	  option3.setForeground(Color.BLUE);
	    	  option3.setFont(new Font("Comic Sans MS", Font.BOLD, 20));

	    	  JRadioButton option4 = new JRadioButton(this.quesTemp.getOptions()[3]);
	    	  option4.setForeground(Color.GREEN);
	    	  option4.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
	    	  optionsGroup.add(option1);
	    	  optionsGroup.add(option2);
	    	  optionsGroup.add(option3);
	    	  optionsGroup.add(option4);
	    	  questionPanel.add(option1);
	    	  questionPanel.add(option2);
	    	  questionPanel.add(option3);
	    	  questionPanel.add(option4);


	    	  int result = JOptionPane.showOptionDialog(null, questionPanel, 
	    	      "Hii " + currentPlayer.getName()+" you are now suppose to be in "+ pos+" but as ur response u will move or stay in "+pos, 
	    	      JOptionPane.OK_CANCEL_OPTION, 
	    	      JOptionPane.PLAIN_MESSAGE, 
	    	      null, null, null);
               
	           if (result == JOptionPane.OK_OPTION) {
	               if (option1.isSelected()) selectedAnswer = 1;
	               if (option2.isSelected()) selectedAnswer = 2;
	               if (option3.isSelected()) selectedAnswer = 3;
	               if (option4.isSelected()) selectedAnswer = 4;

	               handleAnswer(selectedAnswer);
	           }
	       }



	public boolean checkForSnakesAndLadders(int pos, int roll) {
		int lastpos=pos;
		for (Snake snake : game.getBoard().getSnakes()) {
			if (pos == snake.getSquareStart().getValue()) {
				game.getCurrentPlayer().setPosition(snake.getSquareEnd().getValue());
				game.updatePlayerPositionInList(currentPlayer.getName(), snake.getSquareEnd().getValue());
			if (pos == (snake.getSquareStart().getValue())) {
				game.getCurrentPlayer().setPosition((snake.getSquareEnd().getValue()));
				game.updatePlayerPositionInList(currentPlayer.getName(), (snake.getSquareEnd().getValue()));
				showSnakePopup(lastpos); 
				return true;
			}
		}}

		for (Ladder ladder : game.getBoard().getLadders()) {
			if (pos == (ladder.getSquareStart().getValue())) {
				game.getCurrentPlayer().setPosition(ladder.getSquareEnd().getValue());
				currentPlayer.setPosition((ladder.getSquareEnd().getValue()));
				game.updatePlayerPositionInList(currentPlayer.getName(), ladder.getSquareEnd().getValue());
				game.getCurrentPlayer().setPosition((ladder.getSquareEnd().getValue()));
				currentPlayer.setPosition((ladder.getSquareEnd().getValue()));
				game.updatePlayerPositionInList(currentPlayer.getName(), (ladder.getSquareEnd().getValue()));
				showLadderPopup(lastpos); 
				return true;
			}
		}

		for (Square q : game.getBoard().getQuestions()) {
			
			if (pos== (q.getValue())) {
				SysData sysdata=new SysData();
				Point startPoint = controller.boardPositionToPixel(currentPlayer.getPosition(),currentPlayer,path);
				Point endPoint = controller.boardPositionToPixel(currentPlayer.getPosition() + roll,currentPlayer,path); 
				JLabel playerLabel = getPlayerLabel(currentPlayer);
				animateMovement(playerLabel, startPoint, endPoint);
				displayPlayerPositions();
				sysdata.LoadQuestions();
				questionsPOPUP=SysData.getQuestionsPOPUP();
				SysData.putQuestions(questionsPOPUP);
				quesTemp= SysData.getQuestionForPosition(pos);
				currentPlayer.setPosition(pos);
				game.updatePlayerPositionInList(currentPlayer.getName(), pos);
				showEditQuestionDialog(pos);
				movePlayer1(currentPlayer,0);
				return true;
			}
		}



		return false;
	}
	public void handleAnswer(int selectedAnswer ) {
		boolean isCorrectAnswer=true;

		if(selectedAnswer == this.quesTemp.getCorrectOption()) {
			JOptionPane.showMessageDialog(this, "Congratsss! It's a correct Answer!");
			if(this.quesTemp.getDiffculty()==1)
			{

				movePlayerBasedOnQuestion(1 ,isCorrectAnswer);

			}
			if(this.quesTemp.getDiffculty()==2)
			{

				movePlayerBasedOnQuestion(2 ,isCorrectAnswer);

			}
			if(this.quesTemp.getDiffculty()==3)
			{

				movePlayerBasedOnQuestion(3 ,isCorrectAnswer);

			}
		} else {
			JOptionPane.showMessageDialog(this, "Opss! It's an wrong Answer!");
			isCorrectAnswer=false;
			if(this.quesTemp.getDiffculty()==1)
			{
				movePlayerBasedOnQuestion(1 ,isCorrectAnswer);

			}
			if(this.quesTemp.getDiffculty()==2)
			{

				movePlayerBasedOnQuestion(2 ,isCorrectAnswer);

			}
			if(this.quesTemp.getDiffculty()==3)
			{

				movePlayerBasedOnQuestion(3 ,isCorrectAnswer);

			}
		}


	}
    public void pauseGame() {
    	elapsedTime = 0 ; 
    	turnElapsedTime = 0 ; 
    	//stop main timer
            gameTimer.stop();
            isGamePaused = true;
            elapsedTime += System.currentTimeMillis() - startTime;
        // stop turn timer
            autoRollTimer.stop();
            turnElapsedTime+= System.currentTimeMillis() - turnStartTime;     
            
        
    }
    
  

    public void resumeGame() {
    	startTime = 0 ;
    	turnStartTime = 0 ; 
            startTime = System.currentTimeMillis() - elapsedTime;
            gameTimer.start();
            turnStartTime = System.currentTimeMillis() - turnElapsedTime;
            autoRollTimer.start();
            isGamePaused = false;

    }
    

	public void movePlayerBasedOnQuestion(int questionDifficulty, boolean isCorrectAnswer) {
		// Define the movement rules based on difficulty and correctness

		int steps=0;
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
		controller.updatePlayerPosition(steps,currentPlayer);
	}
}