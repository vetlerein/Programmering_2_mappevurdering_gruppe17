package laddergameTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import laddergameTest.dummyClasses.TestSetup;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.tile.PlayerSwapTile;

public class PlayerSwapTest {

    private Game game;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void setUp() {
        Map<String, Object> setup = TestSetup.createLaddergameSetUp();
        game = (Game) setup.get("game");
        player1 = (Player) setup.get("player1");
        player2 = (Player) setup.get("player2");
        player3 = (Player) setup.get("player3");

        System.out.println("Player 1: " + player1.getPlayerName() + ", Position: " + player1.getPosition());
        System.out.println("Player 2: " + player2.getPlayerName() + ", Position: " + player2.getPosition());
        System.out.println("Player 3: " + player3.getPlayerName() + ", Position: " + player3.getPosition());
        System.out.println("Game: " + game);
    }

    /**
     * Checks that the player swaps with the player furthest ahead.
     */
    @Test
    void testPlayerSwap() {
        PlayerSwapTile swapTile = new PlayerSwapTile(0);
        swapTile.action(player1, game);

        assertEquals(5, player1.getPosition(), "player1 should have swapped with player3.");
        assertEquals(2, player2.getPosition(), "player2 should not have moved.");
        assertEquals(1, player3.getPosition(), "player3 should have swapped with player1.");
    }

    /**
     * Checks that the player swaps with the furthest other player, while being the furthest player themselves. 
     */
    @Test
    void testPlayerSwapsWithFurthestPlayer(){

        PlayerSwapTile swapTile = new PlayerSwapTile(0);
        swapTile.action(player3, game);

        assertEquals(5, player2.getPosition(), "player2 should have swapped with player3.");
        assertEquals(2, player3.getPosition(), "player3 should have swapped with player2.");
        assertEquals(1, player1.getPosition(), "player1 should not have moved.");
    }
}