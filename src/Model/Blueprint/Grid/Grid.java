package Model.Blueprint.Grid;

import Launcher.Launcher;
import Model.Blueprint.Blueprint;
import Model.Blueprint.Grid.Threads.GridThreadBuilder;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


//Multitreading add grid to each


public class Grid {
    private static GridDensity currGridDensity = GridDensity.MEDIUM;

    public enum GridDensity {
        LOW(20, new Pane()),
        MEDIUM(15, new Pane()),
        HIGH(10, new Pane()),
        ULTRA(5, new Pane());

        private final int value;
        private final Pane gridPane;

        GridDensity(int value, Pane x) {
            this.value = value;
            this.gridPane = x;
        }
        public int getValue() {
            return value;
        }
        public Pane getGridPane() {
            return gridPane;
        }
    }

    public Grid() {
        createGridLines();
    }

    private void createGridLines() {

        for (GridDensity den : GridDensity.values()) {
            Thread curr = GridThreadBuilder.GridThread(den);
            curr.start();
        }
        StackPane stackPane = Launcher.getStackPane();
        stackPane.getChildren().add(GridDensity.MEDIUM.gridPane);
    }

    public static void zoomOut() {
        switch(currGridDensity.getValue()){
            case(20):
                updateGrid(GridDensity.MEDIUM);
                break;
            case(15):
                updateGrid(GridDensity.HIGH);
                break;
            case(10):
                updateGrid(GridDensity.ULTRA);
                break;
            default:
                updateGrid(GridDensity.LOW);
                break;
        }
    }


    public static void zoomIn() {
        switch(currGridDensity.getValue()){
            case(20):
                updateGrid(GridDensity.ULTRA);
                break;
            case(15):
                updateGrid(GridDensity.HIGH);
                break;
            case(10):
                updateGrid(GridDensity.MEDIUM);
                break;
            default:
                updateGrid(GridDensity.LOW);
                break;
        }
    }
    private static void updateGrid(GridDensity x) {
        StackPane stackPane = Launcher.getStackPane();
        stackPane.getChildren().remove(currGridDensity.getGridPane());
        currGridDensity = x;
        stackPane.getChildren().add(1,x.getGridPane());

    }
}
