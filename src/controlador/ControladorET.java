
package controlador;

import java.util.ArrayList;
import modelo.DatosFases;
import modelo.Producto;
import modelo.Resultados;
import modelo.TiempoModel;
import vista.frmEstacionResultados;
import vista.frmEstacionTrabajo;

/**
 *
 * @author Omar
 */
public class ControladorET {
    frmEstacionTrabajo vistaEstacionTrabajo; //Vista
    Producto producto; //Modelo
    TiempoModel tiempoModelo = new TiempoModel(); //Modelo que nos servirá para mostrar el tiempo transcurrido
    DatosFases datosFases = new DatosFases(); //Intanciamos una variable de tipo Datos fases y llamamos al contructor vacio, para en la siguiente línea poder usar una de sus funciones
    ArrayList<DatosFases> datosFaseEnsamblaje = datosFases.obtenerDatos(true); //En este Array tenemos los datos de tiempo y costo según el material para la fase ensamblaje, por eso es "true"
    ArrayList<DatosFases> datosFasePintura = datosFases.obtenerDatos(false); //En este Array tenemos los datos de tiempo y costo según el color para la fase de pintura, por eso es "false"
    String tiempoTotal; //Variable donde se guardará el tiempo total
    String tiempoUnidad; //variable donde se guardará el tiempo por Unidad
    String costoTotal; //Variable donde se guardará el costo total
    String costoUnidad; //Variable donde se guardará el costo por unidad.
    
    
    public ControladorET (frmEstacionTrabajo vistaEstacionTrabajo, Producto producto) {
        this.vistaEstacionTrabajo = vistaEstacionTrabajo;
        this.producto = producto;
        this.tiempoTotal = "";
        this.tiempoUnidad = "";
        this.costoTotal = "";
        this.costoUnidad = "";
    }
    
    public void iniciar(){
        this.vistaEstacionTrabajo.setVisible(true);
        this.vistaEstacionTrabajo.setLocationRelativeTo(null);
        //Iniciamos los hilos
        hiloProduccion();
        hiloTiempo();
    }
    
    
    
    //Procedimiento encargado del hilo del tiempo
    private void hiloTiempo() {
        //Creamos un nuevo hilo que estará encargado del tiempo
        Thread hilo = new Thread(()-> { //Implementamos las acciones del "Run" con este lambda, es decir, las acciones a ejecutar cuando el hilo sea iniciado
            try {
                while(tiempoModelo.getHiloProduccionIsAlive()) { //Mientras el hilo de Producción no haya terminado
                    this.vistaEstacionTrabajo.lblContadorTiempo.setText(this.tiempoModelo.tiempoActual()); //Imprimimos en tiempo actual
                    Thread.sleep(1000); //Dormimos al hilo 1 segundo
                    this.tiempoModelo.incrementarSegundos(); //Incremenamos los segundos y al volver a iniciar el ciclo se actualizará en pantalla segundo por segundo
                }
                
            } catch (InterruptedException exp) {
                exp.printStackTrace();
            }
        });
        //Ya definido lo que hará el "run", incia el hilo
        hilo.start();
    }
    
    
    
