package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.service.LdapService;
import cz.cvut.fit.mi_mpr_dip.admission.service.UserIdentityService;

@Service(value = "mobileService")
@Path(MobileServiceImpl.ENDPOINT_PATH)
public class MobileServiceImpl implements MobileService {

	public static final String AUTHENTICATE_PATH = "/authenticate";
	public static final String PERSON_PATH = "/person";
	public static final String SAVE_RESULT_PATH = "/saveResult";
	public static final String SAVE_PHOTO_PATH = "/savePhoto";
	public static final String ENDPOINT_PATH = "/mobile";

	@Autowired
	private LdapService ldapService;

	@Autowired
	private UserIdentityService userIdentityService;

	@Path(AUTHENTICATE_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response authenticate(String username, String password) {
		boolean authentified = ldapService.authenticate(username, password);
		ResponseBuilder responseBuilder = null;
		if (authentified) {
			responseBuilder = Response.ok(userIdentityService.getUserIdentity(username));
		} else {
			responseBuilder = Response.status(Status.UNAUTHORIZED);
		}
		return responseBuilder.build();
	}

	@Secured("PERM_READ_PERSON")
	@Override
	public Response getPerson(String sessionId, String admissionCode) {
		return null;
	}

	@Secured("PERM_WRITE_RESULT")
	@Override
	public void saveResult(String sessionId, String admissionCode, Double result) {
		// TODO Auto-generated method stub

	}

	@Secured("PERM_WRITE_PHOTO")
	@Override
	public void savePhoto(String sessionId, String admissionCode, String photo) {
		// TODO Auto-generated method stub

	}

}