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
    public void onOpen(Session session) throws IOException {
        total++;
        String nick = session.getQueryString();
        sessions.put(session,nick);
        System.out.println(nick);

            JSONObject ob =
                    new JSONObject().put("sender","system").put("message",nick + " connected!").put("type","connected").put("connectedNick",nick);

        for(Session s : sessions.keySet()){
            s.getBasicRemote().sendText(String.valueOf(ob));
        }
        System.  out.println(total + "  opened");
    }

    // кнопка перерыва соединения
    @OnClose
    public void onClose(Session session) throws IOException {
        // удалить отключенное соединение
        total--;
        String nick =sessions.get(session);
        sessions.remove(session);
        JSONObject disconnected = new JSONObject()
                .put("sender","system")
                .put("message",nick + " disconnected!")
                .put("type","disconnected")
                .put("disconnectedNick",nick);
        System.out.println(total + "closed");
        for(Session ses : sessions.keySet()){
            if(!ses.getBasicRemote().equals(session.getBasicRemote())){
                ses.getBasicRemote().sendText(String.valueOf(disconnected));
            }
        }
    }

    // получить сообщение
    @OnMessage
    public void onMessage(Session session, String message) {
//        System.out.println(jsonMessage + "        json");
//                  System.out.println(message +  "             msg");
        try {
            System.out.println(message);
            JSONObject jsonMessage = new JSONObject(message);
            System.out.println(jsonMessage);
            System.out.println("и");
            System.out.println(message);
            System.out.println();
            System.out.println();

            for (Session sess : sessions.keySet()) {
                if (session.getBasicRemote().equals(sess.getBasicRemote())) {
                } else {
                    sess.getBasicRemote().sendText(message);
                }
            }
        } catch (IOException e) {
            System.out.println(message + "    ERRORED!!!");
            e.printStackTrace();
        }
//        if(jsonMessage.get("type").equals("hang-up")) {
//            String target = (String) jsonMessage.get("target");
//
//            for (Map.Entry<Session, String> entry : sessions.entrySet()) {
//                if (entry.getValue().equals(target)) {
//                    entry.getKey().getBasicRemote().sendText(String.valueOf(jsonMessage));
//                }
//            }
//        }
//
//        if(jsonMessage.get("type").equals("video-offer")) {
//            String target = (String) jsonMessage.get("target");
//
//            for (Map.Entry<Session, String> entry : sessions.entrySet()) {
//                if (entry.getValue().equals(target)) {
//                    entry.getKey().getBasicRemote().sendText(String.valueOf(jsonMessage));
//                }
//            }
//        }
//        if(jsonMessage.get("type").equals("video-answer")) {
//            String target = (String) jsonMessage.get("target");
//
//            for (Map.Entry<Session, String> entry : sessions.entrySet()) {
//                if (entry.getValue().equals(target)) {


//                    entry.getKey().getBasicRemote().sendText(String.valueOf(jsonMessage));
//                }
//            }
//        }
//        if(jsonMessage.get("type").equals("new-ice-candidate")) {
//            String target = (String) jsonMessage.get("target");
//
//            for (Map.Entry<Session, String> entry : sessions.entrySet()) {
//                if (entry.getValue().equals(target)) {
//                    entry.getKey().getBasicRemote().sendText(String.valueOf(jsonMessage));
//                }
//            }
//        }


//
//        for (Session sess : sessions.keySet()) {
//            sess.getBasicRemote().sendText(String.valueOf(jsonMessage));
//        }

    }


    public void broadcastMessage(String message){
        sessions.keySet().forEach(k-> {
            try {k.getBasicRemote().sendText(message);}
            catch (IOException e) {throw new RuntimeException(e);}
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
