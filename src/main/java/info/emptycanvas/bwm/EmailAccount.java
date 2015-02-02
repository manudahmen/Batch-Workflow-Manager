/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.emptycanvas.bwm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manuel Dahmen <dahmen.manuel@scarlet.be>
 */
public class EmailAccount {
    private String store = "private-email.txt";

    int port;String protocol;
String server;
String username;
String password;

    public String getStore() {
        return store;
    }

    public int getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getServer() {
        return server;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public EmailAccount(String store)
    {
        if(store==null) store = this.store;
        
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(System.getProperty("user.home")+File.separator+store));
        } catch (IOException ex) {
            Logger.getLogger(EmailAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        protocol = (String) properties.get("mail.store.protocol");
        server = (String)properties.get("server");
        username = (String)properties.get("username");
        password = (String)properties.get("password");
        port = Integer.parseInt((String)properties.get("port"));
    }

    @Override
    public String toString() {
        return "Email account:\n"+server+"\n"+port+"\n"+protocol+"\nEmail:"+username+"\n"+(password!=null?"PWD":"NOPWD");
    }
    
        
}
