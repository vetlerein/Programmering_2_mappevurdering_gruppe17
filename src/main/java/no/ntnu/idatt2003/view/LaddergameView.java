package no.ntnu.idatt2003.view;

import java.net.URL;

import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import no.ntnu.idatt2003.controller.laddergameController.GameController;
import no.ntnu.idatt2003.controller.laddergameController.PlayerController;
import no.ntnu.idatt2003.model.Board;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.tile.LadderTile;
import no.ntnu.idatt2003.model.tile.Tile;

public class LaddergameView implements PositionChangeObserver{
    public BorderPane mainLayout = new BorderPane();
    public GenericGameView genericGameView = new GenericGameView();
    private Game game;
    public int playerSize = 30;
  
    //Main layout
    public BorderPane mainLayout(){

        PlayerController playerController = new PlayerController();
        GameController gameController = new GameController();

        this.mainLayout.setId("mainLayout");

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
                
        //Adding everything to the final window
        mainLayout.setTop(topMenu);
        mainLayout.setRight(rightMenu);
        return mainLayout;
    }

    /**
     * Shows the physical board
     * @param game the active game
     * @return borderpane with the board
     */
    public void setGameBoard(Game game) {
        this.game = game;
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
                int iconSize = 40;
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
                        URL urlSwap = getClass().getResource("/tiles/arrows.png");
                        ImageView swapImage = new ImageView(urlSwap.toExternalForm());
                        swapImage.setFitWidth(iconSize);
                        swapImage.setFitHeight(iconSize);
                        tilePane.getChildren().add(swapImage);
                        break;
                    case "BackToStartTile":
                        tilePane.setStyle("-fx-background-color:rgba(255, 81, 0, 0.85);");
                        break;
                    case "PauseTile":
                        tilePane.setStyle("-fx-background-color:rgb(89, 35, 198);");
                        URL urlWatch = getClass().getResource("/tiles/stopwatch.png");
                        ImageView pauseImage = new ImageView(urlWatch.toExternalForm());
                        pauseImage.setFitWidth(iconSize);
                        pauseImage.setFitHeight(iconSize);
                        tilePane.getChildren().add(pauseImage);
                        break;
                    case "FinishTile":
                        tilePane.setStyle("-fx-background-color:rgb(0, 251, 255)");
                        break;
                }
                tilePane.getChildren().add(new Label(String.valueOf(game.getBoard().getLocation(j, i))));
                gameBoard.add(tilePane, j, i); 
            }
        }
        //TODO Add properties/observers for moving lines
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <cols; j++) {
                Tile tile = game.getBoard().getGameboard().get(game.getBoard().getLocation(j, i)-1);
                if (tile instanceof LadderTile ladderTile) {
                    int[] coordinatesEnd = game.getBoard().getCoordinates(ladderTile.getTravelLocation());
                    StackPane startTile = getTileAt(gameBoard, j, i);
                    StackPane endTile = getTileAt(gameBoard, coordinatesEnd[0], coordinatesEnd[1]);

                    Line ladderLine = new Line();
                    ladderLine.startXProperty().bind(startTile.layoutXProperty().add(startTile.widthProperty().divide(2)));
                    ladderLine.startYProperty().bind(startTile.layoutYProperty().add(startTile.heightProperty().divide(2)));
                    ladderLine.endXProperty().bind(endTile.layoutXProperty().add(endTile.widthProperty().divide(2)));
                    ladderLine.endYProperty().bind(endTile.layoutYProperty().add(endTile.heightProperty().divide(2)));

                    if (tile.getLocation() > ladderTile.getTravelLocation()) {
                        ladderLine.setStyle("-fx-stroke: rgb(255, 0, 0); -fx-stroke-width: 2;");
                    } else {
                        ladderLine.setStyle("-fx-stroke: rgb(26, 199, 35); -fx-stroke-width: 2;");
                    }
                    
                    lines.getChildren().add(ladderLine);
                }
            }
        }

        //Adds the players pieces to the board
        for (Player player : game.getPlayers()){
            StackPane tilePane = getTileAt(gameBoard, 0, game.getBoard().getRows()-1);
            ImageView playerImage = new ImageView(player.getPicture().toExternalForm());
            playerImage.setFitWidth(playerSize);
            playerImage.setFitHeight(playerSize);
            playerImage.setId("player" + player.getPlayerNumber());
            tilePane.getChildren().add(playerImage);
            player.setObserver(this);
        }
        //Bottom box
        HBox bottomBox = new HBox();
        bottomBox.setId("bottomBox");
        Button throwDice = new Button("Throw dice");
        throwDice.setOnAction(e -> {
            if(game.getGameStatus() == true){
                Player player = game.getPlayers().get(game.getActivePlayer());
                player.move(game);
                genericGameView.showDice(player.getDicePaths(), mainLayout);
            }       
        });

        Button simulateDice = new Button("Simulate game");
        PauseTransition pause = new PauseTransition(Duration.millis(500));
        simulateDice.setOnAction(e -> {
            simulateGame(0, 2000);     
        });

        bottomBox.getChildren().add(throwDice);
        bottomBox.getChildren().add(simulateDice);

        gameBoard.setGridLinesVisible(true);

        mainLayout = mainLayout();
        StackPane gameBoardWithLadder = new StackPane(gameBoard, lines);
        gameBoardWithLadder.setId("gameBoardWithLadder");
        mainLayout.setCenter(gameBoardWithLadder);
        mainLayout.setBottom(bottomBox);
    }

    public void simulateGame(int currentTurn, int maxTurns) {
        if (currentTurn > maxTurns || !game.getGameStatus()) {
            return;
        }
    
        Player player = game.getPlayers().get(game.getActivePlayer());
        player.move(game);
        genericGameView.showDice(player.getDicePaths(), mainLayout);

        PauseTransition pause = new PauseTransition(Duration.millis(100));
        pause.setOnFinished(e -> {
            simulateGame(currentTurn + 1, maxTurns); 
        });
        pause.play();
    }
    

    private StackPane getTileAt(GridPane grid, int col, int row) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return (StackPane) node;
            }
        }
        return null;
    }

    /**
     * Returns the main layout of the game
     * @return the main layout of the game
     */
    public BorderPane getMainLayout() {
        return mainLayout;
    }

    /**
     * Updates the position of the player on the board
     * @param player the player to update
     * @param newPosition the new position of the player
     */
    @Override
    public void positionChanged(Player player) {
        int[] coordiantes = this.game.getBoard().getCoordinates(player.getPosition());
        StackPane tilePane = getTileAt((GridPane) mainLayout.lookup("#gameBoard"), coordiantes[0], coordiantes[1]);
        String id = "player" + player.getPlayerNumber();
        Node node = mainLayout.lookup("#"+id);

        if (node != null) {
            ((Pane) node.getParent()).getChildren().remove(node);
        }
        ImageView playerImage = new ImageView(player.getPicture().toExternalForm());
        playerImage.setId(id);
        playerImage.setFitWidth(playerSize);
        playerImage.setFitHeight(playerSize);
        tilePane.getChildren().add(playerImage);
    }

}
