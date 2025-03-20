package no.ntnu.idatt2003.model.tile;

/**
 * Represents a tile in the game.
 */
public class Tile{
    
    public int location;    
    
    /**
     * Constructs a Tile with a location and color.
     *
     * @param location the location of the tile 
     */
    public Tile(int location) {
        this.location = location;
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
     * Sets the location of the tile.
     *
     * @param location the new location of the tile
     */
    public void setLocation(int location) {
        this.location = location;
    }
}
