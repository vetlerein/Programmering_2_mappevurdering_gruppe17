package monopolyTests;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.chanceCards.ChanceCard;
import no.ntnu.idatt2003.model.chanceCards.ChanceCardFactory;
import no.ntnu.idatt2003.model.chanceCards.ChanceCardJail;
import no.ntnu.idatt2003.model.chanceCards.ChanceCardMoney;
import no.ntnu.idatt2003.model.chanceCards.ChanceCardMove;
import no.ntnu.idatt2003.model.tile.ChanceCardTile;
import no.ntnu.idatt2003.model.tile.Tile;

public class ChanceCardsTest {
    private Player player3;
    private Player player2;
    private Game game;

    /**
     * Sets up the test environment by creating a game and a player.
     */
    @BeforeEach
    void setUp() {    
        game = (Game) TestSetup.createMonoplygameSetUp().get("game");
        player3 = (Player) TestSetup.createMonoplygameSetUp().get("player3");
        player2 = (Player) TestSetup.createMonoplygameSetUp().get("player2");
    }

    /**
    * Checks that the player is on the correct position when landing on a specific ChanceCardTile.
    */
    @Test
    void checkThatGetsChanceCard() {
        player3.setPosition(5);
        ChanceCardTile tile = (ChanceCardTile) game.getBoard().getGameboard().get(player3.getPosition());
        tile.action(player3, game);
        assertTrue(tile.getActiveChanceCard() != null, "Player should have received a chance card.");
    }

    @Test
    void checkCardFunctions() {
        player2.setPosition(5);
        int balanceBefore = player2.getBalance();
        int positionBefore = player2.getPosition();
        boolean hasJailCardBefore = player2.getJailCard();
    
        for (int i = 0; i < 50; i++) {
            player2.setPosition(5);
            ChanceCardTile tile = (ChanceCardTile) game.getBoard().getGameboard().get(player2.getPosition());
            tile.action(player2, game);
            ChanceCard card = tile.getActiveChanceCard();

            if (card instanceof ChanceCardMoney) {
                assertTrue(balanceBefore != player2.getBalance(), "Player should have received or lost money.");
                balanceBefore = player2.getBalance();
            } else if (card instanceof ChanceCardMove) { 
                ChanceCardMove moveCard = (ChanceCardMove) card;
                if (moveCard.getNewPosition() == positionBefore) {
                    assertEquals(positionBefore, moveCard.getNewPosition());
                } else {
                    assertTrue(positionBefore != player2.getPosition(), "Player should have moved.");
                    positionBefore = player2.getPosition();
                }
            } else if (card instanceof ChanceCardJail) {
                if (hasJailCardBefore) {
                    assertEquals(hasJailCardBefore, player2.getJailCard(), "Player should have received a jail card.");
                    hasJailCardBefore = player2.getJailCard();
                } else {
                    assertTrue(player2.getJailCard(), "Player should be in jail.");
                }
            }
        }
    }
}
