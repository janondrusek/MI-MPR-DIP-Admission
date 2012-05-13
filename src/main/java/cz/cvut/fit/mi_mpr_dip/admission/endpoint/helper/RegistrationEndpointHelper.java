package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Apology;

public interface RegistrationEndpointHelper {

	public Response addRegistration(String admissionCode, String dateOfTerm, String room);

	public Response deleteRegistration(String admissionCode, String dateOfTerm, String room);

	public Response addApology(String admissionCode, String dateOfTerm, String room, Apology apology);

	public Response deleteApology(String admissionCode, String dateOfTerm, String room);

	public Response updateApology(String admissionCode, String dateOfTerm, String room, Apology apology);
}
