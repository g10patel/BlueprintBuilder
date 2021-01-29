package Model;

import javafx.scene.layout.Pane;

import java.util.Date;

public class Layer extends Pane {
    private int layerId;
    public Layer()
    {
       layerId =  (int) (new Date().getTime()/1000);
    }

}
