package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.action.AdmissionAction;

public interface AdmissionEndpointHelper extends EndpointHelper<Admission> {

	public Response getAdmission(String admissionCode);

	public Response deleteAdmission(String admissionCode);

	public <T> Response mergeAdmission(String admissionCode, String baseLocation, T actor, AdmissionAction<T> action)
			throws URISyntaxException;
}
