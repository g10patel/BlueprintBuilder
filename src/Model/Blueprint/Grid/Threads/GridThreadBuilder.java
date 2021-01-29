package Model.Blueprint.Grid.Threads;

import Model.Blueprint.Blueprint;
import Model.Blueprint.Grid.Grid;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class GridThreadBuilder {
    public static Thread GridThread(Grid.GridDensity x)
    {


        Thread t1 = new Thread(){
            public void run(){
                Pane currPane = x.getGridPane();
                Rectangle2D primaryScreenBounds = Blueprint.getBounds();

                double width = primaryScreenBounds.getWidth();
                double height = primaryScreenBounds.getHeight();

                Grid.GridDensity currDensity = x;
                //Creates Horizontal Grid Lines
                for (int i = 1; i < height; i= i + x.getValue())
                {
                    Line lineX = new Line();
                    lineX.setStartX(0);
                    lineX.setStartY(i);
                    lineX.setEndX(width);
                    lineX.setEndY(i);
                    lineX.setStyle("-fx-stroke: #252a34");
                    lineX.setStrokeWidth(.5);
                    if(i % 50 == 0)
                    {
                        lineX.setStrokeWidth(.9);
                    }
                    currPane.getChildren().add(lineX);

                }
                for (int i = 1; i < width; i = i + x.getValue())
                {
                    Line lineY = new Line();
                    lineY.setStartX(i);
                    lineY.setStartY(0);
                    lineY.setEndX(i);
                    lineY.setEndY(height);
                    lineY.setStyle("-fx-stroke: #252a34");
                    lineY.setStrokeWidth(.5);
                    if(i % 50 == 0)
                    {
                        lineY.setStrokeWidth(.9);
                    }
                    currPane.getChildren().add(lineY);

                }

            }

        };
        return t1;


    }
}
