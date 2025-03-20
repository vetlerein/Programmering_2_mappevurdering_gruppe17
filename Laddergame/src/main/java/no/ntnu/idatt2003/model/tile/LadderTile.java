package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Player;

/**
 * LadderTile class that extends superclass Tile.
 */
public class LadderTile extends Tile implements TileAction{
    private int travelLocation;

    /**
     * Constructs a LadderTile with the specified location, color, and travel location.
     *
     * @param travelLocation the travel location of the tile
     */
    public LadderTile(int travelLocation, int location) {
        super(location);
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

   @Override
    public void action(Player player) {
 
    }
}
