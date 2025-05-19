package no.ntnu.idatt2003.model.tile;

import java.util.ArrayList;
import java.util.Random;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.chanceCards.ChanceCard;
import no.ntnu.idatt2003.model.chanceCards.ChanceCardFactory;

/**
 * The ChanceCardTile class represents a tile on the game board that allows players to draw chance
 * cards.
 */
public class ChanceCardTile extends Tile {

  private final ArrayList<ChanceCard> chanceCards;
  private ChanceCard activeChanceCard;
 
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
    activeChanceCard = chanceCards.get(randomIndex);
    chanceCards.get(randomIndex).effect(player);
  }

  /**
   * Returns the current displayed chance card.
   *
   * @return the active chance card
   */
  public ChanceCard getActiveChanceCard() {
    return this.activeChanceCard;
  }
}
