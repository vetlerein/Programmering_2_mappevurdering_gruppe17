package no.ntnu.idatt2003.model.chanceCards;

import no.ntnu.idatt2003.model.Player;

/**
 * ChanceCardJail class represents a chance card that gives the player a "Get Out of Jail Free" card.
 */
public class ChanceCardJail implements ChanceCard {
    private final String description = "You recive a get out of jail free card!";

    /**
    * toString method returns the description of the chance card.
    */
    @Override
    public String toString() {
        return description;
    }

    /**
     * The effect method is called when a player draws a chance card.
     * @param player the player who drew the chance card
     */
    @Override
    public void effect(Player player) {
        player.giveJailCard();
    }
}
