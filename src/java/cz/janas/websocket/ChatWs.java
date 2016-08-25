package cz.janas.websocket;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnError;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author janas
 */
@ApplicationScoped
@ServerEndpoint("/chatws")
public class ChatWs {
    
    private final SessionHandler sessionHandler = new SessionHandler();
    
    @OnOpen
    public void onOpen(Session session) {
        sessionHandler.addSession(session);
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
        sessionHandler.sendToAllClients(message);
    }
    
    @OnClose
    public void onClose(Session session) {
        sessionHandler.removeSession(session);
    }
    
    @OnError
    public void onError(Throwable error) {
        
    }
}
