package no.ntnu.idatt2003.view;

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

        //Right box
        VBox rightMenu = new VBox();
        rightMenu.setId("rightMenu");
        Button throwDice = new Button("Throw dice");
        throwDice.setOnAction(e -> {
            if(game.getGameStatus() == true){
                Player player = game.getPlayers().get(game.getActivePlayer());
                player.move(game);
                genericGameView.showDice(player.getDicePaths(), mainLayout);
            }       
        });

        Button simulateDice = new Button("Simulate game");
        simulateDice.setOnAction(e -> {
            int turns = 0;
            while(game.getGameStatus() == true && turns < 500) {
                Player player = game.getPlayers().get(game.getActivePlayer());
                player.move(game);
                turns++;
            }
            System.out.println("Game finished in " + turns/game.getPlayers().size() + " turns.");       
        });

        Label whosTurn = new Label(game.getPlayers().get(game.activePlayer) + "'s turn");
        whosTurn.setId("whosTurn");
        Label players = new Label("Players: ");

        VBox playersBox = new VBox();
        playersBox.setId("playersBox");
        for (Player player : game.getPlayers()){
            HBox pictureNameSplitter = new HBox(10);
            ImageView playerImage = new ImageView(player.getPicture().toExternalForm());
            playerImage.setFitWidth(40); 
            playerImage.setPreserveRatio(true); 
            Label playerLabel = new Label(player.getPlayerName()+": ");
            Label position = new Label(player.getPosition() + "");
            position.setId("position" + player.getPlayerNumber());
            pictureNameSplitter.getChildren().addAll(playerImage, playerLabel, position);
            playersBox.getChildren().add(pictureNameSplitter);
        }

        rightMenu.getChildren().addAll(throwDice, simulateDice, whosTurn, players, playersBox);

        gameBoard.setGridLinesVisible(true);

        mainLayout = mainLayout();
        StackPane gameBoardWithLadder = new StackPane(gameBoard, lines);
        gameBoardWithLadder.setId("gameBoardWithLadder");
        mainLayout.setCenter(gameBoardWithLadder);
        mainLayout.setRight(rightMenu);
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
    
        Label turnLabel = (Label) mainLayout.lookup("#whosTurn");
        if (game.getActivePlayer()+1 >= game.getPlayers().size()) {
            turnLabel.setText(game.getPlayers().get(0) + "'s turn");
        } else {
            turnLabel.setText(game.getPlayers().get(game.getActivePlayer()+1) + "'s turn");
        }

        Label positionLabel = (Label) mainLayout.lookup("#position" + player.getPlayerNumber());
        if (positionLabel != null) {
            positionLabel.setText(player.getPosition() + "");
        } else {
            System.out.println("Position label not found for player " + player.getPlayerNumber());
        }

    }
}
