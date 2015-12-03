/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package savos_weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuele Savoldi
 */
public class Savos_Weather {

    public static String meteo;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        try {
            System.out.println("Benvenuto in Savos_Weather!");
            System.out.println("Inserire località:");
            //Leggo input località
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String address = br.readLine();
            boolean proxy = false;
            boolean temp = false;
            System.out.println("Volete inserire il proxy?(S/N)");
            //Leggo input proxy          
            String answer = br.readLine();
            while (temp == false) {
                if (answer.equalsIgnoreCase("S")) {
                    proxy = true;
                    temp = true;
                } else if (answer.equalsIgnoreCase("N")) {
                    proxy = false;
                    temp = true;
                } else {
                    System.out.println("Carattere non valido. Si può inserire solo S o N.");
                }
            }

            GoogleMaps indirizzo = new GoogleMaps(address, proxy);
            Meteo m = new Meteo(indirizzo.getLocalità(), proxy);
            meteo = m.getMeteo();
            System.out.println(meteo);

        } catch (IOException ex) {
            Logger.getLogger(Savos_Weather.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

