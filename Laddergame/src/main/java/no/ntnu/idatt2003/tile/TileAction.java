package no.ntnu.idatt2003.tile;

import no.ntnu.idatt2003.Player;

/**
 * The TileAction interface represents an action that can be performed on a tile in the game.
 * Classes implementing this interface should define the specific action to be taken when a player
 * lands on a tile.
 */
public interface TileAction {
    /**
     * Performs an action on the given player.
     *
     * @param player the player who perfoms the action
     */
    void action(Player player);
}