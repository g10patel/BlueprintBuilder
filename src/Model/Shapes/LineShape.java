package Model.Shapes;

import Launcher.Launcher;
import Model.Tool.LineTool;
import Model.Tool.ScissorTool;
import Model.ToolBar;
import javafx.scene.paint.Color;
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
        addEventHandlers();
    }

    //Line needs more width
    private void addEventHandlers()
    {
        this.setOnMouseEntered(mouseEvent -> {
            if(ToolBar.getCurrTool() instanceof ScissorTool) {
                Launcher.getBlueprint().setShapeHoveringOver((Shape) line);
                line.setStrokeWidth(.3);
            }
        });

        this.setOnMouseExited(mouseEvent ->
        {
            line.setStrokeWidth(.5);
            Launcher.getBlueprint().setShapeHoveringOver(null);
        });
    }

    public void updateLine(double x1, double y1, double x2, double y2)
    {
        line.setStartX(x1);
        line.setStartY(y1);
        line.setEndX(x2);
        line.setEndY(y2);
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
