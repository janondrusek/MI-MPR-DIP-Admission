package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.action.AdmissionAction;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@Service
public class AdmissionEndpointHelper implements EndpointHelper {

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

	@Override
	public Response deleteAdmission(String admissionCode) {
		Admission admission = admissionDao.getAdmission(admissionCode);
		ResponseBuilder builder;
		if (admission.getCode() == null) {
			builder = Response.status(Status.NOT_FOUND);
		} else {
			admission.remove();
			builder = Response.ok();
		}
		return builder.build();
	}

	@Override
	public <T> Response mergeAdmission(String admissionCode, String baseLocation, T actor, AdmissionAction<T> action)
			throws URISyntaxException {
		Admission admission = admissionDao.getAdmission(admissionCode);
		ResponseBuilder builder;
		if (admission.getCode() == null) {
			builder = Response.status(Status.NOT_FOUND);
		} else {
			action.performAction(admission, actor);
			admission.merge();
			builder = Response.seeOther(new URI(baseLocation + StringPool.SLASH + admission.getCode()));
		}
		return builder.build();
	}
}
