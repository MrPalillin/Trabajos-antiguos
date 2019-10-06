package uva.tds.pr3.equipo12;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Clase encargada de testear la red de autobuses
 * 
 * @author Daniel de Vicente Garrote (dandevi)
 * @author Marcos Mulero Lorenzo (marmule)
 */

@Category(Unit.class)
public class RedAutobusesTest {

	public static final double ErrorAdmisible = 0.001;

	private double[] posicion1 = { 54.00001, -16 };
	private double[] posicion2 = { 54.00001559, -16.00000165 };
	private double[] posicion3 = { 54.00002959, -16.00000056 };
	private double[] posicion4 = { 54.00003054, -16.00000045 };
	private double[] posicion5 = { 54.0004084, -16.00000021 };
	private double[] posicion6 = { 54.000024, -16.00000001 };
	private double[] posicion7 = { 154.00040351, -16.00000003 };
	private double[] posicion8 = { 154.00040368, -16.00000004 };
	private double[] posicion9 = { 154.00040350, -16.00000005 };
	
	private ArrayList<Parada> paradas1;
	private Parada parada1;
	private Parada parada2;
	private Parada parada3;
	private ArrayList<Parada> paradas2;
	private Parada parada4;
	private Parada parada5;
	private Parada parada6;
	private ArrayList<Parada> paradas3;
	private Parada parada7;
	private Parada parada8;
	private Parada parada9;
	
	private Linea linea1;
	private Linea linea2;
	private Linea linea3;
	private Linea linea4;
	private Linea linea5;
	
	private ArrayList<Linea> lineas;
	private ArrayList<Linea> otrasLineas;
	private ArrayList<Linea> lineasError;
	
	private RedAutobuses red;
	private RedAutobuses errorRed;
	
	@Before
	public void setUp() throws Exception {
		parada1 = new Parada("Plaza Mayor", posicion1);
		parada2 = new Parada("Plaza España", posicion2);
		parada3 = new Parada("Calle Rastrojo 1", posicion3);
		parada4 = new Parada("Calle Rastrojo 52", posicion4);
		parada5 = new Parada("Calle Mota 16", posicion5);
		parada6 = new Parada("Avda Salamanca 52", posicion6);
		parada7 = new Parada("Una calle", posicion7);
		parada8 = new Parada("Una calle", posicion8);
		parada9 = new Parada("Una calle", posicion9);

		paradas1 = new ArrayList<Parada>();
		paradas1.add(parada1);
		paradas1.add(parada2);
		paradas1.add(parada3);

		paradas2 = new ArrayList<Parada>();
		paradas2.add(parada3);
		paradas2.add(parada4);
		paradas2.add(parada5);
		paradas2.add(parada6);

		paradas3 = new ArrayList<Parada>();
		paradas3.add(parada7);
		paradas3.add(parada8);
		paradas3.add(parada9);

		linea1 = new Linea("1", paradas1);
		linea2 = new Linea("2", paradas2);
		linea3 = new Linea("3", paradas1);
		linea4 = new Linea("4", paradas2);
		linea5 = new Linea("5", paradas3);

		lineas = new ArrayList<Linea>();
		lineas.add(linea1);
		lineas.add(linea2);
		lineas.add(linea3);

		otrasLineas = new ArrayList<Linea>();
		otrasLineas.add(linea1);
		otrasLineas.add(linea2);

		lineasError = new ArrayList<Linea>();
		lineasError.add(linea1);
		lineasError.add(linea2);

		red = new RedAutobuses(lineas);
		errorRed = new RedAutobuses(lineasError);
	}

	@After
	public void tearDown() throws Exception {
		parada1 = null;
		parada2 = null;
		parada3 = null;
		parada4 = null;
		parada5 = null;
		parada6 = null;
		parada7 = null;
		parada8 = null;
		parada9 = null;
		
		paradas1 = null;
		paradas2 = null;
		paradas3 = null;
		
		linea1 = null;
		linea2 = null;
		linea3 = null;
		linea4 = null;
		linea5 = null;
		
		lineas = null;
		otrasLineas = null;
		lineasError = null;
		
		red = null;
		errorRed = null;
	}

	@Test
	public void testGetLineas() {
		assertArrayEquals(red.getLineas(), lineas.toArray());
	}

	@Test(expected = AssertionError.class)
	public void testGetLineasError() {
		assertArrayEquals(red.getLineas(), lineasError.toArray());
	}

	@Test
	public void testAñadirLinea() {
		red.añadirLinea(linea4);
		assertEquals(red.getLineas().length, 4);
	}

	@Test(expected = AssertionError.class)
	public void testAñadirLineaError() {
		red.añadirLinea(linea4);
		assertEquals(red.getLineas().length, 3);
	}

	@Test
	public void testBorraLinea() {
		red.borraLinea(linea1);
		assertEquals(red.getLineas().length, 2);
	}

	@Test(expected = AssertionError.class)
	public void testBorraLineaError() {
		red.borraLinea(linea1);
		assertEquals(red.getLineas().length, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBorraLineaError2lineas() {
		errorRed.borraLinea(linea1);
		assertEquals(red.getLineas().length, 3);
	}

	@Test
	public void testGetLinea() {
		assertEquals(red.getLinea(0), linea1);
	}

	@Test(expected = AssertionError.class)
	public void testGetLineaError() {
		assertEquals(red.getLinea(1), linea1);
	}

	@Test
	public void getParadasCercanas() {
		double[] origen = { 0, 0 };
		assertTrue(red.getParadasCercanas(origen, 10000000).length != 0);
	}

	@Test(expected = AssertionError.class)
	public void getParadasCercanasError() {
		double[] origen = { 0, 0 };
		assertTrue(red.getParadasCercanas(origen, 0.1).length != 0);
	}

	@Test
	public void getCorrespondenciaCercanatest() {
		assertTrue(red.getCorrespondenciaCercana(linea1, linea3).length != 0);
	}

	@Test(expected = AssertionError.class)
	public void getCorrespondenciaCercanatestError() {
		assertTrue(red.getCorrespondenciaCercana(linea1, linea5).length != 0);
	}

	@Test
	public void testgetTransbordos() {
		assertTrue(red.getTransbordos(linea1, linea2).length != 0);
	}

	@Test(expected = AssertionError.class)
	public void testgetTransbordosError() {
		assertTrue(red.getTransbordos(linea1, linea5).length != 0);
	}

	@Test
	public void getDistanciaParadasOtraLinea() {
		assertEquals(red.getDistanciaParadasOtraLinea(parada1, parada2), 36.67, ErrorAdmisible);
	}

	@Test(expected = AssertionError.class)
	public void getDistanciaParadasOtraLineaError() {
		assertEquals(red.getDistanciaParadasOtraLinea(parada1, parada2), 36, ErrorAdmisible);
	}

}
