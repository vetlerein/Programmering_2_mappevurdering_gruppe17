package laddergameTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;

public class FinishTileTest {
    
    private Game game;
    private Player nils;

    @BeforeEach
    void setUp() {
        Map<String, Object> setup = TestSetup.createLaddergameSetUp();
        game = (Game) setup.get("game");
        nils = (Player) setup.get("nils");
    }

    @Test
    void testIfFinishes(){
        game.finish(nils);
        assertFalse(game.getGameStatus());
    }
}
