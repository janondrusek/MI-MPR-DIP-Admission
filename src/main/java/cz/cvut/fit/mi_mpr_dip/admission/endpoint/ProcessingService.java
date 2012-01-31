package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

public interface ProcessingService {

	public Admission getAdmission(Long id);

	public List<Admission> getAdmissions();

	public Response addAdmission(Admission admission) throws URISyntaxException;
}
