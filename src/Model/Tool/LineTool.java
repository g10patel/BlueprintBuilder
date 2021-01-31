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


public class LineTool implements Tool{
    private boolean firstClick;
    private LineShape currShape;
    private EventHandler<MouseEvent> clickEvent;
    private EventHandler<MouseEvent> dragEvent;
    private EventHandler<MouseEvent> followEvent;
    private EventHandler<KeyEvent> escapeEvent;
    private static LineShape firstShape;
    private boolean atEdge;
    private LineShape prevShape;
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

               currShape = new LineShape(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
               currShape.addToBlueprint();
               if(firstShape == null)
               {
                   firstShape = currShape;
               }
               Blueprint.setCurrShape(currShape);
               if(!atEdge) {
                   DimensionTool.showDimensions(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
               }
               else
               {
                   currShape.setStartX(prevShape.getEndX());
                   currShape.setStartY(prevShape.getEndY());
               }
               firstClick = false;
           }
           else
           {
               if(!atEdge) {
                   currShape.setEndCoord(mouseEvent.getX(), mouseEvent.getY());
               }
               firstClick = true;
               prevShape = currShape;
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
            if(currShape != null) {
                currShape.setEndCoord(mouseEvent.getX(), mouseEvent.getY());
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
            if(currShape != null && mouseEvent.getX() > 1 && mouseEvent.getY() > 1) {
                currShape.setEndCoord(mouseEvent.getX(), mouseEvent.getY());
                DimensionTool.updateDimension(currShape.getStartX(), currShape.getStartY(), currShape.getEndX(), currShape.getEndY());

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

    public static LineShape getFirstShape()
    {
        return firstShape;
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


}
