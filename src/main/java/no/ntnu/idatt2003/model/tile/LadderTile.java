package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Game;
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
    public LadderTile(int location, int travelLocation) {
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

    /**
     * Moves the player to the travel location
     * @param player the player to move
     */
   @Override
    public void action(Player player, Game game) {
        player.setPosition(this.travelLocation);
        System.out.println(player.playerName + " has moved to " + this.travelLocation);
    }
}