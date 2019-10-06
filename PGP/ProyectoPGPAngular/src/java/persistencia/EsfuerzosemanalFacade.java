/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Esfuerzosemanal;
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
public class EsfuerzosemanalFacade extends AbstractFacade<Esfuerzosemanal> implements EsfuerzosemanalFacadeLocal {
    @PersistenceContext(unitName = "ProyectoPGPAngularPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EsfuerzosemanalFacade() {
        super(Esfuerzosemanal.class);
    }
    
    @Override
    public List<Esfuerzosemanal> findByIdProyecto(int idProyecto){
        Query q = em.createNamedQuery("Esfuerzosemanal.findByIdProyecto");
        q.setParameter("idProyecto", idProyecto);
        
        return q.getResultList();
    }
    
    @Override
    public List<Esfuerzosemanal> findByIdActividadIdProyecto(int idActividad, int idProyecto){
        Query q = em.createNamedQuery("Esfuerzosemanal.findByIdActividadIdProyecto");
        q.setParameter("idActividad", idActividad);
        q.setParameter("idProyecto", idProyecto);
        
        return q.getResultList();
    }
    
    @Override
    public boolean comprobarHorasAndEsfuerzosPersonaSemana(String dni, int numSemana, int horas){
        Query q = em.createNamedQuery("Esfuerzosemanal.actividadesPorSemana");
        q.setParameter("dni", dni);
        q.setParameter("numSemana", numSemana);
        
        List<Esfuerzosemanal> esfuezoSemana = q.getResultList();
        if(esfuezoSemana.size()==4)
            return false;
        int sumaHoras=0;
        for(Esfuerzosemanal e: esfuezoSemana){
            sumaHoras += e.getHorasInvertidas();
        }
        if((sumaHoras+horas) > 40)
            return false;
        return true;
    }
}
