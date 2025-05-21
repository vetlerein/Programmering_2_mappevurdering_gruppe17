package no.ntnu.idatt2003.model.tile;

import java.util.ArrayList;
import java.util.Random;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.chanceCards.ChanceCard;
import no.ntnu.idatt2003.model.chanceCards.ChanceCardFactory;

/**
 * This tile makes the player draw a chance card when they land on it.
 */
public class ChanceCardTile extends Tile {

  /**
   * The list of chance cards available in the game.
   */
  private final ArrayList<ChanceCard> chanceCards;

  /**
   * The active chance card that was drawn.
   */
  private ChanceCard activChanceCard;
 
  /**
   * Constructor for the ChanceCardTile class.
   *
   * @param location the location of the tile
   */
  public ChanceCardTile(int location) {
    super(location);
    this.chanceCards = ChanceCardFactory.createDeck();
  }

  /**
   * Draws a chance card when the player lands on this tile.
   *
   * @param player the player who landed on the tile
   * @param game   the game instance
   */
  @Override
  public void action(Player player, Game game) {
    Random random = new Random();
    int randomIndex = random.nextInt(chanceCards.size());
    activChanceCard = chanceCards.get(randomIndex);
    chanceCards.get(randomIndex).effect(player);
  }

  /**
   * Returns the active chance card.
   *
   * @return the active chance card
   */
  public ChanceCard getActiveChanceCard() {
    return this.activChanceCard;
  }
}
