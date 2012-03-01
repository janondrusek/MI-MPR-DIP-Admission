package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

@Service
public class AdmissionEndpointImpl implements AdmissionEndpoint {

	@Autowired
	private AdmissionDao admissionDao;

	@Override
	public Response getAdmission(String admissionCode) {
		Admission admission = admissionDao.getAdmission(admissionCode);
		ResponseBuilder builder;
		if (admission.getCode() == null) {
			builder = Response.status(Status.NOT_FOUND);
		} else {
			builder = Response.ok(admission);
		}
		return builder.build();
	}

}
