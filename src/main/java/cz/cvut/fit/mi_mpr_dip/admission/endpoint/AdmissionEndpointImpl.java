package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AdmissionResult;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.Admissions;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.action.SavePhotoAdmissionAction;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.action.SetResultAdmissionAction;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.AdmissionEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.AppendixEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UriEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.jbpm.ProcessService;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.AdmissionDeduplicationService;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserIdentityService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;
import cz.cvut.fit.mi_mpr_dip.admission.util.URIKeys;

@Path(AdmissionEndpointImpl.ENDPOINT_PATH)
public class AdmissionEndpointImpl implements AdmissionEndpoint {

	public static final String ENDPOINT_PATH = "/admission";

	public static final String ADMISSION_PATH = StringPool.SLASH + URIKeys.ADMISSION_CODE;
	public static final String PHOTO_PATH = AdmissionEndpointImpl.ADMISSION_PATH + URIKeys.PHOTO_PATH
			+ StringPool.SLASH + URIKeys.IDENTIFIER_ATTRIBUTE + URIKeys.IDENTIFIER;

	@Autowired
	private AdmissionDeduplicationService admissionDeduplicationService;

	@Autowired
	private AdmissionEndpointHelper admissionEndpointHelper;

	@Autowired
	private AppendixEndpointHelper appendixEndpointHelper;

	@Autowired
	private ProcessService processService;

	@Autowired
	private SavePhotoAdmissionAction savePhotoAdmissionAction;

	@Autowired
	private SetResultAdmissionAction setResultAdmissionAction;

	@Autowired
	private UriEndpointHelper uriEndpointHelper;

	@Autowired
	private UserIdentityService userIdentityService;

	@Secured({ "PERM_READ_ADMISSION", "PERM_READ_PERSON" })
	@Path(ADMISSION_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getAdmission(@PathParam("admissionCode") String admissionCode) {
		return admissionEndpointHelper.getAdmission(admissionCode);
	}

	@Secured("PERM_READ_ADMISSIONS")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Admissions getAdmissions(@QueryParam("count") Integer count, @QueryParam("page") Integer page) {
		return admissionEndpointHelper.getAdmissions(count, page);
	}

	@Secured("PERM_WRITE_ADMISSION")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response addAdmission(Admission admission) {
		validateAndDeduplicateAndStore(admission);
		return Response.created(uriEndpointHelper.getAdmissionLocation(ENDPOINT_PATH + StringPool.SLASH, admission))
				.build();
	}

	@Secured("PERM_WRITE_ADMISSION")
	@Path(ADMISSION_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@PUT
	@Override
	public Response updateAdmission(@PathParam("admissionCode") String admissionCode, Admission admission) {
		validateAndUpdate(admissionCode, admission);
		return admissionEndpointHelper.getOkResponse();
	}

	@Transactional
	private void validateAndUpdate(String admissionCode, Admission admission) {
		Admission dbAdmission = admissionEndpointHelper.validate(admissionCode, admission);
		admissionDeduplicationService.deduplicate(admission);
		admissionEndpointHelper.update(admission, dbAdmission);
	}

	private void validateAndDeduplicateAndStore(Admission admission) {
		admissionEndpointHelper.validate(admission);
		userIdentityService.buildUserIdentity(admission);
		deduplicateAndStoreAndRunJbpmProcess(admission);
	}

	@Transactional
	private void deduplicateAndStoreAndRunJbpmProcess(Admission admission) {
		deduplicateAndStore(admission);
		// TODO: after processes fixed, runJbpmProcess(admission);
	}

	private void deduplicateAndStore(Admission admission) {
		admissionDeduplicationService.deduplicateAndStore(admission);
	}

	private void runJbpmProcess(Admission admission) {
		processService.runProcess(admission);
	}

	@Secured("PERM_DELETE_ADMISSION")
	@Path(ADMISSION_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@DELETE
	@Override
	public Response deleteAdmission(@PathParam("admissionCode") String admissionCode) {
		return admissionEndpointHelper.deleteAdmission(admissionCode);
	}

	@Secured("PERM_WRITE_RESULT")
	@Path(ADMISSION_PATH + URIKeys.RESULT_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response saveResult(@PathParam("admissionCode") String admissionCode, @Valid AdmissionResult result) {
		return admissionEndpointHelper.mergeAdmission(admissionCode, ENDPOINT_PATH, result, setResultAdmissionAction);
	}

	@Secured("PERM_READ_PHOTO")
	@Path(PHOTO_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getAdmissionPhoto(@PathParam("admissionCode") String admissionCode,
			@PathParam("identifier") String identifier) {
		return appendixEndpointHelper.getAdmissionPhoto(admissionCode, identifier);
	}

	@Secured("PERM_WRITE_PHOTO")
	@Path(ADMISSION_PATH + URIKeys.PHOTO_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response savePhoto(@PathParam("admissionCode") String admissionCode, Appendix photo) {
		return admissionEndpointHelper.mergeAdmission(admissionCode, ENDPOINT_PATH, photo, savePhotoAdmissionAction);
	}

}
