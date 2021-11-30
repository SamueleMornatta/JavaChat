/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mornatta_samuele
 */
public class ThreadClient extends Thread{
    Socket client;
    PrintWriter out;
    BufferedReader in;
    String nickname;
    String otherNick;
    Condivisa cond;
    boolean alreadyNick;
    ChatFrame frame;
    public ThreadClient(Socket client, String nickname) throws IOException{
        this.client = client;
        this.nickname = nickname;
        otherNick = "";
        alreadyNick = false;
        cond = Condivisa.getInstance();
        out = new PrintWriter(client.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        frame = cond.getFrame();
        cond.addSocket(client);
    }
    
    @Override
    public void run() {
        while (true){
            try {
                String tmp = recieveMessage();
                String[] params = tmp.split(";");
                if (params[0].equals("y")){
                    if (alreadyNick == false){
                        otherNick = params[1];
                        sendMessage("y;");
                    }
                    frame.makePopUp("Connection enstablished with " + otherNick);
                    frame.connesso();
                } else if(params[0].equals("n")){
                    frame.makePopUp("Connection declined");
                    frame.disconesso();
                } else if(params[0].equals("c")){
                    sendMessage("n;");
                } else if(params[0].equals("m")){
                    Message mes = new Message(otherNick, params[1]);
                    cond.appendMessage(mes);
                } else if (params[0].equals("e")){
                    cond.clearMessages();
                    alreadyNick = false;
                    frame.disconesso();
                }
            } catch (IOException ex) {
                Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void sendMessage(String message){
        out.println(message);
    }
    public String recieveMessage() throws IOException{
        return in.readLine();
    }
}
