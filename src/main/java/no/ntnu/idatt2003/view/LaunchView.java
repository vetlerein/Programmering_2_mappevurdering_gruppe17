package no.ntnu.idatt2003.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

//TODO: Implement this

public class LaunchView {
    
    public BorderPane mainLayout(){

        BorderPane mainLayout = new BorderPane();
        StackPane centered = new StackPane();
        HBox mainMenu = new HBox();
        mainMenu.setId("mainMenu");
        centered.getChildren().add(mainMenu);
        mainLayout.setCenter(centered);
        centered.setId("background");
        
        mainLayout.setId("mainLayout");
        return mainLayout;

    }
}
