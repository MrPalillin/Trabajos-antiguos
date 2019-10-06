package persistencia;
/**
 *
 * @author Enrique
 */
import modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteBD {

    private static final String CONSULTA_CLIENTE = "SELECT C.NOMBRE";
        
    public static void insert(Cliente c) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conexion = pool.getConnection();
        try {
            PreparedStatement prs = conexion.prepareStatement("INSERT INTO usuarios VALUES(?,?,?,?,?,?,?,?,?)");
            prs.setString(1, c.getDni());
            prs.setString(2, c.getNombre());
            prs.setString(3, c.getAp1());
            prs.setString(4, c.getAp2());
            prs.setString(5, c.getContra());
            prs.setString(6, c.getEmail());
            prs.setString(7, c.getfNac());
            prs.setString(8, c.getMovil());
            prs.setString(9, c.getSexo());
            int res2 = prs.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        }try {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO clientes VALUES(?,?,?)");
            ps.setString(1, c.getDni());
            ps.setString(2, c.getDireccion());
            ps.setString(3, c.getMinusvalias());
            int res = ps.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        pool.freeConnection(conexion);
    }
    public static boolean compruebaNifCliente(String nif) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conexion = pool.getConnection();
        boolean correcto = false;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM clientes U WHERE U.IDCLIENTE=?");
            ps.setString(1, nif);
            ResultSet rs = ps.executeQuery();
            correcto = rs.next();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        pool.freeConnection(conexion);
        return correcto;
    }
    public static ArrayList<String> atributosCliente(String dni){
        ArrayList<String> a = new ArrayList<String>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM usuarios " + "WHERE DNI = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            a.add(rs.getString("nombre"));
            a.add(rs.getString("apellido1"));
            a.add(rs.getString("apellido2"));
            a.add(rs.getString("contraseña"));
            a.add(rs.getString("email"));
            a.add(rs.getString("f_nacim"));
            a.add(rs.getString("movil"));
            a.add(rs.getString("sexo"));
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        query = "SELECT * FROM clientes " + "WHERE IDCLIENTE = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            a.add(rs.getString("ciudad"));
            a.add(rs.getString("minusvalias"));
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    public static boolean esCliente(String dni) {
        boolean existe = false;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection con = pool.getConnection();
            PreparedStatement psCliente = con.prepareStatement("SELECT * FROM clientes WHERE idCliente=?");
            psCliente.setString(1, dni);
            ResultSet rsCliente = psCliente.executeQuery();
            existe = (rsCliente.next());
            rsCliente.close();
            psCliente.close();
            pool.freeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    public static Cliente getCliente(String dni) {
        Cliente cliente = null;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection con = pool.getConnection();
            PreparedStatement psCliente = con.prepareStatement("SELECT * FROM clientes WHERE idCliente=?");
            psCliente.setString(1, dni);
            ResultSet rsCliente = psCliente.executeQuery();
            cliente = new Cliente();
            while (rsCliente.next()) {
                cliente.setDireccion(rsCliente.getString(2));
                cliente.setMinusvalias(rsCliente.getString(3));
            }
            rsCliente.close();
            psCliente.close();
            pool.freeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ConnectionPool pool1 = ConnectionPool.getInstance();
            Connection con = pool1.getConnection();
            PreparedStatement psCliente1 = con.prepareStatement("SELECT * FROM usuarios WHERE dni=?");
            psCliente1.setString(1, dni);
            ResultSet rsCliente1 = psCliente1.executeQuery();
            cliente.setDni(dni);
            while (rsCliente1.next()) {
                cliente.setNombre(rsCliente1.getString(2));
                cliente.setAp1(rsCliente1.getString(3));
                cliente.setAp2(rsCliente1.getString(4));
                cliente.setContra(rsCliente1.getString(5));
                cliente.setEmail(rsCliente1.getString(6));
                cliente.setfNac(rsCliente1.getString(7));
                cliente.setMovil(rsCliente1.getString(8));
                cliente.setSexo(rsCliente1.getString(9));
                
            }
            rsCliente1.close();
            psCliente1.close();
            pool1.freeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }    
}