package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Player;

public class BackToStartTile extends Tile implements TileAction{

    public BackToStartTile(int location) {
        super(location);
    }

    @Override
    public void action(Player player) {
    }
    
}
