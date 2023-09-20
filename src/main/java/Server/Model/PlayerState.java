package Server.Model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PlayerState implements Serializable{
    /**
     * User of the state
     */
    private String username;
    /**
     * Cards the player has
     */
    private List<Card> cards;
    /**
     * Current rank of the player in the hierarchy of the game
     */
    private int currentRank;
    /**
     * New rank when player finished the game
     * If player is still playing, the value is -1
     */
    private int finishedRank;
    /**
     * The amounts of cards the user still has
     */
    private int cardsLeft;
    /**
     * If player played or passed
     */
    private boolean played;

    public PlayerState() {
    }

    public PlayerState(String username, List<Card> cards, int currentRank, int finishedRank, int cardsLeft, boolean played) {
        this.username = username;
        this.cards = cards;
        this.currentRank = currentRank;
        this.finishedRank = finishedRank;
        this.cardsLeft = cardsLeft;
        this.played = played;
    }

    public String getUsername() {
        return username;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getCurrentRank() {
        return currentRank;
    }

    public int getFinishedRank() {
        return finishedRank;
    }

    public int getCardsLeft() {
        return cardsLeft;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void setCurrentRank(int currentRank) {
        this.currentRank = currentRank;
    }

    public void setFinishedRank(int finishedRank) {
        this.finishedRank = finishedRank;
    }

    public void setCardsLeft(int cardsLeft) {
        this.cardsLeft = cardsLeft;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards);
    }

}
