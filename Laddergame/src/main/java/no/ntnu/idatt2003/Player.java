package no.ntnu.idatt2003;
import java.util.Date;

public class Player {
    public int position;
    public String playerName;
    public int playerNumber;
    private final Date birthDate;
    private final Dice playerDice = new Dice();
    public boolean playerActive;

    public Player(String playerName, int playerNumber, Date birthDate) {
        this.position = 0;
        this.playerName = playerName;
        this.playerNumber = playerNumber;
        this.birthDate = birthDate;
    }

    public void setPlayerActive() {
        this.playerActive = true;
    }

    public boolean getPlayerActive() {
        return this.playerActive;
    }

    public int getPosition() {
        return this.position;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public Date getBirthDate(){
        return this.birthDate;
    }

    public int getPlayerNumber() {
        return this.playerNumber;
    }

    public void move(){
        this.position += playerDice.rollDice();
        //Flytt spiller fysisk
    }

    public void playerSwapped(int newPosition){
        this.position = newPosition;
    }
}
