package Server.Model;

import java.io.Serializable;
import java.util.List;

public class Move implements Serializable {
    /**
     * Amount of cards used for the move
     */
    private int quantity;
    /**
     * ID for player doing the move
     */
    private String username;
    /**
     * Order of the cards
     */
    private String order;

    private List<Card> cards;

    public Move() {}

    public Move(int quantity, String username, String order, List<Card> cards) {
        this.quantity = quantity;
        this.username = username;
        this.order = order;
        this.cards = cards;
    }


    public int getQuantity() {
        return quantity;
    }

    public String getUsername() {
        return username;
    }

    public String getOrder() {
        return order;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
