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
public class Stampa {
    private Double temperatura;
    private String umidità;
    private String cielo;

    public Stampa(String temperatura, String umidità, String cielo) {
        this.temperatura = toCelsius(Double.parseDouble(temperatura));
        this.umidità = umidità;
        this.cielo = cielo;
    }
    
    private Double toCelsius(Double temperatura)
    {
        return temperatura-273.15; //Per convertire da Kelvin a Celsius, basta sottrarre i Kelvin. 0°C = 273,15 K
                                
    }
    
    /*Stampa il meteo*/
    @Override
    public String toString(){
        if(temperatura != null){
            return "Ecco il meteo: \n"
                + "Temperatura: " + temperatura + "\n"
                + "Umidità: " + umidità + "% \n"
                + "Cielo: " + cielo + "\n";
            
        }
        else{
            return "Valori nulli.";
        }
        
    }
}
