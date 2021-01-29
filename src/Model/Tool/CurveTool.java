package Model.Tool;

import Launcher.Launcher;
import Model.Shapes.CurveShape;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.robot.Robot;

public class CurveTool {
    private int[] click = {0,1,2};
    private int clickIndex;
    private CurveShape currShape;
    public CurveTool()
    {
        initialize();
        clickIndex = 0;
    }

    private void initialize()
    {
        addEventHandlers();
    }

    private void addEventHandlers()
    {
        addEscapeEventHandler();
        addClickEventHandler();
        addDragEventHandler();
        addMouseFollowEventHandler();
    }

    private void addEscapeEventHandler()
    {

    }
    private void addClickEventHandler()
    {
        EventHandler<MouseEvent> clickEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch(clickIndex){
                    //First Click
                    case(0):
                        currShape = new CurveShape(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
                        clickIndex = 1;
                        break;
                    //Control Click
                    case(1):
                        currShape.setControl(mouseEvent.getX(), mouseEvent.getY());
                        clickIndex = 2;
                        break;
                    //Final Click trigger another mouse click to add new curve
                    default:
                        currShape.updateCurve(mouseEvent.getX(), mouseEvent.getY());
                        clickIndex = 0;
                        Event.fireEvent(Launcher.getBlueprint(), new MouseEvent(MouseEvent.MOUSE_CLICKED, mouseEvent.getSceneX(),
                                mouseEvent.getSceneY(), 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                                true, true, true, true, true, true, null));
                        break;

                }
            }
        };
        Launcher.getStackPane().addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);

    }
    private void addDragEventHandler()
    {
        EventHandler<MouseEvent> dragEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(currShape != null) {
                    currShape.updateCurve(mouseEvent.getX(), mouseEvent.getY());
                }
            }
        };
        Launcher.getStackPane().addEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent);
    }
    private void addMouseFollowEventHandler()
    {
        EventHandler<MouseEvent> followEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(currShape != null) {
                    currShape.updateCurve(mouseEvent.getX(), mouseEvent.getY());
                }
            }
        };
        Launcher.getStackPane().addEventHandler(MouseEvent.MOUSE_MOVED, followEvent);
    }






}
