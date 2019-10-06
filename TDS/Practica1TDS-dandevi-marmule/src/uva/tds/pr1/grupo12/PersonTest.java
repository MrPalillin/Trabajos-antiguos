package uva.tds.pr1.grupo12;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersonTest {

	EnumKindOfPhone movilPersonal = EnumKindOfPhone.MOVILPERSONAL;
	EnumKindOfPhone fijoPersonal = EnumKindOfPhone.FIJOPERSONAL;
	EnumKindOfPhone movilTrabajo = EnumKindOfPhone.MOVILTRABAJO;
	EnumKindOfPhone fijoTrabajo = EnumKindOfPhone.FIJOTRABAJO;
	HashMap<String, EnumKindOfPhone> telefonos, comparar;
	ArrayList<String> correos = new ArrayList<String>();
	Person persona;

	@Before
	public void setUp() throws Exception {
		telefonos = new HashMap<String, EnumKindOfPhone>();
		telefonos.put("318454155", movilTrabajo);
		telefonos.put("423854563", fijoTrabajo);
		telefonos.put("523456789", fijoPersonal);
		comparar = (HashMap<String, EnumKindOfPhone>) telefonos.clone();
		correos.add("daniel.vicente@alumnos.uva.es");
		persona = new Person("Daniel", "5", correos, "de Vicente", telefonos, "dandevi");
	}

	@After
	public void tearDown() throws Exception {
		telefonos = null;
		correos = null;
		persona = null;
		comparar = null;
	}

	@Test
	public void testGetNombre() {
		assertEquals(persona.getNombre(), "Daniel");
	}

	@Test(expected = AssertionError.class)
	public void testGetNombreError() {
		assertEquals(persona.getNombre(), "Marcos");
	}

	@Test
	public void testSetNombre() {
		persona.setNombre("Marcos");
		assertEquals(persona.getNombre(), "Marcos");
	}

	@Test(expected = AssertionError.class)
	public void testSetNombreError() {
		persona.setNombre("Marcos");
		assertEquals(persona.getNombre(), "Daniel");
	}

	@Test
	public void testGetApellidos() {
		assertEquals(persona.getApellidos(), "de Vicente");
	}

	@Test(expected = AssertionError.class)
	public void testGetApellidosError() {
		assertEquals(persona.getApellidos(), "Garrote");
	}

	@Test
	public void testSetApellidos() {
		persona.setApellidos("Garrote");
		assertEquals(persona.getApellidos(), "Garrote");
	}

	@Test(expected = AssertionError.class)
	public void testSetApellidosError() {
		persona.setApellidos("Garrote");
		assertEquals(persona.getApellidos(), "de Vicente");
	}

	@Test
	public void testGetNumeros() {
		assertEquals(persona.getNumeros(), comparar);
	}

	@Test(expected = AssertionError.class)
	public void testGetNumerosError() {
		fail("Not yet implemented");
	}

	@Test
	public void testAñadeNumero() {
		persona.añadeNumero("654321987", fijoTrabajo);
		comparar.put("654321987", fijoTrabajo);
		assertEquals(persona.getNumeros(), comparar);
	}

	@Test(expected = AssertionError.class)
	public void testAñadeNumeroError() {
		persona.añadeNumero("318454155", fijoTrabajo);
		comparar.put("523456789", fijoTrabajo);
		assertEquals(persona.getNumeros(), comparar);
	}

	@Test
	public void testBorraNumero() {
		persona.borraNumero("318454155");
		comparar.remove("318454155");
		assertEquals(persona.getNumeros(), comparar);
	}

	@Test(expected = AssertionError.class)
	public void testBorraNumeroError() {
		persona.borraNumero("318454155");
		comparar.remove("423854563");
		assertEquals(persona.getNumeros(), comparar);
	}

	@Test
	public void testGetCorreo() {
		assertEquals(persona.getCorreos().get(0), "daniel.vicente@alumnos.uva.es");
	}

	@Test(expected = AssertionError.class)
	public void testGetCorreoError() {
		assertEquals(persona.getCorreos().get(0), "dandevi@alumnos.uva.es");
	}

	@Test
	public void testGetNick() {
		assertEquals(persona.getNick(), "dandevi");
	}

	@Test(expected = AssertionError.class)
	public void testGetNickError() {
		assertEquals(persona.getNick(), "marmule");
	}

	@Test
	public void testSetNick() {
		persona.setNick("marmule");
		assertEquals(persona.getNick(), "marmule");
	}

	@Test(expected = AssertionError.class)
	public void testSetNickError() {
		persona.setNick("marmule");
		assertEquals(persona.getNick(), "dandevi");
	}
}
