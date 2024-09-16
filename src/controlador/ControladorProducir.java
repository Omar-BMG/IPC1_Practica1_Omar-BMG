
package controlador;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ManejoArchivoBinario;
import modelo.Producto;
import vista.frmEstacionCarga;
import vista.frmEstacionTrabajo;
import vista.frmProducir;

/**
 *
 * @author Omar
 */
public class ControladorProducir {
    private frmProducir vistaProducir;
    private frmEstacionCarga vistaEstacionCarga; //Esta vista nos servirá para mandar a cerrarla
    private ManejoArchivoBinario archivo; //Modelo que nos servirá para obtener la lista de productos
    
    public ControladorProducir(frmProducir vistaProducir, frmEstacionCarga vistaEstacionCarga, ManejoArchivoBinario archivo) {
        this.vistaProducir = vistaProducir;
        this.vistaEstacionCarga = vistaEstacionCarga;
        this.archivo = archivo;
    }
    
    //Prodecimiento encargado de mostrar la ventana y escuchar los eventos de la misma
    public void iniciar() {
        this.vistaProducir.setVisible(true);
        this.vistaProducir.setLocationRelativeTo(null);
        llenarCombobox();
        this.vistaProducir.btnProducir.addActionListener(listenerProducir());
    }
    
    //Procedimiento encargado de llenar el combobox con los codigos disponibles
    private void llenarCombobox() {
        ArrayList<Producto> listaProductos = archivo.obtenerContenido("productos.bin"); //Obtenemos todos los productos del binario
        
        //Validamos que el arreglo con los productos no esté vacío
        if (listaProductos.size() != 0) {
            //Ahora recorremos la listaProductos y vamos agregando al combobox los códigos
            for(Producto producto : listaProductos) {
                this.vistaProducir.cbbxCodigoProducto.addItem(producto.getCodigo());
            }
            
        } 
    }
    
    //Listener del botón producir que contiene el evento del mismo
    private ActionListener listenerProducir() {
        return e -> { //retornamos el evento contenido a continuación:
            if((this.vistaProducir.txtCatidadProducto.getText().length()!=0)) {
                //Primero obtenemos el código y la cantidad seleccionadas
                String codigo = (String)this.vistaProducir.cbbxCodigoProducto.getSelectedItem(); //Obtendremos de tipo objeto lo seleccionado en el combobox y lo casteamos a string
                int cantidad = Integer.parseInt(this.vistaProducir.txtCatidadProducto.getText().trim()); //Obtenemos de tipo string y sin espacion lo escrito en el textField y lo casteamos a string
                if (cantidad>0){
                    //Ahora, obtenemos del binario la lista de productos disponibles y lo guardaremos en una variable de tipo Producto 
                    ArrayList<Producto> listaProductos = archivo.obtenerContenido("productos.bin");
                    Producto producto; //Variable donde se guardará el producto que coincida.


                    for (Producto productoSelect : listaProductos) {
                        if((productoSelect.getCodigo().equals(codigo))) { //Si el código seleccionado en el combobox coincide con alguno de la lista
                            producto = new Producto(productoSelect.getCodigo(), productoSelect.getNombre(), productoSelect.getMaterial(), productoSelect.getColor()); //Entonces, guardamos el producto encontrado
                            producto.setCantidad(cantidad); //Seteamos la cantidad a la nueva clase
                            
                            //Ahora que ya tenemos todos los datos que necesitará el controlador de la Estación de trabajo, se los enviamos
                            frmEstacionTrabajo vistaEstacionTrabajo = new frmEstacionTrabajo();
                            ControladorET controladorET = new ControladorET(vistaEstacionTrabajo, producto); //Al controlador le enviamos la vista y su modelo, en este caso la estacion de trabajo y el modelo producto
                            controladorET.iniciar(); //Iniciamos el controlador de la estacion de trabajo
                            this.vistaProducir.dispose();
                            this.vistaEstacionCarga.dispose();
                            //Cerramos las ventanas que ya no necesitaremos
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(vistaProducir, "Ingrese una cantidad válida");
                }     
            }   
            else {
                JOptionPane.showMessageDialog(vistaProducir, "Ingrese una cantidad");
            }
        };
    }
    
}
