package no.ntnu.idatt2003.view;

import no.ntnu.idatt2003.model.Player;

public interface  PositionChangeObserver {
    void positionChanged(int newPosition, Player player);
}
