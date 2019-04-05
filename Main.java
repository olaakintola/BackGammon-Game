/*
Main Class
Written by: Shane Byrne, Weronika Wolska, Ola Akintola
Date: 11/02/2019
* */

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Optional;

public class Main extends Application{


    private InformationPanel infoPanel = new InformationPanel();
    private TextPanel textPanel = new TextPanel();

    private Board board = new Board();
    private Exit exit = new Exit();


    //Moving secondloop and wait are used for the move method that moves pips around 1 at a time
    private int moving = 1;
    private boolean secondLoop = false;
    private int wait = 0;

    //used to determine where in the text panel stuff is printed
    private int textRow = 0;

    //determines whether or not dice are on board, dice imageview store dice images
    private boolean diceOnBoard = false;
    private ImageView diceImgView1;
    private ImageView diceImgView2;

    //these are used to track who's turn it is
    private int turn=0;
    private int player1Tracker;
    private int player2Tracker;

    // dice initialisation
    private Dice dice1 = new Dice();
    private Dice dice2 = new Dice();

    //player initialisation
    private Player player1 = new Player();
    private Player player2 = new Player();

    //Borderpane is used to format the stage
    private BorderPane borderPane = new BorderPane();

    private boolean doublesToMove = true;
    private int doubleCounter=0;

    // variable to keep track of the last command entered by the user
    private String prevCommand = null;
    private int doubleValue = 1;

    // if the value is 0, anyone can double
    // if the value is 1, player 1 has the doubling dice
    // if the value is 2, player 2 has the doubling dice
    private int playerWithDoublingDice=0;
    int rolledDice;

    private boolean waitingForDouble = false;

