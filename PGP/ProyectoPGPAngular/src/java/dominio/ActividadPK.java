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

/**
 *
 * @author jorge
 */
@Embeddable
public class ActividadPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_actividad")
    private int idActividad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_proyecto")
    private int idProyecto;

    public ActividadPK() {
    }

    public ActividadPK(int idActividad, int idProyecto) {
        this.idActividad = idActividad;
        this.idProyecto = idProyecto;
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
        hash += (int) idActividad;
        hash += (int) idProyecto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActividadPK)) {
            return false;
        }
        ActividadPK other = (ActividadPK) object;
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
        return "dominio.ActividadPK[ idActividad=" + idActividad + ", idProyecto=" + idProyecto + " ]";
    }
    
}
