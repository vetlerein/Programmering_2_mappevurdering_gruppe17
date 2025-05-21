package no.ntnu.idatt2003.model.chanceCards;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import no.ntnu.idatt2003.model.Player;

/**
 * ChanceCardMove class represents a chance card that moves the player to a new position on the
 * board.
 */
public class ChanceCardMove implements ChanceCard {
    private final int newPosition;
    private final String description;
    public static boolean SKIP_DELAY_IN_TEST = false;

    public ChanceCardMove(int position, String description) {
        this.newPosition = position+1;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

    /**
     * @return the new position of the player
     */
    public int getNewPosition() {
        return newPosition;
    }

    @Override
    public void effect(Player player) {
        PauseTransition pause = new PauseTransition(Duration.millis(1500));
        if(SKIP_DELAY_IN_TEST) {
            if (newPosition < player.getPosition()) {
                player.addPlayerBalance(2000);
            } 
            player.setPosition(newPosition);
        } else {
            pause.setOnFinished(e -> {
                if (newPosition < player.getPosition()) {
                    player.addPlayerBalance(2000);
                } 
                player.setPosition(newPosition);
            });
            pause.play();
        }
        
    }

}

