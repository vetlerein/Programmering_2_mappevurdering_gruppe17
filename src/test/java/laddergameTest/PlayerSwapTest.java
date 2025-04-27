package laddergameTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void testPlayerSwapsWithFurthestPlayer(){

        PlayerSwapTile swapTile = new PlayerSwapTile(0);
        swapTile.action(nils, game);

        assertEquals(5, petter.getPosition());
        assertEquals(2, nils.getPosition());
        assertEquals(1, vetle.getPosition());
    }

    @Test
    void testPlayerSwap() {
        PlayerSwapTile swapTile = new PlayerSwapTile(0);
        swapTile.action(vetle, game);

        assertEquals(5, vetle.getPosition());
        assertEquals(2, petter.getPosition());
        assertEquals(1, nils.getPosition());
    }
}