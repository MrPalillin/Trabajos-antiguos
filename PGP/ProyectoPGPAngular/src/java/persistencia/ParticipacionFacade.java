/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Participacion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jorge
 */
@Stateless
public class ParticipacionFacade extends AbstractFacade<Participacion> implements ParticipacionFacadeLocal {
    @PersistenceContext(unitName = "ProyectoPGPAngularPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParticipacionFacade() {
        super(Participacion.class);
    }

    @Override
    public List<Participacion> findParticipacionesByIdProyecto(int idProyecto) {
        Query q = em.createNamedQuery("Participacion.findByIdProyecto");
        q.setParameter("idProyecto", idProyecto);
        
        return q.getResultList();
    }  
    
}
