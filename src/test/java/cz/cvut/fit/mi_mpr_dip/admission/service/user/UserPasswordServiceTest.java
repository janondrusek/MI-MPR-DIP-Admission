package cz.cvut.fit.mi_mpr_dip.admission.service.user;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.same;
import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.dao.PersonDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserPassword;

public class UserPasswordServiceTest {

	private static final String EMAIL = "email";

	private UserPasswordServiceImpl userPasswordService;

	private Admission admission;
	private AdmissionDao admissionDao;
	private PasswordGenerator passwordGenerator;
	private Person person;
	private PersonDao personDao;
	private UserIdentity userIdentity;
	private UserPassword userPassword;

	private Object[] mocks;

	@Before
	public void setUp() {
		userPasswordService = new UserPasswordServiceImpl();

		initMocks();
	}

	private void initMocks() {
		admission = createMock(Admission.class);
		admissionDao = createMock(AdmissionDao.class);
		userPasswordService.setAdmissionDao(admissionDao);
		passwordGenerator = createMock(PasswordGenerator.class);
		userPasswordService.setPasswordGenerator(passwordGenerator);
		person = createMock(Person.class);
		personDao = createMock(PersonDao.class);
		userPasswordService.setPersonDao(personDao);
		userIdentity = createMock(UserIdentity.class);
		userPassword = createMock(UserPassword.class);

		mocks = new Object[] { admission, admissionDao, passwordGenerator, person, personDao, userIdentity,
				userPassword };
	}

	@Test
	public void testCreateRandomWithEmailNotNullUserPassword() {
		setExpectations(userPassword);
		passwordGenerator.resetUserPassword(same(userPassword));

		doTestCreateRandom();
	}

	@Test
	public void testCreateRandomWithEmailNullUserPassword() {
		setExpectations(null);
		expect(passwordGenerator.createUserPassword()).andReturn(userPassword);
		userIdentity.setUserPassword(same(userPassword));

		doTestCreateRandom();
	}

	private void setExpectations(UserPassword userPassword) {
		expect(personDao.findByEmail(same(EMAIL))).andReturn(getPeople());
		expect(admissionDao.getAdmission(same(person))).andReturn(admission);
		expect(admission.getUserIdentity()).andReturn(userIdentity);
		expect(userIdentity.getUserPassword()).andReturn(userPassword).atLeastOnce();
	}

	private List<Person> getPeople() {
		List<Person> people = new ArrayList<Person>();
		people.add(person);
		return people;
	}

	private void doTestCreateRandom() {
		replay(mocks);
		userPasswordService.createRandomPassword(EMAIL);
		verify(mocks);
	}

}
