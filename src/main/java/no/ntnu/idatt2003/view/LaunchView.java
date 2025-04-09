package no.ntnu.idatt2003.view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

//TODO: Implement this

public class LaunchView {
    
    public BorderPane mainLayout(){

        BorderPane mainLayout = new BorderPane();
        StackPane centered = new StackPane();
        VBox mainMenu = new VBox();
        HBox splitButtons = new HBox();
        Button laddergameButton = new Button("Laddergame");
        laddergameButton.setId("laddergameButton");
        laddergameButton.setOnAction(e -> {
            
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
        mainMenu.setId("mainMenu");


        centered.getChildren().add(mainMenu);
        mainLayout.setCenter(centered);
        centered.setId("background");
        
        mainLayout.setId("mainLayout");
        
        return mainLayout;

    }
}
