package uva.tds.pr3.equipo12;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.easymock.Mock;
import static org.easymock.EasyMock.*;

/**
 * Clase encargada de testear las lineas de autobuses mediante MockObjects
 * 
 * @author Daniel de Vicente Garrote (dandevi)
 * @author Marcos Mulero Lorenzo (marmule)
 */

@Category({Isolation.class,TDD.class})
public class LineaMockTest {

	private static final double ErrorAdmisible = 0.001;
	
	@Mock
	private Parada parada1;
	@Mock
	private Parada parada2;
	@Mock
	private Parada parada3;
	@Mock
	private Parada parada4;
	@Mock
	private Parada parada5;
	@Mock
	private Parada parada6;
	
	private ArrayList<Parada> paradas;
	private ArrayList<Parada> otrasParadas;
	private ArrayList<Parada> errorParadas;
	
	private Linea linea;
	private Linea otraLinea;
	private Linea errorLinea;
	
	@Before
	public void setUp() {
		parada1 = createMock(Parada.class);
		parada2 = createMock(Parada.class);
		parada3 = createMock(Parada.class);
		parada4 = createMock(Parada.class);
		parada5 = createMock(Parada.class);
		parada6 = createMock(Parada.class);
		
		expect(parada1.getNombre()).andReturn("Plaza Mayor").anyTimes();
		expect(parada2.getNombre()).andReturn("Plaza España").anyTimes();
		expect(parada3.getNombre()).andReturn("Calle Rastrojo 1").anyTimes();
		expect(parada4.getNombre()).andReturn("Calle Rastrojo 52").anyTimes();
		expect(parada5.getNombre()).andReturn("Calle Mota 16").anyTimes();
		expect(parada6.getNombre()).andReturn("Avda Salamanca 52").anyTimes();

		expect(parada1.getPosicion()).andReturn(new double[] { 54.00001, -16 }).anyTimes();
		expect(parada2.getPosicion()).andReturn(new double[] { 54.00001559, -16.00000165 }).anyTimes();
		expect(parada3.getPosicion()).andReturn(new double[] { 54.00002959, -16.00000056 }).anyTimes();
		expect(parada4.getPosicion()).andReturn(new double[] { 54.00003054, -16.00000045 }).anyTimes();
		expect(parada5.getPosicion()).andReturn(new double[] { 54.0004084, -16.00000021 }).anyTimes();
		expect(parada6.getPosicion()).andReturn(new double[] { 54.000024, -16.00000001 }).anyTimes();
		
		replay(parada1);
		replay(parada2);
		replay(parada3);
		replay(parada4);
		replay(parada5);
		replay(parada6);

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
	public void tearDown() {
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