package no.ntnu.idatt2003.tile;

/**
 * SpecialTile class that extends superclass Tile.
 */
public class SpecialTile extends Tile {
    private String tileType;

    /**
     * Constructs a SpecialTile with the specified location, color, and tile type.
     *
     * @param location the location of the tile
     * @param color the color of the tile
     * @param tileType the type of the tile
     */
    public SpecialTile(int location, String color, String tileType) {
        super(location, color);
        this.tileType = tileType;
    }

    /**
     * Gets the type of the tile.
     *
     * @return the type of the tile
     */
    public String getType() {
        return tileType;
    }

    /**
     * Moves the piece back to the start.
     */
    public void backToStart() {
    
    }

    /**
     * Indicates the final tile that shows the player has won.
     */
    public void finish() {
    
    }

    /**
     * Pauses a piece for one round.
     */
    public void pause() {
      
    }

    /**
     * Swaps the positions of two players.
     */
    public void playerSwap() {
        
    }

    /**
     * Sets the color of the tile.
     */
    public void setColor() {

    }
}
