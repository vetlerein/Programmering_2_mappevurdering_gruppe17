package no.ntnu.idatt2003.view;

import no.ntnu.idatt2003.model.Player;

/**
 * This interface defines a method to observe changes in a player's position. Implementing classes
 * can use this interface to receive notifications when a player's position changes.
 */
public interface PositionChangeObserver {

  /**
   * This method is called when a player's position changes.
   *
   * @param player The player whose position has changed.
   */
  void positionChanged(Player player);
}
