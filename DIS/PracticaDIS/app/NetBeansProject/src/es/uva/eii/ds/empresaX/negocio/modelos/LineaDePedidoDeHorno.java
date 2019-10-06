package es.uva.eii.ds.empresaX.negocio.modelos;


public class LineaDePedidoDeHorno {
    
    private int cantidad;
    private Producto productoPedido;

    public LineaDePedidoDeHorno(int cantidad, Producto productoPedido) {
        this.cantidad = cantidad;
        this.productoPedido = productoPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Producto getProductoPedido() {
        return productoPedido;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setProductoPedido(Producto productoPedido) {
        this.productoPedido = productoPedido;
    }


    
}
