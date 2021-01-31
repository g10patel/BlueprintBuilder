package Model;

import Model.Blueprint.Blueprint;
import Model.Tool.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ToolBar extends GridPane {
    private static Tool currTool = null;
    public ToolBar(){
        initialize(this);
        setupIcons(this);
    }

    public static Tool getCurrTool() {
        return currTool;
    }

    private void initialize(ToolBar toolBar)
    {
        this.setMinWidth(50);
        this.setMinHeight(Blueprint.getBounds().getHeight());
        this.setMaxWidth(Blueprint.getBounds().getWidth()/15);
        this.setStyle("-fx-background-color: #343c48");
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    }

    private void setupIcons(GridPane x)
    {
        Button line = Helper.pathToButton("resources/LineTool.png");
        line.setOnAction(actionEvent -> {
                if(currTool!= null) {
                    currTool.delete();
                }
                currTool = new LineTool();});
        x.add(line, 0,0,1,1);
        Button curve = Helper.pathToButton("resources/arc.png");
        curve.setOnAction(actionEvent -> {
            if(currTool != null) {
                currTool.delete();
            }
                currTool = new CurveTool();});
        x.add(curve, 0,1,1,1);
        Button scissor = Helper.pathToButton("resources/scissor.png");
        scissor.setOnAction(actionEvent -> {
            if(currTool != null) {
                currTool.delete();
            }
            currTool = new ScissorTool();
        });
        x.add(scissor,0,2,1,1);
        Button bezier = Helper.pathToButton("resources/bezier.png");
        bezier.setOnAction(actionEvent -> {
            if(currTool != null)
            {
                currTool.delete();
            }
            currTool = new BezierTool();
        });
        x.add(bezier, 0,3,1,1);
        Button door = Helper.pathToButton("resources/door.png");
        x.add(door, 0,4,1,1);
        Button window = Helper.pathToButton("resources/window.png");
        x.add(window, 0,5,1,1);
        Button wall = Helper.pathToButton("resources/wall.png");
        x.add(wall, 0,6,1,1);
        Button bulb = Helper.pathToButton("resources/bulb.png");
        x.add(bulb, 0,7,1,1);
        Button entryway = Helper.pathToButton("resources/entryway.png");
        x.add(entryway, 0,8,1,1);
        Button dimension = Helper.pathToButton("resources/dimension.png");
        x.add(dimension, 0,9,1,1);
        Button layer = Helper.pathToButton("resources/layerBlack.png");
        x.add(layer, 0,10,1,1);
    }

    public static void resetCurrTool()
    {
        currTool = null;
    }




}
