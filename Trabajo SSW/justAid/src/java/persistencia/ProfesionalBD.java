/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

/**
 *
 * @author Enrique
 */
import modelo.Profesional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProfesionalBD {

    public static boolean compruebaNifProfesional(String nif) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conexion = pool.getConnection();
        boolean correcto = false;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM profesionales U WHERE U.IDPROF=?");
            ps.setString(1, nif);
            ResultSet rs = ps.executeQuery();
            correcto = rs.next();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        pool.freeConnection(conexion);
        return correcto;
    }

    public static void insert(Profesional c) {
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
        }
        try {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO profesionales VALUES(?,?)");
            ps.setString(1, c.getDni());
            ps.setString(2, c.getProfesion());
            int res = ps.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        pool.freeConnection(conexion);
    }

    public static void deleteProfesional(String dni) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "DELETE FROM profesionales WHERE IDPROF = '" + dni + "'";
        try {
            ps = connection.prepareStatement(query);
            ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionPool pool1 = ConnectionPool.getInstance();
        Connection connection1 = pool1.getConnection();
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        query = "DELETE FROM usuarios WHERE DNI = '" + dni + "'";
        try {
            ps1 = connection1.prepareStatement(query);
            ps1.executeUpdate();
            ps1.close();
            pool1.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int contarProfesionales() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM profesionales";
        int num = 0;
        try {
            ps = connection.prepareStatement(query);
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
                ++num;
            }
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        pool.freeConnection(connection);
        return num;
    }

    public static ArrayList<String> getDni() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> dev = new ArrayList<>();
        String query = "SELECT IDPROF FROM profesionales";
        try {
            ps = connection.prepareStatement(query);
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
                dev.add(rs.getString(1));
            }
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        pool.freeConnection(connection);
        return dev;
    }

    public static String getNombre(String dni) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet rs = null;
        String dev = " ";
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT NOMBRE FROM usuarios WHERE DNI = ?");
            ps.setString(1, dni);
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
                dev = rs.getString(1);
            }
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        pool.freeConnection(connection);
        return dev;
    }

    public static boolean dniExists(String dni) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT DNI FROM profesionales " + "WHERE IDPROF = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            boolean res = rs.next();
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean esProf(String dni) {
        boolean existe = false;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection con = pool.getConnection();
            PreparedStatement psProf = con.prepareStatement("SELECT * FROM profesionales WHERE idProf=?");
            psProf.setString(1, dni);
            ResultSet rsProf = psProf.executeQuery();
            existe = (rsProf.next());
            rsProf.close();
            psProf.close();
            pool.freeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    public static Profesional getProf(String dni) {
        Profesional prof = null;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection con = pool.getConnection();
            PreparedStatement psProf = con.prepareStatement("SELECT * FROM profesionales WHERE idProf=?");
            psProf.setString(1, dni);
            ResultSet rsProf = psProf.executeQuery();
            prof = new Profesional();
            while (rsProf.next()) {
                prof.setProfesion(rsProf.getString(2));
            }
            rsProf.close();
            psProf.close();
            pool.freeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ConnectionPool pool1 = ConnectionPool.getInstance();
            Connection con = pool1.getConnection();
            PreparedStatement psProf1 = con.prepareStatement("SELECT * FROM usuarios WHERE dni=?");
            psProf1.setString(1, dni);
            ResultSet rsProf1 = psProf1.executeQuery();
            prof.setDni(dni);
            while (rsProf1.next()) {
                prof.setNombre(rsProf1.getString(2));
                prof.setAp1(rsProf1.getString(3));
                prof.setAp2(rsProf1.getString(4));
                prof.setContra(rsProf1.getString(5));
                prof.setEmail(rsProf1.getString(6));
                prof.setfNac(rsProf1.getString(7));
                prof.setMovil(rsProf1.getString(8));
                prof.setSexo(rsProf1.getString(9));

            }
            rsProf1.close();
            psProf1.close();
            pool1.freeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prof;
    }

    public static ArrayList<Profesional> getProfesionalesPorProfesion(String profesion) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conexion = pool.getConnection();
        ArrayList<Profesional> listaProfesionales = new ArrayList<>();
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM usuarios JOIN profesionales ON usuarios.DNI=profesionales.idProf WHERE profesion=?");
            ps.setString(1, profesion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Profesional prof = new Profesional(rs.getString("nombre"), rs.getString("apellido1"), rs.getString("apellido2"), rs.getString("contraseña"),
                        rs.getString("email"), rs.getString("f_nacim"), rs.getString("movil"), rs.getString("DNI"), rs.getString("sexo"), rs.getString("profesion"));
                listaProfesionales.add(prof);
            }
            return listaProfesionales;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaProfesionales;
    }
}
