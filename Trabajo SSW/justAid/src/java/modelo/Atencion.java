package modelo;

import java.sql.Timestamp;

public class Atencion {

    private int idAtencion;
    private String idCliente;
    private String idProfAtencion;
    private Timestamp hora;
    private String observaciones;

    public Atencion() {
        idAtencion = 0;
        idCliente = "";
        idProfAtencion = "";
        hora = null;
        observaciones = "";
    }

    public Atencion(int idAtencion, String idCliente, String idProfAtencion, Timestamp hora, String observaciones) {
        this.idAtencion = idAtencion;
        this.idCliente = idCliente;
        this.idProfAtencion = idProfAtencion;
        this.hora = hora;
        this.observaciones = observaciones;
    }
    
    public Atencion(String idCliente, String idProfAtencion, Timestamp hora, String observaciones) {
        idAtencion = 0;
        this.idCliente = idCliente;
        this.idProfAtencion = idProfAtencion;
        this.hora = hora;
        this.observaciones = observaciones;
    }

    public int getIdAtencion() {
        return idAtencion;
    }

    public void setIdAtencion(int idAtencion) {
        this.idAtencion = idAtencion;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdProfAtencion() {
        return idProfAtencion;
    }

    public void setIdProfAtencion(String idProfAtencion) {
        this.idProfAtencion = idProfAtencion;
    }

    public Timestamp getHora() {
        return hora;
    }

    public void setHora(Timestamp hora) {
        this.hora = hora;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
