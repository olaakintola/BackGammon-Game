/*
Board Class
Shane Byrne
* */

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

    public class Board extends BorderPane {
        StackPane sp = new StackPane();

        /* four girds are made, one for each section of the board
         * this makes it a lot easier to handle the placement of the pips
         * on the board */
        private GridPane topRightGrid = new GridPane();
        private GridPane topLeftGrid = new GridPane();
        private GridPane bottomLeftGrid = new GridPane();
        private GridPane bottomRightGrid = new GridPane();
        private GridPane barGrid = new GridPane();
        private GridPane topBearOffGrid = new GridPane();
        private GridPane bottomBearOffGrid = new GridPane();

        private int topBearOffGridNumberOfPips = 0;
        private int bottomBearOffGridNumberOfPips = 0;

        private Label topBearOffGridLabel = new Label();
        private Label bottomBearOffGridLabel = new Label();

        //an array of pointDataTypes which is the data type used to store the information of a given point
        //26 are made, one for each point and bar position
        public PointDataType[] pointHolder = new PointDataType[26];

        private GridPane topRightNumbers = new GridPane();
        private GridPane topleftNumbers = new GridPane();
        private GridPane bottomLeftNumbers = new GridPane();
        private GridPane bottomRightNumbers = new GridPane();

        public boolean numbersInInit;

        //following grids are used for positioning
        private GridPane doubleCubeGrid = new GridPane();

        private GridPane player1ScoreGrid = new GridPane();
        private GridPane player1NameGrid = new GridPane();

        private GridPane player2ScoreGrid = new GridPane();
        private GridPane player2NameGrid = new GridPane();

        private Label player1Score = new Label();
        private Label player2Score = new Label();

        private Label player1Name = new Label();
        private Label player2Name = new Label();

        private Image doubleCube;
        private ImageView doubleCubeView;

        private GridPane matchNumberGrid = new GridPane();
        private Label matchNumberVisual = new Label();


    //label array is used when there's an excess number of pips on a point, 26 are made, one for each position
    private Label[] labelArray = new Label[26];

    //label array used to put the numbers on the points
    private Label[] numberLabelArray = new Label[24];

    public Board() {
        /*this stackpane is the root node of the scene
        * it draws the backgammon board and as it's the root all other nodes are drawn on top of it*/
        //getClass().getResource("backgammonBoard.jpg")
        Image img = new Image(getClass().getResourceAsStream("backgammonBoard.jpg"));
        ImageView imgView = new ImageView(img);
        sp.getChildren().add(imgView);
        imgView.setFitHeight(656);
        imgView.setFitWidth(864);

        doubleCube = new Image(getClass().getResourceAsStream("double64.jpg"));
        doubleCubeView = new ImageView(doubleCube);


        Label doublingCubeDoubling = new Label(" Doubling");
        Label doublingCubeCube = new Label("    Cube");
        doublingCubeCube.setTextFill(Color.web("white"));
        doublingCubeDoubling.setTextFill(Color.web("white"));


        GridPane.setConstraints(doublingCubeDoubling, 0, 0);
        GridPane.setConstraints(doubleCubeView, 0, 1);
        GridPane.setConstraints(doublingCubeCube, 0, 2);

        doubleCubeGrid.getChildren().addAll(doubleCubeView, doublingCubeCube, doublingCubeDoubling);
        doubleCubeGrid.setPadding(new Insets(280, 0, 0, 15));

        boardInitialize(); //call to method to initialize the board state
        drawNumbers();

        sp.getChildren().addAll(topLeftGrid, topRightGrid, bottomLeftGrid, bottomRightGrid, barGrid, player1NameGrid, player2NameGrid, matchNumberGrid,
                topRightNumbers, topleftNumbers, bottomLeftNumbers, bottomRightNumbers, doubleCubeGrid, player1ScoreGrid, player2ScoreGrid, topBearOffGrid, bottomBearOffGrid);
        setCenter(sp);
    }

    private void boardInitialize(){

        //labels used to set match number in center of board
        Label matchNumberMatch = new Label(" Match");
        matchNumberMatch.setTextFill(Color.web("white"));
        Label matchNumberLength = new Label("Number");
        matchNumberLength.setTextFill(Color.web("white"));
        matchNumberVisual.setText(" 0");
        matchNumberVisual.setTextFill(Color.web("black"));

        matchNumberVisual.setStyle("-fx-font: normal bold 50px 'sans-serif' ");

        GridPane.setConstraints(matchNumberMatch, 0, 0);
        GridPane.setConstraints(matchNumberVisual, 0, 1);
        GridPane.setConstraints(matchNumberLength, 0, 2);

        matchNumberGrid.setPadding(new Insets(267, 0, 0, 408));
        matchNumberGrid.setVgap(3);
        matchNumberGrid.getChildren().addAll(matchNumberLength, matchNumberMatch, matchNumberVisual);

        //doubleCube is initialised as the 64 cube
        doubleCube = new Image(getClass().getResourceAsStream("double64.jpg"));
        doubleCubeView = new ImageView(doubleCube);

        //labels are used for visual indication of doubling cube on board
        Label doublingCubeDoubling = new Label(" Doubling");
        Label doublingCubeCube = new Label("    Cube");
        doublingCubeCube.setTextFill(Color.web("white"));
        doublingCubeDoubling.setTextFill(Color.web("white"));

        //grid constraints for double cube constituents are set
        GridPane.setConstraints(doublingCubeDoubling, 0, 0);
        GridPane.setConstraints(doubleCubeView, 0, 1);
        GridPane.setConstraints(doublingCubeCube, 0, 2);

        doubleCubeGrid.getChildren().addAll(doubleCubeView, doublingCubeCube, doublingCubeDoubling);
        doubleCubeGrid.setPadding(new Insets(280, 0, 0, 15));


        //Dimensions for the pips bearing off are set here
        topBearOffGrid.setPadding(new Insets(23, 0, 0, 795));
        bottomBearOffGrid.setPadding(new Insets(590, 0, 0, 795));


        //Positions the player name and score, sets the color and font and then initialises the respective players to player1 and player2
        //with scores of 0
        player1NameGrid.setPadding(new Insets(40, 0, 0, 20));
        setPlayerScore(1, "Player1", 0);
        player1Score.setTextFill(Color.web("black"));
        player1Name.setTextFill(Color.web("white"));
        player1Score.setStyle("-fx-font: normal bold 50px 'sans-serif' ");

        player2NameGrid.setPadding(new Insets(475, 0, 0, 20));
        setPlayerScore(2, "Player2", 0);
        player2Score.setTextFill(Color.web("black"));
        player2Name.setTextFill(Color.web("white"));
        player2Score.setStyle("-fx-font: normal bold 50px 'sans-serif' ");

        //insets and horizontal gaps are set here, these ensure the pips are displayed in the correct positions
        topRightGrid.setPadding(new Insets(23, 80, 50, 483));
        topRightGrid.setHgap(4);

        topLeftGrid.setPadding(new Insets(23, 0, 0, 100));
        topLeftGrid.setHgap(4);

        bottomLeftGrid.setPadding(new Insets(412, 0, 0, 100));
        bottomLeftGrid.setHgap(4);

        bottomRightGrid.setPadding(new Insets(412, 0, 0, 483));
        bottomRightGrid.setHgap(4);

        barGrid.setPadding(new Insets(200, 0, 0, 411));
        barGrid.setVgap(170);

        int temp = 6; //a temporary variable used in the for loops
        /*BOTTOM RIGHT GRID */

        /*
        the display works in a way that every point on the grid has to have something in it
        for the display to work properly. as such this next for loop sets the first row for every
        column in the bottom right grid as a placeholder pip which is invisible. these are cleared
        when actual pips are added into the columns, but every empty column needs to have one.
        each grid has one of these initialiser for loops and they all work in the same way
        */

        for(int i=0; i<6; i++){
            //this adds a point to the array, its position in the array is the same as its position on the board
            //i.e. the 6th point would be pointHolder[6]
            pointHolder[temp] = new PointDataType();
            //placeholder pip is pushed into the point and then displayed on the grid
            pointHolder[temp].push(new Pip('P'));
            GridPane.setConstraints(pointHolder[temp].peek(), i, 0);
            bottomRightGrid.getChildren().add(pointHolder[temp].peek());
            //each point has its own column index that is set here
            pointHolder[temp].setColumnIndex(i);
            temp--;
        }

        //addPip method is used to add 5 white pips to the 6th point
        for (int i=0; i< 5; i++){
            addPip(6, 'W');
        }

        //addPip method is used to add 3 black pips to the 1st point
        for(int i=0; i<2; i++){
            addPip(1, 'B');
        }

        /*BOTTOM LEFT GRID*/
        temp = 12;
        for(int i=0; i<6; i++){
            pointHolder[temp] = new PointDataType();
            for (int j=0; j<5; j++){
                pointHolder[temp].push(new Pip('P'));
                GridPane.setConstraints(pointHolder[temp].peek(), i, j);
                bottomLeftGrid.getChildren().add(pointHolder[temp].peek());
            }
            pointHolder[temp].setColumnIndex(i);
            temp--;
        }

        for(int i=0; i<5; i++){
            addPip(12, 'B');
        }

        for(int i=0;i<3;i++){
            addPip(8, 'W');
        }

        //TOP LEFT GRID
        temp = 18;
        for(int i=0; i<6; i++){
            pointHolder[temp] = new PointDataType();
            pointHolder[temp].push(new Pip('P'));
            GridPane.setConstraints(pointHolder[temp].peek(), 5-i, 0);
            topLeftGrid.getChildren().add(pointHolder[temp].peek());
            pointHolder[temp].setColumnIndex(5-i);
            temp--;
        }

        for(int i=0; i<5;i++){
            addPip(13, 'W');
        }

        for(int i=0; i<3; i++){
            addPip( 17, 'B');
        }


        //TOP RIGHT GRID
        temp = 24;
        for(int i=5; i>=0; i--){
            pointHolder[temp] = new PointDataType();
            pointHolder[temp].push(new Pip('P'));
            GridPane.setConstraints(pointHolder[temp].peek(), i, 0);
            topRightGrid.getChildren().add(pointHolder[temp].peek());
            pointHolder[temp].setColumnIndex(i);
            temp--;
        }

        for(int i=0; i<5; i++){
            addPip(19, 'B');
        }

        for(int i=0; i<2; i++){
            addPip(24, 'W');
        }

        //bar pips are added in here

        pointHolder[0] = new PointDataType();
        pointHolder[25] = new PointDataType();

        pointHolder[25].push(new Pip('P'));
        GridPane.setConstraints(pointHolder[25].peek(), 0, 0);
        barGrid.getChildren().add(pointHolder[25].peek());
        pointHolder[25].setColumnIndex(0);

        pointHolder[0].push(new Pip('P'));
        GridPane.setConstraints(pointHolder[0].peek(), 0, 1);
        barGrid.getChildren().add(pointHolder[0].peek());
        pointHolder[0].setColumnIndex(1);

        pointHolder[0].setInverse(25);
        pointHolder[25].setInverse(0);

        int j=24;
        for(int i=1; i<25; i++){
            pointHolder[i].setInverse(j);
            j--;
        }
    }

    /*
    Modify the `cheat’ command so that the board goes to a position where all of the checkers have
        been bore off except that both players should have two checkers on their own ace points.
     */
        // changes board display when "cheat" as a text command
        public void cheatBoard(){

            /* REMOVE ALL PIPS FROM ORIGINAL BOARD HERE */
            for(int i=1;i<=24;i++){
                int numPips = getNumberOfPips(i);
                if(numPips>0){
                    for(int j=0;j<numPips;j++){
                        removePip(i,pointHolder[i].getPipColour());
                    }
                }
            }

        /*
            Adding white checkers to the bottom right grid to
            the positions specified in Sprint 3 text
         */

            for (int i=0; i< 2; i++){
                addPip(1, 'W');
            }

            for (int i=0; i<13;i++){
                addBearOff('W');
            }


        /*
              Adding black checkers to the top right grid to the
              positions specified in the sprint 3 notes
         */

            for(int i=0;i<2;i++){
                addPip(24, 'B');
            }

            for (int i=0; i<13;i++){
                addBearOff('B');
            }



        }

    /*
    This method is used to move a pip from one point to another
    It takes in the pip's starting position, the amount it's moving by and its colour
    It works by calling removePip() to remove the pip at starting position and then calling
    addPip() to add a pip at its final position
    * */
    public void move(int startingPosition, int moveAmount, char pipColour){
        int finalPos = startingPosition + moveAmount;

        if(numbersInInit){
            //checks if a hit will take place
            if(pointHolder[finalPos].getPlayerPip() == 1 && pointHolder[finalPos].getPipColour() != pipColour){
                hitPip(startingPosition, finalPos, pipColour);
            }

            //if checks if the move will take pip off the board, if so only remove is called
            else if(startingPosition + moveAmount < 0){
                removePip(startingPosition, pipColour);
            }

            else if(finalPos == 0){
                removePip(startingPosition, pipColour);
            }

            //otherwise remove and add pip methods are called
            else{
                removePip(startingPosition, pipColour);
                addPip(finalPos, pipColour);
            }
        }

        else{
            startingPosition = pointHolder[startingPosition].getInverse();
            pipColour = pointHolder[startingPosition].getPipColour();
            finalPos = startingPosition - moveAmount;

            if(pointHolder[finalPos].getPlayerPip() == 1 && pointHolder[finalPos].getPipColour() != pipColour){
                hitPip(startingPosition, finalPos, pipColour);
            }

            else if(startingPosition + moveAmount > 25){
                removePip(startingPosition, pipColour);
            }

            else if(finalPos == 25){
                removePip(startingPosition, pipColour);
            }

            else{
                removePip(startingPosition, pipColour);
                addPip(finalPos, pipColour);
            }
        }
    }

    //method used by board initialize to add pips to the points
    /*
    This method is used to add a pip of a given color to a given point.
    If the point already has 5 pips, a label is shown on the 5th pip stating how
    many pips that position has.
    */
    private void addPip(int pointNumber, char pipColour){
        //sets the pip colour for the point if it's empty before adding
        if(pointHolder[pointNumber].getPlayerPip() == 0){
            pointHolder[pointNumber].setPipColour(pipColour);
        }

        //different statements are required for each grid as the top and bottom are drawn differently and they
        //each have different gridpanes as parents
        if(pointNumber == 0 || pointNumber == 25){
            pointHolder[pointNumber].pipCounterAdder();
            int numberOfPips = pointHolder[pointNumber].getPlayerPip(); //No. of player pips in current pointer
            if(numberOfPips > 1){
                barGrid.getChildren().remove(labelArray[pointNumber]);

                int labelText = numberOfPips;
                labelArray[pointNumber] = new Label( labelText + " Pips");
                GridPane.setConstraints(labelArray[pointNumber], 0, pointHolder[pointNumber].getColumnIndex());
                barGrid.getChildren().add(labelArray[pointNumber]);
                //red is used as the color as its visible over both red and white pips
                labelArray[pointNumber].setTextFill(Color.web("red"));
            }

            else{
                barGrid.getChildren().remove(pointHolder[pointNumber].peek());

                pointHolder[pointNumber].push(new Pip(pipColour));
                GridPane.setConstraints(pointHolder[pointNumber].peek(), 0, pointHolder[pointNumber].getColumnIndex());
                barGrid.getChildren().add(pointHolder[pointNumber].peek());
            }
        }


        if(pointNumber <= 12 && pointNumber > 0){

            pointHolder[pointNumber].pipCounterAdder();
            int numberOfPips = pointHolder[pointNumber].getPlayerPip(); //No. of player pips in current pointer

            if(pointNumber > 6){
                //this if statement checks if there are already 5 pips at the position
                //if there are a label is added at the 5th position
                if(numberOfPips > 5){
                    bottomLeftGrid.getChildren().remove(labelArray[pointNumber]);
                    //number of pips - 4 is the amount of pips at the 5th position
                    int labelText = numberOfPips - 4;

                    labelArray[pointNumber] = new Label( labelText + " Pips");
                    GridPane.setConstraints(labelArray[pointNumber], pointHolder[pointNumber].getColumnIndex(), 0);
                    bottomLeftGrid.getChildren().add(labelArray[pointNumber]);
                    //red is used as the color as its visible over both red and white pips
                    labelArray[pointNumber].setTextFill(Color.web("red"));
                }

                //entered if there aren't 5 or more pips
                //due to the way the bottom grid is drawn it's easiest to display a new pip by emptying the point and then
                //redrawing the whole thing with the new pip
                else{
                    //this for loop empties the point and removes its previous pips from the board
                    for(int i=0; i < pointHolder[pointNumber].size(); i++){
                        bottomLeftGrid.getChildren().remove(pointHolder[pointNumber].peek());
                        pointHolder[pointNumber].pop();
                    }

                    //placeholder pips are put in the positions that don't have player pips, this is because the grid draws
                    //downwards and so to display, say 3 pips, position 4 and 5 must be filled first. the placeholder pips do this
                    for(int i=0; i < 5 - numberOfPips; i++){
                        pointHolder[pointNumber].push(new Pip('P'));
                        GridPane.setConstraints(pointHolder[pointNumber].peek(), pointHolder[pointNumber].getColumnIndex(), i);
                        bottomLeftGrid.getChildren().add(pointHolder[pointNumber].peek());
                    }

                    //player pips are then drawn into their positions
                    for(int i= 5 - numberOfPips; i < 5 ; i++){
                        pointHolder[pointNumber].push(new Pip(pipColour));
                        GridPane.setConstraints(pointHolder[pointNumber].peek(), pointHolder[pointNumber].getColumnIndex(), i);
                        bottomLeftGrid.getChildren().add(pointHolder[pointNumber].peek());
                    }
                }
            }

            //the logic here is the same as for the bottomLeftGrid above
            if(pointNumber <7){

                if(numberOfPips > 5){
                    bottomRightGrid.getChildren().remove(labelArray[pointNumber]);

                    int labelText = numberOfPips - 4;

                    labelArray[pointNumber] = new Label( labelText + " Pips");
                    GridPane.setConstraints(labelArray[pointNumber], pointHolder[pointNumber].getColumnIndex(), 0);
                    bottomRightGrid.getChildren().add(labelArray[pointNumber]);
                    labelArray[pointNumber].setTextFill(Color.web("red"));
                }

                else {
                    for(int i=0; i < pointHolder[pointNumber].size(); i++){
                        bottomRightGrid.getChildren().remove(pointHolder[pointNumber].peek());
                        pointHolder[pointNumber].pop();
                    }

                    for(int i=0; i < 5 - numberOfPips; i++){
                        pointHolder[pointNumber].push(new Pip('P'));
                        GridPane.setConstraints(pointHolder[pointNumber].peek(), pointHolder[pointNumber].getColumnIndex(), i);
                        bottomRightGrid.getChildren().add(pointHolder[pointNumber].peek());
                    }

                    for(int i= 5 - numberOfPips; i < 5 ; i++){
                        pointHolder[pointNumber].push(new Pip(pipColour));
                        GridPane.setConstraints(pointHolder[pointNumber].peek(), pointHolder[pointNumber].getColumnIndex(), i);
                        bottomRightGrid.getChildren().add(pointHolder[pointNumber].peek());
                    }
                }
            }
        }

        if(pointNumber > 12 && pointNumber < 25){

            pointHolder[pointNumber].pipCounterAdder();
            int numberOfPips = pointHolder[pointNumber].getPlayerPip();

            if(pointNumber < 19){
                //label logic is the same as above
                if(numberOfPips > 5){
                    topLeftGrid.getChildren().remove(labelArray[pointNumber]);

                    int labelText = numberOfPips - 4;

                    labelArray[pointNumber] = new Label( labelText + " Pips");
                    GridPane.setConstraints(labelArray[pointNumber], pointHolder[pointNumber].getColumnIndex(), 4);
                    topLeftGrid.getChildren().add(labelArray[pointNumber]);
                    labelArray[pointNumber].setTextFill(Color.web("red"));
                }

                //as the top points draw in the same way as the grid there is no need to empty and refill here
                else{
                    //if the grid just has a placeholder pip in it this is removed
                    if(numberOfPips-1 == 0){
                        topLeftGrid.getChildren().remove(pointHolder[pointNumber].peek());
                        pointHolder[pointNumber].pop();
                    }

                    //the new pip is pushed onto the pointHolder and then displayed on the grid
                    pointHolder[pointNumber].push(new Pip(pipColour));
                    GridPane.setConstraints(pointHolder[pointNumber].peek(), pointHolder[pointNumber].getColumnIndex(), numberOfPips-1);
                    topLeftGrid.getChildren().add(pointHolder[pointNumber].peek());
                }
            }

            //logic is the same as above
            if(pointNumber > 18){
                if(numberOfPips > 5){
                    topRightGrid.getChildren().remove(labelArray[pointNumber]);

                    int labelText = numberOfPips - 4;

                    labelArray[pointNumber] = new Label( labelText + " Pips");
                    GridPane.setConstraints(labelArray[pointNumber], pointHolder[pointNumber].getColumnIndex(), 4);
                    topRightGrid.getChildren().add(labelArray[pointNumber]);
                    labelArray[pointNumber].setTextFill(Color.web("red"));
                }

                else{
                    if(numberOfPips-1 == 0){
                        topRightGrid.getChildren().remove(pointHolder[pointNumber].peek());
                        pointHolder[pointNumber].pop();
                    }

                    pointHolder[pointNumber].push(new Pip(pipColour));
                    GridPane.setConstraints(pointHolder[pointNumber].peek(), pointHolder[pointNumber].getColumnIndex(), numberOfPips-1);
                    topRightGrid.getChildren().add(pointHolder[pointNumber].peek());
                }
            }
        }
    }

    /*
    This method is used to remove a pip of given colour from a given point
    * */
    public void removePip(int pointNumber, char pipColour){
        int numberOfPips = pointHolder[pointNumber].getPlayerPip();

        if(pointNumber == 0 || pointNumber == 25){
            pointHolder[pointNumber].pipCounterDecrementer();
            if(numberOfPips == 2){
                barGrid.getChildren().remove(labelArray[pointNumber]);
            }

            else if(numberOfPips > 2){
                barGrid.getChildren().remove(labelArray[pointNumber]);

                int labelText = numberOfPips - 1;
                labelArray[pointNumber] = new Label( labelText + " Pips");
                GridPane.setConstraints(labelArray[pointNumber], 0, pointHolder[pointNumber].getColumnIndex());
                barGrid.getChildren().add(labelArray[pointNumber]);
                //red is used as the color as its visible over both red and white pips
                labelArray[pointNumber].setTextFill(Color.web("red"));
            }

            else{
                barGrid.getChildren().remove(pointHolder[pointNumber].peek());
                pointHolder[pointNumber].pop();
                pointHolder[pointNumber].push(new Pip('P'));
                GridPane.setConstraints(pointHolder[pointNumber].peek(), 0, pointHolder[pointNumber].getColumnIndex());
                barGrid.getChildren().add(pointHolder[pointNumber].peek());
            }
        }


        if(pointNumber < 13 && pointNumber > 0){

            if(pointNumber < 7){
                bottomRightGrid.getChildren().remove(labelArray[pointNumber]);
                //removes label and does nothing if there will just be one pip on the last row
                if(numberOfPips == 6){
                    pointHolder[pointNumber].pipCounterDecrementer();
                }

                //label drawn the same as in addPip
                else if(numberOfPips > 5){
                    pointHolder[pointNumber].pipCounterDecrementer();
                    int labelText = (numberOfPips - 1) - 4;

                    labelArray[pointNumber] = new Label( labelText + " Pips");
                    GridPane.setConstraints(labelArray[pointNumber], pointHolder[pointNumber].getColumnIndex(), 0);
                    bottomRightGrid.getChildren().add(labelArray[pointNumber]);
                    labelArray[pointNumber].setTextFill(Color.web("red"));
                }

                //like with addPip, the bottom grids are emptied and refilled
                else{
                    //This for loop emptys the point and clears it from the board, this is neccessary for the bottom grids
                    for(int i=0; i < pointHolder[pointNumber].size(); i++){
                        bottomRightGrid.getChildren().remove(pointHolder[pointNumber].peek());
                        pointHolder[pointNumber].pop();
                    }

                    //This for loops fills the empty spaces in the pointer with placeholder pips, required to fill the actual positions
                    for(int i=0; i < 5 - numberOfPips+1; i++){
                        pointHolder[pointNumber].push(new Pip('P'));
                        GridPane.setConstraints(pointHolder[pointNumber].peek(), pointHolder[pointNumber].getColumnIndex(), i);
                        bottomRightGrid.getChildren().add(pointHolder[pointNumber].peek());
                    }

                    //This for loops fills the colored pips in
                    for(int i= 5 - numberOfPips+1; i < 5 ; i++){
                        pointHolder[pointNumber].push(new Pip(pipColour));
                        GridPane.setConstraints(pointHolder[pointNumber].peek(), pointHolder[pointNumber].getColumnIndex(), i);
                        bottomRightGrid.getChildren().add(pointHolder[pointNumber].peek());
                    }
                    pointHolder[pointNumber].pipCounterDecrementer();
                }
            }

            //logic the same as above
            if(pointNumber > 6){
                bottomLeftGrid.getChildren().remove(labelArray[pointNumber]);
                //removes label and does nothing if there will just be one pip on the last row
                if(numberOfPips == 6){
                    pointHolder[pointNumber].pipCounterDecrementer();
                }

                else if(numberOfPips > 5){
                    pointHolder[pointNumber].pipCounterDecrementer();

                    int labelText = (numberOfPips - 1) - 4;

                    labelArray[pointNumber] = new Label( labelText + " Pips");
                    GridPane.setConstraints(labelArray[pointNumber], pointHolder[pointNumber].getColumnIndex(), 0);
                    bottomLeftGrid.getChildren().add(labelArray[pointNumber]);
                    labelArray[pointNumber].setTextFill(Color.web("red"));
                }

                else{
                    //This for loop emptys the point holder and clears it from the board, this is neccessary for the bottom grids
                    for(int i=0; i < pointHolder[pointNumber].size(); i++){
                        bottomLeftGrid.getChildren().remove(pointHolder[pointNumber].peek());
                        pointHolder[pointNumber].pop();
                    }

                    //This for loops fills the empty spaces in the pointer with transparent pips, required to fill the actual positions
                    for(int i=0; i < 5 - numberOfPips+1; i++){
                        pointHolder[pointNumber].push(new Pip('P'));
                        GridPane.setConstraints(pointHolder[pointNumber].peek(), pointHolder[pointNumber].getColumnIndex(), i);
                        bottomLeftGrid.getChildren().add(pointHolder[pointNumber].peek());
                    }

                    //This for loops fills the colored pips in
                    for(int i= 5 - numberOfPips+1; i < 5 ; i++){
                        pointHolder[pointNumber].push(new Pip(pipColour));
                        GridPane.setConstraints(pointHolder[pointNumber].peek(), pointHolder[pointNumber].getColumnIndex(), i);
                        bottomLeftGrid.getChildren().add(pointHolder[pointNumber].peek());
                    }
                    pointHolder[pointNumber].pipCounterDecrementer();
                }
            }
        }

        if(pointNumber < 25 && pointNumber > 12){

            if(pointNumber < 19){
                //removes label and does nothing if there will just be one pip on the last row
                if(numberOfPips == 6){
                    topLeftGrid.getChildren().remove(labelArray[pointNumber]);
                    pointHolder[pointNumber].pipCounterDecrementer();
                }

                //label drawn the same as in the bottom grids
                else if(numberOfPips > 5){
                    topLeftGrid.getChildren().remove(labelArray[pointNumber]);
                    pointHolder[pointNumber].pipCounterDecrementer();

                    int labelText = (numberOfPips - 1) - 4;

                    labelArray[pointNumber] = new Label( labelText + " Pips");
                    GridPane.setConstraints(labelArray[pointNumber], pointHolder[pointNumber].getColumnIndex(), 4);
                    topLeftGrid.getChildren().add(labelArray[pointNumber]);
                    labelArray[pointNumber].setTextFill(Color.web("red"));
                }


                else{
                    //top pip is popped and removed from grid
                    topLeftGrid.getChildren().remove(pointHolder[pointNumber].peek());
                    pointHolder[pointNumber].pop();
                    pointHolder[pointNumber].pipCounterDecrementer();

                    //if removing the pip would leave the point empty then a placeholder pip is added after the pip is removed
                    if(numberOfPips==1){
                        pointHolder[pointNumber].push(new Pip('P'));
                        GridPane.setConstraints(pointHolder[pointNumber].peek(), pointHolder[pointNumber].getColumnIndex(), 0);
                        topLeftGrid.getChildren().add(pointHolder[pointNumber].peek());
                    }
                }
            }

            //logic the same as above
            if(pointNumber > 18){
                //removes label and does nothing if there will just be one pip on the last row
                if(numberOfPips == 6){
                    topRightGrid.getChildren().remove(labelArray[pointNumber]);
                    pointHolder[pointNumber].pipCounterDecrementer();
                }

                else if(numberOfPips > 5){
                    topRightGrid.getChildren().remove(labelArray[pointNumber]);
                    pointHolder[pointNumber].pipCounterDecrementer();

                    int labelText = (numberOfPips - 1) - 4;

                    labelArray[pointNumber] = new Label( labelText + " Pips");
                    GridPane.setConstraints(labelArray[pointNumber], pointHolder[pointNumber].getColumnIndex(), 4);
                    topRightGrid.getChildren().add(labelArray[pointNumber]);
                    labelArray[pointNumber].setTextFill(Color.web("red"));
                }

                else{
                    topRightGrid.getChildren().remove(pointHolder[pointNumber].peek());
                    pointHolder[pointNumber].pop();
                    pointHolder[pointNumber].pipCounterDecrementer();

                    if(numberOfPips==1){
                        pointHolder[pointNumber].push(new Pip('P'));
                        GridPane.setConstraints(pointHolder[pointNumber].peek(), pointHolder[pointNumber].getColumnIndex(), 0);
                        topRightGrid.getChildren().add(pointHolder[pointNumber].peek());
                    }
                }
            }
        }
    }

    //this flips the board view, used for changing player turns
    public void boardFlip(){
        removeNumbers();

        if(numbersInInit){
            topRightNumbers.setHgap(40);
            topleftNumbers.setHgap(36);
            bottomLeftNumbers.setHgap(32);
            bottomRightNumbers.setHgap(32);

            int j=5;
            for(int i=0; i<6; i++){
                GridPane.setConstraints(numberLabelArray[j], i, 0);
                topRightNumbers.getChildren().add(numberLabelArray[j]);
                j--;
            }

            j=11;
            for(int i=0; i<6; i++){
                GridPane.setConstraints(numberLabelArray[j], i, 0);
                topleftNumbers.getChildren().add(numberLabelArray[j]);
                j--;
            }

            for(int i=0; i<6; i++){
                GridPane.setConstraints(numberLabelArray[i+12], i, 0);
                bottomLeftNumbers.getChildren().add(numberLabelArray[i+12]);
            }

            for(int i=0; i<6; i++){
                GridPane.setConstraints(numberLabelArray[i+18], i, 0);
                bottomRightNumbers.getChildren().add(numberLabelArray[i+18]);
            }
            numbersInInit = false;
        }

        else{
            topRightNumbers.setHgap(32);
            topleftNumbers.setHgap(32);
            bottomLeftNumbers.setHgap(36);
            bottomRightNumbers.setHgap(40);

            for(int i=0; i<6; i++){
                GridPane.setConstraints(numberLabelArray[i+18], i, 0);
                topRightNumbers.getChildren().add(numberLabelArray[i+18]);
            }

            for(int i=0; i<6; i++){
                GridPane.setConstraints(numberLabelArray[i+12], i, 0);
                topleftNumbers.getChildren().add(numberLabelArray[i+12]);
            }

            int j=11;
            for(int i=0; i<6; i++){
                GridPane.setConstraints(numberLabelArray[j], i, 0);
                bottomLeftNumbers.getChildren().add(numberLabelArray[j]);
                j--;
            }

            for(int i=0; i<6; i++){
                GridPane.setConstraints(numberLabelArray[j], i, 0);
                bottomRightNumbers.getChildren().add(numberLabelArray[j]);
                j--;
            }

            numbersInInit = true;
        }

    }

    //used to perform hit action on a point
    private void hitPip(int startingPointNumber, int finalPointNumber, char newPipColour){
        if(pointHolder[finalPointNumber].getPipColour() == 'B') addPip(0, pointHolder[finalPointNumber].getPipColour());
        else addPip(25, pointHolder[finalPointNumber].getPipColour());

        removePip(finalPointNumber, pointHolder[finalPointNumber].getPipColour());
        addPip(finalPointNumber, newPipColour);
        removePip(startingPointNumber, newPipColour);
    }

    //draws the numbers corresponding to the pips
    public void drawNumbers(){
        for (int i = 1; i<25; i++){
            numberLabelArray[i-1] = new Label(Integer.toString(i));
            numberLabelArray[i-1].setTextFill(Color.web("white"));
        }

        topRightNumbers.setPadding(new Insets(0, 80, 50, 497));
        topRightNumbers.setHgap(32);

        topleftNumbers.setHgap(32);
        topleftNumbers.setPadding(new Insets(0, 0, 0, 113));

        bottomLeftNumbers.setHgap(36);
        bottomLeftNumbers.setPadding(new Insets(635, 0, 0, 113));

        bottomRightNumbers.setHgap(40);
        bottomRightNumbers.setPadding(new Insets(635, 0, 0, 497));

        for(int i=0; i<6; i++){
            GridPane.setConstraints(numberLabelArray[i+18], i, 0);
            topRightNumbers.getChildren().add(numberLabelArray[i+18]);
        }

        for(int i=0; i<6; i++){
            GridPane.setConstraints(numberLabelArray[i+12], i, 0);
            topleftNumbers.getChildren().add(numberLabelArray[i+12]);
        }

        int j=11;
        for(int i=0; i<6; i++){
            GridPane.setConstraints(numberLabelArray[j], i, 0);
            bottomLeftNumbers.getChildren().add(numberLabelArray[j]);
            j--;
        }

        for(int i=0; i<6; i++){
            GridPane.setConstraints(numberLabelArray[j], i, 0);
            bottomRightNumbers.getChildren().add(numberLabelArray[j]);
            j--;
        }

        numbersInInit = true;
    }

    //removes the numbers corresponding to the pips
    public void removeNumbers(){
        for(int i=0; i<6; i++){
            topRightNumbers.getChildren().remove(numberLabelArray[i+18]);
        }

        for(int i=0; i<6; i++){
            topleftNumbers.getChildren().remove(numberLabelArray[i+12]);
        }

        int j=11;
        for(int i=0; i<6; i++){
            bottomLeftNumbers.getChildren().remove(numberLabelArray[j]);
            j--;
        }

        for(int i=0; i<6; i++){
            bottomRightNumbers.getChildren().remove(numberLabelArray[j]);
            j--;
        }
    }

    public int getNumberOfPips(int point){
        return pointHolder[point].getPlayerPip();
    }

    //caled by main to get number of pips. required due to the way the positions on the board are stored
    public int getNumberOfPipsMain(int point){
        if(numbersInInit) return pointHolder[point].getPlayerPip();
        else return pointHolder[pointHolder[point].getInverse()].getPlayerPip();
    }
    
	public static int totalWhitePip = 15;
	public static int totalBlackPip = 15;

	//used to check if the board is empty for either pip colour
    public void PipCount() {

        boolean whiteEmpty = true;
        boolean blackEmpty = true;

        for(int i=0; i<26; i++){
            if(pointHolder[i].getPlayerPip() > 0 && pointHolder[i].getPipColour() == 'W') whiteEmpty = false;
        }

        for(int i=0; i<26; i++){
            if(pointHolder[i].getPlayerPip() > 0 && pointHolder[i].getPipColour() == 'B') blackEmpty = false;
        }

        if(whiteEmpty) totalWhitePip = 0;
        if(blackEmpty) totalBlackPip = 0;
    }

    //sets the player score and links it to a name. takes the player name, number and score
    public void setPlayerScore(int playerNumber, String playerName, int playerScore){
        if(playerNumber==1){
            player1ScoreGrid.getChildren().removeAll(player1Score);
            player1NameGrid.getChildren().removeAll(player1Name);

            player1Name.setText(playerName);
            player1Score.setText(Integer.toString(playerScore));

            player1ScoreGrid.getChildren().addAll(player1Score);
            player1NameGrid.getChildren().addAll(player1Name);

            GridPane.setConstraints(player1Name, 0, 0);
            GridPane.setConstraints(player1Score, 0, 0);

            if(playerScore<10){
                player1ScoreGrid.setPadding(new Insets(63, 0, 0, 30));
            }
            else player1ScoreGrid.setPadding(new Insets(63, 0, 0, 15));
        }

        if(playerNumber==2){
            player2ScoreGrid.getChildren().removeAll(player2Score);
            player2NameGrid.getChildren().removeAll(player2Name);

            player2Name.setText(playerName);
            player2Score.setText(Integer.toString(playerScore));

            player2ScoreGrid.getChildren().addAll(player2Score);
            player2NameGrid.getChildren().addAll(player2Name);

            GridPane.setConstraints(player2Name, 0, 0);
            GridPane.setConstraints(player2Score, 0, 0);

            if(playerScore<10){
                player2ScoreGrid.setPadding(new Insets(495, 0, 0, 30));
            }
            else player2ScoreGrid.setPadding(new Insets(495, 0, 0, 15));
        }
    }

    //updates the doubling dice to a given integer. value must be one that cna show on dice naturally
    public void updateDoublingCude(int doubleValue){
        if(doubleValue==1){
            doubleCubeGrid.getChildren().remove(doubleCubeView);

            doubleCube =  new Image(getClass().getResourceAsStream("double64.jpg"));
            doubleCubeView = new ImageView(doubleCube);
            doubleCubeGrid.getChildren().add(doubleCubeView);
        }

        if(doubleValue==2){
            doubleCubeGrid.getChildren().remove(doubleCubeView);

            doubleCube =  new Image(getClass().getResourceAsStream("double2.jpg"));
            doubleCubeView = new ImageView(doubleCube);
            doubleCubeGrid.getChildren().add(doubleCubeView);
        }

        if(doubleValue==4){
            doubleCubeGrid.getChildren().remove(doubleCubeView);

            doubleCube =  new Image(getClass().getResourceAsStream("double4.jpg"));
            doubleCubeView = new ImageView(doubleCube);
            doubleCubeGrid.getChildren().add(doubleCubeView);
        }

        if(doubleValue==8){
            doubleCubeGrid.getChildren().remove(doubleCubeView);

            doubleCube =  new Image(getClass().getResourceAsStream("double8.jpg"));
            doubleCubeView = new ImageView(doubleCube);
            doubleCubeGrid.getChildren().add(doubleCubeView);
        }

        if(doubleValue==16){
            doubleCubeGrid.getChildren().remove(doubleCubeView);

            doubleCube =  new Image(getClass().getResourceAsStream("double16.jpg"));
            doubleCubeView = new ImageView(doubleCube);
            doubleCubeGrid.getChildren().add(doubleCubeView);
        }

        if(doubleValue==32){
            doubleCubeGrid.getChildren().remove(doubleCubeView);

            doubleCube =  new Image(getClass().getResourceAsStream("double32.jpg"));
            doubleCubeView = new ImageView(doubleCube);
            doubleCubeGrid.getChildren().add(doubleCubeView);
        }

        if (doubleValue == 64) {

            doubleCubeGrid.getChildren().remove(doubleCubeView);

            doubleCube =  new Image(getClass().getResourceAsStream("double64.jpg"));
            doubleCubeView = new ImageView(doubleCube);
            doubleCubeGrid.getChildren().add(doubleCubeView);
        }
    }

    //adds a pip to the bear off position
    public void addBearOff(char pipColour){
        if(pipColour == 'W') {
            //adds first pip if bear off grid is empty
            if (bottomBearOffGridNumberOfPips == 0) {
                Pip bearOffWhitePip = new Pip('W');
                GridPane.setConstraints(bearOffWhitePip, 0, 0);
                bottomBearOffGrid.getChildren().add(bearOffWhitePip);
            }

            //label displaying number of pips is removed, updated then re-added here
            else{
                bottomBearOffGrid.getChildren().remove(bottomBearOffGridLabel);
                bottomBearOffGridLabel.setText(" " + Integer.toString(bottomBearOffGridNumberOfPips+1) + " Pips");
                bottomBearOffGridLabel.setTextFill(Color.web("red"));
                GridPane.setConstraints(bottomBearOffGridLabel, 0, 0);
                bottomBearOffGrid.getChildren().add(bottomBearOffGridLabel);

            }
            bottomBearOffGridNumberOfPips++;
        }

        if(pipColour == 'B'){
            //adds first pip if bear off grid is empty
            if (topBearOffGridNumberOfPips == 0) {
                Pip bearOffBlackPip = new Pip('B');
                GridPane.setConstraints(bearOffBlackPip, 0, 0);
                topBearOffGrid.getChildren().add(bearOffBlackPip);
            }

            //label displaying number of pips is removed, updated then re-added here
            else{
                topBearOffGrid.getChildren().remove(topBearOffGridLabel);
                topBearOffGridLabel.setText(" " +Integer.toString(topBearOffGridNumberOfPips+1) + " Pips");
                topBearOffGridLabel.setTextFill(Color.web("red"));
                GridPane.setConstraints(topBearOffGridLabel, 0, 0);
                topBearOffGrid.getChildren().add(topBearOffGridLabel);
            }
            topBearOffGridNumberOfPips++;
        }
    }

    //removes all pips from both bear off spots
    public void clearBearOff(){
            topBearOffGrid.getChildren().clear();
            bottomBearOffGrid.getChildren().clear();
    }

    //sets match number
    public void setMatchNumber(int matchNumber){
        if(matchNumber < 10) matchNumberVisual.setText(" " + matchNumber);
        else matchNumberVisual.setText("" + matchNumber);
    }



}
