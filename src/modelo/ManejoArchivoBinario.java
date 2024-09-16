
package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 
 */
public class ManejoArchivoBinario {
    public void agregarContenido (String ruta_archivo, Producto producto) {
        try {
           //Obtenemos el listado de productos en caso ya exista un archivo binario
           List<Producto> listado_productos = this.obtenerContenido(ruta_archivo);
           listado_productos.add(producto);
           
           FileOutputStream salidaArchivo = new FileOutputStream(ruta_archivo);
           ObjectOutputStream salidaObjeto = new ObjectOutputStream(salidaArchivo);
           salidaObjeto.writeObject(listado_productos);
           salidaArchivo.close();
           salidaObjeto.close();
            
        } catch (Exception e) {
            System.out.println("Error al agregar contenido: "+e.getMessage());
        }
    } 
    
    public ArrayList<Producto> obtenerContenido(String ruta_archivo){
        //Creamos un ArrayList donde se almacenaran los productos obtenidos del binario
        ArrayList<Producto> listaProductosActual = new ArrayList<>();
        try {
            //Verificamos si el archivo existe
            File archivo = new File(ruta_archivo); //Creamos el archivo enviando la ruta del existente al constructor
            if(archivo.exists()) {
                FileInputStream entradaArchivo = new FileInputStream(ruta_archivo); //Ubicamos el archivo
                ObjectInputStream entradaObjeto = new ObjectInputStream(entradaArchivo); //Leemos los objetos
                //Por medio de un casteo, convertimos los Objetos leidos a una lista dinámica de tipo Producto
                listaProductosActual = (ArrayList<Producto>)entradaObjeto.readObject(); 
            }
            
        } catch (Exception e) {
            System.out.println("Error al obtener el contenido: "+e.getMessage());
        }
        
        return listaProductosActual;
    }
}
