import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AnnounceGame {
	
	public static void loadDialog() {
		Stage popUp = new Stage();
		
		popUp.initModality(Modality.APPLICATION_MODAL);
		popUp.setTitle("New Game");
		popUp.setMinWidth(400);
		popUp.setHeight(200);
		
		Label displayLabel = new Label();
		displayLabel.setText("A new BackGammon Game is about to start!!!\r\n" + 
				"The object of the game is to move your pieces along the board's \n"
				+ "triangles and off the board before your opponent does.\n"
				+ "Your pieces move counterclockwise from the upper right,\n"
				+ "while your opponent's move clockwise from the bottom right.\n");
		Button exitDisplay = new Button("Close the Window to Start Game");
		exitDisplay.setOnAction(e -> popUp.close());
		
		VBox windowDisplay = new VBox();
		windowDisplay.getChildren().addAll(displayLabel, exitDisplay);
		windowDisplay.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(windowDisplay);
		popUp.setScene(scene);
		popUp.showAndWait();	
	} 

}
