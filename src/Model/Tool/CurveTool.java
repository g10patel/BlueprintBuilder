package Model.Tool;

import Launcher.Launcher;
import Model.Blueprint.Blueprint;
import Model.Shapes.CurveShape;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class CurveTool implements Tool{
    private int clickIndex;
    private CurveShape currShape;
    private EventHandler<MouseEvent> followEvent;
    private EventHandler<MouseEvent> clickEvent;
    private EventHandler<MouseEvent> dragEvent;
    private EventHandler<KeyEvent> escapeEvent;
    private static CurveShape firstShape;
    private boolean atEdge;
    private CurveShape prevShape;
    public CurveTool()
    {
        initialize();
        clickIndex = 0;
        firstShape = null;
        atEdge = false;
    }

    public static CurveShape getFirstShape() {
        return firstShape;
    }
    public void setAtEdge(boolean x)
    {
        atEdge = x;
    }

    @Override
    public void delete() {
        DimensionTool.removeTempDimension();
        currShape.remove();
        removeEventHandlers();
        Launcher.getBlueprint().setCurrTool(null);
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
        escapeEvent = keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE)
            {
              delete();
            }
        };
        Launcher.getStackPane().addEventHandler(KeyEvent.KEY_PRESSED, escapeEvent);
    }

    private void removeEventHandlers()
    {
        StackPane stackPane = Launcher.getStackPane();
        stackPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
        stackPane.removeEventHandler(MouseEvent.MOUSE_MOVED, followEvent);
        stackPane.removeEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent);
        stackPane.removeEventHandler(KeyEvent.KEY_PRESSED, escapeEvent);
    }

    private void addClickEventHandler()
    {
        clickEvent = mouseEvent -> {
            //First Click
            //Control Click
            //Final Click trigger another mouse click to add new curve
            switch (clickIndex) {
                case (0) -> {
                    currShape = new CurveShape(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
                    Blueprint.setCurrShape(currShape);
                    if (firstShape == null) {
                        firstShape = currShape;
                    }
                    if(!atEdge) {
                        DimensionTool.showDimensions(currShape.getStartX(), currShape.getStartY(), currShape.getEndX(), currShape.getEndY());
                    }
                    else
                    {
                        currShape.setStartX(prevShape.getEndX());
                        currShape.setStartY(prevShape.getEndY());
                    }
                    clickIndex = 1;
                }
                case (1) -> {
                    currShape.setControl(mouseEvent.getX(), mouseEvent.getY());
                    clickIndex = 2;
                }
                default -> {
                    if(!atEdge)
                    {
                        currShape.setEndCoord(mouseEvent.getX(), mouseEvent.getY());
                    }
                    prevShape = currShape;
                    clickIndex = 0;
                    DimensionTool.removeTempDimension();
                    Event.fireEvent(Launcher.getBlueprint(), new MouseEvent(MouseEvent.MOUSE_CLICKED, mouseEvent.getSceneX(),
                            mouseEvent.getSceneY(), 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                            true, true, true, true, true, true, null));
                }
            }
        };
        Launcher.getStackPane().addEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);

    }
    private void addDragEventHandler()
    {
        dragEvent = mouseEvent -> {
            if(currShape != null) {
                currShape.setEndCoord(mouseEvent.getX(), mouseEvent.getY());
                DimensionTool.updateDimension(currShape.getStartX(), currShape.getStartY(), currShape.getEndX(), currShape.getEndY());
            }
        };
        Launcher.getStackPane().addEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent);
    }
    private void addMouseFollowEventHandler()
    {
        followEvent = mouseEvent -> {
            if(currShape != null) {
                currShape.setEndCoord(mouseEvent.getX(), mouseEvent.getY());
                DimensionTool.updateDimension(currShape.getStartX(), currShape.getStartY(), currShape.getEndX(), currShape.getEndY());

            }
        };
        Launcher.getStackPane().addEventHandler(MouseEvent.MOUSE_MOVED, followEvent);
    }

    @Override
    public void addFollowEvent() {
        addMouseFollowEventHandler();
    }

    public void removeFollowEvent()
    {
       Launcher.getStackPane().removeEventHandler(MouseEvent.MOUSE_MOVED, followEvent);
    }




}
