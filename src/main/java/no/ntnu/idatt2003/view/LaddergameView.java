package no.ntnu.idatt2003.view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatt2003.controller.laddergameController.GameController;
import no.ntnu.idatt2003.controller.laddergameController.PlayerController;

public class LaddergameView {
    //Main layout
    public BorderPane mainLayout(){

        PlayerController playerController = new PlayerController();
        GameController gameController = new GameController();

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
        Button newGameButton = new Button("Start new game");
        newGameButton.setOnAction(e -> gameController.newGame());
        Button newPlayerButton = new Button("New player");
        newPlayerButton.setOnAction(e -> playerController.addPlayerWindow());
        topMenu.getChildren().addAll(newGameButton, newPlayerButton);

        //Right box
        VBox rightMenu = new VBox();
        rightMenu.setId("rightMenu");

        //Bottom box
        StackPane bottomBox = new StackPane();
        bottomBox.setId("bottomBox");
        Button throwDice = new Button("Throw dice");
        bottomBox.getChildren().add(throwDice);

        //Adding everything to the final window
        mainLayout.setTop(topMenu);
        mainLayout.setRight(rightMenu);
        mainLayout.setBottom(bottomBox);
        mainLayout.setCenter(gameBoard);

        return mainLayout;
    }
}
