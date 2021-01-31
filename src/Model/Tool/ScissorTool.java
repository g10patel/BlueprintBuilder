package Model.Tool;

import Launcher.Launcher;
import Model.Shapes.Shape;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;


public class ScissorTool implements Tool{

    public ScissorTool()
    {
        addEventHandler();
    }

    private void addEventHandler()
    {
        EventHandler<MouseEvent> overObject = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("click");
                Shape hoveringOver = Launcher.getBlueprint().getShapeHoveringOver();
                if(hoveringOver != null)
                {
                    hoveringOver.remove();
                }
            }
        };

        Launcher.getStackPane().addEventHandler(MouseEvent.MOUSE_CLICKED, overObject);
    }


    @Override
    public void addFollowEvent() {
    }

    @Override
    public void removeFollowEvent() {

    }

    @Override
    public void setAtEdge(boolean x) {

    }

    @Override
    public void delete() {

    }
}
