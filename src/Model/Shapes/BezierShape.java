package Model.Shapes;

import Launcher.Launcher;
import Model.Blueprint.Blueprint;
import Model.Tool.BezierTool;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;

public class BezierShape extends CubicCurve implements Shape{

    private final CubicCurve bezier;
    private final Edge edge;

    public BezierShape(double x1, double y1, double cx1, double cy1, double cx2, double cy2, double x2, double y2)
    {
        this.setStartX(x1);
        this.setStartY(y1);
        this.setControlX1(cx1);
        this.setControlY1(cy1);
        this.setControlX2(cx2);
        this.setControlY2(cy2);
        this.setEndX(x2);
        this.setEndY(y2);
        this.setStroke(Color.WHITE);
        this.setFill(Color.TRANSPARENT);
        this.setStrokeWidth(.5);

        bezier = this;
        Blueprint blueprint = Launcher.getBlueprint();
        blueprint.addShape(bezier);
        edge = new Edge(x1, y1);
    }


    public void updateShape(double x1, double y1, double cx1, double cy1, double cx2, double cy2, double x2, double y2)
    {
        bezier.setStartX(x1);
        bezier.setStartY(y1);
        bezier.setControlX1(cx1);
        bezier.setControlY1(cy1);
        bezier.setControlX2(cx2);
        bezier.setControlY2(cy2);
        bezier.setEndX(x2);
        bezier.setEndY(y2);
    }

    public void setControl1(double cx1, double cy1)
    {

        bezier.setControlX1(cx1);
        bezier.setControlY1(cy1);
    }

    public void setControl2(double cx2, double cy2)
    {
        bezier.setControlX2(cx2);
        bezier.setControlY2(cy2);
    }

    @Override
    public void setEndCoord(double x2, double y2) {
        bezier.setEndX(x2);
        bezier.setEndY(y2);
    }

    @Override
    public void remove() {
        Launcher.getBlueprint().removeShape(bezier);
        if(this == BezierTool.getFirstShape())
        {
            edge.removeEdge();
        }
    }
}
