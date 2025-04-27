package laddergameTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import no.ntnu.idatt2003.model.Board;
import no.ntnu.idatt2003.model.BoardGameFactory;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;

public class TestSetup {

    public static Map<String, Object> createLaddergameSetUp() {
        Map<String, Object> setup = new HashMap<>();

        Player vetle = new Player("Vetle", 1, new Date());
        vetle.setPosition(1);
        Player petter = new Player("Petter", 2, new Date());
        petter.setPosition(2);
        Player nils = new Player("Nils", 3, new Date());
        nils.setPosition(5);

        ArrayList<Player> players = new ArrayList<>();
        players.add(vetle);
        players.add(petter);
        players.add(nils);

        Board smallboard = BoardGameFactory.createSmallBoard();
        Game game = new Game(players, smallboard);

        setup.put("game", game);
        setup.put("vetle", vetle);
        setup.put("petter", petter);
        setup.put("nils", nils);

        return setup;
    }
}


