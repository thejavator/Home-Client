/*
 * Copyright Pockethome 2016
 */
package com.pockethome.client;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.websocket.*;

@ApplicationScoped
@ClientEndpoint
public class ClientEndPoint {

    private static final Logger LOGGER = Logger.getLogger(ClientEndPoint.class.getName());

    Session session = null;

    public ClientEndPoint() {
    }

    @OnOpen
    public void onOpen(Session session) {
        LOGGER.log(Level.INFO, "Client endpoint open");
        this.session = session;
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        LOGGER.log(Level.INFO, "Client endpoint close");
        this.session = null;
    }

    @OnError
    public void onError(Throwable t) {
        LOGGER.log(Level.SEVERE, "Client endpoint error ", t);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        LOGGER.log(Level.INFO, "message recu={0}", message);
    }

    public void sendMessage(String message) {
        if (session != null) {
            this.session.getAsyncRemote().sendText(message);
        }
    }
}
