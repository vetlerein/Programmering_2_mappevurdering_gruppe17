import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt2003.model.Dice;

public class DiceTest {

    /**
    * Checks that the method throws an excpetion when given 0 as input
    */
    @Test
    public void throwDiceZeroTimes() {
        Dice dice = new Dice();
        assertThrows(IllegalArgumentException.class, () -> dice.rollDice(0));
    }

    /**
    * Checks that the dice returns only valid values
    */
    @Test
    public void diceReturnsValidNumber () {
        Dice dice = new Dice();
        for (int i = 0; i < 100; i++){
            int diceRoll = dice.rollDice(1);
            assertTrue(diceRoll<=6 && diceRoll>=1);
        }
    }

    @Test
    public void diceIncludesAllNumbers () {
        Dice dice = new Dice();
        ArrayList<Integer> diceRolls = new ArrayList<>();
        for (int i = 0; i<100; i++){
            diceRolls.add(dice.rollDice(1));
        }
        assertTrue(diceRolls.contains(1));
        assertTrue(diceRolls.contains(2));
        assertTrue(diceRolls.contains(3));
        assertTrue(diceRolls.contains(4));
        assertTrue(diceRolls.contains(5));
        assertTrue(diceRolls.contains(6));
    }
}
