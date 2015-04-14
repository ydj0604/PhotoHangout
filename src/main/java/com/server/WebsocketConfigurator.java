package com.server;

import com.server.Websocket;
import javax.websocket.server.*;
import javax.websocket.server.ServerEndpointConfig.Configurator;

public class WebsocketConfigurator extends ServerEndpointConfig.Configurator {

    public static final Websocket endPoint = new Websocket();

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return (T) endPoint; //for now, keep only a single end point; to scale, we need to be able to manage multiple end points (Websocket instances)
    }
}