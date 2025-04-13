import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt2003.model.Dice;
import no.ntnu.idatt2003.model.Player;

public class DiceTest {

    Player testPlayer = new Player("testPlayer", 1, new Date());

    /**
    * Checks that the method throws an excpetion when given 0 as input
    */
    @Test
    public void throwDiceZeroTimes() {
        assertThrows(IllegalArgumentException.class, () -> Dice.rollDice(0, testPlayer));
    }

    /**
    * Checks that the dice returns only valid values
    */
    @Test
    public void diceReturnsValidNumber () {
        for (int i = 0; i < 100; i++){
            int diceRoll = Dice.rollDice(1, testPlayer);
            assertTrue(diceRoll<=6 && diceRoll>=1);
        }
    }

    /**
    * Checks that the dice give atleast one of each value on 100 throws
    */
    @Test
    public void diceIncludesAllNumbers () {
        ArrayList<Integer> diceRolls = new ArrayList<>();
        for (int i = 0; i<200; i++){
            diceRolls.add(Dice.rollDice(1, testPlayer));
        }
        assertTrue(diceRolls.contains(1));
        assertTrue(diceRolls.contains(2));
        assertTrue(diceRolls.contains(3));
        assertTrue(diceRolls.contains(4));
        assertTrue(diceRolls.contains(5));
        assertTrue(diceRolls.contains(6));
    }
}
