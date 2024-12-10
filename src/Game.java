import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private ArrayList<Player> players;
    private ArrayList<Cards> tableCards;
    private ArrayList<Cards> deckCards;
    private Player currentPlayer;
    private int playerIndex;
    private boolean clockwise = true;

    public Game() {
        this.players = new ArrayList<>();
        this.tableCards = new ArrayList<>();
        this.deckCards = new ArrayList<>();
    }

    public void newGame() {
        // Create players
        for (int i = 0; i < 4; i++) {
            Player player = new Player("Player " + (i + 1));
            if (i <= 1) {
                player.setIsBot(true);
            }
            players.add(player);
        }

        currentPlayer = players.get(0);
        playerIndex = 0;

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
            for (int i = 13; i < 15; i++) {
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

    public void nextPlayer(String action) {
        if (action.equals("reverse")) {
            clockwise = !clockwise; // Richtung umkehren
        }
        if (action.equals("skip")) {
            playerIndex = getNextPlayerIndex(2); // Überspringt den nächsten Spieler
        } else {
            playerIndex = getNextPlayerIndex(1); // Geht zum nächsten Spieler
        }

        currentPlayer = players.get(playerIndex);
        if (currentPlayer.getIsBot()) {
            // Bot-Logik

            System.out.println("Bot: " + currentPlayer.getPlayerName());
            botMove(currentPlayer, tableCards.get(tableCards.size() - 1));
        }
        System.out.println("Aktueller Spieler: " + playerIndex);
    }

    private int getNextPlayerIndex(int steps) {
        if (clockwise) {
            return (playerIndex + steps) % 4; // Bewegung im Uhrzeigersinn
        } else {
            return (playerIndex - steps + 4) % 4; // Bewegung gegen den Uhrzeigersinn
        }
    }


    public int playCard(Player player, Cards card) {
        Cards topCard = tableCards.get(tableCards.size() - 1);

        if (card.getCardColour().equals(topCard.getCardColour()) || card.getCardValue() == topCard.getCardValue() || card.getCardColour().equals("wild") || card.getCardColour().equals(topCard.getWildColour())) {
            tableCards.add(card);
            player.removeCard(card);
            if (card.getCardValue() == 10) {
                player.addCard(deckCards.remove(0));
                player.addCard(deckCards.remove(0));
                nextPlayer("draw2");
            }
            if (player.getPlayerCards().isEmpty()) {
                return 2;
            }
            if (card.getCardValue() == 11) {
                nextPlayer("reverse");
                return 3;
            }
            if (card.getCardValue() == 12) {
                nextPlayer("skip");
                return 4;
            }
            if (card.getCardValue() == 13) {
                nextPlayer("nothing");
                return 5;
            }
            if (card.getCardValue() == 14) {
                nextPlayer("draw4");
                player.addCard(deckCards.remove(0));
                player.addCard(deckCards.remove(0));
                player.addCard(deckCards.remove(0));
                player.addCard(deckCards.remove(0));
                return 5;
            }
            nextPlayer("nothing");
            return 1;
        }
        return 0;
    }

    public boolean drawCard(Player player) {
        if (deckCards.size() > 0 && !checkAllCardPlayable(player, tableCards.get(tableCards.size() - 1))) {
            Cards card = deckCards.remove(0);
            player.addCard(card);
            return true;
        }
        return false;
    }

    public static boolean checkAllCardPlayable(Player player, Cards topCard) {
        for (Cards card : player.getPlayerCards()) {
            if (card.getCardColour().equals(topCard.getCardColour()) || card.getCardValue() == topCard.getCardValue() || card.getCardColour().equals("wild") || card.getCardColour().equals(topCard.getWildColour())) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkCardPlayable(Cards topCard, Cards card) {
            if (card.getCardColour().equals(topCard.getCardColour()) || card.getCardValue() == topCard.getCardValue() || card.getCardColour().equals("wild") || card.getCardColour().equals(topCard.getWildColour())) {
                return true;
            }
        return false;
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


    public void botMove(Player currentPlayer, Cards topCard) {
        // Bot logic
        ArrayList<Cards> playableCards = new ArrayList<>();
        ArrayList<Cards> playerCards = currentPlayer.getPlayerCards();
        for (Cards card : playerCards) {
            if (card.getCardColour().equals(topCard.getCardColour()) || card.getCardValue() == topCard.getCardValue() || card.getCardColour().equals("wild")) {
                playableCards.add(card);
            }
        }
        if (playableCards.isEmpty()) {
            // Draw card
            currentPlayer.addCard(deckCards.remove(0));
            botMove(currentPlayer, topCard);
        }
        Collections.shuffle(playableCards);
        Cards card = playableCards.get(0);
        playCard(currentPlayer, card);

    }

}
