package uva.tds.pr3.equipo12;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Clase encargada de testear las lineas de autobuses
 * 
 * @author Daniel de Vicente Garrote (dandevi)
 * @author Marcos Mulero Lorenzo (marmule)
 */

@Category(Unit.class)
public class LineaTest {

	private static final double ErrorAdmisible = 0.001;

	private double[] posicion1 = { 54.00001, -16 };
	private double[] posicion2 = { 54.00001559, -16.00000165 };
	private double[] posicion3 = { 54.00002959, -16.00000056 };
	private double[] posicion4 = { 54.00003054, -16.00000045 };
	private double[] posicion5 = { 54.0004084, -16.00000021 };
	private double[] posicion6 = { 54.000024, -16.00000001 };
	
	private Parada parada1;
	private Parada parada2;
	private Parada parada3;
	private Parada parada4;
	private Parada parada5;
	private Parada parada6;
	
	private ArrayList<Parada> paradas;
	private ArrayList<Parada> otrasParadas;
	private ArrayList<Parada> errorParadas;
	
	private Linea linea;
	private Linea otraLinea;
	private Linea errorLinea;

	@Before
	public void setUp() throws Exception {
		parada1 = new Parada("Plaza Mayor", posicion1);
		parada2 = new Parada("Plaza España", posicion2);
		parada3 = new Parada("Calle Rastrojo 1", posicion3);
		parada4 = new Parada("Calle Rastrojo 52", posicion4);
		parada5 = new Parada("Calle Mota 16", posicion5);
		parada6 = new Parada("Avda Salamanca 52", posicion6);

		paradas = new ArrayList<Parada>();
		paradas.add(parada1);
		paradas.add(parada2);
		paradas.add(parada3);
		paradas.add(parada4);

		otrasParadas = new ArrayList<Parada>();
		otrasParadas.add(parada4);
		otrasParadas.add(parada5);
		otrasParadas.add(parada6);
		
		errorParadas = new ArrayList<Parada>();
		errorParadas.add(parada1);
		errorParadas.add(parada2);
		errorParadas.add(parada3);
		errorParadas.add(parada4);
		errorParadas.add(parada5);
		errorParadas.add(parada6);

		linea = new Linea("1", paradas);
		otraLinea = new Linea("2", otrasParadas);
		errorLinea = new Linea("3", errorParadas);
	}

	@After
	public void tearDown() throws Exception {
		parada1 = null;
		parada2 = null;
		parada3 = null;
		parada4 = null;
		parada5 = null;
		parada6 = null;
		
		paradas = null;
		otrasParadas = null;
		errorParadas = null;
		
		linea = null;
		otraLinea = null;
		errorLinea = null;	
	}

	@Test
	public void getIdtest() {
		assertEquals(linea.getId(), "1");
	}

	@Test(expected = AssertionError.class)
	public void getIdtestError() {
		assertEquals(linea.getId(), "2");
	}

	@Test
	public void getParadastest() {
		assertArrayEquals(linea.getParadas(), paradas.toArray());
	}

	@Test(expected = AssertionError.class)
	public void getParadastestError() {
		assertArrayEquals(linea.getParadas(), otrasParadas.toArray());
	}

	@Test
	public void testAñadirParada() {
		linea.añadirParada(parada5, 2);
		assertEquals(linea.getParadas().length, 5);
	}

	@Test(expected = AssertionError.class)
	public void testAñadirParadaError() {
		linea.añadirParada(parada5, 2);
		assertEquals(linea.getParadas().length, 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAñadirParadaError100m() {
		errorLinea.añadirParada(parada5, 1);
	}

	@Test
	public void testBorraParada() {
		linea.borraParada(parada2);
		assertEquals(linea.getParadas().length, 3);
	}

	@Test(expected = AssertionError.class)
	public void testBorraParadaError() {
		linea.borraParada(parada2);
		assertEquals(linea.getParadas().length, 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBorraParadaError3paradas() {
		otraLinea.borraParada(parada4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBorraParadaInicioError100m() {
		linea.borraParada(parada6);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBorraParadaFinError100m() {
		linea.borraParada(parada1);
	}

	@Test
	public void getParadasCercanastest() {
		double[] direccion = { 54, -16 };
		assertTrue(linea.getParadasCercanas(direccion).length != 0);
	}

	@Test(expected = AssertionError.class)
	public void getParadasCercanastestError() {
		double[] direccion = { 0, 0 };
		assertTrue(linea.getParadasCercanas(direccion).length != 0);
	}

	@Test
	public void getDistanciaParadastest() {
		assertEquals(linea.getDistanciaParadas(parada1, parada2), 36.67, ErrorAdmisible);
	}

	@Test(expected = AssertionError.class)
	public void getDistanciaParadastestError() {
		assertEquals(linea.getDistanciaParadas(parada1, parada2), 36, ErrorAdmisible);
	}

}
