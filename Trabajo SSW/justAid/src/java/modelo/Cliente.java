
package modelo;
/**
 *
 * @author Enrique
 */

public class Cliente extends Usuario{
    private String minusvalias;
    private String direccion;
    
    public Cliente() {
        super();
        minusvalias = "";
        direccion = "";
    }
   public Cliente(String direccion,String nombre,String ap1,String ap2,String contra,
           String email,String fNac,String movil,String minusvalias,String dni,String sexo){
        super(nombre,ap1,ap2,contra,email,fNac,movil,dni,sexo);
        this.minusvalias=minusvalias;
        this.direccion=direccion;
    }

    public String getMinusvalias() {
        return minusvalias;
    }

    public void setMinusvalias(String minusvalias) {
        this.minusvalias = minusvalias;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
   
}
