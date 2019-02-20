import java.util.Random;

public class Dice {

    static Random rand = new Random();
    public int diceValue;

    public Dice(){

    }

    public Dice(int value){
        this.diceValue = value;
    }

    public static int rollDice(){
        int diceValue = rand.nextInt(6);
        diceValue+=1;
        return diceValue;
    }


}
