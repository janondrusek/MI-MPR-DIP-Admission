package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserRoles;

public interface UserIdentityEndpointHelper extends EndpointHelper<UserIdentity> {
	
	public Response getUserIdentity();
	
	public Response deleteUserSession(String username, String identifier);

	public Response updateUserRoles(String username, UserRoles userRoles);
}
