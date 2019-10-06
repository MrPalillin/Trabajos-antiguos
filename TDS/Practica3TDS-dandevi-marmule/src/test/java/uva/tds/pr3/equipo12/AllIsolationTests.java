package uva.tds.pr3.equipo12;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Clase encargada de ejecutar todos los Test en aislamiento
 * @author Daniel de Vicente Garrote (dandevi)
 * @author Marcos Mulero Lorenzo (marmule)
 * 
 */

@Ignore
@RunWith(Suite.class)
@SuiteClasses({ Parada.class, LineaMockTest.class, RedAutobusesMockTest.class })
public class AllIsolationTests{

}
