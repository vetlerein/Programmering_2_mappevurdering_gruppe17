package no.ntnu.idatt2003.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This is the launch method for the program and the main GUI class.
 */
public class View extends Application{
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {


        GridPane mainLayout = new GridPane(); 
        mainLayout.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        Scene scene = new Scene(mainLayout, 800, 600, Color.LIGHTSKYBLUE);
        window.setScene(scene);
        window.setTitle("Laddergame");
        window.show();
    }
}