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

//	private static int player1;
//	private static int player2;
	static Random rand = new Random();
	public static int Player1 =  rand.nextInt(3);  // =3
	public static int Player2 =  rand.nextInt(3);  // =2

	public BackgammonAnnounceWinner() {
	}
	
	public int getPayer1() {
		return Player1;
	}
	
	public int getPlayer2() {
		return Player2;
	}
	
	public boolean isWin(int playerCount) {
		return (playerCount == 0);

	}
	
	public int winner() {
		if(isWin(Player1)) {
			System.out.println(Player1);
			return (1);
		}
		else if(isWin(Player2))
			return (2);
		else
			return(0);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Player1: " +Player1);
		System.out.println("Player2: " +Player2);
		BackgammonAnnounceWinner newGame = new BackgammonAnnounceWinner();
		int player1 = 0;	// temp variables
		int player2 = 0;
		do {
			System.out.println("hello");
			// Code to play game should be here
		}while(player1 != 0 || player2 != 0);
		/**
		 * I should be able to call this three lines at the end of the game or after every move.
		 * Maybe I should initially try after every move and then make it only at the end of the
		 * game when I get it working.
		 * */
		int result = newGame.winner();
		String[] displayResult = {"", "Player1 Wins", "PLayer2 WIns"};
		// I am thinking of including this line in a pop up dialog box to announce the winner.

		System.out.println(displayResult[result]);
		if(Player1 == 0 || Player2 == 0) {
			System.out.println("Ola");
		}    	
	}
}
