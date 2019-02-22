/* InformationPanel Class
*  Written by: Ola
*/

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/*
*class is responsible for outputting the user input to the display panel
*/

public class InformationPanel extends BorderPane
{
	// instance variables
	private Pane pane;
	private Canvas canvas;
	private GridPane grid = new GridPane();		
 
	public InformationPanel() {

		//pane is used for the panel
		pane = new Pane( );
		canvas = new Canvas( Dimensions.APP_WIDTH - Dimensions.APP_PADDING,
	                        Dimensions.APP_HEIGHT - Dimensions.APP_PADDING );
		pane.setStyle("-fx-background-color:Wheat"); //background colour is set
		pane.getChildren( ).addAll( canvas, grid );

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

		GridPane.setConstraints(label, 0, position);
		grid.getChildren().addAll(label);


		//below was an attempt to style the code, will be revisited next sprint so it hasn't been deleted, just commented out
		
		// set the constraints
		//Label label3 = new Label(text);
		//Label label2 = new Label(" It is the next player's turn...Enter how many moves");
		// setting the styles of the text being shown on the display panel
        //label3.setStyle("-fx-background-color:Wheat; -fx-text-fill:Black; -fx-font-size:20");
        //label2.setStyle("-fx-background-color:Yellow; -fx-text-fill:Black; -fx-font-size:20");

		//GridPane.setConstraints(label2, 1, position);
//		GridPane.setConstraint(label2, 1, position);
		// add the child to the grid
	}
}