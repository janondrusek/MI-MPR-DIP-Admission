package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

public interface ProcessingEndpoint {

	public Response getAdmission(String admissionCode);

	public List<Admission> getAdmissions();
	
	public Response importAdmissions(Collection<Admission> admissions) throws URISyntaxException;

	public Response addAdmission(Admission admission) throws URISyntaxException;
}
