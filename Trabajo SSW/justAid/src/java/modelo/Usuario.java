package modelo;
/**
 *
 * @author Enrique
 */
public abstract class Usuario {
    private String nombre;
    private String ap1;
    private String ap2;
    private String contra;
    private String email;
    private String fNac;
    private String movil;
    private String dni;
    private String sexo;
    
    public Usuario(){
        String a="";
        this.nombre=a;
        this.ap1=a;
        this.ap2=a;
        this.contra=a;
        this.email=a;
        this.fNac=a;
        this.movil=a;
        this.dni=a;
        this.sexo=a;
    }
    
    public Usuario(String nombre,String ap1,String ap2,String contra,
            String email,String fNac,String movil,
            String dni,String sexo){
        this.nombre=nombre;
        this.ap1=ap1;
        this.ap2=ap2;
        this.contra=contra;
        this.email=email;
        this.fNac=fNac;
        this.movil=movil;
        this.dni=dni;
        this.sexo=sexo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAp1() {
        return ap1;
    }

    public void setAp1(String ap1) {
        this.ap1 = ap1;
    }

    public String getAp2() {
        return ap2;
    }

    public void setAp2(String ap2) {
        this.ap2 = ap2;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfNac() {
        return fNac;
    }

    public void setfNac(String fNac) {
        this.fNac = fNac;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }
     
}
