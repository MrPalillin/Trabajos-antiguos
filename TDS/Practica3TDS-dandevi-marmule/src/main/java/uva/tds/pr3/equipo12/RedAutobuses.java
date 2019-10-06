package uva.tds.pr3.equipo12;

import java.util.ArrayList;

/**
 * Clase encargada de implementar la red de autobuses
 * 
 * @author Daniel de Vicente Garrote (dandevi)
 * @author Marcos Mulero Lorenzo (marmule)
 */
public class RedAutobuses {

	private ArrayList<Linea> lineas = new ArrayList<Linea>();

	/**
	 * Constructor de la clase RedAutobuses
	 * 
	 * @param lineas
	 *            Lineas de la red de autobuses
	 * @throws IllegalArgumentException
	 *             Cuando hay menos de dos lineas de autobuses
	 */
	public RedAutobuses(ArrayList<Linea> lineas) {
		this.lineas = lineas;
	}

	/**
	 * Devuelve las lineas de la red de autobuses
	 * 
	 * @return Lineas de la red de autobuses
	 */
	public Linea[] getLineas() {
		Linea[] r = new Linea[lineas.size()];
		return lineas.toArray(r);
	}

	/**
	 * Añade una nueva linea a la red de autobuses
	 * 
	 * @param nuevaLinea
	 *            Nueva linea de autobuses
	 */
	public void añadirLinea(Linea nuevaLinea) {
		lineas.add(nuevaLinea);
	}

	/**
	 * Borra una linea de la red de autobuses
	 * 
	 * @param borrar
	 *            Linea a borrar
	 * @throws IllegalArgumentException
	 *             Cuando hay solo dos lineas de autobuses
	 */
	public void borraLinea(Linea borrar) {
		if (lineas.size() == 2) {
			throw new IllegalArgumentException();
		}
		lineas.remove(borrar);
	}

	/**
	 * Devuelve una linea concreta de la red de autobuses
	 * 
	 * @param pos
	 *            Linea de la red a devolver, debe elegirse mediante la posición en
	 *            el ArrayList
	 * @return Una linea de paradas existente en la red
	 */
	public Linea getLinea(int pos) {
		return lineas.get(pos);
	}

	/**
	 * Calcula las paradas cercanas a un punto en una distancia maxima
	 * 
	 * @param origen
	 *            Punto de coordenadas GPS desde donde buscar (en GD)
	 * @param radio
	 *            Distancia maxima de las paradas a devolver
	 * @return Array de paradas cercanas a un punto dado
	 */
	public Parada[] getParadasCercanas(double[] origen, double radio) {
		ArrayList<Parada> res = new ArrayList<Parada>();
		for (int i = 0; i < lineas.size(); i++) {
			for (int j = 0; j < lineas.get(i).getParadas().length; j++) {
				if (distGD(origen, lineas.get(i).getParadas()[j].getPosicion()) <= radio) {
					res.add(lineas.get(i).getParadas()[j]);
				}
			}

		}
		Parada[] r = new Parada[res.size()];
		return res.toArray(r);
	}

	/**
	 * Devuelve las paradas que tengan correspondencia cercana de una linea con otra
	 * 
	 * @param l1
	 *            Primera linea a analizar
	 * @param l2
	 *            Segunda linea a analizar
	 * @return Array de paradas que tienen correspondencia entre dos lineas
	 *         distintas, entendiendose por correspondencia a cuando tiene paradas
	 *         distanciadas a menos de 200 metros
	 */
	public Parada[] getCorrespondenciaCercana(Linea l1, Linea l2) {
		ArrayList<Parada> res = new ArrayList<Parada>();
		for (int i = 0; i < l1.getParadas().length; i++) {
			for (int j = 0; j < l2.getParadas().length; j++) {
				if (distGD(l1.getParadas()[i].getPosicion(), l2.getParadas()[j].getPosicion()) <= 200) {
					if (!(res.contains(l1.getParadas()[i]))) {
						res.add(l1.getParadas()[i]);
					}

					if (!(res.contains(l1.getParadas()[j]))) {
						res.add(l1.getParadas()[j]);
					}
				}
			}

		}
		Parada[] r = new Parada[res.size()];
		return res.toArray(r);
	}

	/**
	 * Devuelve las paradas que permiten hacer transbordo entre dos lineas
	 * 
	 * @param l1
	 *            Primera linea a analizar
	 * @param l2
	 *            Segunda linea a analizar
	 * @return Array de las paradas que tienen transbordo, es decir, las paradas de
	 *         una linea en comun con las otra
	 */
	public Parada[] getTransbordos(Linea l1, Linea l2) {
		ArrayList<Parada> res = new ArrayList<Parada>();
		for (int i = 0; i < l1.getParadas().length; i++) {
			for (int j = 0; j < l2.getParadas().length; j++) {
				if (Math.abs(distGD(l1.getParadas()[i].getPosicion(), l2.getParadas()[j].getPosicion()))< 0.000001) {
					if (!(res.contains(l1.getParadas()[i]))) {
						res.add(l1.getParadas()[i]);
					}
				}
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
	 * Calcula la distancia entre dos paradas de distintas lineas
	 * 
	 * @param p1
	 *            Primera parada a analizar
	 * @param p2
	 *            Segunda parada a analizar
	 * @return Distancia, en metros, de dos paradas que no sean de la misma linea
	 */
	public double getDistanciaParadasOtraLinea(Parada p1, Parada p2) {
		return distGD(p1.getPosicion(), p2.getPosicion());
	}

}
