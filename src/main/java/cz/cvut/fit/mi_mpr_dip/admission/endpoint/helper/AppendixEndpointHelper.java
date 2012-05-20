package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import javax.ws.rs.core.Response;

public interface AppendixEndpointHelper {

	public Response getAdmissionPhoto(String admissionCode, String identifier);

	public Response getApologyAppendix(String admissionCode, String dateOfTerm, String room, String identifier);

}
