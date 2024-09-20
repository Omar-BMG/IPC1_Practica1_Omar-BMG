
package controlador;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import modelo.ManejoArchivoBinario;
import modelo.Producto;
import vista.frmEstacionCarga;
import vista.frmProducir;


/**
 *
 * @author Omar
 */
public class ControladorEC {
   
    private frmEstacionCarga vistaEstacionCarga;
    private ManejoArchivoBinario archivo;
    
    
    //Constructor//Le asignamos a las instancias de estacion carga 
    public ControladorEC(frmEstacionCarga vistaEstacionCarga, ManejoArchivoBinario archivo) {
        this.vistaEstacionCarga = vistaEstacionCarga;
        this.archivo = archivo;
    }
    
    //Procedimiento encargado de iniciar la ventana y escuchar las acciones de los botones, para añadir eventos
    public void iniciar() {
        this.vistaEstacionCarga.setVisible(true);
        this.vistaEstacionCarga.setLocationRelativeTo(null);
        this.vistaEstacionCarga.btnCargarProductos.addActionListener(listenerCargarCSV()); //añadimos un Listener al botón
        this.vistaEstacionCarga.btnProducir.addActionListener(listenerProducir()); //añadimos un listener para el evento del botón producir
        actualizarTabla(); //Actualizamos la tabla si existen productos en el binario guardados.
    }
    
    //Procedimiento que nos servirá para refrescar la tabla de productos de la vista estacion de carga
    public void actualizarTabla(){
        ArrayList<Producto> productos = archivo.obtenerContenido("productos.bin"); //Recibimos en un ArrayList lo leido del binario
        
        DefaultTableModel tablaModelo = (DefaultTableModel)this.vistaEstacionCarga.tablaProductos.getModel(); 
        tablaModelo.setRowCount(0); //Para evitar que se dupliquen las filas
        for (Producto producto : productos) {
            tablaModelo.addRow(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getMaterial(), producto.getColor()});
        }
        
    }
    
    //Listener del botón cargar que contiene el evento del mismo
    private ActionListener listenerCargarCSV() {
        return e -> { //Añadimos el evento
            //Con el "JFileChooser" le damos la orden que se abra la ventana del archivo
            JFileChooser fileChooser = new JFileChooser();
            int seleccion = fileChooser.showOpenDialog(this.vistaEstacionCarga); //Con "showOpenDialog" Aparece un cuadro de diálogo de selección de archivos "cargar archivo".
        
            //Condición
            if(seleccion == JFileChooser.APPROVE_OPTION) {//Comparamos las acciones almacenadas en la variable selección, y sólo si escojió un arhivo ejecuta las siguientes instrucciones:
                String filePath = fileChooser.getSelectedFile().getAbsolutePath(); //Para obtener el Path, definimos de tipo string la variable "filePath"
                leerProductosCSV(filePath); //Llamamos la procedimiento leerProductosCSV 
                actualizarTabla(); //Llamamos al procedimiento actualizarTabla
            }
        };
    }
    
    //Este procedimiento lee el archivo CSV y almacena en el binario los nuevos producto
    private void leerProductosCSV(String filePath) {
        boolean codigoExiste = false; //Inicializamos esta variable que más adelante servirá para validaciones
        ArrayList<Producto> productos_actuales = archivo.obtenerContenido("productos.bin"); //Creamos un arreglo para guardar los productos que ya tenga el binario
        ArrayList<Producto> productos = new ArrayList<>(); //Creamos un arreglo vacío donde se almacenarán los nuevos productos 
        //Haremos un bufferReader porque al final es similar al archivo binario, también debe ser leído el CSV
        try(BufferedReader br = new BufferedReader (new FileReader(filePath))) {
            String linea; //Variable que representará cada línea del CSV
            br.readLine(); //Saltamos la primera línea por ser el encabezado
            while ((linea = br.readLine()) != null) {  //A línea le asignamos lo que lea el buffer del CSV mientras no esté vacía la línea se ejecutarán las acciones.
                String [] partes = linea.split(","); //Creamos un array de Strings llamado "partes",le asginamos lo leìdo en "línea" y con el "slipt" indicamos que el separador es una coma. Cada dato se posicionarà en el arreglo iniciando el la posición cero.
                String codigo = partes[0].trim();//El codigo del Producto será la posición 0 del arreglo
                String nombre = partes[1].trim();//El nombre del Producto es la posición 1 del arreglo.
                String material = partes[2].trim();// El material del Investigador es la posición 2 del arreglo
                String color = partes[3].trim();//El color será la posición 3 del arreglo.
                
                
                if(productos_actuales.size()==0) { //Si el binario no tiene productos, ejecuta esto
                    //Ahora usamos el arraylist "productos" y almacenamos un nuevo producto y le enviamos al constructor los datos obtenidos.
                    productos.add(new Producto(codigo, nombre, material.toLowerCase(), color.toLowerCase())); //Respetamos el orden del constructor de la clase Producto
                }
                else if((codigoExiste = validarCodigoCSV(productos_actuales,codigo))){    
                } else{
                    productos.add(new Producto(codigo, nombre, material.toLowerCase(), color.toLowerCase())); //Respetamos el orden del constructor de la clase Producto
                } 
            }
            
            //Ahora recorremos el arreglo "productos" y finalmente vamos almacenando en el binario todos los productos obtenidos
            for (Producto producto:productos) {
                archivo.agregarContenido("productos.bin", new Producto(producto.getCodigo(), producto.getNombre(), producto.getMaterial(), producto.getColor()));
            }
            
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
    
    //Creamos una función para validar que  el código contenido en el CSV no exista ya en el binario
    private boolean validarCodigoCSV (ArrayList<Producto> productos_actuales, String codigo) {
        boolean respuesta = false;
        for (Producto producto: productos_actuales){
            if(producto.getCodigo().equals(codigo)){
                respuesta = true;
            }
        }
        return respuesta;
    }
    
    //Listener del botón producir que contiene el evento del mismo
    private ActionListener listenerProducir() {
        return e -> { //Añadimos el evento
            //Instanciamos una nueva vista de la ventana producir así como su controlador
            frmProducir vistaProducir = new frmProducir();
            ControladorProducir controladorProducir = new ControladorProducir(vistaProducir, this.vistaEstacionCarga, archivo); //Al controlador le eviamos las vistas y el modelo
            controladorProducir.iniciar();
            
        };
    }
}
