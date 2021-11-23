/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;

/**
 *
 * @author samue
 */
public class Chat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException {
        DatagramSocket server = new DatagramSocket(2003);
        Condivisa cond = Condivisa.getInstance();
        int choice = -1;
        String message = "";
        Scanner in = new Scanner(System.in);
        while (choice != 2) {
            System.out.println("-----------");
            System.out.println("1) Richiedi comunicazione");
            System.out.println("2) Esci");
            System.out.println("-----------");
            System.out.println("FAI UNA SCELTA:");
            choice = in.nextInt();
            switch(choice) {
                case 1:
                    Scanner sc1 = new Scanner(System.in);
                    System.out.println("Inserire IP host con cui connettersi:");
                    String ip = sc1.nextLine();
                    Connection con = new Connection(server,ip,"Eternal");
                    con.start();
                    System.out.println("Richiesta inviata");
                    System.out.println("Aspettare conferma connessione");
                    String mess = "";
                    while (!(mess.equals("exitChat"))){
                        Scanner txt = new Scanner(System.in);
                        mess = txt.nextLine();
                        if (con.isConnection()){
                            con.setMessaggio(mess);
                        }
                        else {
                            System.out.println("Connessione non ancora stabilita");
                        }
                    }
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }
    
}
