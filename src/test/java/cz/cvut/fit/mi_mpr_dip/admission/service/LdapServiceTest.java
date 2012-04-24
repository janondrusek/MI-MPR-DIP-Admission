package cz.cvut.fit.mi_mpr_dip.admission.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.cvut.fit.mi_mpr_dip.admission.BaseSpringJbpmTest;
import cz.cvut.fit.mi_mpr_dip.admission.service.auth.AuthenticationService;

@Ignore
public class LdapServiceTest extends BaseSpringJbpmTest {

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
