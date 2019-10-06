package persistencia;

import modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import modelo.Atencion;

public class AtencionBD {

    public static void insert(Atencion a) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conexion = pool.getConnection();
        
        try {
            PreparedStatement prs = 
                    conexion.prepareStatement("INSERT INTO atenciones " +
                    "(idClienteAtencion, idProfAtencion, hora, observaciones) VALUES(?,?,?,?)");

            prs.setString(1, a.getIdCliente());
            prs.setString(2, a.getIdProfAtencion());
            prs.setTimestamp(3, a.getHora());
            prs.setString(4, a.getObservaciones());
            prs.executeUpdate();

        } catch (SQLException s) {
            s.printStackTrace();
        }
        
        pool.freeConnection(conexion);
    }

    public static ArrayList<Atencion> getAtencionesProfesional(String idProf) {
        ArrayList<Atencion> a = new ArrayList<Atencion>();
        String idCli, obs;
        int idAt;
        Timestamp hora = null;

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM atenciones " + "WHERE idProfAtencion = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, idProf);
            rs = ps.executeQuery();

            while (rs.next()) {
                idAt = rs.getInt("idAtencion");
                idCli = rs.getString("idClienteAtencion");
                hora = rs.getTimestamp("hora");
                obs = rs.getString("observaciones");
                a.add(new Atencion(idAt, idCli, idProf, hora, obs));
            }

            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    public static Atencion getAtencion(String idAt) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        Atencion atencion = null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM atenciones WHERE idAtencion=?");
            ps.setString(1, idAt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                atencion = new Atencion(rs.getInt("idAtencion"), rs.getString("idClienteAtencion"), rs.getString("idProfAtencion"), rs.getTimestamp("hora"), rs.getString("observaciones"));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atencion;
    }

    public static Cliente getClientePorAtencion(String idAt) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        Cliente cli = null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM usuarios,clientes WHERE dni=(SELECT idClienteAtencion FROM atenciones WHERE idAtencion=?)");
            ps.setInt(1, Integer.parseInt(idAt));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cli = new Cliente(rs.getString("direccion"), rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getString("contraseña"), rs.getString("email"), rs.getString("f_nacim"), rs.getString("movil"), rs.getString("minusvalias"), rs.getString("dni"), rs.getString("sexo"));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cli;
    }

    public static void borraAtencion(int idAt) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM atenciones WHERE idAtencion=?");
            ps.setString(1, Integer.toString(idAt));
            int rs = ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
