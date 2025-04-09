package no.ntnu.idatt2003.model;

import java.io.IOException;
import java.util.ArrayList;

import no.ntnu.idatt2003.model.tile.FinishTile;
import no.ntnu.idatt2003.model.tile.LadderTile;
import no.ntnu.idatt2003.model.tile.PauseTile;
import no.ntnu.idatt2003.model.tile.PlayerSwapTile;
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
        for (int i = 0; i < rows * cols; i++) {
            gameboard.add(new Tile(i + 1));
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
     * @throws IOException 
     */
    public static Board createMediumBoard () throws IOException {
        int rows = 10;
        int cols = 10;
        String name = "Medium Board";
        String description = "A medium board";
        ArrayList<Tile> gameboard = new ArrayList<>();
        for (int i = 0; i < rows * cols; i++) {
            gameboard.add(new Tile(i+1));
        }
        gameboard.set(3, new LadderTile(3,17));
        gameboard.set(12, new LadderTile(12, 4));
        gameboard.set(20, new LadderTile(20, 42));
        gameboard.set(30, new LadderTile(30, 10));
        gameboard.set(35, new LadderTile(32, 40));
        gameboard.set(40, new LadderTile(43, 73));
        gameboard.set(51, new LadderTile(51, 42));
        gameboard.set(65, new LadderTile(65, 10));
        gameboard.set(71, new LadderTile(71, 40));
        gameboard.set(84, new LadderTile(84, 93));
        gameboard.set(90, new LadderTile(90, 75));
        gameboard.set(99, new LadderTile(99, 1));

        gameboard.set(18, new PlayerSwapTile(18));
        gameboard.set(23, new PlayerSwapTile(23));

        gameboard.set(15, new PauseTile(15));
        gameboard.set(2, new PauseTile(2));
        gameboard.set(48, new PauseTile(48));
        gameboard.set(97, new PauseTile(97));

        gameboard.set(rows*cols-1, new FinishTile(100));

        return new Board(gameboard, name, description, rows, cols);
        //Write board to file
        // try {
        //     Board board = new Board(gameboard, name, description, rows, cols);
        //     Path path = Paths.get("data/medium_board.json");
        //     Files.createDirectories(path.getParent());
        //     new BoardFileWriterGson().writeBoardToFile(path, board);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

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
        for (int i = 0; i < rows * cols; i++) {
            gameboard.add(new Tile(i+1));
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
}
