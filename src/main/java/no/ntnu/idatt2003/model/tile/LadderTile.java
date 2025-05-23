package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;

/**
 * LadderTile class that extends superclass Tile.
 */
public class LadderTile extends Tile {

  /**
   * The location where the player will travel to when they land on this tile.
   */
  private final int travelLocation;

  /**
   * Constructs a LadderTile with the specified location, color, and travel location.
   *
   * @param location       the location of the tile
   * @param travelLocation the travel location of the tile
   */
  public LadderTile(int location, int travelLocation) {
    super(location);
    this.travelLocation = travelLocation;
  }

  /**
   * Gets the travel location of the tile.
   *
   * @return the travel location
   */
  public int getTravelLocation() {
    return travelLocation;
  }

  /**
   * Moves the player to the travel location
   *
   * @param player the player to move
   */
  @Override
  public void action(Player player, Game game) {
    player.setPosition(this.travelLocation);
  }
}