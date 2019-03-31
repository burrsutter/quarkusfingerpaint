package org.acme.websocket;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.jboss.logging.Logger;
import javax.websocket.server.PathParam;

import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

// import org.eclipse.microprofile.health.Health;

// @Health
@ServerEndpoint("/paint/{id}")
@ApplicationScoped
public class WebSocket {

    private static final Logger LOG = Logger.getLogger(WebSocket.class);

    private int sessioncounter;

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        sessioncounter++;
        LOG.info("onOpen: " + id);        
        sessions.put(id,session);        
    }

    @OnClose
    public void onClose(Session session, @PathParam("id") String id) {
        LOG.info("onClose: " + id);
        sessions.remove(id);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {        
        LOG.error("onError", throwable);        
    }

    @OnMessage
    public void onMessage(String message) {  
        // message definition contained in index.html
        JsonObject jsonObject = jsonFromString(message);        
        LOG.info("message FROM " + jsonObject.getString("id"));
        // anybody who lands on /dashboard.html is a dashboard
        sendToDashboards(message);
    }

    /*
        dashboard.html adds the "DASHBOARD:" prefix
        DASHBOARD:cd290852-7249-4874-8f68-a50c2e1b8611

        non-dashboards, index.html, just have the UUID
        47350b60-4ade-49e9-8764-5aa34df43f08
    */
    private void sendToDashboards(String message) {        
        sessions.keySet().forEach(key -> {
            if (key.startsWith("DASHBOARD:")) {
                LOG.info("message TO " + key);
                Session s = sessions.get(key);
                s.getAsyncRemote().sendObject(message, result ->  {
                    if (result.getException() != null) {
                        System.out.println("Unable to send message: " + result.getException());
                    }
                });    
            }
        });
    }

    private static JsonObject jsonFromString(String jsonObjectStr) {

        JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
    
        return object;
    }

}