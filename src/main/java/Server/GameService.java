package Server;

import Server.Model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GameService {

    // Method to shuffle and distribute cards to players
    public void shuffleCard(GameState state) {
        List<Card> deck = new ArrayList<>();

        // Create a deck (list) of card
        for (int i = 0; i < 4; i++) {
            String suit = "";
            switch (i) {
                case 0:
                    suit = "Spades";
                    break;
                case 1:
                    suit = "Clubs";
                    break;
                case 2:
                    suit = "Hearts";
                    break;
                case 3:
                    suit = "Diamonds";
                    break;
                default:
                    break;
            }

            for (int j = 0; j < 13; j++) {
                String order = "";

                switch (j) {
                    case 0:
                        order = "Two";
                        break;
                    case 1:
                        order = "Three";
                        break;
                    case 2:
                        order = "Four";
                        break;
                    case 3:
                        order = "Five";
                        break;
                    case 4:
                        order = "Six";
                        break;
                    case 5:
                        order = "Seven";
                        break;
                    case 6:
                        order = "Eight";
                        break;
                    case 7:
                        order = "Nine";
                        break;
                    case 8:
                        order = "Ten";
                        break;
                    case 9:
                        order = "Jack";
                        break;
                    case 10:
                        order = "Queen";
                        break;
                    case 11:
                        order = "King";
                        break;
                    case 12:
                        order = "Ace";
                        break;
                    default:
                        break;
                }
                deck.add(new Card(suit, order));
            }
        }

        // Shuffle the deck
        Collections.shuffle(deck);

        // Get all the player states and usernames
        List<String> usernames = state.getUsernames();
        Map<String, PlayerState> players = state.getPlayers();

        // Distribute the cards
        int index = 0;
        for (Card card : deck) {
            players.get(usernames.get(index)).getCards().add(card);
            players.get(usernames.get(index)).setCardsLeft(players.get(usernames.get(index)).getCardsLeft() + 1);
            if (index < usernames.size() - 1) {
                index++;
            } else {
                index = 0;
            }
        }
    }

    // Just for test, give same cards without shuffling
    public void distributeCards(GameState state) {
        List<Card> deck = new ArrayList<>();

        // Create a deck (list) of card
        for (int i = 0; i < 4; i++) {
            String suit = "";
            switch (i) {
                case 0:
                    suit = "Spades";
                    break;
                case 1:
                    suit = "Clubs";
                    break;
                case 2:
                    suit = "Hearts";
                    break;
                case 3:
                    suit = "Diamonds";
                    break;
                default:
                    break;
            }

            for (int j = 0; j < 1; j++) {
                String order = "";

                switch (j) {
                    case 0:
                        order = "Two";
                        break;
                    case 1:
                        order = "Three";
                        break;
                    case 2:
                        order = "Four";
                        break;
                    case 3:
                        order = "Five";
                        break;
                    case 4:
                        order = "Six";
                        break;
                    case 5:
                        order = "Seven";
                        break;
                    case 6:
                        order = "Eight";
                        break;
                    case 7:
                        order = "Nine";
                        break;
                    case 8:
                        order = "Ten";
                        break;
                    case 9:
                        order = "Jack";
                        break;
                    case 10:
                        order = "Queen";
                        break;
                    case 11:
                        order = "King";
                        break;
                    case 12:
                        order = "Ace";
                        break;
                    default:
                        break;
                }
                deck.add(new Card(suit, order));
            }
        }

        // Get all the player states and usernames
        List<String> usernames = state.getUsernames();
        Map<String, PlayerState> players = state.getPlayers();

        // Distribute the cards
        int index = 0;
        for (Card card : deck) {
            players.get(usernames.get(index)).getCards().add(card);
            players.get(usernames.get(index)).setCardsLeft(players.get(usernames.get(index)).getCardsLeft() + 1);
            if (index < usernames.size() - 1) {
                index++;
            } else {
                index = 0;
            }
        }
    }


    public void handleMove(GameState state, Move move) {
        int playersCount = state.getUsernames().size();

        if (move.getQuantity() > 0) {
            // Add to the list of moves and set last move
            state.getMoves().add(move);
            state.setLastMove(move);

            // Update card deck for the player
            PlayerState playerState =  state.getPlayers().get(move.getUsername());
            playerState.getCards().removeAll(move.getCards());
            playerState.setCardsLeft(playerState.getCards().size());

            // If player is finished, add the player to the finished list and set rank
            if(playerState.getCardsLeft() == 0) {
                state.getFinishedPlayers().add(playerState.getUsername());
                playerState.setFinishedRank(state.getFinishedPlayers().size());
            }
        } else {
            PlayerState playerState =  state.getPlayers().get(move.getUsername());
            playerState.setPlayed(false);
        }

        // Next Turn
        int turn = 0;
        boolean allPlayed = false;
        // Iterate through all players to see if they passed or finished
        for (int i = 1; i <= playersCount; i++) {
            turn = (state.getCurrentTurn() + i) % playersCount;
            boolean played = state.getPlayers()
                    .get(state.getUsernames().get(turn))
                    .isPlayed();

            boolean notFinished = state.getPlayers()
                    .get(state.getUsernames().get(turn))
                    .getFinishedRank() < 0;

            if (played && notFinished) {
                break;
            }

            if (i == playersCount) allPlayed = true;
        }


        if (state.getFinishedPlayers().size() == playersCount - 1) {
            // Do something that the round is finished
            state.setGameFinished(true);
            String loserId = "";
            for (String id : state.getUsernames()) {
                if (!state.getFinishedPlayers().contains(id)) {
                    loserId = id;
                    break;
                }
            }

            state.getFinishedPlayers().add(loserId);
            state.getPlayers().get(loserId).setCards(new ArrayList<>());
            state.getPlayers().get(loserId).setCardsLeft(0);
            state.getPlayers().get(loserId).setFinishedRank(state.getUsernames().size());

        } else if (allPlayed) {
            // Reset cards in the middle
            int lastPlayed = state.getUsernames().indexOf(state.getLastMove().getUsername());
            int nextTurn = lastPlayed;
            for (int i = 0; i < playersCount; i++) {
                nextTurn = (lastPlayed + i) % playersCount;
                if (state.getPlayers().get(state.getUsernames().get(nextTurn)).getFinishedRank() == -1) {
                    break;
                }
            }

            state.setCurrentTurn(nextTurn);

            for (String id : state.getUsernames()) {
                state.getPlayers().get(id).setPlayed(true);
            }

            state.setMoves(new ArrayList<>());
            state.setLastMove(null);
        } else {
            // Set turn count
            state.setCurrentTurn(turn);
        }


    }

    // Create New Game
    public GameState newGame(List<String> players) {
        GameState state = new GameState();

        int rank = 0;
        for (String username : players) {
            PlayerState playerState = new PlayerState(username, new ArrayList<>(), rank, -1, 0, true);
            state.getPlayers().put(username, playerState);
            state.getUsernames().add(username);
            rank++;
        }

        return state;
    }

    // Reset GameState for new Round
    public void newRound(GameState state) {
        state.setGameFinished(false);
        state.setUsernames(new ArrayList<>());

        for (int i = 0; i < state.getFinishedPlayers().size(); i++) {
            String username = state.getFinishedPlayers().get(i);
            state.getUsernames().add(username);
            state.getPlayers().get(username).setCurrentRank(i);
            state.getPlayers().get(username).setCards(new ArrayList<>());
            state.getPlayers().get(username).setFinishedRank(-1);
            state.getPlayers().get(username).setCardsLeft(0);
            state.getPlayers().get(username).setPlayed(true);
        }

        state.setLastMove(null);
        state.setMoves(new ArrayList<>());
        state.setCurrentTurn(0);
        state.setRoundNumber(state.getRoundNumber() + 1);
        state.setFinishedPlayers(new ArrayList<>());
        state.setGameFinished(false);
    }
}

