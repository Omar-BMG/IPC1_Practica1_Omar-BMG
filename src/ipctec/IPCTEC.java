
package ipctec;

import controlador.ControladorEC;
import modelo.ManejoArchivoBinario;
import vista.frmEstacionCarga;

/**
 *
 * @author Omar
 */
public class IPCTEC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       frmEstacionCarga vistaEstacionCarga = new frmEstacionCarga();  //Intanciamos una ventana de carga
       ManejoArchivoBinario archivo = new ManejoArchivoBinario(); //Instaciamos el modelo para el controlador
       ControladorEC controladorEC = new ControladorEC(vistaEstacionCarga, archivo); //Intanciamos el controlador de la ventana de carga y le enviamos la ventana de carga instanicada previamente, y el modelo del archivo
       controladorEC.iniciar(); //Llamamos al procedimiento iniciar del controlador de la estacion de carga para mostrar la ventana.
    }
    
}
