package no.ntnu.idatt2003.model.fileManagement.playerFileManagement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.opencsv.CSVWriter;

import no.ntnu.idatt2003.model.Player;

/**
 * This class writes player information to a CSV file.
 */
public class PlayerFileWriter {

    /**
     * @param player Input: a player object.
     */
    public void writeToFile(Player player){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (CSVWriter writer = new CSVWriter(new FileWriter(new File("data/players.csv")))) { 
            String birthDate = dateFormat.format(player.getBirthDate());

            String playerNumber = Integer.toString(player.getPlayerNumber());
            String[] playerData = { player.getPlayerName(), playerNumber, birthDate}; 
            writer.writeNext(playerData); 
        } 
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
    }
}
