public class Cards {
    private String cardColour;
    private int cardValue;
    private String cardImage;


    public Cards(String cardColour, int cardValue) {
        this.cardColour = cardColour;
        this.cardValue = cardValue;
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

}
