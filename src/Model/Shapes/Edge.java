package Model.Shapes;

import Launcher.Launcher;
import Model.Blueprint.Blueprint;
import Model.Tool.Tool;
import Model.ToolBar;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Edge {
    Circle edge;
    public Edge(double x1, double y1)
    {
        Blueprint blueprint = Launcher.getBlueprint();
        double scale = Blueprint.getScale();
        edge = new Circle(15 * scale);
        edge.setCenterX(x1);
        edge.setCenterY(y1);
        edge.setFill(Color.TRANSPARENT);
        edge.setStroke(Color.TRANSPARENT);

        blueprint.addShape(edge);
        addEventHandler();
    }

    private void addEventHandler() {

        EventHandler<MouseEvent> enterEdge = mouseEvent -> {
            Launcher.getBlueprint().requestFocus();
            Tool tool = ToolBar.getCurrTool();
            tool.removeFollowEvent();
            Shape shape = (Shape) Launcher.getBlueprint().getCurrShape();
            Circle source = (Circle)mouseEvent.getSource();
            source.setStroke(Color.GREEN);
            shape.setEndCoord(source.getCenterX(), source.getCenterY());
            tool.setAtEdge(true);


        };
        EventHandler<MouseEvent> exitEdge = mouseEvent -> {
            Launcher.getBlueprint().requestFocus();
            Tool tool = ToolBar.getCurrTool();
            tool.addFollowEvent();
            ((Circle) mouseEvent.getSource()).setStroke(Color.TRANSPARENT);
            tool.setAtEdge(false);

        };
        edge.setOnMouseEntered(enterEdge);
        edge.setOnMouseExited(exitEdge);
    }

    public void removeEdge()
    {
        Launcher.getBlueprint().removeShape(edge);
    }

}
