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
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samue
 */
public class Connection extends Thread{
    DatagramSocket server;
    String ip;
    String nickname;
    String otherNick;
    boolean connection;
    boolean tosend;
    String mes;
    Condivisa cond;

    public Connection(DatagramSocket server, String ip, String nickname) {
        this.server = server;
        this.ip = ip;
        this.nickname = nickname;
        otherNick = "unkown";
        connection = false;
        tosend = false;
        mes = "";
        cond = Condivisa.getInstance();
    }

    @Override
    public void run() {
        sendMessage(server, "c;"+nickname+";", ip);
        String message = recieveMessage(server);
        String text[] = message.split("|");
        String params[] = text[0].split(";");
        if (params[0].equals("y")){
            otherNick = params[1];
            connection = true;
            sendMessage(server, "y;", ip);
            try {
                server.setSoTimeout(500);
            } catch (SocketException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (connection){
                if (tosend){
                    if (mes == "exitChat"){
                        sendMessage(server, "e;", ip);
                    } else {
                        sendMessage(server, mes, ip);
                        Message tmp = new Message(mes, nickname);
                        cond.appendMessage(tmp);
                    }
                    tosend = false;
                } else {
                    String reply = recieveMessage(server);
                    if (!(reply.equals(""))){
                        String rp[] = reply.split("|");
                        String args[] = rp[0].split(";");
                        if (args[0].equals("m")){
                            Message tmp = new Message(args[1], otherNick);
                            cond.appendMessage(tmp);
                        } else if (args[0].equals("e")){
                            connection = false;
                            cond.clearMessages();
                        } else if (args[0].equals("c")){
                            sendMessage(server, "n;", rp[1]);
                        }
                    }
                }
            }
        }
    }
    
    public void sendMessage(DatagramSocket server, String message, String ip){
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
    public String recieveMessage(DatagramSocket server){
        String message = "";
        boolean dontRecieve = false;
        byte[] replyBuff = new byte[1500];
        
        DatagramPacket replyPacket = new DatagramPacket(replyBuff, replyBuff.length);

        try {
            server.receive(replyPacket);
        } catch (IOException ex) {
            dontRecieve = true;
        }
        if (dontRecieve == false){
            byte[] receivedData = replyPacket.getData();
            message = new String(receivedData);
            message += "|" + replyPacket.getAddress().toString();
        }
        return message;
    }
    public void setMessaggio(String messaggio){
        mes = messaggio;
        tosend = true;
    }
}
