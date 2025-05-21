package laddergameTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.tile.LadderTile;

public class LadderTileTest {

  private Player player;
  private Game game;

  @BeforeEach
  void setUp() {
    game = (Game) TestSetup.createLaddergameSetUp().get("game");
    player = (Player) TestSetup.createLaddergameSetUp().get("player1");
  }

  @Test
  void testLadderTile() {

    LadderTile ladderTile = new LadderTile(2, 5);
    player.setPosition(2);
    ladderTile.action(player, game);

    assertEquals(5, player.getPosition(),
        "Player should have moved to the travel location of the ladder tile.");
  }
}
