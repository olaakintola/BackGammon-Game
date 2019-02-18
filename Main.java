/*
Main Class
Written by: Shane Byrne, Weronika Wolska, Ola Akintola
Date: 11/02/2019
* */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{

    private Board board = new Board();
    Exit exit = new Exit();


    //Moving secondloop and wait are used for the move method that moves pips around 1 at a time
    private int moving = 1;
    private boolean secondLoop = false;
    private int wait = 0;

    private int textRow = 0;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage){
        //Borderpane is used to format the stage
        BorderPane borderPane = new BorderPane();

        BackgammonController bC = new BackgammonController();
        TextPanel textPanel = new TextPanel();

        borderPane.setCenter(board);
        borderPane.setRight(bC);
        borderPane.setBottom(textPanel);

        //event handler handles inputs
        textPanel.button.setOnAction(e -> {
            if (textPanel.getTextFieldText().equals("move")) {
                move();
            }

            //quits program if quit is entered
            else if (textPanel.getTextFieldText().equals("quit")) {
                exit.exitProgram(primaryStage);
            }

            else if (textPanel.getTextFieldText().equals("flip")){
                board.boardFlip();
            }

            //if none of the other functions are being called it is assumed move is called
            else{
                moveInput(textPanel.getTextFieldText());
            }

            bC.addText(textRow, textPanel.getTextFieldText());
                    textRow++;
                });

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void move(){
        //first if statement moves the black player's pip all around the board
        //once that's completed, secondLoop is set to true and the second if statement is entered
        //which moves the white player's pip all around the board
        if(moving < 24 && secondLoop == false){
            //in instances where the black pip has to go on a stack of white pips it just skips that point
            //the move function from board is called to do the moving
            if(moving + 1 == 6 || moving + 1 == 8 || moving + 1 == 13 || moving + 1 == 24){
                board.move(moving, 2, 'B');
                moving++;
            }
            else{
                board.move(moving, 1, 'B');
            }
            if(moving == 24) secondLoop = true;
            else moving++;
        }

        //moves white pip all around
        if(secondLoop && wait == 1 && moving > 0){
            board.move(moving, -1, 'W');
            moving--;
        }

        //used to delay second panel by one iteration when moving == 24 so that the last black pip can move
        if(secondLoop && wait == 0 && moving > 0){
            wait = 1;
        }
    }

    //takes the player input and moves the pips accordingly
    public void moveInput(String input){
        int point =0, move=0, i=0;
        String firstInt = "";
        String secondInt = "";

        boolean isInt = false;
        boolean isInt2 = false;
        //for loop runs through the input, appending all digits onto the firstInt string and breaking if encounters
        //a non digit character
        for(i=0;i < input.length(); i++){
            //if the position holds an integer it's appended onto firstInt
            if(Character.isDigit(input.charAt(i))){
                isInt = true;
                firstInt += input.charAt(i);
            }

            //if it's a space after an integer then this is accepted
            else if(isInt && input.charAt(i) == ' ') {
                i++;
                break;
            }

            //any other characters are deemed an invalid input
            else{
                isInt=false;
                System.out.println("invalid input");
                break;
            }
        }

        //entered if the first input was valid
        if (isInt){
            //point is set to the int stored in firstInt
            point = Integer.parseInt(firstInt);

            //similar to first int, runs through input from previous position to the end and if any invalid
            //characters are encountered it breaks.
            for(; i < input.length(); i++){
                //digits are appended to secondInt
                if(Character.isDigit(input.charAt(i))){
                    isInt2 = true;
                    secondInt += input.charAt(i);
                }

                //any other characters are invalid inputs
                else{
                    System.out.println("invalid input");
                    isInt2 = false;
                    break;
                }
            }
            //if the second input was valid move is set the secondInt string as an int
            if (isInt2) move = Integer.parseInt(secondInt);

            //if both inputs were valid board.move is called with them
            if(isInt && isInt2) board.move(point, move, 'B');
        }
    }
}
