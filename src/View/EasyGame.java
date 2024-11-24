package View;

import javax.swing.*;

import Model.Board;
import Model.Dice;
import Model.Game;
import Model.Ladder;
import Model.Player;
import Model.Snake;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class EasyGame extends JFrame {
    private JLabel[][] boardLabels;
    private JLabel[] playerLabels;
    private JButton rollDiceButton;
    private JLabel currentPlayerLabel;
    private JLabel timerLabel;
    private Game easygame;
    private Timer gameTimer;
    private int timeElapsed;
    private Player currentPlayer;
  
	public EasyGame(Game game) {
        this.easygame = game;
    }

    
    private void roll(int dice_result) {
    	switch (dice_result) {
		case 1:
			//currentPlayer
			break;

		default:
			break;
		}
    }
}
