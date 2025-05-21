package no.ntnu.idatt2003.model.chanceCards;

import no.ntnu.idatt2003.model.Player;

/**
 * ChanceCardJail class represents a chance card that gives the player a "get out of jail free" card.
 */
public class ChanceCardJail implements ChanceCard {

  /**
   * The description of the chance card.
   */
  private final String description = "You recive a get out of jail free card!";

  /**
   * to string method for the chance card.
   * @return the description of the chance card
   */
  @Override
  public String toString() {
    return description;
  }

  /**
   * Effect of the chance card.
   * @param player the player that draws the card
   */
  @Override
  public void effect(Player player) {
    player.giveJailCard();
  }
}
