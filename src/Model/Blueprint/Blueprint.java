package Model.Blueprint;

import Launcher.Launcher;
import Model.Blueprint.Grid.Grid;
import Model.Layer;
import Model.Shapes.Shape;
import Model.Tool.Tool;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.util.HashSet;

public class Blueprint extends Pane {

    private static double scale;
    private final HashSet<Layer> layers = new HashSet<>();
    private Layer currLayer;
    private Scene scene;
    private static Node currShape;
    private Grid grid;
    private Shape shapeHoveringOver;

    public Tool getCurrTool() {
        return currTool;
    }

    public void setCurrTool(Tool currTool) {
        this.currTool = currTool;
    }

    private Tool currTool;
    public Blueprint()
    {
        initialize();
    }


    public Blueprint(Scene x)
    {
        scene = x;
        initialize();
    }

    private void initialize()
    {
        setScale(1.0);
        grid = new Grid();
        this.setStyle("-fx-background-color: #1f242a");
        setCursor("resources/whiteCursor.png");
        addGridScrollEventHandler();
        addLayer();
    }

    public void setShapeHoveringOver(Shape x)
    {
        shapeHoveringOver = x;
    }
    public Shape getShapeHoveringOver()
    {
        return shapeHoveringOver;
    }


    public static Rectangle2D getBounds()
    {
        return Screen.getPrimary().getVisualBounds();
    }

    public static double getScale() { return scale; }

    public void setScale(double x) { scale = x; }

    public void addShape(Node shape) {
        currLayer.getChildren().add(shape);
    }

    public void removeShape(Node shape) {
        currLayer.getChildren().remove(shape);
    }

    public void addLayer()
    {
        Layer layer = new Layer();
        layers.add(layer);
        Launcher.getStackPane().getChildren().add(layer);
        setCurrLayer(layer);
    }

    private void setCurrLayer(Layer layer) {
        currLayer = layer;
    }

    public Layer getCurrLayer()
    {
        return currLayer;
    }



    private void setCursor(String s) {
        Image image = new Image(s);
        Launcher.getStackPane().setOnMouseEntered(mouseEvent -> scene.setCursor(new ImageCursor(image, image.getWidth() /2, image.getHeight()/2)));
        Launcher.getStackPane().setOnMouseExited(mouseEvent -> scene.setCursor(Cursor.DEFAULT));

        }

    public void setScene(Scene x)
    {
        scene = x;
    }



    private void addGridScrollEventHandler()
    {
        Launcher.getStackPane().setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY();
            if(deltaY > 0)
            {
                Grid.zoomOut();
            }
            else {
                Grid.zoomIn();
            }
        });
    }

    public Node getCurrShape()
    {
        return currShape;
    }
    public static void setCurrShape(Node x)
    {
        currShape = x;
    }


}
