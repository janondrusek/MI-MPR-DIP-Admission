package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;

public interface AdmissionEndpoint {

	public Response getAdmission(String admissionCode);
}
