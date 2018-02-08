package uva.tds.pr2.equipo12;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LineaTest {
	
	public static final double ErrorAdmisible=0.001;
	
	private double[] posicion1={54,-16};
	private double[] posicion2={68,58};
	private double[] posicion3= {-68,165};
	private double[] posicion4= {-56,-68};
	private double[] posicion5= {48,56};
	private double[] posicion6= {51,61};
	private Parada parada1;
	private Parada parada2;
	private Parada parada3;
	private Parada parada4;
	private Parada parada5;
	private Parada parada6;
	private Linea linea,otraLinea,errorLinea;
	private ArrayList<Parada> paradas,otrasParadas,errorParadas;

	@Before
	public void setUp() throws Exception {
		parada1=new Parada("Plaza Mayor",posicion1);
		parada2=new Parada("Plaza España",posicion2);
		parada3=new Parada("Calle Rastrojo 1",posicion3);
		parada4=new Parada("Calle Rastrojo 52",posicion4);
		parada5=new Parada("Calle Mota 16",posicion5);
		parada6=new Parada("Avda Salamanca 52",posicion6);
		linea=new Linea("1",paradas);
		otraLinea=new Linea("2",otrasParadas);
		errorLinea=new Linea("3",errorParadas);
		paradas=new ArrayList<Parada>();
		otrasParadas=new ArrayList<Parada>();
		errorParadas=new ArrayList<Parada>();
		paradas.add(parada1);
		paradas.add(parada2);
		paradas.add(parada3);
		otrasParadas=new ArrayList<Parada>();
		otrasParadas.add(parada4);
		otrasParadas.add(parada5);
		otrasParadas.add(parada6);
		errorParadas=new ArrayList<Parada>();
		errorParadas.add(parada1);
		errorParadas.add(parada4);
	}

	@After
	public void tearDown() throws Exception {
		parada1=null;
		parada2=null;
		parada3=null;
		parada4=null;
		parada5=null;
		parada6=null;
		linea=null;
		otraLinea=null;
		errorLinea=null;
		paradas=null;
		otrasParadas=null;
		errorParadas=null;
	}

	@Test
	public void getIdtest() {
		assertEquals(linea.getId(),"1");
	}
	
	@Test(expected=AssertionError.class)
	public void getIdtestError() {
		assertEquals(linea.getId(),"2");
	}
	
	@Test
	public void getParadastest() {
		assertArrayEquals(linea.getParadas(),paradas.toArray());
	}
	
	@Test(expected=AssertionError.class)
	public void getParadastestError() {
		assertArrayEquals(linea.getParadas(),otrasParadas.toArray());
	}
	
	@Test
	public void getParadasCercanastest() {
		double[] direccion= {-46.96,25.23};
		assertNotNull(linea.getParadasCercanas(direccion));
	}
	
	@Test(expected=AssertionError.class)
	public void getParadasCercanastestError() {
		double[] direccion= {46.96,-25.23};
		assertNotNull(linea.getParadasCercanas(direccion));
	}
	
	@Test
	public void getCorrespondenciaCercanatest() {
		assertNotNull(linea.getCorrespondenciaCercana(otraLinea));
	}
	
	@Test(expected=AssertionError.class)
	public void getCorrespondenciaCercanatestError() {
		assertNotNull(linea.getCorrespondenciaCercana(errorLinea));
	}
	
	@Test
	public void getDistanciaParadas() {
		//parada1=linea.getParadas()[0];
		//parada2=linea.getParadas()[2];
		assertEquals(linea.getDistanciaParadas(parada1,parada2),450,ErrorAdmisible);
	}
	
	@Test(expected=AssertionError.class)
	public void getDistanciaParadasError() {
		//parada1=linea.getParadas().get(0);
		//parada2=linea.getParadas().get(5);
		assertEquals(linea.getDistanciaParadas(parada1,parada2),450,ErrorAdmisible);
	}
	
	@Test
	public void getDistanciaParadasOtraLinea() {
		//parada1=linea.getParadas().get(0);
		//parada2=otraLinea.getParadas().get(0);
		assertEquals(linea.getDistnaciaParadasOtraLinea(parada1,parada2),300,ErrorAdmisible);
	}
	
	@Test(expected=AssertionError.class)
	public void getDistanciaParadasOtraLineaError() {
		//parada1=linea.getParadas()[0];
		//parada2=linea.getParadas()[5];
		assertEquals(linea.getDistanciaParadas(parada1,parada2),450,ErrorAdmisible);
	}

}
