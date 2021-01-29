package Model;

import Model.Blueprint.Blueprint;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Helper {
    public static Button pathToButton(String path)
    {

        Image icon = new Image(path);
        ImageView iv = new ImageView(icon);
        iv.setFitWidth(40);
        iv.setFitHeight(40);
        iv.setStyle("-fx-background-color: transparent");
        iv.setPreserveRatio(true);
        Button button = new Button();
        button.setPrefSize(buttonWidth(),buttonWidth());
        button.setMaxWidth(buttonWidth()/2);
        button.setMaxHeight(buttonWidth());
        button.setGraphic(iv);
        button.setStyle("-fx-background-color: transparent;");
        button.setOnMouseEntered(mouseEvent -> button.setStyle("-fx-background-color:  #2c333d"));

        button.setOnMouseExited(mouseEvent -> button.setStyle("-fx-background-color: transparent"));
        return button;


    }
    private static double buttonWidth()
    {
        return ((Blueprint.getBounds().getWidth()/20)/2);
    }

}
