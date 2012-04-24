package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.Admissions;

public interface ProcessingEndpoint {

	public Response getUserIdentity();

	public Response resetPassword(String email);

	public Response resetPassword(String admissionCode, String email);

	public Response getAdmission(String admissionCode);

	public Admissions getAdmissions(Integer count, Integer page);

	public Response updateAdmission(Admission admission);

	public Response addAdmission(Admission admission) throws URISyntaxException;

	public Response deleteAdmission(String admissionCode);
}
