package es.uva.eii.ds.empresaX.negocio.controladoresCasoUso;

import com.google.gson.JsonObject;
import es.uva.eii.ds.empresaX.negocio.modelos.Empleado;
import es.uva.eii.ds.empresaX.negocio.modelos.Sesion;
import es.uva.eii.ds.empresaX.persistencia.FachadaPersistenciaEmpleado;
import es.uva.eii.ds.empresaX.servicioscomunes.JSONHelper;
import es.uva.eii.ds.empresaX.servicioscomunes.MessageException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ControladorCUIdentificarse {
    
    /**
     * Identifica un empleado con el dni y password proporcionados. Si se identifica
     * con éxito, devuelve null. Si no, devuelve el mensaje de error correspondiente.
     * 
     * @param dni DNI del empleado
     * @param password Password del empleado
     * @throws es.uva.eii.ds.empresaX.servicioscomunes.MessageException Si ha ocurrido un error
     */
    public void identificarEmpleado(String dni, String password) throws MessageException {
        JsonObject jCreds = new JsonObject();
        
        try {
            jCreds.addProperty(JSONHelper.JSON_DNI, dni);
            jCreds.addProperty(JSONHelper.JSON_PASSWORD, obtenerSHA256(password));
            String resultado = FachadaPersistenciaEmpleado.consultaEmpleadoPorLoginYPassword(jCreds.toString());
            
            // Si no ha saltado excepción, login correcto
            Empleado e = new Empleado(resultado);
            if(!e.estaActivo()) {
                throw new MessageException("Empleado inactivo");
            }
            
            Sesion.getInstancia().setEmpleado(new Empleado(resultado));
        } catch (NoSuchAlgorithmException ex) {
            throw new MessageException("Error al generar el SHA-2 de la contraseña.");
        }
    }
    
    /**
     * Devuelve el hash SHA-256 para una cadena de entrada.
     * @param str Entrada
     * @return Hash correspondiente
     * @throws NoSuchAlgorithmException 
     */
    public static String obtenerSHA256(String str) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        
        return hexString.toString();
    }
    
    
    /*****     SINGLETON     *****/
    /**
     * Devuelve una instancia única para la clase.
     * @return Instancia única
     */
    private static ControladorCUIdentificarse instancia;
    public static ControladorCUIdentificarse getInstanciaSingleton() {
        if(instancia == null){
            instancia = new ControladorCUIdentificarse();
            }
        
        return instancia;
    }
    
}
