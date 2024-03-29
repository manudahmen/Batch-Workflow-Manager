/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.emptycanvas.bwm;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;

/**
 *
 * @author Manuel Dahmen
 */
public class ByEmailListener implements AppListener, Runnable {

    private App app;
    private String username;
    private String password;
    private String server;
    private int port;
    private Session session;

    public ByEmailListener() {
        try {
            EmailAccount ea = new EmailAccount(null);
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", ea.getProtocol());
            props.setProperty("mail.pop3.ssl.enable", "true");
            props.setProperty("mail.pop3.starttls.enable", "true");
            props.setProperty("mail.pop3.starttls.required", "false");
            props.put("mail.store.protocol", "pop3");
            props.put("mail.pop3.host", ea.getServer());
            props.put("mail.pop3.port", "995");
            props.put("mail.pop3s.ssl.checkserveridentity", "true");
            props.put("mail.pop3s.ssl.trust", ea.getServer());
            POP3Authentificator poP3Authentificator = new POP3Authentificator(ea.getUsername(), ea.getPassword());
            this.session = Session.getInstance(props, poP3Authentificator);
            System.out.println(ea.toString());

            session.setDebug(true);

            Store store;
            store = session.getStore("pop3s");

            store.connect(ea.getServer(), ea.getPort(), ea.getUsername(), ea.getPassword());

            System.out.println("Connection OH OH OK");
            //create the folder object and open it
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            System.out.println("Message count: " + inbox.getMessageCount());

            for (int i = 1; i <= inbox.getMessageCount(); i++) {
                Message m = inbox.getMessage(i);
                MessageAction messageAction = null;
                try {
                    messageAction = new MessageAction(m);
                    
                    messageAction.setVisible(true);
                    
                } catch (Exception ex) {
                    Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("Message from : " + m.getFrom()[0]);
                System.out.println("\nSujet : " + m.getSubject());
                System.out.println("\n");
            }
        } catch (MessagingException ex) {
            Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void configure(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.server = host;
        this.port = port;

    }

    public class POP3Authentificator extends Authenticator {

        String user;
        String pw;

        public POP3Authentificator(String username, String password) {
            super();
            this.user = username;
            this.pw = password;
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, pw);
        }
    }

    public void listenFor(App app) {
        this.app = app;
    }

    public void run() {
        while (app.isRunning()) {

        }
    }

    public static void main(String[] args) {
        EmailAccount ea = new EmailAccount(null);
        try {
            ByEmailListener byEmailListener = new ByEmailListener();
        } catch (Exception ex) {
            Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
