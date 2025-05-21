package monopolyTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;

public class StartAndJailTileTest {

  private Player player3;
  private Player player2;
  private Player player1;
  private Game game;

  @BeforeEach
  void setUp() {
    game = (Game) TestSetup.createMonoplygameSetUp().get("game");
    player3 = (Player) TestSetup.createMonoplygameSetUp().get("player3");
    player2 = (Player) TestSetup.createMonoplygameSetUp().get("player2");
    player1 = (Player) TestSetup.createMonoplygameSetUp().get("player1");
  }

  @Test
  void ShouldGoToJail() {
    player1.setPosition(30);
    game.getBoard().getGameboard().get(player1.getPosition()).action(player1, game);
    assertEquals(player1.getJailStatus(), 1,
        "Player should be in jail after landing on the GoToJailTile.");
  }

  @Test
  void ShouldGetMoneyForLandingOnStart() {
    int expectedMoney = player2.getBalance() + 2000;
    player2.setPosition(0);
    game.getBoard().getGameboard().get(player2.getPosition()).action(player2, game);
    assertEquals(expectedMoney, player2.getBalance(),
        "Player should receive 2000 for passing the StartTile.");
  }

  @Test
  void jailShouldNotBeActive() {
    player3.setPosition(10);
    game.getBoard().getGameboard().get(player3.getPosition()).action(player3, game);
    assertEquals(player3.getJailStatus(), 0,
        "Player should not be in jail after landing on the StartTile.");
  }

  @Test
  void jailShouldBeActive() {
    player3.setPosition(10);
    player3.setJailStatus(1);
    game.getBoard().getGameboard().get(player3.getPosition()).action(player3, game);
    assertEquals(player3.getJailStatus(), 2,
        "Player should not be in jail after landing on the StartTile.");
  }
}
