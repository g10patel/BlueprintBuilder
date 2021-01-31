package Model.Shapes;


import Launcher.Launcher;
import Model.Blueprint.Blueprint;
import Model.Tool.CurveTool;
import Model.Tool.Tool;
import Model.ToolBar;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;

///Add c2 after curve is done

public class CurveShape extends QuadCurve implements Shape{
    private final QuadCurve curve;
    private Edge edge;
    public CurveShape(double x1, double y1, double c1, double c2, double x2, double y2)
    {
        this.setStartX(x1);
        this.setStartY(y1);
        this.setControlX(c1);
        this.setControlY(c2);
        this.setEndX(x2);
        this.setEndY(y2);
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.WHITE);

        curve = this;
        Launcher.getBlueprint().addShape(curve);
        edge = new Edge(x1, y1);
    }



    public void updateShape(double x1, double y1, double c1, double c2, double x2, double y2)
    {

        curve.setStartX(x1);
        curve.setStartY(y1);
        curve.setControlX(c1);
        curve.setControlY(c2);
        curve.setEndX(x2);
        curve.setEndY(y2);

    }

    public void setControl(double c1, double c2)
    {
        curve.setControlX(c1);
        curve.setControlY(c2);

    }

    @Override
    public void setEndCoord(double x2, double y2) {

        this.setEndX(x2);
        this.setEndY(y2);

    }

    public void remove() {
        Launcher.getBlueprint().removeShape(curve);
        if(this == CurveTool.getFirstShape())
        {
            edge.removeEdge();
        }
    }
}
