package cz.cvut.fit.mi_mpr_dip.admission;

import org.jbpm.test.JbpmJUnitTestCase;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring/jbpmApplicationContext-test.xml" })
public abstract class BaseSpringJbpmTest extends JbpmJUnitTestCase {

}
