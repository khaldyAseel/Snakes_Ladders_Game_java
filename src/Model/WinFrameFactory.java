package Model;

import View.BlueWin;
import View.GreenWin;
import View.RedWin;
import View.YellowWin;

public class WinFrameFactory {
	public WinFrame getFrame(Color color, String winner,String time,Game game)
	{
		switch (color) {
		case RED:
			return new RedWin(winner,time,game);
		case GREEN:
			return new GreenWin(winner,time,game);
		case BLUE:
			return new BlueWin(winner,time,game);
		case YELLOW:
		return new YellowWin(winner,time,game);

		} 
		return null;
		
	}

}