    //qll used in asking for score being played to
    private boolean receivedPlayingToScore = false;
    private boolean waitingForScore = false;
    private int playingTo; //match score that's being played to
    private boolean firstRun = true;



    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage){
        //Announce game is instantiated and use to announce the game
        AnnounceGame newgame = new AnnounceGame();
        AnnounceGame.loadDialog();

        //opens up confirm box when exit window is clicked
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            exit.exitProgram(primaryStage);
        });

        borderPane.setCenter(board);
        borderPane.setRight(infoPanel);
        borderPane.setBottom(textPanel);

        //asks for player 1's name
        infoPanel.addText(0, "");
        textRow++;

        //event handler handles inputs upon enter button press
        textPanel.button.setOnAction(e -> {


            if(waitingForDouble){
                if (textPanel.getTextFieldText().equals("accept")) {
                    doubleValue *= 2;
                    board.boardFlip();
                    infoPanel.addText(textRow, "The player accepted your double.");
                    textRow++;
                    board.updateDoublingCude(doubleValue);
                    if(rolledDice==player1Tracker){
                        playerWithDoublingDice=player2Tracker;
                    } else{
                        playerWithDoublingDice=player1Tracker;
                    }
                } else if (textPanel.getTextFieldText().equals("reject")) {
                    board.boardFlip();
                    infoPanel.addText(textRow, "The player does not want to double. ");
                    textRow++;
                } else {
                    infoPanel.addText(textRow, "Invalid input");
                    textRow++;
                }
            }

            //if player1's name is empty, input becomes that and player 2's name is asked for
            if(player1.getPlayerName()==null){
                player1.setPlayerName(textPanel.getTextFieldText());
                infoPanel.addText(1, " ");
            }


            //if player1's name is not null and player2's name is, player2's name becomes the input
            //the dice is then rolled
            else if(player2.getPlayerName()==null){
                player2.setPlayerName(textPanel.getTextFieldText());
                //after both player names are set we ask what they'll play to
                infoPanel.addText(5, "How many points would you like to play to? Answer must be a number.");
            }

            else if(!receivedPlayingToScore){
                //first run is used to make sure the player2 input isn't used for this question
                if(!firstRun) infoPanel.addText(5, "How many points would you like to play to? Answer must be a number.");
                    for(int i=0; i<textPanel.getTextFieldText().length(); i++) {
                        receivedPlayingToScore=true;
                        if (!Character.isDigit(textPanel.getTextFieldText().charAt(i))) {
                            infoPanel.addText(5, "Not a valid input");
                            receivedPlayingToScore=false;
                            break;
                        }
                    }

                    if(receivedPlayingToScore){

                        waitingForScore = false;
                        playingTo = Integer.parseInt(textPanel.getTextFieldText());
                        infoPanel.addText(5,"You're playing to " + playingTo + " points.");

                        //once playing to score has been established the dice rolls

                        int diceResult1 = dice1.rollDice();
                        int diceResult2 = dice2.rollDice();

                        //playerTracker is used to help determine who's turn it is. There's one for each player and there values
                        //are based on the first roll
                        if(diceResult1>diceResult2){
                            player1Tracker = 0;
                            player2Tracker = 1;
                        } else if(diceResult1<diceResult2){
                            player1Tracker = 1;
                            player2Tracker = 0;

                            // if the two die output the same result, roll again
                        } else{
                            diceResult1 = dice1.rollDice();
                            System.out.println(diceResult1);
                            diceResult2 = dice2.rollDice();
                            System.out.println(diceResult2);
                        }

                        //first player gets the white pip, second player gets the black pip. this is displayed on the info panel
                        if(player1Tracker==0){
                            player1.setColour('W');
                            player2.setColour('B');
                            infoPanel.addText(textRow++, player1.getPlayerName() + " is white. They move first.");
                            infoPanel.addText(textRow++, player2.getPlayerName() + " is black. They move second.");
                        } else {
                            player1.setColour('B');
                            player2.setColour('W');
                            infoPanel.addText(textRow++, player2.getPlayerName() + " is white. They move first.");
                            infoPanel.addText(textRow++, player1.getPlayerName() + " is black. They move second.");
                        }

                        infoPanel.addText(textRow, "Type next to end your turn.");

                        //rolls dice on screen
                        visualRollDice(diceResult1, diceResult2);
                        calculateMoves(diceResult1, diceResult2);
                    }

                    if(firstRun) waitingForScore = true;
            }


            else if (textPanel.getTextFieldText().equals("move")) {
                move();
                prevCommand = "move";
            }

            //quits program if quit is entered
            else if (textPanel.getTextFieldText().equals("quit")) {
                exit.exitProgram(primaryStage);
                prevCommand = "quit";
            }

            else if (textPanel.getTextFieldText().equals("cheat")){
                textPanel.cheatCommand(board);
                prevCommand = "cheat";
            }

            //ends player's turn
            else if (textPanel.getTextFieldText().equals("next")){
                prevCommand = "next";
                turn++;
                board.boardFlip();
                infoPanel.addText(textRow, "Type roll to roll the dice.");
                if (player1.isTurn(turn, player1Tracker)) {
                    String pipColour;
                    if (player1.getColour() == 'B') pipColour = "Black";
                    else pipColour = "White";
                    infoPanel.addText(textRow, "It is " + player1.getPlayerName() + "'s turn. Their colour is " + pipColour);
                }
                else{
                    String pipColour;
                    if (player2.getColour() == 'B') pipColour = "Black";
                    else pipColour = "White";
                    infoPanel.addText(textRow, "It is " + player2.getPlayerName() + "'s turn. Their colour is " + pipColour);
                }
                textRow++;
            }

            /*
                Action event for when user types "double"
                **** Still to do: Keep track of who has the rolling dice ****
             */
            else if (textPanel.getTextFieldText().equals("double")){

                if(turn%2==player1Tracker){
                    rolledDice = player1Tracker;
                } else{
                    rolledDice = player2Tracker;
                }
                if(prevCommand=="roll" || prevCommand=="move" || (playerWithDoublingDice==2) || (turn%2!=rolledDice)  ){
                    infoPanel.addText(textRow,"Doubling is not allowed");
                    textRow++;
                } else {
                    textPanel.textField.setText("");
                    board.boardFlip();
                    infoPanel.addText(textRow, "Would you like to double the stakes?");
                    textRow++;
                    infoPanel.addText(textRow, "Type 'accept' or 'reject'");
                    textRow++;
                    waitingForDouble=true;

                }

            }


            //rolls dice
            else if (textPanel.getTextFieldText().equals("roll")){
                prevCommand = "roll";
                //if there are dice on board already they are removed
                if(diceOnBoard == true){
                    borderPane.getChildren().removeAll(diceImgView1, diceImgView2);
                    diceOnBoard = false;
                }

                int diceResult1 = dice1.rollDice();
                int diceResult2 = dice2.rollDice();

                if(diceResult1 == diceResult2){
                    infoPanel.addText(textRow, "You rolled doubles! You get four moves this turn.");
                    infoPanel.addText(textRow, "Enter your first set of moves and you'll be given another set.");
                    textRow++;
                }
                visualRollDice(diceResult1, diceResult2);
                calculateMoves(diceResult1, diceResult2);
            }

            //if none of the other functions are being called it is assumed a move action is called
            else {
                prevCommand = "move";
                moveInput(textPanel.getTextFieldText());
                board.PipCount();
                if (board.totalWhitePip == 0) {
                    if (player1.getColour() == 'W') {
                        AnnounceGame.WinnerDialog(player1.getPlayerName());
                    } else {
                        AnnounceGame.WinnerDialog(player2.getPlayerName());
                    }
                }

                if (board.totalBlackPip == 0) {
                    if (player1.getColour() == 'B') {
                        AnnounceGame.WinnerDialog(player1.getPlayerName());
                    } else {
                        AnnounceGame.WinnerDialog(player2.getPlayerName());
                    }
                }
            }

            //prints user input to infoPanel, if statement is used for formatting when asking for player names
            if (textRow == 1 || textRow == 4);
            else if(receivedPlayingToScore || !firstRun) infoPanel.addText(textRow, textPanel.getTextFieldText());
            textRow++;
            textPanel.textField.setText(""); //empties input box after enter is pressed
            if(firstRun && waitingForScore) firstRun = false;

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
        boolean isBar = false;
        boolean secondOff=false;


        if(input.length() > 3 && input.charAt(0) == 'B' && input.charAt(1) == 'a' && input.charAt(2) == 'r' && input.charAt(3) == ' '){
            point = 25;
            isInt = true;
            isBar = true;
        }

        else {
            //for loop runs through the input, appending all digits onto the firstInt string and breaking if encounters
            //a non digit character
            for (i = 0; i < input.length(); i++) {
                //if the position holds an integer it's appended onto firstInt
                if (Character.isDigit(input.charAt(i))) {
                    isInt = true;
                    firstInt += input.charAt(i);
                }

                //if it's a space after an integer then this is accepted
                else if (isInt && input.charAt(i) == ' ') {
                    i++;
                    break;
                }

                //any other characters are deemed an invalid input
                else {
                    isInt = false;
                    infoPanel.addText(textRow, "Invalid input. Try again.");
                    textRow++;
                    break;
                }

                point = Integer.parseInt(firstInt);
            }
        }

        if(isBar) i=4;
        if(input.length() == i+3 && input.charAt(i) =='O' && input.charAt(i+1)=='f' && input.charAt(i+2)=='f'){
            secondOff=true;
            isInt2=true;
        }

        //entered if the first input was valid
        if (isInt && !secondOff){

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
                    infoPanel.addText(textRow, "Invalid input. Try again.");
                    textRow++;
                    isInt2 = false;
                    break;
                }
            }

            //if the second input was valid move is set the secondInt string as an int
            if (isInt2) move = Integer.parseInt(secondInt);

            char pipColour;
            pipColour =  board.pointHolder[point].getPipColour();

            //checks if the position exists
            if(point > 25 || point < 0){
                infoPanel.addText(textRow, "There is no point here.");
                textRow++;
                isInt = false;
            }

            //checks if point has any pip
            else if(board.getNumberOfPipsMain(point) <= 0){
                infoPanel.addText(textRow, "There is no pip at this point.");
                textRow++;
                isInt = false;
            }

            move = point - move;
            //if both inputs were valid and the previous checks were true board.move is called

            if(isInt && isInt2) board.move(point, move * -1, pipColour);

        }
        if(secondOff){
            board.removePip(point, board.pointHolder[point].getPipColour());
            board.addBearOff(board.pointHolder[point].getPipColour());
        }
    }

    private void visualRollDice(int diceOneValue, int diceTwoValue){
        //dice images and timelines are instantiated
        Image diceImg1;
        Image diceImg2;
        Timeline rollOne = new Timeline();
        Timeline rollTwo = new Timeline();
        //switch statement gives dice the correct face
        switch (diceOneValue){
            case 1: diceImg1 = new Image(getClass().getResourceAsStream("Die1.png"));
                break;
            case 2: diceImg1 = new Image(getClass().getResourceAsStream("Die2.png"));
                break;
            case 3: diceImg1 = new Image(getClass().getResourceAsStream("Die3.png"));
                break;
            case 4: diceImg1 = new Image(getClass().getResourceAsStream("Die4.png"));
                break;
            case 5: diceImg1 = new Image(getClass().getResourceAsStream("Die5.png"));
                break;
            case 6: diceImg1 = new Image(getClass().getResourceAsStream("Die6.png"));
                break;
            default: diceImg1 = new Image(getClass().getResourceAsStream("Die1.png"));
        }

        diceImgView1 = new ImageView(diceImg1);
        diceImgView1.setFitHeight(50);
        diceImgView1.setFitWidth(50);
        borderPane.getChildren().add(diceImgView1);

        //animation for the left roll
        rollOne.getKeyFrames().addAll(
                new KeyFrame(new Duration( 0), // set start position at 0
                        new KeyValue(diceImgView1.translateXProperty(), 432),
                        new KeyValue(diceImgView1.translateYProperty(), 656)
                ),
                new KeyFrame(new Duration(300), // set end position at 40s
                        new KeyValue(diceImgView1.translateXProperty(), 216),
                        new KeyValue(diceImgView1.translateYProperty(), 300)
                )
        );
        rollOne.play();

        //logic for second roll is same as first
        switch (diceTwoValue){
            case 1: diceImg2 = new Image(getClass().getResourceAsStream("Die1.png"));
                break;
            case 2: diceImg2 = new Image(getClass().getResourceAsStream("Die2.png"));
                break;
            case 3: diceImg2 = new Image(getClass().getResourceAsStream("Die3.png"));
                break;
            case 4: diceImg2 = new Image(getClass().getResourceAsStream("Die4.png"));
                break;
            case 5: diceImg2 = new Image(getClass().getResourceAsStream("Die5.png"));
                break;
            case 6: diceImg2 = new Image(getClass().getResourceAsStream("Die6.png"));
                break;
            default: diceImg2 = new Image(getClass().getResourceAsStream("Die1.png"));
        }

        diceImgView2 = new ImageView(diceImg2);
        diceImgView2.setFitHeight(50);
        diceImgView2.setFitWidth(50);
        borderPane.getChildren().add(diceImgView2);


        rollTwo.getKeyFrames().addAll(
                new KeyFrame(new Duration( 0), // set start position at 0
                        new KeyValue(diceImgView2.translateXProperty(), 432),
                        new KeyValue(diceImgView2.translateYProperty(), 656)
                ),
                new KeyFrame(new Duration(300), // set end position at 40s
                        new KeyValue(diceImgView2.translateXProperty(), 648),
                        new KeyValue(diceImgView2.translateYProperty(), 300)
                )
        );
        rollTwo.play();
        diceOnBoard = true;
    }
    // method to be called when there are no possible moves for the player
    public void noPossibleMoves(){
        System.out.println("no moves available");
        infoPanel.addText(textRow, "No moves available");
        textRow++;
        turn++;
        board.boardFlip();
        infoPanel.addText(textRow, "Type roll to roll the dice.");
        if (player1.isTurn(turn, player1Tracker)) {
            String pipColour;
            if (player1.getColour() == 'B') pipColour = "Black";
            else pipColour = "White";
            infoPanel.addText(textRow, "It is " + player1.getPlayerName() + "'s turn. Their colour is " + pipColour);
        }
        else{
            String pipColour;
            if (player2.getColour() == 'B') pipColour = "Black";
            else pipColour = "White";
            infoPanel.addText(textRow, "It is " + player2.getPlayerName() + "'s turn. Their colour is " + pipColour);
        }
        textRow++;
    }


    public void calculateMoves(int dice1, int dice2) {
        String[] potentialMoves = new String[500];
        boolean deadPipOnBarWhite=false, deadPipOnBarBlack=false;

        int k = 0;
        char pipColour;
        if (player1.isTurn(turn, player1Tracker)) {
            if (player1.getColour() == 'B') pipColour = 'B';
            else pipColour = 'W';
        } else {
            if (player2.getColour() == 'B') pipColour = 'B';
            else pipColour = 'W';
        }

        if (pipColour == 'W') {

            //moving from bar
            if (board.pointHolder[25].getPipColour() == 'W' && board.getNumberOfPipsMain(25) > 0) {
                deadPipOnBarWhite=true;
                int point = 25;
                //dice1 safe
                if (point - dice1 > 0 && (board.getNumberOfPipsMain(point - dice1) == 0 || board.pointHolder[point - dice1].getPipColour() == 'W')) {
                    int currentMove = point - dice1;

                    if (currentMove - dice2 > 0 && (board.getNumberOfPipsMain(currentMove - dice2) == 0 || board.pointHolder[currentMove - dice2].getPipColour() == 'W')) {
                        potentialMoves[k] = "Bar-" + currentMove + " " + currentMove + "-" + (currentMove - dice2);
                        k++;
                        deadPipOnBarWhite=false;
                    }

                    if (currentMove - dice2 > 0 && board.pointHolder[currentMove - dice2].getPipColour() == 'B' && board.getNumberOfPipsMain(currentMove - dice2) == 1) {
                        potentialMoves[k] = "Bar-" + currentMove + " " + currentMove + "-" + (currentMove - dice2) + "*";
                        k++;
                        deadPipOnBarWhite=false;
                    }

                    for (int j = 1; j < 25; j++) {
                        if (board.pointHolder[j].getPipColour() == 'W' && board.getNumberOfPipsMain(j) > 0) {

                            if (j - dice2 > 0 && (board.getNumberOfPipsMain(j - dice2) == 0 || board.pointHolder[j - dice2].getPipColour() == 'W')) {
                                potentialMoves[k] = "Bar-" + currentMove + " " + j + "-" + (j - dice2);
                                k++;
                            }
                            deadPipOnBarWhite=false;

                            if (j - dice2 > 0 && board.pointHolder[j - dice2].getPipColour() == 'B' && board.getNumberOfPipsMain(j - dice2) == 1) {
                                potentialMoves[k] = "Bar-" + currentMove + " " + j + "-" + (j - dice2) + "*";
                                k++;
                            }
                            deadPipOnBarWhite=false;
                        }
                    }
                }

                //dice1 hit
                if (point - dice1 > 0 && board.pointHolder[point - dice1].getPipColour() == 'B' && board.getNumberOfPipsMain(point - dice1) == 1) {
                    int currentMove = point - dice1;

                    if (currentMove - dice2 > 0 && (board.getNumberOfPipsMain(currentMove - dice2) == 0 || board.pointHolder[currentMove - dice2].getPipColour() == 'W')) {
                        potentialMoves[k] = "Bar*-" + currentMove + "* " + currentMove + "-" + (currentMove - dice2);
                        k++;
                        deadPipOnBarWhite=false;
                    }

                    if (currentMove - dice2 > 0 && board.pointHolder[currentMove - dice2].getPipColour() == 'B' && board.getNumberOfPipsMain(currentMove - dice2) == 1) {
                        potentialMoves[k] = "Bar*-" + currentMove + "* " + currentMove + "-" + (currentMove - dice2) + "*";
                        k++;
                        deadPipOnBarWhite=false;
                    }

                    for (int j = 1; j < 25; j++) {

                        if (board.pointHolder[j].getPipColour() == 'W' && board.getNumberOfPipsMain(j) > 0) {

                            if (j - dice2 > 0 && (board.getNumberOfPipsMain(j - dice2) == 0 || board.pointHolder[j - dice2].getPipColour() == 'W')) {
                                potentialMoves[k] = "Bar*-" + currentMove + "* " + j + "-" + (j - dice2);
                                k++;
                                deadPipOnBarWhite=false;
                            }

                            if (j - dice2 > 0 && board.pointHolder[j - dice2].getPipColour() == 'B' && board.getNumberOfPipsMain(j - dice2) == 1) {
                                potentialMoves[k] = "Bar*-" + currentMove + "* " + j + "-" + (j - dice2) + "*";
                                k++;
                                deadPipOnBarWhite=false;
                            }
                        }
                    }
                }

                //dice2 safe
                if (point - dice2 > 0 && (board.getNumberOfPipsMain(point - dice2) == 0 || board.pointHolder[point - dice2].getPipColour() == 'W')) {
                    int currentMove = point - dice2;

                    if (currentMove - dice1 > 0 && (board.getNumberOfPipsMain(currentMove - dice1) == 0 || board.pointHolder[currentMove - dice1].getPipColour() == 'W')) {
                        potentialMoves[k] = "Bar-" + currentMove + " " + currentMove + "-" + (currentMove - dice1);
                        k++;
                        deadPipOnBarWhite=false;
                    }

                    if (currentMove - dice1 > 0 && board.pointHolder[currentMove - dice1].getPipColour() == 'B' && board.getNumberOfPipsMain(currentMove - dice1) == 1) {
                        potentialMoves[k] = "Bar-" + currentMove + " " + currentMove + "-" + (currentMove - dice1) + "*";
                        k++;
                        deadPipOnBarWhite=false;
                    }



                    for (int j = 1; j < 25; j++) {

                        if (board.pointHolder[j].getPipColour() == 'W' && board.getNumberOfPipsMain(j) > 0) {

                            if (j - dice1 > 0 && (board.getNumberOfPipsMain(j - dice1) == 0 || board.pointHolder[j - dice1].getPipColour() == 'W')) {
                                potentialMoves[k] = "Bar-" + currentMove + " " + j + "-" + (j - dice1);
                                k++;
                                deadPipOnBarWhite=false;
                            }

                            if (j - dice1 > 0 && board.pointHolder[j - dice1].getPipColour() == 'B' && board.getNumberOfPipsMain(j - dice1) == 1) {
                                potentialMoves[k] = "Bar-" + currentMove + " " + j + "-" + (j - dice1) + "*";
                                k++;
                                deadPipOnBarWhite=false;
                            }
                        }
                    }
                }

                //dice2 hit
                if (point - dice2 > 0 && board.pointHolder[point - dice2].getPipColour() == 'B' && board.getNumberOfPipsMain(point - dice2) == 1) {
                    int currentMove = point - dice2;

                    if (currentMove - dice1 > 0 && (board.getNumberOfPipsMain(currentMove - dice1) == 0 || board.pointHolder[currentMove - dice1].getPipColour() == 'W')) {
                        potentialMoves[k] = "Bar*-" + currentMove + "* " + currentMove + "-" + (currentMove - dice1);
                        k++;
                        deadPipOnBarWhite=false;
                    }

                    if (currentMove - dice1 > 0 && board.pointHolder[currentMove - dice1].getPipColour() == 'B' && board.getNumberOfPipsMain(currentMove - dice1) == 1) {
                        potentialMoves[k] = "Bar*-" + currentMove + "* " + currentMove + "-" + (currentMove - dice1) + "*";
                        k++;
                        deadPipOnBarWhite=false;
                    }

                    for (int j = 1; j < 25; j++) {

                        if (board.pointHolder[j].getPipColour() == 'W' && board.getNumberOfPipsMain(j) > 0) {

                            if (j - dice1 > 0 && (board.getNumberOfPipsMain(j - dice1) == 0 || board.pointHolder[j - dice1].getPipColour() == 'W')) {
                                potentialMoves[k] = "Bar*-" + currentMove + "* " + j + "-" + (j - dice1);
                                k++;
                                deadPipOnBarWhite=false;
                            }

                            if (j - dice1 > 0 && board.pointHolder[j - dice1].getPipColour() == 'B' && board.getNumberOfPipsMain(j - dice1) == 1) {
                                potentialMoves[k] = "Bar*-" + currentMove + "* " + j + "-" + (j - dice1) + "*";
                                k++;
                                deadPipOnBarWhite=false;
                            }
                        }
                    }
                }
            }

            if(!deadPipOnBarWhite) {

                for (int i = 24; i > 0; i--) {

                    if (board.pointHolder[i].getPipColour() == 'W' && board.getNumberOfPipsMain(i) > 0) {
                        //dice1 safe spot
                        if (i - dice1 > 0 && (board.getNumberOfPipsMain(i - dice1) == 0 || board.pointHolder[i - dice1].getPipColour() == 'W')) {
                            int currentMove = i - dice1;

                            if (currentMove - dice2 > 0 && (board.getNumberOfPipsMain(currentMove - dice2) == 0 || board.pointHolder[currentMove - dice2].getPipColour() == 'W')) {
                                potentialMoves[k] = i + "-" + currentMove + " " + currentMove + "-" + (currentMove - dice2);
                                k++;
                            }

                            if (currentMove - dice2 > 0 && board.pointHolder[currentMove - dice2].getPipColour() == 'B' && board.getNumberOfPipsMain(currentMove - dice2) == 1) {
                                potentialMoves[k] = i + "-" + currentMove + " " + currentMove + "-" + (currentMove - dice2) + "*";
                                k++;
                            }

                            for (int j = i; j < 25; j++) {

                                if (board.pointHolder[j].getPipColour() == 'W' && board.getNumberOfPipsMain(j) > 0 && (j != i || board.getNumberOfPipsMain(j) > 1)) {

                                    if (j - dice2 > 0 && (board.getNumberOfPipsMain(j - dice2) == 0 || board.pointHolder[j - dice2].getPipColour() == 'W')) {
                                        potentialMoves[k] = i + "-" + currentMove + " " + j + "-" + (j - dice2);
                                        k++;
                                    }

                                    if (j - dice2 > 0 && board.pointHolder[j - dice2].getPipColour() == 'B' && board.getNumberOfPipsMain(j - dice2) == 1) {
                                        potentialMoves[k] = i + "-" + currentMove + " " + j + "-" + (j - dice2) + "*";
                                        k++;
                                    }
                                }
                            }
                        }

                        //dice1 hit
                        if (i - dice1 > 0 && board.pointHolder[i - dice1].getPipColour() == 'B' && board.getNumberOfPipsMain(i - dice1) == 1) {
                            int currentMove = i - dice1;

                            if (currentMove - dice2 > 0 && (board.getNumberOfPipsMain(currentMove - dice2) == 0 || board.pointHolder[currentMove - dice2].getPipColour() == 'W')) {
                                potentialMoves[k] = i + "-" + currentMove + "* " + currentMove + "-" + (currentMove - dice2);
                                k++;
                            }

                            if (currentMove - dice2 > 0 && board.pointHolder[currentMove - dice2].getPipColour() == 'B' && board.getNumberOfPipsMain(currentMove - dice2) == 1) {
                                potentialMoves[k] = i + "-" + currentMove + "* " + currentMove + "-" + (currentMove - dice2) + "*";
                                k++;
                            }

                            for (int j = i; j < 25; j++) {

                                if (board.pointHolder[j].getPipColour() == 'W' && board.getNumberOfPipsMain(j) > 0 && (j != i || board.getNumberOfPipsMain(j) > 1)) {

                                    if (j - dice2 > 0 && (board.getNumberOfPipsMain(j - dice2) == 0 || board.pointHolder[j - dice2].getPipColour() == 'W')) {
                                        potentialMoves[k] = i + "-" + currentMove + "* " + j + "-" + (j - dice2);
                                        k++;
                                    }

                                    if (j - dice2 > 0 && board.pointHolder[j - dice2].getPipColour() == 'B' && board.getNumberOfPipsMain(j - dice2) == 1) {
                                        potentialMoves[k] = i + "-" + currentMove + "* " + j + "-" + (j - dice2) + "*";
                                        k++;
                                    }
                                }
                            }
                        }

                        //dice2 safe
                        if (i - dice2 > 0 && (board.getNumberOfPipsMain(i - dice2) == 0 || board.pointHolder[i - dice2].getPipColour() == 'W')) {
                            int currentMove = i - dice2;

                            if (currentMove - dice1 > 0 && (board.getNumberOfPipsMain(currentMove - dice1) == 0 || board.pointHolder[currentMove - dice1].getPipColour() == 'W')) {
                                potentialMoves[k] = i + "-" + currentMove + " " + currentMove + "-" + (currentMove - dice1);
                                k++;
                            }

                            if (currentMove - dice1 > 0 && board.pointHolder[currentMove - dice1].getPipColour() == 'B' && board.getNumberOfPipsMain(currentMove - dice1) == 1) {
                                potentialMoves[k] = i + "-" + currentMove + " " + currentMove + "-" + (currentMove - dice1) + "*";
                                k++;
                            }

                            for (int j = i; j < 25; j++) {

                                if (board.pointHolder[j].getPipColour() == 'W' && board.getNumberOfPipsMain(j) > 0 && (j != i || board.getNumberOfPipsMain(j) > 1)) {

                                    if (j - dice1 > 0 && (board.getNumberOfPipsMain(j - dice1) == 0 || board.pointHolder[j - dice1].getPipColour() == 'W')) {
                                        potentialMoves[k] = i + "-" + currentMove + " " + j + "-" + (j - dice1);
                                        k++;
                                    }

                                    if (j - dice1 > 0 && board.pointHolder[j - dice1].getPipColour() == 'B' && board.getNumberOfPipsMain(j - dice1) == 1) {
                                        potentialMoves[k] = i + "-" + currentMove + " " + j + "-" + (j - dice1) + "*";
                                        k++;
                                    }
                                }
                            }
                        }

                        //dice2 hit
                        if (i - dice2 > 0 && board.pointHolder[i - dice2].getPipColour() == 'B' && board.getNumberOfPipsMain(i - dice2) == 1) {
                            int currentMove = i - dice2;

                            if (currentMove - dice1 > 0 && (board.getNumberOfPipsMain(currentMove - dice1) == 0 || board.pointHolder[currentMove - dice1].getPipColour() == 'W')) {
                                potentialMoves[k] = i + "-" + currentMove + "* " + currentMove + "-" + (currentMove - dice1);
                                k++;
                            }

                            if (currentMove - dice1 > 0 && board.pointHolder[currentMove - dice1].getPipColour() == 'B' && board.getNumberOfPipsMain(currentMove - dice1) == 1) {
                                potentialMoves[k] = i + "-" + currentMove + "* " + currentMove + "-" + (currentMove - dice1) + "*";
                                k++;
                            }

                            for (int j = i; j < 25; j++) {

                                if (board.pointHolder[j].getPipColour() == 'W' && board.getNumberOfPipsMain(j) > 0 && (j != i || board.getNumberOfPipsMain(j) > 1)) {

                                    if (j - dice1 > 0 && (board.getNumberOfPipsMain(j - dice1) == 0 || board.pointHolder[j - dice1].getPipColour() == 'W')) {
                                        potentialMoves[k] = i + "-" + currentMove + "* " + j + "-" + (j - dice1);
                                        k++;
                                    }

                                    if (j - dice1 > 0 && board.pointHolder[j - dice1].getPipColour() == 'B' && board.getNumberOfPipsMain(j - dice1) == 1) {
                                        potentialMoves[k] = i + "-" + currentMove + "* " + j + "-" + (j - dice1) + "*";
                                        k++;
                                    }
                                }
                            }
                        }
                    }
                }

                boolean whiteBearOffReady = true;
                for (int i = 7; i < 26; i++) {
                    if (board.pointHolder[i].getPipColour() == 'W' && board.getNumberOfPipsMain(i) > 0)
                        whiteBearOffReady = false;
                }

                //bear off functionality
                if (whiteBearOffReady) {

                    //dice1 bear off
                    boolean canBearOff = true;
                    for (int j = dice1; j > 0; j--) {
                        if (canBearOff && board.pointHolder[j].getPipColour() == 'W' && board.getNumberOfPipsMain(j) > 0) {
                            canBearOff = false;
                            for (int l = 6; l > 0; l--) {
                                boolean secondMoveExists = false;
                                if (board.pointHolder[l].getPipColour() == 'W' && board.getNumberOfPipsMain(l) > 0) {

                                    if (l - dice2 > 0 && (board.getNumberOfPipsMain(l - dice2) == 0 || board.pointHolder[l - dice2].getPipColour() == 'W')) {
                                        potentialMoves[k] = j + "-Off " + l + "-" + (l - dice2);
                                        k++;
                                        secondMoveExists = true;
                                    }

                                    if (l - dice2 > 0 && board.pointHolder[l - dice2].getPipColour() == 'B' && board.getNumberOfPipsMain(l - dice2) == 1) {
                                        potentialMoves[k] = j + "-Off " + l + "-" + (l - dice2) + "*";
                                        k++;
                                        secondMoveExists = true;
                                    }
                                }

                                boolean nestedCanBearOff = true;
                                for (int i = dice2; i > 0; i--) {
                                    if (nestedCanBearOff && board.pointHolder[i].getPipColour() == 'W' && board.getNumberOfPipsMain(i) > 0 && (i != j || board.getNumberOfPipsMain(i) > 1)) {
                                        nestedCanBearOff = false;
                                        potentialMoves[k] = j + "-Off " + i + "-Off";
                                        k++;
                                        secondMoveExists = true;
                                    }
                                }

                                if (!secondMoveExists && k == 0) {
                                    potentialMoves[k] = j + "-Off ";
                                    canBearOff = false;
                                }
                            }
                        }
                    }

                    //dice2 bear off
                    canBearOff = true;
                    for (int j = dice2; j > 0; j--) {
                        if (canBearOff && board.pointHolder[j].getPipColour() == 'W' && board.getNumberOfPipsMain(j) > 0) {
                            canBearOff = false;
                            for (int l = 6; l > 0; l--) {
                                boolean secondMoveExists = false;
                                if (board.pointHolder[l].getPipColour() == 'W' && board.getNumberOfPipsMain(l) > 0) {

                                    if (l - dice1 > 0 && (board.getNumberOfPipsMain(l - dice1) == 0 || board.pointHolder[l - dice1].getPipColour() == 'W')) {
                                        potentialMoves[k] = j + "-Off " + l + "-" + (l - dice1);
                                        k++;
                                        secondMoveExists = true;
                                    }

                                    if (l - dice1 > 0 && board.pointHolder[l - dice1].getPipColour() == 'B' && board.getNumberOfPipsMain(l - dice1) == 1) {
                                        potentialMoves[k] = j + "-Off " + l + "-" + (l - dice1) + "*";
                                        k++;
                                        secondMoveExists = true;
                                    }
                                }

                                boolean nestedCanBearOff = true;
                                for (int i = dice1; i > 0; i--) {
                                    if (nestedCanBearOff && board.pointHolder[i].getPipColour() == 'W' && board.getNumberOfPipsMain(i) > 0 && (i != j || board.getNumberOfPipsMain(i) > 1)) {
                                        nestedCanBearOff = false;
                                        potentialMoves[k] = j + "-Off " + i + "-Off";
                                        k++;
                                        secondMoveExists = true;
                                    }
                                }

                                if (!secondMoveExists && k == 0) {
                                    potentialMoves[k] = j + "-Off ";
                                    canBearOff = false;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (pipColour == 'B') {
            //moving from bar

            if (board.pointHolder[0].getPipColour() == 'B' && board.getNumberOfPips(25) > 0) {
                deadPipOnBarBlack=true;

                int point = 0;

                if (point + dice1 < 25 && (board.getNumberOfPips(point + dice1) == 0 || board.pointHolder[point + dice1].getPipColour() == 'B')) {
                    int currentMove = point + dice1;

                    if (currentMove + dice2 < 25 && (board.getNumberOfPips(currentMove + dice2) == 0 || board.pointHolder[currentMove + dice2].getPipColour() == 'B')) {
                        potentialMoves[k] = "Bar-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice2].getInverse());
                        k++;
                        deadPipOnBarBlack=false;
                    }

                    if (currentMove + dice2 < 25 && board.pointHolder[currentMove + dice2].getPipColour() == 'W' && board.getNumberOfPips(currentMove + dice2) == 1) {
                        potentialMoves[k] = "Bar-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice2].getInverse() + "*");
                        k++;
                        deadPipOnBarBlack=false;
                    }

                    for (int j = 1; j < 25; j++) {

                        if (board.pointHolder[j].getPipColour() == 'B' && board.getNumberOfPipsMain(j) > 0) {

                            if (j + dice2 < 25 && (board.getNumberOfPips(j + dice2) == 0 || board.pointHolder[j + dice2].getPipColour() == 'B')) {
                                potentialMoves[k] = "Bar-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice2].getInverse());
                                k++;
                                deadPipOnBarBlack=false;
                            }

                            if (j + dice2 < 25 && board.pointHolder[j + dice2].getPipColour() == 'W' && board.getNumberOfPips(j + dice2) == 1) {
                                potentialMoves[k] = "Bar-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice2].getInverse()) + "*";
                                k++;
                                deadPipOnBarBlack=false;
                            }
                        }
                    }
                }

                //dice1 hit
                if (point + dice1 < 25 && board.pointHolder[point + dice1].getPipColour() == 'W' && board.getNumberOfPipsMain(point + dice1) == 1) {
                    int currentMove = point + dice1;

                    if (currentMove + dice2 < 25 && (board.getNumberOfPips(currentMove + dice2) == 0 || board.pointHolder[currentMove + dice2].getPipColour() == 'B')) {
                        potentialMoves[k] = "Bar*-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice2].getInverse());
                        k++;
                        deadPipOnBarBlack=false;
                    }

                    if (currentMove + dice2 < 25 && board.pointHolder[currentMove + dice2].getPipColour() == 'W' && board.getNumberOfPips(currentMove + dice2) == 1) {
                        potentialMoves[k] = "Bar*-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice2].getInverse() + "*");
                        k++;
                        deadPipOnBarBlack=false;
                    }

                    for (int j = 1; j < 25; j++) {

                        if (board.pointHolder[j].getPipColour() == 'B' && board.getNumberOfPipsMain(j) > 0) {

                            if (j + dice2 < 25 && (board.getNumberOfPips(j + dice2) == 0 || board.pointHolder[j + dice2].getPipColour() == 'B')) {
                                potentialMoves[k] = "Bar*-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice2].getInverse());
                                k++;
                                deadPipOnBarBlack=false;
                            }

                            if (j + dice2 < 25 && board.pointHolder[j + dice2].getPipColour() == 'W' && board.getNumberOfPips(j + dice2) == 1) {
                                potentialMoves[k] = "Bar*-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice2].getInverse()) + "*";
                                k++;
                                deadPipOnBarBlack=false;
                            }
                        }
                    }
                }


                //dice2 safe
                if (point + dice2 < 25 && (board.getNumberOfPips(point + dice2) == 0 || board.pointHolder[point + dice2].getPipColour() == 'B')) {
                    int currentMove = point + dice2;

                    if (currentMove + dice1 < 25 && (board.getNumberOfPips(currentMove + dice1) == 0 || board.pointHolder[currentMove + dice1].getPipColour() == 'B')) {
                        potentialMoves[k] = "Bar-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice1].getInverse());
                        k++;
                        deadPipOnBarBlack=false;
                    }

                    if (currentMove + dice1 < 25 && board.pointHolder[currentMove + dice1].getPipColour() == 'W' && board.getNumberOfPips(currentMove + dice1) == 1) {
                        potentialMoves[k] = "Bar-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice1].getInverse() + "*");
                        k++;
                        deadPipOnBarBlack=false;
                    }

                    for (int j = 1; j < 25; j++) {

                        if (board.pointHolder[j].getPipColour() == 'B' && board.getNumberOfPipsMain(j) > 0) {

                            if (j + dice1 < 25 && (board.getNumberOfPips(j + dice1) == 0 || board.pointHolder[j + dice1].getPipColour() == 'B')) {
                                potentialMoves[k] = "Bar-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice1].getInverse());
                                k++;
                                deadPipOnBarBlack=false;
                            }

                            if (j + dice1 < 25 && board.pointHolder[j + dice1].getPipColour() == 'W' && board.getNumberOfPips(j + dice1) == 1) {
                                potentialMoves[k] = "Bar-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice1].getInverse()) + "*";
                                k++;
                                deadPipOnBarBlack=false;
                            }
                        }
                    }
                }

                //dice2 hit
                if (point + dice2 < 25 && board.pointHolder[point + dice2].getPipColour() == 'W' && board.getNumberOfPips(point + dice2) == 1) {
                    int currentMove = point + dice2;

                    if (currentMove + dice1 < 25 && (board.getNumberOfPips(currentMove + dice1) == 0 || board.pointHolder[currentMove + dice1].getPipColour() == 'B')) {
                        potentialMoves[k] = "Bar*-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice1].getInverse());
                        k++;
                        deadPipOnBarBlack=false;
                    }

                    if (currentMove + dice1 < 25 && board.pointHolder[currentMove + dice1].getPipColour() == 'W' && board.getNumberOfPips(currentMove + dice1) == 1) {
                        potentialMoves[k] = "Bar*-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice1].getInverse() + "*");
                        k++;
                        deadPipOnBarBlack=false;
                    }

                    for (int j = 1; j < 25; j++) {

                        if (board.pointHolder[j].getPipColour() == 'B' && board.getNumberOfPips(j) > 0) {

                            if (j + dice1 < 25 && (board.getNumberOfPipsMain(j + dice1) == 0 || board.pointHolder[j + dice1].getPipColour() == 'B')) {
                                potentialMoves[k] = "Bar*-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice1].getInverse());
                                k++;
                                deadPipOnBarBlack=false;
                            }

                            if (j + dice1 < 25 && board.pointHolder[j + dice1].getPipColour() == 'W' && board.getNumberOfPips(j + dice1) == 1) {
                                potentialMoves[k] = "Bar*-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice1].getInverse()) + "*";
                                k++;
                                deadPipOnBarBlack=false;
                            }
                        }
                    }
                }
            }

            if(!deadPipOnBarBlack){


            for (int i = 1; i < 25; i++) {
                if (board.pointHolder[i].getPipColour() == 'B' && board.getNumberOfPipsMain(i) > 0) {

                    //dice1 safe
                    if (i + dice1 < 25 && (board.getNumberOfPipsMain(i + dice1) == 0 || board.pointHolder[i + dice1].getPipColour() == 'B')) {
                        int currentMove = i + dice1;

                        if (currentMove + dice2 < 25 && (board.getNumberOfPipsMain(currentMove + dice2) == 0 || board.pointHolder[currentMove + dice2].getPipColour() == 'B')) {
                            potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice2].getInverse());
                            k++;
                        }

                        if (currentMove + dice2 < 25 && board.pointHolder[currentMove + dice2].getPipColour() == 'W' && board.getNumberOfPipsMain(currentMove + dice2) == 1) {
                            potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice2].getInverse() + "*");
                            k++;
                        }

                        for (int j = i; j < 25; j++) {

                            if (board.pointHolder[j].getPipColour() == 'B' && board.getNumberOfPipsMain(j) > 0 && (j != i || board.getNumberOfPipsMain(j) > 1)) {

                                if (j + dice2 < 25 && (board.getNumberOfPipsMain(j + dice2) == 0 || board.pointHolder[j + dice2].getPipColour() == 'B')) {
                                    potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice2].getInverse());
                                    k++;
                                }

                                if (j + dice2 < 25 && board.pointHolder[j + dice2].getPipColour() == 'W' && board.getNumberOfPipsMain(j + dice2) == 1) {
                                    potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice2].getInverse()) + "*";
                                    k++;
                                }
                            }
                        }
                    }

                    //dice1 hit
                    if (i + dice1 < 25 && board.pointHolder[i + dice1].getPipColour() == 'W' && board.getNumberOfPipsMain(i + dice1) == 1) {
                        int currentMove = i + dice1;

                        if (currentMove + dice2 < 25 && (board.getNumberOfPipsMain(currentMove + dice2) == 0 || board.pointHolder[currentMove + dice2].getPipColour() == 'B')) {
                            potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice2].getInverse());
                            k++;
                        }

                        if (currentMove + dice2 < 25 && board.pointHolder[currentMove + dice2].getPipColour() == 'W' && board.getNumberOfPipsMain(currentMove + dice2) == 1) {
                            potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice2].getInverse() + "*");
                            k++;
                        }

                        for (int j = i; j < 25; j++) {

                            if (board.pointHolder[j].getPipColour() == 'B' && board.getNumberOfPipsMain(j) > 0 && (j != i || board.getNumberOfPipsMain(j) > 1)) {

                                if (j + dice2 < 25 && (board.getNumberOfPipsMain(j + dice2) == 0 || board.pointHolder[j + dice2].getPipColour() == 'B')) {
                                    potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice2].getInverse());
                                    k++;
                                }

                                if (j + dice2 < 25 && board.pointHolder[j + dice2].getPipColour() == 'W' && board.getNumberOfPipsMain(j + dice2) == 1) {
                                    potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice2].getInverse()) + "*";
                                    k++;
                                }
                            }
                        }
                    }

                    //dice2 safe
                    if (i + dice2 < 25 && (board.getNumberOfPipsMain(i + dice2) == 0 || board.pointHolder[i + dice2].getPipColour() == 'B')) {
                        int currentMove = i + dice2;

                        if (currentMove + dice1 < 25 && (board.getNumberOfPipsMain(currentMove + dice1) == 0 || board.pointHolder[currentMove + dice1].getPipColour() == 'B')) {
                            potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice1].getInverse());
                            k++;
                        }

                        if (currentMove + dice1 < 25 && board.pointHolder[currentMove + dice1].getPipColour() == 'W' && board.getNumberOfPipsMain(currentMove + dice1) == 1) {
                            potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice1].getInverse() + "*");
                            k++;
                        }

                        for (int j = i; j < 25; j++) {

                            if (board.pointHolder[j].getPipColour() == 'B' && board.getNumberOfPipsMain(j) > 0 && (j != i || board.getNumberOfPipsMain(j) > 1)) {

                                if (j + dice1 < 25 && (board.getNumberOfPipsMain(j + dice1) == 0 || board.pointHolder[j + dice1].getPipColour() == 'B')) {
                                    potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice1].getInverse());
                                    k++;
                                }

                                if (j + dice1 < 25 && board.pointHolder[j + dice1].getPipColour() == 'W' && board.getNumberOfPipsMain(j + dice1) == 1) {
                                    potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + " " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice1].getInverse()) + "*";
                                    k++;
                                }
                            }
                        }
                    }

                    //dice2 hit
                    if (i + dice2 < 25 && board.pointHolder[i + dice2].getPipColour() == 'W' && board.getNumberOfPipsMain(i + dice2) == 1) {
                        int currentMove = i + dice2;

                        if (currentMove + dice1 < 25 && (board.getNumberOfPipsMain(currentMove + dice1) == 0 || board.pointHolder[currentMove + dice1].getPipColour() == 'B')) {
                            potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice1].getInverse());
                            k++;
                        }

                        if (currentMove + dice1 < 25 && board.pointHolder[currentMove + dice1].getPipColour() == 'W' && board.getNumberOfPipsMain(currentMove + dice1) == 1) {
                            potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[currentMove].getInverse() + "-" + (board.pointHolder[currentMove + dice1].getInverse() + "*");
                            k++;
                        }

                        for (int j = i; j < 25; j++) {


                            if (board.pointHolder[j].getPipColour() == 'B' && board.getNumberOfPipsMain(j) > 0 && (j != i || board.getNumberOfPipsMain(j) > 1)) {

                                if (j + dice1 < 25 && (board.getNumberOfPipsMain(j + dice1) == 0 || board.pointHolder[j + dice1].getPipColour() == 'B')) {
                                    potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice1].getInverse());
                                    k++;
                                }

                                if (j + dice1 < 25 && board.pointHolder[j + dice1].getPipColour() == 'W' && board.getNumberOfPipsMain(j + dice1) == 1) {
                                    potentialMoves[k] = board.pointHolder[i].getInverse() + "-" + board.pointHolder[currentMove].getInverse() + "* " + board.pointHolder[j].getInverse() + "-" + (board.pointHolder[j + dice1].getInverse()) + "*";
                                    k++;
                                }
                            }
                        }
                    }
                }
            }

            boolean blackBearOffReady = true;
            for (int i = 18; i >= 0; i--) {
                if (board.pointHolder[i].getPipColour() == 'B' && board.getNumberOfPips(i) > 0)
                    blackBearOffReady = false;
            }

            //bear off functionality
            if (blackBearOffReady) {

                //dice1 bear off
                boolean canBearOff = true;
                for (int j = 25 - dice1; j < 25; j++) {
                    if (canBearOff && board.pointHolder[j].getPipColour() == 'B' && board.getNumberOfPips(j) > 0) {
                        canBearOff = false;
                        for (int l = 25 - 6; l < 25; l++) {
                            boolean secondMoveExists = false;
                            if (board.pointHolder[l].getPipColour() == 'B' && board.getNumberOfPips(l) > 0) {

                                if (l + dice2 < 25 && (board.getNumberOfPips(l + dice2) == 0 || board.pointHolder[l + dice2].getPipColour() == 'B')) {
                                    potentialMoves[k] = board.pointHolder[j].getInverse() + "-Off " + board.pointHolder[l].getInverse() + "-" + (board.pointHolder[l + dice2].getInverse());
                                    k++;
                                    secondMoveExists = true;
                                    System.out.println(j + " " + l + " j n l" + board.pointHolder[j].getInverse() + " " + board.pointHolder[l].getInverse() + " " + board.pointHolder[22].getInverse());
                                }

                                if (l + dice2 < 25 && board.pointHolder[l + dice2].getPipColour() == 'W' && board.getNumberOfPips(l + dice2) == 1) {
                                    potentialMoves[k] = board.pointHolder[j].getInverse() + "-Off " + board.pointHolder[l].getInverse() + "-" + (board.pointHolder[l + dice2].getInverse()) + "*";
                                    k++;
                                    secondMoveExists = true;
                                }
                            }

                            boolean nestedCanBearOff = true;
                            for (int i = 25 - dice2; i < 25; i++) {
                                if (nestedCanBearOff && board.pointHolder[i].getPipColour() == 'B' && board.getNumberOfPipsMain(i) > 0 && (i != j || board.getNumberOfPips(i) > 1)) {
                                    nestedCanBearOff = false;
                                    potentialMoves[k] = board.pointHolder[j].getInverse() + "-Off " + board.pointHolder[i].getInverse() + "-Off";
                                    k++;
                                    secondMoveExists = true;
                                }
                            }

                            if (!secondMoveExists && k == 0) {
                                potentialMoves[k] = board.pointHolder[j].getInverse() + "-Off ";
                                canBearOff = false;
                            }
                        }
                    }
                }

                //dice2 bear off
                canBearOff = true;
                for (int j = 25 - dice2; j < 25; j++) {
                    if (canBearOff && board.pointHolder[j].getPipColour() == 'B' && board.getNumberOfPips(j) > 0) {
                        canBearOff = false;
                        for (int l = 25 - 6; l < 25; l++) {
                            boolean secondMoveExists = false;
                            if (board.pointHolder[l].getPipColour() == 'B' && board.getNumberOfPips(l) > 0) {

                                if (l + dice1 < 25 && (board.getNumberOfPips(l + dice1) == 0 || board.pointHolder[l + dice1].getPipColour() == 'B')) {
                                    potentialMoves[k] = board.pointHolder[j].getInverse() + "-Off " + board.pointHolder[l].getInverse() + "-" + (board.pointHolder[l + dice1].getInverse());
                                    k++;
                                    secondMoveExists = true;
                                }

                                if (l + dice1 < 25 && board.pointHolder[l + dice1].getPipColour() == 'W' && board.getNumberOfPips(l + dice1) == 1) {
                                    potentialMoves[k] = board.pointHolder[j].getInverse() + "-Off " + board.pointHolder[l].getInverse() + "-" + (board.pointHolder[l + dice1].getInverse()) + "*";
                                    k++;
                                    secondMoveExists = true;
                                }
                            }

                            boolean nestedCanBearOff = true;
                            for (int i = 25 - dice1; i < 25; i++) {
                                if (nestedCanBearOff && board.pointHolder[i].getPipColour() == 'B' && board.getNumberOfPipsMain(i) > 0 && (i != j || board.getNumberOfPips(i) > 1)) {
                                    nestedCanBearOff = false;
                                    potentialMoves[k] = board.pointHolder[j].getInverse() + "-Off " + board.pointHolder[i].getInverse() + "-Off";
                                    k++;
                                    secondMoveExists = true;
                                }
                            }

                            if (!secondMoveExists && k == 0) {
                                potentialMoves[k] = board.pointHolder[j].getInverse() + "-Off ";
                                canBearOff = false;
                            }
                        }
                    }
                }
            }
            }
        }

        if(deadPipOnBarBlack || deadPipOnBarWhite) k=0;


        System.out.println("k: " + k);
        for (k = k - 1; k >= 0; k--) {
            System.out.println(potentialMoves[k]);
        }

        if(k==0){
            noPossibleMoves();
        }

        if(dice1 == dice2 && doublesToMove){
            textPanel.button.setOnAction(e -> {
                calculateMoves(dice1, dice2);
            });
            doublesToMove = false;
        }
        if(dice1==dice2 && doubleCounter==0){
            doublesToMove=true;
            doubleCounter=1;
        }
        else{
            doublesToMove=false;
            doubleCounter=0;
        }

        AnnounceGame menuOption = new AnnounceGame();

        InformationPanel displayMenu = new InformationPanel();
//        displayMenu.printMenu(potentialMoves);

        String[] alphabets = new String[26];
        for (int i = 0; i < 26; i++) {
            alphabets[i] =  (char)('A' + i) + "";
        }

        System.out.println("reach");
        System.out.println("k: "+k);
        for(k=k-1; k>=0; k--){

            System.out.println(potentialMoves[k]);


        }

        String[] finalStringArray = new String[26];
        for(int i=0; i<26;i++) {
            finalStringArray[i] = alphabets[i] +" "+ potentialMoves[i];
        }

        ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
                "one", "two", "three"));
        choiceBox.setPrefWidth(500);

        menuOption.OptionsMenu(finalStringArray);
    }

}

