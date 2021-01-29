package Model.Tool;

import Launcher.Launcher;
import Model.Shapes.LineShape;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

public class LineTool extends Line {
    private boolean firstClick;
    private LineShape currLine;
    private EventHandler<MouseEvent> clickEvent;
    private EventHandler<MouseEvent> dragEvent;
    private EventHandler<MouseEvent> followEvent;
    private EventHandler<KeyEvent> escapeEvent;
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
    }

    private void addClickEventHandler()
    {
        EventHandler<MouseEvent> mouseClicked = mouseEvent -> {
           if(!firstClick)
           {
               currLine = new LineShape(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
               currLine.addToBlueprint();
               DimensionTool.showDimensions(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
               firstClick = true;
           }
           else
           {
               currLine.updateLine(currLine.getStartX(), currLine.getStartY(), mouseEvent.getX(), mouseEvent.getY());
               currLine = new LineShape(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
               currLine.addToBlueprint();

           }
        };
        clickEvent = mouseClicked;
        Launcher.getStackPane().addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClicked);
    }

    private void addDragEventHandler()
    {
        EventHandler<MouseEvent> mouseDragged = mouseEvent -> {
            if(firstClick) {
                currLine.updateLine(mouseEvent.getX(), mouseEvent.getY());
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
                DimensionTool.removeTempDimension();
                currLine.remove();
                removeEventHandlers();
            }
        };
        Launcher.getStackPane().addEventHandler(KeyEvent.KEY_PRESSED, escapePressed);
        escapeEvent = escapePressed;
    }

    private void addMouseFollowEventHandler()
    {
        EventHandler<MouseEvent> mouseMoved = mouseEvent -> {
            if(currLine != null && mouseEvent.getX() > 1 && mouseEvent.getY() > 1) {
                currLine.updateLine(mouseEvent.getX(), mouseEvent.getY());
                DimensionTool.updateDimension(currLine.getStartX(), currLine.getStartY(), currLine.getEndX(), currLine.getEndY());
                System.out.println(mouseEvent.getX());
                System.out.println(mouseEvent.getY());
            }
        };
        Launcher.getStackPane().addEventHandler(MouseEvent.MOUSE_MOVED, mouseMoved);
        followEvent = mouseMoved;
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
