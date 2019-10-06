/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.eii.ds.empresaX.interfaz.pares_vista_control.dependiente;

import javax.swing.JFrame;

/**
 *
 * @author daniel
 */
public class CtrlVistaPrecioTotal {
    
    private VistaPrecioTotal vista;
    
    public CtrlVistaPrecioTotal(VistaPrecioTotal v) {
        vista = v;
        // Evita que termine el programa al cerrar la ventana
        vista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Centra en la pantalla
        vista.setLocationRelativeTo(null);
    }

}
