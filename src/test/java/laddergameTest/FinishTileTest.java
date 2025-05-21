package laddergameTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;

public class FinishTileTest {
    
    private Game game;
    private Player player;

    @BeforeEach
    void setUp() {
        Map<String, Object> setup = TestSetup.createLaddergameSetUp();
        game = (Game) setup.get("game");
        player = (Player) setup.get("player1");
    }

    /**
     * Checks that the gamestatus is set to false when a player reaches the finish tile.
     */
    @Test
    void testIfFinishes(){
        game.finish(player);
        assertFalse(game.getGameStatus(), "Game should be finished after player reaches the finish tile.");
    }
}
