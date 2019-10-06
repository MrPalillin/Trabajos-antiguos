package es.uva.eii.ds.empresaX.persistencia;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import es.uva.eii.ds.empresaX.servicioscomunes.JSONHelper;
import es.uva.eii.ds.empresaX.servicioscomunes.MessageException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author Abel Herrero Gómez (abeherr)
 * @author Daniel De Vicente Garrote (dandevi)
 * @author Roberto García Antoranz (robegar)
 */
public class FachadaPersistenciaEncargado {

    // FACTURAS PENDIENTES DE PAGO
    // Devuelve el ID del proveedor especificado
    private static final String QUERY_EXISTE_PROVEEDOR = "SELECT cif FROM Proveedor WHERE UPPER(nombre) = (?) OR UPPER(cif) = (?)";
    // Devuelve el año de la primera fecha de emisión
    private static final String QUERY_MIN_ANIO_FAC = "SELECT YEAR(MIN(FECHADEEMISION)) AS MINANIO FROM FACTURA";
    // Devuelve el año de la última fecha de emisión
    private static final String QUERY_MAX_ANIO_FAC = "SELECT YEAR(MAX(FECHADEEMISION)) AS MAXANIO FROM FACTURA";
    // Devuelve las facturas pendientes, en el rango de fechas especificado, para el proveedor especificado
    private static final String QUERY_FACTURAS_PEND = 
            "SELECT * FROM "
            + "Factura INNER JOIN PedidoAProveedor ON Factura.pedido = PedidoAProveedor.numeroDePedido "
            + "INNER JOIN proveedor ON PedidoAProveedor.proveedor = Proveedor.cif "
            + "WHERE fechaDeEmision >= (?) AND fechaDeEmision <= (?) AND enTransferencia IS NULL";
    private static final String QUERY_PLUS_PROVEEDOR = " AND ( UPPER(Proveedor.nombre) = (?) OR UPPER(Proveedor.cif) = (?) )";

    private static ConexionBD conectarse() throws ClassNotFoundException, SQLException, MessageException {
        return ConexionBD.getInstancia();
    }
    
    /**
     * Devuelve el año de la primera factura.
     *
     * @return Año de la primera factura
     * @throws es.uva.eii.ds.empresaX.servicioscomunes.MessageException
     */
    public static int getMinAnioFacturas() throws MessageException {
        int minAnio = 1970;

        try {
            ConexionBD conn = conectarse();
            PreparedStatement pst = conn.prepareStatement(QUERY_MIN_ANIO_FAC);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                minAnio = rs.getInt("MINANIO");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            throw new MessageException("[!] Error al consultar el año de la primera factura.");
        }

        return minAnio;
    }

    /**
     * Devuelve el año de la última factura.
     *
     * @return Año de la última factura
     * @throws es.uva.eii.ds.empresaX.servicioscomunes.MessageException
     */
    public static int getMaxAnioFacturas() throws MessageException {
        int maxAnio = LocalDate.now().getYear();

        try {
            ConexionBD conn = conectarse();
            PreparedStatement pst = conn.prepareStatement(QUERY_MAX_ANIO_FAC);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                maxAnio = rs.getInt("MAXANIO");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            throw new MessageException("[!] Error al consultar el año de la primera factura.");
        }

        return maxAnio;
    }

    /**
     * Comprueba si existe un proveedor a partir de su nombre
     *
     * @param proveedor Nombre de proveedor
     * @return true si existe, false si no
     * @throws es.uva.eii.ds.empresaX.servicioscomunes.MessageException
     */
    public static boolean existeProveedor(String proveedor) throws MessageException {
        boolean existe = false;

        try {
            ConexionBD conn = conectarse();
            PreparedStatement pst = conn.prepareStatement(QUERY_EXISTE_PROVEEDOR);
            pst.setString(1, proveedor.toUpperCase());
            pst.setString(2, proveedor.toUpperCase());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                existe = true;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            throw new MessageException("[!] Error al consultar si existe el proveedor \"" + proveedor + "\".");
        }

        return existe;
    }

