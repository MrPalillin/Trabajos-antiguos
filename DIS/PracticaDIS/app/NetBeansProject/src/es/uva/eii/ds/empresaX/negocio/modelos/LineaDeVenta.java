/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.eii.ds.empresaX.negocio.modelos;

/**
 *
 * @author daniel
 */
public class LineaDeVenta {
    private int cantidad;
    private ProductoVendible producto;

    public LineaDeVenta(int cantidad, ProductoVendible producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ProductoVendible getProducto() {
        return producto;
    }

    public void setProducto(ProductoVendible producto) {
        this.producto = producto;
    }
    
    
}
