package es.uva.eii.ds.empresaX.negocio.modelos;

import es.uva.eii.ds.empresaX.negocio.controladoresCasoUso.ControladorCUIdentificarse;

/**
 * Contiene los datos de la sesión actual.
 * 
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class Sesion {
    
    private Empleado empleado;
    
    public Sesion() {
        
    }
    
    /**
     * Devuelve el empleado identificado de la sesión.
     * @return Empleado de la sesión
     */
    public Empleado getEmpleado() {
        return empleado;
    }
    
    /**
     * Asigna un empleado a la sesión.
     * @param empleado Empleado conectado
     */
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    /**
     * Resetea la sesión.
     */
    public static void reset() {
        instancia = new Sesion();
    }
    
    
    /*****     SINGLETON     *****/
    /**
     * Devuelve una instancia única para la clase.
     * @return Instancia única
     */
    private static Sesion instancia;
    public static Sesion getInstancia() {
        if(instancia == null){
            instancia = new Sesion();
        }
        
        return instancia;
    }
    
}
