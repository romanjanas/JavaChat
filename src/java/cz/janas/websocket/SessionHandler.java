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
 *
 * @author janas
 */
@ApplicationScoped
public class SessionHandler {
    
    private final List<Session> sessions = Collections
            .synchronizedList(new LinkedList<Session>());

    public SessionHandler() {
    }
    
    public void addSession(Session session) {
        sessions.add(session);
    }
    
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    void sendToAllClients(String message) {
        sessions.forEach(s -> {
            try {
                ((Session) s).getBasicRemote().sendText(message);
            } catch (IOException ex) {
                Logger.getLogger(ChatWs.class.getName()).log(Level.SEVERE, null,
                        ex);
            }
        });
    }
}
