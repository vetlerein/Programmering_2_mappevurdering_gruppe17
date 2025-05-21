package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;

/**
 * This class creates a tile that pauses players
 */
public class PauseTile extends Tile {

  /**
   * Constructor for the PauseTile class
   *
   * @param location the location of the tile
   */
  public PauseTile(int location) {
    super(location);
  }

  /**
   * Pauses the inputted player
   *
   * @param player the player to pause
   */
  @Override
  public void action(Player player, Game game) {
    player.setPlayerPause();
  }

}
