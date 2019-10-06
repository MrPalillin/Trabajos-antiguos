package uva.tds.pr3.equipo12;

/**
 * Clase encargada de implementar las paradas de las lineas
 * 
 * @author Daniel de Vicente Garrote (dandevi)
 * @author Marcos Mulero Lorenzo (marmule)
 */
public class Parada {

	private String nombre;
	private double[] posicion;

	/**
	 * Constructor de la clase Parada
	 * 
	 * @param nombre
	 *            Nombre de la parada
	 * @param posicion
	 *            Posición en coordenadas GPS de la parada (expresada en GD)
	 */
	public Parada(String nombre, double[] posicion) {
		this.nombre = nombre;
		this.posicion = posicion;
	}

	/**
	 * Devuelve el nombre de la parada
	 * 
	 * @return Nombre de la parada
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve la posición GPS de la parada
	 * 
	 * @return Posición GPS de la parada, en grados decimales (GD)
	 */
	public double[] getPosicion() {
		return posicion;
	}

	/**
	 * Fija el nuevo nombre de la parada
	 * 
	 * @param nombre
	 *            Nuevo nombre de la parada
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Fija la nueva posición de la parada
	 * 
	 * @param posicion
	 *            Nueva posición, en grados decimales (GD)
	 */
	public void setPosicion(double[] posicion) {
		this.posicion = posicion;
	}

}
