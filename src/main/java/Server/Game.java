package Server;

import Server.Model.*;

import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.List;

class Game {
    private ArrayList<WebSocket> players;
    private int maxPlayers;
    private ArrayList<String> gameState;
    private Boolean started = false;
    private List<String> playersList;
    private GameState state;
    private GameService service;

    public Game(Integer maxPlayers) {
        players = new ArrayList<WebSocket>();
        this.maxPlayers = maxPlayers;
        gameState = new ArrayList<String>();
        state = null;
        service = new GameService();
        playersList = new ArrayList<>();
    }

    public void startGame() {
        started = true;
        // Get List of Players


        // Initialize Game
        state = service.newGame(playersList);
        //shuffle cards
        service.shuffleCard(state);

        // Send message to client to start the game
    }

    public Boolean hasStarted() {
        return started;
    }

    public Boolean isFull() {
        return players.size() == maxPlayers;
    }

    public Boolean addPlayer(WebSocket player) {
        if (players.size() == maxPlayers || started) {
            return false;
        }
        players.add(player);
        for (WebSocket p : players) {
            p.send("Player " + players.size() + " has joined the game.");
            // TODO Add to players list;

        }
        return true;
    }

    public void removePlayer(WebSocket player) {
        players.remove(player);
        for (WebSocket p : players) {
            p.send("A player has left the game.");
            // TODO Remove player from Players List

        }
    }

    public boolean hasPlayer(WebSocket player) {
        return players.contains(player);
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void handleMessage(WebSocket player, String message) {
        // Handle message from player and update game state
        gameState.add(message);
        // TODO handle message and convert to move object
        Move move = new Move();

        service.handleMove(state, move);
        // TODO Serialize game state class and send it to players
        for (WebSocket p : players) {
            p.send(message);
        }
    }
}