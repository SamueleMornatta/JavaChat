/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.util.ArrayList;

/**
 *
 * @author mornatta_samuele
 */
public class ThreadChat extends Thread{
    Condivisa cond;
    ChatFrame frame;

    public ThreadChat() {
        cond = Condivisa.getInstance();
        frame = cond.getFrame();
    }

    @Override
    public void run() {
        while (true){
            if (cond.checkNew()){
                frame.setChat(cond.getChatmessages());
            }
        }
    }
}
