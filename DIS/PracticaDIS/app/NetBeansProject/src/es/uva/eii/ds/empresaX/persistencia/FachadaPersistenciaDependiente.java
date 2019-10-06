package es.uva.eii.ds.empresaX.persistencia;

import com.google.gson.JsonObject;
import es.uva.eii.ds.empresaX.negocio.modelos.Empleado;
import es.uva.eii.ds.empresaX.negocio.modelos.LineaDeVenta;
import es.uva.eii.ds.empresaX.negocio.modelos.Venta;
import es.uva.eii.ds.empresaX.servicioscomunes.MessageException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Abel Herrero Gómez (abeherr)
 * @author Daniel De Vicente Garrote (dandevi)
 * @author Roberto García Antoranz (robegar)
 */
public class FachadaPersistenciaDependiente {

    private static final String QUERY_ID_PRODUCTO = "SELECT * FROM PRODUCTO WHERE CODIGO = ?";

    private static final String QUERY_ID_VENTA_VENTA = "INSERT INTO VENTA VALUES(?,?,?)";
    private static final String QUERY_ID_VENTA_LINEA = "INSERT INTO LINEADEVENTA VALUES(?,?,?)";

    private static final String QUERY_EXISTENCIAS = "UPDATE PRODUCTO SET EXISTENCIAS = ? WHERE CODIGO = ?";

    private static ConexionBD conectarse() throws ClassNotFoundException, SQLException, MessageException {
        return ConexionBD.getInstancia();
    }

    public static JsonObject getProductoBD(String codigo) throws MessageException {

        String cif = null;
        try {
            ConexionBD conn = conectarse();
            PreparedStatement pst = conn.prepareStatement(QUERY_ID_PRODUCTO);
            System.out.println();
            pst.setString(1, codigo);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String cod = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                int existencias = rs.getInt("existencias");
                int cantMin = rs.getInt("cantidadminimaenstock");
                String subtipo = rs.getString("subtipo");
                double precio = rs.getDouble("precio");
                int diasEntrega = rs.getInt("diasparaentregadelproveedor");
                String tipoMateria = rs.getString("tipodemateriaprima");
                JsonObject json = new JsonObject();
                json.addProperty("codigo", cod);
                json.addProperty("nombre", nombre);
                json.addProperty("descripcion", descripcion);
                json.addProperty("existencias", existencias);
                json.addProperty("cantMin", cantMin);
                json.addProperty("subtipo", subtipo);
                json.addProperty("precio", precio);
                json.addProperty("diasEntrega", diasEntrega);
                json.addProperty("tipoMateria", tipoMateria);
                return json;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FachadaPersistenciaEncargado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void setVentaBD(Venta venta,Empleado empleado) throws MessageException {

        try {
            ConexionBD conn = conectarse();
            
            PreparedStatement count = conn.prepareStatement("SELECT COUNT(*) AS rowcount FROM VENTA");
            ResultSet rsCount = count.executeQuery();
            rsCount.next();
            
            Date hoy = java.sql.Date.valueOf(venta.getFechaDeVenta());
            PreparedStatement pst = conn.prepareStatement(QUERY_ID_VENTA_VENTA);
            pst.setInt(1, rsCount.getInt("rowcount")+1);
            pst.setDate(2, hoy);
            pst.setString(3, empleado.getDni());
            int rs = pst.executeUpdate();

            
            for (LineaDeVenta lp : venta.getLineas()) {
                PreparedStatement pst1 = conn.prepareStatement(QUERY_ID_VENTA_LINEA);
                pst1.setInt(1, lp.getCantidad());
                pst1.setInt(2, rsCount.getInt("rowcount")+1);
                pst1.setString(3, lp.getProducto().getCodigo());
                int rs1 = pst1.executeUpdate();
            }   
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FachadaPersistenciaEncargado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void actualizarExistenciasBD(Venta venta) throws MessageException {

        try {
            ConexionBD conn = conectarse();
            for (LineaDeVenta lp : venta.getLineas()) {
                PreparedStatement pst1 = conn.prepareStatement(QUERY_ID_PRODUCTO);
                pst1.setString(1, lp.getProducto().getCodigo());
                ResultSet rs1 = pst1.executeQuery();
                rs1.next();
                PreparedStatement pst2 = conn.prepareStatement(QUERY_EXISTENCIAS);
                pst2.setInt(1, rs1.getInt("existencias")-lp.getCantidad());
                pst2.setString(2, lp.getProducto().getCodigo());
                int rs2 = pst2.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FachadaPersistenciaEncargado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
