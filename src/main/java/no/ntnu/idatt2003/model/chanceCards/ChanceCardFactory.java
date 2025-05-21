package no.ntnu.idatt2003.model.chanceCards;

import java.util.ArrayList;

/**
 * The ChanceCardFactory class is responsible for creating a deck of chance cards. It contains a
 * static method to create and return an ArrayList of ChanceCard objects.
 */
public class ChanceCardFactory {

  /**
   * Creates a deck of chance cards.
   *
   * @return an ArrayList of ChanceCard objects
   */
  public static ArrayList<ChanceCard> createDeck() {
    ArrayList<ChanceCard> chanceCards = new ArrayList<>();
    chanceCards.add(new ChanceCardJail());
    chanceCards.add(new ChanceCardJail());
    chanceCards.add(new ChanceCardJail());
    chanceCards.add(new ChanceCardJail());

    chanceCards.add(new ChanceCardMoney(-500, "You ride an electric scooter into a river"));
    chanceCards.add(new ChanceCardMoney(-400, "You treat yourself to some Burger King"));
    chanceCards.add(new ChanceCardMoney(-200, "You use dark mode in your IDE"));
    chanceCards.add(new ChanceCardMoney(-300, "You buy a new computer to play minecraft"));
    chanceCards.add(
        new ChanceCardMoney(-500, "You are invited to a wedding and you need a new suit"));
    chanceCards.add(
        new ChanceCardMoney(-800, "You rent a lamborghini to look rich on social media"));
    chanceCards.add(new ChanceCardMoney(-1000, "Someone stole your monopoly game collection"));
    chanceCards.add(new ChanceCardMoney(-1500,
        "You send your money to a Nigerian prince, he promised you a lot of money in return"));

    chanceCards.add(new ChanceCardMoney(500,
        "You stole money from a baby, i hope you feel good about yourself"));
    chanceCards.add(new ChanceCardMoney(400, "You win on a scratch off ticket"));
    chanceCards.add(new ChanceCardMoney(400, "You commit tax fraud!"));
    chanceCards.add(
        new ChanceCardMoney(300, "You get money from the government for just being a cool person"));
    chanceCards.add(
        new ChanceCardMoney(800, "You accidentally create a famous rap song that goes viral"));
    chanceCards.add(
        new ChanceCardMoney(1000, "You sue red bull for not actually giving you wings"));
    chanceCards.add(new ChanceCardMoney(200, "You use light mode in your IDE"));
    chanceCards.add(new ChanceCardMoney(1500, "You help your grandmother set up her computer"));

    chanceCards.add(new ChanceCardMove(2, "You go to study at NTNU trondheim"));
    chanceCards.add(new ChanceCardMove(9, "You visit Kjetil Rekdal, move to Ålesund"));
    chanceCards.add(new ChanceCardMove(12, "You go to see the northern lights, go to Tromsø"));
    chanceCards.add(new ChanceCardMove(19, "You go to Kristiansund for no apparent reason"));
    chanceCards.add(new ChanceCardMove(26, "You go to ride a reindeer, move to Kautokeino"));
    chanceCards.add(new ChanceCardMove(39, "You go on vaction to Norway's Dubai, go to Molde"));
    chanceCards.add(
        new ChanceCardMove(37, "You go to visit Julius the chimpanzee, go to Kristiansand"));

    return chanceCards;
  }
}
