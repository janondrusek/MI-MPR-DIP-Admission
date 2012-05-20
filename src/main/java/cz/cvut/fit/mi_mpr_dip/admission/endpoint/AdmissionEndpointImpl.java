package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.util.HashSet;

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
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AdmissionResult;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.Admissions;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.action.AdmissionAction;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.AdmissionEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.AppendixEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UriEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.jbpm.ProcessService;
import cz.cvut.fit.mi_mpr_dip.admission.service.AppendixService;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.AdmissionDeduplicationService;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.AppendixDeduplicationSevice;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserIdentityService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;
import cz.cvut.fit.mi_mpr_dip.admission.util.URIKeys;

@RooJavaBean
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
	private AppendixDeduplicationSevice appendixDeduplicationSevice;

	@Autowired
	private AppendixEndpointHelper appendixEndpointHelper;

	@Autowired
	private AppendixService appendixService;

	@Autowired
	private ProcessService processService;

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
		return getAdmissionEndpointHelper().getAdmission(admissionCode);
	}

	@Secured("PERM_READ_ADMISSIONS")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Admissions getAdmissions(@QueryParam("count") Integer count, @QueryParam("page") Integer page) {
		return getAdmissionEndpointHelper().getAdmissions(count, page);
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
		return getAdmissionEndpointHelper().getOkResponse();
	}

	@Transactional
	private void validateAndUpdate(String admissionCode, Admission admission) {
		Admission dbAdmission = getAdmissionEndpointHelper().validate(admissionCode, admission);
		getAdmissionDeduplicationService().deduplicate(admission);
		getAdmissionEndpointHelper().update(admission, dbAdmission);
	}

	@Transactional
	private void validateAndDeduplicateAndStore(Admission admission) {
		getAdmissionEndpointHelper().validate(admission);
		deduplicateAndStore(admission);
		// TODO: after @chobodav fixes processes, runJbpmProcess(admission);
	}

	private void deduplicateAndStore(Admission admission) {
		getUserIdentityService().buildUserIdentity(admission);
		getAdmissionDeduplicationService().deduplicateAndStore(admission);
	}

	private void runJbpmProcess(Admission admission) {
		getProcessService().runProcess(admission);
	}

	@Secured("PERM_DELETE_ADMISSION")
	@Path(ADMISSION_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@DELETE
	@Override
	public Response deleteAdmission(@PathParam("admissionCode") String admissionCode) {
		return getAdmissionEndpointHelper().deleteAdmission(admissionCode);
	}

	@Secured("PERM_WRITE_RESULT")
	@Path(ADMISSION_PATH + URIKeys.RESULT_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response saveResult(@PathParam("admissionCode") String admissionCode, @Valid final AdmissionResult result) {
		return getAdmissionEndpointHelper().mergeAdmission(admissionCode, ENDPOINT_PATH, result,
				new AdmissionAction<AdmissionResult>() {

					@Override
					public void performAction(Admission admission, AdmissionResult actor) {
						admission.setResult(result);
					}
				});
	}

	@Secured("PERM_READ_PHOTO")
	@Path(PHOTO_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getAdmissionPhoto(@PathParam("admissionCode") String admissionCode,
			@PathParam("identifier") String identifier) {
		return getAppendixEndpointHelper().getAdmissionPhoto(admissionCode, identifier);
	}

	@Secured("PERM_WRITE_PHOTO")
	@Path(ADMISSION_PATH + URIKeys.PHOTO_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response savePhoto(@PathParam("admissionCode") String admissionCode, Appendix photo) {
		return getAdmissionEndpointHelper().mergeAdmission(admissionCode, ENDPOINT_PATH, photo,
				new SavePhotoAdmissionAction());
	}

	private class SavePhotoAdmissionAction implements AdmissionAction<Appendix> {

		@Override
		public void performAction(Admission admission, Appendix photo) {
			getAppendixService().addContent(photo);
			getAppendixDeduplicationSevice().deduplicate(photo);
			getAppendixService().addIdentifier(photo);
			ensurePhotos(admission);
			admission.getPhotos().add(photo);
		}

		private void ensurePhotos(Admission admission) {
			if (admission.getPhotos() == null) {
				admission.setPhotos(new HashSet<Appendix>());
			}
		}
	}

}
