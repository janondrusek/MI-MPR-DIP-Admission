package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.net.URISyntaxException;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.annotation.Secured;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AdmissionResult;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.UserRoles;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.action.AdmissionAction;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.AdmissionEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.DefaultAdmissionEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UserIdentityEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserIdentityService;

@RooJavaBean
@Path(AndroidMobileEndpoint.ENDPOINT_PATH)
public class AndroidMobileEndpoint implements MobileEndpoint {

	protected static final String ENDPOINT_PATH = "/mobile";

	private static final String ADMISSION_PATH = "/admission";
	private static final String SAVE_PHOTO_PATH = "/photo";
	private static final String RESULT_PATH = "/result";

	@Autowired
	private AdmissionEndpointHelper admissionEndpointHelper;

	@Autowired
	private UserIdentityEndpointHelper userIdentityEndpointHelper;

	@Autowired
	private UserIdentityService userIdentityService;

	@Path(DefaultAdmissionEndpointHelper.IDENTITY_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getUserIdentity() {
		return getUserIdentityEndpointHelper().getUserIdentity();
	}

	@Secured("PERM_DELETE_SESSION")
	@Path(DefaultAdmissionEndpointHelper.IDENTITY_PATH + "/{userIdentity}" + "/{sessionIdentifier}")
	@Produces
	@DELETE
	@Override
	public Response deleteUserSession(@PathParam("userIdentity") String username,
			@PathParam("sessionIdentifier") String identifier) {
		return getUserIdentityEndpointHelper().deleteUserSession(username, identifier);
	}

	@Secured("PERM_WRITE_USER_ROLES")
	@Path(DefaultAdmissionEndpointHelper.IDENTITY_PATH + "/{userIdentity}" + "/roles")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response updateUserRoles(@PathParam("userIdentity") String username, UserRoles userRoles) {
		return getUserIdentityEndpointHelper().updateUserRoles(username, userRoles);
	}

	@Secured("PERM_READ_PERSON")
	@Path(ADMISSION_PATH + "/{admissionCode}")
	@GET
	@Override
	public Response getAdmission(@PathParam("admissionCode") String admissionCode) {
		return getAdmissionEndpointHelper().getAdmission(admissionCode);
	}

	@Secured("PERM_WRITE_RESULT")
	@Path(ADMISSION_PATH + "/{admissionCode}" + RESULT_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response saveResult(@PathParam("admissionCode") String admissionCode, @Valid final AdmissionResult result)
			throws URISyntaxException {
		return getAdmissionEndpointHelper().mergeAdmission(admissionCode, getAdmissionBasePath(), result,
				new AdmissionAction<AdmissionResult>() {

					@Override
					public void performAction(Admission admission, AdmissionResult actor) {
						admission.setResult(result);
					}
				});
	}

	@Secured("PERM_WRITE_PHOTO")
	@Path(ADMISSION_PATH + "/{admissionCode}" + SAVE_PHOTO_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response savePhoto(@PathParam("admissionCode") String admissionCode, @Valid final Appendix photo)
			throws URISyntaxException {
		return getAdmissionEndpointHelper().mergeAdmission(admissionCode, getAdmissionBasePath(), photo,
				new AdmissionAction<Appendix>() {

					@Override
					public void performAction(Admission admission, Appendix actor) {
						admission.getPhotos().add(photo);
					}
				});
	}

	private String getAdmissionBasePath() {
		return ENDPOINT_PATH + ADMISSION_PATH;
	}

}