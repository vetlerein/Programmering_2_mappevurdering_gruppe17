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
    public boolean playerActive;
    public boolean playerPause = false;
    public Game game;
    private String picture = null;

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
     * sets the active game
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * sets the position of the player
     * @param position
     */
    public void setPosition(int Position) {
        this.position = position;
    }

    /**
     * pauses the player
     */
    public void setPlayerPause() {
        this.playerPause = true;
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
     * Returns the current position of the player on the board.
     * @return the active game
     */
    public Game getGame() {
        return this.game;
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
     * Returns the picture of the player.
     * @return player picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Sets the picture of the player.
     * @param picture the picture of the player
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * Throws the dice and moves the player on the board, and does actions according on where it lands
     */
    public void move(){
        if(playerPause == false) {
            int diceRoll = Dice.rollDice(2);
            int finalTile = game.getBoard().getGameboard().size();
            if (this.position + diceRoll <= finalTile) {
                this.position += diceRoll;
            } else if (this.position + diceRoll > finalTile) {
                this.position = finalTile - (diceRoll-(finalTile-this.position));
            }

            this.game.getBoard().getGameboard().get(this.position).action(this);

        } else if (playerPause == true) {
            playerPause = false;
        }
    }
}
