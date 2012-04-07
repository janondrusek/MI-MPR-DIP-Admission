package cz.cvut.fit.mi_mpr_dip.admission;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-withoutJbpm-test.xml" })
public abstract class BaseSpringTest {

}
