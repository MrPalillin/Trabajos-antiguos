/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "PARTICIPACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Participacion.findAll", query = "SELECT p FROM Participacion p"),
    @NamedQuery(name = "Participacion.findByIdProyecto", query = "SELECT p FROM Participacion p WHERE p.participacionPK.idProyecto = :idProyecto"),
    @NamedQuery(name = "Participacion.findByDni", query = "SELECT p FROM Participacion p WHERE p.participacionPK.dni = :dni"),
    @NamedQuery(name = "Participacion.findByPorcentaje", query = "SELECT p FROM Participacion p WHERE p.porcentaje = :porcentaje"),
    @NamedQuery(name = "Participacion.findByRol", query = "SELECT p FROM Participacion p WHERE p.rol = :rol")})
public class Participacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParticipacionPK participacionPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "porcentaje")
    private Double porcentaje;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "rol")
    private String rol;
    @JoinColumn(name = "dni", referencedColumnName = "dni", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Persona persona;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;

    public Participacion() {
    }

    public Participacion(ParticipacionPK participacionPK) {
        this.participacionPK = participacionPK;
    }

    public Participacion(ParticipacionPK participacionPK, String rol) {
        this.participacionPK = participacionPK;
        this.rol = rol;
    }

    public Participacion(int idProyecto, String dni) {
        this.participacionPK = new ParticipacionPK(idProyecto, dni);
    }

    public ParticipacionPK getParticipacionPK() {
        return participacionPK;
    }

    public void setParticipacionPK(ParticipacionPK participacionPK) {
        this.participacionPK = participacionPK;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (participacionPK != null ? participacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Participacion)) {
            return false;
        }
        Participacion other = (Participacion) object;
        if ((this.participacionPK == null && other.participacionPK != null) || (this.participacionPK != null && !this.participacionPK.equals(other.participacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dominio.Participacion[ participacionPK=" + participacionPK + " ]";
    }
    
}
