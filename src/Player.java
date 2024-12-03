import java.util.ArrayList;

public class Player {
    private String playerName;
    private Boolean isBot;
    private ArrayList<Cards> playerCards;

    public Player(String playerName) {
        this.playerName = playerName;
        this.isBot = false;
        this.playerCards = new ArrayList<>();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Boolean getIsBot() {
        return isBot;
    }

    public void setIsBot(Boolean isBot) {
        this.isBot = isBot;
    }

    public ArrayList<Cards> getPlayerCards() {
        return playerCards;
    }

    public void addCard(Cards card) {
        playerCards.add(card);
    }

    public void removeCard(Cards card) {
        playerCards.remove(card);
    }

    public void removeCard(int index) {
        playerCards.remove(index);
    }

    public int getCardCount() {
        return playerCards.size();
    }

    public Cards getCard(int index) {
        return playerCards.get(index);
    }
}
