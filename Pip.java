/*
Pip Class
Shane Byrne
* */

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/*
This class contains the three different pip
It needs a character referencing one of the pip types to be instantiated
* */

public class Pip extends BorderPane {
    public Pip(Character PipType){
        if (PipType == 'P'){
            //circle is set with opacity 0 as it's meant to be invisible
            //stroke is just to ensure it has the same dimensions as the other two pips
            Circle placeholderPip = new Circle(20, Color.web("white", 0));
            placeholderPip.setStrokeType(StrokeType.OUTSIDE);
            placeholderPip.setStroke(Color.web("white", 0));
            placeholderPip.setStrokeWidth(2);

            setCenter(placeholderPip);
        }

        if (PipType == 'B'){
            //black with a white outline to stand out more
            Circle blackPip = new Circle(20, Color.web("black", 1));
            blackPip.setStrokeType(StrokeType.OUTSIDE);
            blackPip.setStroke(Color.web("white", 0.5));
            blackPip.setStrokeWidth(2);

            setCenter(blackPip);
        }

        if (PipType == 'W'){
            //white with a black outline to stand out more
            Circle whitePip = new Circle(20, Color.web("white", 1));
            whitePip.setStrokeType(StrokeType.OUTSIDE);
            whitePip.setStroke(Color.web("black", 0.5));
            whitePip.setStrokeWidth(2);

            setCenter(whitePip);
        }
    }
}
