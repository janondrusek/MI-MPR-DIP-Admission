package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import javax.ws.rs.core.Response;

public interface RegistrationEndpointHelper {

	public Response addRegistration(String admissionCode, String dateOfTerm, String room);
	
	public Response deleteRegistration(String admissionCode, String dateOfTerm, String room);
}
