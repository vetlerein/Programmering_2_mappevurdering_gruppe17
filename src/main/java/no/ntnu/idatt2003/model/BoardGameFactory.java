package no.ntnu.idatt2003.model;

import java.io.IOException;
import java.util.ArrayList;

import no.ntnu.idatt2003.model.tile.ChanceCardTile;
import no.ntnu.idatt2003.model.tile.FinishTile;
import no.ntnu.idatt2003.model.tile.GoToJailTile;
import no.ntnu.idatt2003.model.tile.JailTile;
import no.ntnu.idatt2003.model.tile.LadderTile;
import no.ntnu.idatt2003.model.tile.PauseTile;
import no.ntnu.idatt2003.model.tile.PlayerSwapTile;
import no.ntnu.idatt2003.model.tile.PropertyTile;
import no.ntnu.idatt2003.model.tile.Tile;

public class BoardGameFactory {
    
    /**
     * Create a small board with 7 rows and 7 columns.
     * @return the full board
     */
    public static Board createSmallBoard() {
        int rows = 7;
        int cols = 7;
        String name = "Small Board";
        String description = "A small board.";
    
        ArrayList<Tile> gameboard = new ArrayList<>();
        for (int i = 1; i < rows * cols + 1; i++) {
            gameboard.add(new Tile(i));
        }
    
        gameboard.set(3, new LadderTile(4, 17));
        gameboard.set(12, new LadderTile(13, 4));
        gameboard.set(20, new LadderTile(21, 42));
        gameboard.set(30, new LadderTile(31, 10));
        gameboard.set(35, new LadderTile(36, 40));
        gameboard.set(40, new LadderTile(41, 1));
    
        gameboard.set(18, new PlayerSwapTile(19));
    
        gameboard.set(15, new PauseTile(16));
        gameboard.set(2, new PauseTile(3));
        gameboard.set(39, new PauseTile(40));
    
        gameboard.set(48, new FinishTile(49));
    
        return new Board(gameboard, name, description, rows, cols);
    }
    

    /**
     * Create a medium board with 10 rows and 10 columns.
     * @return writes the full board to file
     * @throws IOException put in when writing to file
     */
    public static Board createMediumBoard (){
        int rows = 10;
        int cols = 10;
        String name = "Medium Board";
        String description = "A medium board";
        ArrayList<Tile> gameboard = new ArrayList<>();
        for (int i = 1; i < rows * cols+1; i++) {
            gameboard.add(new Tile(i));
        }
        gameboard.set(3, new LadderTile(4,17));
        gameboard.set(12, new LadderTile(13, 4));
        gameboard.set(20, new LadderTile(21, 42));
        gameboard.set(30, new LadderTile(31, 10));
        gameboard.set(35, new LadderTile(36, 40));
        gameboard.set(40, new LadderTile(41, 73));
        gameboard.set(51, new LadderTile(52, 42));
        gameboard.set(65, new LadderTile(66, 10));
        gameboard.set(71, new LadderTile(72, 40));
        gameboard.set(84, new LadderTile(85, 93));
        gameboard.set(90, new LadderTile(91, 75));
        gameboard.set(98, new LadderTile(99, 1));

        gameboard.set(18, new PlayerSwapTile(19));
        gameboard.set(23, new PlayerSwapTile(24));

        gameboard.set(15, new PauseTile(16));
        gameboard.set(2, new PauseTile(3));
        gameboard.set(48, new PauseTile(49));
        gameboard.set(97, new PauseTile(98));

        gameboard.set(rows*cols-1, new FinishTile(rows*cols));

        return new Board(gameboard, name, description, rows, cols);
    }

