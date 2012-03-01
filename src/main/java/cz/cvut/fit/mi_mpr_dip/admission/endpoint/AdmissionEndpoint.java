package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.endpoint.action.AdmissionAction;

public interface AdmissionEndpoint {

	public Response getAdmission(String admissionCode);

	public <T> Response mergeAdmission(String admissionCode, String baseLocation, T actor, AdmissionAction<T> action)
			throws URISyntaxException;
}
