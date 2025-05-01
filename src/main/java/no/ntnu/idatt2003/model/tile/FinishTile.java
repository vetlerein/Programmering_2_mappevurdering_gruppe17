package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;

/**
 *  The tile class that makes the finish tile.
 */
public class FinishTile extends Tile {
    
    /**
     * constructor for the FinishTile class
     * @param location the location of the tile
     */
    public FinishTile(int location) {
        super(location);
    }

    /**
     * Decides who won the game
     * @param player the player who won
     * @param game the game that is finished
     */
   @Override
    public void action(Player player, Game game) {
        game.finish(player);
    }
}
