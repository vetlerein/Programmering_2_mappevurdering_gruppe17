package no.ntnu.idatt2003.model.chanceCards;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import no.ntnu.idatt2003.model.Player;

public class ChanceCardMove implements ChanceCard {
    private final int newPosition;
    private final String description;

    public ChanceCardMove(int position, String description) {
        this.newPosition = position+1;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

    @Override
    public void effect(Player player) {
        PauseTransition pause = new PauseTransition(Duration.millis(4000));
        
        pause.setOnFinished(e -> {
            if (newPosition < player.getPosition()) {
                player.addPlayerBalance(2000);
            } 
            player.setPosition(newPosition);
        });
        pause.play();
    }
}

