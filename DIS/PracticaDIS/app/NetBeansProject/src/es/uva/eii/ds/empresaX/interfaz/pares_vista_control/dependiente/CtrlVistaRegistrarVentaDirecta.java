package es.uva.eii.ds.empresaX.interfaz.pares_vista_control.dependiente;

import es.uva.eii.ds.empresaX.interfaz.GestorUI;
import es.uva.eii.ds.empresaX.negocio.controladoresCasoUso.ControladorCURegistrarVenta;
import es.uva.eii.ds.empresaX.negocio.modelos.Empleado;
import es.uva.eii.ds.empresaX.negocio.modelos.Venta;
import javax.swing.JFrame;
import es.uva.eii.ds.empresaX.negocio.modelos.LineaDeVenta;
import es.uva.eii.ds.empresaX.negocio.modelos.Sesion;
import es.uva.eii.ds.empresaX.servicioscomunes.MessageException;
import java.util.ArrayList;

/**
 * Controlador de la vista de registro de venta directa.
 *
 * @author Abel Herrero Gómez (abeherr)
 * @author Daniel De Vicente Garrote (dandevi)
 * @author Roberto García Antoranz (robegar)
 */
public class CtrlVistaRegistrarVentaDirecta {

    private final VistaRegistrarVentaDirecta vista;
    private static Venta venta;
    private static Empleado empleado;

    private final static String errorCantidadIncorrecta = "La cantidad introducida es menos que uno. Vuelva a introducir una cantidad correcta";
    private final static String errorProductoInexistente = "El código introducido no pertenece a ningún producto en la lista de productos existente";
    private final static String errorCantidadNoRelaizable = "No existen unidades suficientes para ese producto";
    private final static String errorListaVacia = "No hay productos introducidos en la lista";

    /**
     * Inicializa el controlador.
     *
     * @param v Vista que controla
     */
    public CtrlVistaRegistrarVentaDirecta(VistaRegistrarVentaDirecta v) {
        vista = v;
        // Evita que termine el programa al cerrar la ventana
        vista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Centra en la pantalla
        vista.setLocationRelativeTo(null);

        empleado = Sesion.getInstancia().getEmpleado();
        
        venta = new Venta(empleado.getDni());

    }

    /**
     * Cuando se cierra la ventana, se vuelve a la anterior.
     */
    public void procesaCierre() {
        GestorUI.getInstanciaSingleton().atras();
    }

    public void introducirProducto(String codigo, String cant) throws MessageException {


        if (cant.isEmpty()) {
            vista.mostrarMensajeError(errorCantidadIncorrecta);
        } else {

            int cantidad = Integer.parseInt(cant);

            if (cantidad < 1) {

            } else {

                if (codigo.isEmpty()) {
                    vista.mostrarMensajeError(errorProductoInexistente);

                } else {

                    LineaDeVenta linea = ControladorCURegistrarVenta.crearLineaDeVenta(codigo, cantidad);

                    if (linea == null) {

                        vista.mostrarMensajeError(errorProductoInexistente);

                    } else {

                        int cantDisp = ControladorCURegistrarVenta.getCantidadDisponible(venta,linea);

                        if (cantDisp < cantidad) {

                            vista.mostrarMensajeError(errorCantidadNoRelaizable);

                        } else {

                            addLinea(linea);
                            vista.mostrarDatosVenta(venta);

                        }
                    }
                }
            }
        }

    }
    public void finalizarVenta() throws MessageException {

        if (venta.getLineas() != null || venta.getLineas().size() > 0) {

            ControladorCURegistrarVenta.registrarVenta(venta,empleado);

            ControladorCURegistrarVenta.actualizarExistencias(venta);

            vista.borrarLista();
            GestorUI.getInstanciaSingleton().atras();

        } else {
            vista.mostrarMensajeError(errorListaVacia);
        }

    }

    private double getTotalVenta() {
        double res = 0;
        for (LineaDeVenta lv : venta.getLineas()) {
            res += lv.getCantidad() * lv.getProducto().getPrecioVenta();
        }
        return res;
    }

    void vaciaLista() {
        venta.setLineas(null);
    }

    private void addLinea(LineaDeVenta linea) {
        ArrayList<LineaDeVenta> lv = venta.getLineas();
        lv.add(linea);
        venta.setLineas(lv);
    }

}
