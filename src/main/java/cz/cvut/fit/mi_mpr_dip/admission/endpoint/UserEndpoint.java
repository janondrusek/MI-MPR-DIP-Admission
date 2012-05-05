package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.UserRoles;

public interface UserEndpoint {

	public Response getUserIdentity();

	public Response resetPassword(String email);

	public Response resetPassword(String admissionCode, String email);

	public Response updatePassword(String username, String oldPassword, String newPassword);

	public Response deleteUserSession(String username, String sessionIdentifier);

	public Response updateUserRoles(String username, UserRoles userRoles);
}
