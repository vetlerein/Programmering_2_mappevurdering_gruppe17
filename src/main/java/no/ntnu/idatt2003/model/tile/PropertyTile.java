package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.Property;

/**
 * The PropertyTile class represents a tile on the game board that is associated with a property.
 */
public class PropertyTile extends Tile {

  /**
   * The property associated with this tile.
   */
  private final Property property;

  /**
   * Constructor for the PropertyTile class.
   *
   * @param location the location of the tile
   * @param property the property associated with the tile
   */
  public PropertyTile(int location, Property property) {
    super(location);
    this.property = property;
  }

  /**
   * Gives the player the option to buy the property if they land on it.
   *
   * @param player the player who landed on the tile
   * @param game   the game instance
   */
  @Override
  public void action(Player player, Game game) {
    if (this.property.getOwner() != null && this.property.getOwner() != player
        && !this.property.isPawned()) {
      player.addPlayerBalance(-1 * this.property.getRent());
      this.property.getOwner().addPlayerBalance(this.property.getRent());
    }
  }

  /**
   * Gets the property associated with this tile.
   *
   * @return the property associated with the tile
   */
  public Property getProperty() {
    return property;
  }
}
