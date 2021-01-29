package Model.Shapes;


import Launcher.Launcher;
import Model.Blueprint.Blueprint;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;

public class CurveShape implements Shape{
    private QuadCurve curve;
    private QuadCurve curveBorder;
    private Circle c1;
    private Circle c2;
    public CurveShape(double x1, double y1, double c1, double c2, double x2, double y2)
    {
        curve = new QuadCurve(x1, y1, c1, c2, x2, y2);
        curve.setFill(Color.TRANSPARENT);
        curve.setStroke(Color.WHITE);
        Launcher.getBlueprint().addShape(curve);
        addEdgeDetection();
    }

    private void addEdgeDetection()
    {
        double scale = Blueprint.getScale();

        c1 = new Circle(15*scale);
        c2 = new Circle(15*scale);

        c1.setCenterX(curve.getStartX());
        c1.setCenterY(curve.getStartY());
        c1.setFill(Color.TRANSPARENT);
        c2.setCenterX(curve.getEndX());
        c2.setCenterY(curve.getEndY());
        c2.setFill(Color.TRANSPARENT);

        curveBorder = new QuadCurve(curve.getStartX(), curve.getStartY(), curve.getControlX(), curve.getControlY(), curve.getEndX(), curve.getEndY());
        curveBorder.setStroke(Color.TRANSPARENT);
        curveBorder.setFill(Color.TRANSPARENT);
        curveBorder.setStrokeWidth(10 * scale);

        Blueprint blueprint = Launcher.getBlueprint();
        blueprint.addShape(c1);
        blueprint.addShape(c2);
        blueprint.addShape(curveBorder);
        addEdgeDetectionEventHandlers();
    }

    private void addEdgeDetectionEventHandlers() {
        EventHandler<MouseEvent> enterEdge = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                updateCurve(mouseEvent.getX(), mouseEvent.getY());
                System.out.println(mouseEvent.getSource());
                updateEdgeDetection();
            }
        };
        c1.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, enterEdge);
        c2.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, enterEdge);
    }

    public void updateCurve(double x1, double y1, double c1, double c2, double x2, double y2)
    {
        curve.setStartX(x1);
        curve.setStartY(y1);
        curve.setControlX(c1);
        curve.setControlY(c2);
        curve.setEndX(x2);
        curve.setEndY(y2);
    }
    public void updateCurve(double x2, double y2)
    {
       curve.setEndX(x2);
       curve.setEndY(y2);

       updateEdgeDetection();
    }
    public void setControl(double c1, double c2)
    {
        curve.setControlX(c1);
        curve.setControlY(c2);
    }
    private void updateEdgeDetection()
    {
        c2.setCenterX(curve.getEndX());
        c2.setCenterY(curve.getEndY());
        curveBorder.setControlX(curve.getControlX());
        curveBorder.setControlY(curve.getControlY());
        curveBorder.setEndX(curve.getEndX());
        curveBorder.setEndY(curve.getEndY());
    }


    @Override
    public void setEndCoord(double x2, double y2) {
    }
}
