package es.uva.eii.ds.empresaX.negocio.modelos;


public class Dinero {
    
    // Enumerado de monedas
    public static enum MONEDA {
        DOLAR_USA,  // Dolar estadounidense
        EURO,       // Euro
        YEN,        // Yen japonés
        LIBRA,      // Libra esterlina
        FRANCO,     // Franco suizo
        DOLAR_AUS,  // Dolar australiano
        DOLAR_CAN,  // Dolar canadiense
        CORONA_SWE, // Corona sueca
        DOLAR_HK,   // Dolar Hong Kong
        CORONA_NOR  // Corona noruega
    }
    
    private final int cantidad;
    private final MONEDA moneda;
    
    public Dinero(int cantidad, MONEDA moneda) {
        this.cantidad = cantidad;
        this.moneda = moneda;
    }

    public int getCantidad() {
        return cantidad;
    }

    public MONEDA getMoneda() {
        return moneda;
    }
    
    
}
