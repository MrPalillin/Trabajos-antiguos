package uva.tds.pr1.grupo12;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GroupTest {
	
	Person persona1,persona2,persona3;
	ArrayList<Contact> contactos;
	ArrayList<Contact> contactosError;
	ArrayList<String> correos;
	Group grupo;

	@Before
	public void setUp() throws Exception {
		correos=new ArrayList<String>();
		correos.add("daniel.vicente@alumnos.uva.es");
		persona1=new Person("Daniel","1",correos);
		persona2=new Person("Marcos","2",correos);
		persona3=new Person("Gonzalez","4",correos);
		contactos=new ArrayList<Contact>();
		contactosError=new ArrayList<Contact>();
		contactos.add(persona1);
		contactos.add(persona2);
		contactosError.add(persona2);
		grupo=new Group("TDS",contactos,"3");
	}

	@After
	public void tearDown() throws Exception {
		correos=null;
		persona1=null;
		persona2=null;
		persona3=null;
		contactos=null;
		contactosError=null;
		grupo=null;
	}

	@Test
	public void testGetListaContactos() {
		assertEquals(grupo.getListaContactos(),contactos);
	}
	
	@Test(expected=AssertionError.class)
	public void testGetListaContactosError() {
		assertEquals(grupo.getListaContactos(),contactosError);
	}

	@Test
	public void testAñadeContacto() {
		grupo.añadeContacto(persona3);
		contactos.add(persona3);
		assertEquals(grupo.getListaContactos(),contactos);	
	}
	
	@Test(expected=AssertionError.class)
	public void testAñadeContactoError() {
		grupo.añadeContacto(persona3);
		contactos.add(persona3);
		assertEquals(grupo.getListaContactos(),contactosError);	
	}
	
	@Test
	public void testBorraContacto() {
		grupo.borraContacto(persona1);
		contactos.remove(persona1);
		assertEquals(grupo.getListaContactos(),contactos);
	}
	
	@Test(expected=AssertionError.class)
	public void testBorraContactoError() {
		grupo.borraContacto(persona2);
		assertEquals(grupo.getListaContactos(),contactosError);
	}
	
	@Test
	public void testGetID() {
		assertEquals(grupo.getID(),"3");
	}
	
	@Test(expected=AssertionError.class)
	public void testGetIDError() {
		assertEquals(grupo.getID(),"4");
	}

}
