package no.ntnu.idatt2003.model.chanceCards;

import no.ntnu.idatt2003.model.Board;
import no.ntnu.idatt2003.model.BoardGameFactory;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.tile.PropertyTile;

public class ChanceCardMove implements ChanceCard {
    private final int newPosition;
    private final String description;

    public ChanceCardMove(int position, String description) {
        this.newPosition = position;
        this.description = description;
    }

    @Override
    public String toString() {
        Board board = BoardGameFactory.createMonopolyBoard();
        PropertyTile tile = (PropertyTile) board.getGameboard().get(newPosition);
        return description + ", move to " + tile.getProperty().getName() + ".";
    }

    @Override
    public void effect(Player player) {
        if (newPosition < player.getPosition()) {
            player.setPosition(0);
        } 
        player.setPosition(newPosition);
    }
}

