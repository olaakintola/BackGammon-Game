import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{

    private Board board = new Board();
    Exit exit = new Exit();


    //Moving secondloop and wait are used for the move method that moves pips around 1 at a time
    private int moving = 1;
    private boolean secondLoop = false;
    private int wait = 0;

    private int textRow = 0;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage){
        //Borderpane is used to format the stage
        BorderPane borderPane = new BorderPane();

        BackgammonController bC = new BackgammonController();
        TextPanel textPanel = new TextPanel();

        borderPane.setCenter(board);
        borderPane.setRight(bC);
        borderPane.setBottom(textPanel);

        //event handler handles inputs
        textPanel.button.setOnAction(e -> {
            if (textPanel.getTextFieldText().equals("move")) {
                move();
            }

            //quits program if quit is entered
            if (textPanel.getTextFieldText().equals("quit")) {
                exit.exitProgram(primaryStage);
            }

            bC.addText(textRow, textPanel.getTextFieldText());
                    textRow++;
                });

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void move(){
        //first if statement moves the black player's pip all around the board
        //once that's completed, secondLoop is set to true and the second if statement is entered
        //which moves the white player's pip all around the board
        if(moving < 24 && secondLoop == false){
            //in instances where the black pip has to go on a stack of white pips it just skips that point
            //the move function from board is called to do the moving
            if(moving + 1 == 6 || moving + 1 == 8 || moving + 1 == 13 || moving + 1 == 24){
                board.move(moving, 2, 'B');
                moving++;
            }
            else{
                board.move(moving, 1, 'B');
            }
            if(moving == 24) secondLoop = true;
            else moving++;
        }

        //moves white pip all around
        if(secondLoop && wait == 1 && moving > 0){
            board.move(moving, -1, 'W');
            moving--;
        }

        //used to delay second panel by one iteration when moving == 24 so that the last black pip can move
        if(secondLoop && wait == 0 && moving > 0){
            wait = 1;
        }
    }
}
