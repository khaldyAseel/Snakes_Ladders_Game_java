package Model;

import javax.swing.JLabel;

import Controller.GameController;

public class HardBoard extends BoardLevelTemplate{
	public HardBoard() {
		super(13);
		BoardLevelTemplate.cells = new Square[13][13];
    	BoardLevelTemplate.snakes = new Snake[8];
    	BoardLevelTemplate.ladders = new Ladder[8];
	}
	@Override
	public boolean endGame(int index , int result , String type , JLabel[] playerLabel , int WinValue,Game game,GameController controller) {
		boolean flag=false;
        flag = controller.updatePlayerPosition(index, result, "Dice",playerLabel,WinValue);
		return flag;
	}

	@Override
	public void startGame(Square[][] cells, Snake[] snakes, Ladder[] ladders,
			Square[] questionSquares, int number) {
		BoardLevelTemplate.cells = cells;
		BoardLevelTemplate.snakes = snakes;
		BoardLevelTemplate.ladders = ladders;
		questions = questionSquares;
		
	}

	protected static HardBoard instance = null;
    
//  Singleton Instance
	public static HardBoard getInstance(int size) {
		if (instance == null) {
			instance = new HardBoard();
		}
		return instance;
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
