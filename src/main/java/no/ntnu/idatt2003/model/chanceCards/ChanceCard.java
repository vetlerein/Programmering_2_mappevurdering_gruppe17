package no.ntnu.idatt2003.model.chanceCards;

import no.ntnu.idatt2003.model.Player;

/**
 * The ChanceCard interface represents a chance card in the game.
 * It defines the effect method that will be implemented by concrete chance card classes.
 */
public interface ChanceCard {

    /**
     * The effect method is called when a player draws a chance card.
     * @param player the player who drew the chance card
     */
    void effect(Player player);
}
