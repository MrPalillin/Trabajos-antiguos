package uva.tds.pr2.equipo12;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RedAutobusesTest {

	public static final double ErrorAdmisible=0.001;
	
	private RedAutobuses red;
	private ArrayList<Linea> lineas;
	private ArrayList<Linea> lineasError;
	private Linea linea1;
	private Linea linea2;
	private Linea linea3;
	private ArrayList<Parada> paradas1;
	private Parada parada1;
	private Parada parada2;
	private Parada parada3;
	private ArrayList<Parada> paradas2;
	private Parada parada4;
	private Parada parada5;
	private Parada parada6;
	private double[] posicion1={54,-16};
	private double[] posicion2={68,58};
	private double[] posicion3= {-68,165};
	private double[] posicion4= {-56,-68};
	private double[] posicion5= {48,56};
	private double[] posicion6= {51,61};


	@Before
	public void setUp() throws Exception {
		parada1=new Parada("Plaza Mayor",posicion1);
		parada2=new Parada("Plaza España",posicion2);
		parada3=new Parada("Calle Rastrojo 1",posicion3);
		parada4=new Parada("Calle Rastrojo 52",posicion4);
		parada5=new Parada("Calle Mota 16",posicion5);
		parada6=new Parada("Avda Salamanca 52",posicion6);
		paradas1=new ArrayList<Parada>();
		paradas1.add(parada1);
		paradas1.add(parada2);
		paradas1.add(parada3);
		paradas2=new ArrayList<Parada>();
		paradas2.add(parada4);
		paradas2.add(parada5);
		paradas2.add(parada6);
		linea1=new Linea("1",paradas1);
		linea2=new Linea("2",paradas2);
		linea3=new Linea("3",paradas1);
		lineas=new ArrayList<Linea>();
		lineas.add(linea1);
		lineas.add(linea2);
		lineasError=new ArrayList<Linea>();
		lineasError.add(linea1);
		red=new RedAutobuses(lineas);
	}

	@After
	public void tearDown() throws Exception {
		parada1=null;
		parada2=null;
		parada3=null;
		parada4=null;
		parada5=null;
		parada6=null;
		paradas1=null;
		paradas2=null;
		linea1=null;
		linea2=null;
		lineas=null;
		red=null;
	}

	@Test
	public void testGetLineas() {
		assertArrayEquals(red.getLineas(),lineas.toArray());
	}
	
	@Test(expected=AssertionError.class)
	public void testGetLineasError() {
		assertArrayEquals(red.getLineas(),lineasError.toArray());
	}
	
	@Test
	public void testAñadirLinea() {
		ArrayList<Parada> paradas3=new ArrayList<Parada>();
		paradas3.add(parada1);
		paradas3.add(parada5);
		Linea linea3=new Linea("3",paradas3);
		red.añadirLinea(linea3);
		fail("Sin implementar");
		//assertEquals(red.getLineas().length,3);
	}
	
	@Test(expected=AssertionError.class)
	public void testAñadirLineaError() {
		ArrayList<Parada> paradas3=new ArrayList<Parada>();
		paradas3.add(parada1);
		paradas3.add(parada5);
		Linea linea3=new Linea("3",paradas3);
		red.añadirLinea(linea3);
		fail("Sin implementar");
		//assertEquals(red.getLineas().length,2);
	}
	
	@Test
	public void testBorraLinea() {
		red.borraLinea(linea1);
		fail("Sin implementar");
		//assertEquals(red.getLineas().length,1);
	}
	
	@Test(expected=AssertionError.class)
	public void testBorraLineaError() {
		red.borraLinea(linea1);
		fail("Sin implementar");
		//assertEquals(red.getLineas().length,2);
	}
	
	@Test
	public void testGetLinea() {
		assertEquals(red.getLinea(0),linea1);
	}
	
	@Test(expected=AssertionError.class)
	public void testGetLineaError() {
		assertEquals(red.getLinea(1),linea1);
	}
	
	@Test
	
	public void getParadasCercanas() {
		double[] origen= {0,0};
		assertNotNull(red.getParadasCercanas(origen,100000));
	}
	
	@Test(expected=AssertionError.class)
	public void getParadasCercanasError() {
		double[] origen= {0,0};
		assertNotNull(red.getParadasCercanas(origen,100));
	}
	
	@Test
	public void getCorrespondenciaCercanatest() {
		assertNotNull(red.getCorrespondenciaCercana(linea1,linea3));
	}
	
	@Test(expected=AssertionError.class)
	public void getCorrespondenciaCercanatestError() {
		assertNotNull(red.getCorrespondenciaCercana(linea1,linea2));
	}
	
	@Test
	public void testgetTransbordos() {
		assertNotNull(red.getTransbordos(linea1,linea2));
	}
	
	@Test(expected=AssertionError.class)
	public void testgetTransbordosError() {
		assertNotNull(red.getTransbordos(linea1,linea2));
	}
	
	@Test
	public void getDistanciaParadasOtraLinea() {
		assertEquals(red.getDistanciaParadasOtraLinea(parada1,parada2),300,ErrorAdmisible);
	}
	
	@Test(expected=AssertionError.class)
	public void getDistanciaParadasOtraLineaError() {
		assertEquals(red.getDistanciaParadasOtraLinea(parada1,parada2),300,ErrorAdmisible);
	}

}
