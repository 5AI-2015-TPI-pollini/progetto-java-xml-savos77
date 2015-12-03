/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package savos_weather;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Samuele Savoldi
 */

public class GoogleMaps {
     Coordinate località;
    private static final String QUERY_STATUS = "/GeocodeResponse/status/text()";
    private static final String QUERY_STATUS_FAIL = "ZERO_RESULT";
    private static final String QUERY_LATITUDE = "/GeocodeResponse/result/geometry/location/lat/text()";
    private static final String QUERY_LONGITUDE = "/GeocodeResponse/result/geometry/location/lng/text()";
    private static final String GEOCODE_URL = "http://maps.googleapis.com/maps/api/geocode/xml?address=";
    

    public GoogleMaps (String indirizzo, boolean proxy){
        Connessione c;
        try {
            c = new Connessione(toFinalURL(indirizzo), proxy);
             interpreter(c.getConnessione());
             System.out.println("Caricamento in corso...");
        } catch (UnsupportedEncodingException ex) {
             Logger.getLogger(GoogleMaps.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
    
    
    private void stampaCoordinate(){
        System.out.println(località);
    }
    
    public Coordinate getLocalità(){
        return this.località;
    }
    
    
    private void interpreter (InputStream result){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(result);
            
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            
            //Controlla se è stato trovato qualcosa
            XPathExpression expressionStatus = xpath.compile(QUERY_STATUS);
            String status = (String) expressionStatus.evaluate(doc, XPathConstants.STRING);
            if(status.equals(QUERY_STATUS_FAIL)){
                System.out.println("Non è stato trovato niente.");
                return;
            }
            
            XPathExpression lat = xpath.compile(QUERY_LATITUDE);
            XPathExpression lon = xpath.compile(QUERY_LONGITUDE);
            NodeList lats = (NodeList) lat.evaluate(doc, XPathConstants.NODESET);
            NodeList lons = (NodeList) lon.evaluate(doc, XPathConstants.NODESET);
            località = new Coordinate(lats.item(0).getNodeValue(), lons.item(0).getNodeValue()); 
            
              
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(GoogleMaps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        
    private String toFinalURL(String indirizzo) throws UnsupportedEncodingException{
        String finalUrl=GEOCODE_URL;
        finalUrl+=URLEncoder.encode(indirizzo, "UTF-8");
        return finalUrl;
    }
}

