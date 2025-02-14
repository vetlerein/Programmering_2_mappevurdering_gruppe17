package no.ntnu.idatt2003;

import java.util.Random;   

public class Dice {
    int diceValue;
    public int rollDice() {
        Random random = new Random();
        diceValue = random.nextInt(6) + 1;
        diceValue += random.nextInt(6) + 1;
        return diceValue;
    }
}
