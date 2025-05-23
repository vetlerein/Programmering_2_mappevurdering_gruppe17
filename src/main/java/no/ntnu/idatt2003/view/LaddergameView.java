package no.ntnu.idatt2003.view;

import java.io.IOException;
import java.net.URL;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import no.ntnu.idatt2003.controller.LaddergameController;
import no.ntnu.idatt2003.model.Board;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.tile.LadderTile;
import no.ntnu.idatt2003.model.tile.Tile;

/**
 * This class is responsible for displaying the game board and the players in the Laddergame.
 */
public class LaddergameView implements PositionChangeObserver {

  /**
   * The main layout of the game.
   */
  public BorderPane mainLayout = new BorderPane();

  /**
   * The game instance.
   */
  private Game game;

  /**
   * The size of the player pieces.
   */
  private final int playerSize = 25;

  /**
   * Boolean for if the animation is active.
   */
  private boolean animationActive = false;

  /**
   * The controller for the game.
   */
  private final LaddergameController laddergameController = new LaddergameController();

  /**
   * Creates the main layout of the game.
   *
   * @return the main layout of the game
   */
  public BorderPane mainLayout() {
    this.mainLayout.setId("mainLayout");

    //Top box
    HBox topMenu = new HBox();
    topMenu.setId("topMenu");
    Button newGameButton = new Button("Start new game");
    newGameButton.setOnAction(e -> laddergameController.newGame());
    Button backToMenuButton = new Button("Main menu");
    backToMenuButton.setOnAction(e -> {
      Stage currentStage = (Stage) mainLayout.getScene().getWindow();
      Scene currentScene = currentStage.getScene();
      try {
        Scene mainMenuScene = new Scene(new MenuView().mainMenu(currentStage),
            currentScene.getWidth(), currentScene.getHeight());
        mainMenuScene.getStylesheets()
            .add(getClass().getResource("/Style/Launcherwindow.css").toExternalForm());
        currentStage.setScene(mainMenuScene);
        currentStage.setTitle("Main Menu");
      } catch (IOException ex) {
        PopupView.showInfoPopup("Error reading styling sheet!", ex.getMessage());
      }
    });
    topMenu.getChildren().addAll(newGameButton, backToMenuButton);

    //Right box
    VBox rightMenu = new VBox();
    rightMenu.setId("rightMenu");

    //Adding everything to the final window
    mainLayout.setTop(topMenu);
    mainLayout.setRight(rightMenu);
    GenericGameView.setMainLayout(mainLayout);
    return mainLayout;
  }

