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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Samuele Savoldi
 */
public class Meteo {
    private final Coordinate località;
    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?mode=xml";
    private static String APIKEY;
    private static final String QUERY_TEMPERATURE_VALUE = "/current/temperature/@value";
    private static final String QUERY_HUMIDITY="/current/humidity/@value";
    private static final String QUERY_CLOUDS="/current/clouds/@name";
    private String meteo;

    public Meteo (Coordinate località, boolean proxy){
        this.località = località;
        readSettings();
        Connessione c=new Connessione(toFinalURL(), proxy);
        interpreter(c.getConnessione());
        
    }
    
    public String getMeteo() {
        return meteo;
    }

    public void setMeteo(String meteo) {
        this.meteo = meteo;
    }
    
    private void interpreter (InputStream result){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(result);
            
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            
            //Temperatura: valore
            XPathExpression currentTemperatureValue = xpath.compile(QUERY_TEMPERATURE_VALUE);
            String tempValue = (String) currentTemperatureValue.evaluate(doc, XPathConstants.STRING);
            
            //Umidità espressa in %
            XPathExpression currentHumidity = xpath.compile(QUERY_HUMIDITY);
            String tempHumidity = (String) currentHumidity.evaluate(doc, XPathConstants.STRING);
            
            //Cielo: coperto, poche nuvole, ...     
            XPathExpression currentClouds = xpath.compile(QUERY_CLOUDS);
            String tempClouds = (String) currentClouds.evaluate(doc, XPathConstants.STRING);
            
            Stampa currentMeteo=new Stampa(tempValue, tempHumidity, tempClouds);
            
            
            this.setMeteo(currentMeteo.toString());
            
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(GoogleMaps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    private String toFinalURL(){
        return WEATHER_URL + 
                "&lat=" + località.getLatitudine() +
                "&lon=" + località.getLongitudine() +
                "&appid=" + APIKEY;
    }
    
    /*Legge le impostazioni per OpenWeather*/
    private void readSettings(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("apikey.txt"));
            APIKEY = br.readLine();
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Il file di connesione è stato perso.");
            exit(2);
        } catch (IOException ex) {
           System.out.println("Si è verificato u problema nell'apertura del file.");
           exit(2);
        }
    }
}
