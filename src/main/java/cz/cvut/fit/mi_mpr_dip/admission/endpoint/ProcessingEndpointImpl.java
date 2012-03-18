package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import cz.cvut.fit.mi_mpr_dip.admission.builder.AdmissionsBuilder;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admissions;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.DeduplicationService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;
import cz.cvut.fit.mi_mpr_dip.admission.validator.AdmissionValidator;

@Service(value = "processingService")
@Path(ProcessingEndpointImpl.ENDPOINT_PATH)
public class ProcessingEndpointImpl implements ProcessingEndpoint, ApplicationContextAware {

	public static final String ADMISSION_PATH = "/admission";
	public static final String ADMISSIONS_PATH = "/admissions";
	public static final String ENDPOINT_PATH = "/processing";

	@Autowired
	private AdmissionsBuilder admissionsBuilder;

	@Autowired
	private AdmissionEndpoint admissionEndpoint;

	@Autowired
	private DeduplicationService deduplicationService;

	private ApplicationContext applicationContext;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new AdmissionValidator());
	}

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
		admissionsBuilder.createNew();
		admissionsBuilder.buildLimit(count, page);

		return admissionsBuilder.get();
	}

	@Path(ADMISSIONS_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Admissions importAdmissions(List<Admission> admissions) throws URISyntaxException {
		for (Admission admission : admissions) {
			deduplicationService.deduplicateAndStore(admission);
		}
		return buildAdmissions(admissions);
	}

	private Admissions buildAdmissions(List<Admission> admissions) {
		admissionsBuilder.createNew();
		admissionsBuilder.buildAdmissions(admissions);
		return admissionsBuilder.get();
	}

	@Path(ADMISSION_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@PUT
	@Override
	public Response addAdmission(@Valid Admission admission) throws URISyntaxException {
		deduplicationService.deduplicateAndStore(admission);
		URI uri = new URI(ENDPOINT_PATH + ADMISSION_PATH + StringPool.SLASH + admission.getAdmissionId().toString());
		return Response.created(uri).build();
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
