package es.uva.eii.ds.empresaX.negocio.controladoresCasoUso;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import es.uva.eii.ds.empresaX.negocio.modelos.Factura;
import es.uva.eii.ds.empresaX.persistencia.FachadaPersistenciaEncargado;
import es.uva.eii.ds.empresaX.servicioscomunes.JSONHelper;
import es.uva.eii.ds.empresaX.servicioscomunes.MessageException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControladorCUConsultarFacturas {
    
    /**
     * Devuelve la lista de facturas pendientes para los datos introducidos.
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @param proveedor Proveedor (vacío para cualquiera)
     * @return Lista de facturas pendientes
     */
    public ArrayList<Factura> obtenerFacturasPendientes(LocalDate fechaInicio, 
                                                                 LocalDate fechaFin, 
                                                                 String proveedor) {
        ArrayList<Factura> pendientes = new ArrayList<>();
        
        // Obtiene el JSON de la BD
        try {
            // Genera el JSON de los parámetros
            JsonObject filtros = new JsonObject();
            filtros.addProperty(JSONHelper.JSON_FECHA_INICIO, fechaInicio.toString());
            filtros.addProperty(JSONHelper.JSON_FECHA_FIN, fechaFin.toString());
            filtros.addProperty(JSONHelper.JSON_PROVEEDOR, proveedor);
            String jsonFacturas = FachadaPersistenciaEncargado.
                                getFacturasPendientesDePago(filtros.toString());
        
            // Genera los objetos
            JsonObject jo = new Gson().fromJson(jsonFacturas, JsonObject.class);
            JsonArray facturasPendientes = jo.getAsJsonArray(JSONHelper.JSON_FACTURAS_PENDIENTES);
            for(JsonElement factura : facturasPendientes) {
                pendientes.add(new Factura(factura.toString()));
            }
        } catch (MessageException e) {
            System.err.println("[!] Error al obtener facturas pendientes:\n\t" + e.getMessage());
            // Devuelve la lista de pendientes vacía
        }
        
        return pendientes;
    }
    
    /**
     * Comprueba si existe un proveedor a partir de su nombre.
     * @param proveedor Nombre del proveedor
     * @return true si existe, false si no
     * @throws es.uva.eii.ds.empresaX.servicioscomunes.MessageException
     */
    public static boolean existeProveedor(String proveedor) throws MessageException {
        return FachadaPersistenciaEncargado.existeProveedor(proveedor);
    }
    
    /**
     * Devuelve el año de la primera factura.
     *
     * @return Año de la primera factura
     */
    public static int getMinAnioFacturas() {
        int res = 2000;
        
        try {
            res = FachadaPersistenciaEncargado.getMinAnioFacturas();
        } catch(Exception e) { }
        
        return res;
    }
    
    /**
     * Devuelve el año de la ultima factura.
     *
     * @return Año de la última factura
     */
    public static int getMaxAnioFacturas() {
        int res = LocalDate.now().getYear();
        
        try {
            res = FachadaPersistenciaEncargado.getMaxAnioFacturas();
        } catch(Exception e) { }
        
        return res;
    }
    
    
    /*****     SINGLETON     *****/
    /**
     * Devuelve una instancia única para la clase.
     * @return Instancia única
     */
    private static ControladorCUConsultarFacturas instancia;
    public static ControladorCUConsultarFacturas getInstanciaSingleton() {
        if(instancia == null){
            instancia = new ControladorCUConsultarFacturas();
            }
        
        return instancia;
    }
    
}
