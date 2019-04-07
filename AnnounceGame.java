/*
* Written by: Ola
* */
import javafx.collections.FXCollections;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AnnounceGame {
	
	public static void loadDialog() {
		//creates a popUp window
		Stage popUp = new Stage();

		// makes sure no changes are made in the Main window while this window is open
		popUp.initModality(Modality.APPLICATION_MODAL);
		popUp.setTitle("New Game");
		popUp.setMinWidth(400);
		popUp.setHeight(200);

		//label explains how the game works
		Label displayLabel = new Label();
		displayLabel.setText("A new BackGammon Game is about to start!!!\r\n" + 
				"The object of the game is to move your pieces along the board's \n"
				+ "points and off the board before your opponent does.\n"
				+ "Your pieces move counterclockwise from the upper right\n");
		Button exitDisplay = new Button("Start Game");
		exitDisplay.setOnAction(e -> popUp.close());

		//vbox stores label and is set in centre
		VBox windowDisplay = new VBox();
		windowDisplay.setStyle("-fx-background-color:Wheat"); //background colour is set
		windowDisplay.getChildren().addAll(displayLabel, exitDisplay);
		windowDisplay.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(windowDisplay);
		popUp.setScene(scene);
		popUp.showAndWait();	
	} 
	
/*	public static void WinnerDialog(String text) {
		//creates a popUp window
		Stage popUp = new Stage();

		// makes sure no changes are made in the Main window while this window is open
		popUp.initModality(Modality.APPLICATION_MODAL);
		popUp.setTitle("Winner:");
		popUp.setMinWidth(400);
		popUp.setHeight(200);

		//label explains how the game works
		Label displayLabel = new Label();
		displayLabel.setText(text);
		Button exitDisplay = new Button("Thanks for playing");
		exitDisplay.setOnAction(e -> popUp.close());

		//vbox stores label and is set in centre
		VBox windowDisplay = new VBox();
		windowDisplay.getChildren().addAll(displayLabel, exitDisplay);
		windowDisplay.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(windowDisplay);
		popUp.setScene(scene);
		popUp.showAndWait();	
	}*/

	public static void WinnerDialog(String text) {
		// TODO Auto-generated method stub
		
		//creates a popUp window
				Stage popUp = new Stage();

				// makes sure no changes are made in the Main window while this window is open
				popUp.initModality(Modality.APPLICATION_MODAL);
				popUp.setTitle("Winner:");
				popUp.setMinWidth(400);
				popUp.setHeight(200);

				//label explains how the game works
				Label displayLabel = new Label();
				displayLabel.setText(text);
				Button exitDisplay = new Button("Congratulations, You Won");
				exitDisplay.setOnAction(e -> popUp.close());

				//vbox stores label and is set in centre
				VBox windowDisplay = new VBox();
				windowDisplay.getChildren().addAll(displayLabel, exitDisplay);
				windowDisplay.setAlignment(Pos.CENTER);
				
				Scene scene = new Scene(windowDisplay);
				popUp.setScene(scene);
				popUp.showAndWait();	
	}

	// Function that displays the the array containing the menu option to the screen
	public void OptionsMenu(String[] finalStringArray) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		//creates a popUp window
		Stage popUp = new Stage();

		//OLA ADDED lINE: makes sure changes are made in the Main window while this window is open
		popUp.initModality(Modality.APPLICATION_MODAL);		
		popUp.setTitle("Options Menu");
		popUp.setMinWidth(400);
		popUp.setHeight(200);

		//label explains how the game works
		Label displayLabel = new Label();

		ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
				finalStringArray));
		//	       borderPane.setRight(choiceBox);
		choiceBox.setPrefWidth(500);


		//		displayLabel.setText(text);
	//	Button exitDisplay = new Button("Congratulations, You Won");
	//	exitDisplay.setOnAction(e -> popUp.close());

		//vbox stores label and is set in centre
		VBox windowDisplay = new VBox();
		windowDisplay.setStyle("-fx-background-color:Wheat"); //background colour is set
		windowDisplay.getChildren().addAll(choiceBox);
		windowDisplay.setAlignment(Pos.CENTER);

		Scene scene = new Scene(windowDisplay);
		popUp.setScene(scene);
		popUp.showAndWait();
	}

}
