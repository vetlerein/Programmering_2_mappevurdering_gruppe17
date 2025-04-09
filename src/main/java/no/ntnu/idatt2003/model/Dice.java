package no.ntnu.idatt2003.model;

import java.util.Random;   

/**
 * Represents a dice in the game.
 */
public class Dice {
    

    /**
     * Rolls a dice a given amout of times.
     *
     * @param diceAmount the amount of times to roll the dice
     * @return the sum of the dice rolls
     */
    public static int rollDice(int diceAmount) {
        int diceValue;
        
        if (diceAmount < 1) {
            throw new IllegalArgumentException("Dice needs to be thrown atleast once");
        }

        Random random = new Random();
        diceValue = 0;
        for(int i = 0; i < diceAmount; i++){
            diceValue += random.nextInt(6) + 1;
        }
        return diceValue;
    }
}
