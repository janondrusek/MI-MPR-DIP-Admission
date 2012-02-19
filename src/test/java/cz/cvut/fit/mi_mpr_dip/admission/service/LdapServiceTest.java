package cz.cvut.fit.mi_mpr_dip.admission.service;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext.xml" })
public class LdapServiceTest {

	@Autowired
	LdapService ldapService;

	@Before
	public void setUp() {
	}

	@Test
	public void testAuthenticate() {
		assertTrue(ldapService.authenticate("ondruja1", "asdfasdf"));
	}
}
