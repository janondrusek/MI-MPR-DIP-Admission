package cz.cvut.fit.mi_mpr_dip.admission.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.BaseSpringTest;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentityAuthentication;

@Repository
public class UserIdentityServiceTest extends BaseSpringTest {

	private static final String PROCHAZKA = "PROCHÁZKA";

	private static final String NOVAK = "NOVÁK";
	private static final String[] SINGLE_LASTNAME = { "novak" };

	private static final String DVORAK = "DVOŘÁK";
	private static final String[] TWO_LASTNAMES = { "dvorak", "dvorak1" };

	private static final String CERNY = "ČERNÝ";
	private static final String[] GAPEED_LASTNAMES = { "cerny1", "cerny101" };

	@Autowired
	private UserIdentityService userIdentityService;

	@Before
	public void setUp() {
		addSingleUserIdentity();
		addTwoUserIdentities();
		addGappedUserIdentities();
	}

	private void addSingleUserIdentity() {
		addUserIdentities(SINGLE_LASTNAME);
	}

	private void addTwoUserIdentities() {
		addUserIdentities(TWO_LASTNAMES);
	}

	private void addGappedUserIdentities() {
		addUserIdentities(GAPEED_LASTNAMES);
	}

	private void addUserIdentities(String[] lastnames) {
		for (String lastname : lastnames) {
			UserIdentity userIdentity = new UserIdentity();
			userIdentity.setUsername(lastname);
			userIdentity.setAuthentication(UserIdentityAuthentication.PWD);

			userIdentity.persist();
		}
	}

	@Rollback
	@Transactional
	@Test
	public void testBuildNoMatchingUserIdentity() {
		Admission admission = getAdmission(PROCHAZKA);
		userIdentityService.buildUserIdentity(admission);

		assertEquals("prochazka", admission.getUserIdentity().getUsername());
	}

	private Admission getAdmission(String lastname) {
		Admission admission = new Admission();
		Person person = new Person();
		person.setLastname(lastname);

		return admission;
	}
}
