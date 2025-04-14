package no.ntnu.idatt2003.view;

import java.net.URL;
import java.util.Random;

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
import no.ntnu.idatt2003.controller.laddergameController.GameController;
import no.ntnu.idatt2003.controller.laddergameController.PlayerController;
import no.ntnu.idatt2003.model.Board;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.tile.LadderTile;
import no.ntnu.idatt2003.model.tile.Tile;

public class LaddergameView implements PositionChangeObserver{
    public BorderPane mainLayout = new BorderPane();
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
                showDice(player.getDicePaths());
            }       
        });

        Button simulateDice = new Button("Simulate game");
        simulateDice.setOnAction(e -> {
            while(game.getGameStatus() == true){
                Player player = game.getPlayers().get(game.getActivePlayer());
                player.move(game);
                showDice(player.getDicePaths());
            }       
        });

        bottomBox.getChildren().add(throwDice);
        bottomBox.getChildren().add(simulateDice);

        BorderPane mainLayout = getMainLayout();

        gameBoard.setGridLinesVisible(true);

        mainLayout = mainLayout();
        StackPane gameBoardWithLadder = new StackPane(gameBoard, lines);
        gameBoardWithLadder.setId("gameBoardWithLadder");
        mainLayout.setCenter(gameBoardWithLadder);
        mainLayout.setBottom(bottomBox);
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
     * Shows the physical dice on the board
     * @param dicePaths the paths to the dice images
     */
    public void showDice(URL[] dicePaths) {
        StackPane centerStackPane = new StackPane();
        Pane dicePane = new Pane();
        dicePane.setId("dicePane");
        int size = 50;        
        for (URL dicePath : dicePaths) {
            ImageView diceImageView = new ImageView(dicePath.toExternalForm());
            diceImageView.setFitWidth(size);
            diceImageView.setFitHeight(size);

            Random random = new Random();

            double maxX = 400 - size;
            double maxY = 400 - size;

            double x = random.nextDouble() * maxX;
            double y = random.nextDouble() * maxY;

            diceImageView.setLayoutX(x);
            diceImageView.setLayoutY(y);

            diceImageView.setRotate(random.nextInt(0, 360));
            dicePane.getChildren().add(diceImageView);
        }

        mainLayout = mainLayout();
        centerStackPane.getChildren().clear();
        centerStackPane.getChildren().add(mainLayout.lookup("#gameBoardWithLadder"));
        centerStackPane.getChildren().add(dicePane);
        mainLayout.setCenter(centerStackPane);
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

    public void playerWon(Player player) {
        Pane winnerPane = new Pane();
        StackPane stackPane = (StackPane) mainLayout.lookup("#gameBoardWithLadder");

        winnerPane.getChildren().add(new Label(player.getPlayerName() + " has won the game!"));

        stackPane.getChildren().add(winnerPane);
        mainLayout.setCenter(stackPane);
    }
}
