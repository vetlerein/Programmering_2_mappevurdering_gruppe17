package no.ntnu.idatt2003.model.chanceCards;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import no.ntnu.idatt2003.model.Player;

/**
 * ChanceCardMove class represents a chance card that moves the player to a new position on the board.
 */
public class ChanceCardMove implements ChanceCard {
    private final int newPosition;
    private final String description;

    /**
     * Constructor for ChanceCardMove.
     * @param position the new position to move the player to
     * @param description the description of the chance card
     */
    public ChanceCardMove(int position, String description) {
        this.newPosition = position+1;
        this.description = description;
    }

    /**
     * toString method returns the description of the chance card.
     */
    @Override
    public String toString() {
        return this.description;
    }

    /**
     * The effect method is called when a player draws a chance card.
     * @param player the player who drew the chance card, and who will be moved
     */
    @Override
    public void effect(Player player) {
        PauseTransition pause = new PauseTransition(Duration.millis(1500));
        
        pause.setOnFinished(e -> {
            if (newPosition < player.getPosition()) {
                player.addPlayerBalance(2000);
            } 
            player.setPosition(newPosition);
        });
        pause.play();
    }
}

