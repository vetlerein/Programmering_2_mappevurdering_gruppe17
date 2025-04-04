package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Player;

/**
 *  The tile class that makes the finish tile.
 */
public class FinishTile extends Tile implements TileAction{
    
    public FinishTile(int location) {
        super(location);

    }

    /**
     * Decides who won the game
     * @param player the player who won
     */
   @Override
    public void action(Player player) {
        player.getGame().finish(player);
    }
}
