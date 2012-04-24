package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.UserRoles;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

public interface UserIdentityEndpointHelper extends EndpointHelper<UserIdentity> {
	
	public Response getUserIdentity();
	
	public Response deleteUserSession(String username, String identifier);

	public Response updateUserRoles(String username, UserRoles userRoles);
}
