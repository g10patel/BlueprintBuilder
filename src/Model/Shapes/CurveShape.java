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
    private QuadCurve curveBorder;
    private Circle c1;
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
        addEdgeDetection();
    }

    private void addEdgeDetection()
    {
        double scale = Blueprint.getScale();

        c1 = new Circle(15*scale);


        c1.setCenterX(curve.getStartX());
        c1.setCenterY(curve.getStartY());
        c1.setFill(Color.TRANSPARENT);
        c1.setStroke(Color.TRANSPARENT);


        curveBorder = new QuadCurve(curve.getStartX(), curve.getStartY(), curve.getControlX(), curve.getControlY(), curve.getEndX(), curve.getEndY());
        curveBorder.setStroke(Color.TRANSPARENT);
        curveBorder.setFill(Color.TRANSPARENT);
        curveBorder.setStrokeWidth(10 * scale);

        Blueprint blueprint = Launcher.getBlueprint();
        blueprint.addShape(c1);

        addEdgeDetectionEventHandlers();
    }

    private void addEdgeDetectionEventHandlers() {
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
        c1.setOnMouseEntered(enterEdge);
        c1.setOnMouseExited(exitEdge);
    }

    public void updateShape(double x1, double y1, double c1, double c2, double x2, double y2)
    {
        curve.setStartX(x1);
        curve.setStartY(y1);
        curve.setControlX(c1);
        curve.setControlY(c2);
        curve.setEndX(x2);
        curve.setEndY(y2);
        updateEdgeDetection();
    }

    public void setControl(double c1, double c2)
    {
        curve.setControlX(c1);
        curve.setControlY(c2);
        updateEdgeDetection();
    }
    private void updateEdgeDetection()
    {

        curveBorder.setControlX(curve.getControlX());
        curveBorder.setControlY(curve.getControlY());
        curveBorder.setEndX(curve.getEndX());
        curveBorder.setEndY(curve.getEndY());

    }


    @Override
    public void setEndCoord(double x2, double y2) {

        this.setEndX(x2);
        this.setEndY(y2);
        updateEdgeDetection();
    }

    public void addBorderEdge() {
        Launcher.getBlueprint().addShape(curveBorder);
    }

    @Override
    public void remove() {
        Launcher.getBlueprint().removeShape(curve);
        Launcher.getBlueprint().removeShape(curveBorder);
        if(this == CurveTool.getFirstShape())
        {
            Launcher.getBlueprint().removeShape(c1);
        }
    }
}
