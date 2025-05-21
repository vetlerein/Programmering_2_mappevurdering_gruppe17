package laddergameTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.tile.BackToStartTile;

public class BackToStartTest {

  private Game game;
  private Player player;

  @BeforeEach
  void setUp() {
    game = (Game) TestSetup.createLaddergameSetUp().get("game");
    player = (Player) TestSetup.createLaddergameSetUp().get("player1");
  }

  @Test
  void testBackToStart() {
    BackToStartTile backToStartTile = new BackToStartTile(5);
    player.setPosition(5);
    backToStartTile.action(player, game);
    assertEquals(0, player.getPosition(), "Player should be moved back to start position (0).");
  }
}
