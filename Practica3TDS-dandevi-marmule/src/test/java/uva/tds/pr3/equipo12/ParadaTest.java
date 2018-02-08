package uva.tds.pr3.equipo12;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Clase encargada de testear las paradas de las lineas
 * 
 * @author Daniel de Vicente Garrote (dandevi)
 * @author Marcos Mulero Lorenzo (marmule)
 */
@Category(Unit.class)
public class ParadaTest {

	private static final double ErrorAdmisible = 0.001;
	private static final double[] TestPosicion = { 150.5, -27.25 };
	private static final double[] TestPosicionError = { 96.2, -78.75 };

	private Parada parada;
	private double[] posicion;

	@Before
	public void setUp() throws Exception {
		posicion = new double[2];
		posicion[0] = 150.5;
		posicion[1] = -27.25;
		parada = new Parada("Plaza Mayor", posicion);
	}

	@After
	public void tearDown() throws Exception {
		posicion = null;
		parada = null;
	}

	@Test
	public void testGetNombre() {
		assertEquals(parada.getNombre(), "Plaza Mayor");
	}

	@Test(expected = AssertionError.class)
	public void testGetNombreError() {
		assertEquals(parada.getNombre(), "Plaza España");
		System.out.println(parada.getNombre());
	}

	@Test
	public void testGetPosicion() {
		assertArrayEquals(parada.getPosicion(), TestPosicion, ErrorAdmisible);
	}

	@Test(expected = AssertionError.class)
	public void testGetPosicionError() {
		assertArrayEquals(parada.getPosicion(), TestPosicionError, ErrorAdmisible);
	}

	@Test
	public void testSetNombre() {
		parada.setNombre("Plaza España");
		assertEquals(parada.getNombre(), "Plaza España");
	}

	@Test(expected = AssertionError.class)
	public void testSetNombreError() {
		parada.setNombre("Plaza España");
		assertEquals(parada.getNombre(), "Plaza Mayor");
	}

	@Test
	public void testSetPosicion() {
		parada.setPosicion(TestPosicionError);
		assertArrayEquals(parada.getPosicion(), TestPosicionError, ErrorAdmisible);
	}

	@Test(expected = AssertionError.class)
	public void testSetPosicionError() {
		parada.setPosicion(TestPosicionError);
		assertArrayEquals(parada.getPosicion(), TestPosicion, ErrorAdmisible);
	}

}
