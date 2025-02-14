package no.ntnu.idatt2003.tile;

/**
 * Represents a tile in the game.
 */
public class Tile {
    
    private int location;    
    private String color;
    
    /**
     * Constructs a Tile with a location and color.
     *
     * @param location the location of the tile
     * @param color the color of the tile (Special tiles have different colors) 
     */
    public Tile(int location, String color) {
        this.location = 0;
        this.color = color;
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

    /**
     * Gets the color of the tile.
     *
     * @return the color of the tile
     */
    public String getColor() {
        return color;
    }

}
