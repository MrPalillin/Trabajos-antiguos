package es.uva.eii.ds.empresaX.negocio.controladoresCasoUso;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import es.uva.eii.ds.empresaX.negocio.modelos.Empleado;
import es.uva.eii.ds.empresaX.negocio.modelos.LineaDePedidoDeHorno;
import es.uva.eii.ds.empresaX.negocio.modelos.PedidoDeHorno;
import es.uva.eii.ds.empresaX.negocio.modelos.Sesion;
import es.uva.eii.ds.empresaX.persistencia.FachadaPersistenciaEmpleadoHorno;
import es.uva.eii.ds.empresaX.servicioscomunes.JSONHelper;
import es.uva.eii.ds.empresaX.servicioscomunes.MessageException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class ControladorCUPrepararPedido {

    private static ArrayList<PedidoDeHorno> listaPedidos; 
    
    /**
     * Devuelve una lista de pedidos en estado 'Registrado' con fechas de
     * entrega deseada del día actual y el siguiente.
     * @return Lista o null si ha ocurrido algún error
     */
    public static ArrayList<PedidoDeHorno> getListaPedidosPendientesHorno() {
        listaPedidos = new ArrayList<>();
        
        // Lista de pedidos de hoy y mañana
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        
        // Consulta la BD
        String resultadoListaPedidos;
        try {
            resultadoListaPedidos = FachadaPersistenciaEmpleadoHorno.getListaPedidosPendientes(today, tomorrow);
        } catch(MessageException e) {
            // Error controlado
            return null;
        }
        
        // Genera la lista de pedidos
        JsonObject jsonResultado = new Gson().fromJson(resultadoListaPedidos, JsonObject.class);
        JsonArray jArrayPedidos = jsonResultado.getAsJsonArray(JSONHelper.JSON_PEDIDOS_PENDIENTES);
        for(JsonElement jPedido : jArrayPedidos) {
            listaPedidos.add(new PedidoDeHorno(jPedido.toString()));
        }
        
        return listaPedidos;
    }
    
    
    /**
     * Devuelve el pedido con el número de pedido especificado.
     * @param numeroPedido Número del pedido
     * @return Pedido (null si no existe)
     */
    public static PedidoDeHorno getPedido(int numeroPedido) {
        PedidoDeHorno res = null;
        
        // Busca el pedido y se lo devuelve
        for(int i = 0; res == null && i < listaPedidos.size(); i++) {
            if(listaPedidos.get(i).getNumeroDePedido() == numeroPedido) {
                res = listaPedidos.get(i);
            }
        }
        
        return res;
    }
    
    
    /**
     * Marca el pedido especificado como preparando.
     * @param pedido Pedido a preparar
     * @return true si se ha realizado con éxito, false si no
     */
    public static boolean prepararPedido(PedidoDeHorno pedido) throws MessageException {
        Empleado empleado = Sesion.getInstancia().getEmpleado();
        // new Operacion
        // get JSON
        return FachadaPersistenciaEmpleadoHorno.cambiarEstadoPedidoAPreparando(
                        LocalDateTime.now(), empleado.getDni(), pedido.getNumeroDePedido());
    }
    
    /**
     * Devuelve las materias que faltan para poder generar un pedido con sus cantidades.
     * @param pedido Pedido
     * @return Lista de materias que faltan (vacía si se puede generar)
     */
    public static ArrayList<LineaDePedidoDeHorno> getMateriasQueFaltan(PedidoDeHorno pedido) {
        ArrayList<LineaDePedidoDeHorno> faltantes = new ArrayList<>();
        
        
        
        return faltantes;
    }
    
}
