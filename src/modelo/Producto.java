
package modelo;

import java.io.Serializable;

/**
 *
 * @author Omar
 */
public class Producto implements Serializable{
    String codigo;
    String nombre;
    String material;
    String color;
    int cantidad;

    public Producto(String codigo, String nombre, String material, String color) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.material = material;
        this.color = color;
        this.cantidad = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    //Getters y setters de cantidad

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
}
