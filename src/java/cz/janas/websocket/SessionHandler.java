package cz.janas.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

/**
 * Handler for connected clients.
 * @author janas
 */
@ApplicationScoped
public class SessionHandler {
    
    // List of connected clients
    private final List<Session> sessions = Collections
            .synchronizedList(new LinkedList<Session>());

    /**
     * Add client to list of all clients.
     * @param session client (session) to add
     */
    public void addSession(Session session) {
        sessions.add(session);
        Logger.getLogger(ChatWs.class.getName()).log(Level.FINE,
                "Client was added", session);
    }
    
    /**
     * Removes client from clients list.
     * @param session client to remove
     */
    public void removeSession(Session session) {
        sessions.remove(session);
        Logger.getLogger(ChatWs.class.getName()).log(Level.FINE,
                "Client was removed", session);
    }

    /**
     * Broadcast message to all clients.
     * @param message message to broadcast
     */
    void sendToAllClients(String message) {
        sessions.forEach(s -> {
            try {
                ((Session) s).getBasicRemote().sendText(message);
            } catch (IOException ex) {
                Logger.getLogger(ChatWs.class.getName()).log(Level.SEVERE,
                        "Sending message to all clients failed.",
                        ex);
            }
        });
    }
}
