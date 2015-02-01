/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.emptycanvas.bwm;

import java.util.*;
import javax.mail.*;
/**
 *
 * @author Manuel Dahmen
 */
public class ByEmailListener implements AppListener, Runnable{
  private App app;
    private String username;
    private String password;
    private String server;
    private int port;
  public ByEmailListener(String username, String password, String host, int port)
  {
    configure(username,password,host,port);
  }
  public void configure(String username, String password, String host, int port)
  {
    this.username = username;
    this.password = password;
    this.server = host;
    this.port = port;
    
    
  }
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3");
        try {
            Authenticator auth = new Authenticator() {};
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(
                  "md500082@scarlet.be", "sposmes1");
            }
            }
            );
            
            Store store = session.getStore();
            store.connect("pop.scarlet.be", 995, "md500082@scarlet.be", "sposmes1");
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message msg = inbox.getMessage(inbox.getMessageCount());
            Address[] in = msg.getFrom();
            for (Address address : in) {
                System.out.println("FROM:" + address.toString());
            }
            Multipart mp = (Multipart) msg.getContent();
            BodyPart bp = mp.getBodyPart(0);
            System.out.println("SENT DATE:" + msg.getSentDate());
            System.out.println("SUBJECT:" + msg.getSubject());
            System.out.println("CONTENT:" + bp.getContent());
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }
  public void listenFor(App app)
  {
      this.app = app;
  }
  public void run()
  {
    while(app.isRunning())
    {
      
    }
  }
}
