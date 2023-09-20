package org.example;

import java.util.ArrayList;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
public class GameServer extends WebSocketServer {
    private ArrayList<Game> games;

    public GameServer(int port) {
        super();
        //super(port); ide complained about this
        games = new ArrayList<Game>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // Add new player to a game, or create a new game if none exist
        Game game = null;
        for (Game g : games) {
            if (g.getNumberOfPlayers() < g.getMaxPlayers()) {
                game = g;
                break;
            }
        }
        if (game == null) {
            game = new Game();
            games.add(game);
        }
        game.addPlayer(conn);
        conn.send("You have joined a game with " + game.getNumberOfPlayers() + " players.");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        // Remove player from game
        for (Game g : games) {
            if (g.hasPlayer(conn)) {
                g.removePlayer(conn);
                break;
            }
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // Handle messages from player
        for (Game g : games) {
            if (g.hasPlayer(conn)) {
                g.handleMessage(conn, message);
                break;
            }
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Server started on port " + getPort());
    }

    public static void main(String[] args) {
        int port = 8080; // Default port number
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            // Use default port number
        }
        GameServer server = new GameServer(port);
        server.start();
    }
}

class Game {
    private ArrayList<WebSocket> players;
    private int maxPlayers;
    private ArrayList<String> gameState;

    public Game() {
        players = new ArrayList<WebSocket>();
        maxPlayers = 5;
        gameState = new ArrayList<String>();
    }

    public void addPlayer(WebSocket player) {
        players.add(player);
        for (WebSocket p : players) {
            p.send("Player " + players.size() + " has joined the game.");
        }
    }

    public void removePlayer(WebSocket player) {
        players.remove(player);
        for (WebSocket p : players) {
            p.send("A player has left the game.");
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
        for (WebSocket p : players) {
            p.send(message);
        }
    }
}