package es.uva.eii.ds.empresaX.interfaz.pares_vista_control.empleado;

import es.uva.eii.ds.empresaX.interfaz.GestorUI;
import es.uva.eii.ds.empresaX.negocio.modelos.TipoRol;

/**
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class CtrlVistaListaOpciones {
    
    private final VistaListaOpciones vista;
        
    public CtrlVistaListaOpciones(VistaListaOpciones v, TipoRol rol) {
        vista = v;
        // Asigna el titulo de la ventana
        vista.setTitulo(rol.toString());
        // Centra en la pantalla
        vista.setLocationRelativeTo(null);
        
        switch(rol) {
            case Supervisor:
                break;
            case Dependiente:
                anadeOpcion(GestorUI.CasosDeUso.ENTREGAR_PEDIDO_CLIENTE);
                anadeOpcion(GestorUI.CasosDeUso.REGISTRAR_VENTA_DIRECTA);
                break;
            case EmpleadoDeHorno:
                anadeOpcion(GestorUI.CasosDeUso.PREPARAR_PEDIDO_HORNO);
                anadeOpcion(GestorUI.CasosDeUso.INFORMAR_PREVISION_MATERIA);
                break;
            case Encargado:
                anadeOpcion(GestorUI.CasosDeUso.CONSULTAR_FACTURAS_PENDIENTES);
                anadeOpcion(GestorUI.CasosDeUso.COMPROBAR_TRANSFERENCIAS);
                break;
        }
    }
    
    /**
     * Añade la opción especificada a la vista.
     * @param opcion Opción de CU
     */
    public void anadeOpcion(GestorUI.CasosDeUso opcion) {
        vista.anadirOpcion(opcion, GestorUI.getInstanciaSingleton().getStringCU(opcion));
    }
    
    /**
     * Procesa el click en una de las opciones, mostrando la pantalla correspondiente.
     */
    public void procesaClickOpcion() {
        // Obtiene cuál ha sido la opción seleccionada y se la manda al gestor de interfaz
        GestorUI.getInstanciaSingleton().gestionaVistaOpcion(vista.getOpcionSeleccionada());
    }
    
    
    /**
     * Vuelve a la vista anterior.
     */
    public void procesaClickAtras() {
        GestorUI.getInstanciaSingleton().atras();
    }
    
}
