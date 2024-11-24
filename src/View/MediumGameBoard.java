package View; 
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



import Controller.GameController;
import Model.Ladder;
import Model.MediumBoard;
import Model.Player;
import Model.Snake;
import Model.Square;
import Model.BoardLevelTemplate;
import Model.Dice;
import Model.Game;
import Model.GameDetails;
import Model.HardBoard;
import Model.SquareType;
 
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.OverlayLayout;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class MediumGameBoard extends JFrame
{
	private static final int GRID_SIZE = 10;
	private static final Color[] COLORS = new Color[]{new Color(175, 238, 238), Color.WHITE, new Color(255, 255, 204), new Color(255, 51, 102), new Color(152, 251, 152)};
	private Color[][] boardColors = new Color[GRID_SIZE][GRID_SIZE];
    private Square[][] squares = new Square[10][10];
    private JLabel[][] boardlabels = new JLabel[GRID_SIZE][GRID_SIZE];
    private Dice dice = new Dice("medium"); 
    private Snake[] snakes = new Snake[6];
    private Ladder[] ladders = new Ladder[6];
    private Square[] quastionSquares = new Square[3];
    private Square[] surpriseSquares = new Square[2];
    private BoardLevelTemplate meduimboard = new MediumBoard();
    private GameController controller ; 
    private int index = 0 ;
    public static JLabel[] playersLable;
	private static Map<ArrayList<Integer>,String> takenCells = new HashMap<>();
    private int WinValue = 100 ; 
    private List<Player> arraylistOrderByPosition ;
	private Timer gameTimer;
	private StringBuilder htmlBuilder ;
    private JTextPane textPane_1 ;
    private BoardLevelTemplate mediumBoard;
    JButton diceButton;
    private Game game;
    Player CurrentPlayer ;
    Random rand = new Random();
    public static Timer turnTimer;
    private JLabel jl1 = new JLabel("00:00", SwingConstants.CENTER);
    JLabel textPane_1_2_1 = new JLabel("");
    long elapsedTime = 0;

    long duration = 3000;
    long startTime = System.currentTimeMillis();
    long turnStartTime = System.currentTimeMillis();
    long turnElapsedTime = 0;
    int countdown = 10; 
    
    int[] ladderLengths = {1, 2, 3, 4, 5, 6};
    JPanel outerPanel = new JPanel();
   private long remainingPlayerTime;
    private boolean isGamePaused = false;
    private long turnTimerStartTime;
    private JLabel textPane = new JLabel("");
    private JLabel textPane_1_2 = new JLabel("");
    private JLabel jl = new JLabel("00:00");
    private boolean isdiceClicked = false  ;
    private boolean isstopMusicClicked = false ;
    
    public MediumGameBoard(Game game) {

    	this.game = game;
        controller = new GameController(game,this);

        // Setting up the main frame
    	//frame = new JFrame();
        setTitle("Game Board");
        this.mediumBoard=new MediumBoard();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 20, 1152, 816);
        // Creating the outer panel with BorderLayout
        outerPanel.setLayout(null);
        // Make the frame undecorated (no title bar, no minimize/maximize/close buttons)
        setUndecorated(true);
        
        // Creating the inner panel
        JPanel innerPanel = new JPanel();
        initializeBoard(innerPanel,outerPanel);
        textPane.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 24));

        textPane.setBounds(355, 34, 252, 55);
        outerPanel.add(textPane);
        textPane.setText("\n Turn: " + game.getCurrentPlayer().getName());
        innerPanel.setBounds(224, 118, 550, 550);
        innerPanel.setBackground(Color.WHITE);
         // Adding the inner panel to the center of the outer panel
        outerPanel.add(innerPanel);
        innerPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/textPaneFrame.png")));
        lblNewLabel_2.setBackground(SystemColor.desktop);
        lblNewLabel_2.setBounds(204, -7, 550, 162);
        outerPanel.add(lblNewLabel_2);
        
        updateTextPane(game.getPlayers());
        startTime = System.currentTimeMillis();
		gameTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isGamePaused) {
					
				long now = System.currentTimeMillis();
				long elapsed = now - startTime;
				long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed);
				long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsed) % 60;
				jl.setText(String.format("%02d:%02d", minutes, seconds));
				}
			}
		});
		gameTimer.start();
        setResizable(false); 

        diceButton = new JButton("");
        diceButton.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/dice 3.jpg")));
        diceButton.setBounds(928, 637, 78, 81);
        outerPanel.add(diceButton);
        game.setBoard(meduimboard);
        game.setDice(dice);
        controller.MainSound(true);
        controller.CallQuestionDataFunc();

        IntilaizePlayerPositionView(game , controller , outerPanel);
        arraylistOrderByPosition = game.getPlayers();
        // create game instance and set the board and the dice >> BACKEND . 
        //CurrentPlayer = controller.getGame().getCurrentPlayer();
        
     	 turnTimer = new Timer(10000, new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
            	 if (!isGamePaused) {
                         turnTimer.stop(); // Stop the timer to prevent it from repeating
                         animateDiceRoll(); // Automatically roll the dice
                         startNewTurn();
                         diceButton.setEnabled(true); 
                     
                 }}
             
         });
     	 
        turnTimer.start();
        
        diceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	controller.countdown(false);
            	isdiceClicked = true ; 
                index = game.getCurrentPlayerIndex();
                if (turnTimer.isRunning()) {
                    turnTimer.stop(); // Stop the countdown as the player is taking action
                }
                animateDiceRoll();
                Player CurrentPlayer = game.getPlayers().get(index);
                diceButton.setEnabled(true);
                
                game.setCurrentPlayerIndex(index);
                game.setCurrentPlayer(game.getPlayers().get(index));
           
             //  controller.setPlayerBackgroundColor(game.getCurrentPlayer().getColor(), textPane);
             // updateTextPane(arraylistOrderByPosition);

                startNewTurn();

   }
  
        });
        controller.setPlayerForegroundColor(game.getCurrentPlayer().getColor(), textPane);

         htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><body><ul>");
        for (Player p : arraylistOrderByPosition) {
            htmlBuilder.append("<li>").append(p.getName() + " " + p.getPosition()).append("</li>");
        }
        htmlBuilder.append("</ul></body></html>");
        String htmlString = htmlBuilder.toString();
        // Adding the outer panel to the frame
        this.getContentPane().add(outerPanel);
        
     // Assuming you have already defined a JButton resume;
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
        outerPanel.add(stopLabel);

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
        outerPanel.add(resumeLabel);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/Dice.png")));
        lblNewLabel.setBounds(701, 596, 500, 150);
        outerPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/Button.png")));
        lblNewLabel_3.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            ExitConfirmationDialog dialog = new ExitConfirmationDialog(MediumGameBoard.this);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
            	gameTimer.stop();
            	turnTimer.stop();
            	controller.MainSound(false);
            	controller.FinalGame(false);
                MediumGameBoard.this.setVisible(false);
                new MainScreen().setVisible(true);
            }
        }
    });
        lblNewLabel_3.setBounds(26, 700, 100, 81);
        outerPanel.add(lblNewLabel_3);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/MainMediumBoard.png")));
        lblNewLabel_1.setBounds(-48, -24, 1200, 1162);
        outerPanel.add(lblNewLabel_1);
        
        
        JLabel lblNewLabel_4 = new JLabel("New label");
        lblNewLabel_4.setBounds(917, 321, 45, 13);
        outerPanel.add(lblNewLabel_4);
    

        this.setVisible(true);
    }
    
    private void initializeBoard(JPanel panel, JPanel outerPanel) { 
        int cellSize = 550 / GRID_SIZE; // the innerPanel is 550x550 and each cell is 55x55 pixels
        int count=0;
        int surpriseCount=0;
        Set<Integer> chosenCells = new HashSet<>(); // Track chosen cell numbers
        Set<Integer> chosenSurpriseCells = new HashSet<>(); // Track chosen cell numbers for surprise squares
        while (chosenCells.size() < 3) {
            int cellNumber = rand.nextInt(98) + 2; // Generate a random cell number between 2 and 99
            chosenCells.add(cellNumber); // Add the chosen cell number to the set
        }
     // Add surprise squares
        while (chosenSurpriseCells.size() < 2) {
            int cellNumber = rand.nextInt(98) + 2; // Generate a random cell number between 2 and 99
            if (!chosenCells.contains(cellNumber) && !chosenSurpriseCells.contains(cellNumber)) {
                chosenSurpriseCells.add(cellNumber); // Add the chosen cell number to the set
            }
        }
        
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                int cellNumber = i * GRID_SIZE + (i % 2 == 0 ? j : GRID_SIZE - 1 - j);
                cellNumber = GRID_SIZE * GRID_SIZE - cellNumber;
                JPanel cell = new JPanel(new BorderLayout());
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabel label = new JLabel(String.valueOf(cellNumber), SwingConstants.CENTER);
                cell.add(label, BorderLayout.CENTER);
                // Get a color for the cell that is not equal to the adjacent cells
                cell.setBackground(getUniqueColor(i, j));
                // Update the board colors array with the new color for reference
                boardColors[i][j] = cell.getBackground();
                // Make the text color contrast with the background
                label.setForeground(getContrastColor(cell.getBackground()));
                panel.add(cell);
                // Calculate the bounds for each label
                int x = j * cellSize + panel.getBounds().x + 224; // Adjust for the actual position of the panel
                int y = i * cellSize + panel.getBounds().y + 118;
                // Initialize square type based on cellNumber
                if (chosenCells.contains(cellNumber)) {
                    label.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/QuestionMark.png")));
                    label.setText(""); // Set empty string for text
                    squares[i][j] = new Square(i, j, SquareType.QUESTION, x, y, cellNumber);
                    quastionSquares[count] = squares[i][j];
                    ArrayList<Integer> arrayList= new ArrayList<Integer>();
                    arrayList.add(i);
                    arrayList.add(j);
                    takenCells.put(arrayList,"question"+count);
                    count++;
                } else if (chosenSurpriseCells.contains(cellNumber)) {
                    label.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/QuestionMarkM.png")));
                    label.setText(""); // Set empty string for text 
                    squares[i][j] = new Square(i, j, SquareType.SURPRISE, x, y, cellNumber);
                    surpriseSquares[surpriseCount] = squares[i][j];
                    ArrayList<Integer> arrayList= new ArrayList<Integer>();
                    arrayList.add(i);
                    arrayList.add(j);
                    takenCells.put(arrayList,"surprise"+surpriseCount);
                    surpriseCount++; // Increment the surprise count
                } else {
                    squares[i][j] = new Square(i, j, SquareType.NORMAL, x, y, cellNumber);
                }
                if(cellNumber == 100) {
                    label.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/StarWin.png")));
                    label.setText(""); // Set empty string for text
 
                }
                boardlabels[i][j] = label;
            }
        }
        setRedSnakes(outerPanel);
        setYellowSnake(outerPanel);
        setBlueSnakes(outerPanel);
        setGreenSnakes(outerPanel);
        setLadders(outerPanel);
