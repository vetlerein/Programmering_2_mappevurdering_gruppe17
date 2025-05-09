package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;

public class JailTile extends Tile{

    /**
     * constructor for the JailTile class
     * @param location
     */
    public JailTile(int location) {
        super(location);
    }
    
    /**
     * Is called when the player lands on the jail tile.
     * If the player is in jail, they can pay bail or throw dice to get out.
     * @param player the player who landed on the tile
     * @param game the game instance
     */
    @Override
    public void action(Player player, Game game) {
        if(player.getJailStatus() >= 1) {
            player.turnInJail();
        }
    }
}
