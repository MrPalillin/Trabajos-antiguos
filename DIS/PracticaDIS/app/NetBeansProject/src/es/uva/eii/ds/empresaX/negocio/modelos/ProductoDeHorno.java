package es.uva.eii.ds.empresaX.negocio.modelos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import es.uva.eii.ds.empresaX.servicioscomunes.JSONHelper;

public class ProductoDeHorno extends ProductoVendible {
    
    public ProductoDeHorno(String codigo, String nombre, String descripcion, int existencias, int cantidadMinimaEnStock, double precioVenta) {
        super(codigo, nombre, descripcion, existencias, cantidadMinimaEnStock, precioVenta);
    }
    /*
    public ProductoDeHorno(String jsonString) {
        try {
            JsonObject jo = new Gson().fromJson(jsonString, JsonObject.class);
            
            String codigo = jo.get(JSONHelper.JSON_CODIGO).getAsString();
            String nombre = jo.get(JSONHelper.JSON_NOMBRE).getAsString();
            String descripcion = jo.get(JSONHelper.JSON_DESCRIPCION).getAsString();
            int existencias = jo.get(JSONHelper.JSON_EXISTENCIAS).getAsInt();
            int cantidadMinimaEnStock = jo.get(JSONHelper.JSON_CANTIDAD_MIN_STOCK).getAsInt();
            double precioVenta = jo.get(JSONHelper.JSON_PRECIO_VENTA).getAsDouble();
            
            //super(codigo, nombre, descripcion, existencias, cantidadMinimaEnStock, precioVenta);
            // usar setters
        } catch(JsonSyntaxException | NumberFormatException e) {
            // Especificar excepciones
            System.out.println("[!] Excepción al crear Producto");
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }
    */

    

}
