package uva.tds.pr3.equipo12;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Clase encargada de ejecutar todos los Test del proyecto
 * @author Daniel de Vicente Garrote (dandevi)
 * @author Marcos Mulero Lorenzo (marmule)
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ ParadaTest.class, LineaTest.class, RedAutobusesTest.class ,LineaMockTest.class,RedAutobusesMockTest.class})
public class AllTests{

}

//757a014b98b4ca240899c46bc5c0c5df15ea855b