public class Eniac implements BotAPI {

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

		System.out.println("pipCountDifference: " + getPipCountDifference());
		System.out.println("blockBlotDifference: " + blockBlotDifference());

		int id = me.getId();

		int numOfPossiblePlays = possiblePlays.number();

		int[] moveScores = new int[numOfPossiblePlays];

		for(int i=0;i<numOfPossiblePlays;i++) {
			int[][] boardCopy = board.get();
			Play play = possiblePlays.get(i);
			for(int j=0;j<play.numberOfMoves();j++){
				Move move = play.getMove(j);
				boardCopy[id][move.getFromPip()]--;
				boardCopy[id][move.getToPip()]++;
			}
			moveScores[i] = 4*getPipCountDifference() + 1*countHomeCheckers() + 5*blockBlotDifference();
		}

		if(match.canDouble(0) && ((getPipCountDifference() > 20 && blockBlotDifference() > 2) || getPipCountDifference() > 30)){
			returnString = "double";
		}
		else returnString = Integer.toString(findMaxIndex(moveScores)+1);

		return returnString;
    }

	public String getDoubleDecision() {
		String doubleDecision;

		if(match.getLength() < 10) doubleDecision = "y";
		else if(getPipCountDifference() > -10 && blockBlotDifference() > -1) doubleDecision = "y";
		else doubleDecision = "n";

		return doubleDecision;
	}


    public Eniac() {
		// TODO Auto-generated constructor stub

    	//System.out.println(board.getNumCheckers(0, 1));
	}


   public int blockBlotDifference(){
    	int blotNumber = 0;
    	int blockNumber = 0;

	   for(int i = 1; i<=Board.NUM_PIPS; i++) {
		   int numberOfPips = board.getNumCheckers(me.getId(), i);
		   if(numberOfPips > 1) blockNumber++;
		   if(numberOfPips == 1) blotNumber++;
	   }

	   return blockNumber - blotNumber;
   }


    public int getPipCountDifference() {
        int totalMeNumber = 0;
        int totalOpposingNumber = 0;

        //  for(int i = me.getId(); i < Backgammon.NUM_PLAYERS; i++) {
        for(int j = 1; j<=Board.NUM_PIPS; j++) {
            int meNumber = board.getNumCheckers(me.getId(), j);
            int numberMultiplybyPipNumber = meNumber*j;
            totalMeNumber += numberMultiplybyPipNumber;
        }
        //   }
        //System.out.println("totalMeNumber"+ totalMeNumber);


//	   for(int i = opponent.getId(); i < Backgammon.NUM_PLAYERS; i++) {
        for(int j =1; j <=Board.NUM_PIPS; j++) {
            int opposingNumber = board.getNumCheckers(opponent.getId(), j);
            int opposingNumberMultiplybyPipNumber = opposingNumber*j;
            totalOpposingNumber += opposingNumberMultiplybyPipNumber;
        }
        //   }
        //System.out.println("totalopposing" + totalOpposingNumber );


        int pipDifference = totalMeNumber -  totalOpposingNumber ;

        return pipDifference;
    }



    public int countHomeCheckers() {

        int totalMeInHome = 0;
        int totalOpponentInHome = 0;

        for(int j = 1; j <=6;j++) {
            totalMeInHome += board.getNumCheckers(me.getId(), j);
        }
        // You can comment out
        //System.out.println("totalMeInHome: "+totalMeInHome);

        for(int j = 1; j <= 6; j++) {
            totalOpponentInHome += board.getNumCheckers(opponent.getId(), j);
        }

        // You can comment out
        //System.out.println("totalOpponentInHome: "+totalOpponentInHome);

        int differenceHome = totalMeInHome - totalOpponentInHome;

        return differenceHome;

    }

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
