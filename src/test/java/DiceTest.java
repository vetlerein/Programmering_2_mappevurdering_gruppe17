import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import laddergameTest.TestSetup;
import no.ntnu.idatt2003.model.Dice;
import no.ntnu.idatt2003.model.Player;

public class DiceTest {

  private Player player;

  @BeforeEach
  void setUp() {
    Map<String, Object> setup = TestSetup.createLaddergameSetUp();
    player = (Player) setup.get("player1");
  }

  @Test
  public void throwDiceZeroTimes() {
    assertThrows(IllegalArgumentException.class, () -> Dice.rollDice(0, player));
  }

  @Test
  public void diceReturnsValidNumber() {
    for (int i = 0; i < 100; i++) {
      int diceRoll = Dice.rollDice(1, player);
      assertTrue(diceRoll <= 6 && diceRoll >= 1);
    }
  }

  @Test
  public void diceIncludesAllNumbers() {
    ArrayList<Integer> diceRolls = new ArrayList<>();
    for (int i = 0; i < 200; i++) {
      diceRolls.add(Dice.rollDice(1, player));
    }
    assertTrue(diceRolls.contains(1));
    assertTrue(diceRolls.contains(2));
    assertTrue(diceRolls.contains(3));
    assertTrue(diceRolls.contains(4));
    assertTrue(diceRolls.contains(5));
    assertTrue(diceRolls.contains(6));
  }
}
