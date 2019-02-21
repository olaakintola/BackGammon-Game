/*
    Class that closes the application properly
    Written by Weronika Wolska
 */

import javafx.stage.Stage;

public class Exit {

    // method to display a pop up window on close request to get confirmation

    public void exitProgram(Stage stage) {
        boolean answer = ConfirmBox.display("Exit Game", "Are you sure you want to quit the game?");
        if (answer){
            stage.close();
        }
    }
}
