package es.uva.eii.ds.empresaX.negocio.modelos;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import es.uva.eii.ds.empresaX.servicioscomunes.JSONHelper;
import java.sql.Date;
import java.time.LocalDate;

public class PedidoAProveedor {
    // ATRIBUTOS
    private final long numeroDePedido;
    private final LocalDate fechaDeRealizacion;
    private final boolean estaPendiente;
    private final Proveedor proveedor;

    /**
     * Construye e inicializa un pedido a proveedor a partir de una string JSON.
     * @param jsonPedido JSON de pedido proveedor
     */
    public PedidoAProveedor(String jsonPedido) {
        JsonObject jo = new Gson().fromJson(jsonPedido, JsonObject.class);
        numeroDePedido = jo.get(JSONHelper.JSON_NUM_PEDIDO).getAsLong();
        fechaDeRealizacion = Date.valueOf(jo.get(JSONHelper.JSON_FECHA_REALIZACION).getAsString()).toLocalDate();
        estaPendiente = jo.get(JSONHelper.JSON_PENDIENTE).getAsBoolean();
        proveedor = new Proveedor(jo.get(JSONHelper.JSON_PROVEEDOR).toString());
    }

    public long getNumeroDePedido() {
        return numeroDePedido;
    }

    public LocalDate getFechaDeRealizacion() {
        return fechaDeRealizacion;
    }

    public boolean isEstaPendiente() {
        return estaPendiente;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }
    
}
