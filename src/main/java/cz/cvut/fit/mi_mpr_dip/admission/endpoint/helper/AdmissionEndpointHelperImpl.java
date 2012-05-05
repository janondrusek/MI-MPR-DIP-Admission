package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.action.AdmissionAction;
import cz.cvut.fit.mi_mpr_dip.admission.validation.AdmissionCodeValidator;

@Service
@RooJavaBean
public class AdmissionEndpointHelperImpl extends CommonEndpointHelper<Admission> implements AdmissionEndpointHelper {

	public static final String IDENTITY_PATH = "/identity";

	@Autowired
	private AdmissionCodeValidator admissionCodeValidator;

	@Autowired
	private AdmissionDao admissionDao;

	@Autowired
	private UriEndpointHelper uriEndpointHelper;

	@Override
	public Response getAdmission(String admissionCode) {
		Admission admission = getAdmissionDao().getAdmission(admissionCode);
		validateNotFound(admission);
		return getOkResponse(admission);
	}

	@Override
	public Response deleteAdmission(String admissionCode) {
		Admission admission = getAdmissionDao().getAdmission(admissionCode);
		validateNotFound(admission);
		admission.remove();
		return getOkResponse();
	}

	@Override
	public <T> Response mergeAdmission(String admissionCode, String baseLocation, T actor, AdmissionAction<T> action) {
		Admission admission = getAdmissionDao().getAdmission(admissionCode);
		validateNotFound(admission);
		action.performAction(admission, actor);
		admission.merge();
		return Response.seeOther(getUriEndpointHelper().getAdmissionLocation(baseLocation, admission)).build();
	}

	@Override
	protected boolean isNotFound(Admission admission) {
		return admission.getCode() == null;
	}

	@Override
	public void validate(Admission admission) {
		super.validate(admission);
		getAdmissionCodeValidator().validate(admission);
	}
}
