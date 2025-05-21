package monopolyTests;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import no.ntnu.idatt2003.model.Board;
import no.ntnu.idatt2003.model.BoardGameFactory;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.chanceCards.ChanceCardMove;

public class TestSetup {

  public static Map<String, Object> createMonoplygameSetUp() {
    Map<String, Object> setup = new HashMap<>();

    Player player1 = new Player("player1", 1, new Date());
    player1.setPosition(1);
    Player player2 = new Player("player2", 2, new Date());
    player2.setPosition(2);
    Player player3 = new Player("player3", 3, new Date());
    player3.setPosition(39);

    ArrayList<Player> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);

    Board board = BoardGameFactory.createMonopolyBoard();
    Game game = new Game(players, board);

    ChanceCardMove.SKIP_DELAY_IN_TEST = true;

    setup.put("game", game);
    setup.put("player1", player1);
    setup.put("player2", player2);
    setup.put("player3", player3);

    return setup;
  }
}
