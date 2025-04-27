package laddergameTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import laddergameTest.dummyClasses.TestSetup;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;

public class PauseTileTest {

    private Player nils;
    private Game game;

    @BeforeEach
    void setUp() {
        Map<String, Object> setup = TestSetup.createLaddergameSetUp();
        nils = (Player) setup.get("nils");
        game = (Game) setup.get("game");
    }

    /**
     * Checks that the player is paused when landing on a PauseTile.
     */
    @Test
    void testPlayerStaysOnSame(){
        nils.setPlayerPause();
        assertFalse(nils.getPlayerActive(), "Player should be paused after landing on a PauseTile.");
    }

    /**
     * Checks that the player is active again after one round of pause.
     */
    @Test
    void testPlayerContinuesAfterOneRound(){
        nils.setPlayerPause();
        assertTrue(nils.isPlayerPause(), "Player should be paused after landing on a PauseTile.");
        nils.move(game);
        assertFalse(nils.getPlayerActive(), "Player should be active after one round of pause.");
    }
}