
package modelo;

/**
 *
 * @author Omar
 */
public class TiempoModel {
    int segundos;
    String tiempoFormato; // 00:00
    boolean hiloProduccionIsAlive = true;
    
    //Contructor, no necesitamos instanciar los atributos, se recibirá nada por parámetro
    public TiempoModel() {
    }
    
    //Getter de segundos

    public int getSegundos() {
        return segundos;
    }
    
    //Getter y Setter de hiloIsAlive

    public synchronized boolean getHiloProduccionIsAlive() {
        return hiloProduccionIsAlive;
    }

    public synchronized void setHiloProduccionIsAlive(boolean hiloProduccionIsAlive) {
        this.hiloProduccionIsAlive = hiloProduccionIsAlive;
    }
    
    
    //procedimiento encargado de incrementar los segundos
    public void incrementarSegundos () {
        segundos++;
    }
    
    //Función que nos retorna el formato del tiempo deseado, estilo un contador
    public String tiempoActual() {
        int minutos = segundos / 60;
        int segundosRestantes = segundos%60;
        
        tiempoFormato = String.format("%02d:%02d", minutos, segundosRestantes);  //El "%d" imprime el entero que encuentre despues de una coma, el "02" indica el tamaño del entero
        return tiempoFormato;
    }
    
    
}
