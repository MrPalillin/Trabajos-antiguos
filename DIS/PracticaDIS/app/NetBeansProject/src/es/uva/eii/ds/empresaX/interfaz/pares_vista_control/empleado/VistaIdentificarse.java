package es.uva.eii.ds.empresaX.interfaz.pares_vista_control.empleado;

/**
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class VistaIdentificarse extends javax.swing.JFrame {

    private final CtrlVistaIdentificarse controlador;
    
    public VistaIdentificarse() {
        initComponents();
        controlador = new CtrlVistaIdentificarse(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lIdentificate = new javax.swing.JLabel();
        lDni = new javax.swing.JLabel();
        inputDNI = new javax.swing.JTextField();
        lPass = new javax.swing.JLabel();
        mensajeError = new javax.swing.JLabel();
        btnEnviar = new javax.swing.JButton();
        inputPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lIdentificate.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        lIdentificate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lIdentificate.setText("Identifícate");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(lIdentificate, gridBagConstraints);

        lDni.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        lDni.setText("DNI");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 15);
        getContentPane().add(lDni, gridBagConstraints);

        inputDNI.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        inputDNI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputDNI.setToolTipText("Usuario");
        inputDNI.setPreferredSize(new java.awt.Dimension(200, 26));
        inputDNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputDNIActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 30);
        getContentPane().add(inputDNI, gridBagConstraints);
        inputDNI.getAccessibleContext().setAccessibleName("Usuario");

        lPass.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        lPass.setText("CONTRASEÑA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 15);
        getContentPane().add(lPass, gridBagConstraints);

        mensajeError.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        mensajeError.setForeground(new java.awt.Color(255, 0, 0));
        mensajeError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mensajeError.setText("Credenciales inválidas");
        mensajeError.setFocusable(false);
        mensajeError.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 30, 10, 30);
        getContentPane().add(mensajeError, gridBagConstraints);

        btnEnviar.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        btnEnviar.setText("ENVIAR");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 30, 30, 30);
        getContentPane().add(btnEnviar, gridBagConstraints);

        inputPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputPassword.setMaximumSize(new java.awt.Dimension(200, 26));
        inputPassword.setMinimumSize(new java.awt.Dimension(200, 26));
        inputPassword.setPreferredSize(new java.awt.Dimension(200, 26));
        inputPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputPasswordActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 30);
        getContentPane().add(inputPassword, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        controlador.procesaEventoIdentificarse();
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void inputDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputDNIActionPerformed
        // Al pulsar enter, realiza el login
        controlador.procesaEventoIdentificarse();
    }//GEN-LAST:event_inputDNIActionPerformed

    private void inputPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputPasswordActionPerformed
        // Al pulsar enter, realiza el login
        controlador.procesaEventoIdentificarse();
    }//GEN-LAST:event_inputPasswordActionPerformed
    
    /**
     * Devuelve el contenido de la entrada del DNI.
     * @return DNI
     */
    public String getDNI() {
        return inputDNI.getText();
    }
    
    /**
     * Devuelve el contenido de la entrada de la password.
     * @return Password
     */
    public String getPassword() {
        return new String(inputPassword.getPassword());
    }
    
    /**
     * Asigna focus al campo del DNI.
     */
    public void hacerFocusDNI() {
        inputDNI.requestFocusInWindow();
    }
    
    /**
     * Asigna focus al campo del password.
     */
    public void hacerFocusPassword() {
        inputPassword.requestFocusInWindow();
    }
    
    /**
     * Muestra el mensaje de error especificado.
     * @param mensaje Mensaje de error.
     */
    public void mostrarMensajeError(String mensaje) {
        mensajeError.setText(mensaje);
        mensajeError.setVisible(true);
    }
    
    /**
     * Borra el mensaje de error.
     */
    public void borrarMensajeError() {
        // Si se utiliza setVisible, se ocupa su espacio
        mensajeError.setText("");
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JTextField inputDNI;
    private javax.swing.JPasswordField inputPassword;
    private javax.swing.JLabel lDni;
    private javax.swing.JLabel lIdentificate;
    private javax.swing.JLabel lPass;
    private javax.swing.JLabel mensajeError;
    // End of variables declaration//GEN-END:variables
}
