package cz.cvut.fit.mi_mpr_dip.admission.service;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.cvut.fit.mi_mpr_dip.admission.BaseSpringTest;

@Ignore
public class LdapServiceTest extends BaseSpringTest {

	@Autowired
	AuthenticationService ldapService;

	@Before
	public void setUp() {
	}

	@Test
	public void testAuthenticate() {
		assertTrue(ldapService.authenticate("username", "asdfasdf"));
	}
}
