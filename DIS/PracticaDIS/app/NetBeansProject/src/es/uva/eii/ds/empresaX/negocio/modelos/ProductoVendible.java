package es.uva.eii.ds.empresaX.negocio.modelos;

public class ProductoVendible extends Producto {
    
    private double precioVenta;
    
    public ProductoVendible(String codigo, String nombre, String descripcion, int existencias, int cantidadMinimaEnStock,double precioVenta) {
        super(codigo, nombre, descripcion, existencias, cantidadMinimaEnStock);
        this.precioVenta = precioVenta;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

}
