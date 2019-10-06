package es.uva.eii.ds.empresaX.persistencia;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import es.uva.eii.ds.empresaX.negocio.modelos.TipoEstadoPedido;
import es.uva.eii.ds.empresaX.servicioscomunes.JSONHelper;
import es.uva.eii.ds.empresaX.servicioscomunes.MessageException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class FachadaPersistenciaEmpleadoHorno {
    
    // Devuelve los pedidos cuya fecha en la quese quiere está en un rango
    private static final String QUERY_PEDIDOS_POR_FECHA =
            "SELECT * FROM PEDIDODEHORNO WHERE FECHAENLAQUESEQUIERE BETWEEN (?) AND (?)";
    
    // Devuelve el dependiente asociado a un pedido de horno
    private static final String QUERY_EMPLEADO = "SELECT * FROM EMPLEADO WHERE NIF = (?)";
    // Devuelve el cliente asociado a un pedido de horno
    private static final String QUERY_CLIENTE = "SELECT * FROM CLIENTE WHERE NIF = (?)";
    // Devuelve todos los estados de un pedido de horno (orden desc)
    private static final String QUERY_ESTADOS_PEDIDO = 
            "SELECT * FROM OPERACIONSOBREPEDIDODEHORNO "
            + "INNER JOIN ESTADODEPEDIDODEHORNO ON TIPO = IDTIPO "
            + "WHERE PEDIDODEHORNO = (?) ORDER BY TIPO DESC";
    
    // Devuelve todas las lineas de pedidos
    private static final String QUERY_LINEAS_PEDIDO = 
            "SELECT * FROM LINEADEPEDIDODEHORNO  WHERE PEDIDO = (?)";
    
        // Devuelve todas las lineas de pedidos
    private static final String QUERY_PRODUCTO = 
            "SELECT * FROM PRODUCTO WHERE CODIGO = (?)";
    
    // Marca un pedido como preparando
    private static final String INSERT_PEDIDO_PREPARANDO = 
            "INSERT INTO OPERACIONSOBREPEDIDODEHORNO VALUES ((?), 2, (?), (?))";
    
    
    private static ConexionBD conectarse() throws ClassNotFoundException, SQLException, MessageException {
        return ConexionBD.getInstancia();
    }
    
    public static String getListaPedidosPendientes(LocalDate inicio, LocalDate fin) throws MessageException {
        JsonArray arrayPedidos = new JsonArray();
        
        try {
            ConexionBD conn = conectarse();
            PreparedStatement pst = conn.prepareStatement(QUERY_PEDIDOS_POR_FECHA);
            pst.setDate(1, Date.valueOf(inicio));
            pst.setDate(2, Date.valueOf(fin));
            
            // Resultados: pedidos en un rango de fechas. Hay que obtener los que están registrados solo
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                JsonObject pedido = new JsonObject();
                
                int nPedido = rs.getInt("NUMERODEPEDIDO");
                
                pst = conn.prepareStatement(QUERY_ESTADOS_PEDIDO);
                pst.setInt(1, nPedido);
                ResultSet rsEstado = pst.executeQuery();
                if(rsEstado.next()) {
                    // Existe, ¿está registrado?
                    String nTipo = rsEstado.getString("NOMBRETIPO");
                    if(TipoEstadoPedido.valueOf(nTipo) == TipoEstadoPedido.Registrado) {
                        // Solo está registrado, se añade
                        pedido.addProperty(JSONHelper.JSON_NUM_PEDIDO, nPedido);
                        pedido.addProperty(JSONHelper.JSON_FECHA_DESEADA, rs.getDate("FECHAENLAQUESEQUIERE").toString());
                        pedido.add(JSONHelper.JSON_CLIENTE, getCliente(rs.getString("CLIENTE"), conn));
                        pedido.add(JSONHelper.JSON_DEPENDIENTE, getEmpleado(rs.getString("DEPENDIENTE"), conn));
                        pedido.add(JSONHelper.JSON_OPERACIONES, getOperacionesPedido(nPedido, conn));
                        pedido.add(JSONHelper.JSON_LINEAS, getLineasPedido(nPedido, conn));
                        arrayPedidos.add(pedido);
                    }
                }
                
                // Para cualquier otro caso, se ignora el resultado
            }
           
        } catch (Exception ex) {
            if(ex instanceof MessageException) {
                // La relanza
                throw new MessageException(ex.getMessage());
            } else {
                // Otra
                throw new MessageException("[!] Ocurrió un error al obtener la lista de pedidos registrados.");
            }
        }
        
        JsonObject pedidosPendientes = new JsonObject();
        pedidosPendientes.add(JSONHelper.JSON_PEDIDOS_PENDIENTES, arrayPedidos);

        return pedidosPendientes.toString();
    }
    
    
    public static boolean cambiarEstadoPedidoAPreparando(LocalDateTime ts, String nifEmpleado, int numeroPedido) throws MessageException {
        boolean res;
        
        try {
            ConexionBD conn = conectarse();
            PreparedStatement pst = conn.prepareStatement(INSERT_PEDIDO_PREPARANDO);
            pst.setTimestamp(1, Timestamp.valueOf(ts));
            pst.setString(2, nifEmpleado);
            pst.setInt(3, numeroPedido);
            
            // Resultados: pedidos en un rango de fechas. Hay que obtener los que están registrados solo
            if(pst.executeUpdate() > 0) {
                // Ha actualizado el estado
                res = true;
            } else {
                res = false;
            }
           
        } catch (ClassNotFoundException | SQLException ex) {
            res = false;
        }
        
        return res;
    }
    
    
    
    public static JsonObject getCliente(String dniCliente, ConexionBD conn) throws MessageException {
        JsonObject res = new JsonObject();
        
        try {
            PreparedStatement pst = conn.prepareStatement(QUERY_CLIENTE);
            pst.setString(1, dniCliente);

            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                res.addProperty(JSONHelper.JSON_DNI, rs.getString("NIF"));
                res.addProperty(JSONHelper.JSON_NOMBRE, rs.getString("NOMBRE"));
                res.addProperty(JSONHelper.JSON_APELLIDOS, rs.getString("APELLIDOS"));
                res.addProperty(JSONHelper.JSON_TELEFONO, rs.getInt("TELEFONO"));
                res.addProperty(JSONHelper.JSON_EMAIL, rs.getString("EMAIL"));
            } else {
                throw new MessageException("[!] No existe el cliente con DNI: " + dniCliente);
            }
        } catch(Exception e) {
            if(e instanceof MessageException) {
                // La relanza
                throw new MessageException(e.getMessage());
            } else {
                throw new MessageException("[!] Ocurrió un error al obtener el cliente con NIF: " + dniCliente);
            }
        }
        
        return res;
    }
    
    public static JsonObject getEmpleado(String nifEmpleado, ConexionBD conn) throws MessageException {
        JsonObject res = new JsonObject();
        
        try {
            PreparedStatement pst = conn.prepareStatement(QUERY_EMPLEADO);
            pst.setString(1, nifEmpleado);

            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                res.addProperty(JSONHelper.JSON_DNI, nifEmpleado);
                res.addProperty(JSONHelper.JSON_NOMBRE, rs.getString("NOMBRE"));
                res.addProperty(JSONHelper.JSON_APELLIDOS, rs.getString("APELLIDOS"));
                res.addProperty(JSONHelper.JSON_FECHA_INICIO, rs.getDate("FECHAINICIOENEMPRESA").toString());
                res.add(JSONHelper.JSON_ROLES, FachadaPersistenciaEmpleado.obtenerRolesEmpleado(conn, nifEmpleado));
                res.add(JSONHelper.JSON_VINCULACIONES, FachadaPersistenciaEmpleado.obtenerVinculacionesEmpleado(conn, nifEmpleado));
                res.add(JSONHelper.JSON_DISPONIBILIDADES, FachadaPersistenciaEmpleado.obtenerDisponibilidadesEmpleado(conn, nifEmpleado));
            } else {
                throw new MessageException("[!] No existe el empleado con NIF: " + nifEmpleado);
            }
        } catch(Exception e) {
            if(e instanceof MessageException) {
                // La relanza
                throw new MessageException(e.getMessage());
            } else {
                throw new MessageException("[!] Ocurrió un error al obtener el empleado con NIF: " + nifEmpleado);
            }
        }
        
        return res;
    }
    
    public static JsonArray getOperacionesPedido(int numeroPedido, ConexionBD conn) throws MessageException {
        JsonArray res = new JsonArray();
        
        try {
            PreparedStatement pst = conn.prepareStatement(QUERY_ESTADOS_PEDIDO);
            pst.setInt(1, numeroPedido);

            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                JsonObject operacion = new JsonObject();
                operacion.addProperty(JSONHelper.JSON_MOMENTO, rs.getTimestamp("MOMENTO").toString());
                operacion.add(JSONHelper.JSON_EMPLEADO, getEmpleado(rs.getString("EMPLEADO"), conn));
                operacion.addProperty(JSONHelper.JSON_ESTADO, rs.getString("NOMBRETIPO"));
                res.add(operacion);
            }
        } catch(Exception e) {
            if(e instanceof MessageException) {
                // La relanza
                throw new MessageException(e.getMessage());
            } else {
                throw new MessageException("[!] Ocurrió un error al obtener las operaciones del pedido con nº: " + numeroPedido);
            }
        }
        
        return res;
    }

    public static JsonElement getLineasPedido(int nPedido, ConexionBD conn) throws MessageException {
        JsonArray res = new JsonArray();
        
        try {
            PreparedStatement pst = conn.prepareStatement(QUERY_LINEAS_PEDIDO);
            pst.setInt(1, nPedido);

            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                JsonObject lineasPedido = new JsonObject();
                lineasPedido.addProperty(JSONHelper.JSON_CANTIDAD, rs.getInt("CANTIDAD"));
                //System.out.println(rs.getInt("CANTIDAD"));
                lineasPedido.add(JSONHelper.JSON_PRODUCTO, getProducto(rs.getString("PRODUCTO"), conn));
                res.add(lineasPedido);
            }
        } catch(Exception e) {
            if(e instanceof MessageException) {
                // La relanza
                throw new MessageException(e.getMessage());
            } else {
                throw new MessageException("[!] Ocurrió un error al obtener las lineas del pedido: " + nPedido);
            }
        }
        
        return res;
    
    
         }

    public static JsonElement getProducto(String codigoProducto, ConexionBD conn) throws MessageException {
         JsonObject res = new JsonObject();
        
        try {
            PreparedStatement pst = conn.prepareStatement(QUERY_PRODUCTO);
            pst.setString(1, codigoProducto);

            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                res.addProperty(JSONHelper.JSON_CODIGO, rs.getString("CODIGO"));
                res.addProperty(JSONHelper.JSON_NOMBRE, rs.getString("NOMBRE"));
                res.addProperty(JSONHelper.JSON_DESCRIPCION, rs.getString("DESCRIPCION"));
                res.addProperty(JSONHelper.JSON_EXISTENCIAS, rs.getInt("EXISTENCIAS"));
                res.addProperty(JSONHelper.JSON_SUBTIPO, rs.getString("SUBTIPO"));
                res.addProperty(JSONHelper.JSON_CANTIDAD_MIN_STOCK, rs.getInt("CANTIDADMINIMAENSTOCK"));

            } else {
                throw new MessageException("[!] No existe el producto con codigo: " + codigoProducto);
            }
        } catch(Exception e) {
            if(e instanceof MessageException) {
                // La relanza
                throw new MessageException(e.getMessage());
            } else {
                throw new MessageException("[!] Ocurrió un error al obtener el codigo del Producto: " + codigoProducto);
            }
        }
        
        return res;
    }
}
