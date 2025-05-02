package no.ntnu.idatt2003.controller;

import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.fileManagement.playerFileManagement.PlayerFileReader;

public class MonopolyController {
    
    PlayerFileReader playerFileReader = new PlayerFileReader();

    



    public void playerList(){
        VBox playerListBox = new VBox();
        List<Player> players = playerFileReader.readPlayers();

        for (Player player : players) {
            Label playerLabel = new Label(player.getPlayerName() + " - " + player.getPlayerNumber() + " - " + player.getBirthDate());
            playerListBox.getChildren().add(playerLabel);
        }
    }
}
