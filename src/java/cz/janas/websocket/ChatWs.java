package cz.janas.websocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnError;
import javax.enterprise.context.ApplicationScoped;

/**
 * WebSocket server for chatting.
 * @author janas
 */
@ApplicationScoped
@ServerEndpoint("/chatws")
public class ChatWs {
    
    // provides information about connected clients
    private static final SessionHandler sessionHandler = new SessionHandler();
    
    /**
     * Adds client to connected clients list.
     * @param session connected client
     */
    @OnOpen
    public void onOpen(Session session) {
        sessionHandler.addSession(session);
    }
    
    /**
     * 
     * @param message
     * @param session 
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        sessionHandler.sendToAllClients(message);
    }
    
    /**
     * Removes client from connected clients list.
     * @param session disconnected client
     */
    @OnClose
    public void onClose(Session session) {
        sessionHandler.removeSession(session);
    }
    
    /**
     * Logs communication error.
     * @param error 
     */
    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(ChatWs.class.getName()).log(Level.SEVERE,
                "Communication error.", error.getMessage());
    }
}
