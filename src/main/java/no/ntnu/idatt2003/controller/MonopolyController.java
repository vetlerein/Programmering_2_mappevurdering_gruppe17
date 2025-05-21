package no.ntnu.idatt2003.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.idatt2003.model.Board;
import no.ntnu.idatt2003.model.BoardGameFactory;
import no.ntnu.idatt2003.model.Dice;
import no.ntnu.idatt2003.model.Game;
import static no.ntnu.idatt2003.model.Game.genericGameView;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.Property;
import no.ntnu.idatt2003.model.fileManagement.playerFileManagement.PlayerFileReader;
import no.ntnu.idatt2003.view.MonopolyView;
import no.ntnu.idatt2003.view.PopupView;

/**
 * The controller class for the Monopoly game.
 * This class handles the game logic and player interactions.
 */
public class MonopolyController {

  /**
   * The player file reader.
   */
  PlayerFileReader playerFileReader = new PlayerFileReader();
  /**
   * The active board.
   */
  Board board;

  /**
   * The monopoly view.
   */
  public static MonopolyView monopolyView;

  /**
   * Sets the monopoly view.
   *
   * @param monopolyView the monopoly view
   */
  public static void setMonopolyView(MonopolyView monopolyView) {
    MonopolyController.monopolyView = monopolyView;
  }

  /**
   * This method creats a new game.
   */
  public void newGame() {
    playerFileReader = new PlayerFileReader();
    if (playerFileReader.readPlayers().isEmpty()) {
      PopupView.showInfoPopup("Can't create game",
          "To create a new game, you need to add players first.");
    } else {
      Stage popupStage = new Stage();
      popupStage.initModality(Modality.APPLICATION_MODAL);
      popupStage.setTitle("New Game");
      popupStage.setMinWidth(210);
      popupStage.setMinHeight(300);
      popupStage.setResizable(false);

      VBox chooseIcons = new VBox(10);
      List<ComboBox<Player>> playerComboBoxes = new ArrayList<>();
      List<Player> players = playerFileReader.readPlayers();
      List<String> pictureName = List.of("cheese.png", "mushroom.png", "olives.png",
          "pepperoni.png", "pineapple.png");

      for (int i = 0; i < 5; i++) {

        HBox pictureMenuSplitter = new HBox(10);
        Image picture = new Image(
            getClass().getResourceAsStream("/playerPieces/" + pictureName.get(i)));
        ImageView imageView = new ImageView(picture);
        imageView.setFitWidth(60);
        imageView.setPreserveRatio(true);
        StackPane pictureContainer = new StackPane(imageView);
        ComboBox<Player> choosePlayer = new ComboBox<>(FXCollections.observableArrayList(players));
        choosePlayer.getStyleClass().add("custom-combo");
        ;

        choosePlayer.setPromptText("Choose player");
        choosePlayer.setOnAction(e -> {
          Player selected = choosePlayer.getValue();
          if (selected != null) {
            for (ComboBox<Player> comboBox : playerComboBoxes) {
              if (comboBox != choosePlayer) {
                comboBox.getItems().remove(selected);
              }
            }
          }
        });

        playerComboBoxes.add(choosePlayer);

        pictureMenuSplitter.getChildren().addAll(pictureContainer, choosePlayer);
        chooseIcons.getChildren().add(pictureMenuSplitter);
      }

      VBox chooseIconsLayout = new VBox(10, chooseIcons);
      chooseIconsLayout.setPadding(new Insets(20));
      StackPane centerStartButton = new StackPane();
      centerStartButton.setPadding(new Insets(20));
      ArrayList<Player> selectedPlayers = new ArrayList<>();

      Button startButton = new Button("Start Game");
      startButton.setOnAction(e -> {
        for (ComboBox<Player> comboBox : playerComboBoxes) {
          Player selectedPlayer = comboBox.getValue();
          if (selectedPlayer != null) {
            selectedPlayer.setPicture(getClass().getResource(
                "/playerPieces/" + pictureName.get(playerComboBoxes.indexOf(comboBox))));
            selectedPlayers.add(selectedPlayer);
          }
        }

        Board board = BoardGameFactory.createMonopolyBoard();

        if (selectedPlayers.isEmpty()) {
          PopupView.showInfoPopup("Can't create game", "Please select players first.");
        } else if (selectedPlayers.size() == 1) {
          PopupView.showInfoPopup("Can't create game",
              "You need more than 1 player to start the game.");
        } else {
          Game game = new Game(selectedPlayers, board);
          game.start();
          System.out.println("Game started with players: " + selectedPlayers);
          System.out.println("Selected board: " + board.getName());
          System.out.println("Game: " + game.getPlayers().toString());

          monopolyView.setGameBoard(game);
          popupStage.close();
        }
      });

      centerStartButton.getChildren().add(startButton);

      BorderPane mainLayout = new BorderPane();
      mainLayout.setTop(chooseIconsLayout);
      mainLayout.setBottom(centerStartButton);
      mainLayout.getStylesheets()
          .add(getClass().getResource("/Style/NewGame.css").toExternalForm());

      popupStage.setScene(new Scene(mainLayout));
      popupStage.getIcons()
          .add(new Image(getClass().getResourceAsStream("/playerPieces/pineapple.png")));
      popupStage.showAndWait();
    }
  }

