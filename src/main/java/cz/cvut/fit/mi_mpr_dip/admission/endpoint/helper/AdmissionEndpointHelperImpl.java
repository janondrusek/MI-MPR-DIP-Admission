package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.action.AdmissionAction;
import cz.cvut.fit.mi_mpr_dip.admission.exception.util.BusinessExceptionUtil;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@Service
@RooJavaBean
public class AdmissionEndpointHelperImpl implements AdmissionEndpointHelper {

	public static final String IDENTITY_PATH = "/identity";

	@Autowired
	private AdmissionDao admissionDao;

	@Autowired
	private BusinessExceptionUtil businessExceptionUtil;

	@Override
	public Response getAdmission(String admissionCode) {
		Admission admission = getAdmissionDao().getAdmission(admissionCode);
		validateAdmissionCode(admission);
		return Response.ok(admission).build();
	}

	@Override
	public Response deleteAdmission(String admissionCode) {
		Admission admission = getAdmissionDao().getAdmission(admissionCode);
		validateAdmissionCode(admission);
		admission.remove();
		return Response.ok().build();
	}

	@Override
	public <T> Response mergeAdmission(String admissionCode, String baseLocation, T actor, AdmissionAction<T> action)
			throws URISyntaxException {
		Admission admission = getAdmissionDao().getAdmission(admissionCode);
		validateAdmissionCode(admission);
		action.performAction(admission, actor);
		admission.merge();
		return Response.seeOther(new URI(baseLocation + StringPool.SLASH + admission.getCode())).build();
	}

	private void validateAdmissionCode(Admission admission) {
		if (admission.getCode() == null) {
			getBusinessExceptionUtil().throwException(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
