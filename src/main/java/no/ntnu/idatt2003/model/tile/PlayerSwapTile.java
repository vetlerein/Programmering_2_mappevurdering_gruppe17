package no.ntnu.idatt2003.model.tile;

import java.util.Comparator;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;

/**
 * This class creates a tile that swaps players
 */
public class PlayerSwapTile extends Tile {


    public PlayerSwapTile(int location) {
        super(location);

    }

    /**
     * Finds the player with the highest position and sets the current player position to that
     * @param player the player to be moved
     */
    @Override
    public void action(Player player, Game game) {
        Player playerToSwap = game.getPlayers().stream()
            .max(Comparator.comparingInt(Player::getPosition))
            .orElse(null);
    
        if (playerToSwap != null) {
            int newPosition = playerToSwap.getPosition();
            playerToSwap.setPosition(player.getPosition());
            player.setPosition(newPosition);
        }
    }
}
