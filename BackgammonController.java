/* BackgammonController Class
*  
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.animation.AnimationTimer;

/*
*class is responsible for outputting the user input to the display panel
*/

public class BackgammonController extends BorderPane
{
	
	// instance variables
	private Pane pane;
	private Canvas canvas; 
	private GraphicsContext gc;
	private GridPane grid = new GridPane();		
 
	public BackgammonController() {
		
		pane = new Pane( );
		canvas = new Canvas( Dimensions.APP_WIDTH - Dimensions.APP_PADDING,
	                        Dimensions.APP_HEIGHT - Dimensions.APP_PADDING );
		pane.getChildren( ).addAll( canvas, grid );

		setCenter(pane);
		}
	
	/**addText method takes the text from the user and outputs it to the display using 
	 * the position parameter to determine row to output the string
	 * */
	public void addText(int position, String text){
		
		// set the constraints
		Label label = new Label(text);
		GridPane.setConstraints(label, 0, position);
		// add the child to the grid
		grid.getChildren().add(label);
    
	}

}