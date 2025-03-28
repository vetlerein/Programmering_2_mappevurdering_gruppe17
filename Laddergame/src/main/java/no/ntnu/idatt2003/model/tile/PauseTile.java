package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Player;

/**
 * This class creates a tile that pauses players
 */
public class PauseTile extends Tile implements TileAction{
    
    public PauseTile(int location, int[] coordinate) {
        super(location, coordinate);
    }

   @Override
    public void action(Player player) {
 
    }

}
