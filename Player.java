import javafx.application.Application;

public class Player {

    // instance variables
    private char colour;
    private String playerName;



    public Player(String playerName, char colour) {
        this.playerName = playerName;
        this.colour = colour;
    }

    public String getPlayerName() {
        return playerName;
    }


    public char getColour() {
        return colour;
    }

    public boolean isTurn(int turn, int playerController){
        return (turn%2 == playerController);
    }
}

