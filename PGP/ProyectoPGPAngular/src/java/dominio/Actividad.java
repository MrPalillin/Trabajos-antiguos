/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "ACTIVIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actividad.findAll", query = "SELECT a FROM Actividad a"),
    @NamedQuery(name = "Actividad.findByIdActividad", query = "SELECT a FROM Actividad a WHERE a.actividadPK.idActividad = :idActividad"),
    @NamedQuery(name = "Actividad.findByIdProyecto", query = "SELECT a FROM Actividad a WHERE a.actividadPK.idProyecto = :idProyecto"),
    @NamedQuery(name = "Actividad.findByDescripcion", query = "SELECT a FROM Actividad a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Actividad.findByDuracionEstimada", query = "SELECT a FROM Actividad a WHERE a.duracionEstimada = :duracionEstimada"),
    @NamedQuery(name = "Actividad.findByDuracionReal", query = "SELECT a FROM Actividad a WHERE a.duracionReal = :duracionReal"),
    @NamedQuery(name = "Persona.findByTipoUsuario", query = "SELECT p FROM Persona p WHERE p.tipoUsuario = :tipoUsuario"),
    @NamedQuery(name = "Persona.findByCredenciales", query = "SELECT p FROM Persona p WHERE p.dni = :dni AND p.psswd = :psswd")})
public class Actividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ActividadPK actividadPK;
    @Size(max = 140)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "duracion_estimada")
    private Integer duracionEstimada;
    @Column(name = "duracion_real")
    private Integer duracionReal;
    @Size(max = 20)
    @Column(name = "rol_requerido")
    private String rolRequerido;
    @JoinTable(name = "ACTPREDECESORAS", joinColumns = {
        @JoinColumn(name = "id_nueva", referencedColumnName = "id_actividad"),
        @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto")}, inverseJoinColumns = {
        @JoinColumn(name = "id_anterior", referencedColumnName = "id_actividad"),
        @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto")})
    @ManyToMany
    private List<Actividad> actividadList;
    @ManyToMany(mappedBy = "actividadList")
    private List<Actividad> actividadList1;
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actividad")
    private List<PersonaActividad> personaActividadList;

    public Actividad() {
    }

    public Actividad(ActividadPK actividadPK) {
        this.actividadPK = actividadPK;
    }

    public Actividad(int idActividad, int idProyecto) {
        this.actividadPK = new ActividadPK(idActividad, idProyecto);
    }

    public ActividadPK getActividadPK() {
        return actividadPK;
    }

    public void setActividadPK(ActividadPK actividadPK) {
        this.actividadPK = actividadPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDuracionEstimada() {
        return duracionEstimada;
    }

    public void setDuracionEstimada(Integer duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public Integer getDuracionReal() {
        return duracionReal;
    }

    public void setDuracionReal(Integer duracionReal) {
        this.duracionReal = duracionReal;
    }

    public String getRolRequerido() {
        return rolRequerido;
    }

    public void setRolRequerido(String rolRequerido) {
        this.rolRequerido = rolRequerido;
    }

    @XmlTransient
    public List<Actividad> getActividadList() {
        return actividadList;
    }

    public void setActividadList(List<Actividad> actividadList) {
        this.actividadList = actividadList;
    }

    @XmlTransient
    public List<Actividad> getActividadList1() {
        return actividadList1;
    }

    public void setActividadList1(List<Actividad> actividadList1) {
        this.actividadList1 = actividadList1;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    @XmlTransient
    public List<PersonaActividad> getPersonaActividadList() {
        return personaActividadList;
    }

    public void setPersonaActividadList(List<PersonaActividad> personaActividadList) {
        this.personaActividadList = personaActividadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actividadPK != null ? actividadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actividad)) {
            return false;
        }
        Actividad other = (Actividad) object;
        if ((this.actividadPK == null && other.actividadPK != null) || (this.actividadPK != null && !this.actividadPK.equals(other.actividadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dominio.Actividad[ actividadPK=" + actividadPK + " ]";
    }
    
}
