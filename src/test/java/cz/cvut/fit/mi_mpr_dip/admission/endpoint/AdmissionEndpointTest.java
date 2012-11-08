package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.junit.Test;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.AdmissionEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UriEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.AdmissionDeduplicationService;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserIdentityService;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;
import eu.prvaci.util.test.annotation.Mock;
import eu.prvaci.util.test.annotation.Tested;
import eu.prvaci.util.test.mock.EasyMockTest;

public class AdmissionEndpointTest extends EasyMockTest {

	private static final String CODE = "code";
	private static final String URI = "http://api.example.com";

	@Tested
	private AdmissionEndpointImpl admissionEndpoint;

	@Mock
	private Admission admission;
	@Mock
	private Admission dbAdmission;
	@Mock
	private AdmissionDeduplicationService admissionDeduplicationService;
	@Mock
	private AdmissionEndpointHelper admissionEndpointHelper;
	@Mock
	private UriEndpointHelper uriEndpointHelper;
	@Mock
	private UserIdentityService userIdentityService;

	@Test
	public void testGetAdmission() {
		Response response = Response.ok().build();
		expect(admissionEndpointHelper.getAdmission(same(CODE))).andReturn(response);

		replay(getMocks());
		assertSame(response, admissionEndpoint.getAdmission(CODE));
	}

	@Test
	public void testAddAdmission() throws URISyntaxException {
		admissionEndpointHelper.validate(same(admission));
		admissionDeduplicationService.deduplicateAndStore(same(admission));
		expect(uriEndpointHelper.getAdmissionLocation(anyObject(String.class), same(admission)))
				.andReturn(new URI(URI));
		userIdentityService.buildUserIdentity(same(admission));

		replay(getMocks());
		Response response = admissionEndpoint.addAdmission(admission);
		assertNotNull(response);
		MultivaluedMap<String, Object> metadata = response.getMetadata();
		assertArrayEquals(new String[] { URI }, metadata.get(WebKeys.LOCATION).toArray(new String[1]));
	}

	@Test
	public void testUpdateAdmission() {
		Response response = Response.ok().build();
		expect(admissionEndpointHelper.validate(same(CODE), same(admission))).andReturn(dbAdmission);
		admissionDeduplicationService.deduplicate(admission);
		admissionEndpointHelper.update(admission, dbAdmission);
		expect(admissionEndpointHelper.getOkResponse()).andReturn(response);

		replay(getMocks());
		assertSame(response, admissionEndpoint.updateAdmission(CODE, admission));
	}

	@Test
	public void testDeleteAdmission() {
		Response response = Response.ok().build();
		expect(admissionEndpointHelper.deleteAdmission(same(CODE))).andReturn(response);

		replay(getMocks());
		assertSame(response, admissionEndpoint.deleteAdmission(CODE));
	}
}
