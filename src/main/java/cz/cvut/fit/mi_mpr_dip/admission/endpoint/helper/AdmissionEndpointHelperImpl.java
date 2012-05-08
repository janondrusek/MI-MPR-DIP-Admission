package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import javax.ws.rs.core.Response;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.builder.AdmissionsBuilder;
import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.Admissions;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.action.AdmissionAction;
import cz.cvut.fit.mi_mpr_dip.admission.validation.AdmissionCodeValidator;

@Service
@RooJavaBean
public class AdmissionEndpointHelperImpl extends CommonEndpointHelper<Admission> implements AdmissionEndpointHelper,
		ApplicationContextAware {

	@Autowired
	private AdmissionCodeValidator admissionCodeValidator;

	@Autowired
	private AdmissionDao admissionDao;

	private ApplicationContext applicationContext;

	@Autowired
	private UriEndpointHelper uriEndpointHelper;

	@Override
	public Admissions getAdmissions(Integer count, Integer page) {
		return buildAdmissions(count, page);
	}

	private Admissions buildAdmissions(Integer count, Integer page) {
		AdmissionsBuilder admissionsBuilder = getAdmissionsBuilder();

		admissionsBuilder.createNew();
		admissionsBuilder.buildLimit(count, page);
		admissionsBuilder.buildAdmissions();

		return admissionsBuilder.get();
	}

	private AdmissionsBuilder getAdmissionsBuilder() {
		return getApplicationContext().getBean(AdmissionsBuilder.class);
	}

	@Override
	public Response getAdmission(String admissionCode) {
		Admission admission = getAdmissionOrThrowNotFound(admissionCode);
		return getOkResponse(admission);
	}

	private Admission getAdmissionOrThrowNotFound(String admissionCode) {
		Admission admission = getAdmissionDao().getAdmission(admissionCode);
		validateNotFound(admission);
		return admission;
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

		return getSeeOtherResponse(getUriEndpointHelper().getAdmissionLocation(baseLocation, admission));
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

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
