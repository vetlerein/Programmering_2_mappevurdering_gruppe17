package no.ntnu.idatt2003.model.chanceCards;

import no.ntnu.idatt2003.model.Player;

/**
 * ChanceCardMoney class represents a chance card that affects the player's balance.
 * It can either add or subtract money from the player's balance.
 */
public class ChanceCardMoney implements ChanceCard {

  /**
   * The amount of money to be added or subtracted.
   */
  private final int amount;
  /**
   * The description of the chance card.
   */
  private final String description;

  /**
   * Constructor for the ChanceCardMoney class.
   *
   * @param amount      the amount of money to be added or subtracted
   * @param description the description of the chance card
   */
  public ChanceCardMoney(int amount, String description) {
    this.amount = amount;
    this.description = description;
  }

  /**
   * to string method for the chance card.
   *
   * @return the description of the chance card + the amount of money
   */
  @Override
  public String toString() {
      if (amount > 0) {
          return description + ", you recive " + amount + " $";
      } else {
          return description + ", you pay " + amount * -1 + " $";
      }
  }

  /**
   * Effect of the chance card.
   *
   * @param player the player that draws the card
   */
  @Override
  public void effect(Player player) {
    player.addPlayerBalance(amount);
  }
}
