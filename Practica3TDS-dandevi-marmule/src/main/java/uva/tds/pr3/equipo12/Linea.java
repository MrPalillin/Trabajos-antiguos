package uva.tds.pr3.equipo12;

import java.util.ArrayList;

/**
 * Clase encargada de implementar las lineas de autobuses
 * 
 * @author Daniel de Vicente Garrote (dandevi)
 * @author Marcos Mulero Lorenzo (marmule)
 */
public class Linea {

	private String id;
	private ArrayList<Parada> paradas = new ArrayList<Parada>();

	/**
	 * Constructor de la clase Linea
	 * 
	 * @param id
	 *            Identificador de la linea
	 * @param paradas
	 *            Paradas pertenecientes a la linea
	 * @throws IllegalArgumentException
	 *             Cuando hay menos de tres paradas en la linea
	 * @throws IllegalArgumentException
	 *             Cuando la parada del inicio y del final estan a mas de 100 metros
	 */
	public Linea(String id, ArrayList<Parada> paradas) {
		if (paradas.size() < 3) {
			throw new IllegalArgumentException();
		}
		if (distGD(paradas.get(0).getPosicion(), paradas.get(paradas.size() - 1).getPosicion()) > 150) {
			throw new IllegalArgumentException();
		}
		this.id = id;
		this.paradas = paradas;
	}

	/**
	 * Devuelve el identificador de la linea de autobuses
	 * 
	 * @return Identificador de la linea de autobuses
	 */
	public String getId() {
		return id;
	}

	/**
	 * Devuelve el array de las paradas pertenecientes a la linea
	 * 
	 * @return Array de las paradas pertenecientes a la linea
	 */
	public Parada[] getParadas() {
		Parada[] res = new Parada[paradas.size()];
		return paradas.toArray(res);
	}

	/**
	 * Añade una nueva parada a una linea
	 * 
	 * @param nuevaParada
	 *            Nueva parada de la linea
	 * @param posicion
	 *            Posición en la que se quiere añadir
	 * @throws IllegalArgumentException
	 *             Cuando se intenta añadir una parada de incio/fin que dista mas de
	 *             100 metros de la de fin/inicio
	 */
	public void añadirParada(Parada nuevaParada, int posicion) {
		if (distGD(paradas.get(0).getPosicion(), paradas.get(paradas.size() - 1).getPosicion()) < 100) {
			throw new IllegalArgumentException();
		}
		paradas.add(posicion, nuevaParada);
	}

	/**
	 * Borra una parada de una linea
	 * 
	 * @param borrar
	 *            Parada a borrar
	 * @throws IllegalArgumentException
	 *             Cuando se intenta borrar una parada que no esta en la linea
	 * @throws IllegalArgumentException
	 *             Cuando hay solo tres paradas en la linea
	 * @throws IllegalArgumentException
	 *             Cuando se intenta eliminar una parada de incio y la que la ocupe
	 *             diste mas de 100 metros de la de fin
	 * 
	 * @throws IllegalArgumentException
	 *             Cuando se intenta eliminar una parada de fin y la que la ocupe
	 *             diste mas de 100 metros de la de inicio
	 */
	public void borraParada(Parada borrar) {
		if (!paradas.contains(borrar)) {
			throw new IllegalArgumentException();
		}
		if (paradas.size() == 3) {
			throw new IllegalArgumentException();
		}
		if (distGD(paradas.get(1).getPosicion(), paradas.get(paradas.size() - 1).getPosicion()) < 100
				&& paradas.get(0).equals(borrar)) {
			throw new IllegalArgumentException();
		}

		if (distGD(paradas.get(0).getPosicion(), paradas.get(paradas.size() - 2).getPosicion()) < 100
				&& paradas.get(paradas.size() - 1).equals(borrar)) {

			throw new IllegalArgumentException();
		}
		if (paradas.contains(borrar))
			paradas.remove(borrar);
	}

	/**
	 * Calcula las paradas cercanas a un punto de coordenadas
	 * 
	 * @param centro
	 *            Punto de coordenadas, en grados decimales (GD)
	 * @return Array de paradas a una distancia de 200 metros o menos del punto
	 */
	public Parada[] getParadasCercanas(double[] centro) {
		ArrayList<Parada> res = new ArrayList<Parada>();
		for (int i = 0; i < paradas.size(); i++) {
			if (distGD(centro, paradas.get(i).getPosicion()) <= 200) {
				res.add(paradas.get(i));
			}
		}
		Parada[] r = new Parada[res.size()];
		return res.toArray(r);
	}

	/**
	 * Calcula la distancia entre dos puntos GPS en GD
	 * 
	 * @param centro
	 *            Punto 1 del mapa
	 * @param posicion
	 *            Punto 2 del mapa
	 * @return Distancia entre los dos puntos,expresado en metros
	 */

	private double distGD(double[] centro, double[] posicion) {

		return Math
				.round((6371 * Math.acos(Math.cos(centro[0]) * Math.cos(posicion[0]) * Math.cos(posicion[1] - centro[1])
						+ Math.sin(centro[0]) * Math.sin(posicion[0])) * 1000 * 1.00) * 100d)
				/ 100d;
	}

	/**
	 * Calcula la distancia entre dos paradas de la misma linea
	 * 
	 * @param p1
	 *            Primera parada de la linea
	 * @param p2
	 *            Segunda parada de la linea
	 * @return Distancia, en metros, entre las dos paradas
	 */
	public double getDistanciaParadas(Parada p1, Parada p2) {
		return distGD(p1.getPosicion(), p2.getPosicion());
	}

}
