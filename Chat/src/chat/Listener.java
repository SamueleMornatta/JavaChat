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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samue
 */
public class Listener extends Thread{
    DatagramSocket server;
    String ip;
    String nickname;
    String otherNick;
    boolean connection;
    boolean tosend;
    String mes;
    Condivisa cond;
    ThreadChat ct;
    ChatFrame frame;
    DatagramPacket replyPacket;
    
    public Listener(DatagramSocket server, String nickname) {
        this.server = server;
        ip = "";
        this.nickname = nickname;
        otherNick = "unkown";
        connection = false;
        tosend = false;
        mes = "";
        cond = Condivisa.getInstance();
        ct = new ThreadChat();
        frame = cond.getFrame();
        try {
            server.setSoTimeout(500);
        } catch (SocketException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while (true){
            if (frame.iscon == false){
                String message = recieveMessage(server);
                if (!(message.equals(""))){
                    try {
                        server.setSoTimeout(10000);
                    } catch (SocketException ex) {
                        Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ip = replyPacket.getAddress().toString();
                    ip = ip.substring(1);
                    String params[] = message.split(";");
                    String response = "";
                    if (params[0].equals("c")){
                        otherNick = params[1];
                        do
                        {
                            response = frame.makePopUpInput(otherNick);
                            if (response.toUpperCase().equals("Y")){
                                sendMessage(server, "y;"+nickname, ip);
                                String check = recieveMessage(server);
                                String carg[] = check.split(";");
                                if (carg[0].equals("y")){
                                    try {
                                        server.setSoTimeout(500);
                                    } catch (SocketException ex) {
                                        Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    connection = true;
                                    frame.makePopUp("Connection enstablished with " + otherNick);
                                    frame.connesso();
                                    if (!frame.ischaton){
                                        ct.start();
                                        frame.ChatActivated();
                                    }
                                    while (connection){
                                        if (tosend){
                                            if (mes.equals("exitChat")){
                                                sendMessage(server, "e;", ip);
                                                connection = false;
                                            } else {
                                                sendMessage(server, "m;"+mes, ip);
                                                Message tmp = new Message(nickname, mes);
                                                cond.appendMessage(tmp);
                                            }
                                            tosend = false;
                                        } else {
                                            String reply = recieveMessage(server);
                                            if (!(reply.equals(""))){
                                                String args[] = reply.split(";");
                                                if (args[0].equals("m")){
                                                    Message tmp = new Message(otherNick, args[1]);
                                                    cond.appendMessage(tmp);
                                                } else if (args[0].equals("e")){
                                                    connection = false;
                                                    cond.clearMessages();
                                                } else if (args[0].equals("c")){
                                                    sendMessage(server, "n;", replyPacket.getAddress().toString());
                                                }
                                            }
                                        }
                                    }
                                    frame.makePopUp("Disconnected");
                                    frame.disconesso();
                                } else if (carg[0].equals("n")){
                                    frame.makePopUp("Check Declined, Disconnected");
                                    frame.disconesso();
                                    connection = false;
                                } else if (carg[0].equals("c")){
                                    sendMessage(server, "n;", ip);
                                }
                            } else if (response.toUpperCase().equals("N")){
                                sendMessage(server, "n;", ip);
                            }
                        } while (!(response.toUpperCase().equals("Y")) && !(response.toUpperCase().equals("N")));
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
        
        replyPacket = new DatagramPacket(replyBuff, replyBuff.length);

        try {
            server.receive(replyPacket);
        } catch (IOException ex) {
            dontRecieve = true;
        }
        if (dontRecieve == false){
            byte[] receivedData = replyPacket.getData();
            message = new String(receivedData);
        }
        return message;
    }
    public void setMessaggio(String messaggio){
        mes = messaggio;
        tosend = true;
    }
}
