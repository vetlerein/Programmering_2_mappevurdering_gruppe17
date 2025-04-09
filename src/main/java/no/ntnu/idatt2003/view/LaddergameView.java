package no.ntnu.idatt2003.view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import no.ntnu.idatt2003.controller.laddergameController.GameController;
import no.ntnu.idatt2003.controller.laddergameController.PlayerController;
import no.ntnu.idatt2003.model.Board;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.tile.LadderTile;
import no.ntnu.idatt2003.model.tile.Tile;

public class LaddergameView {
    //Main layout
    public BorderPane mainLayout(){

        PlayerController playerController = new PlayerController();
        GameController gameController = new GameController();

        BorderPane mainLayout = new BorderPane();
        mainLayout.setId("mainLayout");


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
        
        return mainLayout;
    }

    /**
     * Shows the physical board
     * @param game the active game
     * @param mainLayout the current mainLayout border pane
     * @return borderpane with the board
     */
    public BorderPane setGameBoard(Game game, BorderPane mainLayout) {
        GridPane gameBoard = new GridPane();
        Pane lines = new Pane();
        gameBoard.setId("gameBoard");
        int tileSize = 60;
        int rows = game.getBoard().getRows();
        int cols = game.getBoard().getCols();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++){
                StackPane  tilePane = new StackPane();
                tilePane.setPrefSize(tileSize, tileSize);

                if(i%2 == 0) {
                    if(j%2 == 0) {
                        tilePane.setStyle("-fx-background-color:rgb(190, 192, 203);");
                    } else {
                        tilePane.setStyle("-fx-background-color:rgb(255, 255, 255);");
                    }
                } else {
                    if(j%2 == 0) {
                        tilePane.setStyle("-fx-background-color:rgb(255, 255, 255);");
                    } else {
                        tilePane.setStyle("-fx-background-color:rgb(190, 192, 203);");
                    }
                }

                Board board = game.getBoard();
                Tile tile = board.getGameboard().get(board.getLocation(j, i)-1);
                
                switch (tile.getClass().getSimpleName()) {
                    case "LadderTile":
                        if (tile.getLocation() < ((LadderTile) tile).getTravelLocation()) {
                            tilePane.setStyle("-fx-background-color:rgb(20, 161, 20);");
                        } else {
                            tilePane.setStyle("-fx-background-color:rgb(255, 0, 0);");
                        }
                        break;
                    case "PlayerSwapTile":
                        tilePane.setStyle("-fx-background-color:rgb(223, 234, 22);");
                        break;
                    case "BackToStartTile":
                        tilePane.setStyle("-fx-background-color:rgba(255, 81, 0, 0.85);");
                        break;
                    case "PauseTile":
                        tilePane.setStyle("-fx-background-color:rgb(89, 35, 198);");
                        break;
                    case "FinishTile":
                        tilePane.setStyle("-fx-background-color:rgb(0, 251, 255)");
                        break;
                }
                gameBoard.add(tilePane, j, i); 
            }
        }
        //TODO Add properties/observers for moving lines
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <cols; j++) {
                Tile tile = game.getBoard().getGameboard().get(game.getBoard().getLocation(j, i)-1);
                if (tile instanceof LadderTile ladderTile) {
                    int[] coordinatesEnd = game.getBoard().getCoordinates(ladderTile.getTravelLocation());
                    Line ladderLine = new Line(j * tileSize + tileSize / 2.0, i * tileSize + tileSize / 2.0,
                        coordinatesEnd[0] * tileSize + tileSize / 2.0, coordinatesEnd[1] * tileSize + tileSize / 2.0);
                    if (tile.getLocation() > ladderTile.getTravelLocation()) {
                        ladderLine.setStyle("-fx-stroke: rgb(255, 0, 0); -fx-stroke-width: 2;");
                    } else {
                        ladderLine.setStyle("-fx-stroke: rgb(26, 199, 35); -fx-stroke-width: 2;");
                    }
                    
                    lines.getChildren().add(ladderLine);
                }
            }
        }

        gameBoard.setGridLinesVisible(true);
        StackPane gameBoardWithLadder = new StackPane(gameBoard, lines);
        mainLayout.setCenter(gameBoardWithLadder);
        
        return mainLayout;
    }
}
