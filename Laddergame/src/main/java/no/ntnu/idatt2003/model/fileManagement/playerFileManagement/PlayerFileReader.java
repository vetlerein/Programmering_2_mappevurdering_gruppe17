package no.ntnu.idatt2003.model.fileManagement.playerFileManagement;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.opencsv.CSVReader;
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

        try (CSVReader csvReader = new CSVReader(new FileReader("Laddergame/data/players.csv"))) {
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
                e.printStackTrace();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }
}
