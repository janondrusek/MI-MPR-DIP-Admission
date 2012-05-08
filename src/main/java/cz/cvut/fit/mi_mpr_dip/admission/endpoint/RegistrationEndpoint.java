package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;

public interface RegistrationEndpoint {

	public Response addRegistration(String admissionCode, String dateOfTerm, String room);
	
	public Response deleteRegistration(String admissionCode, String dateOfTerm, String room);
}
