package no.ntnu.idatt2003.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;


/**
 * This is the launch method for the program and the main GUI class.
 */
public class View extends Application{
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {

        //Main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setId("mainLayout");

        //Middle box
        GridPane gameBoard = new GridPane();
        gameBoard.setId("gameBoard");
          //gameBoard.setGridLinesVisible(true);    
        gameBoard.setHgap(10);
        gameBoard.setVgap(10);

        //Top box
        HBox topMenu = new HBox();
        topMenu.setId("topMenu");
        Button newGameButton = new Button("New game");
        Button newPlayerButton = new Button("New player");
        topMenu.getChildren().addAll(newGameButton, newPlayerButton);

        //Right box
        VBox rightMenu = new VBox();
        rightMenu.setId("rightMenu");

        //Bottom box
        StackPane bottomBox = new StackPane();
        bottomBox.setId("bottomBox");
        Button throwDice = new Button("Throw dice");
        bottomBox.getChildren().add(throwDice);

        //Scene
        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());
        
        //Adding everything to the final window
        mainLayout.setTop(topMenu);
        mainLayout.setRight(rightMenu);
        mainLayout.setBottom(bottomBox);
        mainLayout.setCenter(gameBoard);

        window.setScene(scene);
        window.setTitle("Laddergame");
        window.show();
    }
}