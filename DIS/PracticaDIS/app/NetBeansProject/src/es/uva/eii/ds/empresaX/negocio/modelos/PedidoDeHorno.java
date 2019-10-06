package es.uva.eii.ds.empresaX.negocio.modelos;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import es.uva.eii.ds.empresaX.servicioscomunes.JSONHelper;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class PedidoDeHorno {
    private int numeroDePedido;
    private LocalDate fechaEnLaQueSeQuiere;
    private Cliente cliente;
    private Empleado dependiente;
    private ArrayList<LineaDePedidoDeHorno> lineas;
    private TreeMap<LocalDateTime, OperacionPedido> operaciones;

    /**
     * Construye e inicializa un Pedido de Horno desde una string JSON.
     * @param jsonString String JSON
     */
    public PedidoDeHorno(String jsonString) {
        try {
            JsonObject jo = new Gson().fromJson(jsonString, JsonObject.class);
            
            numeroDePedido = jo.get(JSONHelper.JSON_NUM_PEDIDO).getAsInt();
            // FECHA DESEADA
            String[] fechaD = jo.get(JSONHelper.JSON_FECHA_DESEADA).getAsString().split("-");
            fechaEnLaQueSeQuiere = LocalDate.of(
                    Integer.valueOf(fechaD[0]), // YYYY
                    Integer.valueOf(fechaD[1]), // MM
                    Integer.valueOf(fechaD[2])  // DD
            );
            
            cliente = new Cliente(jo.get(JSONHelper.JSON_CLIENTE).toString());
            dependiente = new Empleado(jo.get(JSONHelper.JSON_DEPENDIENTE).toString());
            configuraLineasPedido(jo);
            configuraOperacionesPedido(jo);
        } catch(JsonSyntaxException | NumberFormatException e) {
            // Especificar excepciones
            throw new IllegalArgumentException("[!] Excepción al crear Empleado: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene las líneas del pedido y las añade a la lista.
     * @param jo Objeto JSON
     */
    private void configuraLineasPedido(JsonObject jo) {
        //LineaDePedidoDeHorno linea = new LineaDePedidoDeHorno();
        lineas = new ArrayList<>();
        JsonArray jLineas = jo.getAsJsonArray(JSONHelper.JSON_LINEAS);
        for(JsonElement jlin : jLineas) {
            JsonObject jLinea = new Gson().fromJson(jlin.toString(), JsonObject.class);
            int cantidad =  jLinea.get(JSONHelper.JSON_CANTIDAD).getAsInt();

            //Creando Producto a través de Json
            JsonObject pro = new Gson().fromJson(jLinea.get(JSONHelper.JSON_PRODUCTO).toString(), JsonObject.class);
            String codigo  = pro.get(JSONHelper.JSON_CODIGO).getAsString();
            String nombre  = pro.get(JSONHelper.JSON_NOMBRE).getAsString();
            String descripcion = pro.get(JSONHelper.JSON_DESCRIPCION).getAsString();
            int existencias = pro.get(JSONHelper.JSON_EXISTENCIAS).getAsInt();
            int cantidadMinimaEnStock = pro.get(JSONHelper.JSON_CANTIDAD_MIN_STOCK).getAsInt();
            Producto producto = new Producto(codigo, nombre, descripcion, existencias, cantidadMinimaEnStock) {};
            
            LineaDePedidoDeHorno lineaPedidoHorno = new LineaDePedidoDeHorno(cantidad, producto);
            lineas.add(lineaPedidoHorno);
        }
        
    }
    
    /**
     * Obtiene las operaciones y las añade al árbol.
     * @param jo Objeto JSON
     */
    private void configuraOperacionesPedido(JsonObject jo) {
        operaciones = new TreeMap<>();
        JsonArray jOperaciones = jo.getAsJsonArray(JSONHelper.JSON_OPERACIONES);
        for(JsonElement jop : jOperaciones) {
            JsonObject jOperacion = new Gson().fromJson(jop.toString(), JsonObject.class);
            // Momento de generación de la operación
            LocalDateTime momento = Timestamp.valueOf(jOperacion.get(JSONHelper.JSON_MOMENTO).getAsString()).toLocalDateTime();
            // Estado de la operación
            String estado = jOperacion.get(JSONHelper.JSON_ESTADO).getAsString();
            // Empleado que realizó la operación
            Empleado empleado = new Empleado(jOperacion.get(JSONHelper.JSON_EMPLEADO).toString());
            operaciones.put(momento, new OperacionPedido(TipoEstadoPedido.valueOf(estado), empleado));
        }
    }

    public int getNumeroDePedido() {
        return numeroDePedido;
    }

    public LocalDate getFechaEnLaQueSeQuiere() {
        return fechaEnLaQueSeQuiere;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Empleado getDependiente() {
        return dependiente;
    }

    public OperacionPedido getUltimoEstado() {
        return operaciones.lastEntry().getValue();
    }
    
    public ArrayList<LineaDePedidoDeHorno> getLineas() {
        return lineas;
    }
    
}
