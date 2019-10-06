/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
/**
 *
 * @author Enrique
 */
public class Profesional extends Usuario implements java.io.Serializable{
    String Profesion;
    public Profesional() {
        super();
        Profesion = "";
    }
   public Profesional(String nombre,String ap1,String ap2,String contra,
           String email,String fNac,String movil,String dni,String sexo,String profesion){
        super(nombre,ap1,ap2,contra,email,fNac,movil,dni,sexo);
        this.Profesion = profesion;
    }

    public String getProfesion() {
        return Profesion;
    }

    public void setProfesion(String Profesion) {
        this.Profesion = Profesion;  
    }
    
}
