/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author mornatta_samuele
 */
public class ChatFrame extends javax.swing.JFrame {

    /**
     * Creates new form ChatFrame
     */
    DatagramSocket server;
    Condivisa cond;
    Connection con;
    boolean iscon;
    String nickname;
    Listener l;
    boolean ischaton;
    public ChatFrame() throws SocketException {
        initComponents();
        server = new DatagramSocket(2003);
        cond = new Condivisa(this);
        con = new Connection();
        this.setTitle("Java Chat");
        iscon = false;
        nickname = "anonymous";
        ischaton=false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Terminate = new javax.swing.JButton();
        Connessione = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        TxtNickname = new javax.swing.JLabel();
        btnChange = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ChatList = new javax.swing.JList<>();
        TxtMessaggio = new javax.swing.JTextField();
        btnInvia = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(624, 439));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Terminate.setText("Cancel Connection");
        Terminate.setEnabled(false);
        Terminate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TerminateActionPerformed(evt);
            }
        });

        Connessione.setText("Connection Request");
        Connessione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnessioneActionPerformed(evt);
            }
        });

        jLabel1.setText("Nickname:");
        jLabel1.setToolTipText("");

        TxtNickname.setText("anonymous");

        btnChange.setText("Change Nickname");
        btnChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(Terminate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChange, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtNickname)
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(Connessione)
                    .addContainerGap(758, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnChange, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Terminate, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addComponent(TxtNickname)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(Connessione, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jScrollPane1.setViewportView(ChatList);

        TxtMessaggio.setEnabled(false);

        btnInvia.setText("Send");
        btnInvia.setEnabled(false);
        btnInvia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInviaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(TxtMessaggio)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnInvia, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtMessaggio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInvia)
                .addGap(0, 62, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TerminateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TerminateActionPerformed
        if (con.connection){
            con.setMessaggio("exitChat");
        }
        else {
            l.setMessaggio("exitChat");
        }
    }//GEN-LAST:event_TerminateActionPerformed

    public static boolean validate(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        return ip.matches(PATTERN);
    }
    
    private void ConnessioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnessioneActionPerformed
        String ip = JOptionPane.showInputDialog("Enter the IP of the user you want to comunicate with");
        if (ip != null){
           if (!(ip.equals("")) && validate(ip)){
            con = new Connection(server,ip,nickname);
            con.start();
            iscon = true;
            }
            else {
                JOptionPane.showMessageDialog(null, "Nessun indirizzo valido inserito");
            } 
        }
    }//GEN-LAST:event_ConnessioneActionPerformed

    private void btnInviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInviaActionPerformed
        if (con.connection){
            con.setMessaggio(TxtMessaggio.getText());
            TxtMessaggio.setText("");
        } else {
            l.setMessaggio(TxtMessaggio.getText());
            TxtMessaggio.setText("");
        }
    }//GEN-LAST:event_btnInviaActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        nickname = JOptionPane.showInputDialog("Enter your nickname");
        TxtNickname.setText(nickname);
        CheckForConnections();
    }//GEN-LAST:event_formWindowOpened

    private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeActionPerformed
        nickname = JOptionPane.showInputDialog("Enter your nickname");
        TxtNickname.setText(nickname);
    }//GEN-LAST:event_btnChangeActionPerformed

    public void CheckForConnections(){
        l = new Listener(server,nickname);
        l.start();
    }
    
    public String makePopUpInput(String nick){
        String choice = JOptionPane.showInputDialog("You received a connection from "+nick+", do you wish to accept? Y/N");
        return choice;
    }
    public synchronized void connesso(){
        Terminate.setEnabled(true);
        Connessione.setEnabled(false);
        TxtMessaggio.setEnabled(true);
        ChatList.setEnabled(true);
        btnInvia.setEnabled(true);
        btnChange.setEnabled(false);
        iscon = true;
    }
    public synchronized void disconesso(){
        Terminate.setEnabled(false);
        Connessione.setEnabled(true);
        TxtMessaggio.setEnabled(false);
        ChatList.setEnabled(false);
        btnInvia.setEnabled(false);
        btnChange.setEnabled(true);
        iscon = false;
        DefaultListModel listModel = new DefaultListModel();
        ChatList.setModel(listModel);
        TxtMessaggio.setText("");
    }
    public void makePopUp(String text){
        JOptionPane.showMessageDialog(null, text);
    }
    public void setChat(ArrayList<Message> msg){
        DefaultListModel listModel = new DefaultListModel();
        for (int i = 0; i < msg.size();i++){
            listModel.addElement(msg.get(i).getNickname() + ": " + msg.get(i).getMessaggio());
        }
        ChatList.setModel(listModel);
    }
    public void ChatActivated(){
        ischaton = true;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ChatFrame().setVisible(true);
                } catch (SocketException ex) {
                    Logger.getLogger(ChatFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> ChatList;
    private javax.swing.JButton Connessione;
    private javax.swing.JButton Terminate;
    private javax.swing.JTextField TxtMessaggio;
    private javax.swing.JLabel TxtNickname;
    private javax.swing.JButton btnChange;
    private javax.swing.JButton btnInvia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
