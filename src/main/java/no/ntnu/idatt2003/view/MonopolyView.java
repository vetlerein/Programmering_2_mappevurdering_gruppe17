package no.ntnu.idatt2003.view;

import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import no.ntnu.idatt2003.model.Game;

public class MonopolyView {

    public BorderPane mainLayout = new BorderPane();

    public BorderPane mainLayout(){
        mainLayout.setId("mainLayout");
        
        //Top box
        HBox topMenu = new HBox();
        topMenu.setId("topMenu");
        Button newGameButton = new Button("Start new game");
        //newGameButton.setOnAction();
        Button backToMenuButton = new Button("Main menu");
        backToMenuButton.setOnAction(e -> {
            Stage currentStage = (Stage) mainLayout.getScene().getWindow();
            try {
                Scene mainMenuScene = new Scene(new MenuView().mainMenu(currentStage), currentStage.getWidth(), currentStage.getHeight());
                mainMenuScene.getStylesheets().add(getClass().getResource("/Style/Launcherwindow.css").toExternalForm());
                currentStage.setScene(mainMenuScene);
                currentStage.setTitle("Main Menu");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        topMenu.getChildren().addAll(newGameButton, backToMenuButton);

        //Right box
        VBox rightMenu = new VBox();
        rightMenu.setId("rightMenu");
                
        //Bottom box
        HBox bottomMenu = new HBox();

        //Adding everything to the final window
        mainLayout.setTop(topMenu);
        mainLayout.setRight(rightMenu);
        mainLayout.setBottom(bottomMenu);

        return mainLayout;
    }

    public void setGameBoard(Game game){

    }
}
