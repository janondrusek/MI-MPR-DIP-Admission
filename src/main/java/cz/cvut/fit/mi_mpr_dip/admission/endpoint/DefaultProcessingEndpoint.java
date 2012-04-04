package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.builder.AdmissionsBuilder;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admissions;
import cz.cvut.fit.mi_mpr_dip.admission.service.UserIdentityService;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.DeduplicationService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@Service(value = "processingService")
@Path(DefaultProcessingEndpoint.ENDPOINT_PATH)
public class DefaultProcessingEndpoint implements ProcessingEndpoint, ApplicationContextAware {

	public static final String ADMISSION_PATH = "/admission";
	public static final String ADMISSIONS_PATH = "/admissions";
	public static final String ENDPOINT_PATH = "/processing";

	@Autowired
	private AdmissionEndpoint admissionEndpoint;

	@Autowired
	private DeduplicationService deduplicationService;
	
	@Autowired
	private UserIdentityService userIdentityService;

	private ApplicationContext applicationContext;

	@Path(ADMISSION_PATH + "/{admissionCode}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getAdmission(@PathParam("admissionCode") String admissionCode) {
		return admissionEndpoint.getAdmission(admissionCode);
	}

	@Path(ADMISSIONS_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Admissions getAdmissions(@QueryParam("count") Integer count, @QueryParam("page") Integer page) {
		return buildAdmissions(count, page);
	}

	private Admissions buildAdmissions(Integer count, Integer page) {
		AdmissionsBuilder admissionsBuilder = getAdmissionsBuilder();

		admissionsBuilder.createNew();
		admissionsBuilder.buildLimit(count, page);
		admissionsBuilder.buildAdmissions();

		return admissionsBuilder.get();
	}

	@Path(ADMISSIONS_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Admissions importAdmissions(Admissions admissions) throws URISyntaxException {
		if (CollectionUtils.isNotEmpty(admissions.getAdmissions())) {
			for (Admission admission : admissions.getAdmissions()) {
				deduplicateAndStore(admission);
			}
		}
		return admissions;
	}

	private AdmissionsBuilder getAdmissionsBuilder() {
		return applicationContext.getBean(AdmissionsBuilder.class);
	}

	@Path(ADMISSION_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@PUT
	@Override
	public Response addAdmission(@Valid Admission admission) throws URISyntaxException {
		deduplicateAndStore(admission);
		URI uri = new URI(ENDPOINT_PATH + ADMISSION_PATH + StringPool.SLASH + admission.getCode().toString());
		return Response.created(uri).build();
	}

	private void deduplicateAndStore(Admission admission) {
		userIdentityService.buildUserIdentity(admission);
		deduplicationService.deduplicateAndStore(admission);
	}

	@Path(ADMISSION_PATH + "/{admissionCode}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@DELETE
	@Override
	public Response deleteAdmission(@PathParam("admissionCode") String admissionCode) {
		return admissionEndpoint.deleteAdmission(admissionCode);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
