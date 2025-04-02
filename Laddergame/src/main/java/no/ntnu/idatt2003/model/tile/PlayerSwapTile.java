package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Player;

/**
 * This class creates a tile that swaps players
 */
public class PlayerSwapTile extends Tile implements TileAction{

    public PlayerSwapTile(int location) {
        super(location);
    }

    @Override
    public void action(Player player) {
        int newPosition = player.getGame().getPlayers().stream()
        .mapToInt(Player::getPosition)
        .max()
        .orElse(player.getPosition());
        
        player.setPosition(newPosition);
    }
}
