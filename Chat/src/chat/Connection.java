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
import javax.swing.JFrame;

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
    ThreadChat ct;
    ChatFrame frame;
    DatagramPacket replyPacket;
    
    public Connection(){
        otherNick = "unkown";
        connection = false;
        tosend = false;
        mes = "";
        cond = Condivisa.getInstance();
        ct = new ThreadChat();
        frame = cond.getFrame();
    }
    public Connection(DatagramSocket server, String ip, String nickname) {
        this.server = server;
        this.ip = ip;
        this.nickname = nickname;
        otherNick = "unkown";
        connection = false;
        tosend = false;
        mes = "";
        cond = Condivisa.getInstance();
        ct = new ThreadChat();
        frame = cond.getFrame();
    }

    @Override
    public void run() {
        sendMessage(server, "c;"+nickname+";", ip);
        try {
            server.setSoTimeout(10000);
        } catch (SocketException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        String message = recieveMessage(server);
        String params[] = message.split(";");
        if (params[0].equals("y")){
            otherNick = params[1];
            connection = true;
            frame.makePopUp("Connection enstablished with " + otherNick);
            frame.connesso();
            if (!frame.ischaton){
                ct.start();
                frame.ChatActivated();
            }
            sendMessage(server, "y;", ip);
            try {
                server.setSoTimeout(500);
            } catch (SocketException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
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
            frame.iscon = false;
        } else if(params[0].equals("n")){
            frame.makePopUp("Connection declined");
            frame.iscon = false;
        } else if(params[0].equals("c")){
            sendMessage(server, "n;", replyPacket.getAddress().toString());
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

    public boolean isConnection() {
        return connection;
    }

    public DatagramSocket getServer() {
        return server;
    }

    public String getIp() {
        return ip;
    }

    public String getNickname() {
        return nickname;
    }

    public String getOtherNick() {
        return otherNick;
    }

    public boolean isTosend() {
        return tosend;
    }

    public String getMes() {
        return mes;
    }

    public Condivisa getCond() {
        return cond;
    }

    public ThreadChat getCt() {
        return ct;
    }

    public void setServer(DatagramSocket server) {
        this.server = server;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setOtherNick(String otherNick) {
        this.otherNick = otherNick;
    }

    public void setConnection(boolean connection) {
        this.connection = connection;
    }

    public void setTosend(boolean tosend) {
        this.tosend = tosend;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public void setCond(Condivisa cond) {
        this.cond = cond;
    }

    public void setCt(ThreadChat ct) {
        this.ct = ct;
    }
}