    /**
     * Create a large board with 20 rows and 20 columns and randomized tiles.
     * @return the full board
     */
    public static Board createChaosBoard () {
        int rows = 20;
        int cols = 20;
        int numberOfLadders = 80;
        int numberOfSwaps = 20;
        int numberOfPauses = 20;
        String name = "Chaos Board";
        String description = "A large board with randomized tiles.";

        int[] unusedNumbers = new int[400];

        for (int i = 0; i < unusedNumbers.length; i++) {
            unusedNumbers[i] = i + 1;
        }

        ArrayList<Tile> gameboard = new ArrayList<>();
        for (int i = 1; i < rows * cols+1; i++) {
            gameboard.add(new Tile(i));
        }

        gameboard.set(rows*cols-1, new FinishTile(rows*cols));

        for (int i = 0; i < numberOfPauses; i++) {
            boolean found = false;
            while (!found){
                int randomIndex = (int)(Math.random() * 399);
                if (unusedNumbers[randomIndex] != 0) {
                    int location = randomIndex + 1;
                    gameboard.set(location - 1, new PauseTile(location));
                    unusedNumbers[randomIndex] = 0;
                    found = true;
                }
            }
        }

        for (int i = 0; i < numberOfSwaps; i++) {
            boolean found = false;
            while (!found){
                int randomIndex = (int)(Math.random() * 399);
                if (unusedNumbers[randomIndex] != 0) {
                    int location = randomIndex + 1;
                    gameboard.set(location - 1, new PlayerSwapTile(location));
                    unusedNumbers[randomIndex] = 0;
                    found = true;
                }
            }
        }

        for (int i = 0; i < numberOfLadders; i++) {
            boolean found = false;
            while (!found){
                int randomIndexStart = (int)(Math.random() * 399);
                int randomIndexEnd = (int)(Math.random() * 399);
                if ((unusedNumbers[randomIndexStart] != 0 && unusedNumbers[randomIndexEnd] != 0) && randomIndexStart != randomIndexEnd) {
                    int fromLocation = randomIndexStart + 1;
                    int toLocation = randomIndexEnd + 1;
                    gameboard.set(fromLocation - 1, new LadderTile(fromLocation, toLocation));

                    unusedNumbers[randomIndexStart] = 0;
                    unusedNumbers[randomIndexEnd] = 0;
                    found = true;
                }
            }
        }

        return new Board(gameboard, name, description, rows, cols);
    }

    public static Board createMonopolyBoard() {
        int length = 11;
        int numberOfTiles = 40; 
        ArrayList<Tile> gameboard = new ArrayList<>();

        //24 properties
        Property[] properties = new Property[24];

        //First side
        properties[0] = new Property("Drammen", 2,1, 800, 50);
        properties[1] = new Property("Trondheim", 3,1, 800, 50);
        properties[2] = new Property("Oslo", 5,1, 1000, 75);

        properties[3] = new Property("Stavanger", 7,2, 1200, 100);
        properties[4] = new Property("Bergen", 8,2, 1200, 100);
        properties[5] = new Property("Ålesund", 10,2, 1400, 125);

        //second side
        properties[6] = new Property("Bodø", 12,3, 1600, 150);
        properties[7] = new Property("Tromsø", 13,3, 1600, 150);
        properties[8] = new Property("Lofoten", 15,3, 1800, 175);

        properties[9] = new Property("Narvik", 17,4, 1800, 175);
        properties[10] = new Property("Haugesund", 18,4, 1800, 175);
        properties[11] = new Property("Kristiansund", 20,4, 2000, 200);

        //third side
        properties[12] = new Property("Lillestrøm", 22,5, 2400, 225);
        properties[13] = new Property("Fredrikstad", 23,5, 2400, 225);
        properties[14] = new Property("Sarpsborg", 25,5, 2600, 250);

        properties[15] = new Property("Kautokeino", 27,6, 2600, 250);
        properties[16] = new Property("Røros", 28,6, 2800, 275);
        properties[17] = new Property("Hamar", 30,6, 3000, 300);

        //fourth side
        properties[18] = new Property("Arendal", 32,7, 3700, 350);
        properties[19] = new Property("Horten", 33,7, 3700, 350);
        properties[20] = new Property("Sandefjord", 35,7, 4500, 450);

        properties[21] = new Property("Surnadal", 37, 8, 6000, 600);
        properties[22] = new Property("Kristiansand", 38, 8, 8000, 850);
        properties[23] = new Property("Molde", 40, 8, 8000, 850);

        for (int i = 1; i < numberOfTiles + 1; i++) {
            gameboard.add(new Tile(i));
        }

        for (Property property : properties) {
            gameboard.set(property.getLocation() - 1, new PropertyTile(property.getLocation(), property));
        }

        //Add the rest of the tiles
        gameboard.set(0, new FinishTile(1));
        gameboard.set(3, new ChanceCardTile(4));
        gameboard.set(5, new ChanceCardTile(6));
        gameboard.set(8, new ChanceCardTile(9));
        gameboard.set(10, new JailTile(11));

        gameboard.set(13, new ChanceCardTile(14));
        gameboard.set(15, new ChanceCardTile(16));
        gameboard.set(18, new ChanceCardTile(19));
        gameboard.set(20, new ChanceCardTile(21));

        gameboard.set(23, new ChanceCardTile(24));
        gameboard.set(25, new ChanceCardTile(26));
        gameboard.set(28, new ChanceCardTile(29));
        gameboard.set(30, new GoToJailTile(31));
        
        gameboard.set(33, new ChanceCardTile(34));
        gameboard.set(35, new ChanceCardTile(36));
        gameboard.set(38, new ChanceCardTile(39));

        return new Board(gameboard, "Monopoly", "A monopoly board", length, length);

    }
}
