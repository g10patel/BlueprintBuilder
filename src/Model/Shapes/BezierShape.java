package Model.Shapes;

import Launcher.Launcher;
import Model.Blueprint.Blueprint;
import Model.Tool.BezierTool;
import Model.Tool.CurveTool;
import Model.Tool.Tool;
import Model.ToolBar;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;

public class BezierShape extends CubicCurve implements Shape{

    private CubicCurve bezier;
    private Blueprint blueprint;
    private Circle c1;
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
        blueprint = Launcher.getBlueprint();
        blueprint.addShape(bezier);
        addEdgeDetection();
    }

    private void addEdgeDetection() {
        double scale = Blueprint.getScale();

        c1 = new Circle(15*scale);


        c1.setCenterX(bezier.getStartX());
        c1.setCenterY(bezier.getStartY());
        c1.setFill(Color.TRANSPARENT);
        c1.setStroke(Color.TRANSPARENT);

        blueprint.addShape(c1);

        addEdgeDetectionEventHandlers();
    }

    //TODO
    //Put edge event handlers in its own class
    private void addEdgeDetectionEventHandlers() {

        EventHandler<MouseEvent> enterEdge = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Launcher.getBlueprint().requestFocus();
                Tool tool = ToolBar.getCurrTool();
                tool.removeFollowEvent();
                Shape shape = (Shape) Launcher.getBlueprint().getCurrShape();
                Circle source = (Circle)mouseEvent.getSource();
                source.setStroke(Color.GREEN);
                shape.setEndCoord(source.getCenterX(), source.getCenterY());
                tool.setAtEdge(true);


            }
        };
        EventHandler<MouseEvent> exitEdge = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Launcher.getBlueprint().requestFocus();
                Tool tool = ToolBar.getCurrTool();
                tool.addFollowEvent();
                ((Circle)mouseEvent.getSource()).setStroke(Color.TRANSPARENT);
                tool.setAtEdge(false);

            }
        };
        c1.setOnMouseEntered(enterEdge);
        c1.setOnMouseExited(exitEdge);
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
            Launcher.getBlueprint().removeShape(c1);
        }
    }
}
