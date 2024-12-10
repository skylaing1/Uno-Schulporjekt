public class Cards {
    private String cardColour;
    private int cardValue;
    private String cardImage;
    private String wildColour = null;

    public Cards(String cardColour, int cardValue) {
        this.cardColour = cardColour;
        this.cardValue = cardValue;
        this.wildColour = null;
        this.cardImage = "src/unocards/" + cardColour + "-" + cardValue + ".png";
    }


    public String getCardColour() {
        return cardColour;
    }

    public int getCardValue() {
        return cardValue;
    }

    public void setCardColour(String cardColour) {
        this.cardColour = cardColour;
    }

    public void setCardValue(int cardValue) {
        this.cardValue = cardValue;
    }

    public String getCardImage() {
        return cardImage;
    }

    public String getWildColour() {
        return wildColour;
    }

    public void setWildColour(String wildColour) {
        this.wildColour = wildColour;
    }

}
