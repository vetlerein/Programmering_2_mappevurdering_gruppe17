package no.ntnu.idatt2003.view;

import java.io.IOException;

import javafx.application.Application;
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
    
    window.show();

    }
}