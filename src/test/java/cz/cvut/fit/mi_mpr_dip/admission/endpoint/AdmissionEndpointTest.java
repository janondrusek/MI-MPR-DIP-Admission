package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.same;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.AdmissionEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UriEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.DeduplicationService;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserIdentityService;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;

public class AdmissionEndpointTest {

	private static final String CODE = "code";
	private static final String URI = "http://api.example.com";

	private AdmissionEndpointImpl admissionEndpoint;

	private Admission admission;
	private ApplicationContext applicationContext;
	private DeduplicationService<Admission> deduplicationService;
	private AdmissionEndpointHelper admissionEndpointHelper;
	private UriEndpointHelper uriEndpointHelper;
	private UserIdentityService userIdentityService;

	private Object[] mocks;

	@Before
	public void setUp() {
		admissionEndpoint = new AdmissionEndpointImpl();

		initMocks();
	}

	private void initMocks() {
		initDependencyMocks();

		admission = createMock(Admission.class);

		mocks = new Object[] { admission, applicationContext, deduplicationService, admissionEndpointHelper,
				uriEndpointHelper, userIdentityService };
	}

	@SuppressWarnings("unchecked")
	private void initDependencyMocks() {
		applicationContext = createMock(ApplicationContext.class);
		admissionEndpoint.setApplicationContext(applicationContext);
		deduplicationService = createMock(DeduplicationService.class);
		admissionEndpoint.setDeduplicationService(deduplicationService);
		admissionEndpointHelper = createMock(AdmissionEndpointHelper.class);
		admissionEndpoint.setAdmissionEndpointHelper(admissionEndpointHelper);
		userIdentityService = createMock(UserIdentityService.class);
		admissionEndpoint.setUserIdentityService(userIdentityService);
		uriEndpointHelper = createMock(UriEndpointHelper.class);
		admissionEndpoint.setUriEndpointHelper(uriEndpointHelper);
	}

	@Test
	public void testGetAdmission() {
		Response response = Response.ok().build();
		expect(admissionEndpointHelper.getAdmission(same(CODE))).andReturn(response);

		replay(mocks);
		assertSame(response, admissionEndpoint.getAdmission(CODE));
		verify(mocks);
	}

	@Test
	public void testAddAdmission() throws URISyntaxException {
		admissionEndpointHelper.validate(admission);
		deduplicationService.deduplicateAndStore(same(admission));
		expect(uriEndpointHelper.getAdmissionLocation(anyObject(String.class), same(admission)))
				.andReturn(new URI(URI));
		userIdentityService.buildUserIdentity(same(admission));

		replay(mocks);
		Response response = admissionEndpoint.addAdmission(admission);
		verify(mocks);
		assertNotNull(response);
		MultivaluedMap<String, Object> metadata = response.getMetadata();
		assertArrayEquals(new String[] { URI }, metadata.get(WebKeys.LOCATION).toArray(new String[1]));
	}

	@Test
	public void testUpdateAdmission() {
		// TODO:
	}

	@Test
	public void testDeleteAdmission() {
		Response response = Response.ok().build();
		expect(admissionEndpointHelper.deleteAdmission(same(CODE))).andReturn(response);

		replay(mocks);
		assertSame(response, admissionEndpoint.deleteAdmission(CODE));
		verify(mocks);
	}
}
