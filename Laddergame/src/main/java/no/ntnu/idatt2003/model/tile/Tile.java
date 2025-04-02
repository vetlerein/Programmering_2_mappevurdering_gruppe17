package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Player;

/**
 * Represents a tile in the game.
 */
public class Tile implements TileAction{
    
    public int location;   
    public int[] coordinate = new int[2];
    
    /**
     * Constructs a Tile with a location and color.
     *
     * @param location the location of the tile 
     */
    public Tile(int location, int[] coordinate) {
        this.location = location;
        this.coordinate = coordinate;
    }

    /**
     * Gets the location of the tile.
     *
     * @return the location of the tile
     */
    public int getLocation() {
        return location;
    }


    /**
     * Gets the coordinate of the tile.
     *
     * @return the coordinate of the tile
     */
    public int[] getCoordinate() {
        return coordinate;
    }

    /**
     * Sets the location of the tile.
     *
     * @param location the new location of the tile
     */
    public void setLocation(int location) {
        this.location = location;
    }

    @Override
    public void action(Player player) {
    }
}
