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
                    Connection con = new Connection(server,"93.66.23.99","Eternal");
                    con.start();
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }
    
}
