package Model;

import Model.Blueprint.Blueprint;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MenuBar extends HBox{

    public MenuBar() {
        initialize(this);
        setupIcons(this);
    }
    private void initialize(MenuBar menuBar)
    {
        this.setMinWidth(Blueprint.getBounds().getWidth());
        this.setMinHeight(50);
        this.setStyle("-fx-background-color: #323c4a");
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(.5))));
    }
    private void setupIcons(MenuBar menuBar)
    {
        //TODO
    }


}