    /**
     * Devuelve un JSON con las facturas pendientes de pago en el rango de fechas
     * seleccionado y para el proveedor requerido.
     * 
     * Estructura del JSON de entrada:
     *  {
     *      "fechaInicio" : "2019-05-29", "fechaF": "2019-05-31", "proveedor" : "15264859N"
     *  }
     * 
     * Para buscar cualquier proveedor, proporcionar valor vacío ("proveedor" : "")
     * 
     * @param filtros JSON con las fechas de inicio/fin y el proveedor a buscar
     * @return Facturas que cumplen los requisitos de búsqueda (JSON)
     * @throws es.uva.eii.ds.empresaX.servicioscomunes.MessageException
     */
    public static String getFacturasPendientesDePago(String filtros) throws MessageException {
        // Obtiene los datos de entrada
        JsonObject jo = new Gson().fromJson(filtros, JsonObject.class);
        Date fechaI = Date.valueOf(jo.get(JSONHelper.JSON_FECHA_INICIO).getAsString());
        Date fechaF = Date.valueOf(jo.get(JSONHelper.JSON_FECHA_FIN).getAsString());
        String cifProveedor = jo.get(JSONHelper.JSON_PROVEEDOR).getAsString();
        if(cifProveedor.isEmpty()) cifProveedor = null;
        
        // Obtiene la lista de facturas
        JsonArray arrayFacturas = new JsonArray();
        try {
            ConexionBD conn = conectarse();
            String query = QUERY_FACTURAS_PEND;
            if(cifProveedor != null) {
                // Proveedor especificado
                query += QUERY_PLUS_PROVEEDOR;
            }
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setDate(1, fechaI);
            pst.setDate(2, fechaF);
            if(cifProveedor != null) { 
                pst.setString(3, cifProveedor.toUpperCase());
                pst.setString(4, cifProveedor.toUpperCase());
            }
            
            // Realiza la consulta
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                JsonObject factura = new JsonObject();
                // Atributos directos de la factura
                factura.addProperty(JSONHelper.JSON_FECHA_EMISION, rs.getDate("FECHADEEMISION").toString());
                factura.addProperty(JSONHelper.JSON_IMPORTE, rs.getDouble("IMPORTE"));
                factura.addProperty(JSONHelper.JSON_CUENTA_BANCARIA, rs.getString("CUENTABANCARIA"));
                factura.add(JSONHelper.JSON_PEDIDO, getPedido(rs)); // Añade el pedido

                // Añade la factura a la lista
                arrayFacturas.add(factura);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            throw new MessageException("[!] Error al consultar las facturas pendientes.");
        }

        // Construye el objeto resultado
        JsonObject facturasPendientes = new JsonObject();
        facturasPendientes.add(JSONHelper.JSON_FACTURAS_PENDIENTES, arrayFacturas);
        
        return facturasPendientes.toString();
    }

    /**
     * Devuelve el objeto JSON correspondiente al pedido.
     * @param rs Resultado de la consulta
     * @return JSON del pedido
     * @throws SQLException 
     */
    private static JsonObject getPedido(ResultSet rs) throws SQLException {
        JsonObject pedido = new JsonObject();
        
        pedido.addProperty(JSONHelper.JSON_NUM_PEDIDO, rs.getLong("NUMERODEPEDIDO"));
        pedido.addProperty(JSONHelper.JSON_FECHA_REALIZACION, rs.getDate("FECHADEREALIZACION").toString());
        pedido.addProperty(JSONHelper.JSON_PENDIENTE, rs.getString("ESTAPENDIENTE"));
        // Proveedor
        pedido.add(JSONHelper.JSON_PROVEEDOR, getProveedor(rs));
        
        return pedido;
    }
    
    /**
     * Devuelve el objeto JSON correspondiente al proveedor.
     * @param rs Resultado de la consulta
     * @return JSON del proveedor
     * @throws SQLException 
     */
    private static JsonObject getProveedor(ResultSet rs) throws SQLException {
        JsonObject proveedor = new JsonObject();
        
        proveedor.addProperty(JSONHelper.JSON_NOMBRE, rs.getString("NOMBRE"));
        proveedor.addProperty(JSONHelper.JSON_TELEFONO, rs.getString("TELEFONO"));
        proveedor.addProperty(JSONHelper.JSON_EMAIL, rs.getString("EMAIL"));
        
        return proveedor;
    }
    
}
