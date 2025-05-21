package monopolyTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.Property;
import no.ntnu.idatt2003.model.tile.PropertyTile;

public class PropertyTileTests {

  private Player player3;
  private Player player2;
  private Game game;

  @BeforeEach
  void setUp() {
    game = (Game) TestSetup.createMonoplygameSetUp().get("game");
    player3 = (Player) TestSetup.createMonoplygameSetUp().get("player3");
    player2 = (Player) TestSetup.createMonoplygameSetUp().get("player2");
  }

  @Test
  void shouldBeInMolde() {
    PropertyTile tile = (PropertyTile) game.getBoard().getGameboard().get(player3.getPosition());
    Property property = tile.getProperty();
    String expectedName = "Molde";
    String actualName = property.getName();
    assertEquals(expectedName, actualName, "The property name should be Molde.");
  }

  @Test
  void shouldBeInTrondheim() {
    PropertyTile tile = (PropertyTile) game.getBoard().getGameboard().get(player2.getPosition());
    Property property = tile.getProperty();
    String expectedName = "Trondheim";
    String actualName = property.getName();
    assertEquals(expectedName, actualName, "The property name should be Drammen.");
  }

  @Test
  void shouldPayBaseRent() {
    PropertyTile tile = (PropertyTile) game.getBoard().getGameboard().get(player2.getPosition());
    Property property = tile.getProperty();
    int excpecedBalance = 9950;
    player2.addProperty(property);
    property.setOwner(player2);
    player3.setPosition(player2.getPosition());
    tile.action(player3, game);
    int actualBalance = player3.getBalance();
    assertEquals(excpecedBalance, actualBalance, "The player should have paid rent.");
  }

  @Test
  void shouldPayRentWithHouse() {
    PropertyTile tile = (PropertyTile) game.getBoard().getGameboard().get(player2.getPosition());
    Property property = tile.getProperty();
    int expectedBalance = 9900;
    player2.addProperty(property);
    property.setOwner(player2);
    property.buyHouse();
    player3.setPosition(player2.getPosition());
    tile.action(player3, game);
    int actualBalance = player3.getBalance();
    assertEquals(expectedBalance, actualBalance,
        "The player should have paid the right amount of rent.");
  }

}
