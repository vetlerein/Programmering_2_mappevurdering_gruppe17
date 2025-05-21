package no.ntnu.idatt2003.model.chanceCards;

import no.ntnu.idatt2003.model.Player;

public class ChanceCardJail implements ChanceCard {
    private final String description = "You recive a get out of jail free card!";

    @Override
    public String toString() {
        return description;
    }

    @Override
    public void effect(Player player) {
        player.giveJailCard();
    }
}
