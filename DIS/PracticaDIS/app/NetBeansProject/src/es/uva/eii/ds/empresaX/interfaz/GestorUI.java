package es.uva.eii.ds.empresaX.interfaz;

import es.uva.eii.ds.empresaX.interfaz.pares_vista_control.dependiente.VistaRegistrarVentaDirecta;
import es.uva.eii.ds.empresaX.interfaz.pares_vista_control.empleado.VistaIdentificarse;
import es.uva.eii.ds.empresaX.interfaz.pares_vista_control.empleado.VistaListaOpciones;
import es.uva.eii.ds.empresaX.interfaz.pares_vista_control.empleadohorno.CtrlVistaPrepararPedidoHorno;
import es.uva.eii.ds.empresaX.interfaz.pares_vista_control.empleadohorno.VistaPrepararPedidoHorno;
import es.uva.eii.ds.empresaX.interfaz.pares_vista_control.encargado.VistaConsultarFacturas;
import es.uva.eii.ds.empresaX.negocio.modelos.Sesion;
import es.uva.eii.ds.empresaX.negocio.modelos.TipoRol;
import java.util.EnumMap;
import java.util.Stack;
import javax.swing.JFrame;

/**
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class GestorUI { 
    private final Stack<JFrame> anteriores;
    private JFrame actual;
    
    private static GestorUI instancia;
    private EnumMap<CasosDeUso, String> stringsCasos;
    
    // Enumerado de casos de uso
    public enum CasosDeUso {
        // SUPERVISOR
        // ENCARGADO
        COMPROBAR_TRANSFERENCIAS,
        CONSULTAR_FACTURAS_PENDIENTES,
        // EMPLEADO DE HORNO
        PREPARAR_PEDIDO_HORNO,
        INFORMAR_PREVISION_MATERIA,
        // DEPENDIENTE
        ENTREGAR_PEDIDO_CLIENTE, 
        REGISTRAR_VENTA_DIRECTA
    }
    
    private GestorUI() {
        anteriores = new Stack<>();
        configurarMapaCU();
        // Muestra la pantalla principal
        vistaIdentificarse();
        //vistaConsultarFacturas();
    }
    
    /**
     * Configura el mapa entre los casos de uso y el texto que aparece
     * en el botón asociado.
     */
    private void configurarMapaCU() {
        stringsCasos = new EnumMap<>(CasosDeUso.class);
        
        // SUPERVISOR
        // ENCARGADO
        stringsCasos.put(CasosDeUso.COMPROBAR_TRANSFERENCIAS, 
                    "<html><center>COMPROBAR<br>TRANSFERENCIAS</center></html>");
        stringsCasos.put(CasosDeUso.CONSULTAR_FACTURAS_PENDIENTES, 
                    "<html><center>CONSULTAR FACTURAS<br>PENDIENTES DE PAGO</center></html>");
        // EMPLEADO DE HORNO
        stringsCasos.put(CasosDeUso.PREPARAR_PEDIDO_HORNO, 
                    "<html><center>PREPARAR PEDIDO<br>DE HORNO</center></html>");
        stringsCasos.put(CasosDeUso.INFORMAR_PREVISION_MATERIA, 
                    "<html><center>INFORMAR DE PREVISIÓN DE<br>MATERIA PRIMA NECESARIA</center></html>");
        // DEPENDIENTE
        stringsCasos.put(CasosDeUso.ENTREGAR_PEDIDO_CLIENTE, 
                    "<html><center>ENTREGAR PEDIDO<br>A CLIENTE</center></html>");
        stringsCasos.put(CasosDeUso.REGISTRAR_VENTA_DIRECTA,
                    "<html><center>REGISTRAR<br>VENTA DIRECTA</center></html>");
    }
    
    /**
     * Devuelve la string asociada al enumerado de CU.
     * @param cu Caso de uso
     * @return Cadena asociada
     */
    public String getStringCU(CasosDeUso cu) {
        return stringsCasos.get(cu);
    }
    
            /***********************************
             *              VISTAS             *
             ***********************************/
    /**
     * Gestiona la vista de una opción.
     * @param opcion 
     */
    public void gestionaVistaOpcion(GestorUI.CasosDeUso opcion) {
        switch(opcion) {
            // ENCARGADO
            case COMPROBAR_TRANSFERENCIAS:
                vistaComprobarTransferencias();
                break;
            case CONSULTAR_FACTURAS_PENDIENTES:
                vistaConsultarFacturas();
                break;
            // EMPLEADO DE HORNO
            case PREPARAR_PEDIDO_HORNO:
                vistaPrepararPedido();
                break;
            case INFORMAR_PREVISION_MATERIA:
                vistaInformarPrevision();
                break;
            // DEPENDIENTE
            case ENTREGAR_PEDIDO_CLIENTE:
                vistaEntregarPedido();
                break; 
            case REGISTRAR_VENTA_DIRECTA:
                vistaRegistrarVenta();
                break;
        }
    }
    
    /**
     * Muestra la ventana con el formulario de conexión de los empleados.
     */
    private void vistaIdentificarse() {
        guardaActual();
        
        Sesion.reset(); // Resetea la sesión
        
        java.awt.EventQueue.invokeLater(() -> {
            actual = new VistaIdentificarse();
            actual.setVisible(true);
        });  
    }
    
    /**
     * Muestra la ventana con la lista de opciones del usuario según su rol.
     * @param rol Rol del usuario
     */
    public void empleadoIdentificado(TipoRol rol) {
        guardaActual();
        java.awt.EventQueue.invokeLater(() -> {
            actual = new VistaListaOpciones(rol);
            actual.setVisible(true);
        }); 
    }
    
    //////////////////// DEPENDIENTE ////////////////////
    /**
     * Muestra la ventana para entregar un pedido a un cliente.
     */
    private void vistaEntregarPedido() {
        guardaActual();
       
        java.awt.EventQueue.invokeLater(() -> {
            actual = new VistaNoImplementada();                     // -------------------------- TODO
            actual.setVisible(true);
        });
    }
    
    /**
     * Muestra la ventana para registrar una venta directa.
     */
    private void vistaRegistrarVenta() {
        guardaActual();
        java.awt.EventQueue.invokeLater(() -> {
            actual = new VistaRegistrarVentaDirecta();
            actual.setVisible(true);
        });
    }
    
    
    ////////////////// EMPLEADO DE HORNO ////////////////
    /**
     * Muestra la ventana para preparar un pedido..
     */
    private void vistaPrepararPedido() {
        guardaActual();
        java.awt.EventQueue.invokeLater(() -> {
            actual = new VistaPrepararPedidoHorno();
            actual.setVisible(true);
        });
    }
    
    /**
     * Muestra la ventana para informar de previsión de materia prima necesaria..
     */
    private void vistaInformarPrevision() {
        guardaActual();
       
        java.awt.EventQueue.invokeLater(() -> {
            actual = new VistaNoImplementada();                     // -------------------------- TODO
            actual.setVisible(true);
        });
    }
    
    
    
    ///////////////////// ENCARGADO /////////////////////
    /**
     * Muestra la ventana con la lista de facturas pendientes de pago.
     */
    private void vistaConsultarFacturas() {
        guardaActual();
       
        java.awt.EventQueue.invokeLater(() -> {
            actual = new VistaConsultarFacturas();
            actual.setVisible(true);
        });
    }
    
    /**
     * Muestra la ventana con la lista de facturas pendientes de pago.
     */
    private void vistaComprobarTransferencias() {
        guardaActual();
       
        java.awt.EventQueue.invokeLater(() -> {
            actual = new VistaNoImplementada();                     // -------------------------- TODO
            actual.setVisible(true);
        });
    }
    
    
            /***********************************
             *              GESTOR             *
             ***********************************/
    /**
     * Guarda el estado actual en la pila.
     */
    private void guardaActual() {
        if(actual != null){
            anteriores.push(actual);
            actual.setVisible(false);
        }
    }
    
    /**
     * Vuelve a la vista anterior.
     */
    public void atras() {
       actual.dispose();
       actual = anteriores.pop();
       actual.setVisible(true);
    }
    
            /***********************************
             *            SINGLETON            *
             ***********************************/
    /**
     * Devuelve una instancia única para la clase.
     * @return Instancia única
     */
    public static GestorUI getInstanciaSingleton() {
        if(instancia == null){
            instancia = new GestorUI();
        }
        
        return instancia;
    }

}
