import java.util.Random;

import javafx.scene.Scene;
import javafx.stage.Stage;

// Announce Game

// Anywhere you see player1 or player2, it is referring to the number of pips for each player
// that is left on the board

// I can actually use the playerPipCounter in the PointDataType class but the problem is 
// how do I separate the one for black and white?
// I need to find a way to use PointDataType.getPlayerPip. Maybe add the ones of B and W 
// separately each time until I get 0 for either then we call the winner function in 
// BackgammonAccounceWinner

// PipType is another variable that could be of help


public class BackgammonAnnounceWinner {

	public static int PlayerBlackCount =	Board.totalBlackPip; // rand.nextInt(3);  // =3
	public static int PlayerWhiteCount =  Board.totalWhitePip;// rand.nextInt(3);  // =2

	public BackgammonAnnounceWinner() {
	}
	
	public int getPayer1() {
		return PlayerBlackCount;
	}
	
	public int getPlayer2() {
		return PlayerWhiteCount;
	}
	
	public boolean isWin(int playerCount) {
		return (playerCount == 0);

	}
	
	public int winner() {
		if(isWin(PlayerBlackCount)) {
			System.out.println(PlayerBlackCount);
			return (1);
		}
		else if(isWin(PlayerWhiteCount))
			return (2);
		else
			return(0);
	}
}
