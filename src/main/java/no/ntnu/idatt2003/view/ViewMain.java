package no.ntnu.idatt2003.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This is the launch method for the program and the main GUI class.
 */
public class ViewMain extends Application {

  /**
   * The main method that launches the JavaFX application.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Starts the JavaFX application.
   *
   * @param window the primary stage for this application
   * @throws Exception if an error occurs
   */
  @Override
  public void start(Stage window) throws Exception {

    MenuView menuView = new MenuView();
    BorderPane mainLayout = menuView.mainMenu(window);

    Scene startScene = new Scene(mainLayout, 800, 600);
    startScene.getStylesheets()
        .add(getClass().getResource("/Style/Launcherwindow.css").toExternalForm());
    window.setScene(startScene);
    window.setTitle("Main menu");
    window.getIcons().add(new Image(getClass().getResourceAsStream("/playerPieces/pepperoni.png")));
    window.show();
  }
}
