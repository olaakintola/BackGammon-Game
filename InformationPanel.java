/* InformationPanel Class
*  Written by: Ola
*/

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

/*
*class is responsible for outputting the user input to the display panel
*/

public class InformationPanel extends BorderPane
{
	// instance variables
	private Pane pane;
	private Canvas canvas;
	private GridPane grid = new GridPane();
	private int gridSize = 0;
	private ScrollBar scrollBar = new ScrollBar();
	private Label[] labels = new Label[1000];//stores all the messages that are printed to the panel

	public InformationPanel() {

		//pane is used for the panel
		pane = new Pane( );
		canvas = new Canvas( Dimensions.APP_WIDTH - Dimensions.APP_PADDING,
	                        Dimensions.APP_HEIGHT - Dimensions.APP_PADDING );
		pane.setStyle("-fx-background-color:Wheat"); //background colour is set
		pane.getChildren( ).addAll( canvas, grid, scrollBar);

		//scrollbar used for navigating info panel when the number of lines of texts exceeds size of the panel
        scrollBar.setLayoutX(canvas.getWidth()-scrollBar.getWidth());
        scrollBar.setMin(0);
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setPrefHeight(656);
        scrollBar.setMax(656);
        scrollBar.setValue(0);

        //scrollbar call
        scrollBar.valueProperty().addListener(e -> {
            //only entered when more text than panel size
            if(gridSize < 33);
            else{
                removeLabels();
                displayLabels();
            }
        });


		setCenter(pane);
		}
	
	/**addText method takes the text from the user and outputs it to the top of the display
	 * position is used for asking for the players' names
	 * */
	public void addText(int position, String text){

		Label label = new Label();

		if(position==0){
			label.setText("Enter first player's name, their dice will roll on the left panel.");
		} else if(position==1){
			label.setText("Enter second player's name, their dice will roll on the right panel. ");
		} else {
			label.setText(text);
		}
        labels[gridSize] = label;
        gridSize++;
        removeLabels();
		displayLabels();

        System.out.println(scrollBar.getValue());
	}

	//used to display all the labels on the panel with newest at the top and oldest at the bottom
    //if there are more lines than the size of the panel the code then reacts to the scrollbar position
	public void displayLabels(){
	    //prints messaged for board with less labels than panel size
	    if(gridSize < 31) {
            int numOfLabels = gridSize;
            int j = 0;
            for (int i = numOfLabels; i > numOfLabels - 31; i--) {
                GridPane.setConstraints(labels[i - 1], 0, j);
                grid.getChildren().addAll(labels[i - 1]);
                j++;
                if (i - 1 == 0) break;
            }
        }

	    //else statement is used to react to scrollbar
	    else{

            int startPoint, moveRight, moveLeft;
            double startPointDouble;
            startPointDouble = 1 - (scrollBar.getValue() / scrollBar.getMax());
            startPointDouble *= gridSize;
            startPoint = (int) Math.round(startPointDouble); //is a position in gridsize that corresponds to the position
            //of the scrollbar i.e. if scrollbar is in the middle and gridsize was 50, startPoint would be 25

            moveLeft = startPoint - 15;
            moveRight = startPoint + 15;
            if (moveLeft < 0) {
                moveRight += +(moveLeft * -1);
            }

            if (moveRight > gridSize - 1) {
                moveRight = gridSize - 1;
            }

            int j = 0;
            for (int i = moveRight; i > moveRight - 31; i--) {
                GridPane.setConstraints(labels[i], 0, j);
                grid.getChildren().addAll(labels[i]);
                j++;
                if (i == 0) break;
            }
        }

    }

    public void removeLabels(){
	    for(int i=0;i<gridSize;i++){
	        grid.getChildren().remove(labels[i]);
        }
    }
}