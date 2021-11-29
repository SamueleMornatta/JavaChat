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
    String mes;
    Condivisa cond;
    ThreadChat ct;
    ChatFrame frame;
    boolean alreadyNick;
    
    public Connection(){
        otherNick = "unkown";
        connection = false;
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
        mes = "";
        cond = Condivisa.getInstance();
        ct = new ThreadChat();
        frame = cond.getFrame();
        alreadyNick = false;
    }

    @Override
    public void run() {
        while (true) {
            String message = ConnectionUtils.recieveMessage(server);
            String params[] = message.split(";");
            System.out.println(message);
            if (connection == true){
                if (params[0].equals("y")){
                    if (alreadyNick == false){
                        otherNick = params[1];
                        ConnectionUtils.sendMessage(server, "y;", ip, nickname);
                    }
                    frame.makePopUp("Connection enstablished with " + otherNick);
                    frame.connesso();
                    if (!frame.ischaton){
                        ct.start();
                        frame.ChatActivated();
                    }
                } else if(params[0].equals("n")){
                    frame.makePopUp("Connection declined");
                    connection = false;
                    frame.disconesso();
                } else if(params[0].equals("c")){
                    ConnectionUtils.sendMessage(server, "n;", ip, nickname);
                } else if(params[0].equals("m")){
                    Message tmp = new Message(otherNick, params[1]);
                    cond.appendMessage(tmp);
                } else if (params[0].equals("e")){
                    connection = false;
                    cond.clearMessages();
                    alreadyNick = false;
                    frame.disconesso();
                }
            } else {
                if (params[0].equals("c")){
                    ip = cond.getRecentIP();
                    otherNick = params[1];
                    alreadyNick = true;
                    String response = "";
                    do
                    {
                        response = frame.makePopUpInput(otherNick);
                        if (response.toUpperCase().equals("Y")){
                            ConnectionUtils.sendMessage(server, "y;"+nickname, ip, nickname);
                            frame.connesso();
                            connection = true;
                            if (!frame.ischaton){
                                ct.start();
                                frame.ChatActivated();
                            }
                        } else if (response.toUpperCase().equals("N")){
                            ConnectionUtils.sendMessage(server, "n;", ip, nickname);
                        }
                    } while (!(response.toUpperCase().equals("Y")) && !(response.toUpperCase().equals("N")));
                }
            }
        }
    }
    public void startConnection(){
        connection = true;
        frame.connesso();
        ConnectionUtils.sendMessage(server, "c;"+nickname+";", ip, nickname);
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
