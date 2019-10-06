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
import javax.persistence.JoinColumns;
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
@Table(name = "ESFUERZOSEMANAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Esfuerzosemanal.findAll", query = "SELECT e FROM Esfuerzosemanal e"),
    @NamedQuery(name = "Esfuerzosemanal.findByNumSemana", query = "SELECT e FROM Esfuerzosemanal e WHERE e.esfuerzosemanalPK.numSemana = :numSemana"),
    @NamedQuery(name = "Esfuerzosemanal.findByDni", query = "SELECT e FROM Esfuerzosemanal e WHERE e.esfuerzosemanalPK.dni = :dni"),
    @NamedQuery(name = "Esfuerzosemanal.findByIdActividad", query = "SELECT e FROM Esfuerzosemanal e WHERE e.esfuerzosemanalPK.idActividad = :idActividad"),
    @NamedQuery(name = "Esfuerzosemanal.findByIdProyecto", query = "SELECT e FROM Esfuerzosemanal e WHERE e.esfuerzosemanalPK.idProyecto = :idProyecto"),
    @NamedQuery(name = "Esfuerzosemanal.findByComentario", query = "SELECT e FROM Esfuerzosemanal e WHERE e.comentario = :comentario"),
    @NamedQuery(name = "Esfuerzosemanal.findByHorasInvertidas", query = "SELECT e FROM Esfuerzosemanal e WHERE e.horasInvertidas = :horasInvertidas"),
    @NamedQuery(name = "Esfuerzosemanal.findByAprobado", query = "SELECT e FROM Esfuerzosemanal e WHERE e.aprobado = :aprobado"),
    @NamedQuery(name = "Esfuerzosemanal.findByIdActividadIdProyecto", query = "SELECT e FROM Esfuerzosemanal e WHERE e.esfuerzosemanalPK.idProyecto = :idProyecto AND e.esfuerzosemanalPK.idActividad = :idActividad"),
    @NamedQuery(name = "Esfuerzosemanal.actividadesPorSemana", query = "SELECT e FROM Esfuerzosemanal e WHERE e.esfuerzosemanalPK.dni = :dni AND e.esfuerzosemanalPK.numSemana = :numSemana")})
public class Esfuerzosemanal implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EsfuerzosemanalPK esfuerzosemanalPK;
    @Size(max = 140)
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "horas_invertidas")
    private Integer horasInvertidas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aprobado")
    private boolean aprobado;
    @JoinColumns({
        @JoinColumn(name = "dni", referencedColumnName = "dni", insertable = false, updatable = false),
        @JoinColumn(name = "id_actividad", referencedColumnName = "id_actividad", insertable = false, updatable = false),
        @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private PersonaActividad personaActividad;

    public Esfuerzosemanal() {
    }

    public Esfuerzosemanal(EsfuerzosemanalPK esfuerzosemanalPK) {
        this.esfuerzosemanalPK = esfuerzosemanalPK;
    }

    public Esfuerzosemanal(EsfuerzosemanalPK esfuerzosemanalPK, boolean aprobado) {
        this.esfuerzosemanalPK = esfuerzosemanalPK;
        this.aprobado = aprobado;
    }

    public Esfuerzosemanal(int numSemana, String dni, int idActividad, int idProyecto) {
        this.esfuerzosemanalPK = new EsfuerzosemanalPK(numSemana, dni, idActividad, idProyecto);
    }

    public EsfuerzosemanalPK getEsfuerzosemanalPK() {
        return esfuerzosemanalPK;
    }

    public void setEsfuerzosemanalPK(EsfuerzosemanalPK esfuerzosemanalPK) {
        this.esfuerzosemanalPK = esfuerzosemanalPK;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getHorasInvertidas() {
        return horasInvertidas;
    }

    public void setHorasInvertidas(Integer horasInvertidas) {
        this.horasInvertidas = horasInvertidas;
    }

    public boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public PersonaActividad getPersonaActividad() {
        return personaActividad;
    }

    public void setPersonaActividad(PersonaActividad personaActividad) {
        this.personaActividad = personaActividad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (esfuerzosemanalPK != null ? esfuerzosemanalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Esfuerzosemanal)) {
            return false;
        }
        Esfuerzosemanal other = (Esfuerzosemanal) object;
        if ((this.esfuerzosemanalPK == null && other.esfuerzosemanalPK != null) || (this.esfuerzosemanalPK != null && !this.esfuerzosemanalPK.equals(other.esfuerzosemanalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dominio.Esfuerzosemanal[ esfuerzosemanalPK=" + esfuerzosemanalPK + " ]";
    }
    
}
