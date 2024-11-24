package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import javax.naming.spi.DirStateFactory.Result;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Model.Game;
import Model.Player;
import Model.Questions;
import Model.Sound;
import Model.Square;
import Model.SquareType;
import Model.SysData;
import View.HardGameBoard;
import View.MediumGameBoard;
import View.PlayerTurn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameController {
	private Game game;
    private JFrame frame; // Add this attribute to store the instance of MediumGameBoard
    private PlayerTurn playerTurn;
    private Sound  PlaygroundSound = new Sound("sounds/BlueBoyAdventure.wav");
//    private Sound  PlaygroundSound = new Sound("src/sounds/BlueBoyAdventure.wav");

    private Sound FinalSound = new Sound("sounds/FinalBattle.wav");
 //   private Sound FinalSound = new Sound("src/sounds/FinalBattle.wav");
    private boolean musicFlag = false ; 
    public boolean isGameMuted = false ; 
    private boolean flag; 
     public boolean  isFialMusic = false ; 
     public boolean isMainMusic = true ; 
    
	public GameController(Game game , JFrame frame ) {
		super();
		this.game = game;
		this.frame = frame;
	}
	public GameController(Game game  ) {
		super();
		this.game = game;
	}
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	public void CallQuestionDataFunc() {
		SysData.getInstance().LoadQuestions();
		SysData.putQuestions(SysData.questionsPOPUP);
	}
	
	
	public boolean updatePlayerPosition(int index , int result , String type , JLabel[] playerLabel , int WinValue) { // update player position by dice result or by the type of the square 
		int newPosition = 0;
		int[] IAndJ = new int[2];
		Boolean flag = false  ; 
		int count = 0 ; 
		
		if(type.equals("Dice")) {
	     newPosition = game.getPlayers().get(index).getPosition()+result;
	     if(newPosition >= WinValue ) {
	    	 newPosition = WinValue;
	     }
	     game.getPlayers().get(index).setPosition(newPosition);
	     MusicByPoisitionControl(WinValue);
		}
		if(type.equals("Dice Question")) {
			if(result >= WinValue ) {
				result = WinValue;
		     }
		     game.getPlayers().get(index).setPosition(result);
		     count++;
		}
			
		IAndJ = FindSquareByValue(newPosition);
	    	 do {
	    		 if(count == 0 ) {
	    		     animatePlayerMovement(index , playerLabel, game, new Runnable() {
	   		             @Override
	   		             public void run() {
	   		             }
	   		         }); 
	    		     count ++;
	    		 }else {
	    			 int val = game.getPlayers().get(index).getPosition();
	    				if(val >= WinValue ) {
	    					val = WinValue;
	    			     }
	                 IAndJ = FindSquareByValue(val);
		    		    Timer waitTimer = new Timer(2000, e -> {
		                    animatePlayerMovement(index , playerLabel, game, new Runnable() {
		   		             @Override
		   		             public void run() {
		   		             }
		   		         });	
		                });
		                waitTimer.setRepeats(false); // Ensure the timer only triggers once
		                waitTimer.start();
	    		 }
	    		 
	    	
		 }while (checkTheTypeOfTheSquare(index ,IAndJ[0], IAndJ[1], playerLabel , WinValue));
	   if(game.getPlayers().get(index).getPosition() >= WinValue - 15 ) {
		   //System.out.println("its a finallll");
		   musicFlag = true;
	   }
	   
	   if(game.getPlayers().get(index).getPosition() == WinValue ) {
		   flag = true ; 
		   game.getPlayers().get(index).setPosition(WinValue);
		   animatePlayerMovement(index , playerLabel, game, new Runnable() {
		             @Override
		             public void run() {
		             }
		         });
	   }
	   return flag ; 
	}
	public Point boardPositionToPixel(int boardPosition,Player currentPlayer,String path) {
	    int xDiff = 75; // the horizontal distance between squares
	    int yDiff = 75; // vertical distance between squares

	    int row = (boardPosition - 1) / 7;
	    int col = (boardPosition - 1) % 7;

	    int x = 0;
	    int y = 0;
	    Model.Color color = currentPlayer.getColor();
	    if (color.equals(Model.Color.BLUE) && !path.equals("board3")) {
	        x = 290;
	        y = 640;
	    } else if (color.equals(Model.Color.GREEN)  &&!path.equals("board3")) {
	        x = 260;
	        y = 640;
	    } else if (color.equals(Model.Color.RED)  &&!path.equals("board3")) {
	        x = 290;
	        y = 670;
	    } else if (color.equals(Model.Color.YELLOW) &&  !path.equals("board3")) {
	        x = 260;
	        y = 670;
	    }
	 
	    	if (color.equals(Model.Color.BLUE) &&path.equals("board3")) {
		        x = 220;
		        y = 660;
		    } else if (color.equals(Model.Color.GREEN)&&path.equals("board3")) {
		        x = 250;
		        y = 660;
		    } else if (color.equals(Model.Color.RED)&&path.equals("board3")) {
		        x = 220;
		        y = 635;
		    } else if (color.equals(Model.Color.YELLOW)&&path.equals("board3")) {
		        x = 250;
		        y = 635;
		    }
	    


	    x += col * xDiff;
	    y -= row * yDiff;

	    return new Point(x, y);
	}
	
	  public void updatePlayerPosition(int steps,Player currentPlayer) {
	        int currentPosition = currentPlayer.getPosition();
	        int newPosition = currentPosition + steps;
	        if (newPosition <= 0) {
	            newPosition = 1; // Prevent moving beyond the start
	        }

	        currentPlayer.setPosition(newPosition);
          game.getCurrentPlayer().setPosition(newPosition);
  	    currentPlayer.setPosition(newPosition);
  	    game.updatePlayerPositionInList(currentPlayer.getName(), newPosition);
  	    

	    }
	
	public Boolean checkTheTypeOfTheSquare(int playerindex , int i , int j , JLabel[] playerLabel , int Win) { // call the show pop up if the square is a question 
		Square s = game.getBoard().getCells()[i][j];
		Boolean flag = false ; 
		Questions question = null ; 
		int[] Iandj = new int[2];
		if(s.getType() ==  SquareType.QUESTION) {
			if(playerTurn.lastSquareVisit.containsKey(game.getCurrentPlayer()) && playerTurn.lastSquareVisit.get(game.getCurrentPlayer())== s) {
				return false;
			}
			flag = true ; 
			int index = -1 ;
			Square[] q = game.getBoard().getQuestions();
			for (int k = 0 ; k < q.length ; k ++ ) {
				if (q[k] == s) {
					index = k ;
				}
			}
			if(index == 0 ) {
				question = SysData.getQuestionLevel("easy");
			}
			if(index == 1 ) {
				question = SysData.getQuestionLevel("medium");

			}
			if(index == 2 ) {
				question = SysData.getQuestionLevel("hard");

			}
			playerTurn.lastSquareVisit.put(game.getCurrentPlayer(), s);
			Iandj = showAddQuestionPopup(playerindex , question,playerLabel,Win);
			
		}
		
		else if(s.getType() ==  SquareType.SURPRISE) {
			flag = true ; 
			if(game.getCurrentPlayer().getPosition()>10) {
				int  val =game.getCurrentPlayer().getPosition()-10;
				if(Win <=val ) {
					val = Win;
				}
            	game.getCurrentPlayer().setPosition(val);
            	SurpriseSound();
				
			}
			else {
				int  val =game.getCurrentPlayer().getPosition()+10;
				if(Win <=val ) {
					val = Win;
				}
            	game.getCurrentPlayer().setPosition(val);
			}
		}
		
		else {
		
			for(int l = 0 ; l < game.getBoard().getSnakes().length ; l ++ ) {//check if the player in snake square 
                 if(s == game.getBoard().getSnakes()[l].getSquareStart()) {
                	int  val =game.getBoard().getSnakes()[l].getSquareEnd().getValue();
                	if(Win <=val ) {
    					val = Win;
    				}
                	
                	game.getCurrentPlayer().setPosition(val);
                	SnakeSoundEffect();
                	flag = true ; 

                 }
			}
			for(int t = 0 ; t < game.getBoard().getLadders().length ; t ++ ) {//check if the player in snake square 
                if(s == game.getBoard().getLadders()[t].getSquareStart()) {
               	int  val =game.getBoard().getLadders()[t].getSquareEnd().getValue();
               	if(Win <=val ) {
					val = Win;
				}
                animatePlayerMovement(playerindex , playerLabel, game, new Runnable() {
  		             @Override
  		             public void run() {
  		            	LadderSound();

  		             }
  		         });
            	game.getCurrentPlayer().setPosition(val);
               	 flag = true ; 
               

                }
			}
			
			}
		return flag ; 
		
	}
	
	public int[] DiceQuestion(int index ,int result,JLabel[] playerJLabel , int Win) {
		Questions question =null;
		if(result == 7) {
			 question = SysData.getQuestionLevel("easy");
		}
		else if(result == 8) {
			 question = SysData.getQuestionLevel("medium");
		}
		else {
			 question = SysData.getQuestionLevel("hard");
		}
		int[] IandJ = showAddQuestionPopup(index , question, playerJLabel ,Win);
		return IandJ;
		
	}
	
	public int[] FindSquareByValue(int val) {
		int[] IAndJ = new int[2];
		 for (int i = 0; i < game.getBoard().getCells().length; i++) {
	            for (int j = 0; j < game.getBoard().getCells().length; j++) {
	            	if(val == game.getBoard().getCells()[i][j].getValue()) {
	            		IAndJ[0]=i;
	            		IAndJ[1] = j;
	            		break;
	            	}
	            }
	        }
		 return IAndJ;
	}

	public int[] showAddQuestionPopup(int index, Questions question, JLabel[] playerJLabel, int Win) {
        int selectedOption = -1;

        // Create radio buttons for each answer option
        JRadioButton answer1Button = new JRadioButton();
        JRadioButton answer2Button = new JRadioButton();
        JRadioButton answer3Button = new JRadioButton();
        JRadioButton answer4Button = new JRadioButton();

        // Create labels for question, answer options, and difficulty
        JLabel questionField = new JLabel("<html><b>" + question.getQuestionText() + "</b></html>");
        JLabel answer1Field = new JLabel(question.getOptions()[0]);
        JLabel answer2Field = new JLabel(question.getOptions()[1]);
        JLabel answer3Field = new JLabel(question.getOptions()[2]);
        JLabel answer4Field = new JLabel(question.getOptions()[3]);   
        JLabel imageLabel = new JLabel("<html><img src='" + getClass().getResource("/images/question.jpg") + "' width='100' height='100'></body></html>");

        answer1Field.setForeground(java.awt.Color.RED);
        answer2Field.setForeground(java.awt.Color.GREEN);
        answer3Field.setForeground(java.awt.Color.BLUE);
        answer4Field.setForeground(java.awt.Color.ORANGE); 
        
        // Create JPanel for each answer option to include both the label and radio button
        JPanel answer1Panel = new JPanel();
        JPanel answer2Panel = new JPanel();
        JPanel answer3Panel = new JPanel();
        JPanel answer4Panel = new JPanel();

        answer1Panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        answer2Panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        answer3Panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        answer4Panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        answer1Panel.add(answer1Button);
        answer1Panel.add(answer1Field);
        answer2Panel.add(answer2Button);
        answer2Panel.add(answer2Field);
        answer3Panel.add(answer3Button);
        answer3Panel.add(answer3Field);
        answer4Panel.add(answer4Button);
        answer4Panel.add(answer4Field);

        // Add radio buttons to button group
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(answer1Button);
        buttonGroup.add(answer2Button);
        buttonGroup.add(answer3Button);
        buttonGroup.add(answer4Button);

        // Create JPanel for the content including question, answers, and image
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Use BoxLayout to stack components vertically

        // Create JPanel for the question and answers
        JPanel questionAndAnswersPanel = new JPanel();
        questionAndAnswersPanel.setLayout(new GridLayout(5, 1, 0, 0)); // Adjust grid layout to accommodate question and answers with zero vertical gap

        // Add question and answers to the panel
        questionAndAnswersPanel.add(questionField);
        questionAndAnswersPanel.add(answer1Panel);
        questionAndAnswersPanel.add(answer2Panel);
        questionAndAnswersPanel.add(answer3Panel);
        questionAndAnswersPanel.add(answer4Panel);

        // Add question and answers panel to the content panel
        contentPanel.add(questionAndAnswersPanel);
        contentPanel.add(imageLabel);

        // Customize background color and font
        Font labelFont = questionField.getFont();
        questionField.setFont(new Font(labelFont.getName(), Font.BOLD, 20)); // Bold font for question
        answer1Field.setFont(new Font(labelFont.getName(), Font.BOLD, 20)); // Bold font for answers
        answer2Field.setFont(new Font(labelFont.getName(), Font.BOLD, 20)); // Bold font for answers
        answer3Field.setFont(new Font(labelFont.getName(), Font.BOLD, 20)); // Bold font for answers
        answer4Field.setFont(new Font(labelFont.getName(), Font.BOLD, 20)); // Bold font for answers

        // Create and customize the option pane
        JOptionPane optionPane = new JOptionPane(contentPanel, JOptionPane.QUESTION_MESSAGE);
        JDialog dialog = optionPane.createDialog(frame, "Answer Question");

        // Customize the dialog
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Allow closing the dialog
        dialog.setResizable(false); // Disable resizing

        if (frame.getClass().equals(MediumGameBoard.class)) {
            MediumGameBoard.turnTimer.stop();
        } else if (frame.getClass().equals(HardGameBoard.class)) {
            HardGameBoard.turnTimer.stop();
        }

        // Customize the dialog
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Allow closing the dialog
        dialog.setResizable(false); // Disable resizing

        // Create a timer with a delay of 20 seconds
        Timer timer = new Timer(20000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Close the dialog
                countdown(true);
                JOptionPane.showMessageDialog(frame,
                	    "<html><body><p>Time's up! You didn't answer the question.</p>"
                	    + "<img src='" + getClass().getResource("/images/timeout.jpg") + "' width='100' height='100'></body></html>", null, JOptionPane.ERROR_MESSAGE);
            }
            
        });
        timer.setRepeats(false); // Timer should not repeat

        // Start the timer
        timer.start();

        // Show the dialog
        dialog.setVisible(true);

        // Stop the timer when the dialog is closed
        timer.stop();

        Object option = optionPane.getValue();
        // Get the selected answer option
        if (optionPane.getValue() != null && option instanceof Integer) {
            int result = (int) option;
            if (result == JOptionPane.OK_OPTION) {
                if (answer1Button.isSelected()) {
                    selectedOption = 1;
                } else if (answer2Button.isSelected()) {
                    selectedOption = 2;
                } else if (answer3Button.isSelected()) {
                    selectedOption = 3;
                } else if (answer4Button.isSelected()) {
                    selectedOption = 4;
                }
            } else {
                selectedOption = -1;
            }
        }

        // Update player based on the selected answer
        int[] IandJ = updateplayerbyAnswer(index, question, selectedOption, playerJLabel, Win);

        if (frame.getClass().equals(MediumGameBoard.class)) {
            MediumGameBoard.turnTimer.start();
        } else if (frame.getClass().equals(HardGameBoard.class)) {
            HardGameBoard.turnTimer.start();
        }

        return IandJ;
    }

	  public int[] updateplayerbyAnswer(int index ,Questions question,int result, JLabel[] playerLabel , int Win) {
			int[] IAndJ = new int[2];
			//System.out.println("you answerd: "+ result);
		  if(question.getDiffculty() == 1 ) {
			  if((result != question.getCorrectOption() ||  result == -1)  && game.getCurrentPlayer().getPosition()!=1) {
				 wrongAnswer();
				 updatePlayerPosition(game.getCurrentPlayerIndex(), game.getCurrentPlayer().getPosition()-1, "Dice Question",playerLabel , Win );
				 JOptionPane.showMessageDialog(frame,
						    "<html><body><p>You have selected the wrong answer, subsequently you will move to position " + game.getCurrentPlayer().getPosition() + " backward</p>"
						    + "<img src='" + getClass().getResource("/images/wrongAnswer.png") + "' width='100' height='100'></body></html>");
			  }
			  else if(result == question.getCorrectOption()){
				  	 correctAnswer();
					 JOptionPane.showMessageDialog(null,"<html><body><p>You have selected the right answer , sequensly u will stay in your position </p>"
						 		+"<img src='" + getClass().getResource("/images/RightAnswer.gif")+ "' width='100' height='100'></body></html>");
			  }
			  else {
				  	 wrongAnswer();
					 JOptionPane.showMessageDialog(frame,
							    "<html><body><p>You have selected the wrong answer, subsequently you will move to position " + game.getCurrentPlayer().getPosition() + " backward</p>"
							    + "<img src='" + getClass().getResource("/images/wrongAnswer.png") + "' width='100' height='100'></body></html>");
			  }
		
		  }
		  if(question.getDiffculty() == 2) {
			  if((result != question.getCorrectOption() || result ==-1) && game.getCurrentPlayer().getPosition()>=3) {
				wrongAnswer();
				updatePlayerPosition(game.getCurrentPlayerIndex(), game.getCurrentPlayer().getPosition()-2, "Dice Question",playerLabel , Win );
				JOptionPane.showMessageDialog(frame,
					    "<html><body><p>You have selected the wrong answer, subsequently you will move to position " + game.getCurrentPlayer().getPosition() + " backward</p>"
					    + "<img src='" + getClass().getResource("/images/wrongAnswer.png") + "' width='100' height='100'></body></html>");
			  }
			  else if(result == question.getCorrectOption()){
				  	correctAnswer();
					 JOptionPane.showMessageDialog(null,"<html><body><p>You have selected the right answer , sequensly u will stay in your position</p>"
					 		+"<img src='" +getClass().getResource("/images/RightAnswer.gif")+"' width='100' height='100'></body></html> ");
			  }
			  else {
				  wrongAnswer();
				  updatePlayerPosition(game.getCurrentPlayerIndex(), 1, "Dice Question",playerLabel ,Win );
				  JOptionPane.showMessageDialog(frame,
						    "<html><body><p>You have selected the wrong answer, subsequently you will move to position " + game.getCurrentPlayer().getPosition() + " backward</p>"
						    + "<img src='" + getClass().getResource("/images/wrongAnswer.png") + "' width='100' height='100'></body></html>");
			  }
		  }
		  if(question.getDiffculty() == 3) {

			  if(result == question.getCorrectOption()) {
				  correctAnswer();
				  updatePlayerPosition(game.getCurrentPlayerIndex(), game.getCurrentPlayer().getPosition()+1, "Dice Question",playerLabel ,Win );
				  JOptionPane.showMessageDialog(frame,"<html><body><p> You have selected the right answer , sequensly u will move to position "+game.getCurrentPlayer().getPosition()+" forward</p>"
					 		+"<img src='" +getClass().getResource("/images/RightAnswer.gif")+ "' width='100' height='100'></body></html>");
			  }
			  else if((result != question.getCorrectOption() || result ==-1) && game.getCurrentPlayer().getPosition()>=4){
				  wrongAnswer();
					 updatePlayerPosition(game.getCurrentPlayerIndex(), game.getCurrentPlayer().getPosition()-3, "Dice Question",playerLabel ,Win );
					 JOptionPane.showMessageDialog(frame,
							    "<html><body><p>You have selected the wrong answer, subsequently you will move to position " + game.getCurrentPlayer().getPosition() + " backward</p>"
							    + "<img src='" + getClass().getResource("/images/wrongAnswer.png") + "' width='100' height='100'></body></html>");
			  }
			  else {
				  wrongAnswer();
				  updatePlayerPosition(game.getCurrentPlayerIndex(), 1, "Dice Question",playerLabel , Win );
			  JOptionPane.showMessageDialog(frame,"<html><body><p> You have selected the wrong answer , sequensly your position will start from 1</p>"
					    + "<img src='" + getClass().getResource("/images/wrongAnswer.png") + "' width='100' height='100'></body></html>");
			  }
		  } 
		  IAndJ = FindSquareByValue(game.getCurrentPlayer().getPosition());
		  return IAndJ;
	}
	 
	  public void setPlayerBackgroundColor(Model.Color color , JTextField txtrPlayer) {//change the jtext background - by the player color
	        switch (color.toString()) {
	        case "BLUE":
	            txtrPlayer.setBackground(new java.awt.Color(175, 238, 238)); // Blue
	            break;
	        case "GREEN": 
	            txtrPlayer.setBackground(new java.awt.Color(152, 251, 152)); // Green
	            break;
	        case "RED":
	            txtrPlayer.setBackground(new java.awt.Color(255, 51, 102)); // Red
	            break;
	        case "YELLOW":
	            txtrPlayer.setBackground(new java.awt.Color(255, 255, 204)); // Yellow
	            break;
	        default:
	            // Default color for other players
	            txtrPlayer.setBackground(new java.awt.Color(192, 192, 192));
	            break;
	        } };
	        
	  	  public void setPlayerForegroundColor(Model.Color color , JLabel txtrPlayer) {//change the jtext background - by the player color
		        switch (color.toString()) {
		        case "BLUE":
		            txtrPlayer.setForeground(new java.awt.Color(175, 238, 238)); // Blue
		            break;
		        case "GREEN": 
		            txtrPlayer.setForeground(new java.awt.Color(152, 251, 152)); // Green
		            break;
		        case "RED":
		            txtrPlayer.setForeground(new java.awt.Color(255, 51, 102)); // Red
		            break;
		        case "YELLOW":
		            txtrPlayer.setForeground(new java.awt.Color(255, 255, 204)); // Yellow
		            break;
		        default:
		            // Default color for other players
		            txtrPlayer.setForeground(new java.awt.Color(192, 192, 192));
		            break;
		        } };
	        
	    
	
	  
	        public void animatePlayerMovement(int index , JLabel[] playerLabel, Game g, Runnable onAnimationEnd) {
	            int[] iAndJ = FindSquareByValue(game.getPlayers().get(index).getPosition());
	             int targetX= g.getBoard().getCells()[iAndJ[0]][iAndJ[1]].getBoundsX() +10 ;
	             int targetY= g.getBoard().getCells()[iAndJ[0]][iAndJ[1]].getBoundsY()+10 ; 
	             int spaceX = 0 ;
	             int spaceY = 0;
	            int count = 0 ; 
	            for (int i = 0 ; i < game.getPlayers().size() ; i ++ ) {
	            	if(game.getPlayers().get(i).getPosition() ==game.getPlayers().get(index).getPosition() ) {
	            		count ++;
	            	}
	            }
	            
	           if(count >1 ) { // find the player that stay in the same square 
		           JLabel[] newLabels = new JLabel[count] ; 
	        	   ArrayList<Player> players = new ArrayList<Player>();
	        	   int j = 0 ; 
	        	   for (int i = 0 ; i < game.getPlayers().size() ; i ++ ) {
		            	if(game.getPlayers().get(i).getPosition() == game.getPlayers().get(index).getPosition() ) {
		            		newLabels[j] = playerLabel[i];
		            		PositionTwoPlayerorMoreAtTheSquare(i ,newLabels[j] , g , new Runnable() {
		   		             @Override
		   		             public void run() {
		   		             }
		   		         });	 
		            		if(count > j) {
		            			j++ ; 
		            		}
		            	}
		            }
	        	   
	        	
	           }
	           else {
	            //System.out.println("Count :  " + count);
	            final int delay = 50; // Milliseconds between updates
	            final int steps = 15; // Number of steps to reach the target
	            final int startX = playerLabel[index].getX();
	            final int startY = playerLabel[index].getY();
	            final double dx = (double) (targetX - startX) / steps; // Incremental change per step
	            final double dy = (double) (targetY - startY) / steps;
	            
	            final Timer timer = new Timer(delay, null);
	            timer.addActionListener(new ActionListener() {
	                private int currentStep = 0;

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						   if (currentStep < steps) {
		                        playerLabel[index].setLocation((int) (startX + dx * currentStep), (int) (startY + dy * currentStep));
		                        currentStep++;
		                    } else {
		                        playerLabel[index].setLocation(targetX, targetY); // Ensure it ends exactly at the target
		                        SwingUtilities.invokeLater(onAnimationEnd);
		                        timer.stop();
		                        
		                        // onAnimationComplete();
		                    }
		                }						
					});
	            timer.start();
	           }
	        }
	        
	        public void MusicByPoisitionControl(int WinValue) {
	        	for (int i = 0 ; i < game.getPlayers().size() ; i ++) {
	        		if(game.getPlayers().get(i).getPosition() >=WinValue-25 ) {
	        			flag = true ;
	        		}
	        	}
	        	//System.out.println(flag);
	        	if( !isGameMuted) {
	        		if(flag == true) {
	        		MainSound(false);
	        		FinalGame(true);
	        		isFialMusic = true ; 
	        		isMainMusic = false ; 
	        		}
	        		else {
	        			MainSound(true);
		        		FinalGame(false);
		        		isFialMusic = false ; 
		        		isMainMusic = true ; 
	        		}
	        	}
	        	
        		flag = false ; 

	        }
	        

	        public void PositionTwoPlayerorMoreAtTheSquare(int index , JLabel playerLabel, Game g, Runnable onAnimationEnd) {
	     
	            int[] iAndJ = FindSquareByValue(game.getPlayers().get(index).getPosition());
	            int spaceX = 0 ; 
	            int spaceY = 0 ; 
	        	if(index == 1) {
	    			spaceX= 20;	
	    		}
	 
	    		if(index == 0 || index == 2 ) {
	    			spaceX = 0;
	    		}
	    		if(index == 2 || index == 3) {
	    			spaceY =20;
	    		}
	    		if(index == 1 || index == 3) {
	    			spaceX = 20;
	    		}
	             int targetX= g.getBoard().getCells()[iAndJ[0]][iAndJ[1]].getBoundsX() + spaceX ;
	             int targetY= g.getBoard().getCells()[iAndJ[0]][iAndJ[1]].getBoundsY()-15+ spaceY ; 
	        
	            final int delay = 15; // Milliseconds between updates
	            final int steps = 15; // Number of steps to reach the target
	            final int startX = playerLabel.getX();
	            final int startY = playerLabel.getY();
	            final double dx = (double) (targetX - startX) / steps; // Incremental change per step
	            final double dy = (double) (targetY - startY) / steps;
	            
	            final Timer timer = new Timer(delay, null);
	            timer.addActionListener(new ActionListener() {
	                private int currentStep = 0;

					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						   if (currentStep < steps) {
		                        playerLabel.setLocation((int) (startX + dx * currentStep), (int) (startY + dy * currentStep));
		                        currentStep++;
		                    } else {
		                        playerLabel.setLocation(targetX, targetY); // Ensure it ends exactly at the target
		                        SwingUtilities.invokeLater(onAnimationEnd);
		                        timer.stop();
		                    }
		                }						
					});
	            timer.start();
	            
	        }
	        	        	        
	  	        
   public void  MainSound(boolean  status ){
	   if(status == true) {
		PlaygroundSound.setVolume(0.2f); 
        PlaygroundSound.loop();
        FinalSound.stop();
        isMainMusic = true;
	   }
	   else {
		   PlaygroundSound.stop();
		   isMainMusic = false ;
	   }
   }
   
   
   public void FinalGame(boolean f) {
	   if(f == true) {
		FinalSound.setVolume(0.2f); 
        FinalSound.loop();
        PlaygroundSound.stop();
        isFialMusic = true ; 
        }
	   else {
        	FinalSound.stop();
        	isFialMusic = false ; 
        }
   }

   public void  SnakeSoundEffect(){
 		Sound sound = new Sound("sounds/snake-hissing-6092.wav");
// 		Sound sound = new Sound("src/sounds/snake-hissing-6092.wav");

		sound.setVolume(0.5f); 
         sound.play();
         
    }
   
   public void DiceRollingSound() {
//	    Sound sound = new Sound("src/sounds/dice.wav");
	    Sound sound = new Sound("sounds/dice.wav");
		sound.setVolume(0.5f); 
        sound.play();
   }
   public void buttonClick() {
//	   Sound sound = new Sound("src/sounds/buttonClick.wav");
	   Sound sound = new Sound("sounds/buttonClick.wav");
       sound.play();
   }
   
   public void WiningSound() {
	  Sound WinSound = new Sound("sounds/levelup.wav");
//	  Sound WinSound = new Sound("src/sounds/levelup.wav");

	   WinSound.play();

   }
   public void LadderSound() {
	   Sound sound = new Sound("sounds/ladder.wav");

		sound.setVolume(0.5f); 
        sound.play();
   }
   public void SurpriseSound() {
//	   Sound sound = new Sound("src/sounds/surprise.wav");
	   Sound sound = new Sound("sounds/surprise.wav");
		sound.setVolume(0.5f); 
        sound.play();
   }
   
  public void TimeOut () {
//	  Sound sound = new Sound("src/sounds/TimeOut.wav");
	  Sound sound = new Sound("sounds/TimeOut.wav");
		sound.setVolume(0.5f); 
      sound.play();
  }
  
  public void WinngPage() {
//	  Sound sound = new Sound("src/sounds/winfantasia.wav");
	  Sound sound = new Sound("sounds/winfantasia.wav");

		sound.setVolume(0.5f); 
      sound.play();
  }
  
  public void wrongAnswer() {
//	  Sound sound = new Sound("src/sounds/buzzer-or-wrong-answer.wav");
	  Sound sound = new Sound("sounds/buzzer-or-wrong-answer.wav");
		sound.setVolume(0.5f); 
      sound.play();
  }
  
  public void correctAnswer() {
//	  Sound sound = new Sound("src/sounds/goodresult.wav");
	  Sound sound = new Sound("sounds/goodresult.wav");
		sound.setVolume(0.5f); 
      sound.play();
  }
  
  public void countdown(boolean status) {
//	  Sound sound = new Sound("src/sounds/TimeOut.wav");
	  Sound sound = new Sound("sounds/TimeOut.wav");
		sound.setVolume(0.5f); 

	  if(status)
      sound.play();
	  else {
	      sound.stop();

	  }
  }
  
  
  
	 
	
}
