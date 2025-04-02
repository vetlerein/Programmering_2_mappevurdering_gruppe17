package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Player;

/**
 * The tile class that returns a player back to the first tile.
 */
public class BackToStartTile extends Tile implements TileAction{

    /**
     * @param location
     */
    public BackToStartTile(int location, int[] coordinate) {
        super(location, coordinate);
    }

    /**
     * Moves the player back to start
     * @param player the player to move back to start
     */
    @Override
    public void action(Player player) {
        player.setPosition(0);
    }
}
