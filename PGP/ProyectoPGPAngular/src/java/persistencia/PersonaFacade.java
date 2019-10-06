/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Persona;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jorge
 */
@Stateless
public class PersonaFacade extends AbstractFacade<Persona> implements PersonaFacadeLocal {
    @PersistenceContext(unitName = "ProyectoPGPAngularPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonaFacade() {
        super(Persona.class);
    }
    
    @Override
    public Persona getUsuario(String login, String psswd) {

        try {
            Query query = em.createNamedQuery("Persona.findByCredenciales");
            
            query.setParameter("dni", login);
            query.setParameter("psswd", psswd);
            Persona p = (Persona) query.getSingleResult();
            
            return p;
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public List<Persona> getUsuariosPorNivel(int nivel) {
        Query q = em.createNamedQuery("Persona.findByNivel");
        q.setParameter("nivel", nivel);
          
        return (List<Persona>)q.getResultList();                
    }

}
