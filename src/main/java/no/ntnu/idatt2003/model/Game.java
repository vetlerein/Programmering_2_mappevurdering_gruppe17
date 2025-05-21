package no.ntnu.idatt2003.model;

import java.util.ArrayList;

import no.ntnu.idatt2003.view.GenericGameView;
import no.ntnu.idatt2003.view.LaddergameView;
import no.ntnu.idatt2003.view.MonopolyView;

/**
 * This class represents a game and includes the players and the board.
 */
public class Game {
 
  /**
   * Boolean for game status.
   */
  public boolean gameActiveStatus;
  /**
   * The amount of players in the game.
   */
  public int playerAmount;
  /**
   * The index of the active player.
   */
  public int activePlayer;
  /**
   * List of the players in the game.
   */
  public ArrayList<Player> players;
  /**
   * The game board.
   */
  public Board board;

  /**
   * Constructs a game with a given amount of players, board width, board height and players.
   *
   * @param players   the active players in the game
   * @param gameboard the active gameboard
   */
  public Game(ArrayList<Player> players, Board gameboard) {
    gameActiveStatus = true;
    this.playerAmount = players.size();
    this.players = players;
    board = gameboard;
  }

  /**
   * the generic game view for the game
   */
  public static GenericGameView genericGameView;

  /**
   * Sets the generic game view for the game
   *
   * @param view the generic game view
   */
  public static void setView(GenericGameView view) {
    genericGameView = view;
  }

  /**
   * the laddergameview for the game
   */
  public static LaddergameView laddergameView;

  /**
   * Sets the laddergameview for the game
   *
   * @param view the laddergameview
   */
  public static void setLadderView(LaddergameView view) {
    laddergameView = view;
  }

  /**
   * the monopolyview for the game
   */
  public static MonopolyView monopolyView;

  /**
   * Sets the monopolyview for the game
   *
   * @param view the monopolyview
   */
  public static void setMonopolyView(MonopolyView view) {
    monopolyView = view;
  }

  /**
   * Returns the laddergameview.
   *
   * @return the laddergameview
   */
  public LaddergameView getLaddergameView() {
    return laddergameView;
  }

  /**
   * The game is finished.
   *
   * @param player the winner of the game
   */
  public void finish(Player player) {
    gameActiveStatus = false;
    if (genericGameView != null) {
      genericGameView.playerWon(player);
    }
  }

  /**
   * Starts the game.
   */
  public void start() {
    players.sort((a, b) -> b.getBirthDate().compareTo(a.getBirthDate()));
    for (Player player : players) {
      player.setPlayerActive(true);
    }
  }

  /**
   * Returns the active game board
   *
   * @return gameboard the active board
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Returns game status
   *
   * @return game status
   */
  public boolean getGameStatus() {
    return gameActiveStatus;
  }

  /**
   * Returns the active player
   *
   * @return the index of the active player
   */
  public int getActivePlayer() {
    return activePlayer;
  }

  /**
   * Changes whose turn it is
   */
  public void nextPlayer() {
    if (activePlayer == players.size() - 1) {
      activePlayer = 0;
    } else {
      activePlayer++;
    }
  }

  /**
   * Returns a list of the players in the game
   *
   * @return the players in the game
   */
  public ArrayList<Player> getPlayers() {
    return this.players;
  }
}
