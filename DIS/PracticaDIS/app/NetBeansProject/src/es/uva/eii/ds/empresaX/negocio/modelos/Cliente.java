package es.uva.eii.ds.empresaX.negocio.modelos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import es.uva.eii.ds.empresaX.servicioscomunes.JSONHelper;

/**
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class Cliente {
    private String dni;
    private String nombre;
    private String apellidos;
    private int telefono;
    private String email;

    
    public Cliente(String jsonString) {
        try {
            JsonObject jo = new Gson().fromJson(jsonString, JsonObject.class);
            
            // Configura los atributos
            dni = jo.get(JSONHelper.JSON_DNI).getAsString();
            nombre = jo.get(JSONHelper.JSON_NOMBRE).getAsString();
            apellidos = jo.get(JSONHelper.JSON_APELLIDOS).getAsString();
            telefono = jo.get(JSONHelper.JSON_TELEFONO).getAsInt();
            email = jo.get(JSONHelper.JSON_EMAIL).getAsString();
        } catch(JsonSyntaxException | NumberFormatException e) {
            // Especificar excepciones
            System.out.println("[!] Excepción al crear Empleado:");
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public String getDni() {
        return dni;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }
    
}
