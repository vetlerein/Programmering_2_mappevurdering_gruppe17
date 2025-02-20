package no.ntnu.idatt2003.tile;

import no.ntnu.idatt2003.Player;

public class BackToStartTile extends Tile implements TileAction{

    public BackToStartTile(int location) {
        super(location);
    }

    @Override
    public void action(Player player) {
    }
    
}
