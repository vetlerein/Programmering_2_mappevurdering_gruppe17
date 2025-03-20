package no.ntnu.idatt2003.model;
import java.util.Date;

/**
 * Represents a player in the game.
 */
public class Player {
    public int position;
    public String playerName;
    public int playerNumber;
    private final Date birthDate;
    private final Dice playerDice = new Dice();
    public boolean playerActive;

    /**
     * Constructs a player with a start position, name, number and birthdate.
     *
     * @param playerName the name of the player
     * @param playerNumber the number of the player
     * @param birthDate the birthdate of the player
     */
    public Player(String playerName, int playerNumber, Date birthDate) {
        this.position = 0;
        this.playerName = playerName;
        this.playerNumber = playerNumber;
        this.birthDate = birthDate;
    }

    /**
     * Actives the player for the game.
     */
    public void setPlayerActive() {
        this.playerActive = true;
    }

    /**
     * Checks if player is active.
     * @return wether the player is active or not
     */
    public boolean getPlayerActive() {
        return this.playerActive;
    }

    /**
     * Returns the current position of the player on the board.
     * @return player position
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Returns the name of the player.
     * @return player name  
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Returns the birthdate of the player.
     * @return player birthdate
     */
    public Date getBirthDate(){
        return this.birthDate;
    }

    /**
     * Returns the number of the player.
     * @return player number
     */
    public int getPlayerNumber() {
        return this.playerNumber;
    }

    /**
     * Throws the dice and moves the player on the board.
     */
    public void move(){
        this.position += playerDice.rollDice(2);
        //Flytt spiller fysisk
    }

    /**
     * Switches the position of the player with another player.
     * @param newPosition the new position of the player
     */
    public void playerSwapped(int newPosition){
        this.position = newPosition;
    }
}
