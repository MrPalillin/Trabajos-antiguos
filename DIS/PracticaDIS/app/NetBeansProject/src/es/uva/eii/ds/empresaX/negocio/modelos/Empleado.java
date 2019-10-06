package es.uva.eii.ds.empresaX.negocio.modelos;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import es.uva.eii.ds.empresaX.servicioscomunes.JSONHelper;
import java.time.LocalDate;
import java.util.TreeMap;

/**
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class Empleado {   
    private String dni;
    private String nombre;
    private String apellidos;
    private LocalDate fechaInicioEnEmpresa;
    private TreeMap<LocalDate, Rol> rolesEnLaEmpresa;
    private TreeMap<LocalDate, VinculacionConLaEmpresa> estadoDeVinculacion;
    private TreeMap<LocalDate, Disponibilidad> estadoDeDisponibilidad;
    
    /**
     * Construye un objeto Empleado a partir de una cadena JSON.
     * 
     * @param jsonEmpleado Cadena JSON
     */
    public Empleado(String jsonEmpleado) {
        try {
            JsonObject jo = new Gson().fromJson(jsonEmpleado, JsonObject.class);
            // NOMBRE, APELLIDOS Y DNI
            nombre = jo.get(JSONHelper.JSON_NOMBRE).getAsString();
            apellidos = jo.get(JSONHelper.JSON_APELLIDOS).getAsString();
            dni = jo.get(JSONHelper.JSON_DNI).getAsString();
            // FECHA DE INICIO
            String[] fechaI = jo.get(JSONHelper.JSON_FECHA_INICIO).getAsString().split("-");
            fechaInicioEnEmpresa = LocalDate.of(
                    Integer.valueOf(fechaI[0]), // YYYY
                    Integer.valueOf(fechaI[1]), // MM
                    Integer.valueOf(fechaI[2])  // DD
            );
            
            configuraRoles(jo);
            configuraVinculaciones(jo);
            configuraDisponibilidades(jo);
        } catch(JsonSyntaxException | NumberFormatException e) {
            // Especificar excepciones
            System.out.println("[!] Excepción al crear Empleado:");
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Obtiene los roles y los añade a la lista.
     * @param jo Objeto JSON
     */
    private void configuraRoles(JsonObject jo) {
        rolesEnLaEmpresa = new TreeMap<>();
        JsonArray jRoles = jo.getAsJsonArray(JSONHelper.JSON_ROLES);
        for(JsonElement jr : jRoles) {
            JsonObject jRol = new Gson().fromJson(jr.toString(), JsonObject.class);
            String[] fechaComienzo = jRol.get(JSONHelper.JSON_COMIENZO).getAsString().split("-");
            LocalDate comienzo = LocalDate.of(
                Integer.valueOf(fechaComienzo[0]), // YYYY
                Integer.valueOf(fechaComienzo[1]), // MM
                Integer.valueOf(fechaComienzo[2])  // DD
            );
            Rol rol = new Rol(TipoRol.valueOf(jRol.get(JSONHelper.JSON_ROL).getAsString()));
            rolesEnLaEmpresa.put(comienzo, rol);
        }
    }
    
    /**
     * Obtiene las vinculaciones y las añade a la lista.
     * @param jo Objeto JSON
     */
    private void configuraVinculaciones(JsonObject jo) {
        estadoDeVinculacion = new TreeMap<>();
        JsonArray jVinculaciones = jo.getAsJsonArray(JSONHelper.JSON_VINCULACIONES);
        for(JsonElement jv : jVinculaciones) {
            JsonObject jVinculacion = new Gson().fromJson(jv.toString(), JsonObject.class);
            String[] fechaComienzo = jVinculacion.get(JSONHelper.JSON_COMIENZO).getAsString().split("-");
            LocalDate comienzo = LocalDate.of(
                Integer.valueOf(fechaComienzo[0]), // YYYY
                Integer.valueOf(fechaComienzo[1]), // MM
                Integer.valueOf(fechaComienzo[2])  // DD
            );
            VinculacionConLaEmpresa vinculacion = new VinculacionConLaEmpresa(TipoVinculacion.valueOf(jVinculacion.get(JSONHelper.JSON_VINCULACION).getAsString()));
            estadoDeVinculacion.put(comienzo, vinculacion);
        }
    }
    
    /**
     * Obtiene las disponibilidades y las añade a la lista.
     * @param jo Objeto JSON
     */
    private void configuraDisponibilidades(JsonObject jo) {
        estadoDeDisponibilidad = new TreeMap<>();
        JsonArray jDisponibilidades = jo.getAsJsonArray(JSONHelper.JSON_DISPONIBILIDADES);
        for(JsonElement jd : jDisponibilidades) {
            JsonObject jDisponibilidad = new Gson().fromJson(jd.toString(), JsonObject.class);
            String[] fechaComienzo = jDisponibilidad.get(JSONHelper.JSON_COMIENZO).getAsString().split("-");
            LocalDate comienzo = LocalDate.of(
                Integer.valueOf(fechaComienzo[0]), // YYYY
                Integer.valueOf(fechaComienzo[1]), // MM
                Integer.valueOf(fechaComienzo[2])  // DD
            );
            Disponibilidad disponibilidad = new Disponibilidad(TipoDisponibilidad.valueOf(jDisponibilidad.get(JSONHelper.JSON_DISPONIBILIDAD).getAsString()));
            estadoDeDisponibilidad.put(comienzo, disponibilidad);
        }
    }
    

    /**
     * Devuelve el DNI del empleado, que actúa como identificador único.
     * @return DNI del empleado
     */
    public String getDni() {
        return dni;
    }
    
    /**
     * Devuelve el nombre del empleado.
     * @return Nombre del empleado
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Devuelve los apellidos del empleado.
     * @return Apellidos del empleado
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Devuelve la fecha de inicio en la empresa.
     * @return Fecha de inicio en la empresa
     */
    public LocalDate getFechaInicioEnEmpresa() {
        return fechaInicioEnEmpresa;
    }
    
    /**
     * Devuelve true si el empleado está actualmente activo, false si no.
     * @return true si está activo
     */
    public boolean estaActivo() {
        boolean activoDisponible = false;
        boolean activoVinculacion = false;
        if(estadoDeDisponibilidad.size() > 0 && estadoDeVinculacion.size() > 0) {
            activoDisponible  = estadoDeDisponibilidad.lastEntry().
                                        getValue().estaEnActivo();
            activoVinculacion = estadoDeVinculacion.lastEntry().
                                        getValue().estaEnActivo();
        }
        
        return activoDisponible && activoVinculacion;
    }
    
    /**
     * Devuelve el rol actual del empleado (el último).
     * @return Rol actual
     */
    public Rol obtenerRolActual() {
        return rolesEnLaEmpresa.lastEntry().getValue();
    }
    
}
