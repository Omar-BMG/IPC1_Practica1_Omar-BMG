
package modelo;

/**
 *
 * @author Omar
 */
public class Resultados {
    Producto producto;
    String costoTotal;
    String costoUnidad;
    String tiempoTotal;
    String tiempoUnidad;
    
    public Resultados(Producto producto, String costoTotal, String costoUnidad, String tiempoTotal, String tiempoUnidad) {
        this.producto = producto;
        this.costoTotal = costoTotal;
        this.costoUnidad = costoUnidad;
        this.tiempoTotal = tiempoTotal;
        this.tiempoUnidad = tiempoUnidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(String costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getCostoUnidad() {
        return costoUnidad;
    }

    public void setCostoUnidad(String costoUnidad) {
        this.costoUnidad = costoUnidad;
    }

    public String getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(String tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    public String getTiempoUnidad() {
        return tiempoUnidad;
    }

    public void setTiempoUnidad(String tiempoUnidad) {
        this.tiempoUnidad = tiempoUnidad;
    }
    
    
    
}
