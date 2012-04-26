package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import static org.easymock.EasyMock.aryEq;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.same;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UriEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.service.mail.PasswordResetService;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserPasswordService;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;

public class UserEndpointTest {

	private static final String CODE = "code";
	private static final String EMAIL = "email";
	private static final String URI = "http://api.example.com";

	private UserEndpointImpl userEndpoint;

	private Admission admission;
	private AdmissionDao admissionDao;
	private PasswordResetService passwordResetService;
	private Person person;
	private UriEndpointHelper uriEndpointHelper;
	private UserIdentity userIdentity;
	private UserPasswordService userPasswordService;

	private Object[] mocks;

	@Before
	public void setUp() {
		userEndpoint = new UserEndpointImpl();

		initMocks();
	}

	private void initMocks() {
		admission = createMock(Admission.class);
		admissionDao = createMock(AdmissionDao.class);
		userEndpoint.setAdmissionDao(admissionDao);
		passwordResetService = createMock(PasswordResetService.class);
		person = createMock(Person.class);
		userEndpoint.setPasswordResetService(passwordResetService);
		uriEndpointHelper = createMock(UriEndpointHelper.class);
		userEndpoint.setUriEndpointHelper(uriEndpointHelper);
		userPasswordService = createMock(UserPasswordService.class);
		userEndpoint.setUserPasswordService(userPasswordService);

		userIdentity = createMock(UserIdentity.class);

		mocks = new Object[] { admission, admissionDao, passwordResetService, person, uriEndpointHelper,
				userPasswordService, userIdentity };
	}

	@Test
	public void testResetPasswordEmailOnly() {
		Set<UserIdentity> userIdentities = new HashSet<UserIdentity>();
		userIdentities.add(userIdentity);
		expect(userPasswordService.createRandomPassword(same(EMAIL))).andReturn(userIdentities);
		setPersistAndSendExpcetations(EMAIL);

		replay(mocks);
		Response response = userEndpoint.resetPassword(EMAIL);
		verify(mocks);

		assertNotNull(response);
	}

	@Test
	public void testResetPasswordAdmissionCodeAndEmail() throws Exception {
		expect(admissionDao.getAdmission(same(CODE))).andReturn(admission);
		expect(userPasswordService.createRandomPassword(same(admission), same(EMAIL))).andReturn(userIdentity);
		expect(
				uriEndpointHelper.getAdmissionLocation(eq(ProcessingEndpointImpl.ENDPOINT_PATH
						+ ProcessingEndpointImpl.ADMISSION_PATH), same(CODE))).andReturn(new URI(URI));
		expect(admission.getPerson()).andReturn(person);
		expect(person.getEmail()).andReturn(EMAIL);
		setPersistAndSendExpcetations(EMAIL, EMAIL);

		replay(mocks);
		Response response = userEndpoint.resetPassword(CODE, EMAIL);
		verify(mocks);

		assertNotNull(response);
		assertEquals(HttpServletResponse.SC_SEE_OTHER, response.getStatus());
		MultivaluedMap<String, Object> metadata = response.getMetadata();
		assertArrayEquals(new String[] { URI }, metadata.get(WebKeys.LOCATION).toArray(new String[1]));
	}

	private void setPersistAndSendExpcetations(String... emails) {
		passwordResetService.send(same(userIdentity), aryEq(emails));
		userIdentity.persist();
	}
}
