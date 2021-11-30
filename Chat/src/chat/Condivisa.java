/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author samue
 */
public class Condivisa {
    private static ArrayList<Message> chatmessages;
    private static ArrayList<Socket> sockets;
    private static int numSockets;
    private static boolean isnew;
    private static ChatFrame frame;
    private static Condivisa instance = null;
    private static String recentip;
    public Condivisa(ChatFrame frame) {
        this.frame = frame;
        chatmessages = new ArrayList<Message>();
        sockets = new ArrayList<Socket>();
        isnew = false;
        instance = this;
        recentip = "";
    }
    public static Condivisa getInstance() {
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

    public synchronized ChatFrame getFrame() {
        return frame;
    }
    public synchronized void setRecentIP(String ip){
        recentip = ip;
    }
    public synchronized String getRecentIP(){
        return recentip;
    }
    public synchronized void addSocket(Socket client){
        sockets.add(client);
        numSockets++;
    }
    public synchronized void removeSocket(int index){
        sockets.remove(index);
        numSockets--;
    }
}