  /**
   * This method creates a VBox with the properties of the player.
   *
   * @param player    the player
   * @param tradeList the list of properties to trade
   * @return a VBox with the properties of the player
   */
  public VBox getPlayerPropertiesBox(Player player, ArrayList<Property> tradeList) {

    VBox playerProperties = new VBox();
    playerProperties.setSpacing(5);

    if (player.getPropertyList().isEmpty()) {
      Label noPropertiesLabel = new Label("You have no properties to trade. ");
      playerProperties.getChildren().add(noPropertiesLabel);
      return playerProperties;
    } else {

      for (Property property : player.getPropertyList()) {

        HBox propertyBox = new HBox();
        StackPane propertyStack = new StackPane();
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Label propertyLabel = new Label(property.getName());
        propertyBox.setStyle("-fx-background-color:" + property.getColor() + ";");

        addButton.setOnAction(e -> {
          propertyBox.getChildren().setAll(removeButton, propertyLabel);
          tradeList.add(property);
        });
        removeButton.setOnAction(e2 -> {
          propertyBox.getChildren().setAll(addButton, propertyLabel);
          tradeList.remove(property);
        });
        addButton.setId("tradeButton");
        removeButton.setId("tradeButton");

        propertyStack.getChildren().add(propertyLabel);
        propertyBox.getChildren().addAll(addButton, propertyStack);
        playerProperties.getChildren().add(propertyBox);

      }
      return playerProperties;
    }
  }

  /**
   * Executes a trade between two players.
   * @param p1 player 1
   * @param p2 player 2
   * @param p1Properties player 1 properties
   * @param p2Properties player 2 properties
   * @param p1Money player 1 money
   * @param p2Money player 2 money
   */
  public void executeTrade(Player p1, Player p2, ArrayList<Property> p1Properties,
      ArrayList<Property> p2Properties, int p1Money, int p2Money) {
    List<Property> propertiesFromP1 = new ArrayList<>(p1Properties);
    List<Property> propertiesFromP2 = new ArrayList<>(p2Properties);
    for (Property property : propertiesFromP1) {
      p1.removeProperty(property);
      p2.addProperty(property);
    }
    for (Property property : propertiesFromP2) {
      p2.removeProperty(property);
      p1.addProperty(property);
    }

    p1.setBalance(p1.getBalance() - p1Money);
    p2.setBalance(p2.getBalance() + p1Money);

    p2.setBalance(p2.getBalance() - p2Money);
    p1.setBalance(p1.getBalance() + p2Money);
  }


  /**
   * Creates a dropdown menu for selecting a player.
   *
   * @param game   the game instance
   * @param active the active player
   * @return a ComboBox with the players
   */
  public ComboBox<Player> createPlayerDropdown(Game game, Player active) {
    List<ComboBox<Player>> playerComboBoxes = new ArrayList<>();
    List<Player> tempPlayers = new ArrayList<>(game.getPlayers());
    tempPlayers.remove(active);

    ComboBox<Player> choosePlayer = new ComboBox<>(FXCollections.observableArrayList(tempPlayers));
    choosePlayer.getStyleClass().add("custom-combo");
    choosePlayer.setPromptText("Choose player");
    playerComboBoxes.add(choosePlayer);
    return choosePlayer;
  }

