package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.service.LdapService;
import cz.cvut.fit.mi_mpr_dip.admission.service.UserIdentityService;

@Service
public class MobileServiceImpl implements MobileService {

	@Autowired
	private LdapService ldapService;

	@Autowired
	private UserIdentityService userIdentityService;

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

	@Override
	public Response getPerson(String sessionId, String admissionCode) {
		return null;
	}

	@Override
	public void saveResult(String sessionId, String admissionCode, Double result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void savePhoto(String sessionId, String admissionCode, String photo) {
		// TODO Auto-generated method stub

	}

	public void setLdapService(LdapService ldapService) {
		this.ldapService = ldapService;
	}

	public void setUserIdentityService(UserIdentityService userIdentityService) {
		this.userIdentityService = userIdentityService;
	}

}
