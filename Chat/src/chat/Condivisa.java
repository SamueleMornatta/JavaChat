/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.util.ArrayList;

/**
 *
 * @author samue
 */
public class Condivisa {
    private static ArrayList<Message> chatmessages;
    private static boolean isnew;
    private static Condivisa instance = null; 
    private Condivisa() {}
    public static Condivisa getInstance() {
        if (instance == null) {
            instance = new Condivisa();
            chatmessages = new ArrayList<Message>();
            isnew = false;
        }
        return instance;
    }
    public synchronized void appendMessage(Message nuovo){
        chatmessages.add(nuovo);
        isnew = true;
    }
    public synchronized ArrayList<Message> getChatmessages() {
        return chatmessages;
    }
    public synchronized void clearMessages(){
        chatmessages.clear();
    }
    public synchronized boolean checkNew(){
        boolean tmp = isnew;
        isnew = false;
        return tmp;
    }
}
