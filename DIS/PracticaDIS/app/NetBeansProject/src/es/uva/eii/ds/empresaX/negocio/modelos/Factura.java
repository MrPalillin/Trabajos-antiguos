package es.uva.eii.ds.empresaX.negocio.modelos;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import es.uva.eii.ds.empresaX.servicioscomunes.JSONHelper;
import java.sql.Date;
import java.time.LocalDate;

public final class Factura {
    private final LocalDate fechaEmision;
    private final double importe;
    private final String cuentaBancaria;
    private final PedidoAProveedor pedido;
    
    /**
     * Construye e inicializa una factura a partir de un JSON.
     * @param jsonFactura 
     */
    public Factura(String jsonFactura) {
        JsonObject jo = new Gson().fromJson(jsonFactura, JsonObject.class);
        fechaEmision = Date.valueOf(jo.get(JSONHelper.JSON_FECHA_EMISION).getAsString()).toLocalDate();
        importe = jo.get(JSONHelper.JSON_IMPORTE).getAsDouble();
        cuentaBancaria = jo.get(JSONHelper.JSON_CUENTA_BANCARIA).getAsString();
        JsonElement get = jo.get(JSONHelper.JSON_PEDIDO);
        pedido = new PedidoAProveedor(jo.get(JSONHelper.JSON_PEDIDO).toString());
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public double getImporte() {
        return importe;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public PedidoAProveedor getPedido() {
        return pedido;
    }
   
}
