package es.uva.eii.ds.empresaX.negocio.modelos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import es.uva.eii.ds.empresaX.servicioscomunes.JSONHelper;

public abstract class Producto {

    private String codigo;
    private String nombre;
    private String descripcion;
    private int existencias;
    private int cantidadMinimaEnStock;
    
    public Producto(String codigo, String nombre, String descripcion, int existencias, int cantidadMinimaEnStock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.existencias = existencias;
        this.cantidadMinimaEnStock = cantidadMinimaEnStock;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public int getCantidadMinimaEnStock() {
        return cantidadMinimaEnStock;
    }

    public void setCantidadMinimaEnStock(int cantidadMinimaEnStock) {
        this.cantidadMinimaEnStock = cantidadMinimaEnStock;
    }
    
    
    
}
