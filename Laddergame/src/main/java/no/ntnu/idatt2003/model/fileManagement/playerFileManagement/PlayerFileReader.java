package no.ntnu.idatt2003.model.fileManagement.playerFileManagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.opencsv.CSVReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.opencsv.exceptions.CsvValidationException;

import no.ntnu.idatt2003.model.Player;

/**
 * This class reads player information from a CSV file.
 */
public class PlayerFileReader {

    /**
     * @return the method returns an ArrayList with Player objects within.
     */
    public ArrayList<Player> readPlayers() {
        String name;
        int number;
        Date birthDate;
        
        ArrayList<Player> players = new ArrayList<Player>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        FileReader fileReader;
        try {
            fileReader = new FileReader("Laddergame/data/players.csv");
            CSVReader csvReader = new CSVReader(fileReader);
            //https://www.geeksforgeeks.org/reading-csv-file-java-using-opencsv/
            String[] nextRecord;

            try {
                while ((nextRecord = csvReader.readNext()) != null){
                    name = nextRecord[0];
                    number = Integer.parseInt(nextRecord[1]);
                    birthDate = dateFormat.parse(nextRecord[2]);
                    players.add(new Player(name, number, birthDate));              
                }
            } catch (CsvValidationException | NumberFormatException | IOException | ParseException e) {
                
            }
    
            
        } catch (FileNotFoundException ex) {
            
        }
        return players;
    }
}
