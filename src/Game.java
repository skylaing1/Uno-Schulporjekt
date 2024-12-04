import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private ArrayList<Player> players;
    private ArrayList<Cards> tableCards;
    private ArrayList<Cards> deckCards;

    public Game() {
        this.players = new ArrayList<>();
        this.tableCards = new ArrayList<>();
        this.deckCards = new ArrayList<>();
    }

    public void newGame() {
        // Create players
        for (int i = 0; i < 4; i++) {
            Player player = new Player("Player " + (i + 1));
            players.add(player);
        }

        // Create deck of cards
        String[] colours = {"red", "blue", "green", "yellow"};
        for (String colour : colours) {
            for (int i = 1 ; i < 13; i++) {
                Cards card = new Cards(colour, i);
                Cards card2 = new Cards( colour, i);
                deckCards.add(card);
                deckCards.add(card2);
            }
            Cards card = new Cards(colour, 0);
            deckCards.add(card);
        }
        for (int x = 0; x < 2; x++) {
            for (int i = 1; i < 3; i++) {
                Cards card = new Cards("wild", i);
                Cards card2 = new Cards("wild", i);
                deckCards.add(card);
                deckCards.add(card2);
            }
        }
        Collections.shuffle(deckCards);

        // Deal cards to players
        for (int i = 0; i < 15; i++) {
             for (Player player : players) {
                Cards card = deckCards.remove(0);
                player.addCard(card);
            }
        }
        // Place top card on table
        Cards topCard = deckCards.remove(0);
        tableCards.add(topCard);
    }

    public void playCard(Player player, Cards card) {
        tableCards.add(card);
        player.removeCard(card);
    }

    public void drawCard(Player player) {
        Cards card = deckCards.remove(0);
        player.addCard(card);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Cards> getTableCards() {
        return tableCards;
    }

    public ArrayList<Cards> getDeckCards() {
        return deckCards;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setTableCards(ArrayList<Cards> tableCards) {
        this.tableCards = tableCards;
    }

    public void setDeckCards(ArrayList<Cards> deckCards) {
        this.deckCards = deckCards;
    }



}
