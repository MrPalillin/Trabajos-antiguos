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
 * Clase encargada de testear la red de autobuses mediante MockObjects
 * 
 * @author Daniel de Vicente Garrote (dandevi)
 * @author Marcos Mulero Lorenzo (marmule)
 */

@Category({Isolation.class,TDD.class})
public class RedAutobusesMockTest {

	public static final double ErrorAdmisible = 0.001;

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
	@Mock
	private Parada parada7;
	@Mock
	private Parada parada8;
	@Mock
	private Parada parada9;
	@Mock
	private Linea linea1;
	@Mock
	private Linea linea2;
	@Mock
	private Linea linea3;
	@Mock
	private Linea linea4;
	@Mock
	private Linea linea5;

	private ArrayList<Parada> paradas1;
	private Parada[] p1;
	private ArrayList<Parada> paradas2;
	private Parada[] p2;
	private ArrayList<Parada> paradas3;
	private Parada[] p3;

	private ArrayList<Linea> lineas;
	private ArrayList<Linea> otrasLineas;
	private ArrayList<Linea> lineasError;

	private RedAutobuses red;
	private RedAutobuses errorRed;

	@Before
	public void setUp() throws Exception {
		parada1 = createMock(Parada.class);
		parada2 = createMock(Parada.class);
		parada3 = createMock(Parada.class);
		parada4 = createMock(Parada.class);
		parada5 = createMock(Parada.class);
		parada6 = createMock(Parada.class);
		parada7 = createMock(Parada.class);
		parada8 = createMock(Parada.class);
		parada9 = createMock(Parada.class);

		linea1 = createMock(Linea.class);
		linea2 = createMock(Linea.class);
		linea3 = createMock(Linea.class);
		linea4 = createMock(Linea.class);
		linea5 = createMock(Linea.class);

		expect(parada1.getNombre()).andReturn("Plaza Mayor").anyTimes();
		expect(parada2.getNombre()).andReturn("Plaza España").anyTimes();
		expect(parada3.getNombre()).andReturn("Calle Rastrojo 1").anyTimes();
		expect(parada4.getNombre()).andReturn("Calle Rastrojo 52").anyTimes();
		expect(parada5.getNombre()).andReturn("Calle Mota 16").anyTimes();
		expect(parada6.getNombre()).andReturn("Avda Salamanca 52").anyTimes();
		expect(parada7.getNombre()).andReturn("Una calle").anyTimes();
		expect(parada8.getNombre()).andReturn("Una calle").anyTimes();
		expect(parada9.getNombre()).andReturn("Una calle").anyTimes();

		expect(parada1.getPosicion()).andReturn(new double[] { 54.00001, -16 }).anyTimes();
		expect(parada2.getPosicion()).andReturn(new double[] { 54.00001559, -16.00000165 }).anyTimes();
		expect(parada3.getPosicion()).andReturn(new double[] { 54.00002959, -16.00000056 }).anyTimes();
		expect(parada4.getPosicion()).andReturn(new double[] { 54.00003054, -16.00000045 }).anyTimes();
		expect(parada5.getPosicion()).andReturn(new double[] { 54.0004084, -16.00000021 }).anyTimes();
		expect(parada6.getPosicion()).andReturn(new double[] { 54.000024, -16.00000001 }).anyTimes();
		expect(parada7.getPosicion()).andReturn(new double[] { 154.00040351, -16.00000003 }).anyTimes();
		expect(parada8.getPosicion()).andReturn(new double[] { 154.00040368, -16.00000004 }).anyTimes();
		expect(parada9.getPosicion()).andReturn(new double[] { 154.00040350, -16.00000005 }).anyTimes();

		replay(parada1);
		replay(parada2);
		replay(parada3);
		replay(parada4);
		replay(parada5);
		replay(parada6);
		replay(parada7);
		replay(parada8);
		replay(parada9);

		paradas1 = new ArrayList<Parada>();
		paradas1.add(parada1);
		paradas1.add(parada2);
		paradas1.add(parada3);
		p1=new Parada[paradas1.size()];
		paradas1.toArray(p1);

		paradas2 = new ArrayList<Parada>();
		paradas2.add(parada3);
		paradas2.add(parada4);
		paradas2.add(parada5);
		paradas2.add(parada6);
		p2=new Parada[paradas2.size()];
		paradas2.toArray(p2);

		paradas3 = new ArrayList<Parada>();
		paradas3.add(parada7);
		paradas3.add(parada8);
		paradas3.add(parada9);
		p3=new Parada[paradas3.size()];
		paradas3.toArray(p3);

		expect(linea1.getParadas()).andReturn(p1).anyTimes();
		expect(linea2.getParadas()).andReturn(p2).anyTimes();
		expect(linea3.getParadas()).andReturn(p1).anyTimes();
		expect(linea4.getParadas()).andReturn(p2).anyTimes();
		expect(linea5.getParadas()).andReturn(p3).anyTimes();
		
		replay(linea1);
		replay(linea2);
		replay(linea3);
		replay(linea4);
		replay(linea5);

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
		paradas1 = null;
		paradas2 = null;
		paradas3 = null;

		linea1 = null;
		linea2 = null;
		linea3 = null;
		linea4 = null;
		lineas = null;
		otrasLineas = null;

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
