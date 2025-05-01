package no.ntnu.idatt2003.view;

import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import no.ntnu.idatt2003.controller.LaddergameController;
import no.ntnu.idatt2003.controller.PlayerController;
import no.ntnu.idatt2003.model.Game;

public class MenuView {

     /**
     * @param stage main stage of the application
     * @return Returns the menu layout
     * @throws IOException
     */
    public BorderPane mainMenu(Stage stage) throws IOException {

        BorderPane mainLayout = new BorderPane();
        StackPane centered = new StackPane();   
        centered.setId("background");
        VBox allButtons = new VBox();
        allButtons.setId("allButtons");
        HBox splitButtons = new HBox();
        splitButtons.setId("splitButtons");

        Button laddergameButton = new Button("Laddergame");
        laddergameButton.setOnAction(e -> {
            
            LaddergameView laddergameView = new LaddergameView();
            GenericGameView genericGameView = new GenericGameView();
            BorderPane laddergame = laddergameView.mainLayout();
            
            Scene laddergameScene = new Scene(laddergame, stage.getWidth(), stage.getHeight());
            laddergameScene.getStylesheets().add(getClass().getResource("/Style/Laddergame.css").toExternalForm());         
            stage.setScene(laddergameScene);
            stage.setTitle("Laddergame");
    
            LaddergameController.setLadderGame(laddergameView);
            Game.setView(genericGameView);
            Game.setLadderView(laddergameView);
        });


        Button monopolyButton = new Button("Monopoly");
        monopolyButton.setOnAction(e -> {
            
            MonopolyView monopolyView = new MonopolyView();

            BorderPane monopoly = monopolyView.mainLayout();

            Scene monopolyScene = new Scene(monopoly, stage.getWidth(), stage.getHeight());
            monopolyScene.getStylesheets().add(getClass().getResource("/Style/Monopoly.css").toExternalForm());         
            stage.setScene(monopolyScene);
            stage.setTitle("Monopoly");

            
        });
        
        Button newPlayerButton = new Button("New player");
        newPlayerButton.setOnAction(e -> {
            PlayerController playerController = new PlayerController();
            playerController.addPlayerWindow();
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
        splitButtons.getChildren().addAll(laddergameButton, monopolyButton);
        allButtons.getChildren().addAll(welcome, description, splitButtons, newPlayerButton, exitButton);
        centered.getChildren().add(allButtons);
        mainLayout.setCenter(centered);

        return mainLayout;
    }
}
