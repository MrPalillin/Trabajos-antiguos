package es.uva.eii.ds.empresaX.persistencia;

import es.uva.eii.ds.empresaX.servicioscomunes.MessageException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;

/**
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class ConexionBD {
    
    private final String url;
    private final String usuario;
    private final String password;
    private Connection connection;
    
    private ConexionBD() throws ClassNotFoundException, SQLException, MessageException {
        url = "jdbc:derby://localhost:1527/bd_pasteleria";
        usuario = "temp";
        password = "temp";
        
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        try {
            openConnection();
        } catch(SQLNonTransientConnectionException ex) {
            throw new MessageException("No hay conexión con la BD");
        }
    }
    
    
    public void openConnection() throws SQLException {
        connection = DriverManager.getConnection(url, usuario, password);
    }
    
    public void closeConnection() throws SQLException {
        connection.close();
    }
    
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    /** Alcance de clase **/
    private static ConexionBD theInstance;
    public static ConexionBD getInstancia() throws ClassNotFoundException, SQLException, MessageException {
        if(theInstance == null){
            theInstance = new ConexionBD();
        }
        
        return theInstance;
    }
    
}