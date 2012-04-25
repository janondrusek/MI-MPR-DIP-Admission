package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;

public interface UserEndpoint {

	public Response resetPassword(String email);

	public Response resetPassword(String admissionCode, String email);
}
