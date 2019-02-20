import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


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
        GridPane.setConstraints(button, 5, 1);
        GridPane.setConstraints(textField, 5, 0);

        GridPane.setConstraints(textLabel, 0, 0);
        layout.getChildren().addAll(button, textField, textLabel, grid);

        setCenter(layout);
    }

    //returns the text in the text field
    public String getTextFieldText(){
        return textField.getText();
    }
}
