/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package savos_weather;

/**
 *
 * @author Samuele Savoldi
 */
public class Coordinate {
    private String longitudine;
    private String latitudine;

    public Coordinate(String longitudine, String latitudine) {
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(String longitudine) {
        this.longitudine = longitudine;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(String latitudine) {
        this.latitudine = latitudine;
    }
    
    /*Stampa latitudine e longitudine*/
    @Override
    public String toString(){
        return "latitudine: " + latitudine + "\n" + longitudine + "longitudine";
    }
}
