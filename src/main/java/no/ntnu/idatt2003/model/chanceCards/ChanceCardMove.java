package no.ntnu.idatt2003.model.chanceCards;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import no.ntnu.idatt2003.model.Player;

/**
 * ChanceCardMove is a class that represents a chance card that moves the player to a new position.
 * It implements the ChanceCard interface.
 */
public class ChanceCardMove implements ChanceCard {

  /**
   * The new position to move the player to.
   */
  private final int newPosition;
  /**
   * The description of the chance card.
   */
  private final String description;
  /**
   * A boolean to skip the delay in tests.
   */
  public static boolean SKIP_DELAY_IN_TEST = false;

  /**
   * Constructor for ChanceCardMove.
   *
   * @param position    the new position to move the player to
   * @param description the description of the chance card
   */
  public ChanceCardMove(int position, String description) {
    this.newPosition = position + 1;
    this.description = description;
  }

  /**
   * toString method returns the description of the chance card.
   * 
   * @return the description of the chance card
   */
  @Override
  public String toString() {
    return this.description;
  }

  /**
   * returns the position to move to.
   * @return the new position of the player
   */
  public int getNewPosition() {
    return newPosition;
  }

   /**
   * The effect method is called when a player draws a chance card.
   *
   * @param player the player who drew the chance card, and who will be moved
   */
  @Override
  public void effect(Player player) {
    PauseTransition pause = new PauseTransition(Duration.millis(1500));
    if (SKIP_DELAY_IN_TEST) {
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

