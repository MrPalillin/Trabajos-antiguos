/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Actividad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jorge
 */
@Local
public interface ActividadFacadeLocal {

    void create(Actividad actividad);

    void edit(Actividad actividad);

    void remove(Actividad actividad);

    Actividad find(Object id);

    List<Actividad> findAll();

    List<Actividad> findRange(int[] range);

    int count();
    
}
