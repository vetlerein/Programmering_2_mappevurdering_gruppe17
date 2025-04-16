package no.ntnu.idatt2003.model;

import java.util.ArrayList;

import no.ntnu.idatt2003.view.GenericGameView;
import no.ntnu.idatt2003.view.LaddergameView;

/**
 * Controls the game.
 */
public class Game {
    public boolean gameActiveStatus;
    public int playerAmount;
    public int activePlayer;
    public ArrayList<Player> players;
    public Board board;

    /**
     * Constructs a game with a given amount of players, board width, board height and players.
     *
     * @param board the active gameboard
     * @param players the active players in the game
     */
    public Game(ArrayList<Player> players, Board gameboard){
        gameActiveStatus = true;
        this.playerAmount = players.size();
        this.players = players;
        board = gameboard;
    }

    public static GenericGameView genericGameView;
    public static void setView(GenericGameView view){
        genericGameView = view;
    }

    public static LaddergameView laddergameView;
    public static void setLadderView(LaddergameView view){
        laddergameView = view;
    }

    /**
     * The game is finished.
     *
     * @param player the winner of the game
     */
    public void finish(Player player){
        gameActiveStatus = false;
        genericGameView.playerWon(player, laddergameView.getMainLayout());
    }

    /**
     * Starts the game.
     */
    public void start(){
        players.sort((a, b) -> a.getBirthDate().compareTo(b.getBirthDate()));
        activePlayer = players.get(0).getPlayerNumber();
    }

    /**
     * Returns the active game board
     * @return gameboard the active board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns game status
     * @return game status
     */
    public boolean getGameStatus() {
        return gameActiveStatus;
    }

    /**
     * Returns the active player
     * @return the index of the active player
     */
    public int getActivePlayer(){
        return activePlayer;
    }

    /**
     * Changes whose turn it is
     */
    public void nextPlayer() {
        if (activePlayer == playerAmount - 1) {
            activePlayer = 0;
        } else {
            activePlayer++;
        }
    }

    /**
     * Returns a list of the players in the game
     * @return the players in the game
     */
    public ArrayList<Player> getPlayers(){
        return this.players;
    }
}
