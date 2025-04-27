package no.ntnu.idatt2003.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

        MenuView menuView = new MenuView();
        BorderPane mainLayout = menuView.mainMenu(window);
        
        Scene startScene = new Scene(mainLayout, 800, 600);
        startScene.getStylesheets().add(getClass().getResource("/Style/Launcherwindow.css").toExternalForm());
        window.setScene(startScene);
        window.setTitle("Main menu");
        window.getIcons().add(new Image(getClass().getResourceAsStream("/playerPieces/pepperoni.png")));
        window.show();
    
    }
}
