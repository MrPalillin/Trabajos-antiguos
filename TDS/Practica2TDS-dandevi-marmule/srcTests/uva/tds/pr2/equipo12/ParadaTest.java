package uva.tds.pr2.equipo12;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParadaTest {
	
	public static final double ErrorAdmisible=0.001;
	public static final double[] testPosicion= {150.5,-27.25};
	public static final double[] testPosicionError= {96.2,-78.75};
	
	private Parada parada;
	private double[] posicion;

	@Before
	public void setUp() throws Exception {
		posicion=new double[2];
		posicion[0]=150.5;
		posicion[1]=-27.25;
		parada=new Parada("Plaza Mayor",posicion);
	}

	@Test
	public void testGetNombre() {
		assertEquals(parada.getNombre(),"Plaza Mayor");
	}
	
	@Test(expected=AssertionError.class)
	public void testGetNombreError() {
		assertEquals(parada.getNombre(),"Plaza España");
	}
	
	@Test
	public void testSetNombre() {
		parada.setNombre("Plaza España");
		assertEquals(parada.getNombre(),"Plaza España");
	}
	
	@Test(expected=AssertionError.class)
	public void testSetNombreError() {
		parada.setNombre("Plaza España");
		assertEquals(parada.getNombre(),"Plaza Mayor");
	}
	
	@Test
	public void testGetPosicion() {
		assertArrayEquals(parada.getPosicion(),testPosicion,ErrorAdmisible);
	}
	
	@Test(expected=AssertionError.class)
	public void testGetPosicionError() {
		assertArrayEquals(parada.getPosicion(),testPosicionError,ErrorAdmisible);
	}
	
	@Test
	public void testSetPosicion() {
		parada.setPosicion(testPosicionError);
		assertArrayEquals(parada.getPosicion(),testPosicionError,ErrorAdmisible);
	}
	
	@Test(expected=AssertionError.class)
	public void testSetPosicionError() {
		parada.setPosicion(testPosicionError);
		assertArrayEquals(parada.getPosicion(),testPosicion,ErrorAdmisible);
	}
	
	@After
	public void tearDown() throws Exception {
		posicion=null;
		parada=null;
	}

}
