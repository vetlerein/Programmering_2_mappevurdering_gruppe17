package no.ntnu.idatt2003.model;

import java.util.ArrayList;

import no.ntnu.idatt2003.model.tile.Tile;

/**
 * This class represents the board.
 */
public class Board {

    private ArrayList<Tile> gameboard;
    private String name;
    private String description;
    private int rows;
    private int cols;

    /**
     * Creates a board object.
     *
     * @param gameboard: Arraylist of all the tiles that belongs on the board.
     * @param name: Name of the board.
     * @param description: Description for the board.
     * @param rows: Number of rows on the board.
     * @param cols: Number of columns on the board.
     */
    public Board(ArrayList<Tile> gameboard, String name, String description, int rows, int cols) {
        this.gameboard = gameboard;
        this.name = name;
        this.description = description;
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * @return Returns an ArrayList of the tiles within the board.
     */
    public ArrayList<Tile> getGameboard() {
        return gameboard;
    }

    /**
     * @return returns the board name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return returns the board description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return returns the amount of rows on the board.
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return returns the amount of columns on the board.
     */
    public int getCols() {
        return cols;
    }

    /**
     * Calculates the coordinates based on the position and a given number of rows and columns.
     * @param location the location of the tile to get coordinates for.
     * @return the coordinates of the tile as an array of integers, where the first element is the x-coordinate and the second element is the y-coordinate.
     */
    public int[] getCoordinates (int location) {
        int[] coordinates = new int[2];
        int bottomRow = (location-1)/cols;
        coordinates[1] = rows - 1 - bottomRow;

        //Checks if the next row is going right to left or left to right
        if (bottomRow % 2 == 0) {
            //If the row is even, the coordinates are calculated from right to left
            coordinates[0] = (location - 1) % cols;
        } else {
            //if the row is odd, the coordinates are calculated from left to right
            coordinates[0] = cols - 1 - ((location - 1) % cols);
        }

        return coordinates;
    }

}
