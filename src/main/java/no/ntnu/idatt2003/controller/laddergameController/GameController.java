package no.ntnu.idatt2003.controller.laddergameController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.fileManagement.playerFileManagement.PlayerFileReader;

/**
 * The controller class handling game actions.
 */
public class GameController {
    
    PlayerFileReader playerFileReader = new PlayerFileReader();

    /**
     * This method creats a new game.
     */
    public void newGame(){
        if (playerFileReader.readPlayers().isEmpty()) {
            showInfoPopup();
        }else{
            
            List<Player> tempPlayers = playerFileReader.readPlayers();
            List<String> availableNames = new ArrayList<>(tempPlayers.stream().map(Player::getPlayerName).toList());

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("New Game");
            popupStage.setMinWidth(500);
            popupStage.setMinHeight(300);
            popupStage.setResizable(false);

            VBox chooseIcons = new VBox(10);
            List<String> pictureName = List.of("cheese.png", "mushroom.png", "olives.png", "pepperoni.png", "pineapple.png");
            Map<String, String> playerToPicture = new HashMap<>();
            List<ComboBox<String>> playerLists = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                final int index = i;

                HBox pictureMenuSplitter = new HBox(10);
                Image picture = new Image(getClass().getResourceAsStream("/playerPieces/" + pictureName.get(i)));
                ImageView imageView = new ImageView(picture);
                imageView.setFitWidth(60); 
                imageView.setPreserveRatio(true); 
                StackPane pictureContainer = new StackPane(imageView);

                ComboBox<String> playerList = new ComboBox<>(FXCollections.observableArrayList(availableNames));
                playerList.setPromptText("Choose player");
                playerList.setOnAction(e -> {      
                    String selected = playerList.getValue();

                    for (ComboBox<String> player : playerLists) {
                        if (player != playerList) {
                            player.getItems().remove(selected);
                        }
                    }
                    playerToPicture.put(selected, pictureName.get(index));
                });     
                playerLists.add(playerList);
                pictureMenuSplitter.getChildren().addAll(pictureContainer, playerList);
                chooseIcons.getChildren().add(pictureMenuSplitter);
            }

            VBox chooseIconsLayout = new VBox(10, chooseIcons);
            chooseIconsLayout.setPadding(new Insets(20));

            StackPane centerChooseBoard = new StackPane();
            VBox chooseBoard = new VBox();
            chooseBoard.setPadding(new Insets(20));
            Label label = new Label("Choose board size:");
            ComboBox<String> boardSize = new ComboBox<>(FXCollections.observableArrayList());
            boardSize.setPromptText("Choose board size");
            
            //TODO Implement the logic to get the board sizes from the file and add them to the ComboBox 
            
            chooseBoard.getChildren().addAll(label, boardSize);
            centerChooseBoard.getChildren().add(chooseBoard);


            StackPane centerStartButton = new StackPane();
            centerStartButton.setPadding(new Insets(20));
            Button startButton = new Button("Start Game");
            startButton.setOnAction(e ->{

            //TODO Implement the logic to start the game with the selected players and board size

            });
            centerStartButton.getChildren().add(startButton);

            BorderPane mainLayout = new BorderPane();
            mainLayout.setPadding(new Insets(20));
            mainLayout.setLeft(chooseIconsLayout);
            mainLayout.setRight(centerChooseBoard);
            mainLayout.setBottom(centerStartButton); 

            popupStage.setScene(new Scene(mainLayout));
            popupStage.showAndWait(); 
        }
    }

     /**
     * This method shows an information popup when the user tries to create a new game without players.
     */
    private void showInfoPopup() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Can't create new game!");
        alert.setContentText("To create a new game, you need to add players first.");
        alert.setResizable(false);
        alert.setWidth(300);
        alert.setHeight(200);
        alert.showAndWait();
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
