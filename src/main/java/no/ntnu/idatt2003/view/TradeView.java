package no.ntnu.idatt2003.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    BorderPane leftMenu = new BorderPane();
    BorderPane rightMenu = new BorderPane();
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
    public void showTradeView(Game game, Player activePlayer) {
        //Setting up the stage
        tradeStage.initModality(Modality.APPLICATION_MODAL);
        tradeStage.setTitle("New Game");
        tradeStage.setMinWidth(600);
        tradeStage.setMinHeight(400);
        // tradeStage.setResizable(false);
        mainLayout.setId("background");
        leftMenu.setId("tradeMenu");
        rightMenu.setId("tradeMenu");

        VBox activePlayerMoneyVBox = new VBox(10);
        VBox chosenPlayerMoneyVBox = new VBox(10);
        TextField activePlayerMoneyField = new TextField();
        activePlayerMoneyField.setPromptText("Enter amount");
        TextField chosenPlayerMoneyField = new TextField();
        chosenPlayerMoneyField.setPromptText("Enter amount");
        activePlayerMoneyVBox.getChildren().addAll(new Label("Offer money to the trade:"), activePlayerMoneyField);
        chosenPlayerMoneyVBox.getChildren().addAll(new Label("Offer money to the trade:"), chosenPlayerMoneyField);

        ComboBox<Player> playerDropdown = monopolyController.createPlayerDropdown(game, activePlayer);
        playerDropdown.setPromptText("Choose a player to trade with");
        playerDropdown.setOnAction(e -> {
            chosenPlayer = playerDropdown.getValue();
            ScrollPane propertyScroll2 = new ScrollPane(monopolyController.getPlayerPropertiesBox(chosenPlayer, chosenPlayerChosenProperties));
            propertyScroll2.setFitToWidth(true);    
            propertyScroll2.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
            propertyScroll2.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            rightMenu.setTop(null);
            rightMenu.setCenter(null);
            rightMenu.setBottom(null);
            rightMenu.setTop(new Label(chosenPlayer.getPlayerName()));
            rightMenu.setCenter(propertyScroll2);
            rightMenu.setBottom(chosenPlayerMoneyVBox);
            BorderPane.setMargin(propertyScroll2, new Insets(10, 0, 10, 0));
        });

        //Bottom buttons
        HBox bottomButtons = new HBox(10);
        bottomButtons.setPadding(new Insets(10));
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
                        chosenPlayerMoneyBox = Integer.parseInt(chosenPlayerMoneyField.getText());
                    } else {
                        chosenPlayerMoneyBox = Integer.parseInt(chosenPlayerMoneyField.getText());
                    }

                    if (activePlayer.getBalance() < activePlayerMoneyBox) {
                        PopupView.showInfoPopup("Not enough money", activePlayer.getPlayerName()+" only has "+activePlayer.getBalance()+" to offer.");
                    } else if (chosenPlayer.getBalance() < chosenPlayerMoneyBox) {
                        PopupView.showInfoPopup("Not enough money", chosenPlayer.getPlayerName()+" only has "+chosenPlayer.getBalance()+" to offer.");
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
        bottomButtons.setAlignment(Pos.CENTER);


        ScrollPane propertyScroll1 = new ScrollPane(monopolyController.getPlayerPropertiesBox(activePlayer, activePlayerChosenProperties));
        propertyScroll1.setFitToWidth(true);    
        propertyScroll1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        propertyScroll1.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        //Adding everything to the final window
        leftMenu.setTop(new Label(activePlayer.toString()));
        leftMenu.setCenter(propertyScroll1);
        leftMenu.setBottom(activePlayerMoneyVBox);
        BorderPane.setMargin(propertyScroll1, new Insets(10, 0, 10, 0)); 
        rightMenu.setTop(playerDropdown);
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
