package no.ntnu.idatt2003.view;

import no.ntnu.idatt2003.model.Player;

/**
 * The PositionChangeObserver interface defines a method for observing changes of a player.
 */
public interface PositionChangeObserver {
    void positionChanged(Player player);
}
