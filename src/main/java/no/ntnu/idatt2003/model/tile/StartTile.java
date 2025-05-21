package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;

/**
 * The StartTile class represents the start tile in the game. When a player lands on this tile, they
 * receive a bonus.
 */
public class StartTile extends Tile {

  /**
   * constructor for the StartTile class
   *
   * @param location the location of the tile
   */
  public StartTile(int location) {
    super(location);
  }

  /**
   * Adds the start bonus the player who passes.
   *
   * @param player the player who landed on the tile
   * @param game   the game instance
   */
  @Override
  public void action(Player player, Game game) {
    player.addPlayerBalance(2000);
  }
}
