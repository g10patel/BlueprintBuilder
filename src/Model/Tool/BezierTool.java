package Model.Tool;

import Launcher.Launcher;
import Model.Blueprint.Blueprint;
import Model.Shapes.BezierShape;
import Model.Shapes.CurveShape;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class BezierTool implements Tool{
    private int clickIndex;
    private BezierShape currShape;
    private static BezierShape firstShape;
    private boolean atEdge;
    private EventHandler<MouseEvent> followEvent;
    private EventHandler<MouseEvent> clickEvent;
    private EventHandler<MouseEvent> dragEvent;
    private EventHandler<KeyEvent> escapeEvent;
    public BezierTool()
    {
        initialize();
        clickIndex =0;
        firstShape = null;
        atEdge = false;
    }

    private void initialize()
    {
        addEventHandlers();
    }

    public static BezierShape getFirstShape() {
        return firstShape;
    }

    private void addEventHandlers() {
        addEscapeEventHandler();
        addClickEventHandler();
        addDragEventHandler();
        addMouseFollowEventHandler();
    }

    private void addMouseFollowEventHandler() {
        followEvent = mouseEvent -> {
            if(currShape != null) {
                currShape.setEndCoord(mouseEvent.getX(), mouseEvent.getY());
                DimensionTool.updateDimension(currShape.getStartX(), currShape.getStartY(), currShape.getEndX(), currShape.getEndY());
                if(clickIndex == 2)
                {
                    currShape.setControl2(mouseEvent.getX(), mouseEvent.getY());
                }

            }
        };
        Launcher.getStackPane().addEventHandler(MouseEvent.MOUSE_MOVED, followEvent);
    }

    private void addDragEventHandler() {
        dragEvent = mouseEvent -> {
            if(currShape != null) {
                currShape.setEndCoord(mouseEvent.getX(), mouseEvent.getY());
                DimensionTool.updateDimension(currShape.getStartX(), currShape.getStartY(), currShape.getEndX(), currShape.getEndY());
            }
        };
        Launcher.getStackPane().addEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent);
    }

    private void addClickEventHandler() {
        clickEvent = mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            switch (clickIndex) {
                case (0) -> {
                    currShape = new BezierShape(x, y, x, y, x, y, x, y);
                    Blueprint.setCurrShape(currShape);
                    if (firstShape == null) {
                        firstShape = currShape;
                    }
                    clickIndex += 1;
                    DimensionTool.showDimensions(x, y, x, y);
                }
                case (1) -> {
                    currShape.setControl1(x, y);
                    currShape.setControl2(x, y);
                    clickIndex += 1;
                }
                case (2) -> {
                    currShape.setControl2(x, y);
                    clickIndex += 1;
                }
                default -> {
                    if (!atEdge) {
                        currShape.setEndCoord(x, y);
                    }
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

    private void addEscapeEventHandler() {
        escapeEvent = keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE)
            {
                delete();
            }
        };
        Launcher.getStackPane().addEventHandler(KeyEvent.KEY_PRESSED, escapeEvent);
    }



    @Override
    public void addFollowEvent() {
        addMouseFollowEventHandler();
    }

    @Override
    public void removeFollowEvent() {
        Launcher.getStackPane().removeEventHandler(MouseEvent.MOUSE_MOVED, followEvent);
    }

    @Override
    public void setAtEdge(boolean x) {
        atEdge = x;
    }

    @Override
    public void delete() {
        DimensionTool.removeTempDimension();
        currShape.remove();
        removeEventHandlers();
        Launcher.getBlueprint().setCurrTool(null);
    }

    private void removeEventHandlers()
    {
        StackPane stackPane = Launcher.getStackPane();
        stackPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
        stackPane.removeEventHandler(MouseEvent.MOUSE_MOVED, followEvent);
        stackPane.removeEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent);
        stackPane.removeEventHandler(KeyEvent.KEY_PRESSED, escapeEvent);
    }
}
