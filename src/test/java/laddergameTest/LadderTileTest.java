package laddergameTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import laddergameTest.dummyClasses.TestSetup;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.tile.LadderTile;

public class LadderTileTest {

    private Player nils;
    private Game game;

    /**
     * Sets up the test environment by creating a game and a player.
     */
    @BeforeEach
    void setUp() {
        game = (Game) TestSetup.createLaddergameSetUp().get("game");
        nils = (Player) TestSetup.createLaddergameSetUp().get("nils");
    }

    /**
     * Checks that the player moves to the travel location of the ladder tile.
     */
    @Test
    void testLadderTile() {

        LadderTile ladderTile = new LadderTile(2, 5);
        nils.setPosition(2);
        ladderTile.action(nils, game);

        assertEquals(5, nils.getPosition(), "Player should have moved to the travel location of the ladder tile.");
    }
}
