package no.ntnu.idatt2003.model;
import java.net.URL;
import java.util.ArrayList;
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
  
    private int balance;
    private ArrayList<Property> properties = new ArrayList<Property>();
    private int jailStatus = 0;
    private boolean getOutOfJailCard = false;

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
     * sets the balance of the player
     * @param balance the new balance of the player
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * adds the inputed property to the players property list
     * @param property the property to add to the players property list
     */
    public void addProperty(Property property) {
        this.properties.add(property);
        property.setOwner(this);
    }

    public ArrayList<Property> getPropertyList() {
        return this.properties;
    }

    /**
     * Simulates a turn in jail.
     */
    public void turnInJail() {
        if (this.jailStatus <= 5) {
            this.jailStatus++;
        } else {
            this.jailStatus = 0;
        }
    }

    /**
     * Sets the jail status of the player to 1, which means the player is in jail.
     */
    public void sendToJail() {
        this.jailStatus = 1;
        setPosition(11); //TODO change to jail tile position
    }


    /**
     * Uses the get out of jail card if the player has one.
     */
    public void useJailCard() {
        if(this.getOutOfJailCard == true) {
            this.jailStatus = 0;
            this.getOutOfJailCard = false;
        }
    }

    /**
     * gives the player a get out of jail card
     */
    public void giveJailCard() {
        this.getOutOfJailCard = true;
    }

    /**
     * removes the inputed property from the players property list
     * @param property the property to remove from the players property list
     */
    public void removeProperty(Property property) {
        if (property.getOwner() == this) {
            this.properties.remove(property);
            property.setOwner(null);
        }
    }

    /**
     * adds the the amount to the players balance
     * @param amount the amount of money to add to the players balance
     */
    public void addPlayerBalance(int amount) {
        this.balance += amount;
    }

    /**
     * returns the balance of the player
     * @return balance of the player
     */
    public int getBalance() {
        return this.balance;
    }

    /**
     * returns the players pause status
     * @returns the players pause status
     */
    public boolean getPlayerPause() {
        return this.playerPause;
    }

    /**
     * returns the players jail status
     */
    public int getJailStatus() {
        return this.jailStatus;
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
        if(playerPause == false && jailStatus == 0) {
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
        } else if (jailStatus > 0) {
            turnInJail();
            //TODO add option to pay to get out of jail and throw dice to get out
        }
        
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
