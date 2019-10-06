package es.uva.eii.ds.empresaX.interfaz.pares_vista_control.encargado;

import es.uva.eii.ds.empresaX.negocio.modelos.Factura;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class VistaConsultarFacturas extends javax.swing.JFrame {

    private final CtrlVistaConsultarFacturas controlador;

    private JPanel fillerLista; // Panel para rellenar el final de la lista
    private int y = 0; // Coordenada y actual del ultimo elemento de la lista
    
    public VistaConsultarFacturas() {
        initComponents();
        controlador = new CtrlVistaConsultarFacturas(this);
        configuraComponentes();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lTitulo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lFecha = new javax.swing.JLabel();
        checkAnioActual = new javax.swing.JCheckBox();
        checkTodas = new javax.swing.JCheckBox();
        lInicio = new javax.swing.JLabel();
        lFin = new javax.swing.JLabel();
        inputDiaI = new javax.swing.JComboBox<>();
        inputMesI = new javax.swing.JComboBox<>();
        inputAnioI = new javax.swing.JComboBox<>();
        lDiaI = new javax.swing.JLabel();
        lMesI = new javax.swing.JLabel();
        lAnioI = new javax.swing.JLabel();
        inputDiaF = new javax.swing.JComboBox<>();
        inputMesF = new javax.swing.JComboBox<>();
        inputAnioF = new javax.swing.JComboBox<>();
        lDiaF = new javax.swing.JLabel();
        lMesF = new javax.swing.JLabel();
        lAnioF = new javax.swing.JLabel();
        errorFechas = new javax.swing.JLabel();
        lProveedor = new javax.swing.JLabel();
        checkProveedor = new javax.swing.JCheckBox();
        inputProveedor = new javax.swing.JTextField();
        errorProveedor = new javax.swing.JLabel();
        btnConsultar = new javax.swing.JButton();
        cabeceraLista = new javax.swing.JPanel();
        lRealizacion = new javax.swing.JLabel();
        lEmision = new javax.swing.JLabel();
        spResultados = new javax.swing.JScrollPane();
        listaResultados = new javax.swing.JPanel();
        lNombre = new javax.swing.JLabel();
        lNumero = new javax.swing.JLabel();
        lImporte = new javax.swing.JLabel();
        btnAtras = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        lTitulo.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        lTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lTitulo.setText("Consulta de facturas pendientes");

        lFecha.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        lFecha.setText("<html><u>Según la fecha de emisión:</u></html>");

        checkAnioActual.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        checkAnioActual.setText("Facturas pendientes del año actual");
        checkAnioActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkAnioActualActionPerformed(evt);
            }
        });

        checkTodas.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        checkTodas.setText("Todas las facturas");
        checkTodas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkTodasActionPerformed(evt);
            }
        });

        lInicio.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        lInicio.setText("INICIO");

        lFin.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        lFin.setText("FIN");

        inputDiaI.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        inputDiaI.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7" }));
        inputDiaI.setMinimumSize(new java.awt.Dimension(60, 30));
        inputDiaI.setPreferredSize(new java.awt.Dimension(60, 30));

        inputMesI.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        inputMesI.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        inputMesI.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                inputMesIItemStateChanged(evt);
            }
        });

        inputAnioI.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        inputAnioI.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1998", "1999" }));
        inputAnioI.setMinimumSize(new java.awt.Dimension(80, 30));
        inputAnioI.setPreferredSize(new java.awt.Dimension(80, 30));
        inputAnioI.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                inputAnioIItemStateChanged(evt);
            }
        });

        lDiaI.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        lDiaI.setText("Dia");

        lMesI.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        lMesI.setText("Mes");

        lAnioI.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        lAnioI.setText("Año");

        inputDiaF.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        inputDiaF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7" }));
        inputDiaF.setMinimumSize(new java.awt.Dimension(60, 30));
        inputDiaF.setPreferredSize(new java.awt.Dimension(60, 30));

        inputMesF.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        inputMesF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        inputMesF.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                inputMesFItemStateChanged(evt);
            }
        });

        inputAnioF.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        inputAnioF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1998", "1999" }));
        inputAnioF.setMinimumSize(new java.awt.Dimension(80, 30));
        inputAnioF.setPreferredSize(new java.awt.Dimension(80, 30));
        inputAnioF.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                inputAnioFItemStateChanged(evt);
            }
        });

        lDiaF.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        lDiaF.setText("Dia");

        lMesF.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        lMesF.setText("Mes");

        lAnioF.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        lAnioF.setText("Año");

        errorFechas.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        errorFechas.setForeground(new java.awt.Color(255, 0, 0));
        errorFechas.setText("Mensaje de error para las fechas");

        lProveedor.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        lProveedor.setText("<html><u>Nombre de proveedor:</u></html>");

        checkProveedor.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        checkProveedor.setText("Cualquier proveedor");
        checkProveedor.setName("checkProveedor"); // NOI18N
        checkProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkProveedorActionPerformed(evt);
            }
        });

        inputProveedor.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        inputProveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputProveedorKeyReleased(evt);
            }
        });

        errorProveedor.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        errorProveedor.setForeground(new java.awt.Color(255, 0, 0));
        errorProveedor.setText("El proveedor no existe");

        btnConsultar.setBackground(new java.awt.Color(102, 255, 102));
        btnConsultar.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        btnConsultar.setText("CONSULTAR");
        btnConsultar.setFocusPainted(false);
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        lRealizacion.setFont(new java.awt.Font("Ebrima", 0, 16)); // NOI18N
        lRealizacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lRealizacion.setText("Fecha de realización");

        lEmision.setFont(new java.awt.Font("Ebrima", 0, 16)); // NOI18N
        lEmision.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lEmision.setText("Fecha de emisión");

        javax.swing.GroupLayout cabeceraListaLayout = new javax.swing.GroupLayout(cabeceraLista);
        cabeceraLista.setLayout(cabeceraListaLayout);
        cabeceraListaLayout.setHorizontalGroup(
            cabeceraListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cabeceraListaLayout.createSequentialGroup()
                .addComponent(lRealizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        cabeceraListaLayout.setVerticalGroup(
            cabeceraListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cabeceraListaLayout.createSequentialGroup()
                .addGroup(cabeceraListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lRealizacion)
                    .addComponent(lEmision))
                .addGap(5, 5, 5))
        );

        spResultados.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        spResultados.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        listaResultados.setLayout(new java.awt.GridBagLayout());
        spResultados.setViewportView(listaResultados);

        lNombre.setFont(new java.awt.Font("Ebrima", 0, 16)); // NOI18N
        lNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lNombre.setText("Proveedor");

        lNumero.setFont(new java.awt.Font("Ebrima", 0, 16)); // NOI18N
        lNumero.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lNumero.setText("Número de pedido");

        lImporte.setFont(new java.awt.Font("Ebrima", 0, 16)); // NOI18N
        lImporte.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lImporte.setText("Importe de factura");

        btnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/es/uva/eii/ds/empresaX/interfaz/arrow-left.png"))); // NOI18N
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(lDiaI)
                                    .addComponent(inputDiaI, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(lMesI)
                                    .addComponent(inputMesI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lInicio))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(lAnioI)
                                    .addComponent(inputAnioI, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(lDiaF)
                                    .addComponent(inputDiaF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(lMesF)
                                    .addComponent(inputMesF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lFin))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(lAnioF)
                                    .addComponent(inputAnioF, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(243, 243, 243)
                                .addComponent(errorFechas)))
                        .addGap(176, 176, 176)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(inputProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkProveedor)
                            .addComponent(lProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(errorProveedor)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(lNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(lImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(cabeceraLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(spResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(btnConsultar))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(checkAnioActual)
                        .addGap(30, 30, 30)
                        .addComponent(checkTodas)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(btnAtras)
                .addGap(0, 0, 0)
                .addComponent(lTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 1046, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnAtras)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkAnioActual)
                    .addComponent(checkTodas)
                    .addComponent(lProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(lFin))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(lInicio)))
                        .addGap(5, 5, 5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkProveedor)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(lAnioI))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputAnioF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputMesF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputDiaF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lDiaF)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lMesF)
                                .addComponent(lAnioF))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputAnioI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputMesI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputDiaI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lDiaI)
                            .addComponent(lMesI)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inputProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(errorProveedor)
                    .addComponent(errorFechas))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cabeceraLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lNombre)
                                .addComponent(lNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lImporte)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkAnioActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAnioActualActionPerformed
        controlador.procesaClickAnioActual();
    }//GEN-LAST:event_checkAnioActualActionPerformed

    private void checkTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkTodasActionPerformed
        controlador.procesaClickTodas();
    }//GEN-LAST:event_checkTodasActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
       controlador.procesaClickConsultar();
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void inputMesIItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_inputMesIItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED) {
            // Solo se procesa la seleccion, no la deselección
            controlador.procesaCambioFechaInicio();
        }
    }//GEN-LAST:event_inputMesIItemStateChanged

    private void inputMesFItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_inputMesFItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED) {
            // Solo se procesa la seleccion, no la deselección
            controlador.procesaCambioFechaFin();
        }
    }//GEN-LAST:event_inputMesFItemStateChanged

    private void inputAnioIItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_inputAnioIItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED) {
            // Solo se procesa la seleccion, no la deselección
            controlador.procesaCambioFechaInicio();
        }
    }//GEN-LAST:event_inputAnioIItemStateChanged

    private void inputAnioFItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_inputAnioFItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED) {
            // Solo se procesa la seleccion, no la deselección
            controlador.procesaCambioFechaFin();
        }
    }//GEN-LAST:event_inputAnioFItemStateChanged

    private void inputProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputProveedorKeyReleased
        if(evt.getKeyChar() == '\n') {
            // Enter en el campo de proveedor equivale a pulsar el botón
            btnConsultar.doClick();
        } else {
            controlador.procesaCambioProveedor();
        }
    }//GEN-LAST:event_inputProveedorKeyReleased

    private void checkProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkProveedorActionPerformed
        controlador.procesaClickProveedor();
    }//GEN-LAST:event_checkProveedorActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        controlador.procesaClickAtras();
    }//GEN-LAST:event_btnAtrasActionPerformed
    
    
    /**
     * Configura ciertos aspectos visuales a mayores.
     */
    private void configuraComponentes() {
        // Los textos de los inputs se centran
        ((JLabel) inputDiaI.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        ((JLabel) inputMesI.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        ((JLabel) inputAnioI.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        ((JLabel) inputDiaF.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        ((JLabel) inputMesF.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        ((JLabel) inputAnioF.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        // Oculta errores
        ocultaErrorFechas();
        ocultaErrorProveedor();
        // Pide al controlador que le cargue los años
        controlador.cargaAnios();
        // Lista de resultados
        configuraFillerLista();
    }
    
        /****************************************
         *          CASILLAS DE OPCIÓN          *
         ****************************************/
    /**
     * Devuelve true si está marcada la opción de facturas solo del año actual.
     * @return True si está marcada la opción
     */
    public boolean estaMarcadaAnioActual() {
        return checkAnioActual.isSelected();
    }
    
    /**
     * Devuelve true si está marcada la opción de todas las facturas independientemente
     * de la fecha de emisión.
     * @return True si está marcada la opción
     */
    public boolean estaMarcadaTodas() {
        return checkTodas.isSelected();
    }
    
    /**
     * Devuelve true si esta marcada la opcion de cualquier proveedor.
     * @return True si está marcada la opción
     */
    public boolean estaMarcadaCualquier(){
        return checkProveedor.isSelected();
    }
    
    /**
     * Desmarca la opción de las facturas del año actual.
     */
    public void desmarcaAnioActual() {
        checkAnioActual.setSelected(false);
    }
    
    /**
     * Desmarca la opción de todas las facturas del proveedor.
     */
    public void desmarcaTodas() {
        checkTodas.setSelected(false);
    }
    
    /**
     * Desmarca la opción de cualquier proveedor.
     */
    public void desmarcaProveedor() {
        checkProveedor.setSelected(false);
    }
    
    
        /****************************************
         *                FECHAS                *
         ****************************************/
    /**
     * Inhabilita los selectores de fechas.
     */
    public void inhabilitaFechas() {
        inputDiaI.setEnabled(false);
        inputMesI.setEnabled(false);
        inputAnioI.setEnabled(false);
        inputDiaF.setEnabled(false);
        inputMesF.setEnabled(false);
        inputAnioF.setEnabled(false);
    }
    
    /**
     * Habilita los selectores de fechas.
     */
    public void habilitaFechas() {
        inputDiaI.setEnabled(true);
        inputMesI.setEnabled(true);
        inputAnioI.setEnabled(true);
        inputDiaF.setEnabled(true);
        inputMesF.setEnabled(true);
        inputAnioF.setEnabled(true);
    }
    
    /**
     * Devuelve el día para la fecha de inicio.
     * @return Dia inicial
     */
    public int getDiaInicio() {
        return inputDiaI.getSelectedIndex() + 1;
    }
    
    /**
     * Devuelve el mes para la fecha de inicio.
     * @return Mes inicial (1-12)
     */
    public int getMesInicio() {
        return inputMesI.getSelectedIndex() + 1;
    }
    
    /**
     * Devuelve el año para la fecha de inicio.
     * @return Año inicial
     */
    public int getAnioInicio() {
        return Integer.parseInt((String) inputAnioI.getSelectedItem());
    }
    
    /**
     * Asigna los valores especificados para la fecha de inicio.
     * @param dia Dia 
     * @param mes Mes (1-12)
     * @param anio Año (debe estar en el rango)
     */
    public void setFechaFin(int dia, int mes, int anio) {
        inputDiaF.setSelectedIndex(dia-1);
        inputMesF.setSelectedIndex(mes-1);
        inputAnioF.setSelectedItem(Integer.toString(anio));
    }
    
    /**
     * Devuelve el día para la fecha de fin.
     * @return Dia final
     */
    public int getDiaFin() {
        return inputDiaF.getSelectedIndex() + 1;
    }
    
    /**
     * Devuelve el mes para la fecha de fin.
     * @return Mes final
     */
    public int getMesFin() {
        return inputMesF.getSelectedIndex() + 1;
    }
    
    /**
     * Devuelve el año para la fecha de fin.
     * @return Año final
     */
    public int getAnioFin() {
        return Integer.parseInt((String) inputAnioF.getSelectedItem());
    }
    
    /**
     * Muestra el mensaje de error especificado sobre las fechas.
     * @param mensaje Mensaje de error a mostrar al usuario
     */
    public void muestraErrorFechas(String mensaje) {
        errorFechas.setText(mensaje);
        errorFechas.setVisible(true);
    }
    
    /**
     * Oculta el mensaje de error de las fechas.
     */
    public void ocultaErrorFechas() {
        errorFechas.setVisible(false);
    }
    
    /**
     * Cambia el contenido de las opciones a los días indicados.
     * @param dias Nuevos días
     */
    public void cambiaDiasInicio(String[] dias) {
        int iDia = getDiaInicio()-1;
        inputDiaI.setModel(new DefaultComboBoxModel<>(dias));
        if(iDia >= dias.length) {
            iDia = dias.length-1;
        }
        inputDiaI.setSelectedIndex(iDia);
    }
    
    /**
     * Cambia el contenido de las opciones a los días indicados.
     * @param dias Nuevos días
     */
    public void cambiaDiasFin(String[] dias) {
        int iDia = getDiaFin()-1;
        inputDiaF.setModel(new DefaultComboBoxModel<>(dias));
        if(iDia >= dias.length) {
            iDia = dias.length-1;
        }
        inputDiaF.setSelectedIndex(iDia);
    }
    
    /**
     * Cambia el contenido de las opciones a los años indicados.
     * @param anios Nuevos años
     */
    public void cambiaAnios(String[] anios) {
        inputAnioI.setModel(new DefaultComboBoxModel<>(anios));
        inputAnioF.setModel(new DefaultComboBoxModel<>(anios));
        inputAnioI.setSelectedIndex(0);
        inputAnioF.setSelectedIndex(anios.length-1);
    }
    
        /****************************************
         *               PROVEEDOR              *
         ****************************************/
    /**
     * Devuelve el contenido del input de proveedor.
     * @return Proveedor especificado
     */
    public String getProveedor() {
        return inputProveedor.getText();
    }
    
    
    /**
     * Inhabilita la entrada de un proveedor.
     */
    public void inhabilitaProveedor() {
        inputProveedor.setEnabled(false);
        inputProveedor.setBackground(Color.GRAY);
    }
    
    /**
     * Habilita la entrada de un proveedor.
     */
    public void habilitaProveedor() {
        inputProveedor.setEnabled(true);
        inputProveedor.setBackground(Color.WHITE);
    }
    
    /**
     * Borra el contenido de la entrada de proveedor.
     */
    public void clearProveedor() {
        inputProveedor.setText("");
    }
    
    /**
     * Asigna focus al input del proveedor.
     * @param error True para mostrar un signo de error en el cuadro de texto
     */
    public void focusProveedor(boolean error) {
        if(!estaMarcadaCualquier()) {
            inputProveedor.requestFocusInWindow();
            if(error) {
                inputProveedor.setBackground(Color.red);
            }
        }
    }
    
    /**
     * Muestra el mensaje de error especificado sobre el proveedor.
     * @param mensaje Mensaje de error a mostrar al usuario
     */
    public void muestraErrorProveedor(String mensaje) {
        errorProveedor.setText(mensaje);
        errorProveedor.setVisible(true);
    }
    
    /**
     * Oculta el mensaje de error del proveedor.
     */
    public void ocultaErrorProveedor() {
        errorProveedor.setVisible(false);
    }
    
    /**
     * Habilita la pulsación del botón de consultar.
     */
    public void habilitaBotonConsultar() {
        btnConsultar.setEnabled(true);
        btnConsultar.setBackground(new Color(102,255,102));
    }
    
    /**
     * Deshabilita la pulsación del botón de consultar.
     */
    public void deshabilitaBotonConsultar() {
        btnConsultar.setEnabled(false);
        btnConsultar.setBackground(new Color(204, 204, 204));
    }
    
    
        /****************************************
         *          LISTA DE FACTURAS           *
         ****************************************/
    /**
     * Configura el relleno de la lista de resultados, para que aparezcan alineados
     * arriba del contenedor.
     */
    private void configuraFillerLista() {
        fillerLista = new JPanel();
        fillerLista.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 1;
        gbc.gridy = y;
        listaResultados.add(fillerLista, gbc);
    }
    
    /**
     * Actualiza la lista de facturas pendientes con las especificadas.
     * @param pendientes Facturas a mostrar
     */
    public void muestraFacturasPendientes(ArrayList<Factura> pendientes) {
        limpiaLista();
        
        if(pendientes.size() > 0) {
            GridBagConstraints gbc;
            for(Factura factura : pendientes) {
                // Crea y configura la fila
                JPanel nFila = new JPanel();
                nFila.setLayout(new BoxLayout(nFila, BoxLayout.LINE_AXIS));
                // Nombre de proveedor
                JLabel lNombreProveedor = new JLabel();
                configuraCasillaLista(lNombreProveedor, factura.getPedido().getProveedor().getNombre());
                nFila.add(lNombreProveedor);
                // Número de pedido
                JLabel lNumeroPedido = new JLabel();
                configuraCasillaLista(lNumeroPedido, Long.toString(factura.getPedido().getNumeroDePedido()));
                nFila.add(lNumeroPedido);
                // Importe de factura
                JLabel lImporteFactura = new JLabel();
                configuraCasillaLista(lImporteFactura, String.format("%.02f", factura.getImporte()));
                nFila.add(lImporteFactura);
                // Fecha de pedido
                JLabel lFechaPedido = new JLabel();
                configuraCasillaLista(lFechaPedido, factura.getPedido().getFechaDeRealizacion().toString());
                nFila.add(lFechaPedido);
                // Fecha de emisión
                JLabel lFechaEmision = new JLabel();
                configuraCasillaLista(lFechaEmision, factura.getFechaEmision().toString());
                nFila.add(lFechaEmision);

                // Añade la fila
                gbc = new GridBagConstraints();
                gbc.gridy = y++;
                gbc.weightx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                listaResultados.add(nFila, gbc);
            }
            
            configuraFillerLista();
        } else {
            // No hay facturas
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.BOTH;
            JLabel lNoHay = new JLabel();
            lNoHay.setFont(new Font("Ebrima", Font.BOLD, 16)); // NOI18N
            lNoHay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            lNoHay.setText("No hay facturas para ese filtro");
            listaResultados.add(lNoHay, gbc);
        }

        // Actualiza la vista
        revalidate();
        repaint();
    }
    
    /**
     * Configura una casilla para la lista de facturas pendientes.
     * @param casilla Casilla a configurar
     */
    private void configuraCasillaLista(JLabel casilla, String texto) {
        casilla.setFont(new Font("Ebrima", Font.BOLD, 16)); // NOI18N
        casilla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        casilla.setText(texto);
        casilla.setMaximumSize(new java.awt.Dimension(180, 25));
        casilla.setMinimumSize(new java.awt.Dimension(180, 25));
        casilla.setPreferredSize(new java.awt.Dimension(180, 25));
    }
    
    /**
     * Borra todos los elementos de la lista.
     */
    public void limpiaLista() {
        listaResultados.removeAll();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JPanel cabeceraLista;
    private javax.swing.JCheckBox checkAnioActual;
    private javax.swing.JCheckBox checkProveedor;
    private javax.swing.JCheckBox checkTodas;
    private javax.swing.JLabel errorFechas;
    private javax.swing.JLabel errorProveedor;
    private javax.swing.JComboBox<String> inputAnioF;
    private javax.swing.JComboBox<String> inputAnioI;
    private javax.swing.JComboBox<String> inputDiaF;
    private javax.swing.JComboBox<String> inputDiaI;
    private javax.swing.JComboBox<String> inputMesF;
    private javax.swing.JComboBox<String> inputMesI;
    private javax.swing.JTextField inputProveedor;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lAnioF;
    private javax.swing.JLabel lAnioI;
    private javax.swing.JLabel lDiaF;
    private javax.swing.JLabel lDiaI;
    private javax.swing.JLabel lEmision;
    private javax.swing.JLabel lFecha;
    private javax.swing.JLabel lFin;
    private javax.swing.JLabel lImporte;
    private javax.swing.JLabel lInicio;
    private javax.swing.JLabel lMesF;
    private javax.swing.JLabel lMesI;
    private javax.swing.JLabel lNombre;
    private javax.swing.JLabel lNumero;
    private javax.swing.JLabel lProveedor;
    private javax.swing.JLabel lRealizacion;
    private javax.swing.JLabel lTitulo;
    private javax.swing.JPanel listaResultados;
    private javax.swing.JScrollPane spResultados;
    // End of variables declaration//GEN-END:variables
}