  /**
   * Shows the physical board
   *
   * @param game the active game
   */
  public void setGameBoard(Game game) {
    this.game = game;
    GridPane gameBoard = new GridPane();
    Pane lines = new Pane();
    gameBoard.setId("gameBoard");
    int tileSize = 60;
    int rows = game.getBoard().getRows();
    int cols = game.getBoard().getCols();

    for (int i = 0; i < cols; i++) {
      ColumnConstraints columnConstraints = new ColumnConstraints();
      columnConstraints.setPercentWidth(100.0 / cols);
      columnConstraints.setHgrow(Priority.ALWAYS);
      gameBoard.getColumnConstraints().add(columnConstraints);
    }
    for (int i = 0; i < rows; i++) {
      RowConstraints rowConstraints = new RowConstraints();
      rowConstraints.setPercentHeight(100.0 / rows);
      rowConstraints.setVgrow(Priority.ALWAYS);
      gameBoard.getRowConstraints().add(rowConstraints);
    }

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(tileSize, tileSize);
        stackPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setHgrow(stackPane, Priority.ALWAYS);
        GridPane.setVgrow(stackPane, Priority.ALWAYS);

        if (i % 2 == 0) {
          if (j % 2 == 0) {
            stackPane.setStyle("-fx-background-color:rgb(190, 192, 203);");
          } else {
            stackPane.setStyle("-fx-background-color:rgb(255, 255, 255);");
          }
        } else {
          if (j % 2 == 0) {
            stackPane.setStyle("-fx-background-color:rgb(255, 255, 255);");
          } else {
            stackPane.setStyle("-fx-background-color:rgb(190, 192, 203);");
          }
        }

        Board board = game.getBoard();
        Tile tile = board.getGameboard().get(board.getLocation(j, i) - 1);
        int iconSize = 30;
        switch (tile.getClass().getSimpleName()) {
          case "LadderTile":
            if (tile.getLocation() < ((LadderTile) tile).getTravelLocation()) {
              stackPane.setStyle("-fx-background-color:rgb(20, 161, 20);");
            } else {
              stackPane.setStyle("-fx-background-color:rgb(255, 0, 0);");
            }
            break;
          case "PlayerSwapTile":
            stackPane.setStyle("-fx-background-color:rgb(230, 237, 41);");
            URL urlSwap = getClass().getResource("/tiles/arrows.png");
            assert urlSwap != null;
            ImageView swapImage = new ImageView(urlSwap.toExternalForm());
            swapImage.setFitWidth(iconSize);
            swapImage.setFitHeight(iconSize);
            stackPane.getChildren().add(swapImage);
            break;
          case "BackToStartTile":
            stackPane.setStyle("-fx-background-color:rgba(255, 81, 0, 0.85);");
            break;
          case "PauseTile":
            stackPane.setStyle("-fx-background-color:rgb(146, 108, 222);");
            URL urlWatch = getClass().getResource("/tiles/stopwatch.png");
            ImageView pauseImage = new ImageView(urlWatch.toExternalForm());
            pauseImage.setFitWidth(iconSize);
            pauseImage.setFitHeight(iconSize);
            stackPane.getChildren().add(pauseImage);
            break;
          case "FinishTile":
            GridPane finishGrid = new GridPane();
            for (int h = 0; h < 10; h++) {
              for (int g = 0; g < 10; g++) {
                StackPane cell = new StackPane();
                cell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                GridPane.setHgrow(cell, Priority.ALWAYS);
                GridPane.setVgrow(cell, Priority.ALWAYS);

                if ((h + g) % 2 == 0) {
                  cell.setStyle("-fx-background-color: black;");
                } else {
                  cell.setStyle("-fx-background-color: white;");
                }

                finishGrid.add(cell, h, g);
              }
            }
            stackPane.getChildren().add(finishGrid);
            break;
        }
        Text indexLabel = new Text(String.valueOf(tile.getLocation()));
        indexLabel.setId("indexLabel");
        StackPane.setAlignment(indexLabel, Pos.TOP_LEFT);
        StackPane.setMargin(indexLabel, new Insets(3, 0, 0, 5));
        stackPane.getChildren().add(indexLabel);
        gameBoard.add(stackPane, j, i);
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Tile tile = game.getBoard().getGameboard().get(game.getBoard().getLocation(j, i) - 1);
        if (tile instanceof LadderTile ladderTile) {
          int[] coordinatesEnd = game.getBoard().getCoordinates(ladderTile.getTravelLocation());
          StackPane startTile = getTileAt(gameBoard, j, i);
          StackPane endTile = getTileAt(gameBoard, coordinatesEnd[0], coordinatesEnd[1]);

          Line ladderLine = new Line();
          ladderLine.startXProperty()
              .bind(startTile.layoutXProperty().add(startTile.widthProperty().divide(2)));
          ladderLine.startYProperty()
              .bind(startTile.layoutYProperty().add(startTile.heightProperty().divide(2)));
          ladderLine.endXProperty()
              .bind(endTile.layoutXProperty().add(endTile.widthProperty().divide(2)));
          ladderLine.endYProperty()
              .bind(endTile.layoutYProperty().add(endTile.heightProperty().divide(2)));

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
    int index = 0;
    for (Player player : game.getPlayers()) {
      StackPane stackPane = getTileAt(gameBoard, 0, game.getBoard().getRows() - 1);
      ImageView playerImage = new ImageView(player.getPicture().toExternalForm());
      playerImage.setFitWidth(playerSize);
      playerImage.setFitHeight(playerSize);
      //rotate by the bottom middle of the image
      int pivotX = playerSize / 2;
      Rotate rotate = new Rotate(72 * index, pivotX, playerSize);
      playerImage.getTransforms().add(rotate);
      playerImage.setTranslateY(-10);
      index++;

      playerImage.setId("player" + player.getPlayerNumber());
      stackPane.getChildren().add(playerImage);
      player.setObserver(this);
    }

    //Right box
    VBox rightMenu = new VBox();
    rightMenu.setId("rightMenu");
    Button throwDice = new Button("Throw dice");
    throwDice.setOnAction(e -> laddergameController.throwDice(game, animationActive));

    Button simulateDice = new Button("Simulate game");
    simulateDice.setOnAction(
        e -> laddergameController.simulateGame(0, 2000, game, animationActive));

    Label whosTurn = new Label(game.getPlayers().get(game.activePlayer) + "'s turn");
    whosTurn.setId("whosTurn");
    Label players = new Label("Players: ");

    VBox playersBox = new VBox();
    playersBox.setId("playersBox");
    for (Player player : game.getPlayers()) {
      HBox pictureNameSplitter = new HBox(10);
      ImageView playerImage = new ImageView(player.getPicture().toExternalForm());
      ImageView pauseImage = new ImageView();
      pauseImage.setId("image" + player.getPlayerNumber());
      playerImage.setFitWidth(40);
      playerImage.setPreserveRatio(true);
      Label playerLabel = new Label(player.getPlayerName() + ": ");
      Label position = new Label(player.getPosition() + "");
      position.setId("position" + player.getPlayerNumber());
      pictureNameSplitter.getChildren().addAll(playerImage, playerLabel, position);
      pictureNameSplitter.setId("playerBox" + player.getPlayerNumber());
      playersBox.getChildren().add(pictureNameSplitter);
    }

    rightMenu.getChildren().addAll(throwDice, simulateDice, whosTurn, players, playersBox);

    gameBoard.setGridLinesVisible(true);

    mainLayout = mainLayout();
    StackPane gameBoardWithLadder = new StackPane(gameBoard, lines);
    gameBoardWithLadder.setId("gameBoardFinal");
    mainLayout.setCenter(gameBoardWithLadder);
    mainLayout.setRight(rightMenu);
  }

  /**
   * Returns the tile at the specified coordinates
   *
   * @param grid the gridpane to search in
   * @param col  the column index
   * @param row  the row index
   * @return the tile at the specified coordinates
   */
  private StackPane getTileAt(GridPane grid, int col, int row) {
    for (Node node : grid.getChildren()) {
      if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
        return (StackPane) node;
      }
    }
    return null;
  }

