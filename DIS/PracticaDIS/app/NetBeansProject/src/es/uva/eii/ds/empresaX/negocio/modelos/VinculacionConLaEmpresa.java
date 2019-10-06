package es.uva.eii.ds.empresaX.negocio.modelos;

/**
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class VinculacionConLaEmpresa {
    
    private TipoVinculacion tipo;

    public VinculacionConLaEmpresa(TipoVinculacion tipo) {
        this.tipo = tipo;
    }

    public TipoVinculacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoVinculacion tipo) {
        this.tipo = tipo;
    }
    
    public boolean estaEnActivo(){
        boolean activo = false;
        
        switch(tipo) {
            case Contratado:
                activo = true;
                break;
            case Despedido:
            case EnERTE:
            default:
                activo = false;
                break;
        }
        
        return activo;
    }
}
