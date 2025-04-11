package no.ntnu.idatt2003.model;
import java.net.URL;
import java.util.Date;

import no.ntnu.idatt2003.view.PositionChangeObserver;

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

    private PositionChangeObserver observer;
    public URL[] dicePaths;
    private URL picture;

    /**
     * Constructs a player with a start position, name, number and birthdate.
     *
     * @param playerName the name of the player
     * @param playerNumber the number of the player
     * @param birthDate the birthdate of the player
     */
    public Player(String playerName, int playerNumber, Date birthDate) {
        this.position = 1;
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
     * Sets the observer for the player.
     * @param observer the observer for the player
     */
    public void setObserver(PositionChangeObserver observer) {
        this.observer = observer;
    }

    /**
     * sets the position of the player
     * @param position
     */
    public void setPosition(int positionIn) {
        this.position = positionIn;
    }

    /**
     * pauses the player
     */
    public void setPlayerPause() {
        this.playerPause = true;
    }

    /**
     * Sets the dice paths for the player.
     */
    public void setDicePaths(URL[] dicePaths) {
        this.dicePaths = dicePaths;
    }

    /**
     * Returns the dice paths for the player.
     * @return dice paths
     */
    public URL[] getDicePaths() {
        return this.dicePaths;
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
     * Returns the picture of the player.
     * @return player picture
     */
    public URL getPicture() {
        return picture;
    }

    /**
     * Sets the picture of the player.
     * @param picture the picture of the player
     */
    public void setPicture(URL picture) {
        this.picture = picture;
    }

    /**
     * Throws the dice and moves the player on the board, and does actions according on where it lands
     */
    public void move(Game game) {
        if(playerPause == false) {
            int diceRoll = Dice.rollDice(2, this);
            int finalTile = game.getBoard().getGameboard().size();
            if (this.position + diceRoll <= finalTile) {
                this.position += diceRoll;
            } else if (this.position + diceRoll > finalTile) {
                this.position = finalTile - (diceRoll-(finalTile-this.position));
            }

            game.getBoard().getGameboard().get(this.position-1).action(this, game);   
        } else if (playerPause == true) {
            playerPause = false;
        }
        System.out.println("Player " + this.playerName + " and moved to tile " + this.position);
        if (observer != null) {
            observer.positionChanged(this);
        }
        game.nextPlayer();
    }

    @Override
    public String toString() {
        return playerName;
    }
}