//        for (Map.Entry<ArrayList<Integer>, String> entry : takenCells.entrySet()) {
//           System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
//        }
        meduimboard.startGame(squares,snakes,ladders,quastionSquares,0);
    }

 
    // Get a color that is different from the adjacent cell
    private Color getUniqueColor(int row, int col) {
        List<Color> availableColors = new ArrayList<>(Arrays.asList(COLORS));
        if (row > 0) { availableColors.remove(boardColors[row - 1][col]); } 
        if (col > 0) { availableColors.remove(boardColors[row][col - 1]); } 
        if (row > 0 && col > 0) { availableColors.remove(boardColors[row - 1][col - 1]); } 
        if (row > 0 && col < GRID_SIZE - 1) { availableColors.remove(boardColors[row - 1][col + 1]); } 
        return availableColors.get(new Random().nextInt(availableColors.size()));
    }
    private Color getContrastColor(Color color) { //method that choose the color of the number count on the square 
        double luminance = (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue()) / 255;
        return luminance > 0.5 ? Color.BLACK : Color.WHITE;  
    }

    private void setRedSnakes(JPanel panel) {
        int i1, j1, i2, j2; 
        ArrayList<Integer> arr= new ArrayList<Integer>();
        ArrayList<Integer> arr2= new ArrayList<Integer>();
        // Place the first red snake
        do {
        	arr.clear();
            i1 = rand.nextInt(9)+1; // Red snake 1
            j1 = rand.nextInt(9)+1;
            arr.add(i1);
            arr.add(j1);
        } while (takenCells.containsKey(arr) || (i1==9 && j1==0) || (i1==0 && j1==0) );
        takenCells.put(arr,"redsnak1");
        // Place the second red snake
        do {
            i2 = rand.nextInt(9)+1; // Red snake 2
            j2 = rand.nextInt(9)+1;
            arr2.clear();
            arr2.add(i2);
            arr2.add(j2);
        } while (takenCells.containsKey(arr2) || (i2 == i1 && j2 == j1)|| (i2==9 && j2==0) || (i2==0 && j2==0));
        takenCells.put(arr2,"redsnake2");

        JLabel label_1 = new JLabel();
		label_1.setBounds(squares[i1][j1].getBoundsX()+10, squares[i1][j1].getBoundsY(), 55, 55);
        Snake redSnake1 = new Snake(squares[i1][j1], squares[9][0]);
        snakes[0] = redSnake1;
     textPane_1_2_1.setForeground(new Color(0, 0, 0));
            textPane_1_2_1.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
            
            textPane_1_2_1.setBounds(888, 200, 203, 180);
            outerPanel.add(textPane_1_2_1);
        
            jl.setLocation(955, 365);
            outerPanel.add(jl);
            jl.setVisible(true);
            jl.setSize(150, 86);
            
                    Font labelFont = jl.getFont();
                    jl.setFont(new Font("Maiandra GD", Font.PLAIN, 28));
        
        JLabel lblNewLabel_5 = new JLabel("");
        lblNewLabel_5.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/TimerAndPlayernamess.png")));
        lblNewLabel_5.setBounds(701, 170, 500, 318);
        outerPanel.add(lblNewLabel_5);
        
       
        panel.add(label_1);
        label_1.setIcon(new ImageIcon(HardGameBoard.class.getResource("/images/RedSnake.png")));
        JLabel label_2 = new JLabel();
        label_2.setBounds(squares[i2][j2].getBoundsX()+10, squares[i2][j2].getBoundsY(), 50, 50);
        //object red snake 2 
        Snake redSnake2 = new Snake(squares[i2][j2], squares[9][0]);
        snakes[1] = redSnake2;
        panel.add(label_2);
        label_2.setIcon(new ImageIcon(HardGameBoard.class.getResource("/images/RedSnake.png")));
    }
 
    
    private void setYellowSnake(JPanel panel) {
        int i, j;
        ArrayList<Integer> arr= new ArrayList<Integer>();
        do {
            i = generateRandomNumber_I(Color.YELLOW); // Yellow snakes
            j= generateRandomNumber_J(Color.YELLOW);
        	arr.clear();
            arr.add(i);
            arr.add(j);
        } while(takenCells.containsKey(arr) || (i==9 && j==0) || (i==0 && j==0));       
        takenCells.put(arr,"yellowSnake");
        JLabel yellowSnakeLabel = new JLabel();
        yellowSnakeLabel.setBounds(squares[i][j].getBoundsX(), squares[i][j].getBoundsY(), 100, 100);// Yellow
        Square EndSquare = findSquare(squares[i][j], Color.YELLOW);
        Snake yellowSnake = new Snake(squares[i][j], EndSquare);
        snakes[2] = yellowSnake;
        yellowSnakeLabel.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/rightYellow.png")));
        panel.add(yellowSnakeLabel);
    }
 
    private void setBlueSnakes(JPanel panel) {
        int i, j;
        ArrayList<Integer> arr= new ArrayList<Integer>();
        do {
            i = generateRandomNumber_I(Color.BLUE); // Blue snakes
            j = generateRandomNumber_J(Color.BLUE);
        	arr.clear();
            arr.add(i);
            arr.add(j);
        } while(takenCells.containsKey(arr)  || (i==9 && j==0)  || (i==0 && j==0));
        takenCells.put(arr,"blueSnake");
        JLabel labelBlue = new JLabel();
        labelBlue.setBounds(squares[i][j].getBoundsX() - 110, squares[i][j].getBoundsY() + 15, 140, 170);// BLUE
        Square EndSquare = findSquare(squares[i][j], Color.BLUE);
        Snake BlueSnake1 = new Snake(squares[i][j], EndSquare);
        snakes[3] = BlueSnake1;
        labelBlue.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/SnakeBlueRight.png")));
        panel.add(labelBlue);
    }
 
    public void saveGameDetails(Player winner) {
	    Gson gson = new Gson();
	    java.lang.reflect.Type gameListType = new TypeToken<ArrayList<GameDetails>>(){}.getType();
	    List<GameDetails> gameList;
	    File gameHistory = new File("game_history.json");
	    if (!gameHistory.exists()) {
	        try {
				gameHistory.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // This will throw IOException if the file cannot be created
	    }

	    // Load existing game details
	    try (FileReader reader = new FileReader("game_history.json")) {
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
	    details.time = jl.getText();
	    gameList.add(details);

	    // Save updated game details
	    try (FileWriter writer = new FileWriter("game_history.json")) {
	        gson.toJson(gameList, writer);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

    private void setGreenSnakes(JPanel panel) {
        int i1, j1,i2,j2;
        ArrayList<Integer> arr1= new ArrayList<Integer>();
        ArrayList<Integer> arr2= new ArrayList<Integer>();
        do {
            i1 = generateRandomNumber_I(Color.GREEN); // Green snakes
            j1 = generateRandomNumber_J(Color.GREEN);
        	arr1.clear();
            arr1.add(i1);
            arr1.add(j1);
        }while(takenCells.containsKey(arr1) || (i1==9 && j1==0) || (i1==0 && j1==0));
        takenCells.put(arr1,"greensnake1");
        do {  
            i2 = generateRandomNumber_I(Color.GREEN); // Green snakes
            j2 = generateRandomNumber_J(Color.GREEN);
        	arr2.clear();
            arr2.add(i2);
            arr2.add(j2);
        }while(takenCells.containsKey(arr2) || (i2 == i1 && j2 == j1) || (i2==9 && j2==0)  || (i2==0 && j2==0));
        takenCells.put(arr2,"greensnake2");
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        label1.setBounds(squares[i1][j1].getBoundsX(), squares[i1][j1].getBoundsY() + 15, 170, 140);// Green
        label2.setBounds(squares[i2][j2].getBoundsX(), squares[i2][j2].getBoundsY() + 15, 170, 140);// Green
        Square EndSquare1 = findSquare(squares[i1][j1], Color.GREEN);
        Square EndSquare2 = findSquare(squares[i2][j2], Color.GREEN);
        Snake GreenSnake1 = new Snake(squares[i1][j1], EndSquare1);
        Snake GreenSnake2 = new Snake(squares[i2][j2], EndSquare2);
        snakes[4] = GreenSnake1;
        snakes[5] = GreenSnake2;
        label1.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/GSnake.png")));
        label2.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/Gsnake.png")));
        panel.add(label1);
        panel.add(label2);
    }
    
    private void setLadders(JPanel panel) {
        for (int num = 1; num <= 6; num++) {
            setLadder(panel, num);
        }
    }
    private void setLadder(JPanel panel, int num) {
        int i, j;
        Square startSquare=null, endSquare;
        JLabel ladderLabel;
        ArrayList<Integer> arr1= new ArrayList<Integer>();
        ArrayList<Integer> arr2= new ArrayList<Integer>();
        do {
            arr2.clear(); // Clear the list before adding new values
            i = generateRandomIJ(num)[0]; // Generate random row index
            j = generateRandomIJ(num)[1]; // Generate random column index
            arr2.add(i);
            arr2.add(j);
            startSquare = findStartSquare_ladder(squares[i][j], num);
            arr1.clear(); // Clear the list before adding new values
            arr1.add(startSquare.getRow());
            arr1.add(startSquare.getCol());
        } while (takenCells.containsKey(arr1) || (arr1.get(0)==9 && arr1.get(1)==0) );
        takenCells.put(arr1,"startladder"+num);
        takenCells.put(arr2,"endladder"+num);
        //startSquare = findStartSquare_ladder(squares[i][j], num);
        endSquare = squares[i][j];
        ladderLabel = new JLabel();
        Ladder ladder = new Ladder(startSquare, endSquare);
        ladders[num - 1] = ladder;
        // Set ladder image and add it to the panel
        ladderLabel.setBounds((ladder.getSquareEnd().getBoundsX()+ladderCalc(num)[2]), (ladder.getSquareEnd().getBoundsY()+ladderCalc(num)[3]), ladderCalc(num)[0], ladderCalc(num)[1]);
        ladderLabel.setIcon(new ImageIcon(MediumGameBoard.class.getResource("/images/ladder" + num + ".png")));
        panel.add(ladderLabel);
    }
 
    
    private int[] ladderCalc(int num) {
    	int width,heigth,X,Y;
    	int[] clac = new int[4];
        if(num == 1) {
        	width = 110;
        	heigth = 110;
        	X = 0;
        	Y = 0;
        	clac[0] = width;
        	clac[1] = heigth;
        	clac[2] = X;
        	clac[3] = Y;
        }
        if(num == 2) {
        	width = 110;
        	heigth = 165;
        	X = 5;
        	Y = -10;
        	clac[0] = width;
        	clac[1] = heigth;
        	clac[2] = X;
        	clac[3] = Y;
        }
        if(num == 3) {
        	width = 100;
        	heigth = 300;
        	X = -50;
        	Y = -5;
        	clac[0] = width;
        	clac[1] = heigth;
        	clac[2] = X;
        	clac[3] = Y;
        }
        if(num == 4) {
        	width = 115;
        	heigth = 275;
        	X = -75;
        	Y = 0;
        	clac[0] = width;
        	clac[1] = heigth;
        	clac[2] = X;
        	clac[3] = Y;
        }
        if(num == 5) {
        	width = 200;
        	heigth = 330;
        	X = -140;
        	Y = 0;
        	clac[0] = width;
        	clac[1] = heigth;
        	clac[2] = X;
        	clac[3] = Y;
        }
        if(num == 6) {
        	width = 165;
        	heigth = 385;
        	X = -15;
        	Y = 10;
        	clac[0] = width;
        	clac[1] = heigth;
        	clac[2] = X;
        	clac[3] = Y;
        }
        return clac;
    }
    
    private static int generateRandomNumber_I(Color color) { //..-9
        Random random = new Random();
        int num_i = 0;
        if(color == Color.GREEN ) { 
             num_i = random.nextInt(8); //0-7
        }
        if(color == Color.BLUE ){ 
            num_i = random.nextInt(7); //0-6
        }
        if(color == Color.YELLOW ){ 
        	num_i = random.nextInt(9);//0-8
        }
        return num_i; 
    }
    
    
    private static int generateRandomNumber_J(Color color) { //..-9
        Random random = new Random();
        int num_j = 0;
        if(color == Color.GREEN ) { 
             num_j = random.nextInt(9);  //0-8
        }
        if(color == Color.BLUE ){ 
            num_j = random.nextInt(7)+3; //3-9
        }
        if(color == Color.YELLOW ){ 
            num_j = random.nextInt(9); //0-8
        }
        return num_j; 
    }
    
    private static int[] generateRandomIJ(int num) {
    	Random random = new Random();
    	int i,j;
    	int IANDJ[] = new int[2];
    	if(num ==1) {
    		i = random.nextInt(9); //0-8
        	j = random.nextInt(9); //0-8
        	IANDJ[0] = i;
        	IANDJ[1] = j;
    	}
    	if(num ==2) {
    		i = random.nextInt(8);//0-7
        	j = random.nextInt(9);//0-8
        	IANDJ[0] = i;
        	IANDJ[1] = j;
    	}
    	if(num ==3) {
    		i = random.nextInt(7);//0-6
        	j = random.nextInt(9)+1;//1-9
        	IANDJ[0] = i;
        	IANDJ[1] = j;
    	}
    	if(num ==4) {
    		i = random.nextInt(6); //0-5
    		j = random.nextInt(9)+1; //1-9
        	IANDJ[0] = i;
        	IANDJ[1] = j;
    	}
    	if(num ==5) {
    		i = random.nextInt(5); //0-4
    		j = random.nextInt(8)+2; //2-9
        	IANDJ[0] = i;
        	IANDJ[1] = j;
    	}
    	else if(num == 6) {
    		 i = random.nextInt(4);//0-3
   		  	 j = random.nextInt(8);//0-7
             IANDJ[0] = i;
             IANDJ[1] = j;
    	}
    	return IANDJ;
    }
    private static void IntilaizePlayerPositionView(Game g , GameController control,JPanel panel) { //intilaize player position FrontEnd
    	playersLable = new JLabel[g.getPlayers().size()];
    	int[] indexes = new int[2];
		indexes = control.FindSquareByValue(1);   
		int spaceX = 0;          
		int spaceY = 0;          

 
    	for(int i = 0 ; i < playersLable.length ; i++) {
    		if(i == 1) {
    			spaceX= 20;	
    		}
 
    		if(i == 0 || i == 2 ) {
    			spaceX = 0;
    		}
    		if(i == 2 || i == 3) {
    			spaceY =20;
    		}
    		if(i == 1 || i == 3) {
    			spaceX = 20;
    		}

            int x = g.getBoard().getCells()[indexes[0]][indexes[1]].getBoundsX()+spaceX;
            int y = g.getBoard().getCells()[indexes[0]][indexes[1]].getBoundsY()-15 + spaceY ; 
    		playersLable[i] = new JLabel();
    		playersLable[i].setBounds(x,y , 37, 35);
    		if(g.getPlayers().get(i).getColor() == Model.Color.GREEN) {
    			String path = "/images/green-player.png";
                playersLable[i].setIcon(new ImageIcon(MediumGameBoard.class.getResource(path)));
 
    			
    		}
    		if(g.getPlayers().get(i).getColor() == Model.Color.YELLOW) {
    			String path = "/images/yellow-Player.png";
                playersLable[i].setIcon(new ImageIcon(MediumGameBoard.class.getResource(path)));
 
    		}
    		if(g.getPlayers().get(i).getColor() == Model.Color.RED) {
    			String path = "/images/red-player1.png";
                playersLable[i].setIcon(new ImageIcon(MediumGameBoard.class.getResource(path)));
 
    		}
    		if(g.getPlayers().get(i).getColor() == Model.Color.BLUE) {
 
    			String path = "/images/blue-player.png";
                playersLable[i].setIcon(new ImageIcon(MediumGameBoard.class.getResource(path)));

 
    		}
            panel.add(playersLable[i]);
    		panel.setComponentZOrder(playersLable[i], 0);
 
    	}
 
    }

    private Square findSquare(Square StartSquare,Color color) {
    	  for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
            if(color == Color.YELLOW) {
              if(squares[i][j].getBoundsX() == StartSquare.getBoundsX()+ 55 &&squares[i][j].getBoundsY() == StartSquare.getBoundsY()+ 55) {
            	  return squares[i][j];
              }
            }
            if(color == Color.BLUE) {
            	if(squares[i][j].getBoundsX()+110 == StartSquare.getBoundsX() &&squares[i][j].getBoundsY()-165 == StartSquare.getBoundsY()) {
              	  return squares[i][j];
                }
            }
            if(color == Color.GREEN) {
            	if(squares[i][j].getBoundsX() == StartSquare.getBoundsX()+55 &&squares[i][j].getBoundsY() == StartSquare.getBoundsY()+110) {
              	  return squares[i][j];
                }
            }
            }
        }
    	return null;
    }

 
    private Square findStartSquare_ladder(Square square,int number) {
  	  for (int i = 0; i < squares.length; i++) {
          for (int j = 0; j < squares[i].length; j++) {
          if(number == 1) {
            if(squares[i][j].getBoundsX() == square.getBoundsX()+55 &&squares[i][j].getBoundsY() == square.getBoundsY()+ 55) {
          	  return squares[i][j];
            }
          }
          if(number == 2) {
          	if(squares[i][j].getBoundsX() == square.getBoundsX()+55 &&squares[i][j].getBoundsY() == square.getBoundsY()+110) {
            	  return squares[i][j];
              }
          }
          if(number == 3) {
          	if(squares[i][j].getBoundsX() == square.getBoundsX() &&squares[i][j].getBoundsY()-165 == square.getBoundsY()) {
            	  
            	  return squares[i][j];
              }
          }
          if(number == 4) {
          	if(squares[i][j].getBoundsX() == square.getBoundsX()-55 &&squares[i][j].getBoundsY()-220 == square.getBoundsY()) {
              	  return squares[i][j];
                }
          }
          if(number == 5) {
          	if(squares[i][j].getBoundsX() == square.getBoundsX()-110 &&squares[i][j].getBoundsY()-275 == square.getBoundsY()) {
              	  return squares[i][j];
                }
          }
          if(number == 6) {
          	if(squares[i][j].getBoundsX() == square.getBoundsX()+110 &&squares[i][j].getBoundsY()-330 == square.getBoundsY()) {
              	  return squares[i][j];
                }
          }
          }
      }
  	return null;
  }
    
    
    private void updateTextPane(List<Player> arraylistOrderByPosition2) {
        StringBuilder sb = new StringBuilder("<html><body><ul>");
        for (Player p : arraylistOrderByPosition2) {
            sb.append("<li>").append(p.getName() + " - " + p.getPosition()).append("</li>");
        }
        sb.append("</ul></body></html>");
        textPane_1_2_1.setText(sb.toString()); // Update the JTextPane content
        
    }
    
    
    private void animateDiceRoll() {
        final int[] currentNumber = {1}; 
        final int numberOfFaces = 6; 
        final Timer timer = new Timer(100, null); 
        controller.DiceRollingSound();
        timer.addActionListener(new ActionListener() {
            int count = 0;
            int animationCycles = numberOfFaces * 2; 
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (count < animationCycles) {
                    // Update the dice icon to show the next face
                    String path = "/images/dice " + currentNumber[0] + ".jpg";
                    diceButton.setIcon(new ImageIcon(HardGameBoard.class.getResource(path)));
                    currentNumber[0] = currentNumber[0] % numberOfFaces + 1;
                    count++;
                } else {
                    // Animation ends
                    timer.stop();
                     // Re-enable the dice button after animation
                    diceButton.setEnabled(true);
                    // Call method to handle the end of the dice roll, such as updating game state
                   onDiceAnimationEnd();
                }
            }
        });
        diceButton.setEnabled(false); // Disable the dice button during animation
        timer.start(); // Start the animation
    }
    
    private void onDiceAnimationEnd() {
        int result = dice.DiceForMediumGame(); // Simulate the dice roll result
        // Update the dice icon to show the final result
        String path = "/images/dice " + result + ".jpg";
        diceButton.setIcon(new ImageIcon(HardGameBoard.class.getResource(path)));
        boolean flag = false ; 

        
        if(result < 7) {
        	flag=mediumBoard.endGame(index, result, "Dice",playersLable,WinValue,null,controller);
        	  
        } else { 
            controller.DiceQuestion(index ,result,playersLable,WinValue);   
           
        }
        if(flag == true) {
        controller.WiningSound();
       	saveGameDetails(game.getPlayers().get(index));
       	Player winner = game.getPlayers().get(index);
       	gameTimer.stop();
        turnTimer.stop();
        controller.MainSound(false);
        controller.FinalGame(false);
        int count = 0 ; 
        Timer wintime = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MediumGameBoard.this.setVisible(false); 
                ((MediumBoard) mediumBoard).openFrameForWinner(winner,jl.getText(),game);
                
                // Stop the timer after the action is performed once
                ((Timer)e.getSource()).stop();
            }
        });
        wintime.start();

       }else {
    	   
    	     index++;
             if(index >= game.getPlayers().size()) {
                 index = 0;
             }
             
             textPane.setText("\n Turn: " +game.getPlayers().get(index).getName());
     		controller.setPlayerForegroundColor(game.getPlayers().get(index).getColor(),textPane);

             game.setCurrentPlayerIndex(index);
             game.setCurrentPlayer(game.getPlayers().get(index));
             updateTextPane(game.getPlayers());

             
         //	diceButton.setEnabled(false);


       }
  
       
    }
    
    public void startNewTurn() {// Start the 30-second countdown for the player's turn
        isdiceClicked = false ; 
        turnTimer.stop();
        turnTimer.start(); 
    }
    
    public void pauseGame() {
    	elapsedTime = 0 ; 
    	turnElapsedTime = 0 ; 
    	//stop main timer
            gameTimer.stop();
            isGamePaused = true;
            elapsedTime += System.currentTimeMillis() - startTime;
        // stop turn timer
            turnTimer.stop();
            turnElapsedTime+= System.currentTimeMillis() - turnStartTime;     
            
        
    }
    
  

    public void resumeGame() {
    	startTime = 0 ;
    	turnStartTime = 0 ; 
            startTime = System.currentTimeMillis() - elapsedTime;
            gameTimer.start();
            turnStartTime = System.currentTimeMillis() - turnElapsedTime;
            turnTimer.start();
            isGamePaused = false;

        
    }
    
}