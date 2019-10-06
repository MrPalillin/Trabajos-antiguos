package es.uva.eii.ds.empresaX.interfaz;

import javax.swing.JFrame;

/**
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class CtrlVistaNoImplementada {
    
    private final VistaNoImplementada vista;
    
    public CtrlVistaNoImplementada(VistaNoImplementada v) {
        vista = v;
        // Evita que termine el programa al cerrar la ventana
        vista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Centra en la pantalla
        vista.setLocationRelativeTo(null);
    }
    
    /**
     * Vuelve a la pantalla anterior.
     */
    public void procesaCierre() {
        GestorUI.getInstanciaSingleton().atras();
    }
    
}
