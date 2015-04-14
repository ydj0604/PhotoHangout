package com.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.inject.Singleton;
 
@Singleton @ServerEndpoint(value = "/websocket")
public class Testsocket {
	HashMap<String, HashSet<Session>> groupIdToSessionsMap = new HashMap<String,HashSet<Session>>();
    private Logger logger = Logger.getLogger(this.getClass().getName());
 
    @OnOpen
    public void onOpen(Session session, @PathParam("group-id") String groupId) {
        logger.info("Connected ... " + session.getId() + " from " + groupId);
        System.out.println("Connected " + session.getId() + " from " + groupId);
        System.out.println(this.toString());
        
        if(groupIdToSessionsMap.containsKey(groupId)) {
        	System.out.println("group exists");
        	HashSet<Session> groupSessionBelongs = groupIdToSessionsMap.get(groupId);
        	groupSessionBelongs.add(session);
        } else {
        	System.out.println("new group");
        	HashSet<Session> newGroup = new HashSet<Session>();
        	newGroup.add(session);
        	groupIdToSessionsMap.put(groupId, newGroup);
        }
        System.out.println(groupIdToSessionsMap.size() + "groups");
    }
 
    @OnMessage
    public String onMessage(String message, Session session, @PathParam("group-id") String groupId) {
    	logger.info(String.format("Message arrvied from %s of group %s", session.getId(), groupId));
        System.out.println(String.format("Message arrvied from %s of group %s", session.getId(), groupId));
        
        HashSet<Session> groupSessionBelongs = groupIdToSessionsMap.get(groupId);
        for(Session s : groupSessionBelongs) {
        	try {
				s.getBasicRemote().sendText(message);
			} catch (IOException e) {
				//error case
				return "FAIL";
			}
        }
        
        return "SUCCESS";
    }
 
    @OnClose
    public void onClose(Session session, CloseReason closeReason, @PathParam("group-id") String groupId) {
        HashSet<Session> groupSessionBelongs = groupIdToSessionsMap.get(groupId);
        if(groupSessionBelongs == null) {
        	//do nothing: should never happen
        } else if(groupSessionBelongs.size() == 1) { //this is the last session in the group
        	groupIdToSessionsMap.remove(groupId);
        	groupSessionBelongs.remove(session);
        	System.out.println("last session in the group");
        } else {
        	groupSessionBelongs.remove(session);
        	System.out.println(groupSessionBelongs.size() + " sessions left in the group");
        }
        
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
        System.out.println("Closed " + session.getId());
        System.out.println(groupIdToSessionsMap.size() + "groups");
    }
}

