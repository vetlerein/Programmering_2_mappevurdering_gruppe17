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
    private Player vetle;
    private Player petter;
    private Player nils;

    @BeforeEach
    void setUp() {
        Map<String, Object> setup = TestSetup.createLaddergameSetUp();
        game = (Game) setup.get("game");
        vetle = (Player) setup.get("vetle");
        petter = (Player) setup.get("petter");
        nils = (Player) setup.get("nils");
    }

    /**
     * Checks that the player swaps with the player furthest ahead.
     */
    @Test
    void testPlayerSwap() {
        PlayerSwapTile swapTile = new PlayerSwapTile(0);
        swapTile.action(vetle, game);

        assertEquals(5, vetle.getPosition(), "Vetle should have swapped with Nils.");
        assertEquals(2, petter.getPosition(), "Petter should not have moved.");
        assertEquals(1, nils.getPosition(), "Nils should have swapped with Vetle.");
    }

    /**
     * Checks that the player swaps with the furthest other player, while being the furthest player themselves. 
     */
    @Test
    void testPlayerSwapsWithFurthestPlayer(){

        PlayerSwapTile swapTile = new PlayerSwapTile(0);
        swapTile.action(nils, game);

        assertEquals(5, petter.getPosition(), "Petter should have swapped with Nils.");
        assertEquals(2, nils.getPosition(), "Nils should have swapped with Petter.");
        assertEquals(1, vetle.getPosition(), "Vetle should not have moved.");
    }
}