/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "PERSONA_ACTIVIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonaActividad.findAll", query = "SELECT p FROM PersonaActividad p"),
    @NamedQuery(name = "PersonaActividad.findByDni", query = "SELECT p FROM PersonaActividad p WHERE p.personaActividadPK.dni = :dni"),
    @NamedQuery(name = "PersonaActividad.findByIdActividad", query = "SELECT p FROM PersonaActividad p WHERE p.personaActividadPK.idActividad = :idActividad"),
    @NamedQuery(name = "PersonaActividad.findByIdProyecto", query = "SELECT p FROM PersonaActividad p WHERE p.personaActividadPK.idProyecto = :idProyecto")})
public class PersonaActividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PersonaActividadPK personaActividadPK;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personaActividad")
    private List<Esfuerzosemanal> esfuerzosemanalList;
    @JoinColumns({
        @JoinColumn(name = "id_actividad", referencedColumnName = "id_actividad", insertable = false, updatable = false),
        @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Actividad actividad;
    @JoinColumn(name = "dni", referencedColumnName = "dni", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Persona persona;

    public PersonaActividad() {
    }

    public PersonaActividad(PersonaActividadPK personaActividadPK) {
        this.personaActividadPK = personaActividadPK;
    }

    public PersonaActividad(String dni, int idActividad, int idProyecto) {
        this.personaActividadPK = new PersonaActividadPK(dni, idActividad, idProyecto);
    }

    public PersonaActividadPK getPersonaActividadPK() {
        return personaActividadPK;
    }

    public void setPersonaActividadPK(PersonaActividadPK personaActividadPK) {
        this.personaActividadPK = personaActividadPK;
    }

    @XmlTransient
    public List<Esfuerzosemanal> getEsfuerzosemanalList() {
        return esfuerzosemanalList;
    }

    public void setEsfuerzosemanalList(List<Esfuerzosemanal> esfuerzosemanalList) {
        this.esfuerzosemanalList = esfuerzosemanalList;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personaActividadPK != null ? personaActividadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaActividad)) {
            return false;
        }
        PersonaActividad other = (PersonaActividad) object;
        if ((this.personaActividadPK == null && other.personaActividadPK != null) || (this.personaActividadPK != null && !this.personaActividadPK.equals(other.personaActividadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dominio.PersonaActividad[ personaActividadPK=" + personaActividadPK + " ]";
    }
    
}
