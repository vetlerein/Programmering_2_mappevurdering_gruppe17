package no.ntnu.idatt2003.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This is the launch method for the program and the main GUI class.
 */
public class ViewMain extends Application{
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        
        LaddergameView laddergameView = new LaddergameView();
        BorderPane laddergame = laddergameView.mainLayout();
        
        Scene laddergameScene = new Scene(laddergame, 800, 600);
        laddergameScene.getStylesheets().add(getClass().getResource("/Style/Laddergame.css").toExternalForm());         
        window.setScene(laddergameScene);
        window.setTitle("Laddergame");
        
        window.show();
    }
}