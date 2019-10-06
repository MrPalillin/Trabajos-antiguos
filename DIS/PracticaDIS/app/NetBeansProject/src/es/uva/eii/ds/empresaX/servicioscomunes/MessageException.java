package es.uva.eii.ds.empresaX.servicioscomunes;

/**
 * Excepción personalizada para representar un mensaje.
 * 
 * @author Abel Herrero Gómez         (abeherr)
 * @author Daniel De Vicente Garrote  (dandevi)
 * @author Roberto García Antoranz    (robegar)
 */
public class MessageException extends Exception {
    
    public MessageException(String errorMessage) {
        super(errorMessage);
    }
    
}
