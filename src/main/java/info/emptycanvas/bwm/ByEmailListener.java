/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.emptycanvas.bwm;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import sun.text.normalizer.ICUBinary;

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

    public ByEmailListener(String username, String password, String host, int port) {
        configure(username, password, host, port);
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

    private ByEmailListener(EmailAccount ea) throws Exception{

            System.out.println(ea.toString());

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
            Session session = Session.getInstance(props, poP3Authentificator);
            session.setDebug(true);

            Store store;
            store = session.getStore("pop3s");

            store.connect(ea.getServer(), ea.getPort(),ea.getUsername(), ea.getPassword()); 
            
            System.out.println("Connection OH OH OK");
            Folder inbox;
            inbox = store.getDefaultFolder();            
            System.out.println("Message count: "+inbox.getMessageCount());
    }

    public void listenFor(App app) {
        this.app = app;
    }

    public void run() {
        while (app.isRunning()) {

        }
    }
    
    public static void main(String [] args)
    {
        EmailAccount ea = new EmailAccount(null);
        try {
            new ByEmailListener(ea);
        } catch (Exception ex) {
            Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
