/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author Enrique
 */
public class UsuarioBD {
    public static boolean compruebaDniYContraseña(String dni, String contraseña) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conexion = pool.getConnection();
        boolean correcto = false;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM usuarios U WHERE U.DNI=? and U.CONTRASEÑA=?");
            ps.setString(1, dni);
            ps.setString(2, contraseña);
            ResultSet rs = ps.executeQuery();
            correcto = rs.next();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        pool.freeConnection(conexion);
        return correcto;
    }
    public static ArrayList<String> atributosUsuario(String dni) {
        ArrayList<String> a = new ArrayList<String>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM usuarios WHERE DNI = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            while (rs.next()) {
                a.add(rs.getString("nombre"));
                a.add(rs.getString("apellido1"));
                a.add(rs.getString("apellido2"));
                a.add(rs.getString("contrase\u00f1a"));
                a.add(rs.getString("email"));
                a.add(rs.getString("f_nacim"));
                a.add(rs.getString("movil"));
                a.add(rs.getString("sexo"));
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    public static boolean compruebaUsuario(String nif, String contra) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conexion = pool.getConnection();
        boolean correcto = false;
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM usuarios WHERE DNI=? and contraseña=?");
            ps.setString(1, nif);
            ps.setString(2, contra);
            ResultSet rs = ps.executeQuery();
            correcto = rs.next();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        pool.freeConnection(conexion);
        return correcto;
    }
     
}
