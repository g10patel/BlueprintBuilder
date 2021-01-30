package Model.Tool;

import Launcher.Launcher;
import Model.Blueprint.Blueprint;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class DimensionTool {
    private static Text currDimension;


    public static void showDimensions(double x1, double y1, double x2, double y2)
    {
        currDimension = new Text(Double.toString(getDimension(x1,y1,x2,y2)));

        currDimension.setX((x1+x2)/2);
        currDimension.setY((y1+y2)/2+30);
        currDimension.setFill(Color.GREEN);
        Launcher.getBlueprint().addShape(currDimension);

    }

    private static double getDimension(double x1, double y1, double x2, double y2) {
        return (Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2))/12);
    }
    public static void updateDimension(double x1, double y1, double x2, double y2)
    {
        currDimension.setX((x1+x2)/2);
        currDimension.setY((y1+y2)/2+30);
        currDimension.setText(Double.toString(getDimension(x1,y1,x2,y2)));

    }

    public static void removeTempDimension()
    {
        Blueprint blueprint = Launcher.getBlueprint();
        blueprint.removeShape(currDimension);
    }

}
