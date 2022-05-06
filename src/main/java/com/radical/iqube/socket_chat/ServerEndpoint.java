package com.radical.iqube.socket_chat;

import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import javax.websocket.*;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


@javax.websocket.server.ServerEndpoint(value="/chat")
public class ServerEndpoint extends Socket {

private static volatile int total = 0;
    // Запись сеанса каждого соединения, чтобы отправить сообщение
    private static Map<Session,String> sessions = new HashMap<>();

    @OnOpen
    public void onOpen(Session session){
        total++;
        sessions.put(session,session.getQueryString());

            JSONObject ob = new JSONObject().put("sender","system").put("message",sessions.get(session) + " connected!");
//
//        msg.add(session.`;
//
//    broadcastMessage("disc");
        System.  out.println(total + "  opened");
    }

    // кнопка перерыва соединения
    @OnClose
    public void onClose(Session session){
        // удалить отключенное соединение
        total--;
        String disconnectableUser =sessions.get(session);
        sessions.remove(session);
        System.out.println(total + "closed");
    }

    // получить сообщение
    @OnMessage
    public void onMessage(Session session,String message) throws IOException {
//        System.out.println(jsonMessage + "        json");
//        System.out.println(message +  "             msg");
        System.out.println(message);
                if( message.contains("target")) {
                    JSONObject jsonMessage = new JSONObject(message);

                    System.out.println(jsonMessage.get("sdp"));
                    System.out.println(jsonMessage.get("target"));

                }
//            jsonMessage.get("target");
//        }

        for(Session sess : sessions.keySet()){
            if(session.getBasicRemote().equals(sess.getBasicRemote())){

            }else{
                sess.getBasicRemote().sendText(message);
            }
        }
            }

    public void broadcastMessage(String message){

        sessions.keySet().forEach(k-> {
            try {
                k.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    @OnError
    public void onError(Session session, Throwable e){
        try {
            if(session.isOpen()) session.close();
        } catch (IOException ex) {
            System.out.println("Exception handling: " + ex.getMessage());
            ex.printStackTrace();
        }finally {
            e.printStackTrace();
            System.out.println("Session error handled. (likely unexpected EOF) resulting in closing User Session.");
        }
    }

}
