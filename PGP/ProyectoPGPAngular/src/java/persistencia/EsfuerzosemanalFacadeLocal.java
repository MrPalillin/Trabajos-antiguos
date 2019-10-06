/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Esfuerzosemanal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jorge
 */
@Local
public interface EsfuerzosemanalFacadeLocal {

    void create(Esfuerzosemanal esfuerzosemanal);

    void edit(Esfuerzosemanal esfuerzosemanal);

    void remove(Esfuerzosemanal esfuerzosemanal);

    Esfuerzosemanal find(Object id);

    List<Esfuerzosemanal> findAll();

    List<Esfuerzosemanal> findRange(int[] range);

    int count();
    
    List<Esfuerzosemanal> findByIdProyecto(int idProyecto);
    
    List<Esfuerzosemanal> findByIdActividadIdProyecto(int idActividad, int idProyecto);
    
    boolean comprobarHorasAndEsfuerzosPersonaSemana(String dni, int numSemana, int horas);
    
}
