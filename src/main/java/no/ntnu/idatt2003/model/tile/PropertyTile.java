package no.ntnu.idatt2003.model.tile;

import no.ntnu.idatt2003.model.Game;
import no.ntnu.idatt2003.model.Player;
import no.ntnu.idatt2003.model.Property;

public class PropertyTile extends Tile{
    private Property property;

    /**
     * Constructor for the PropertyTile class.
     * @param location the location of the tile
     * @param property the property associated with the tile
     */
    public PropertyTile(int location, Property property) {
        super(location);
        this.property = property;
    }

    /**
     * Gives the player the option to buy the property if they land on it.
     * @param player the player who landed on the tile
     * @param game the game instance
     */
    @Override
    public void action(Player player, Game game) {
        //TODO check if property is pawned 
        if (this.property.getOwner() != null && this.property.getOwner() != player) {
            player.addPlayerBalance(-1*this.property.getRent());
            this.property.getOwner().addPlayerBalance(this.property.getRent());
        }
    }

    /**
     * @return the property associated with the tile
     */
    public Property getProperty() {
        return property;
    }
}
