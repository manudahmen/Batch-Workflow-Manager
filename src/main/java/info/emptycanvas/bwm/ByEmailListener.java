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

    public static void main(String[] args) {
            EmailAccount ea = new EmailAccount(null);
            
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", ea.getProtocol());
            
            Authenticator auth = new Authenticator() {
            };
            Session session = Session.getInstance(props, null/*new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                    "md500082@scarlet.be", "sposmes1");
                    }
                    }*/
            );
            
            Store store = null;
            try {
                store = session.getStore();
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                store.connect(ea.getServer(), ea.getPort(), ea.getUsername(), ea.getPassword());
            } catch (MessagingException ex) {
                Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            Folder inbox = null;
            try {
                inbox = store.getFolder("INBOX");
            } catch (MessagingException ex) {
                Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                inbox.open(Folder.READ_ONLY);
            } catch (MessagingException ex) {
                Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            Message msg = null;
            try {
                msg = inbox.getMessage(inbox.getMessageCount());
            } catch (MessagingException ex) {
                Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            Address[] in = null;
            try {
                in = msg.getFrom();
            } catch (MessagingException ex) {
                Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (Address address : in) {
                System.out.println("FROM:" + address.toString());
            }
            Multipart mp = null;
            try {
                mp = (Multipart) msg.getContent();
            } catch (IOException ex) {
                Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            BodyPart bp = null;
            try {
                bp = mp.getBodyPart(0);
            } catch (MessagingException ex) {
                Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
            }
         try {
           System.out.println("SENT DATE:" + msg.getSentDate());
            System.out.println("SUBJECT:" + msg.getSubject());
            System.out.println("CONTENT:" + bp.getContent());
            
        } catch (IOException ex) {
            Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ByEmailListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void listenFor(App app) {
        this.app = app;
    }

    public void run() {
        while (app.isRunning()) {

        }
    }
}
