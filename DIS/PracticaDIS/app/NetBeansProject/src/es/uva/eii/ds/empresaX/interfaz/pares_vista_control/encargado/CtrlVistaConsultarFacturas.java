package es.uva.eii.ds.empresaX.interfaz.pares_vista_control.encargado;

import es.uva.eii.ds.empresaX.interfaz.GestorUI;
import es.uva.eii.ds.empresaX.negocio.controladoresCasoUso.ControladorCUConsultarFacturas;
import es.uva.eii.ds.empresaX.negocio.modelos.Factura;
import es.uva.eii.ds.empresaX.servicioscomunes.MessageException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * Controlador de la vista de consulta de facturas pendientes de pago.
 * 
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class CtrlVistaConsultarFacturas {
    
    private final VistaConsultarFacturas vista;
    
    /**
     * Inicializa el controlador.
     * @param v Vista que controla
     */
    public CtrlVistaConsultarFacturas(VistaConsultarFacturas v) {
        vista = v;
        // Deshabilita el botón de consultar
        vista.deshabilitaBotonConsultar();
        // Centra en la pantalla
        vista.setLocationRelativeTo(null);
    }
    
    /**
     * Carga los años para los que pueden existir facturas, buscando el año más bajo
     * y el más alto.
     */
    public void cargaAnios() {
        // Consulta en la BD el año más bajo y el más alto
        int minAnio = ControladorCUConsultarFacturas.getMinAnioFacturas();
        int maxAnio = ControladorCUConsultarFacturas.getMaxAnioFacturas();
        
        // Obtiene los años entre medias y los mete en un array
        String[] anios = new String[maxAnio-minAnio+1];
        for(int i = minAnio; i <= maxAnio; i++) {
            anios[i-minAnio] = Integer.toString(i);
        }
        
        // Se los manda a la vista
        vista.cambiaAnios(anios);
    }
    
    /**
     * Procesa el evento de click en la casilla de selección de facturas pendientes
     * en el año actual. Inhabilita los selectores de fechas.
     */
    public void procesaClickAnioActual() {
        if(vista.estaMarcadaAnioActual()) {
            // Marcada -> inhabilita la selección de fechas
            vista.inhabilitaFechas();
            vista.desmarcaTodas();       // Desmarca la otra opción
            vista.focusProveedor(false); // Solo focus
        } else {
            // Desmarcada -> habilita la selección de fechas
            vista.habilitaFechas();
        }
    }
    
    /**
     * Procesa el evento de click en la casilla de selección de todas las 
     * facturas pendientes independientemente de la fecha de emisión.
     */
    public void procesaClickTodas() {
        if(vista.estaMarcadaTodas()) {
            // Marcada -> inhabilita la selección de fechas
            vista.inhabilitaFechas();
            vista.desmarcaAnioActual();  // Desmarca la otra opción
            vista.focusProveedor(false); // Solo focus
        } else {
            // Desmarcada -> habilita la selección de fechas
            vista.habilitaFechas();
        }
    }
    
    /**
     * Procesa el evento de click en la casilla de selección de facturas
     * de cualquier proveedor.
     */
    public void procesaClickProveedor(){
        if(vista.estaMarcadaCualquier()) {
            // Marcada -> inhabilita la entrada de un proveedor
            vista.inhabilitaProveedor();
            vista.clearProveedor(); // Borra cualquier contenido
            vista.habilitaBotonConsultar();
        } else {
            // Desmarcada -> habilita la entrada de un proveedor
            vista.habilitaProveedor();
            vista.focusProveedor(false); // Solo focus
        }
    }
    
    
    /**
     * Procesa el evento de click en el botón de generación de la consulta.
     */
    public void procesaClickConsultar() {
        vista.ocultaErrorFechas();
        vista.ocultaErrorProveedor();
        vista.limpiaLista();
        
        boolean anioActual = vista.estaMarcadaAnioActual();
        boolean todas = vista.estaMarcadaTodas();
        boolean cualquier = vista.estaMarcadaCualquier();
        
        // Primero obtiene las fechas con las que tratar
        LocalDate fechaI;
        LocalDate fechaF;
        if(anioActual) {
            // Opción de facturas del año actual marcada.
            int currentYear = LocalDate.now().getYear();
            fechaI = LocalDate.of(currentYear, 1, 1);    // Primer día del año
            fechaF = LocalDate.of(currentYear, 12, 31);  // Último día del año
        } else if(todas) {
            // Opción de todas las facturas
            int anioMin = ControladorCUConsultarFacturas.getMinAnioFacturas();
            int anioMax = ControladorCUConsultarFacturas.getMaxAnioFacturas();
            fechaI = LocalDate.of(anioMin, 1, 1);
            fechaF = LocalDate.of(anioMax, 12, 31);
        } else {
            // Obtiene las fechas de la vista y comprueba que son válidas
            try {
                fechaI = LocalDate.of(vista.getAnioInicio(), 
                                      vista.getMesInicio(), 
                                      vista.getDiaInicio());
            } catch (DateTimeException e) {
                // La fecha de inicio no es válida
                vista.muestraErrorFechas("La fecha de inicio no es válida");
                return;
            }
            try {
                fechaF = LocalDate.of(vista.getAnioFin(), 
                                      vista.getMesFin(), 
                                      vista.getDiaFin());
            } catch (DateTimeException e) {
                // La fecha de fin no es válida
                vista.muestraErrorFechas("La fecha de fin no es válida");
                return;
            }
            
            // Comprueba que las fechas están ordenadas
            if(fechaF.isBefore(fechaI)) {
                vista.muestraErrorFechas("La fecha de fin debe ser posterior a la de inicio");
                return;
            }
        }
        
        // Luego obtiene el proveedor
        String proveedor = "";
        if(!cualquier) {
            proveedor = vista.getProveedor();
            if(proveedor == null || proveedor.isEmpty()) {
                vista.muestraErrorProveedor("Introduce un proveedor");
                return;
            }
            
            // Comprueba que existe el proveedor
            try {
                if(!ControladorCUConsultarFacturas.existeProveedor(proveedor)) {
                    vista.muestraErrorProveedor("Proveedor no existente");
                    return;
                }
            } catch (MessageException e) {
                vista.muestraErrorProveedor("Error inesperado");
                System.err.println(e.getMessage());
                return;
            }
        }
        
        // Finalmente le pasa los datos al controlador del CU y obtiene las facturas
        ArrayList<Factura> facturasPendientes = 
                ControladorCUConsultarFacturas.getInstanciaSingleton().
                        obtenerFacturasPendientes(fechaI, fechaF, proveedor);
        
        
        // Le manda a la vista mostrar las facturas
        vista.muestraFacturasPendientes(facturasPendientes);
    }
    
    
    /**
     * Procesa el evento de cambio de mes de inicio de la búsqueda, actualizando
     * los días válidos para dicho mes.
     */
    public void procesaCambioFechaInicio() {
        int mes = vista.getMesInicio();
        int anio = vista.getAnioInicio();
        vista.cambiaDiasInicio(getDiasMes(mes, anio));
    }
    
    /**
     * Procesa el evento de cambio de mes de fin de la búsqueda, actualizando
     * los días válidos para dicho mes.
     */
    public void procesaCambioFechaFin() {
        int mes = vista.getMesFin();
        int anio = vista.getAnioFin();
        vista.cambiaDiasFin(getDiasMes(mes, anio));
    }
    
    /**
     * Procesa un cambio en el input del proveedor. Quita el mensaje de error.
     */
    public void procesaCambioProveedor() {
        vista.ocultaErrorProveedor();
        if(vista.getProveedor().isEmpty()) {
            vista.deshabilitaBotonConsultar();
        } else {
            vista.habilitaBotonConsultar();
        }
    }
    
    /**
     * Vuelve a la vista anterior.
     */
    public void procesaClickAtras() {
        GestorUI.getInstanciaSingleton().atras();
    }
    
    /**
     * Devuelve un array de enteros con los días del mes especificado. Tiene en
     * cuenta si el año es bisiesto.
     * @param mes Mes deseado (1-12)
     * @return Dias del mes en array
     */
    private String[] getDiasMes(int mes, int anio) {
        int n;
        switch (mes) {
            case 4:
            case 6:
            case 9:
            case 11:
                n = 30;
                break;
            case 2:
                if(esBisiesto(anio)) {
                    n = 29;
                } else {
                    n = 28;
                }
                break;
            default:
                n = 31;
                break;
        }
        
        String[] dias = new String[n];
        for(int i = 0; i < n; i++) {
            dias[i] = Integer.toString(i+1);
        }
        
        return dias;
    }
    
    /**
     * Devuelve true si el año especificado es bisiesto.
     * @param anio Año
     * @return True si es bisiesto
     */
    private boolean esBisiesto(int anio) {
        return (anio % 4 == 0) && ( (anio % 100 != 0) || (anio % 400 == 0) );
    }
}
