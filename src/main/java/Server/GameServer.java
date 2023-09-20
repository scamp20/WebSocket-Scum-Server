package Server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameServer extends WebSocketServer {

    // map of websocket connections to games (for onMessage)
    Map<WebSocket, String> gameConns;

    // map of game IDs to games (for onOpen)
    Map<String, Game> games;

    public GameServer() {
        super();
        gameConns = new HashMap<>();
        games = new HashMap<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
        String message = clientHandshake.getResourceDescriptor(); // get the message sent by the client
        String[] messageParts = message.split("/");

        if (messageParts[1].equals("search")) {
            // return list of games
            List<String> gameList = new ArrayList<>();
            for (Map.Entry<String, Game> entry : games.entrySet()) {
                if (!entry.getValue().hasStarted() && !entry.getValue().isFull()) {
                    gameList.add(entry.getKey());
                }
            }
            JSONArray gamesArray = new JSONArray(gameList);
            conn.send(gamesArray.toString());
            conn.close();
        } else if (messageParts[1].equals("join")) {
            String gameID = messageParts[2];
            if (games.containsKey(gameID)) {
                Game game = games.get(gameID);
                if (game.addPlayer(conn)) {
                    gameConns.put(conn, gameID);
                } else {
                    conn.send("This game is either full or already started.");
                    conn.close();
                }
            } else {
                conn.send("That game could not be found.");
                conn.close();
            }
        } else if (messageParts[1].equals("make")) {
            String gameID = messageParts[2];
            if (games.containsKey(gameID)) {
                conn.send("Error: game with ID \"" + gameID + "\" already exists.");
                conn.close();
                return;
            }

            // handle make game logic
            Integer maxPlayers = Integer.parseInt(messageParts[3]);
            Game game = new Game(maxPlayers);
            games.put(gameID, game);
            gameConns.put(conn, gameID);

            game.addPlayer(conn);
        } else {
            // handle invalid message
            conn.send("Invalid message");
            conn.close();
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        String gameID = gameConns.get(conn);
        Game game = games.get(gameID);
        if (remote) {
            //notify players in game that conn(player) left the game
        } else {
            //notify players in game that conn(player) was disconnected from the game by the server
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        if (message.equals("start")) {

        }

    }

    @Override
    public void onError(WebSocket conn, Exception e) {

    }

    @Override
    public void onStart() {
        System.out.println("Server Started");
    }
}