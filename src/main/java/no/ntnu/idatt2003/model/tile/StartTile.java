package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;

public class StartTile extends Tile {

    /**
     * constructor for the StartTile class
     * @param location
     */
    public StartTile(int location) {
        super(location);
    }

    /**
     * Adds the start bonus the the player who passes.
     * @param player the player who landed on the tile
     * @param game the game instance
     */
    @Override
    public void action(Player player, Game game) {
        //TODO add final passing start bonus
        player.addPlayerBalance(1000);
    }
    
}
