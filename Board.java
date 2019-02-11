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
    /* four girds are made, one for each section of the board
    * this makes it a lot easier to handle the placement of the pips
    * on the board */
    private GridPane topRightGrid = new GridPane();
    private GridPane topLeftGrid = new GridPane();
    private GridPane bottomLeftGrid = new GridPane();
    private GridPane bottomRightGrid = new GridPane();

    //an array of pointDataTypes which is the data type used to store the information of a given point
    //26 are made, one for each point and bar position
    private PointDataType[] pointHolder = new PointDataType[26];

    //label array is used when there's an excess number of pips on a point, 26 are made, one for each position
    private Label[] labelArray = new Label[26];

    public Board() {
        /*this stackpane is the root node of the scene
        * it draws the backgammon board and as it's the root all other nodes are drawn on top of it*/
        StackPane sp = new StackPane();
        Image img = new Image("file:src/backgammonBoard.jpg");
        ImageView imgView = new ImageView(img);
        sp.getChildren().add(imgView);
        imgView.setFitHeight(656);
        imgView.setFitWidth(864);

        boardInitialize(); //call to method to initialize the board state

        sp.getChildren().addAll(topLeftGrid, topRightGrid, bottomLeftGrid, bottomRightGrid);
        setCenter(sp);
    }

    private void boardInitialize(){

        //insets and horizontal gaps are set here, these ensure the pips are displayed in the correct positions
        topRightGrid.setPadding(new Insets(23, 80, 50, 483));
        topRightGrid.setHgap(4);

        topLeftGrid.setPadding(new Insets(23, 0, 0, 100));
        topLeftGrid.setHgap(4);

        bottomLeftGrid.setPadding(new Insets(412, 0, 0, 100));
        bottomLeftGrid.setHgap(4);

        bottomRightGrid.setPadding(new Insets(412, 0, 0, 483));
        bottomRightGrid.setHgap(4);


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
    }

    /*
    This method is used to move a pip from one point to another
    It takes in the pip's starting position, the amount it's moving by and its colour
    It works by calling removePip() to remove the pip at starting position and then calling
    addPip() to add a pip at its final position
    * */
    public void move(int startingPosition, int moveAmount, char pipType){
         int finalPos = startingPosition + moveAmount;
         removePip(startingPosition, pipType);
         addPip(finalPos, pipType);
    }

    //method used by board initialize to add pips to the points
    /*
    This method is used to add a pip of a given color to a given point.
    If the point already has 5 pips, a label is shown on the 5th pip stating how
    many pips that position has.
    */
    private void addPip(int pointNumber, char pipColour){
        //different statements are required for each grid as the top and bottom are drawn differently and they
        //each have different gridpanes as parents
        if(pointNumber <= 12 && pointNumber > 0){

            pointHolder[pointNumber].pipCounterAdder();
            int numberOfPips = pointHolder[pointNumber].getPlayerPip(); //No. of player pips in current pointer

            if(pointNumber > 6){
                //this if statement checks if there are already 5 pips at the position
                //if there are a label is added at the 5th position
                if(numberOfPips > 5){
                    //previous label is removed to be replaced by new one
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
                    bottomLeftGrid.getChildren().remove(labelArray[pointNumber]);
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
                    bottomRightGrid.getChildren().remove(labelArray[pointNumber]);
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
                    topLeftGrid.getChildren().remove(labelArray[pointNumber]);
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
                    topRightGrid.getChildren().remove(labelArray[pointNumber]);
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
    private void removePip(int pointNumber, char pipColour){
        int numberOfPips = pointHolder[pointNumber].getPlayerPip();

        if(pointNumber < 13 && pointNumber > 0){

            if(pointNumber < 7){
                bottomRightGrid.getChildren().remove(labelArray[pointNumber]);
                //label drawn the same as in addPip
                if(numberOfPips > 5){
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
                if(numberOfPips > 5){
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

        if(pointNumber < 25 && pointNumber > 13){

            if(pointNumber < 19){
                //label drawn the same as in the bottom grids
                if(numberOfPips > 5){
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
                if(numberOfPips > 5){
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
}
