package no.ntnu.idatt2003.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import no.ntnu.idatt2003.model.tile.FinishTile;
import no.ntnu.idatt2003.model.tile.StartTile;
import no.ntnu.idatt2003.view.PositionChangeObserver;

/**
 * Represents a player in the game.
 */
public class Player {

  /**
   * The position of the player on the board.
   */
  public int position;

  /**
   * The name of the player.
   */
  public String playerName;

  /**
   * The number of the player.
   */
  public int playerNumber;

  /**
   * The birthdate of the player.
   */
  private final Date birthDate;

  /**
   * boolean for if the player is active
   */
  public boolean playerActive;

  /**
   * boolean for if the player is paused.
   */
  public boolean playerPause = false;

  /**
   * The total balance of the player.
   */
  private int balance = 10000;

  /**
   * The list of properties owned by the player.
   */
  private final ArrayList<Property> properties = new ArrayList<>();

  /**
   * The jail status of the player.
   */
  private int jailStatus = 0;

  /**
   * Boolean for if the payer has a get out of jail free card.
   */
  private boolean getOutOfJailCard = false;

  /**
   * The observer for the player.
   */
  private PositionChangeObserver observer;

  /**
   * The paths to the dice images.
   */
  public URL[] dicePaths;

  /**
   * The picture of the player.
   */
  private URL picture;

  /**
   * Constructs a player with a start position, name, number and birthdate.
   *
   * @param playerName   the name of the player
   * @param playerNumber the number of the player
   * @param birthDate    the birthdate of the player
   */
  public Player(String playerName, int playerNumber, Date birthDate) {
    this.position = 1;
    this.playerName = playerName;
    this.playerNumber = playerNumber;
    this.birthDate = birthDate;
  }

  /**
   * Actives the player for the game.
   *
   * @param active the active status of the player
   */
  public void setPlayerActive(Boolean active) {
    this.playerActive = active;
  }

  /**
   * Sets the observer for the player.
   *
   * @param observer the observer for the player
   */
  public void setObserver(PositionChangeObserver observer) {
    this.observer = observer;
  }

  /**
   * sets the position of the player
   *
   * @param positionIn the new position of the player
   */
  public void setPosition(int positionIn) {
    this.position = positionIn;
    if (observer != null) {
      observer.positionChanged(this);
    }
  }

  /**
   * pauses the player
   */
  public void setPlayerPause() {
    this.playerPause = true;
  }

  /**
   * sets the balance of the player
   *
   * @param balance the new balance of the player
   */
  public void setBalance(int balance) {
    this.balance = balance;
    if (observer != null) {
      observer.positionChanged(this);
    }
  }

  /**
   * adds the inputted property to the players property list
   *
   * @param property the property to add to the players property list
   */
  public void addProperty(Property property) {
    this.properties.add(property);
    property.setOwner(this);
    if (observer != null) {
      observer.positionChanged(this);
    }
  }

  /**
   * returns the players property list
   *
   * @return the players property list
   */
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
    setPosition(11);
  }

  /**
   * Changes the players jail status
   *
   * @param newStatus the new jail status of the palyer
   */
  public void setJailStatus(int newStatus) {
    this.jailStatus = newStatus;
  }


  /**
   * Uses the get out of jail card if the player has one.
   */
  public void useJailCard() {
    if (this.getOutOfJailCard) {
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
   * returns if the player has a get out of jail free card
   *
   * @return boolean for if the player has a get out of jail free card
   */
  public boolean getJailCard() {
    return this.getOutOfJailCard;
  }

  /**
   * removes the inputed property from the players property list
   *
   * @param property the property to remove from the players property list
   */
  public void removeProperty(Property property) {
    if (property.getOwner() == this) {
      this.properties.remove(property);
      property.setOwner(null);
    }
    if (observer != null) {
      observer.positionChanged(this);
    }
  }

  /**
   * adds the amount to the players balance
   *
   * @param amount the amount of money to add to the players balance
   */
  public void addPlayerBalance(int amount) {
    this.balance += amount;
    if (observer != null) {
      observer.positionChanged(this);
    }
  }

  /**
   * returns the balance of the player
   *
   * @return balance of the player
   */
  public int getBalance() {
    return this.balance;
  }

  /**
   * returns the players pause status
   *
   * @return the players pause status
   */
  public boolean getPlayerPause() {
    return this.playerPause;
  }

  /**
   * returns the players jail status
   *
   * @return the players jail status
   */
  public int getJailStatus() {
    return this.jailStatus;
  }

  /**
   * Sets the dice paths for the player.
   *
   * @param dicePaths the paths to the dice images
   */
  public void setDicePaths(URL[] dicePaths) {
    this.dicePaths = dicePaths;
  }

  /**
   * Returns the dice paths for the player.
   *
   * @return dice paths
   */
  public URL[] getDicePaths() {
    return this.dicePaths;
  }

  /**
   * Checks if player is active.
   *
   * @return wether the player is active or not
   */
  public boolean getPlayerActive() {
    return this.playerActive;
  }

  /**
   * Returns the current position of the player on the board.
   *
   * @return player position
   */
  public int getPosition() {
    return this.position;
  }

  /**
   * Returns the properties of the player.
   *
   * @return player properties
   */
  public ArrayList<Property> getProperties() {
    return this.properties;
  }

  /**
   * Returns the name of the player.
   *
   * @return player name
   */
  public String getPlayerName() {
    return this.playerName;
  }

  /**
   * Returns the birthdate of the player.
   *
   * @return player birthdate
   */
  public Date getBirthDate() {
    return this.birthDate;
  }

  /**
   * Returns the number of the player.
   *
   * @return player number
   */
  public int getPlayerNumber() {
    return this.playerNumber;
  }

  /**
   * Returns the picture of the player.
   *
   * @return player picture
   */
  public URL getPicture() {
    return picture;
  }

  /**
   * Sets the picture of the player.
   *
   * @param picture the picture of the player
   */
  public void setPicture(URL picture) {
    this.picture = picture;
  }

  /**
   * Throws the dice and moves the player on the board, and does actions according on where it
   * lands
   *
   * @param game the game instance
   */
  public void move(Game game) {
    if (!playerPause && jailStatus == 0) {
      int diceRoll = Dice.rollDice(2, this);
      int finalTile = game.getBoard().getGameboard().size();
      if (this.position + diceRoll <= finalTile) {
        this.position += diceRoll;
      } else if ((this.position + diceRoll > finalTile) && game.getBoard().getGameboard()
          .get(finalTile - 1) instanceof FinishTile) {
        this.position = finalTile - (diceRoll - (finalTile - this.position));
      } else if ((this.position + diceRoll > finalTile) && game.getBoard().getGameboard()
          .getFirst() instanceof StartTile) {
        this.position = (this.position + diceRoll) - finalTile;
        if (this.position != 0) {
          game.getBoard().getGameboard().getFirst().action(this, game);
        }
      }

      game.getBoard().getGameboard().get(this.position - 1).action(this, game);
    } else if (playerPause) {
      playerPause = false;
    }

    if (observer != null) {
      observer.positionChanged(this);
    }
    game.nextPlayer();
  }

  /**
   * to string method for the player class
   *
   * @return player name
   */
  @Override
  public String toString() {
    return playerName;
  }
}
