package es.uva.eii.ds.empresaX.negocio.modelos;

/**
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class OperacionPedido {
    
    private final TipoEstadoPedido tipo;
    private final Empleado empleado;
    
    public OperacionPedido(TipoEstadoPedido tipo, Empleado empleado){
        this.tipo = tipo;
        this.empleado = empleado;
    }

    public TipoEstadoPedido getTipo() {
        return tipo;
    }
    
    public Empleado getEmpleado() {
        return empleado;
    }
    
}
