package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.Admissions;

public interface AdmissionEndpoint {

	public Response getUserIdentity();

	public Response getAdmission(String admissionCode);

	public Admissions getAdmissions(Integer count, Integer page);

	public Response updateAdmission(Admission admission);

	public Response addAdmission(Admission admission);

	public Response deleteAdmission(String admissionCode);
}
