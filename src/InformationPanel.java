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
	private Label[] labels = new Label[500];

	public InformationPanel() {

		//pane is used for the panel
		pane = new Pane( );
		canvas = new Canvas( Dimensions.APP_WIDTH - Dimensions.APP_PADDING,
	                        Dimensions.APP_HEIGHT - Dimensions.APP_PADDING );
		pane.setStyle("-fx-background-color:Wheat"); //background colour is set
		pane.getChildren( ).addAll( canvas, grid, scrollBar);

        scrollBar.setLayoutX(canvas.getWidth()-scrollBar.getWidth());
        scrollBar.setMin(0);
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setPrefHeight(656);
        scrollBar.setMax(656);
        scrollBar.setValue(0);

        scrollBar.valueProperty().addListener(e -> {
            if(gridSize < 33);
            else{
                removeLabels();
                displayLabels();
            }
        });


		setCenter(pane);
		}
	
	/**addText method takes the text from the user and outputs it to the display using 
	 * the position parameter to determine row to output the string
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

		/*GridPane.setConstraints(label, 0, position);
		grid.getChildren().addAll(label);*/


		//below was an attempt to style the code, will be revisited next sprint so it hasn't been deleted, just commented out
		
		// set the constraints 31 is the number i need to remember sorry ola :p
		//Label label3 = new Label(text);
		//Label label2 = new Label(" It is the next player's turn...Enter how many moves");
		// setting the styles of the text being shown on the display panel
        //label3.setStyle("-fx-background-color:Wheat; -fx-text-fill:Black; -fx-font-size:20");
        //label2.setStyle("-fx-background-color:Yellow; -fx-text-fill:Black; -fx-font-size:20");

		//GridPane.setConstraints(label2, 1, position);
//		GridPane.setConstraint(label2, 1, position);
		// add the child to the grid
	}

	public void displayLabels(){
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

	    else{
            System.out.println("scrollbar.gerValue: " + scrollBar.getValue()/scrollBar.getMax());
            int startPoint, moveRight, moveLeft;
            double startPointDouble;
            startPointDouble = 1 - (scrollBar.getValue() / scrollBar.getMax());
            startPointDouble *= gridSize;
            startPoint = (int) Math.round(startPointDouble);

            moveLeft = startPoint - 15;
            moveRight = startPoint + 15;
            if (moveLeft < 0) {
                moveRight += +(moveLeft * -1);
                moveLeft = 0;
            }

            if (moveRight > gridSize - 1) {
                moveLeft -= gridSize - moveRight;
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