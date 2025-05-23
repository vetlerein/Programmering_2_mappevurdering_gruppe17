package no.ntnu.idatt2003.view;

import java.io.IOException;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import no.ntnu.idatt2003.controller.MonopolyController;
import no.ntnu.idatt2003.model.Board;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.Property;
import no.ntnu.idatt2003.model.chanceCards.ChanceCard;
import no.ntnu.idatt2003.model.tile.ChanceCardTile;
import no.ntnu.idatt2003.model.tile.JailTile;
import no.ntnu.idatt2003.model.tile.PropertyTile;
import no.ntnu.idatt2003.model.tile.Tile;

/**
 * This class is the view for the Monopoly game. It handles the layout and display of the game
 * board,
 */
public class MonopolyView implements PositionChangeObserver {

  /**
   * The game instance.
   */
  private Game game;

  /**
   * The size of the player pieces.
   */
  private final int playerSize = 25;

  /**
   * The Monopoly controller.
   */
  MonopolyController monopolyController = new MonopolyController();

  /**
   * The main layout borderpane.
   */
  public BorderPane mainLayout = new BorderPane();

  /**
   * The main pane for the game board.
   */
  StackPane mainPane;

  /**
   * Creates the main layout for the Monopoly game view.
   *
   * @return the main layout
   */
  public BorderPane mainLayout() {
    mainLayout.setId("mainLayout");

    //Top box
    HBox topMenu = new HBox();
    topMenu.setId("topMenu");
    Button newGameButton = new Button("Start new game");

    newGameButton.setOnAction(e -> monopolyController.newGame());

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
        PopupView.showInfoPopup("Error loading style sheet!", ex.getMessage());
      }
    });
    topMenu.getChildren().addAll(newGameButton, backToMenuButton);

    //Bottom box
    HBox bottomMenu = new HBox();
    bottomMenu.setId("bottomMenu");

    //Adding everything to the final window
    mainLayout.setTop(topMenu);
    mainLayout.setBottom(bottomMenu);
    GenericGameView.setMainLayout(mainLayout);
    return mainLayout;
  }

  /**
   * Sets the game board for the view.
   *
   * @param game the game to set
   */
  public void setGameBoard(Game game) {
    this.game = game;
    GridPane gameBoard = new GridPane();
    gameBoard.setId("gameBoard");
    int rows = game.getBoard().getRows();
    int cols = game.getBoard().getCols();
    int tileSize = 60;
    mainPane = new StackPane();
    mainPane.setId("mainPane");
    mainPane.getChildren().add(gameBoard);
    mainLayout.setCenter(mainPane);

    for (int i = 0; i < game.getBoard().getGameboard().size(); i++) {
      StackPane stackPane = new StackPane();
      stackPane.setPrefSize(tileSize, tileSize);
      stackPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
      GridPane.setHgrow(stackPane, Priority.ALWAYS);
      GridPane.setVgrow(stackPane, Priority.ALWAYS);

      int[] coordiantes = game.getBoard().getCoordinatesMonopoly(i + 1);

      Board board = game.getBoard();
      Tile tile = board.getGameboard().get(i);
      switch (tile.getClass().getSimpleName()) {
        case "StartTile":
          stackPane.setStyle("-fx-background-color:rgb(251, 255, 0);");
          break;
        case "PropertyTile":
          PropertyTile propertyTile = (PropertyTile) tile;
          Property property = propertyTile.getProperty();
          String name = property.getName();
          int price = property.getPrice();
          Text text = new Text(name + "\n" + price + " kr");
          text.setStyle("-fx-font-size: 9px; -fx-fill: white; -fx-font-weight: bold");
          stackPane.setStyle("-fx-background-color:" + property.getColor() + ";");
          stackPane.getChildren().add(text);
          break;
        case "ChanceCardTile":
          stackPane.setStyle("-fx-background-color:rgba(31, 16, 241, 0.85);");
          Text chanceCardText = new Text("?");
          chanceCardText.setStyle("-fx-font-size: 25px; -fx-fill: white; -fx-font-weight: bold;");
          stackPane.getChildren().add(chanceCardText);
          break;
        case "GoToJailTile":
          stackPane.setStyle("-fx-background-color:rgb(228, 168, 239);");
          Text goToJailText = new Text("Go to Jail");
          goToJailText.setStyle("-fx-font-size: 10px; -fx-fill: white; -fx-font-weight: bold;");
          stackPane.getChildren().add(goToJailText);
          break;
        case "JailTile":
          stackPane.setStyle("-fx-background-color:rgb(255, 167, 26);");
          Text jailText = new Text("Jail");
          jailText.setStyle("-fx-font-size: 15px; -fx-fill: white; -fx-font-weight: bold;");
          stackPane.getChildren().add(jailText);
          break;
      }

      //Adds the lines to squares
      boolean isTopRow = (coordiantes[1] == 0);
      boolean isBottomRow = (coordiantes[1] == rows - 1);
      boolean isLeftCol = (coordiantes[0] == 0);
      boolean isRightCol = (coordiantes[0] == cols - 1);

      double top = 1;
      double left = 1;
      double bottom = (isBottomRow || isTopRow) ? 1 : 0;
      double right = (isLeftCol || isRightCol) ? 1 : 0;

      BorderStroke stroke = new BorderStroke(
          Color.BLACK,
          BorderStrokeStyle.SOLID,
          CornerRadii.EMPTY,
          new BorderWidths(top, right, bottom, left)
      );
      stackPane.setBorder(new Border(stroke));
      gameBoard.add(stackPane, coordiantes[0], coordiantes[1]);
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

    gameBoard.setGridLinesVisible(false);
    gameBoard.setId("gameBoardFinal");
    updateSideBar();
    showPlayerCards(game.getPlayers().getFirst());
  }

  /**
   * The method that is called when a position is changed.
   *
   * @param player the player that changed position
   */
  @Override
  public void positionChanged(Player player) {
    showPlayerCards(player);
    int[] coordiantes = this.game.getBoard().getCoordinatesMonopoly(player.getPosition());
    StackPane stackPane = getTileAt((GridPane) mainLayout.lookup("#gameBoardFinal"), coordiantes[0],
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

    Tile tile = game.getBoard().getGameboard().get(player.getPosition() - 1);
    switch (tile) {
      case ChanceCardTile chanceCard -> showChanceCard(chanceCard.getActiveChanceCard());
      case PropertyTile propertyTile -> showProperty(propertyTile.getProperty());
      case JailTile ignored when player.getJailStatus() == 1 -> inJailText(player);
      case null, default -> clearMiddleCard();
    }

    Label positionLabel = (Label) mainLayout.lookup("#position" + player.getPlayerNumber());
    positionLabel.setText(
        game.getBoard().getGameboard().get(player.getPosition() - 1).getClass().getSimpleName());
    if (game.getBoard().getGameboard()
        .get(player.getPosition() - 1) instanceof PropertyTile propertyTile) {
      positionLabel.setText(propertyTile.getProperty().getName());
    }

    for (Player playerByBox : game.getPlayers()) {
      if (activePlayer.equals(playerByBox)) {
        VBox playerBox = (VBox) mainLayout.lookup("#playerBox" + playerByBox.getPlayerNumber());
        playerBox.setStyle(
            "-fx-background-color: lightgreen; -fx-border-color: black; -fx-border-width: 2px; -fx-border-style: solid;");
      } else {
        VBox playerBox = (VBox) mainLayout.lookup("#playerBox" + playerByBox.getPlayerNumber());
        playerBox.setStyle(
            "-fx-background-color: none; -fx-border-color: black; -fx-border-width: 2px; -fx-border-style: solid;");
      }
    }
 
    diceThrows = 0;
    Label balanceLabel = (Label) mainLayout.lookup("#balance" + player.getPlayerNumber());
    balanceLabel.setText(player.getBalance() + " kr");
    monopolyController.checkForWinner(game);
  }

  /**
   * Gets the tile at the specified coordinates in the grid.
   *
   * @param grid the grid to search
   * @param col  the column index
   * @param row  the row index
   * @return the tile at the specified coordinates, or null if not found
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
   * Shows the property cards of the specified player.
   *
   * @param player the player whose cards to show
   */
  public void showPlayerCards(Player player) {
    HBox cardBox = new HBox();

    ScrollPane scroll = new ScrollPane(cardBox);
    scroll.setFitToHeight(true);
    scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    scroll.setPrefHeight(100);
    scroll.setPannable(true);

    for (int i = 0; i < player.getProperties().size(); i++) {
      Property property = player.getProperties().get(i);
      cardBox.getChildren().add(getPropertyCard(property));
    }
    mainLayout.setBottom(scroll);
  }

  /**
   * Gets the property card for the specified property.
   *
   * @param property the property to get the card for
   * @return the property card
   */
  private VBox getPropertyCard(Property property) {
    Player activePlayer = game.getPlayers().get(game.getActivePlayer());
    VBox card = new VBox(5);
    card.setPrefSize(80, 100);
    card.setPadding(new Insets(5));
    card.setMaxSize(80, 100);

    //Nameplate
    StackPane nameHolder = new StackPane();
    nameHolder.setMaxWidth(Double.MAX_VALUE);
    VBox.setVgrow(nameHolder, Priority.NEVER);
    Text name = new Text(property.getName());
    name.setStyle("-fx-font-size: 10px; -fx-fill: white; -fx-font-weight: bold;");
    Rectangle rect = new Rectangle();
    rect.setHeight(30);
    rect.widthProperty().bind(nameHolder.widthProperty());
    rect.setFill(Color.valueOf(property.getColor()));
    nameHolder.getChildren().addAll(rect, name);

    if (!property.isPawned()) {
      card.setStyle(
          "-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px; -fx-border-style: solid;");

      //Rent grid
      GridPane rentGrid = new GridPane();
      for (int i = 0; i < 4; i++) {

        Label rentText = new Label("Rent with " + i + " houses");
        rentText.setMinWidth(100);
        rentGrid.add(rentText, 0, i);
        Label rentAmount = new Label((property.getRent() * (i + 1)) + " kr");
        rentAmount.setMinWidth(40);
        rentGrid.add(rentAmount, 1, i);
        GridPane.setMargin(rentText, new Insets(5));
        GridPane.setMargin(rentAmount, new Insets(5));
      }

      //Hotel rent
      Label rentText = new Label("Rent with a hotel");
      rentText.setMinWidth(60);
      rentGrid.add(rentText, 0, 4);
      Label rentAmount = new Label((property.getRent() * 6) + " kr");
      rentAmount.setMinWidth(20);
      rentGrid.add(rentAmount, 1, 4);
      rentGrid.setGridLinesVisible(true);

      GridPane.setMargin(rentText, new Insets(5));
      GridPane.setMargin(rentAmount, new Insets(5));
      rentGrid.setPadding(new Insets(5));

      //Making the current propertylevel green
      int currentPropertyLevel = property.getPropertyLevel();

      for (Node node : rentGrid.getChildren()) {
        Integer row = GridPane.getRowIndex(node);

        row = (row == null) ? 0 : row;

        if (row == currentPropertyLevel) {
          node.setStyle("-fx-background-color: lightgreen;");
        }
      }

      //Costs
      Label houseCostText = new Label("House cost: " + property.getHouseCost() + " kr");
      Label propertyBuyPrice = new Label("Property price: " + property.getPrice() + " kr");

      HBox houseButtons = new HBox(2);
      StackPane centerHouseButtons = new StackPane();
      StackPane centerPawnButton = new StackPane();
      Button pawnButton = new Button("Pawn");
      Button buyHouseButton = new Button("Buy house");
      Button sellHouseButton = new Button("Sell house");
      pawnButton.setId("propertyButton");
      centerPawnButton.getChildren().add(pawnButton);
      centerHouseButtons.getChildren().add(houseButtons);
      buyHouseButton.setId("propertyButton");
      sellHouseButton.setId("propertyButton");
      card.getChildren().addAll(nameHolder, rentGrid, houseCostText, propertyBuyPrice);

      if (property.getOwner() == activePlayer) {
        pawnButton.setOnAction(e -> monopolyController.pawnProperty(property));
        if (property.getPropertyLevel() < 5) {

          buyHouseButton.setOnAction(e -> monopolyController.buyPropertyHouse(property));
          houseButtons.getChildren().add(buyHouseButton);
        }
        if (property.getPropertyLevel() > 0) {
          sellHouseButton.setOnAction(e -> monopolyController.sellPropertyHouse(property));
          houseButtons.getChildren().add(sellHouseButton);
        }
        card.getChildren().addAll(centerPawnButton, centerHouseButtons);
      }
      return card;

    } else if (property.isPawned()) {

      Button rePurchaseButton = new Button("Re-purchase property");
      rePurchaseButton.setOnAction(e -> {
        monopolyController.rePurchaseProperty(property);
        showPlayerCards(activePlayer);
        clearMiddleCard();
      });

      card.getChildren().addAll(nameHolder, rePurchaseButton);
      return card;

    } else {
      PopupView.showInfoPopup("Error!", "Error loading a property card.");
      return null;
    }

  }

  int diceThrows = 0;
  Player activePlayer;

  /**
   * Updates the sidebar with the players and their information.
   */
  private void updateSideBar() {
    VBox rightMenu = new VBox();
    rightMenu.setId("rightMenu");
    Button throwDice = new Button("Throw dice");

    throwDice.setOnAction(e -> {
      activePlayer = game.getPlayers().get(game.getActivePlayer());
      if (game.getGameStatus() && activePlayer.getJailStatus() == 0) {
        monopolyController.throwDice(game, activePlayer);
      } else if (activePlayer.getJailStatus() >= 1) {
        inJailOptions(activePlayer);
      }
    });

    Button tradeButton = new Button("Trade");
    tradeButton.setOnAction(e -> {
      if (game.getGameStatus()) {
        TradeView tradeView = new TradeView();
        tradeView.showTradeView(game, activePlayer);
      } else {
        PopupView.showInfoPopup("No active game", "You need to start a game before you can trade.");
      }
    });

    Label players = new Label("Players: ");

    //Box on the side for the players
    VBox playersBox = new VBox(10);
    playersBox.setId("playersBox");
    for (Player player : game.getPlayers()) {
      VBox personalBox = new VBox();
      personalBox.setPadding(new Insets(5));
      personalBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
      personalBox.setMinWidth(Region.USE_COMPUTED_SIZE);
      personalBox.setMaxWidth(Region.USE_COMPUTED_SIZE);

      HBox pictureNameSplitter = new HBox(10);
      ImageView playerImage = new ImageView(player.getPicture().toExternalForm());
      playerImage.setPreserveRatio(true);
      playerImage.setFitWidth(20);
      playerImage.setPreserveRatio(true);
      Label playerLabel = new Label(player.getPlayerName() + ": ");
      pictureNameSplitter.getChildren().addAll(playerImage, playerLabel);
      personalBox.setId("playerBox" + player.getPlayerNumber());

      Label position = new Label(
          game.getBoard().getGameboard().get(player.getPosition() - 1).getClass().getSimpleName());

      if (game.getBoard().getGameboard()
          .get(player.getPosition() - 1) instanceof PropertyTile propertyTile) {
        position.setText(propertyTile.getProperty().getName());
      }
      position.setId("position" + player.getPlayerNumber());

      Label playerBalance = new Label(player.getBalance() + " kr");
      playerBalance.setId("balance" + player.getPlayerNumber());
      personalBox.getChildren().addAll(pictureNameSplitter, playerBalance, position);
      personalBox.setStyle(
          "-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px; -fx-border-style: solid;");
      playersBox.getChildren().addAll(personalBox);
    }

    rightMenu.getChildren().addAll(throwDice, tradeButton, players, playersBox);
    mainLayout.setRight(rightMenu);
  }

  /**
   * Shows the chance card in the middle of the board.
   *
   * @param chanceCard the chance card to show
   */
  private void showChanceCard(ChanceCard chanceCard) {
    GridPane board = (GridPane) mainLayout.lookup("#gameBoardFinal");

    clearMiddleCard();

    StackPane chanceCardPane = new StackPane();
    chanceCardPane.setPrefSize(250, 100);
    chanceCardPane.setMaxSize(250, 100);
    chanceCardPane.setMinSize(250, 100);
    Text chanceCardText = new Text(chanceCard.toString());
    chanceCardText.setWrappingWidth(200);
    chanceCardText.setStyle("-fx-font-size: 10px; -fx-fill: white; -fx-font-weight: bold;");
    chanceCardPane.getChildren().add(chanceCardText);
    chanceCardPane.setStyle(
        "-fx-padding: 5px ;-fx-background-color:rgba(182, 181, 196, 1); -fx-border-color: rgba(31, 16, 241, 1); -fx-border-width: 2px; -fx-border-style: solid;");
    chanceCardPane.setId("middleShowcase");

    int cols = game.getBoard().getCols();
    int rows = game.getBoard().getRows();
    board.add(chanceCardPane, 0, 0, cols, rows);
    GridPane.setHalignment(chanceCardPane, HPos.CENTER);
    GridPane.setValignment(chanceCardPane, VPos.CENTER);

    chanceCardPane.toFront();
  }

  /**
   * Shows the property card in the middle of the board.
   *
   * @param property the property to show
   */
  private void showProperty(Property property) {
    GridPane board = (GridPane) mainLayout.lookup("#gameBoardFinal");
    clearMiddleCard();
    StackPane propertyPane = new StackPane();
    propertyPane.setId("middleShowcase");
    propertyPane.setMaxSize(200, 400);
    propertyPane.setMinSize(200, 400);

    VBox card = getPropertyCard(property);
    card.getChildren().removeIf(n ->
        n instanceof Button && "pawnButton".equals(n.getId())
    );

    if (property.getOwner() == null) {
      Button buyButton = new Button("Buy property");
      buyButton.setOnAction(e -> {
        monopolyController.buyProperty(property, activePlayer);
        showPlayerCards(activePlayer);
        clearMiddleCard();
      });

      propertyPane.getChildren().add(new VBox(5, card, buyButton));
    } else {
      Label owner = new Label("Owner: " + property.getOwner().getPlayerName());
      owner.setStyle("-fx-font-size:10px; -fx-fill:white; -fx-font-weight:bold;");
      propertyPane.getChildren().add(new VBox(5, card, owner));
    }

    int cols = game.getBoard().getCols();
    int rows = game.getBoard().getRows();
    board.add(propertyPane, 0, 0, cols, rows);
    GridPane.setHalignment(propertyPane, HPos.CENTER);
    GridPane.setValignment(propertyPane, VPos.CENTER);
    propertyPane.setTranslateY(50);
    propertyPane.toFront();
  }

  /**
   * Clears the middle card from the board.
   */
  private void clearMiddleCard() {
    GridPane board = (GridPane) mainLayout.lookup("#gameBoardFinal");
    if (board != null) {
      board.getChildren().removeIf(node ->
          "middleShowcase".equals(node.getId())
      );
    }
  }

  /**
   * Shows the text when a player goes to jail.
   *
   * @param player the player who went to jail
   */
  private void inJailText(Player player) {
    GridPane board = (GridPane) mainLayout.lookup("#gameBoardFinal");
    clearMiddleCard();
    StackPane stackPane = new StackPane();
    stackPane.setId("middleShowcase");
    stackPane.setPrefSize(200, 400);
    stackPane.setMaxSize(200, 400);
    stackPane.setMinSize(200, 400);
    GridPane.setHalignment(stackPane, HPos.CENTER);
    GridPane.setValignment(stackPane, VPos.CENTER);
    Text jailText = new Text(player.getPlayerName() + " has gone to jail");
    jailText.setStyle("-fx-font-size:20px; -fx-fill:black; -fx-font-weight:bold;");
    stackPane.getChildren().add(jailText);
    int cols = game.getBoard().getCols();
    int rows = game.getBoard().getRows();
    board.add(stackPane, 0, 0, cols, rows);
    stackPane.toFront();
  }

  /**
   * Shows the options for a player in jail.
   *
   * @param player the player who is in jail
   */
  private void inJailOptions(Player player) {
    GridPane board = (GridPane) mainLayout.lookup("#gameBoardFinal");
    clearMiddleCard();
    StackPane stackPane = new StackPane();
    stackPane.setId("middleShowcase");
    stackPane.setPrefSize(200, 400);
    stackPane.setMaxSize(200, 400);
    stackPane.setMinSize(200, 400);
    VBox optionBox = new VBox(10);
    if (player.getJailCard()) {
      Button freeCardButton = new Button("Use get out of jail free card");
      freeCardButton.setOnAction(e -> monopolyController.useJailCard(player, game));
      optionBox.getChildren().add(freeCardButton);
    }

    if (player.getBalance() >= 2000) {
      Button payButton = new Button("Pay to get out of jail");
      payButton.setOnAction(e -> monopolyController.payForJail(player, game));
      optionBox.getChildren().add(payButton);
    }

    Button throwButton = new Button("Throw Dice");
    diceThrows = 0;
    throwButton.setOnAction(e -> {
      monopolyController.throwJailDice(player, game, diceThrows);
      clearMiddleCard();
    });

    int cols = game.getBoard().getCols();
    int rows = game.getBoard().getRows();
    optionBox.getChildren().add(throwButton);
    optionBox.setTranslateY(50);
    stackPane.getChildren().add(optionBox);
    board.add(stackPane, 0, 0, cols, rows);
    GridPane.setHalignment(stackPane, HPos.CENTER);
    GridPane.setValignment(stackPane, VPos.CENTER);
    GridPane.setHalignment(stackPane, HPos.CENTER);
    GridPane.setValignment(stackPane, VPos.CENTER);
    stackPane.toFront();
  }
}