  /**
   * Creates a VBox with the players in the game.
   */
  public void playerList() {
    VBox playerListBox = new VBox();
    List<Player> players = playerFileReader.readPlayers();

    for (Player player : players) {
      Label playerLabel = new Label(
          player.getPlayerName() + " - " + player.getPlayerNumber() + " - "
              + player.getBirthDate());
      playerListBox.getChildren().add(playerLabel);
    }
  }

  /**
   * buys a house on a property.
   * @param property the property to buy a house on
   */
  public void buyPropertyHouse(Property property) {
    if (property.getOwner().getBalance() < property.getHouseCost()) {
      PopupView.showInfoPopup("Can't buy house!", "You don't have enought money to buy a house.");
    } else {
      property.setPropertyLevel(property.getPropertyLevel() + 1);
      property.getOwner().addPlayerBalance(-1 * property.getHouseCost());
    }
  }

  /**
   * sells a house on a property.
   * @param property the property to sell a house on
   */
  public void sellPropertyHouse(Property property) {
    property.setPropertyLevel(property.getPropertyLevel() - 1);
    property.getOwner().addPlayerBalance(property.getHouseCost() / 2);
  }

  /**
   * pawns a property.
   * @param property the property to pawn
   */
  public void pawnProperty(Property property) {
    property.setPawned();
    property.getOwner().addPlayerBalance(property.getPrice() / 2);
  }

  /**
   * Repurchase a property.
   * @param property the property to repurchase
   */
  public void rePurchaseProperty(Property property) {
    property.rePurchase();
    property.getOwner().addPlayerBalance(-1 * property.getPrice());
  }

  /**
   * Throws the dice for a player.
   *
   * @param game   the game instance
   * @param player the player
   */
  public void throwDice(Game game, Player player) {
    player.move(game);
    genericGameView.showDice(player.getDicePaths());
  }

  /**
   * Buys a property.
   *
   * @param property the property to buy
   * @param player   the player who buys the property
   */
  public void buyProperty(Property property, Player player) {
    property.setOwner(player);
    player.addProperty(property);
    player.addPlayerBalance(-1 * property.getPrice());
    System.out.println("Property bought: " + property.getOwner());
  }

  /**
   * Uses a jail card.
   *
   * @param player the player who uses the jail card
   * @param game   the game instance
   */
  public void useJailCard(Player player, Game game) {
    player.useJailCard();
    game.nextPlayer();
  }

  /**
   * Pays to get out of jail.
   *
   * @param player the player who pays for jail
   * @param game   the game instance
   */
  public void payForJail(Player player, Game game) {
    player.addPlayerBalance(-2000);
    player.setJailStatus(0);
    game.nextPlayer();
  }

  /**
   * Throws the dice for a player in jail.
   *
   * @param player      the player who is in jail
   * @param game        the game instance
   * @param diceThrows  the number of times the player has thrown the dice
   */
  public void throwJailDice(Player player, Game game, int diceThrows) {
    Dice.rollDice(2, player);
    URL[] dicePaths = player.getDicePaths();
    genericGameView.showDice(player.getDicePaths());
    diceThrows++;
    if (dicePaths[0].equals(dicePaths[1])) {
      player.setJailStatus(0);
      game.nextPlayer();
    } else if (diceThrows == 3) {
      player.turnInJail();
      game.nextPlayer();
    }
  }

  /**
   * Checks if a player has won the game.
   *
   * @param game the game instance
   */
  public void checkForWinner(Game game) {
    for (Player player : game.getPlayers()) {
      if (player.getBalance() <= 0) {
        player.setPlayerActive(false);
      } else {
        player.setPlayerActive(true);
        break;
      }

      boolean hasUnpawned = false;
      for (Property property : player.getProperties()) {
        if (property.isPawned() == false) {
          hasUnpawned = true;
          player.setPlayerActive(true);
          break;
        }
      }
      if (!hasUnpawned) {
        player.setPlayerActive(false);
      }
    }

    Player lastActive = null;
    int activeCount = 0;
    for (Player player : game.getPlayers()) {
      if (player.getPlayerActive()) {
        activeCount++;
        lastActive = player;
      }
    }
    if (activeCount == 1 && lastActive != null) {
      game.finish(lastActive);
    }
  }
}