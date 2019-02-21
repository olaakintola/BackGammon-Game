/*
    Dice class which includes a method to roll the dice
        Written by Weronika Wolska
 */

import java.util.Random;

public class Dice {

    static Random rand = new Random();
    public int diceValue;


    public Dice(){

    }

    public Dice(int value){
        this.diceValue = value;
    }

    // chooses a random number between 1 and 6 to simulate rolling a dice
    public static int rollDice(){
        int diceValue = rand.nextInt(6);
        diceValue+=1;
        return diceValue;
    }


}
