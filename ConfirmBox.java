/*
    Class that displays a pop up window when the user tries to exit the application
        Written by Weronika Wolska
 */

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {


    private static boolean answer;
    static Button yesButton;
    static Button noButton;

    public static boolean display(String title, String message){

        Stage popUpWindow = new Stage();
        popUpWindow.setMinWidth(250);

        // makes sure no changes are made in the main window while this window is open
        popUpWindow.initModality(Modality.APPLICATION_MODAL);

        // display parameters
        popUpWindow.setTitle(title);
        Label displayMessage = new Label();
        displayMessage.setText(message);

        // create yes and no buttons

        yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            answer = true;
            popUpWindow.close();
        });

        noButton = new Button("No");
        noButton.setOnAction(e -> {
            answer = false;
            popUpWindow.close();
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(displayMessage, yesButton, noButton);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox);
        popUpWindow.setScene(scene);

        // cannot close window without clicking one of the buttons
        popUpWindow.showAndWait();

        // return the value associated with the button clicked
        return answer;
    }

}
