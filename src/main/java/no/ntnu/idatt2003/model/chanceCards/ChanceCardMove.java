package no.ntnu.idatt2003.model.chanceCards;

import no.ntnu.idatt2003.model.Player;

public class ChanceCardMove implements ChanceCard {
    private final int newPosition;
    private final String description;

    public ChanceCardMove(int position, String description) {
        this.newPosition = position;
        this.description = description;
    }

    @Override
    public String toString() {
        return description + ", move to " + newPosition;
    }

    @Override
    public void effect(Player player) {
        if (newPosition < player.getPosition()) {
            player.setPosition(0);
        } 
        player.setPosition(newPosition);
    }
}

