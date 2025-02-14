package no.ntnu.idatt2003.tile;

/**
 * LadderTile class that extends superclass Tile.
 */
public class LadderTile extends Tile {
    private int travelLocation;

    /**
     * Constructs a LadderTile with the specified location, color, and travel location.
     *
     * @param location the location of the tile
     * @param color the color of the tile
     * @param travelLocation the travel location of the tile
     */
    public LadderTile(int location, String color, int travelLocation) {
        super(location, color);
        this.travelLocation = travelLocation;
    }

    /**
     * Gets the travel location of the tile.
     *
     * @return the travel location
     */
    public int getTravelLocation() {
        return travelLocation;
    }

    /**
     * Sets the travel location of the tile.
     *
     * @param travelLocation the new travel location
     */
    public void setTravelLocation(int travelLocation) {
        this.travelLocation = travelLocation;
    }

    /**
     * Sets the color of the tile.
     * To be implemented.
     */
    public void setColor() {
        // TBA
    }
}
