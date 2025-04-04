package no.ntnu.idatt2003.controller.laddergameController;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The controller class handling game actions.
 */
public class GameController {
 
    public void newGame(){

        //TODO Legg til sjekk for om det er spillere i spillet
        if (false) {
            showInfoPopup();
        }else{

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("New Game");
            popupStage.setMinWidth(300);
            popupStage.setMinHeight(300);
            popupStage.setResizable(false);

            VBox chooseIcons = new VBox(10);
            List<String> pictureName = List.of("cheese.png", "mushroom.png", "olives.png", "pepperoni.png", "pineapple.png");
            
            for (int i = 0; i < 5; i++) {

                HBox pictureMenuSplitter = new HBox(10);

                Image picture = new Image(getClass().getResourceAsStream("/playerPieces/" + pictureName.get(i)));
                ImageView imageView = new ImageView(picture);
                imageView.setFitWidth(60); // Juster etter behov
                imageView.setPreserveRatio(true); // Behold proporsjonene
                StackPane pictureContainer = new StackPane(imageView);

                //TODO add players
                ComboBox<String> playerList = new ComboBox<>();
                playerList.getItems().addAll("Player 1", "Player 2", "Player 3", "Player 4", "Player 5");
                playerList.setPromptText("Choose player");

                playerList.setOnAction(e -> {
                    String chosenPlayer = playerList.getValue();
                    System.out.println("You chose: " + chosenPlayer);
                });

                //TODO Asign picture to player        
        
                pictureMenuSplitter.getChildren().addAll(pictureContainer, playerList);
                chooseIcons.getChildren().add(pictureMenuSplitter);
            
            }

            VBox layout = new VBox(10, chooseIcons);
            layout.setPadding(new Insets(20));

            popupStage.setScene(new Scene(layout));
            popupStage.showAndWait(); // Venter til popupen lukkes

        }
    }

     private void showInfoPopup() {
        // Oppretter en informasjons-popup
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Cant create new game!");
        alert.setContentText("To create a new game, you need to add players first.");
        alert.setResizable(false);
        alert.setWidth(300);
        alert.setHeight(200);
        alert.showAndWait();
    }
}
