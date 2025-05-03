package no.ntnu.idatt2003.controller;

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
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.Property;
import no.ntnu.idatt2003.model.fileManagement.playerFileManagement.PlayerFileReader;
import no.ntnu.idatt2003.view.MonopolyView;
import no.ntnu.idatt2003.view.PopupView;

public class MonopolyController {

    PlayerFileReader playerFileReader = new PlayerFileReader();
    Board board;

    public static MonopolyView monopolyView;
    public static void setMonopolyView(MonopolyView monopolyView) {
        MonopolyController.monopolyView = monopolyView;
    }

    /**
     * This method creats a new game.
     */
    public void newGame(){
        playerFileReader = new PlayerFileReader();
        if (playerFileReader.readPlayers().isEmpty()) {
            PopupView.showInfoPopup("Can't create game","To create a new game, you need to add players first.");
        }else{
            
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("New Game");
            popupStage.setMinWidth(210);
            popupStage.setMinHeight(300);
            popupStage.setResizable(false);

            VBox chooseIcons = new VBox(10);
            List<ComboBox<Player>> playerComboBoxes = new ArrayList<>();
            List<Player> players = playerFileReader.readPlayers();
            List<String> pictureName = List.of("cheese.png", "mushroom.png", "olives.png", "pepperoni.png", "pineapple.png");

            for (int i = 0; i < 5; i++) {

                HBox pictureMenuSplitter = new HBox(10);
                Image picture = new Image(getClass().getResourceAsStream("/playerPieces/" + pictureName.get(i)));
                ImageView imageView = new ImageView(picture);
                imageView.setFitWidth(60); 
                imageView.setPreserveRatio(true); 
                StackPane pictureContainer = new StackPane(imageView);
                ComboBox<Player> choosePlayer = new ComboBox<>(FXCollections.observableArrayList(players));
                choosePlayer.getStyleClass().add("custom-combo");;
               
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
            startButton.setOnAction(e ->{
                for (ComboBox<Player> comboBox : playerComboBoxes) {
                    Player selectedPlayer = comboBox.getValue();
                    if (selectedPlayer != null) {
                        selectedPlayer.setPicture(getClass().getResource("/playerPieces/" + pictureName.get(playerComboBoxes.indexOf(comboBox))));
                        selectedPlayers.add(selectedPlayer);
                    }
                }

                
                Board board = BoardGameFactory.createMonopolyBoard();
            
                if(selectedPlayers.isEmpty()){
                    PopupView.showInfoPopup("Can't create game","Please select players first.");
                }else if (selectedPlayers.size() == 1) {
                    PopupView.showInfoPopup("Can't create game","You need more than 1 player to start the game.");
                }else{
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
            mainLayout.getStylesheets().add(getClass().getResource("/Style/NewGame.css").toExternalForm());         

            popupStage.setScene(new Scene(mainLayout));
            popupStage.getIcons().add(new Image(getClass().getResourceAsStream("/playerPieces/pineapple.png")));
            popupStage.showAndWait(); 
        }
    }

    public HBox getPlayerPropertiesBox(Player player) {
 
        HBox playerProperties = new HBox();
        for (Property property : player.getPropertyList()) {
            
            VBox propertyBox = new VBox();
            Button addButton = new Button("Add ");
            Label propertyLabel = new Label(property.getName());
            propertyBox.getChildren().addAll(addButton, propertyLabel);
            playerProperties.getChildren().add(propertyBox);
            
        }
        
        return playerProperties;
    }

    public void playerList(){
        VBox playerListBox = new VBox();
        List<Player> players = playerFileReader.readPlayers();

        for (Player player : players) {
            Label playerLabel = new Label(player.getPlayerName() + " - " + player.getPlayerNumber() + " - " + player.getBirthDate());
            playerListBox.getChildren().add(playerLabel);
        }
    }
}
