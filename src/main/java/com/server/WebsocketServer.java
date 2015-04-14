package com.server;

import java.io.BufferedReader;
import java.io.InputStreamReader; 
import org.glassfish.tyrus.server.Server;
import com.server.Websocket;
import com.server.Testsocket;

 
public class WebsocketServer {
	
	public static void main(String[] args) {
    	runServer();
    }
 
    public static void runServer() {
        Server server = new Server("localhost", 8030, "/photohangout", Websocket.class);
        try {
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Please press a key to stop the server.");
            reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }
}