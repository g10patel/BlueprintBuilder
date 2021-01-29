package Main;

import Launcher.Launcher;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Launcher launch = new Launcher();
        launch.launch(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
