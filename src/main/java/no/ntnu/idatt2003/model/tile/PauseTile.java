package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;

/**
 * This class creates a tile that pauses players
 */
public class PauseTile extends Tile {

  public PauseTile(int location) {
    super(location);

  }

  /**
   * Pauses the inputed player
   *
   * @param player the player to pause
   */
  @Override
  public void action(Player player, Game game) {
    player.setPlayerPause();
  }

}
