package no.ntnu.idatt2003.model;

import java.util.ArrayList;

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
     * @param playerAmount the amount of players in the game
     * @param board the active gameboard
     * @param players the active players in the game
     */

    public Game(int playerAmount, ArrayList<Player> players, Board gameboard){
        gameActiveStatus = true;
        this.playerAmount = playerAmount;
        this.players = players;
        board = gameboard;
    }

    /**
     * The game is finished.
     *
     * @param player the winner of the game
     */
    public void finish(Player player){
        gameActiveStatus = false;
        //Masse fest og moro på skjermen
    }

    /**
     * Starts the game.
     */
    public void start(){
        players.sort((a, b) -> a.getBirthDate().compareTo(b.getBirthDate()));
        while(gameActiveStatus){
            for (Player player : players){
                player.move();
            }
        }
    }

    /**
     * Returns the active game board
     * @return gameboard the active board
     */
    public Board getBoard() {
        return board;
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }
}
