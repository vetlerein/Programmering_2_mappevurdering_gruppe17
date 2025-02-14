package no.ntnu.idatt2003.tile;


public class SpecialTile extends Tile {
    private String tileType;


    public SpecialTile(int location, String color, String tileType) {
        super(location, color);
        this.tileType = tileType;
    }

    public String getType() {
        return tileType;
    }


    //Methods that are not implemented yet
    public void backToStart(){
        //Flytter brikken tilbake til start
    }
    public void finish(){
        //Den siste tilen som viser at man vinner
    }
    public void pause(){
        //En tile som pauser brikken i en runde
    }
    public void playerSwap(){
        //En tile som bytter plass på to spillere
    }
    public void setColor(){
        //En metode som setter fargen på en specialTile
    }

}
