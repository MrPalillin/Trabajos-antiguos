/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.eii.ds.empresaX.negocio.modelos;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class Venta {
    
    private LocalDate fechaDeVenta;
    private int idDeVenta;
    private ArrayList<LineaDeVenta> lineas;
    private String empleado;

    public Venta(String empleado) {
        this.fechaDeVenta = LocalDate.now();
        this.idDeVenta = -1;
        this.lineas = new ArrayList<>();
        this.empleado = empleado;
    }
    
    public Venta() {
        this.fechaDeVenta = LocalDate.now();
        this.idDeVenta = -1;
        this.lineas = new ArrayList<>();
    }

    public LocalDate getFechaDeVenta() {
        return fechaDeVenta;
    }

    public void setFechaDeVenta(LocalDate fechaDeVenta) {
        this.fechaDeVenta = fechaDeVenta;
    }

    public int getIdDeVenta() {
        return idDeVenta;
    }

    public void setIdDeVenta(int idDeVenta) {
        this.idDeVenta = idDeVenta;
    }

    public ArrayList<LineaDeVenta> getLineas() {
        return lineas;
    }

    public void setLineas(ArrayList<LineaDeVenta> lineas) {
        this.lineas = lineas;
    }
    
    
    
}
