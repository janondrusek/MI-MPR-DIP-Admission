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

public class ProcessingEndpointTest {

	private static final String CODE = "code";

	private ProcessingEndpointImpl processingEndpoint;

	private AdmissionCodeValidator admissionCodeValidator;
	private AnnotatedBeanValidator annotatedBeanValidator;
	private ApplicationContext applicationContext;
	private DeduplicationService<Admission> deduplicationService;
	private AdmissionEndpointHelper endpointHelper;
	private UserIdentityService userIdentityService;

	private Object[] mocks;

	@Before
	public void setUp() {
		processingEndpoint = new ProcessingEndpointImpl();

		initMocks();
	}

	private void initMocks() {
		initDependencyMocks();

		mocks = new Object[] { applicationContext, admissionCodeValidator, annotatedBeanValidator,
				deduplicationService, endpointHelper, userIdentityService };
	}

	@SuppressWarnings("unchecked")
	private void initDependencyMocks() {
		admissionCodeValidator = createMock(AdmissionCodeValidator.class);
		processingEndpoint.setAdmissionCodeValidator(admissionCodeValidator);
		annotatedBeanValidator = createMock(AnnotatedBeanValidator.class);
		processingEndpoint.setBeanValidator(annotatedBeanValidator);
		applicationContext = createMock(ApplicationContext.class);
		processingEndpoint.setApplicationContext(applicationContext);
		deduplicationService = createMock(DeduplicationService.class);
		processingEndpoint.setDeduplicationService(deduplicationService);
		endpointHelper = createMock(AdmissionEndpointHelper.class);
		processingEndpoint.setAdmissionEndpointHelper(endpointHelper);
		userIdentityService = createMock(UserIdentityService.class);
		processingEndpoint.setUserIdentityService(userIdentityService);
	}

	@Test
	public void testGetAdmission() {
		Response response = Response.ok().build();
		expect(endpointHelper.getAdmission(same(CODE))).andReturn(response);

		replay(mocks);
		assertSame(response, processingEndpoint.getAdmission(CODE));
		verify(mocks);
	}

}
