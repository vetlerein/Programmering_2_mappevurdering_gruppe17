package monopolyTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt2003.controller.MonopolyController;
import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.Property;
import no.ntnu.idatt2003.model.tile.PropertyTile;

public class MonopolyControllerTest {
    private Player player3;
    private Player player2;
    private Player player1;
    private Game game;
    private MonopolyController controller;

    /**
     * Sets up the test environment by creating a game and 3 players.
     */
    @BeforeEach
    void setUp() {    
        game = (Game) TestSetup.createMonoplygameSetUp().get("game");
        player3 = (Player) TestSetup.createMonoplygameSetUp().get("player3");
        player2 = (Player) TestSetup.createMonoplygameSetUp().get("player2");
        player1 = (Player) TestSetup.createMonoplygameSetUp().get("player1");
        controller = new MonopolyController();
    }

    @Test
    void PropertiesShouldBeTraded (){
        player1.setPosition(1);
        player2.setPosition(2);
        PropertyTile tile = (PropertyTile) game.getBoard().getGameboard().get(player1.getPosition());
        Property property1 = tile.getProperty();
        PropertyTile tile2 = (PropertyTile) game.getBoard().getGameboard().get(player2.getPosition());
        Property property2 = tile2.getProperty();
        player1.addProperty(property1);
        player2.addProperty(property2);

        controller.executeTrade(player1, player2, player1.getProperties(), player2.getProperties(), 0, 0);
        assertEquals(property1, player2.getProperties().getFirst(), "Player 2 should have property1");
        assertEquals(property2, player1.getProperties().getFirst(), "Player 1 should have property2");
    }

    @Test
    void TradeWithMoney (){
        controller.executeTrade(player1, player2, player1.getProperties(), player2.getProperties(), 1000, 0);
        assertEquals(9000, player1.getBalance(), "Player 1 should have 9000");
        assertEquals(11000, player2.getBalance(), "Player 2 should have 11000");
    }

    @Test
    void BuyHouseOnProperty (){
        player3.setPosition(1);
        PropertyTile tile = (PropertyTile) game.getBoard().getGameboard().get(player3.getPosition());
        Property property = tile.getProperty();
        player3.addProperty(property);
        controller.buyPropertyHouse(property);
        assertEquals(1, property.getPropertyLevel(), "Player 3 should have 1 house on the property");
        assertEquals(9500, player3.getBalance(), "Player 3 should have 9500");
    }

    @Test
    void CheckForWinnerTest() {
        player1.setBalance(0);
        player2.setBalance(0);
        player3.setBalance(1000);
        controller.checkForWinner(game);
        assertFalse(game.getGameStatus(), "There should be a winner");
    }

    @Test
    void PayForJailTest() {
        player1.setJailStatus(1);
        controller.payForJail(player1, game);
        assertEquals(8000, player1.getBalance(), "Player 1 should have 8000");
        assertEquals(0, player1.getJailStatus(), "Player 1 should not be in jail");
    }

    @Test
    void UseGetOutOfJailCardTest() {
        player1.giveJailCard();
        player1.setJailStatus(1);
        controller.useJailCard(player1, game);
        assertEquals(10000, player1.getBalance(), "Player 1 should have 10000");
        assertEquals(0, player1.getJailStatus(), "Player 1 should not be in jail");
    }

    @Test
    void BuyPropertyTest() {
        player1.setPosition(1);
        PropertyTile tile = (PropertyTile) game.getBoard().getGameboard().get(player1.getPosition());
        Property property = tile.getProperty();
        controller.buyProperty(property, player1);
        assertEquals(9200, player1.getBalance(), "Player 1 should have 9200");
        assertEquals(player1, property.getOwner(), "Player 1 should be the owner of the property");
        assertEquals(property, player1.getProperties().getFirst(), "Player 1 should have the property");
    }

    @Test
    void PawnPropertyTest() {
        player1.setPosition(1);
        PropertyTile tile = (PropertyTile) game.getBoard().getGameboard().get(player1.getPosition());
        Property property = tile.getProperty();
        player1.addProperty(property);
        controller.pawnProperty(property);
        assertEquals(10400, player1.getBalance(), "Player 1 should have 10400");
        assertTrue(property.isPawned(), "Property should be pawned");
    }

    @Test
    void RePurchasePropertyTest() {
        player1.setPosition(1);
        PropertyTile tile = (PropertyTile) game.getBoard().getGameboard().get(player1.getPosition());
        Property property = tile.getProperty();
        player1.addProperty(property);
        controller.pawnProperty(property);
        controller.rePurchaseProperty(property);
        assertEquals(9600, player1.getBalance(), "Player 1 should have 9600");
        assertFalse(property.isPawned(), "Property should not be pawned");
    }

    @Test
    void SellPropertyHouseTest() {
        player1.setPosition(1);
        PropertyTile tile = (PropertyTile) game.getBoard().getGameboard().get(player1.getPosition());
        Property property = tile.getProperty();
        player1.addProperty(property);
        controller.buyPropertyHouse(property);
        assertEquals(9500, player1.getBalance(), "Player 1 should have 9500");
        assertEquals(1, property.getPropertyLevel(), "Player 1 should have 1 house on the property");
        controller.sellPropertyHouse(property);
        assertEquals(9750, player1.getBalance(), "Player 1 should have 9750");
        assertEquals(0, property.getPropertyLevel(), "Player 1 should have 0 houses on the property");
    }
}
