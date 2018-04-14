package com.example.websocket.websocket.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint( "/socket" )
@Component
public class Socket {

    private Session session;


    @OnOpen
    public void onOpen( Session session ) {
        try {
            this.session = session;
            session.getBasicRemote().sendText("你好");
        } catch ( IOException e ) {
            e.printStackTrace();
            try {
                session.close();
            } catch ( IOException e1 ) {

            }
        }
        System.out.println("onOpend");
    }


    @OnMessage
    public void onMessage( String message, Session session ) {
        try {
            session.getBasicRemote().sendText(message + ", 你好");
        } catch ( IOException e ) {
            e.printStackTrace();
            try {
                session.close();
            } catch ( IOException e1 ) {

            }
        }
        System.out.println("message: " + message);
    }


    @OnClose
    public void onClose( Session session ) {
        System.out.println("OnClose");
    }


    @OnError
    public void onError( Throwable e ) {
        System.out.println(e.getMessage());
    }
}
