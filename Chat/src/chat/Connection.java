/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samue
 */
public class Connection extends Thread{
    ServerSocket server;
    Condivisa cond;
    ThreadChat ct;
    ChatFrame frame;
    String nickname;
    
    public Connection(){
        cond = Condivisa.getInstance();
        ct = new ThreadChat();
        frame = cond.getFrame();
    }
    public Connection(ServerSocket server, String nickname) {
        this.server = server;
        cond = Condivisa.getInstance();
        ct = new ThreadChat();
        frame = cond.getFrame();
        this.nickname = nickname;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket client = server.accept();
                ThreadClient tmp = new ThreadClient(client, nickname);
                tmp.start();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
