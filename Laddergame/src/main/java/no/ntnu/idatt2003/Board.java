package no.ntnu.idatt2003;

import java.util.ArrayList;

import no.ntnu.idatt2003.tile.Tile;

public class Board {
    private ArrayList<Tile> gameboard;
    private String name;
    private String description;

    public Board(ArrayList<Tile> gameboard, String name, String description) {
        this.gameboard = gameboard;
        this.name = name;
        this.description = description;
    }

    public ArrayList<Tile> getGameboard() {
        return gameboard;
    }
    
}
