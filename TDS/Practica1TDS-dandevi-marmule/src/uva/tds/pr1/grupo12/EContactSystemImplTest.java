package uva.tds.pr1.grupo12;

import static org.junit.Assert.*;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EContactSystemImplTest {
	
	EContactSystemImpl sistema;
	Path path;

	@Before
	public void setUp() throws Exception {
		sistema=new EContactSystemImpl();
		path=FileSystems.getDefault().getPath("", "Agenda.xml");
		
		
	}

	@After
	public void tearDown() throws Exception {
		sistema=null;
		path=null;
	}

	@Test
	public void loadFromTest() {
		sistema.loadFrom(path);
	}
	
	

}
