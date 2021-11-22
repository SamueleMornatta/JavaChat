/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

/**
 *
 * @author samue
 */
public class Message {
    String nickname;
    String messaggio;
    public Message(String nickname, String messaggio) {
        this.nickname = nickname;
        this.messaggio = messaggio;
    }
    public String getNickname() {
        return nickname;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }
}
