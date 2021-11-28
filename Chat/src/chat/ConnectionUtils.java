/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samue
 */
public class ConnectionUtils {
    public ConnectionUtils(){};
    public static void sendMessage(DatagramSocket server, String message, String ip, String nickname){
        if (message.equals("exitChat")){
            message = "e;";
        } else if (!message.contains("c;") && !message.contains("y;") && !message.contains("n;")) {
            message = "m;" + message;
            Message tmp = new Message(nickname, message);
            Condivisa.getInstance().appendMessage(tmp);
        }
        byte[] mesBuff = message.getBytes();
        DatagramPacket messPacket = new DatagramPacket(mesBuff, mesBuff.length);
        try {
            messPacket.setAddress(InetAddress.getByName(ip));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        messPacket.setPort(2003);
        try {
            server.send(messPacket);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static String recieveMessage(DatagramSocket server){
        String message = "";
        byte[] replyBuff = new byte[1500];
        
        DatagramPacket replyPacket = new DatagramPacket(replyBuff, replyBuff.length);

        try {
            server.receive(replyPacket);
        } catch (IOException ex) {
        }
        byte[] receivedData = replyPacket.getData();
        message = new String(receivedData);
        String ip = replyPacket.getAddress().toString();
        Condivisa.getInstance().setRecentIP(ip.substring(1));
        return message;
    }
}
