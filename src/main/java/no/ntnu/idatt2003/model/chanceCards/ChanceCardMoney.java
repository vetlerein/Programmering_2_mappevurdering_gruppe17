package no.ntnu.idatt2003.model.chanceCards;

import no.ntnu.idatt2003.model.Player;

public class ChanceCardMoney implements ChanceCard {
    private final int amount;
    private final String description;

    public ChanceCardMoney(int amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    @Override
    public String toString() {
        if(amount > 0){
            return description + ", recive " + amount;
        } else {
            return description + ", you have to pay " + amount;
        }        
    }

    @Override
    public void effect(Player player) {
        player.addPlayerBalance(amount);
    }
}
