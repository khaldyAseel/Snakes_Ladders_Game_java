package Model;

import javax.swing.JLabel;

import Controller.GameController;
import View.BoardEasyViewPlayers;

public class MediumBoard extends BoardLevelTemplate{
	public MediumBoard() {
		super(10);
		BoardLevelTemplate.cells = new Square[10][10];
    	BoardLevelTemplate.snakes = new Snake[6];
    	BoardLevelTemplate.ladders = new Ladder[6];
    	BoardLevelTemplate.questions = new Square[3];
	}


	@Override
	public void startGame(Square[][] cellsformeduim, Snake[] snakesformeduim, Ladder[] laddersformeduim,
			Square[] questionSquares,int number) {
		cells = cellsformeduim;
		snakes = snakesformeduim;
		ladders = laddersformeduim;
		questions = questionSquares;		
	}

	@Override
	public boolean endGame(int index, int result, String type, JLabel[] playerLabel, int WinValue, Game game,
			GameController controller) {
		boolean flag=false;
        flag = controller.updatePlayerPosition(index, result, "Dice",playerLabel,WinValue);
		return flag;
	}

	public void openFrameForWinner(Player winner,String time,Game game)
	{
		WinFrameFactory winframe=new WinFrameFactory();
		switch (winner.getColor()) {
		case RED:
			WinFrame redFrame= winframe.getFrame(Model.Color.RED,winner.getName(),time,game);
			redFrame.createWinFrame(winner.getName(), time, game);
			break;
		case GREEN:
			WinFrame greenFrame= winframe.getFrame(Model.Color.GREEN,winner.getName(),time,game);
			greenFrame.createWinFrame(winner.getName(), time, game);
			break;
		case BLUE:
			WinFrame blueFrame= winframe.getFrame(Model.Color.BLUE,winner.getName(),time,game);
			blueFrame.createWinFrame(winner.getName(),time, game);
			break;
		case YELLOW:
			WinFrame yellowFrame= winframe.getFrame(Model.Color.YELLOW,winner.getName(),time,game);
			yellowFrame.createWinFrame(winner.getName(),time, game);
			break;

		}}

}
