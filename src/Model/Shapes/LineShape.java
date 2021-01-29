package Model.Shapes;

import Launcher.Launcher;
import Model.Blueprint.Blueprint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class LineShape extends Line implements Shape {
    private Line line;
    Line borderEdge;
    Circle c1;
    Circle c2;
    public LineShape(double x1, double y1, double x2, double y2)
    {
        this.setStartX(x1);
        this.setStartY(y1);
        this.setEndX(x2);
        this.setEndY(y2);
        line = this;
        line.setStroke(Color.WHITE);

        addEdgeDetection();
    }

    private void addEdgeDetection()
    {
        double scale = Blueprint.getScale();
        c1 = new Circle(15*scale);
        c2 = new Circle(15*scale);

        c1.setCenterX(line.getStartX());
        c1.setCenterY(line.getStartY());
        c1.setFill(Color.TRANSPARENT);
        c2.setCenterX(line.getEndX());
        c2.setCenterY(line.getEndY());
        c2.setFill(Color.TRANSPARENT);

        borderEdge = new Line(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        borderEdge.setStroke(Color.TRANSPARENT);
        borderEdge.setStrokeWidth(10*scale);

        Launcher.getBlueprint().addShape(c1);
        Launcher.getBlueprint().addShape(c2);
        Launcher.getBlueprint().addShape(borderEdge);

        //add Event handlers
    }

    public void updateLine(double x1, double y1, double x2, double y2)
    {
        line.setStartX(x1);
        line.setStartY(y1);
        line.setEndX(x2);
        line.setEndY(y2);
        updateEdgeDetection();
    }

    public void updateLine(double x2, double y2)
    {
        line.setEndX(x2);
        line.setEndY(y2);
        updateEdgeDetection();
    }

    private void updateEdgeDetection()
    {
        c1.setCenterX(line.getStartX());
        c1.setCenterY(line.getStartY());
        c2.setCenterX(line.getEndX());
        c2.setCenterY(line.getEndY());

        borderEdge.setStartX(line.getStartX());
        borderEdge.setStartY(line.getStartY());
        borderEdge.setEndX(line.getEndX());
        borderEdge.setEndY(line.getEndY());
    }
    public void addToBlueprint()
    {
        Launcher.getBlueprint().addShape(line);

    }

    public Line getLine()
    {
        return line;
    }

    public void remove()
    {
        Launcher.getBlueprint().removeShape(line);
        Launcher.getBlueprint().removeShape(c1);
        Launcher.getBlueprint().removeShape(c2);
        Launcher.getBlueprint().removeShape(borderEdge);
    }


    @Override
    public void setEndCoord(double x2, double y2) {

    }
}
