package Model.Tool;

import Launcher.Launcher;
import Model.Blueprint.Blueprint;
import Model.Shapes.LineShape;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

import java.util.Stack;

public class LineTool implements Tool{
    private boolean firstClick;
    private LineShape currLine;
    private EventHandler<MouseEvent> clickEvent;
    private EventHandler<MouseEvent> dragEvent;
    private EventHandler<MouseEvent> followEvent;
    private EventHandler<KeyEvent> escapeEvent;
    private static LineShape firstShape;
    private boolean atEdge;
    public LineTool()
    {
        initialize();
    }

    private void initialize()
    {
        addEscapeEventHandler();
        addClickEventHandler();
        addDragEventHandler();
        addMouseFollowEventHandler();
        atEdge = false;
        firstShape = null;
        firstClick = true;
    }

    private void addClickEventHandler()
    {
        EventHandler<MouseEvent> mouseClicked = mouseEvent -> {
           if(firstClick)
           {

               currLine = new LineShape(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
               currLine.addToBlueprint();
               if(firstShape == null)
               {
                   firstShape = currLine;
               }
               Blueprint.setCurrShape(currLine);
               DimensionTool.showDimensions(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
               firstClick = false;
           }
           else
           {
               if(!atEdge) {
                   currLine.setEndCoord(mouseEvent.getX(), mouseEvent.getY());
               }
               firstClick = true;
               DimensionTool.removeTempDimension();
               Event.fireEvent(Launcher.getBlueprint(), new MouseEvent(MouseEvent.MOUSE_CLICKED, mouseEvent.getSceneX(),
                       mouseEvent.getSceneY(), 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                       true, true, true, true, true, true, null));

           }
        };
        clickEvent = mouseClicked;
        Launcher.getStackPane().addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClicked);
    }

    private void addDragEventHandler()
    {
        EventHandler<MouseEvent> mouseDragged = mouseEvent -> {
            if(currLine != null) {
                currLine.setEndCoord(mouseEvent.getX(), mouseEvent.getY());
            }
        };
        Launcher.getStackPane().addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDragged);
        dragEvent = mouseDragged;
    }

    private void addEscapeEventHandler()
    {
        Launcher.getStackPane().requestFocus();
        EventHandler<KeyEvent> escapePressed = keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE)
            {
                delete();
            }
        };
        Launcher.getStackPane().addEventHandler(KeyEvent.KEY_PRESSED, escapePressed);
        escapeEvent = escapePressed;
    }

    private void addMouseFollowEventHandler()
    {
        followEvent = mouseEvent -> {
            if(currLine != null && mouseEvent.getX() > 1 && mouseEvent.getY() > 1) {
                currLine.setEndCoord(mouseEvent.getX(), mouseEvent.getY());
                DimensionTool.updateDimension(currLine.getStartX(), currLine.getStartY(), currLine.getEndX(), currLine.getEndY());

            }
        };
        Launcher.getStackPane().addEventHandler(MouseEvent.MOUSE_MOVED, followEvent);

    }

    private void removeEventHandlers()
    {
        StackPane stackPane = Launcher.getStackPane();
        stackPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, clickEvent);
        stackPane.removeEventHandler(MouseEvent.MOUSE_MOVED, followEvent);
        stackPane.removeEventHandler(MouseEvent.MOUSE_DRAGGED, dragEvent);
        stackPane.removeEventHandler(KeyEvent.KEY_PRESSED, escapeEvent);
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
        currLine.remove();
        removeEventHandlers();
        Launcher.getBlueprint().setCurrTool(null);
    }
}
