package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.same;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.AdmissionEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.DeduplicationService;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserIdentityService;
import cz.cvut.fit.mi_mpr_dip.admission.validation.AdmissionCodeValidator;
import cz.cvut.fit.mi_mpr_dip.admission.validation.AnnotatedBeanValidator;

public class AdmissionProcessingEndpointTest {

	private static final String CODE = "code";

	private AdmissionCodeValidator admissionCodeValidator;
	private AnnotatedBeanValidator annotatedBeanValidator;
	private AdmissionProcessingEndpoint admissionProcessingEndpoint;
	private ApplicationContext applicationContext;
	private DeduplicationService<Admission> deduplicationService;
	private AdmissionEndpointHelper endpointHelper;
	private UserIdentityService userIdentityService;

	private Object[] mocks;

	@Before
	public void setUp() {
		admissionProcessingEndpoint = new AdmissionProcessingEndpoint();

		initMocks();
	}

	@SuppressWarnings("unchecked")
	private void initMocks() {
		admissionCodeValidator = createMock(AdmissionCodeValidator.class);
		admissionProcessingEndpoint.setAdmissionCodeValidator(admissionCodeValidator);
		annotatedBeanValidator = createMock(AnnotatedBeanValidator.class);
		admissionProcessingEndpoint.setBeanValidator(annotatedBeanValidator);
		applicationContext = createMock(ApplicationContext.class);
		admissionProcessingEndpoint.setApplicationContext(applicationContext);
		deduplicationService = createMock(DeduplicationService.class);
		admissionProcessingEndpoint.setDeduplicationService(deduplicationService);
		endpointHelper = createMock(AdmissionEndpointHelper.class);
		admissionProcessingEndpoint.setAdmissionEndpointHelper(endpointHelper);
		userIdentityService = createMock(UserIdentityService.class);
		admissionProcessingEndpoint.setUserIdentityService(userIdentityService);

		mocks = new Object[] { applicationContext, admissionCodeValidator, annotatedBeanValidator,
				deduplicationService, endpointHelper, userIdentityService };
	}

	@Test
	public void testGetAdmission() {
		Response response = Response.ok().build();
		expect(endpointHelper.getAdmission(same(CODE))).andReturn(response);

		replay(mocks);
		assertSame(response, admissionProcessingEndpoint.getAdmission(CODE));
		verify(mocks);
	}
}
