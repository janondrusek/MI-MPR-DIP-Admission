package cz.cvut.fit.mi_mpr_dip.admission.service.user;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.same;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import cz.cvut.fit.mi_mpr_dip.admission.adapter.PwdAuthenticationAdapter;
import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.dao.PersonDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserPassword;
import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;
import cz.cvut.fit.mi_mpr_dip.admission.exception.util.BusinessExceptionUtil;

public class UserPasswordServiceTest {

	private static final String CODE = "code";
	private static final String EMAIL = "email";
	private static final String NEW_PWD = "new";
	private static final String OLD_PWD = "old";
	private static final String USERNAME = "username";

	private UserPasswordServiceImpl userPasswordService;

	private Admission admission;
	private AdmissionDao admissionDao;
	private PwdAuthenticationAdapter authenticationAdapter;
	private BusinessExceptionUtil businessExceptionUtil;
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
		authenticationAdapter = createMock(PwdAuthenticationAdapter.class);
		userPasswordService.setAuthenticationAdapter(authenticationAdapter);
		businessExceptionUtil = createMock(BusinessExceptionUtil.class);
		userPasswordService.setBusinessExceptionUtil(businessExceptionUtil);
		userPasswordService.setAdmissionDao(admissionDao);
		passwordGenerator = createMock(PasswordGenerator.class);
		userPasswordService.setPasswordGenerator(passwordGenerator);
		person = createMock(Person.class);
		personDao = createMock(PersonDao.class);
		userPasswordService.setPersonDao(personDao);
		userIdentity = createMock(UserIdentity.class);
		userPassword = createMock(UserPassword.class);

		mocks = new Object[] { admission, admissionDao, authenticationAdapter, businessExceptionUtil,
				passwordGenerator, person, personDao, userIdentity, userPassword };
	}

	@Test
	public void testCreateRandomWithEmailNotNullUserPassword() {
		setCreateRandomWithEmailExpectations(userPassword);
		setResetPasswordExpectations();

		doTestCreateRandom();
	}

	@Test
	public void testCreateRandomWithEmailNullUserPassword() {
		setCreateRandomWithEmailExpectations(null);
		expect(passwordGenerator.createUserPassword()).andReturn(userPassword);
		userIdentity.setUserPassword(same(userPassword));

		doTestCreateRandom();
	}

	@Test
	public void testCreateRandomWithEmailAndAdmissionCode() {
		expect(admission.getCode()).andReturn(CODE);
		setUserIdentityAndPasswordExpectations(userPassword);
		setResetPasswordExpectations();

		UserIdentity created = doTestCreateRandomWithEmailAndAdmissionCode();
		assertSame(userIdentity, created);
	}

	@Test
	public void testCreateRandomWithEmailAndAdmissionCodeNotFound() {
		BusinessException exception = getBusinessException();

		expect(admission.getCode()).andReturn(null);
		setBusinessExceptionExpectations(exception);

		try {
			doTestCreateRandomWithEmailAndAdmissionCode();
			fail("Should throw BusinessException 404");
		} catch (BusinessException e) {
			verify(mocks);
			assertSame(exception, e);
		}
	}

	private void setBusinessExceptionExpectations(BusinessException exception) {
		businessExceptionUtil.throwException(HttpServletResponse.SC_NOT_FOUND);
		expectLastCall().andThrow(exception);
	}

	private UserIdentity doTestCreateRandomWithEmailAndAdmissionCode() {
		replay(mocks);
		UserIdentity userIdentity = userPasswordService.createRandomPassword(admission);
		verify(mocks);

		return userIdentity;
	}

	@Test
	public void tesUpdatePassword() {
		setUsernameExpectations(USERNAME);
		setUserPasswordExpectations(userPassword);
		setAuthenticationAdapterExpectations(true);
		passwordGenerator.createUserPassword(same(NEW_PWD), same(userPassword));

		assertSame(userIdentity, doTestUpdatePassword());
	}

	@Test
	public void tesUpdatePasswordUsernameNotFound() {
		BusinessException exception = getBusinessException();
		setUsernameExpectations(null);
		setBusinessExceptionExpectations(exception);

		doTestUpdatePasswordAndVerifyBusinessException(exception);
	}

	private void doTestUpdatePasswordAndVerifyBusinessException(BusinessException exception) {
		try {
			doTestUpdatePassword();
			fail("Must throw BEx 404");
		} catch (BusinessException e) {
			assertSame(exception, e);
		}
	}

	@Test
	public void tesUpdatePasswordNotFound() {
		BusinessException exception = getBusinessException();
		setUsernameExpectations(USERNAME);
		setUserPasswordExpectations(null);
		setBusinessExceptionExpectations(exception);

		doTestUpdatePasswordAndVerifyBusinessException(exception);
	}

	@Test
	public void tesUpdatePasswordPasswordDoesNotMatch() {
		BusinessException exception = getBusinessException();
		setUsernameExpectations(USERNAME);
		setUserPasswordExpectations(userPassword);
		setAuthenticationAdapterExpectations(false);
		setBusinessExceptionExpectations(exception);

		doTestUpdatePasswordAndVerifyBusinessException(exception);
	}

	private void setCreateRandomWithEmailExpectations(UserPassword userPassword) {
		expect(personDao.findByEmail(same(EMAIL))).andReturn(getPeople());
		expect(admissionDao.getAdmission(same(person))).andReturn(admission);
		setUserIdentityAndPasswordExpectations(userPassword);
	}

	private void setUserIdentityAndPasswordExpectations(UserPassword userPassword) {
		expect(admission.getUserIdentity()).andReturn(userIdentity);
		setUserPasswordExpectations(userPassword);
	}

	private void setAuthenticationAdapterExpectations(boolean authenticated) {
		expect(authenticationAdapter.authenticate(same(userIdentity), same(OLD_PWD))).andReturn(authenticated);
	}

	private void setUsernameExpectations(String username) {
		expect(userIdentity.getUsername()).andReturn(username);
	}

	private void setUserPasswordExpectations(UserPassword userPassword) {
		expect(userIdentity.getUserPassword()).andReturn(userPassword).atLeastOnce();
	}

	private void setResetPasswordExpectations() {
		passwordGenerator.resetUserPassword(same(userPassword));
	}

	private List<Person> getPeople() {
		List<Person> people = new ArrayList<Person>();
		people.add(person);
		return people;
	}

	private BusinessException getBusinessException() {
		return new BusinessException(0, new Throwable());
	}

	private void doTestCreateRandom() {
		replay(mocks);
		userPasswordService.createRandomPassword(EMAIL);
		verify(mocks);
	}

	private UserIdentity doTestUpdatePassword() {
		replay(mocks);
		UserIdentity updated = userPasswordService.updatePassword(userIdentity, OLD_PWD, NEW_PWD);
		verify(mocks);

		return updated;
	}

}
