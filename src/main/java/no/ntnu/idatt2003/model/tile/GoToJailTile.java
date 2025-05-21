package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;

public class GoToJailTile extends Tile {

  /**
   * constructor for the GoToJailTile class
   *
   * @param location the location of the tile
   */
  public GoToJailTile(int location) {
    super(location);
  }

  /**
   * Sends the player to jail
   *
   * @param player the player to send to jail
   * @param game   the game instance
   */
  @Override
  public void action(Player player, Game game) {
    player.sendToJail();
  }
}
