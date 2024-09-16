
package controlador;

import java.awt.event.ActionListener;
import modelo.ManejoArchivoBinario;
import modelo.Resultados;
import vista.frmEstacionCarga;
import vista.frmEstacionResultados;

/**
 *
 * @author Omar
 */
public class ControladorER {
    Resultados resultado; //Modelo
    frmEstacionResultados vistaEstacionResultados; //Vista

    public ControladorER(Resultados resultado, frmEstacionResultados vistaEstacionResultados) {
        this.resultado = resultado;
        this.vistaEstacionResultados = vistaEstacionResultados;
    }
    
    public void iniciar(){
        this.vistaEstacionResultados.setVisible(true);
        this.vistaEstacionResultados.setLocationRelativeTo(null);
        this.vistaEstacionResultados.lblCodigoResultado.setText("Producto: "+this.resultado.getProducto().getCodigo());
        this.vistaEstacionResultados.lblNombreResultado.setText("Nombre: "+this.resultado.getProducto().getNombre());
        this.vistaEstacionResultados.lblColorResultado.setText("Color: "+this.resultado.getProducto().getColor());
        this.vistaEstacionResultados.lblMaterialResultado.setText("Material: "+this.resultado.getProducto().getMaterial());
        this.vistaEstacionResultados.lblCantidadResultado.setText("Cantidad: "+this.resultado.getProducto().getCantidad());
        this.vistaEstacionResultados.lblCostoResultado.setText("Costo total: "+this.resultado.getCostoTotal());
        this.vistaEstacionResultados.lblCostoUnidadResultado.setText("Costo c/u: "+this.resultado.getCostoUnidad());
        this.vistaEstacionResultados.lblTiempoResultado.setText("Tiempo total: "+this.resultado.getTiempoTotal());
        this.vistaEstacionResultados.lblTiempoUnidadResultado.setText("Tiempo c/u: "+this.resultado.getTiempoUnidad());
        this.vistaEstacionResultados.btnVolverEstacionCarga.addActionListener(listenerVolver());
    }
    
    private ActionListener listenerVolver() {
        return e ->{ //Lambda para el action Performd
            frmEstacionCarga vistaEstacionCarga = new frmEstacionCarga();  //Intanciamos una ventana de carga
            ManejoArchivoBinario archivo = new ManejoArchivoBinario(); //Instaciamos el modelo para el controlador
            ControladorEC controladorEC = new ControladorEC(vistaEstacionCarga, archivo); //Intanciamos el controlador de la ventana de carga y le enviamos la ventana de carga instanicada previamente, y el modelo del archivo
            controladorEC.iniciar(); //Llamamos al procedimiento iniciar del controlador de la estacion de carga para mostrar la ventana.
            this.vistaEstacionResultados.dispose();
        };
    }
    
    
}
