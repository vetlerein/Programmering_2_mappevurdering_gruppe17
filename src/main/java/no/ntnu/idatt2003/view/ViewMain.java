package no.ntnu.idatt2003.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import no.ntnu.idatt2003.controller.laddergameController.GameController;
import no.ntnu.idatt2003.model.Game;

/**
 * This is the launch method for the program and the main GUI class.
 */
public class ViewMain extends Application{
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
    
        BorderPane mainLayout = new BorderPane();
        StackPane centered = new StackPane();   
        centered.setId("background");
        VBox allButtons = new VBox();
        allButtons.setId("allButtons");
        HBox splitButtons = new HBox();
        splitButtons.setId("splitButtons");

        Button laddergameButton = new Button("Laddergame");
        laddergameButton.setId("laddergameButton");
        laddergameButton.setOnAction(e -> {
            
            LaddergameView laddergameView = new LaddergameView();
            GenericGameView genericGameView = new GenericGameView();
            BorderPane laddergame = laddergameView.mainLayout();
            
            Scene laddergameScene = new Scene(laddergame, 800, 600);
            laddergameScene.getStylesheets().add(getClass().getResource("/Style/Laddergame.css").toExternalForm());         
            window.setScene(laddergameScene);
            window.setTitle("Laddergame");
    
            GameController.setLadderGame(laddergameView);
            Game.setView(genericGameView);
            Game.setLadderView(laddergameView);
        });
        Button othergameButton = new Button("Other game");
        othergameButton.setId("othergameButton");
        othergameButton.setOnAction(e -> {
            

            
        });
        Button exitButton = new Button("Exit");
        exitButton.setId("exitButton");
        exitButton.setOnAction(e -> {
            
            System.exit(0);

        });


        Label welcome = new Label("Welcome!");
        Label description = new Label("Choose a game to play:");
        welcome.setId("welcomeLabel");
        description.setId("descriptionLabel");
        splitButtons.getChildren().addAll(laddergameButton, othergameButton);
        allButtons.getChildren().addAll(welcome, description, splitButtons, exitButton);
        centered.getChildren().add(allButtons);
        mainLayout.setCenter(centered);

        Scene startScene = new Scene(mainLayout, 800, 600);
        startScene.getStylesheets().add(getClass().getResource("/Style/Launcherwindow.css").toExternalForm());
        window.setScene(startScene);
        window.setTitle("Main menu");
        window.getIcons().add(new Image(getClass().getResourceAsStream("/playerPieces/pepperoni.png")));
        window.show();
    }
}