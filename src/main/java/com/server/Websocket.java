package com.server;

import java.io.IOException;
import java.util.logging.Logger;
 
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;
 
@ServerEndpoint(value = "/websocket")
public class Websocket {
 
    private Logger logger = Logger.getLogger(this.getClass().getName());
 
    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
        System.out.println("Opened " + session.getId());
    }
 
    @OnMessage
    public String onMessage(String message, Session session) {
        System.out.println("Received: " + message);
        return message;
    }
 
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
        System.out.println("Closed " + session.getId());
    }
}
