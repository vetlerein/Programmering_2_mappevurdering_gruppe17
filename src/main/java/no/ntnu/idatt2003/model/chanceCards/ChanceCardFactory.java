package no.ntnu.idatt2003.model.chanceCards;

import java.util.ArrayList;

public class ChanceCardFactory {
    public static ArrayList<ChanceCard> createDeck() {
        ArrayList<ChanceCard> chanceCards = new ArrayList<>();
        //TODO: create a deck of chance cards
        chanceCards.add(new ChanceCardMoney(-200, "You ride an electric scooter into a river"));
        return chanceCards;
    }
}
