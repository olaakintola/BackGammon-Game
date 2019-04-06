/*
 * Written by: Ola
 * */

public class Player {

    // instance variables
    private char colour;
    private String playerName;
    private int matchScore;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setColour(char c) {
        this.colour = c;
    }

    public char getColour() {
        return colour;
    }

    //returns whether or not it's the player's turn
    public boolean isTurn(int turn, int playerController){
        return (turn%2 == playerController);
    }

    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }
}

