/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jorge
 */
@Embeddable
public class EsfuerzosemanalPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_semana")
    private int numSemana;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "dni")
    private String dni;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_actividad")
    private int idActividad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_proyecto")
    private int idProyecto;

    public EsfuerzosemanalPK() {
    }

    public EsfuerzosemanalPK(int numSemana, String dni, int idActividad, int idProyecto) {
        this.numSemana = numSemana;
        this.dni = dni;
        this.idActividad = idActividad;
        this.idProyecto = idProyecto;
    }

    public int getNumSemana() {
        return numSemana;
    }

    public void setNumSemana(int numSemana) {
        this.numSemana = numSemana;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) numSemana;
        hash += (dni != null ? dni.hashCode() : 0);
        hash += (int) idActividad;
        hash += (int) idProyecto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EsfuerzosemanalPK)) {
            return false;
        }
        EsfuerzosemanalPK other = (EsfuerzosemanalPK) object;
        if (this.numSemana != other.numSemana) {
            return false;
        }
        if ((this.dni == null && other.dni != null) || (this.dni != null && !this.dni.equals(other.dni))) {
            return false;
        }
        if (this.idActividad != other.idActividad) {
            return false;
        }
        if (this.idProyecto != other.idProyecto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dominio.EsfuerzosemanalPK[ numSemana=" + numSemana + ", dni=" + dni + ", idActividad=" + idActividad + ", idProyecto=" + idProyecto + " ]";
    }
    
}
