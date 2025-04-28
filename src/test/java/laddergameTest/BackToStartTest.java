package laddergameTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import laddergameTest.dummyClasses.TestSetup;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.tile.BackToStartTile;

public class BackToStartTest {
    
    private Game game;
    private Player nils;

    /**
     * Sets up the test environment by creating a game and a player.
     */
    @BeforeEach
    void setUp() {    
        game = (Game) TestSetup.createLaddergameSetUp().get("game");
        nils = (Player) TestSetup.createLaddergameSetUp().get("nils");
    }

    /**
     * Checks that the player is moved back to the start position when landing on a BackToStartTile.
     */
    @Test
    void testBackToStart() {
        BackToStartTile backToStartTile = new BackToStartTile(5);
        nils.setPosition(5); 
        backToStartTile.action(nils, game);
        assertEquals(0, nils.getPosition(), "Player should be moved back to start position (0).");
    }        
}
