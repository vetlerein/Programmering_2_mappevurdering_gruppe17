package no.ntnu.idatt2003.model.chanceCards;

import no.ntnu.idatt2003.model.Player;

/**
 * ChanceCardMoney class represents a chance card that gives or takes money from the player.
 */
public class ChanceCardMoney implements ChanceCard {
    private final int amount;
    private final String description;

    /**
     * Constructor for ChanceCardMoney.
     * @param amount the amount of money to give or take from the player
     * @param description the description of the chance card
     */
    public ChanceCardMoney(int amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    /**
     * toString method returns the description of the chance card.
     */
    @Override
    public String toString() {
        if(amount > 0)
            return description + ", you recive " + amount + " $";
         else {
            return description + ", you pay " + amount*-1 + " $";
        }        
    }

    /**
     * The effect method is called when a player draws a chance card.
     * @param player the player who drew the chance card
     */
    @Override
    public void effect(Player player) {
        player.addPlayerBalance(amount);
    }
}
