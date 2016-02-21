/*
 * Copyright Pockethome 2016
 */
package com.pockethome.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;

@ManagedBean(name = "handler")
@SessionScoped
public class Handler {

    private String server = "localhost";
    private int port = 8025;
    private Session session;
    private String message;

    public Handler() {
    }

    public void sendMessage() {
        if (session != null & message != null) {
            this.session.getAsyncRemote().sendText(message);
        }
    }

    public void connect() throws URISyntaxException, DeploymentException, IOException {
        if (session == null || !session.isOpen()) {
            final ClientManager client = ClientManager.createClient();
            final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
            session = client.connectToServer(ClientEndPoint.class, new URI("ws", "", server, port, "/websocket/echo", "", ""));
        }
    }

    public void close() throws IOException {
        if (session != null && session.isOpen()) {
            session.close(new CloseReason(CloseReason.CloseCodes.GOING_AWAY, "Bye"));
        }

    }

    public int getPort() {
        return port;
    }

    public String getServer() {
        return server;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
