/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package savos_weather;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.exit;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;


/**
 *
 * @author Samuele Savoldi
 */
public class Connessione {
    private String url;
    private InputStream output;
    private String proxyAddress, proxyPort, authUser, authPassword;
    private final Boolean proxy;
    
    
    public Connessione(String url, boolean proxy){
        this.url = url;
        this.proxy=proxy;
        connetti();
    }
    
    private void connetti(){
        try {
            URL URLInput = new URL(url);
            URLConnection ConnectionInput = URLInput.openConnection();
            ConnectionInput.connect();
            output = new BufferedInputStream(ConnectionInput.getInputStream());
            
            if (proxy==true){
                setProxy();
                connetti();
            }
        } catch (MalformedURLException ex) {
            System.out.println("Si è verificato un problema nel settare il proxy");
            output = null;
            exit(1);
        } 
        catch (IOException ex) {
                System.out.println("Non riesco a connetermi a Internet. Controlla la tua connessione.");
                exit(1);
            }
        
    }
    
    public InputStream getConnessione(){
        return output;
    }
    
    private void setProxy(){
        readSettings();
        System.setProperty("useProxy", "true");
        System.err.println("impostato");
        System.setProperty("http.proxyHost", proxyAddress);
        System.setProperty("http.proxyPort", proxyPort);
                
        
        Authenticator.setDefault(
           new Authenticator() {
              @Override
              public PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(
                       authUser, authPassword.toCharArray());
              }
           }
        );
        
    }
    
    /*Legge le impostazioni del proxy*/
    private void readSettings(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("proxy.config"));
            proxyAddress = br.readLine();
            System.out.println("proxy: " + proxyAddress);
            proxyPort = br.readLine();
            authUser = br.readLine();
            authPassword = br.readLine();
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println("E' stato perso il file del proxy.");
            exit(1);
        } catch (IOException ex) {
            System.out.println("Non riesco a connetermi a Internet. Controlla la tua connessione.");
        }
    }
}
