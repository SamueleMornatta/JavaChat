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
    boolean chatalive;
    ArrayList<Message> msg;

    public ThreadChat() {
        cond = Condivisa.getInstance();
        chatalive = true;
        msg = new ArrayList<Message>();
    }

    @Override
    public void run() {
        while (chatalive){
            if (cond.checkNew()){
                msg = cond.getChatmessages();
                System.out.println("CHAT:");
                System.out.println("-------------");
                for (int i = 0; i < msg.size(); i++){
                    System.out.println(msg.get(i).getNickname() + ":");
                    System.out.println(msg.get(i).getMessaggio());
                }
                System.out.println("-------------");
            }
        }
    }

    public void setChatalive(boolean chatalive) {
        this.chatalive = chatalive;
    }
}
