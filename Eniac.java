public class Eniac implements BotAPI {

	/*
	* Team Members
	* Weronika Wolska
	* Ola Akintola
	* Shane Byrne
	* */

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the game objects
    // It may only inspect the state of the board and the player objects

    private PlayerAPI me, opponent;
    private BoardAPI board;
    private CubeAPI cube;
    private MatchAPI match;
    private InfoPanelAPI info;
    private Dice dice;

    Eniac(PlayerAPI me, PlayerAPI opponent, BoardAPI board, CubeAPI cube, MatchAPI match, InfoPanelAPI info) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.cube = cube;
        this.match = match;
        this.info = info;
    }

    public String getName() {
        return "Eniac"; // must match the class name
    }

    public String getCommand(Plays possiblePlays) {
        // Add your code here
		String returnString;

		int id = me.getId();

		int numOfPossiblePlays = possiblePlays.number();

		int[] moveScores = new int[numOfPossiblePlays];

		// get all the possible moves, calculate the scores for the moves
        // and store the scores in moveScores array
		for(int i=0;i<numOfPossiblePlays;i++) {
			int[][] boardCopy = board.get();
			Play play = possiblePlays.get(i);
			for(int j=0;j<play.numberOfMoves();j++){
				Move move = play.getMove(j);
				boardCopy[id][move.getFromPip()]--;
				boardCopy[id][move.getToPip()]++;
			}
			moveScores[i] =  1*countHomeCheckers() + 5*blockBlotDifference() - 4*getPipCountDifference();
		}

		//checks if there's an overwhelming advantage, if so the bot doubles. otherwise it just returns the best play
		if(match.canDouble(0) && ((getPipCountDifference() > 20 && blockBlotDifference() > 2) || getPipCountDifference() > 30)){
			returnString = "double";
		}
		else returnString = Integer.toString(findMaxIndex(moveScores)+1);


		return returnString;

    }

    //determines whether or not to accept a double
	public String getDoubleDecision() {
		String doubleDecision;

		//if the game hasn't gone five turns yet the double is always accepted
		if(match.getLength() < 5) doubleDecision = "y";
		//if the pipcountdifference or the blockblotdifference isn't too low the double is accepted
		else if(getPipCountDifference() > -10 || blockBlotDifference() > -1) doubleDecision = "y";
		//otherwise if both the pipcountdifference and the blockblotdifference are poor, say no to the double
		else doubleDecision = "n";

		return doubleDecision;
	}



	//returns number of blocks - number of blots
   public int blockBlotDifference(){
    	int blotNumber = 0;
    	int blockNumber = 0;

    	//loops through the board, checks each point to see if Eniac has pips on point
	   //if there's one pip blot number is incremented
	   //if there's more than one pip block number is incremented
	   for(int i = 1; i<=Board.NUM_PIPS; i++) {
		   int numberOfPips = board.getNumCheckers(me.getId(), i);
		   if(numberOfPips > 1) blockNumber++;
		   if(numberOfPips == 1) blotNumber++;
	   }

	   return blockNumber - blotNumber;
   }


    // This calculates the difference between the distance that my bot has to get to 
    // end of the game has opposed to the distance of the opposing player finishing.
    public int getPipCountDifference() {
        int totalMeNumber = 0;
        int totalOpposingNumber = 0;

        for(int j = 1; j<=Board.NUM_PIPS; j++) {
            int meNumber = board.getNumCheckers(me.getId(), j);
            int numberMultiplybyPipNumber = meNumber*j;
            totalMeNumber += numberMultiplybyPipNumber;
        }


        for(int j =1; j <=Board.NUM_PIPS; j++) {
            int opposingNumber = board.getNumCheckers(opponent.getId(), j);
            int opposingNumberMultiplybyPipNumber = opposingNumber*j;
            totalOpposingNumber += opposingNumberMultiplybyPipNumber;
        }


        int pipDifference = totalMeNumber - totalOpposingNumber ;

        return pipDifference;
    }

   // This gets the number of checkers on each players home board
    public int countHomeCheckers() {

        int totalMeInHome = 0;
        int totalOpponentInHome = 0;

        for(int j = 1; j <=6;j++) {
            totalMeInHome += board.getNumCheckers(me.getId(), j);
        }


        for(int j = 1; j <= 6; j++) {
            totalOpponentInHome += board.getNumCheckers(opponent.getId(), j);
        }

        int differenceHome =  -(totalMeInHome - totalOpponentInHome);

        return differenceHome;

    }

    // find the index of the max number in an array
    private int findMaxIndex(int[] a){
        int max = 0;

        for(int i=1;i<a.length;i++){
            if(a[i]>max) {
                max = i;
            }
        }

        return max;
    }


}
