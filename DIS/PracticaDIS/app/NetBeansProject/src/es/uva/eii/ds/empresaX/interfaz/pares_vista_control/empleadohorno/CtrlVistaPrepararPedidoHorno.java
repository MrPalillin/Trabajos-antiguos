package es.uva.eii.ds.empresaX.interfaz.pares_vista_control.empleadohorno;

import es.uva.eii.ds.empresaX.interfaz.GestorUI;
import es.uva.eii.ds.empresaX.negocio.controladoresCasoUso.ControladorCUPrepararPedido;
import es.uva.eii.ds.empresaX.negocio.modelos.LineaDePedidoDeHorno;
import es.uva.eii.ds.empresaX.negocio.modelos.PedidoDeHorno;
import es.uva.eii.ds.empresaX.servicioscomunes.MessageException;
import java.util.ArrayList;


/**
 * Controlador de la vista de consulta de facturas pendientes de pago.
 * 
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class CtrlVistaPrepararPedidoHorno {
    
    private final VistaPrepararPedidoHorno vista;
    
    private PedidoDeHorno pedidoActual; // Pedido seleccionado actual
    
    /**
     * Inicializa el controlador.
     * @param v Vista que controla
     */
    public CtrlVistaPrepararPedidoHorno(VistaPrepararPedidoHorno v) {
        vista = v;
        // Centra en la pantalla
        vista.setLocationRelativeTo(null);
        // Carga los pedidos para hoy o mañana
        ArrayList<PedidoDeHorno> pedidos = ControladorCUPrepararPedido.getListaPedidosPendientesHorno();
        if(pedidos.isEmpty()) {
            vista.mostrarMensajeSinPedidos();
        } else {
            vista.cargaListaPedidos(pedidos);
        }
        
        pedidoActual = null;
    }
    
    
    /**
     * El empleado ha confirmado el pedido a preparar.
     */
    public void procesaPedidoConfirmado() throws MessageException {
        if(pedidoActual == null) {
            return;
        }
        
        // Obtiene las materias faltantes
        ArrayList<LineaDePedidoDeHorno> faltantes = ControladorCUPrepararPedido.
                                                getMateriasQueFaltan(pedidoActual);
        
        if(!faltantes.isEmpty()) {
            // Falta materia -> muestra mensaje al usuario
            vista.mostrarMensajeFaltanMaterias(faltantes);
            return;
        }
        
        if(ControladorCUPrepararPedido.prepararPedido(pedidoActual)) {
            // Cambiado a actual -> muestra mensaje
            vista.mostrarMensajeExito();
        } else {
            // Ocurrió un error y no se pudo cambiar -> muestra mensaje
            vista.mostrarMensajeError();
        }
        
    }
    
    /**
     * El empleado a ha seleccionado un pedido de la lista.
     */
    public void procesaClickPedido() {
        // Obtiene el pedido seleccionado
        int numPedido = vista.getPedidoSeleccionado();
        
        pedidoActual = ControladorCUPrepararPedido.getPedido(numPedido);
        if(pedidoActual == null) {
            // ¡No existe el pedido!
            vista.mostrarErrorSeleccionPedido();
        }

        // Muestra los detalles al empleado
       // System.out.println(pedidoActual.getLineas().get(0).getProductoPedido().getCodigo());
        vista.mostrarDetallesPedido(pedidoActual);
    }
    
    
    /**
     * Cuando se cierra la ventana, se vuelve a la anterior.
     */
    public void procesaClickAtras() {
        GestorUI.getInstanciaSingleton().atras();
    }
    
}
