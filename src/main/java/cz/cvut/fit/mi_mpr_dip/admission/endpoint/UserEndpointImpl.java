package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;

import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UriEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserPasswordService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@RooJavaBean
@Path(UserEndpointImpl.ENDPOINT_PATH)
public class UserEndpointImpl implements UserEndpoint {

	protected static final String ENDPOINT_PATH = "/user";

	private static final String PERSON_PATH = "/person";
	private static final String RESET_PASSWORD_PATH = "/reset_password";

	private static final String EMAIL_ATTRIBUTE = "email:";

	private static final String FULL_RESET_PASSWORD_PATH = PERSON_PATH + StringPool.SLASH + EMAIL_ATTRIBUTE + "{email}"
			+ RESET_PASSWORD_PATH;

	@Autowired
	private UriEndpointHelper uriEndpointHelper;

	@Autowired
	private UserPasswordService userPasswordService;

	@Path(FULL_RESET_PASSWORD_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response resetPassword(@PathParam("email") String email) {
		getUserPasswordService().createRandomPassword(email);
		return Response.ok().build();
	}

	@Path(ProcessingEndpointImpl.ADMISSION_PATH + "/{admissionCode}" + FULL_RESET_PASSWORD_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response resetPassword(@PathParam("admissionCode") String admissionCode, @PathParam("email") String email) {
		getUserPasswordService().createRandomPassword(admissionCode, email);
		return Response.seeOther(
				getUriEndpointHelper().getAdmissionLocation(
						ProcessingEndpointImpl.ENDPOINT_PATH + ProcessingEndpointImpl.ADMISSION_PATH, admissionCode))
				.build();
	}
}
