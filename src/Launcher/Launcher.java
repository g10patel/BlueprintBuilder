package Launcher;

import Model.Blueprint.Blueprint;
import Model.MenuBar;
import Model.ToolBar;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Launcher {
    private static Blueprint blueprint;
    private static StackPane stackPane;
    public void launch(Stage primaryStage) {
        InitializeView(primaryStage);
    }
    private void InitializeView(Stage primaryStage)
    {
        Pane root = new Pane();
        Scene scene = new Scene(root, 300, 275);
        HBox hbox = new HBox();
        VBox vbox = new VBox();
        stackPane = new StackPane();
        blueprint = new Blueprint(scene);
        stackPane.getChildren().add(0,blueprint);
        ToolBar toolbar = new ToolBar();
        MenuBar menubar = new MenuBar();
        vbox.getChildren().addAll(menubar, hbox);
        hbox.getChildren().addAll(toolbar,stackPane);
        root.getChildren().add(vbox);


        blueprint.setScene(scene);
        primaryStage.setTitle("Blueprint Builder");
        primaryStage.setScene(scene);

        Rectangle2D primaryScreenBounds = Blueprint.getBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());

        primaryStage.show();

    }
    public static Blueprint getBlueprint()
    {
        return blueprint;
    }
    public static StackPane getStackPane() {return stackPane;}


}
