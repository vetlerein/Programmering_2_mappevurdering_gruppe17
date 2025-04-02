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

    /**
     * Creates a board object.
     *
     * @param gameboard: Arraylist of all the tiles that belongs on the board.
     * @param name: Name of the board.
     * @param description: Description for the board.
     */
    public Board(ArrayList<Tile> gameboard, String name, String description) {
        this.gameboard = gameboard;
        this.name = name;
        this.description = description;
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

}