    //Procedimiento encargao del hilo de la produccion de los productos
    private void hiloProduccion() {
        //Creamos un nuevo hilo encargado de manejar las 3 fases de produccion
        Thread hilo = new Thread(()->{ //Definimos con este lambda lo que hará el "run" del hilo
            try {
                //Almacenamos los datos como el tiempo y costo según material y pintura:
                DatosFases datosEnsamblaje = this.obtenerDatosFase(producto.getMaterial(), true); //Obtenemos los datos correspondientes al material para el ensamblaje, es decir, los segundos y el costo
                DatosFases datosPintura = this.obtenerDatosFase(producto.getColor(), false); //Obtenemos los datos correspondientes a la pintura para el producto, es decir, los segundos y el costo
                
                for(int i = 1; i <= producto.getCantidad(); i++) { //Ciclo for para la cantidad de productos solicitada
                    this.vistaEstacionTrabajo.lblContadorProductos.setText(i+"/"+producto.getCantidad()); //Actualizamos el label del contador de productos
                    //---------FASE DE ENSAMBLAJE-----------
                    for(int j = 0; j <=100; j++){ //Ciclo for para actualizar la progressBar y el label de porcentaje
                        Thread.sleep((datosEnsamblaje.getTiempo()*1000)/100); //Obtenemos los segundos a tardar, se multiplica por 1000 para pasarlo a mili segundos y se divide en 100 para que en cada iteración duerma al hilo los segundos necesarios hasta llenar la barra
                        this.vistaEstacionTrabajo.pbEnsamblaje.setValue(j);//Actualizamos el estado de la barra
                        this.vistaEstacionTrabajo.lblPorcentajeEnsamblaje.setText(j+"%"); //Actualiza la etiqueta que indica el porcentaje en cada iteración
                    }
                    
                    //---------FASE DE PINTURA--------
                    for(int j = 0; j<=100;j++) { //Ciclo for para actualizar el progressBar  y el label de porcentaje
                        Thread.sleep((datosPintura.getTiempo()*1000)/100); //Obtenemos los segundos a tardar, se multiplica por 1000 para pasarlo a mili segundos y se divide en 100 para que en cada iteración duerma al hilo los segundos necesarios hasta llenar la barra
                        this.vistaEstacionTrabajo.pbPintura.setValue(j); //Actualizamos el estado del progressBar
                        this.vistaEstacionTrabajo.lblPorcentajePintura.setText(j+"%"); //Actualizamos la etiqueta del porcentaje
                    }
                    
                    //--------FASE DE EMPAQUE----------
                    //Ya sabemos que debe tardarse 10 segundos
                    for(int j = 0; j<=100 ;j++) {
                        Thread.sleep((10*1000)/100); 
                        this.vistaEstacionTrabajo.pbEmpaque.setValue(j);
                        this.vistaEstacionTrabajo.lblPorcentajeEmpaque.setText(j+"%");
                    }
                    //---------Ahora almacenamos el tiempo que tomó 1 producto para RESULTADOS-----
                    if(i == 1) {
                        this.tiempoUnidad = tiempoModelo.tiempoActual(); //Almacenamos el tiempo por unidad si es la primera vez que termina un producto
                    }
                    
                    //Ahora limpiamos las barras y los porcentajes
                    this.vistaEstacionTrabajo.pbEnsamblaje.setValue(0);
                    this.vistaEstacionTrabajo.lblPorcentajeEnsamblaje.setText("0%");
                    this.vistaEstacionTrabajo.pbPintura.setValue(0);
                    this.vistaEstacionTrabajo.lblPorcentajePintura.setText("0%");
                    this.vistaEstacionTrabajo.pbEmpaque.setValue(0);
                    this.vistaEstacionTrabajo.lblPorcentajeEmpaque.setText("0%");
                }
                tiempoModelo.setHiloProduccionIsAlive(false); //Comunicamos al hilo del tiempo que este hilo terminó sus procesos principales
                //-----almacenamos datos que servirán para la ventana RESULTADOS -----------
                this.tiempoTotal = tiempoModelo.tiempoActual(); //Guardamos el tiempo total
                this.costoUnidad = String.format("%.2f", ((datosEnsamblaje.getCosto()*datosEnsamblaje.getTiempo())+(datosPintura.getCosto()*datosPintura.getTiempo())+20));
                this.costoTotal = String.format("%.2f", ((datosEnsamblaje.getCosto()*datosEnsamblaje.getTiempo())+(datosPintura.getCosto()*datosPintura.getTiempo())+20)*producto.getCantidad());
                
                Thread.sleep(5000); //Dormimos el hilo 5 segundos para que el usuairo pueda apreciar los datos de la ventana
                //-----Ahora instanciamos el modelo y la vista para la estación de resultados------
                Resultados resultado = new Resultados(producto, costoTotal, costoUnidad, tiempoTotal, tiempoUnidad); //Modelo
                frmEstacionResultados vistaEstacionResultados = new frmEstacionResultados(); //vista
                ControladorER controladorER = new ControladorER(resultado, vistaEstacionResultados); //intanciamos el controlador
                controladorER.iniciar();
                this.vistaEstacionTrabajo.dispose();
                
                
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        });
        //Luego de definir "run", iniciamos el hilo
        hilo.start();
        
    }
    
    //Función encargada de retornar una variable de tipo datosFases que contiene los segundos y el costo del producto en produccion
    private DatosFases obtenerDatosFase(String descripcion, boolean esEnsamblaje) {
        if(esEnsamblaje) { //Si es true, es de la fase ensamblaje
            for(DatosFases datos: datosFaseEnsamblaje) {
                if(datos.getDescripcion().equals(descripcion.toUpperCase())) { //Si encuentra la coincidencia de descripcion, es decir, el material, retorna el objeto
                    return datos;
                }
            }
        } else { //Si no es de la fase ensamblaje, es de la fase pintura
            for(DatosFases datos: datosFasePintura) {
                if(datos.getDescripcion().equals(descripcion.toUpperCase())){ //Si encuentra la coincidencia de descripcion, es decir, el color, retorna el objeto.    
                    return datos;
                }
                
            }
        }
        return null;
    }
    
}
