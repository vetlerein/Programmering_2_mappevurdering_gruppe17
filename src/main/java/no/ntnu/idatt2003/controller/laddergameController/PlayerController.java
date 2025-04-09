package no.ntnu.idatt2003.controller.laddergameController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.fileManagement.playerFileManagement.PlayerFileReader;
import no.ntnu.idatt2003.model.fileManagement.playerFileManagement.PlayerFileWriter;

/**
 * The controller class handling player actions.
 */
public class PlayerController {

    /**
     * Opens a popup window to add a new player.
     */
    public void addPlayerWindow() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Legg til spiller");
        popupStage.setMinWidth(300);
        popupStage.setMinHeight(200);
        popupStage.setResizable(false);

        Label label = new Label("Create a new player:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter name");
        
        DatePicker birthDatePicker = new DatePicker();
        LocalDate defaultDate = LocalDate.of(2000, 1, 1);
        birthDatePicker.setValue(defaultDate);
        birthDatePicker.setPromptText("Select birth date");
        //TODO Tanker om hva som skjer om to har samme bursdag.
        
        Button addButton = new Button("Add ✅");
    
         addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()&&birthDatePicker != null) {
                
                int playernumber;

                PlayerFileWriter playerFileWriter = new PlayerFileWriter();
                PlayerFileReader playerFileReader = new PlayerFileReader();
                
                LocalDate birthDate = birthDatePicker.getValue();
                Date date = convertToDate(birthDate);
                
                ArrayList<Player> players = playerFileReader.readPlayers();
                
                if (players.isEmpty()) {
                    playernumber = 1;
                } else {
                    playernumber = players.size() + 1;
                    System.out.println(players.size());
                }

                Player newPlayer = new Player(name, playernumber, date);
                  
                playerFileWriter.writeToFile(newPlayer);
                popupStage.close();
            }
        });

        VBox layout = new VBox(10, label, nameField, birthDatePicker, addButton);
        layout.setPadding(new Insets(20));
        nameField.setFocusTraversable(false);
        birthDatePicker.setFocusTraversable(false);
        addButton.requestFocus();

        popupStage.setScene(new Scene(layout));
        popupStage.showAndWait(); // Venter til popupen lukkes
    }


    /**
     * Convert LocalDate to java.util.Date.
     *
     * @param localDate LocalDate-object to be converted.
     * @return The converted Date object.
     */
    private Date convertToDate(LocalDate localDate) {
        // Konverter LocalDate til LocalDateTime
        LocalDateTime localDateTime = localDate.atStartOfDay();
        // Konverter LocalDateTime til java.util.Date
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
