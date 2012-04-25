package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.same;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserPasswordService;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;

public class UserEndpointTest {

	private static final String CODE = "code";
	private static final String EMAIL = "email";

	private UserEndpointImpl userEndpoint;

	private UserPasswordService userPasswordService;

	private Object[] mocks;

	@Before
	public void setUp() {
		userEndpoint = new UserEndpointImpl();

		initMocks();
	}

	private void initMocks() {
		userPasswordService = createMock(UserPasswordService.class);
		userEndpoint.setUserPasswordService(userPasswordService);

		mocks = new Object[] { userPasswordService };
	}

	@Test
	public void testResetPasswordEmailOnly() {
		userPasswordService.createRandomPassword(same(EMAIL));

		replay(mocks);
		Response response = userEndpoint.resetPassword(EMAIL);
		verify(mocks);

		assertNotNull(response);
	}

	@Test
	public void testResetPasswordAdmissionCodeAndEmail() {
		userPasswordService.createRandomPassword(same(CODE), same(EMAIL));

		replay(mocks);
		Response response = userEndpoint.resetPassword(CODE, EMAIL);
		verify(mocks);

		assertNotNull(response);
		assertEquals(HttpServletResponse.SC_SEE_OTHER, response.getStatus());
		MultivaluedMap<String, Object> metadata = response.getMetadata();
		assertNotNull(metadata.get(WebKeys.LOCATION));
	}
}
