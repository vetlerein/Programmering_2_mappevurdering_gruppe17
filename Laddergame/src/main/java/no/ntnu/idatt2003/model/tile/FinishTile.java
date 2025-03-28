package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Player;

/**
 *  The tile class that makes the finish tile.
 */
public class FinishTile extends Tile implements TileAction{
    
    public FinishTile(int location, int[] coordinate) {
        super(location, coordinate);
    }

   @Override
    public void action(Player player) {
 
    }
}
