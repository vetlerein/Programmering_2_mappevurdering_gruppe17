package no.ntnu.idatt2003;

import java.util.ArrayList;

public class Game {
    public boolean gameActiveStatus;
    public int playerAmount;
    public int activePlayer;
    public ArrayList<Tile> gameboard;
    public ArrayList<Player> players;

    public Game(int playerAmount,  int boardWidth, int boardHeight, ArrayList<Player> players){
        this.gameActiveStatus = true;
        this.playerAmount = playerAmount;
        this.players = players;
        this.gameboard = new ArrayList<Tile>();
        for(int i = 0; i < boardWidth*boardHeight; i++){
            gameboard.add(new Tile());
        }
    }

    public void finish(Player player){
        gameActiveStatus = false;
        //Masse fest og moro på skjermen
    }

    public void start(){
        players.sort((a, b) -> a.getBirthDate().compareTo(b.getBirthDate()));
        while(gameActiveStatus){
            for (Player player : players){
                player.move();
            }
        }
    }
}
