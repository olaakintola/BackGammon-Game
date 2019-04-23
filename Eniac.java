
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
   	int i = getPipCountDifference();
   	int j = countHomeCheckers();
    	
    	System.out.println(i);
    	System.out.println(j);
    	System.out.println();
        return "1";
    }
    
    
    public Eniac() {
		// TODO Auto-generated constructor stub
    	
    	System.out.println(board.getNumCheckers(0, 1));
	}
    
    // This calculates the difference between the distance that my bot has to get to 
    // end of the game has opposed to the distance of the opposing player finishing.
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
	   System.out.println("totalMeNumber"+ totalMeNumber);

	   
//	   for(int i = opponent.getId(); i < Backgammon.NUM_PLAYERS; i++) {
		   for(int j =1; j <=Board.NUM_PIPS; j++) {
			   int opposingNumber = board.getNumCheckers(opponent.getId(), j);
			   int opposingNumberMultiplybyPipNumber = opposingNumber*j;
			   totalOpposingNumber += opposingNumberMultiplybyPipNumber;
		   }
	//   }
	   System.out.println("totalopposing" + totalOpposingNumber );

	   
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
	   System.out.println("totalMeInHome: "+totalMeInHome);
	   
		   for(int j = 1; j <= 6; j++) {
			   totalOpponentInHome += board.getNumCheckers(opponent.getId(), j); 
		   }
		   
		// You can comment out   
	   System.out.println("totalOpponentInHome: "+totalOpponentInHome);

	int differenceHome = totalMeInHome - totalOpponentInHome;
	   
	return differenceHome;
	   
   }
   
    

    		
    public String getDoubleDecision() {
        // Add your code here
        return "n";
       
    }
}
