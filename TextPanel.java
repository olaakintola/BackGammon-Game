/*
    Text Panel Class
    Written by Weronika Wolska
 */

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static javafx.geometry.Pos.CENTER;


public class TextPanel extends BorderPane {

    Button button;
    TextField textField = new TextField();

    public TextPanel(){

        GridPane grid = new GridPane();
        GridPane layout = new GridPane();


        Label textLabel = new Label("Enter instruction: ");
        textField.setPrefColumnCount(5);
        textField.getText();
        button = new Button("Enter");
        grid.setConstraints(button, 10, 1);
        grid.setConstraints(textField, 10, 0);
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(CENTER);
        //grid.setAlignment(Pos.CENTER);
        //grid.getColumnConstraints();

        GridPane.setConstraints(textLabel, 9, 0);
        layout.getChildren().addAll(button, textField, textLabel, grid);

        setCenter(layout);
    }

    //returns the text in the text field
    public String getTextFieldText(){
        return textField.getText();
    }
}