  /**
   * Updates the position of the player on the board
   *
   * @param player the player to update
   */
  @Override
  public void positionChanged(Player player) {
    int[] coordiantes = this.game.getBoard().getCoordinates(player.getPosition());
    StackPane stackPane = getTileAt((GridPane) mainLayout.lookup("#gameBoard"), coordiantes[0],
        coordiantes[1]);
    String id = "player" + player.getPlayerNumber();
    Node node = mainLayout.lookup("#" + id);
    Rotate rotate = null;
    if (node != null) {
      rotate = (Rotate) node.getTransforms().getFirst();
      ((Pane) node.getParent()).getChildren().remove(node);
    }
    ImageView playerImage = new ImageView(player.getPicture().toExternalForm());
    playerImage.setId(id);
    playerImage.setFitWidth(playerSize);
    playerImage.setFitHeight(playerSize);
    playerImage.setTranslateY(-10);
    playerImage.getTransforms().add(rotate);
    stackPane.getChildren().add(playerImage);

    Label turnLabel = (Label) mainLayout.lookup("#whosTurn");
    if (game.getActivePlayer() + 1 >= game.getPlayers().size()) {
      turnLabel.setText(game.getPlayers().getFirst() + "'s turn");
    } else {
      turnLabel.setText(game.getPlayers().get(game.getActivePlayer() + 1) + "'s turn");
    }

    Label positionLabel = (Label) mainLayout.lookup("#position" + player.getPlayerNumber());
    if (positionLabel != null) {
      positionLabel.setText(player.getPosition() + "");
    } else {
      PopupView.showInfoPopup("Error!",
          "Position label not found for player " + player.getPlayerName());
    }

    playerPaused(player);
  }

  /**
   * The method that is called when a player switches places with another player
   *
   * @param playerToSwitch     the player that is switching
   * @param playerToBeSwitched the player that is being switched with
   */
  public void playerSwitch(Player playerToSwitch, Player playerToBeSwitched) {
    URL swapUrl = getClass().getResource("/tiles/arrows.png");
    String playerSwitchText =
        playerToSwitch.getPlayerName() + " and " + playerToBeSwitched.getPlayerName()
            + " have switched places!";
    positionChanged(playerToSwitch);
    positionChanged(playerToBeSwitched);
    showImageAndText(swapUrl, playerSwitchText);
  }

  /**
   * The method that is called when a player is paused
   *
   * @param player the player that is paused
   */
  public void playerPaused(Player player) {
    HBox pictureNameSplitter = (HBox) mainLayout.lookup("#playerBox" + player.getPlayerNumber());
    if (!player.getPlayerPause()) {
      ImageView pauseImage = (ImageView) pictureNameSplitter.lookup(
          "#pause" + player.getPlayerNumber());
      pictureNameSplitter.getChildren().remove(pauseImage);
    } else {
      //Adds small stopwatch icon to the player box
      URL urlWatch = getClass().getResource("/tiles/stopwatch.png");
      ImageView pauseImage = new ImageView(urlWatch.toExternalForm());
      pauseImage.setId("pause" + player.getPlayerNumber());
      pauseImage.setFitWidth(20);
      pauseImage.setFitHeight(20);
      pictureNameSplitter.getChildren().addFirst(pauseImage);

      //Adds visual for pause on the whole board
      String pauseText = player.getPlayerName() + " has been paused!";
      showImageAndText(urlWatch, pauseText);
    }
  }

  /**
   * Shows an image and text on the board for 2 seconds
   *
   * @param imageUrl the url of the image to show
   * @param text     the text to show
   */
  public void showImageAndText(URL imageUrl, String text) {
    int cols = game.getBoard().getCols();
    int rows = game.getBoard().getRows();
    ImageView imageView = new ImageView(imageUrl.toExternalForm());
    imageView.setFitWidth(360);
    imageView.setFitHeight(360);
    Text textNode = new Text(text);
    textNode.setStyle(
        "-fx-font-size: 40px; -fx-text-fill: white; -fx-text-stroke: black; -fx-stroke-width: 3px;");
    VBox showBox = new VBox(20, imageView, textNode);
    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(showBox);

    GridPane gridPane = (GridPane) mainLayout.lookup("#gameBoard");
    gridPane.add(stackPane, 0, 0, cols, rows);
    showBox.toFront();

    //removes the pause image after 2 seconds
    PauseTransition pause = new PauseTransition(Duration.millis(2000));
    animationActive = true;
    pause.setOnFinished(e -> {
      gridPane.getChildren().remove(stackPane);
      animationActive = false;
    });
    pause.play();
  }
}
