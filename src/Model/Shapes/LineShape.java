package Model.Shapes;

import Launcher.Launcher;
import Model.Blueprint.Blueprint;
import Model.Tool.LineTool;
import Model.Tool.Tool;
import Model.ToolBar;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;



public class LineShape extends Line implements Shape {
    private final Line line;
    Edge edge;
    public LineShape(double x1, double y1, double x2, double y2)
    {
        this.setStartX(x1);
        this.setStartY(y1);
        this.setEndX(x2);
        this.setEndY(y2);
        line = this;
        line.setStroke(Color.WHITE);

        edge = new Edge(x1, y1);
    }
    public void updateLine(double x1, double y1, double x2, double y2)
    {
        line.setStartX(x1);
        line.setStartY(y1);
        line.setEndX(x2);
        line.setEndY(y2);
        //updateEdgeDetection();
    }

    public void addToBlueprint()
    {
        Launcher.getBlueprint().addShape(line);

    }

    @Override
    public void remove()
    {
        Launcher.getBlueprint().removeShape(line);
        if(this == LineTool.getFirstShape())
        {
            edge.removeEdge();
        }
    }


    @Override
    public void setEndCoord(double x2, double y2) {
        line.setEndX(x2);
        line.setEndY(y2);

    }
}
