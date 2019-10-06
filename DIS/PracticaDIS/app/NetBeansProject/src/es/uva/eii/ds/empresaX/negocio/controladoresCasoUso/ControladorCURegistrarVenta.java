package es.uva.eii.ds.empresaX.negocio.controladoresCasoUso;

import com.google.gson.JsonObject;
import es.uva.eii.ds.empresaX.negocio.modelos.Empleado;
import es.uva.eii.ds.empresaX.negocio.modelos.LineaDeVenta;
import es.uva.eii.ds.empresaX.negocio.modelos.ProductoVendible;
import es.uva.eii.ds.empresaX.negocio.modelos.Venta;
import es.uva.eii.ds.empresaX.persistencia.FachadaPersistenciaDependiente;
import es.uva.eii.ds.empresaX.servicioscomunes.MessageException;
import java.util.ArrayList;

/**
 * @author Abel Herrero Gómez (abeherr)
 * @author Daniel De Vicente Garrote (dandevi)
 * @author Roberto García Antoranz (robegar)
 */
public class ControladorCURegistrarVenta {

    public static ProductoVendible crearProducto(JsonObject prod) {
        ProductoVendible pv = new ProductoVendible(prod.get("codigo").getAsString(),prod.get("nombre").getAsString(),prod.get("descripcion").getAsString(),prod.get("existencias").getAsInt(),prod.get("cantMin").getAsInt(),prod.get("precio").getAsDouble());
        return pv;
    }

    public static LineaDeVenta crearLineaDeVenta(String codigo,int cantidad) throws MessageException {
        JsonObject prod = FachadaPersistenciaDependiente.getProductoBD(codigo);
        ProductoVendible pv = new ProductoVendible(prod.get("codigo").getAsString(),prod.get("nombre").getAsString(),prod.get("descripcion").getAsString(),prod.get("existencias").getAsInt(),prod.get("cantMin").getAsInt(),prod.get("precio").getAsDouble());
        LineaDeVenta lv = new LineaDeVenta(cantidad,pv);
        return lv;
    }
    
    public static void registrarVenta(Venta venta,Empleado empleado) throws MessageException{
        FachadaPersistenciaDependiente.setVentaBD(venta,empleado);
    }
    
    public static void actualizarExistencias(Venta venta) throws MessageException{
        FachadaPersistenciaDependiente.actualizarExistenciasBD(venta);
    }

    public static int getCantidadDisponible(Venta venta,LineaDeVenta linea) {
        ProductoVendible pv = linea.getProducto();
        int res = pv.getExistencias();
        for(LineaDeVenta lv : venta.getLineas()){
            if(lv.getProducto().getCodigo().equals(pv.getCodigo()))
                res -= lv.getCantidad();
        }
        return res;
    }
        
}
