/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.PersonaActividad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jorge
 */
@Local
public interface PersonaActividadFacadeLocal {

    void create(PersonaActividad personaActividad);

    void edit(PersonaActividad personaActividad);

    void remove(PersonaActividad personaActividad);

    PersonaActividad find(Object id);

    List<PersonaActividad> findAll();

    List<PersonaActividad> findRange(int[] range);

    int count();
    
}
