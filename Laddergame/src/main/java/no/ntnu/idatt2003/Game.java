package no.ntnu.idatt2003;

import java.util.ArrayList;

import no.ntnu.idatt2003.tile.Tile;

/**
 * Controls the game.
 */
public class Game {
    public boolean gameActiveStatus;
    public int playerAmount;
    public int activePlayer;
    public ArrayList<Tile> gameboard;
    public ArrayList<Player> players;

    /**
     * Constructs a game with a given amount of players, board width, board height and players.
     *
     * @param playerAmount the amount of players in the game
     * @param boardWidth number of horizontal tiles
     * @param boardHeight number of vertical tiles 
     * @param players the active players in the game
     */

    public Game(int playerAmount,  int boardWidth, int boardHeight, ArrayList<Player> players){
        this.gameActiveStatus = true;
        this.playerAmount = playerAmount;
        this.players = players;
        this.gameboard = new ArrayList<>();

        //Les brett fra json
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
}
