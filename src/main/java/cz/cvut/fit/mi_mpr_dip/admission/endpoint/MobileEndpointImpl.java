package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AdmissionResult;
import cz.cvut.fit.mi_mpr_dip.admission.service.UserIdentityService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@Path(MobileEndpointImpl.ENDPOINT_PATH)
public class MobileEndpointImpl implements MobileEndpoint {

	public static final String IDENTITY_PATH = "/identity";
	public static final String ADMISSION_PATH = "/admission";
	public static final String SAVE_PHOTO_PATH = "/savePhoto";
	public static final String ENDPOINT_PATH = "/mobile";

	@Autowired
	private AdmissionDao admissionDao;

	@Autowired
	private AdmissionEndpoint admissionEndpoint;

	@Autowired
	private UserIdentityService userIdentityService;

	private SecurityContextHolderStrategy securityContextHolderStrategy;

	@PreAuthorize("!hasRole('ROLE_ANONYMOUS')")
	@Path(IDENTITY_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getUserIdentity() {
		Authentication authentication = getAuthentication();
		return Response.ok(userIdentityService.getUserIdentity(authentication.getPrincipal().toString())).build();
	}

	private Authentication getAuthentication() {
		return securityContextHolderStrategy.getContext().getAuthentication();
	}

	@Secured("PERM_READ_PERSON")
	@Path(ADMISSION_PATH + "/{admissionCode}")
	@GET
	@Override
	public Response getAdmission(@PathParam("admissionCode") String admissionCode) {
		return admissionEndpoint.getAdmission(admissionCode);
	}

	@Secured("PERM_WRITE_RESULT")
	@Path(ADMISSION_PATH + "/{admissionCode}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response saveResult(@PathParam("admissionCode") String admissionCode, @Valid AdmissionResult result)
			throws URISyntaxException {
		Admission admission = admissionDao.getAdmission(admissionCode);
		ResponseBuilder builder;
		if (admission.getCode() == null) {
			builder = Response.status(Status.NOT_FOUND);
		} else {
			admission.setResult(result);
			admission.merge();
			builder = Response
					.seeOther(new URI(ENDPOINT_PATH + ADMISSION_PATH + StringPool.SLASH + admission.getCode()));
		}
		return builder.build();
	}

	@Secured("PERM_WRITE_PHOTO")
	@Override
	public void savePhoto(String admissionCode, String photo) {
		// TODO Auto-generated method stub

	}

	@Required
	public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
		this.securityContextHolderStrategy = securityContextHolderStrategy;
	}

}