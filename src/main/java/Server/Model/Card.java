package Server.Model;

import java.io.Serializable;
import java.util.Objects;

public class Card implements Serializable {

    /**
     * Suit of the card: (clubs, spades, hearts, diamonds)
     */
    private String suit;

    /**
     * Order of the card: (1 - 13 (King))
     */
    private String order;

    public Card() {
    }

    public Card(String suit, String order) {
        this.suit = suit;
        this.order = order;
    }

    public String getSuit() {
        return suit;
    }

    public String getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return suit.equals(card.getSuit()) && Objects.equals(order, card.getOrder());
    }

}
