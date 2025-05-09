package no.ntnu.idatt2003.model;

public class Property {
    private String name;
    private int location;
    private int streetNumber;
    private int price;
    private int baseRent;
    private int houseCost;
    private int propertyLevel;
    private Player owner = null;
    private String colorCode;
    private boolean isPawned;

    public Property (String name, int location, int streetNumber, int price, int baseRent){
        this.name = name;
        this.location = location;
        this.streetNumber = streetNumber;
        this.price = price;
        this.baseRent = baseRent;
        //TODO House cost based on street number
        this.propertyLevel = 0;
        this.isPawned = false;

        switch(streetNumber) {
            case 1:
                this.colorCode = "#664825";
                break;
            case 2:
                this.colorCode = "#42ddeb";
                break;
            case 3:
                this.colorCode = "#d108b0";
                break;
            case 4:
                this.colorCode = "#eb9e05";
                break;
            case 5:
                this.colorCode = "#e3df09";
                break;
            case 6:
                this.colorCode = "#e01d1d";
                break;
            case 7:
                this.colorCode = "#459608";
                break;
            case 8:
                this.colorCode = "#142375";
                break;
        }
    }

    /**
     * Sets the owner of the property in case of a trade.
     * @param owner the new owner of the property
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Buys the property for the player if they have enough money.
     * @param player the player who is trying to buy the property
     */
    public void buyProperty(Player player){
        if (player.getBalance() > this.price) {
            player.addProperty(this);
            player.setBalance(player.getBalance() - this.price);
            this.owner = player;
        }
    }

    /**
     * Buys a house for the property if the player has enough money and the property level is less than 5.
     */
    public void buyHouse(){
        if(this.propertyLevel < 5 && this.owner.getBalance() > this.houseCost) {
            this.propertyLevel++;
        }
    }

    /**
     * Returns the name of the property.
     * @return name the name of the property
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the hex color code of the property.
     * @return colorCode the color code of the property
     */
    public String getColor() {
        return this.colorCode;
    }

    /**
     * Returns the location of the property on the board.
     * @return location the location of the property
     */
    public int getLocation() {
        return this.location;
    }

    /**
     * Returns the street number of the property.
     * @return streetNumber the street number of the property
     */
    public int getStreetNumber() {
        return this.streetNumber;
    }

    /**
     * Returns the price of the property.
     * @return price the price of the property
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Returns the base rent of the property.
     * @return baseRent the base rent of the property
     */
    public int getBaseRent() {
        return this.baseRent;
    }

    /**
     * Returns the current rent for the proprty including houses
     * @return rent the current rent of the property
     */
    public int getRent() {
        //TODO add rent calculation based on property level
        switch (this.propertyLevel) {
            case 1:
                return this.baseRent * 2;
            case 2:
                return this.baseRent * 3;
            case 3:
                return this.baseRent * 4;
            case 4:
                return this.baseRent * 5;
            default:
                return this.baseRent;
        }
    }

    /**
     * Returns the current property level of the property.
     * @return propertyLevel the current property level of the property
     */
    public int getHouseCost() {
        return this.houseCost;
    }

    /**
     * Returns the current owner of the property.
     * @return owner the current owner of the property
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Returns a boolean indicating if the property is pawned or not.
     * @return isPawned true if the property is pawned, false otherwise
     */
    public boolean isPawned() {
        return this.isPawned;
    }

    /**
     * Sets the property to pawned.
     */
    public void setPawned() {
        this.isPawned = true;
    }

    /**
     * Sets the property to not pawned, when repurchased.
     * This is used when the player repurchases the property after it has been pawned.
     */
    public void rePurchase() {
        this.isPawned = false;
    }
}
