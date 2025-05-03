package no.ntnu.idatt2003.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.idatt2003.controller.MonopolyController;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.Property;

public class TradeView {
    
    Stage tradeStage = new Stage();
    BorderPane mainLayout = new BorderPane();
    VBox leftMenu = new VBox();
    VBox rightMenu = new VBox();
    MonopolyController monopolyController = new MonopolyController();

    private Player chosenPlayer;
    ArrayList<Property> activePlayerChosenProperties = new ArrayList<>();
    ArrayList<Property> chosenPlayerChosenProperties = new ArrayList<>();
    int activePlayerMoneyBox;
    int chosenPlayerMoneyBox;

    /**
     * The method shows the trade view.
     * 
     * @param game The game object that contains the players and their properties.
     */
    public void showTradeView(Game game){
        
        //Setting up the stage
        tradeStage.initModality(Modality.APPLICATION_MODAL);
        tradeStage.setTitle("New Game");
        tradeStage.setMinWidth(500);
        tradeStage.setMinHeight(300);
        tradeStage.setResizable(false);
        leftMenu.setId("tradeMenu");
        rightMenu.setId("tradeMenu");
        
        TextField activePlayerMoneyField = new TextField();
        activePlayerMoneyField.setPromptText("Money to offer");
        TextField chosenPlayerMoneyField = new TextField();
        chosenPlayerMoneyField.setPromptText("Money to offer");

        Player activePlayer = game.getPlayers().get(game.activePlayer);
        ComboBox<Player> playerDropdown = monopolyController.createPlayerDropdown(game);
        playerDropdown.setPromptText("Choose a player to trade with");
        playerDropdown.setOnAction(e -> {
            chosenPlayer = playerDropdown.getValue();

            rightMenu.getChildren().clear();
            rightMenu.getChildren().addAll(
                new Label(chosenPlayer.getPlayerName()), 
                monopolyController.getPlayerPropertiesBox(chosenPlayer, chosenPlayerChosenProperties), 
                chosenPlayerMoneyField);
        });

        //Bottom buttons
        HBox bottomButtons = new HBox(10);
        Button acceptTrade = new Button("Accept trade");

        acceptTrade.setOnAction(e -> {
            if (chosenPlayer != null) {
                try {
                    if (activePlayerMoneyField.getText().isEmpty()) {
                        activePlayerMoneyField.setText("0");
                        activePlayerMoneyBox = Integer.parseInt(activePlayerMoneyField.getText());
                    } else {
                        activePlayerMoneyBox = Integer.parseInt(activePlayerMoneyField.getText());
                    }
                    if (chosenPlayerMoneyField.getText().isEmpty()) {
                        chosenPlayerMoneyField.setText("0");
                        chosenPlayerMoneyBox = Integer.parseInt(activePlayerMoneyField.getText());
                    } else {
                        chosenPlayerMoneyBox = Integer.parseInt(activePlayerMoneyField.getText());
                    }

                    if (activePlayer.getBalance() < activePlayerMoneyBox) {
                        PopupView.showInfoPopup("Not enough money", activePlayer.getPlayerName()+" don't have enough money to offer.");
                    } else if (chosenPlayer.getBalance() < chosenPlayerMoneyBox) {
                        PopupView.showInfoPopup("Not enough money", chosenPlayer.getPlayerName()+" don't have enough money to offer.");
                    } else {
                        monopolyController.executeTrade(
                            activePlayer, chosenPlayer,
                            activePlayerChosenProperties, chosenPlayerChosenProperties,
                            activePlayerMoneyBox, chosenPlayerMoneyBox
                        );
                        tradeStage.close();
                    }
                } catch (NumberFormatException ex) {
                    PopupView.showInfoPopup("Invalid amount", "Please enter valid numbers for money.");
                } 
            }
            else {
                PopupView.showInfoPopup("No player selected", "Please select a player to trade with.");
            }
        });

        Button cancelTrade = new Button("Cancel trade");
        cancelTrade.setOnAction(e -> {
            tradeStage.close();
        });
        bottomButtons.getChildren().addAll(acceptTrade, cancelTrade);


        //Adding everything to the final window
        leftMenu.getChildren().addAll(new Label(activePlayer.toString()), monopolyController.getPlayerPropertiesBox(activePlayer, activePlayerChosenProperties), activePlayerMoneyField);
        rightMenu.getChildren().addAll(playerDropdown);
        mainLayout.setLeft(leftMenu);
        mainLayout.setRight(rightMenu);
        mainLayout.setBottom(bottomButtons);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getStylesheets().add(getClass().getResource("/Style/TradeView.css").toExternalForm());         
        tradeStage.setScene(new Scene(mainLayout));
        tradeStage.getIcons().add(new Image(getClass().getResourceAsStream("/playerPieces/pepperoni.png")));
        tradeStage.showAndWait(); 
    }
}
