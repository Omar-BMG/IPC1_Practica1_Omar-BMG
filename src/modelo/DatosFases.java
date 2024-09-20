
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Omar
 */
public class DatosFases {
    String descripcion;
    int tiempo;
    double costo;
    
    public DatosFases (String descripcion, int tiempo, double costo) {
        this.descripcion = descripcion;
        this.tiempo = tiempo;
        this.costo = costo;
    }

    public DatosFases() {
    }
    
    
    //Getters y Setters

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    
    
    //Función que retornará los datos deseados, si es verdadero retorna los datos de la fase ensamblaje, si es falso retorna los datos de la fase pintura
    public ArrayList<DatosFases> obtenerDatos (boolean esEnsamblaje) {
        ArrayList­<DatosFases> datosFases = new ArrayList<>();
        if(esEnsamblaje) {
            datosFases.add(new DatosFases("METAL", 15, 3));
            datosFases.add(new DatosFases("MADERA", 25, 1));
            datosFases.add(new DatosFases("VIDRIO", 10, 6));
            datosFases.add(new DatosFases("NYLON", 20, 2));
            datosFases.add(new DatosFases("HULE", 10, 5));
            datosFases.add(new DatosFases("POLIESTER", 5, 4));
        } else {
            datosFases.add(new DatosFases("VERDE", 15, 3));
            datosFases.add(new DatosFases("NEGRO", 25, 1));
            datosFases.add(new DatosFases("NA", 0, 0));
            datosFases.add(new DatosFases("AZUL", 20, 2));
            datosFases.add(new DatosFases("ROJO", 10, 5));
            datosFases.add(new DatosFases("AMARILLO", 5, 4));
        }
        
        return datosFases;
        
    }
    
    
}
