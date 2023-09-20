package Server.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {
    /**
     * Map of players' states
     * Key: username Value: PlayerState
     */
    private Map<String, PlayerState> players;
    /**
     * List of Moves so far in the round.
     */
    private List<Move> moves;
    /**
     * Last Move made in the round
     */
    private Move lastMove;
    /**
     * List of playerId for the order/hierarchy
     */
    private List<String> usernames;
    /**
     * Int for current turn based on hierarchy (0 == king's turn)
     */
    private int currentTurn;
    /**
     * Int for the round number of the game
     */
    private int roundNumber;

    /**
     * List of finished players
     */
    private List<String> finishedPlayers;

    /**
     * If game is finished.
     */
    private boolean gameFinished;

    public GameState() {
        players = new HashMap<>();
        moves = new ArrayList<>();
        lastMove = null;
        usernames = new ArrayList<>();
        currentTurn = 0;
        roundNumber = 1;
        finishedPlayers = new ArrayList<>();
    }

    public GameState(Map<String, PlayerState> players, List<Move> moves,
                     Move lastMove, List<String> usernames, int currentTurn,
                     int roundNumber) {
        this.players = players;
        this.moves = moves;
        this.lastMove = lastMove;
        this.usernames = usernames;
        this.currentTurn = currentTurn;
        this.roundNumber = roundNumber;
        finishedPlayers = new ArrayList<>();
        gameFinished = false;
    }

    public Map<String, PlayerState> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, PlayerState> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public List<String> getFinishedPlayers() {
        return finishedPlayers;
    }

    public void setFinishedPlayers(List<String> finishedPlayers) {
        this.finishedPlayers = finishedPlayers;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }
}
