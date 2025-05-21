package no.ntnu.idatt2003.model;

import java.net.URL;
import java.util.Random;

/**
 * Represents a dice in the game.
 */
public class Dice {
 
  /**
   * Rolls a dice a given amout of times.
   *
   * @param diceAmount the amount of times to roll the dice
   * @param player     the player who rolled the dice
   * @return the sum of the dice rolls
   */
  public static int rollDice(int diceAmount, Player player) {
    /**
     * the value of the dice
     */
    int diceValue;
    /**
     * the paths to the dice images
     */
    URL[] dicePaths = new URL[diceAmount];

    if (diceAmount < 1) {
      throw new IllegalArgumentException("Dice needs to be thrown atleast once");
    }

    Random random = new Random();
    diceValue = 0;
    for (int i = 0; i < diceAmount; i++) {
      int diceFace = random.nextInt(6) + 1;
      diceValue += diceFace;
      dicePaths[i] = Dice.class.getResource("/dice/" + diceFace + ".png");
    }
    player.setDicePaths(dicePaths);

    return diceValue;
  }
}
