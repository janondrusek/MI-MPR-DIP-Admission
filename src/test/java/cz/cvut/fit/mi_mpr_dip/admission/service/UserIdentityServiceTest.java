package cz.cvut.fit.mi_mpr_dip.admission.service;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.BaseSpringJbpmTest;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentityAuthentication;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserRole;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserIdentityService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@Repository
public class UserIdentityServiceTest extends BaseSpringJbpmTest {

	private static final String PROCHAZKA = "PROCHÁZKA";

	private static final String NOVAK = "NOVÁK";
	private static final String[] SINGLE_LASTNAME = { "novak" };

	private static final String DVORAK = "DVOŘÁK";
	private static final String[] TWO_LASTNAMES = { "dvorak", "dvorak1" };

	private static final String CERNY = "ČERNÝ";
	private static final String[] BEGINNING_GAPPED_LASTNAMES = { "cerny1", "cerny2" };

	private static final String KUCERA = "KUČERA";
	private static final String[] MIDDLE_GAPPED_LASTNAMES = { "kucera", "kucera1", "kucera3", "kucera4" };

	private static final String POSPISIL_CYRILLIC = "ПОСПÍШИЛ";
	private static final String[] MIDDLE_GAPPED_CYRILLIC_LASTNAMES = { "поспiшил", "поспiшил1", "поспiшил3",
			"поспiшил4" };

	private static final String DEPLOYMENT_PROPERTIES = "META-INF/deployment.properties";
	private static final String USER_IDENTITY_ROLES_DEFAULT = "user.identity.roles.default";

	private Set<UserRole> userRoles;
	private Properties properties;

	@Autowired
	private UserIdentityService userIdentityService;

	@Before
	public void setUp() throws IOException {
		addSingleUserIdentity();
		addTwoUserIdentities();
		addBeginningGappedUserIdentities();
		addMiddleGappedUserIdentities();
		addMiddleGappedUserIdentitiesCyrillic();

		initRoles();
	}

	private void initRoles() throws IOException {
		properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(DEPLOYMENT_PROPERTIES));
		String roles = properties.getProperty(USER_IDENTITY_ROLES_DEFAULT);
		userRoles = new HashSet<>();
		for (String role : roles.split(StringPool.COMMA)) {
			UserRole userRole = new UserRole();
			userRole.setName(role);
			userRoles.add(userRole);
		}
	}

	private void addSingleUserIdentity() {
		addUserIdentities(SINGLE_LASTNAME);
	}

	private void addTwoUserIdentities() {
		addUserIdentities(TWO_LASTNAMES);
	}

	private void addBeginningGappedUserIdentities() {
		addUserIdentities(BEGINNING_GAPPED_LASTNAMES);
	}

	private void addMiddleGappedUserIdentities() {
		addUserIdentities(MIDDLE_GAPPED_LASTNAMES);
	}

	private void addMiddleGappedUserIdentitiesCyrillic() {
		addUserIdentities(MIDDLE_GAPPED_CYRILLIC_LASTNAMES);
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
		Admission admission = getAdmissionAndBuildUserIdentity(PROCHAZKA);

		verifyUserIdentity("prochazka", admission);
	}

	@Rollback
	@Transactional
	@Test
	public void testBuildSingleMatchingUserIdentity() {
		Admission admission = getAdmissionAndBuildUserIdentity(NOVAK);

		verifyUserIdentity("novak1", admission);
	}

	@Rollback
	@Transactional
	@Test
	public void testBuildTwoMatchingUserIdentities() {
		Admission admission = getAdmissionAndBuildUserIdentity(DVORAK);

		verifyUserIdentity("dvorak2", admission);
	}

	@Rollback
	@Transactional
	@Test
	public void testBuildUserIdentitiesWithBeginningGap() {
		Admission admission = getAdmissionAndBuildUserIdentity(CERNY);

		verifyUserIdentity("cerny", admission);
	}

	@Rollback
	@Transactional
	@Test
	public void testBuildUserIdentitiesWithMiddleGap() {
		Admission admission = getAdmissionAndBuildUserIdentity(KUCERA);

		verifyUserIdentity("kucera2", admission);
	}

	@Rollback
	@Transactional
	@Test
	public void testBuildUserIdentitiesWithMiddleGapCyrillic() {
		Admission admission = getAdmissionAndBuildUserIdentity(POSPISIL_CYRILLIC);

		verifyUserIdentity("поспiшил2", admission);
	}

	private Admission getAdmissionAndBuildUserIdentity(String lastname) {
		Admission admission = getAdmission(lastname);
		userIdentityService.buildUserIdentity(admission);

		return admission;
	}

	private Admission getAdmission(String lastname) {
		Admission admission = new Admission();
		Person person = new Person();
		person.setLastname(lastname);
		admission.setPerson(person);

		return admission;
	}

	private void verifyUserIdentity(String expectedLastname, Admission admission) {
		assertEquals(expectedLastname, admission.getUserIdentity().getUsername());
		assertEquals(UserIdentityAuthentication.PWD, admission.getUserIdentity().getAuthentication());
		assertNotNull(admission.getUserIdentity().getUserPassword());
		assertThat(admission.getUserIdentity().getUserPassword().getHash().length(), greaterThan(0));
		assertEquals(userRoles, admission.getUserIdentity().getRoles());
	}
}
